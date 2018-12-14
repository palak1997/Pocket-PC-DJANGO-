import threading
from endtag_parse import *
import time


class mainthread(threading.Thread):
    def __init__(self, threadId, name):
        threading.Thread.__init__(self)
        self.threadId = threadId
        self.name = name

    def run(self):
        #time.sleep(20)
        req = Scrap()
        req.find_req()

thread1 = mainthread(1, "Thread-1")

thread1.start()
