#Import the os module, for the os.walk function
import os
import threading
import time

result = ""


class mythread(threading.Thread):
    def __init__(self, threadId, name, directory):
        threading.Thread.__init__(self)
        self.threadId = threadId
        self.name = name
        self.directory = directory

    def run(self):
        print("thread starting" + self.name + "\n")

        fun(self.directory)
        print("thread exiting" + self.name + "\n")


def fun(rd):
    # p=[C:\python codes,D:,E:]
    rootDir = rd
    i = 0
    j = 0
    global result
    # Set the directory you want to start from

    # for rootDir in p:

    for dirName, subdirList, fileList in os.walk(rootDir):
        i = i + 1
        # print("Found directory: ", i, dirName+"\n")
        j = 0
        for fname in fileList:
            j=j+1
            print("\t", j, fname)
            if fname == '295883.jpg':
                result = str(os.path.abspath(os.path.join(dirName, fname)))
                print("\n" + result + "\n")
                break

                # print("\n\n"+result+"\n\n")


thread1 = mythread(1, "Thread-1", "C:\Users\Avneet Kaur\Desktop")
thread2 = mythread(2, "Thread-2", "E:")
#thread3 = mythread(3, "Thread-3", "E:")

thread1.start()
thread2.start()
thread3.start()


