__author__ = 'colin'

import time


class Config:
    debug = True


def log(message):
    if Config.debug:
        print("[%s] %s" % (time.strftime("%Y-%m-%d %H:%M:%S", time.gmtime()), message))