from ftplib import FTP
import sys, re, urllib.request, urllib.error, datetime

class DatabaseSearch:
    """ Class that searches a particular online database for keywords. """
    def __init__(self, keywords = []):
        self.keywords = keywords
        self.matches = {}

    def access_webpage(self, url):
        """ Access given webpage and error if there is a problem decoding """
        try:
            byte_str = urllib.request.urlopen(url) # byte string
            page_read = byte_str.read().decode("utf-8") # decoded byte string
        # page is unable to be read
        except urllib.error.URLError:
            print("ERROR: unable to open or decode given website")
            sys.exit(1)
        return page_read

    def search_alg(self, topic, web_url, case_sens=0):
        """ Report number of matches in webpage by accessing webpage """
        pattern = re.compile(topic)
        # O(logn)
        iterator = re.finditer(pattern, access_webpage(web_url))
        count = 0
        # O(logn)
        for match in iterator:
            count += 1
        # return result string to client
        return (topic, count)

    def find_matches(self, web_url):
        if web_url not in self.matches:
            self.matches[web_url] = []
        # O(n)
        for i in self.keywords:
            self.matches[web_url].append(self.find_match(web_url))
        return self.matches[web_url]

class ftp(FTP):
    """ Class to FTP into remote hosts and fetch files. """
    def __init__(self, path, user, pw):
        FTP.__init__(self, path)
        FTP.login(self, user, pw)

    cd = lambda self, path: FTP.cwd(self, path) 
    ls = lambda self: FTP.retrlines(self, "LIST")
    get = lambda self, A: FTP.retrbinary(self, "RETR " + A, open(A, 'wb').write)

class KeySort:
    """ Class used for Sorting (helper). """
    def __init__(self, iterable):
        self.iterable = iterable

    def sort(self):
        
class KeyedHeap:
    """ Class used for sorting structure. """
    def __init__(self, key_func = min): 
        self.keyed_heap = [None]
        self.key = key_func
  
    def parent(self, i): 
        return i // 2

    def child(self, n):
        return 2 * i + n
      
    def insert(self, k): 
  
    def decreaseKey(self, i, new_val): 
        self.heap[i] = new_val  
        while(i != 0 and self.heap[self.parent(i)] > self.heap[i]): 
            self.heap[i] , self.heap[self.parent(i)] = \
            self.heap[self.parent(i)], self.heap[i]
              
    def pop(self): 
        return self.heap.pop(0)
  
    def deleteKey(self, i): 
        self.decreaseKey(i, float("-inf")) 
        self.extractMin() 
  
    def peek(self): 
        return self.heap[0] 
