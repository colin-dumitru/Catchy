__author__ = 'colin'

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

    def start(self, callback):
        if self.applicationRunning():
            print("Application already running, exiting...")
        else:
            self.beginListen(callback)

    def publishPid(self):
        try:
            pf = open(self.pidFile, "w")
            self.pid = os.getpid()
            pf.write(str(self.pid))
            pf.close()
        except IOError:
            self.pid = None

    def beginListen(self, callback):
        log("starting daemon")
        self.publishPid()

        while True:
            callback()
            time.sleep(60)


def main():
    app = App()
    app.initialize()

    def update():
        app.update()

    daemon = Daemon()
    daemon.start(update)


if __name__ == "__main__":
    main()


