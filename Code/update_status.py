import threading
import time
#import urllib.request
import re
from bs4 import BeautifulSoup
from facepy import GraphAPI

ACCESS_TOKEN = 'EAACEdEose0cBADD7LgfPk0TFbllOMCzQaVKmuKW4ZBstq7f11a1URIrmyeWwcVHpinVjbLMhgy4qimdoB7sjbfE8N2aSds8oPTt9RrHVO6fwZB8G770X6UBdxRnwd0DWqkVJtxpxjo9xZCdmU3aZBP9QX8aYwJqQRYZAN4UCZCPywo9GzhFjws6EeQRJ3rSUwZD'

class updatestatus(threading.Thread):
    def __init__(self,threadId,name,mess):
        threading.Thread.__init__(self)
        self.threadId=threadId
        self.name=name
        self.mess=mess

    def run(self):
            print("thread starting",self.name)
            #time.sleep(10)
            graph=GraphAPI(ACCESS_TOKEN)
            graph.post('me/feed', message=self.mess)
            print("thread exiting")


#thread1 = mythread1(1,"Thread-1","great success23")


#thread1.start()
