__author__ = 'Colin Dumitru'

import sqlite3
import urllib.request
import urllib.parse
import xml.etree.ElementTree as ET
from threading import Timer
from gi.repository import Gtk
from gi.repository import AppIndicator3 as appindicator
from gi.repository import GObject
from gi.repository import Gio

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

    @staticmethod
    def getExistingGuids(feedId, guids):
        rows = G.connection.execute(
            "SELECT guid FROM item WHERE feed_id = ? AND guid IN (%s)" % ",".join(["?"] * len(guids)),
            [feedId] + guids).fetchall()
        return [row[0] for row in rows]

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
        self.unread = 0

    @staticmethod
    def getAll():
        return [Feed.fromRow(row) for row in G.connection.execute("SELECT * FROM feed")]

    @staticmethod
    def getUnread(feed_id):
        return G.connection.execute("SELECT COUNT(*) FROM item WHERE feed_id = ?1 AND read = 0",
                                    (feed_id, )).fetchone()[0]


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
        self.time = None

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

    def createAndBindMenuItem(self, label, method, param=None, image=None):
        img = None

        if image:
            img = Gtk.Image(stock=image)

        item = Gtk.ImageMenuItem(label=label, image=img)
        item.set_always_show_image(True)

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

        self.createAndBindMenuItem("Open", self.openApplication, "open", image=Gtk.STOCK_OPEN)
        self.addMenuSeparator()
        self.initFeedItems()
        self.addMenuSeparator()
        self.createAndBindMenuItem("Quit", self.quit, "quit", image=Gtk.STOCK_CLOSE)

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
        self.startStopwatch()
        log("updating feeds")
        G.connection = sqlite3.connect(Config.resourceRoot + "db/main.sqlite")

        self.performUpdate()

        G.connection.close()

        self.cachedTimer = Timer(60, self.update)
        self.cachedTimer.start()

        self.printElapsed()

    def performUpdate(self):
        self.metadata.update()
        self.loadItems()
        self.printNewestItems()
        self.storeNewestItems()
        self.updateMenuItems()

    def startStopwatch(self):
        self.time = time.time()

    def printElapsed(self):
        now = time.time()
        log("look %d milliseconds to update feeds" % (now - self.time))

    def updateMenuItems(self):
        for feed in self.metadata.feeds:
            feed.unread = Feed.getUnread(feed.id)

        sortedFeeds = sorted([feed for feed in self.metadata.feeds if feed.unread > 0], key=lambda x: x.unread)
        numFeeds = len(sortedFeeds)

        self.updateMenuItem(numFeeds, sortedFeeds)

        if numFeeds == 0:
            self.cachedMenuItems[0].show()
            self.cachedMenuItems[0].get_child().set_text("No new items")

        self.updateIndicatorIcon(numFeeds)

    def updateMenuItem(self, numFeeds, sortedFeeds):
        for index in range(0, Config.maxMenuItems):
            if index >= numFeeds:
                self.cachedMenuItems[index].hide()
            else:
                self.cachedMenuItems[index].show()
                self.updateMenuItemText(index, numFeeds, sortedFeeds)

    def updateMenuItemText(self, index, numFeeds, sortedFeeds):
        if index == (Config.maxMenuItems - 1) and numFeeds > Config.maxMenuItems:
            self.cachedMenuItems[index].get_child().set_text("And (%d) more" % (numFeeds - Config.maxMenuItems))
        else:
            self.cachedMenuItems[index].get_child().set_text(
                "%s (%d)" % (sortedFeeds[index].title, sortedFeeds[index].unread))

    def updateIndicatorIcon(self, numFeeds):
        if numFeeds > 0:
            self.indicator.set_status(appindicator.IndicatorStatus.ATTENTION)
        else:
            self.indicator.set_status(appindicator.IndicatorStatus.ACTIVE)

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
        existingGuids = Item.getExistingGuids(feed.id, [item.guid for item in items])
        feed.items = [item for item in items if item.guid not in existingGuids]

    def loadItems(self):
        for feed in self.metadata.feeds:
            self.loadItemsInFeed(feed)

    def printNewestItems(self):
        for feed in self.metadata.feeds:
            for item in feed.items:
                Notifier.pushNotification(feed.title, item.title)

        Notifier.showNotifications()
