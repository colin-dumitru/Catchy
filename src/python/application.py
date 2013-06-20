from threading import Timer

__author__ = 'colin'

import sqlite3
import urllib.request
import urllib.parse
import xml.etree.ElementTree as ET
from gi.repository import Gtk
from gi.repository import AppIndicator3 as appindicator
from gi.repository import GObject

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

    @staticmethod
    def store(feedId, item):
        G.connection.execute("INSERT INTO item (guid, feed_id, xml, read, stared, pub_date) VALUES (?, ?, ?, ?, ?, ?)",
                             (item.guid, feedId, item.xml, 0, 0, time.time()))

    @staticmethod
    def deleteWithNullGuid():
        G.connection.execute("DELETE FROM item WHERE guid = NULL")

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
        rows = G.connection.execute("SELECT guid FROM item WHERE feed_id = ?1 ORDER BY pub_date desc LIMIT ?2",
                                    (feedId, count)).fetchall()
        return [row[0] for row in rows]


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
        self.indicator = None
        self.menu = None
        self.cachedMenuItems = None
        self.cachedTimer = None

    def initialize(self):
        Notifier.initialize()
        self.metadata.initialize()
        self.initMenu()
        self.initIndicator()

    def initIndicator(self):
        self.indicator = appindicator.Indicator.new("richfeed-indicator", "indicator-messages",
                                                    appindicator.IndicatorCategory.APPLICATION_STATUS)
        self.indicator.set_status(appindicator.IndicatorStatus.ACTIVE)
        self.indicator.set_attention_icon("indicator-messages-new")

        self.indicator.set_menu(self.menu)

    def initMenu(self):
        self.menu = Gtk.Menu()
        self.resetMenuItems()

    def addMenuSeparator(self):
        hBar = Gtk.SeparatorMenuItem()
        hBar.show()
        self.menu.append(hBar)

    def createAndBindMenuItem(self, label, method, param=None):
        item = Gtk.MenuItem(label)
        item.show()
        item.connect_object("activate", method, param)
        self.menu.append(item)
        return item

    def openApplication(self, param):
        log("opening application")

    def quit(self, param):
        log("quiting")
        if self.cachedTimer:
            self.cachedTimer.cancel()
        Gtk.main_quit()

    def resetMenuItems(self):
        for child in self.menu:
            self.menu.remove(child)

        self.createAndBindMenuItem("Open", self.openApplication, "open")
        self.addMenuSeparator()
        self.initFeedItems()
        self.addMenuSeparator()
        self.createAndBindMenuItem("Quit", self.quit, "quit")

    def initFeedItems(self):
        self.cachedMenuItems = [self.createAndBindMenuItem("Feed", self.openFeed, index) for index in
                                range(0, Config.maxMenuItems)]

    def openFeed(self, param):
        log("opening feed")

    def start(self):
        GObject.threads_init()
        self.update()
        Gtk.main()

    def update(self):
        log("updating feeds")
        G.connection = sqlite3.connect(Config.resourceRoot + "db/main.sqlite")

        self.metadata.update()
        self.loadItems()
        self.printNewestItems()
        self.storeNewestItems()
        self.updateMenuItems()

        G.connection.close()

        self.cachedTimer = Timer(60, self.update)
        self.cachedTimer.start()

    def updateMenuItems(self):
        sortedFeeds = sorted([feed for feed in self.metadata.feeds if len(feed.items) > 0], key=lambda x: len(x.items))
        numFeeds = len(sortedFeeds)

        for index in range(0, Config.maxMenuItems):
            if index >= numFeeds:
                self.cachedMenuItems[index].hide()
            else:
                self.cachedMenuItems[index].show()
                self.updateMenuItemText(index, numFeeds, sortedFeeds)

        if numFeeds == 0:
            self.cachedMenuItems[0].show()
            self.cachedMenuItems[0].get_child().set_text("No new items")

        self.updateIndicatorIcon(numFeeds)

    def updateIndicatorIcon(self, numFeeds):
        if numFeeds > 0:
            self.indicator.set_status(appindicator.IndicatorStatus.ATTENTION)
        else:
            self.indicator.set_status(appindicator.IndicatorStatus.ACTIVE)

    def updateMenuItemText(self, index, numFeeds, sortedFeeds):
        if index == (Config.maxMenuItems - 1) and numFeeds > Config.maxMenuItems:
            self.cachedMenuItems[index].get_child().set_text("And (%d) more" % (numFeeds - Config.maxMenuItems))
        else:
            self.cachedMenuItems[index].get_child().set_text(
                "%s (%d)" % (sortedFeeds[index].title, len(sortedFeeds[index].items)))

    def storeNewestItems(self):
        itemsUpdated = 0

        for feed in self.metadata.feeds:
            for item in feed.items:
                itemsUpdated += 1
                Item.store(feed.id, item)

        G.connection.commit()

        log("%d new items were added to the db" % itemsUpdated)

    def deleteItemsWithNullGuid(self):
        Item.deleteWithNullGuid()

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
                Notifier.pushNotification(feed.title, item.title)

        Notifier.showNotifications()
