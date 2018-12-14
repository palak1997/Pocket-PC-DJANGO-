import smtplib
import socket
import sys
from email.mime.multipart import MIMEMultipart
from email.mime.base import MIMEBase
from email.mime.text import MIMEText
from email.utils import COMMASPACE, formatdate
from email import encoders


class Mailit(object):
    def __init__(self, mailid, password, to, sub, body, file=[]):
        self.mailid = mailid
        self.password = password
        self.to = to
        self.sub = sub
        self.body = body
        self.file = file
        # message
        msg = MIMEMultipart()
        msg['From'] = mailid
        msg['To'] = COMMASPACE.join(to)
        msg['Date'] = formatdate(localtime=True)
        msg['Subject'] = sub
        msg.attach(MIMEText(body))
        #print ("yy")
        l = len(file)
        #print(l)
        for i in range(0, l, 1):
            f = file[i]
            #print (f)
            #print(file[i])
            #print("kl")
            part = MIMEBase('application', "octet-stream")
            #print("k")
            part.set_payload(open(f, "rb").read())
            #print("this")
            encoders.encode_base64(part)
           # print("av")
            part.add_header('Content-Disposition', 'attachment', filename=f)
            #print("avni")
            msg.attach(part)
        # smtp connection, login, send mail
        #print("yes")
        i = self.mailid.find('@')
        j = self.mailid.find('.co')
        host = self.mailid[(i+1):j]
        #print(host)
        if host == 'gmail':
            #print("y")
            hostname = 'smtp.gmail.com'
        elif host == 'yahoo':
            hostname = 'smtp.mail.yahoo.com'
        elif host == 'hotmail' or host == 'outlook':
            hostname = 'smtp-mail.outlook.com'
        else:
            hostname = 'localhost'

        try:
            server = smtplib.SMTP(hostname, 587)
            server.ehlo()
            server.starttls()
            server.ehlo()
            print('connected to '+host+'\n')
            try:
                server.login(self.mailid, self.password)

            except smtplib.SMTPException:
                print(' authentication failed ')
                server.close()
                sys.exit(1)
        except (socket.gaierror, socket.error, socket.herror, smtplib.SMTPException):
            print(' connection to '+host+'failed ' + '\n')
            sys.exit(1)

        try:
            server.sendmail(self.mailid, self.to, msg.as_string())
        except smtplib.SMTPException:
            print('email could not be sent')
            server.close()
            sys.exit(1)

        print(' email sent successfully ')
        server.close()
        sys.exit(1)
