__author__ = 'colin'

import os
import time

from common import *


class App:
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

    def start(self):
        if self.applicationRunning():
            print("Application already running, exiting...")
        else:
            self.beginListen()

    def publishPid(self):
        try:
            pf = open(self.pidFile, "w")
            self.pid = os.getpid()
            pf.write(str(self.pid))
            pf.close()
        except IOError:
            self.pid = None

    def beginListen(self):
        log("starting daemon")
        self.publishPid()

        while True:
            time.sleep(1)




