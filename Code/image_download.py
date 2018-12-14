try:
    import urllib.request as urllib2
except ImportError:
    import urllib2

class imagedownload(object):
    def __init__(self,address):
        self.address=address
        print(address)


    def down():
        
      resource = urllib2.urlopen(self.address)
      print(resource)
      output = open("indexfb.jpg","wb")
      print(output)
      #image gets stored in the same directory where this code is saved"""
      output.write(resource.read())
      output.close()
