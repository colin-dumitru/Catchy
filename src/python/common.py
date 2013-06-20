__author__ = 'colin'

import time


class Config:
    debug = True
    resourceRoot = "../../resources/"
    maxNotification = 10
    maxMenuItems = 10


class G:
    connection = None


def log(message):
    if Config.debug:
        print("[DEB][%s] %s" % (time.strftime("%Y-%m-%d %H:%M:%S", time.gmtime()), message))


def err(message):
    print("[ERR][%s] %s" % (time.strftime("%Y-%m-%d %H:%M:%S", time.gmtime()), message))


def shorten(str, maxLen):
    suffix = ""

    if len(str) > maxLen:
        suffix = "..."
        maxLen -= 3

    return str[:maxLen] + suffix
