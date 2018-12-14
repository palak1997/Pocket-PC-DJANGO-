import threading
import time
import re
from bs4 import BeautifulSoup
from facepy import GraphAPI
import facebook

ACCESS_TOKEN = 'EAACEdEose0cBADD7LgfPk0TFbllOMCzQaVKmuKW4ZBstq7f11a1URIrmyeWwcVHpinVjbLMhgy4qimdoB7sjbfE8N2aSds8oPTt9RrHVO6fwZB8G770X6UBdxRnwd0DWqkVJtxpxjo9xZCdmU3aZBP9QX8aYwJqQRYZAN4UCZCPywo9GzhFjws6EeQRJ3rSUwZD'

class imageupload(threading.Thread):
    def __init__(self,threadId,name,path,mess):
        threading.Thread.__init__(self)
        self.threadId=threadId
        self.name=name
        self.mess=mess
        self.path= path

    def run(self):
            print("thread starting",self.name)
            #time.sleep(10)
            graph = facebook.GraphAPI(ACCESS_TOKEN)
            #photo=open(self.path,'rb')
            #graph.put_object("me","photos",message=self.mess,source=photo.read())
            graph.put_photo(image=open(self.path, 'rb'), message=self.mess)
            #photo.close()
            print("thread exiting")
