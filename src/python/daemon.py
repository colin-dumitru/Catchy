__author__ = 'colin'

import os

from application import *


class Daemon:
    def __init__(self):
        self.pid = None
        self.pidFile = "/tmp/richfeed.pid"

    def readPid(self):
        try:
            pf = open(self.pidFile, "r")
            self.pid = int(pf.read().strip())
            pf.close()
        except IOError:
            self.pid = None

    def applicationRunning(self):
        self.readPid()

        if self.pid:
            try:
                os.kill(self.pid, 0)
                return True
            except OSError:
                return False

    def start(self, app):
        if self.applicationRunning():
            print("Application already running, exiting...")
        else:
            self.beginListen(app)

    def publishPid(self):
        try:
            pf = open(self.pidFile, "w")
            self.pid = os.getpid()
            pf.write(str(self.pid))
            pf.close()
        except IOError:
            self.pid = None

    def beginListen(self, app):
        log("starting daemon")
        self.publishPid()

        app.start()


def main():
    app = App()
    app.initialize()

    daemon = Daemon()
    daemon.start(app)


if __name__ == "__main__":
    main()


