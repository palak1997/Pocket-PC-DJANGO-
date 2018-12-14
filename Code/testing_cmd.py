import os

def showFiles(String):
    folder=[]
    files=[]
    val={}
    print ("received String is : " , String)
    #String+="\\"
    contents = os.listdir(String)
    for content in contents:
        if os.path.isfile(String+content):
            files.append(content)
        elif os.path.isdir(String+content):
            folder.append(content) 

    #print "folder are : "
    # for f in folder:
    #     #print f
    #     files.append(f)

    # #print "files are : "
    # for f in files:
    #     #print f
    #    folder.append(f)
    val['file']=files
    val['folder']=folder
    return val

#TESTING SET
#data = subprocess.check_output("dir", shell=True)
#print "Output is : " + str(data)
#print "that's all"
# get={}
# get=showFiles("C:\\super yash\\")
# print "files are : " ,get['file']
# print "folders are : ",get['folder']
