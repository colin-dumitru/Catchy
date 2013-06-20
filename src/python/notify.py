import notify2

from common import *


#todo limit notifications
class Notifier:
    @staticmethod
    def initialize():
        if not notify2.init("icon-summary-body"):
            err("cannot initialise notifications")

    @staticmethod
    def notifyItem(feedName, item):
        notification = notify2.Notification(feedName, item.title)
        notification.show()

