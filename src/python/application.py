__author__ = 'colin'

import sqlite3
import urllib.request
import urllib.parse
import xml.etree.ElementTree as ET

from common import *
from notify import *


class Item:
    @staticmethod
    def fromXml(root):
        item = Item()

        item.xml = ET.tostring(root)
        item.title = root.find("title").text
        item.description = root.find("description").text
        item.guid = root.find("guid").text

        return item

    def __init__(self):
        self.title = None
        self.description = None
        self.guid = None
        self.xml = None
        self.image = None


class Feed:
    @staticmethod
    def fromRow(row):
        feed = Feed()

        feed.id = row[0]
        feed.title = row[2]
        feed.url = row[3]
        feed.notify = row[4] == 1

        return feed

    def __init__(self):
        self.items = []
        self.id = 0
        self.title = None
        self.url = None
        self.notify = False
        self.lastValidGuid = None

    @staticmethod
    def getAll():
        return [Feed.fromRow(row) for row in G.connection.execute("SELECT * FROM feed")]

    @staticmethod
    def getLastGuid(feedId, count):
        return G.connection.execute("SELECT guid FROM item WHERE feed_id = ?1 ORDER BY pub_date LIMIT ?2",
                                    (feedId, count)).fetchall()


class MetadataCache:
    def __init__(self):
        self.feeds = []
        self.invalidated = True

    def initialize(self):
        pass

    def reload(self):
        log("metadata invalidated, performing update")

        self.reloadFeeds()

        log("metadata reloaded")
        self.invalidated = False

    def reloadFeeds(self):
        self.feeds = [feed for feed in Feed.getAll() if feed.notify]

    def update(self):
        if self.invalidated:
            self.reload()


class App:
    def __init__(self):
        self.metadata = MetadataCache()

    def initialize(self):
        G.connection = sqlite3.connect(Config.resourceRoot + "db/main.sqlite")

        Notifier.initialize()
        self.metadata.initialize()

    def update(self):
        log("updating feeds")
        self.metadata.update()
        self.loadItems()
        self.printNewestItems()

    def loadItemsInFeed(self, feed):
        root = ET.fromstring(urllib.request.urlopen(feed.url).read())
        items = [Item.fromXml(item) for chanel in root.findall("channel") for item in chanel.findall("item")]
        lastGuids = Feed.getLastGuid(feed.id, len(items))
        feed.items = [item for item in items if item.guid not in lastGuids]

    def loadItems(self):
        for feed in self.metadata.feeds:
            self.loadItemsInFeed(feed)

    def printNewestItems(self):
        for feed in self.metadata.feeds:
            for item in feed.items:
                Notifier.notifyItem(feed.title, item)









