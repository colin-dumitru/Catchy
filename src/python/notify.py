import notify2

from common import *


#todo limit notifications
class Notifier:
    messageQueue = []

    @staticmethod
    def initialize():
        if not notify2.init("icon-summary-body"):
            err("cannot initialise notifications")

    @staticmethod
    def pushNotification(title, content):
        Notifier.messageQueue.append((title, content))

    @staticmethod
    def showNotifications():
        Notifier.messageQueue = Notifier.messageQueue[:min(Config.maxNotification, len(Notifier.messageQueue))]

        for n in Notifier.messageQueue:
            notify2.Notification(n[0], n[1]).show()

        Notifier.messageQueue = []

