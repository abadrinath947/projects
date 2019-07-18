###############################################################################
# CS 21B Intermediate Python Programming Lab #1
# Description: Logs into an FTP server and prints
#              info and fetches file.
# User Input: none
# Output: file and info about server
# Version: 3.6.5
# Development Environment:  Windows 10
# Developer:  Anirudhan Badrinath
# Date:  11/28/18
###############################################################################

from ftplib import FTP

############################# FUNCTIONS #######################################

def login(ftp_obj):
   """ Logs into the server and displays the welcome message """
   try:
      print("Server Connection: " +
            ftp_obj.login("anonymous", "anonymous") + "\n")
   except ftplib.all_errors:
      # if the server login fails (validate)
      print("Error: couldn't connect to the server.")
      exit()
   # show welcome message to user output
   print("Welcome Message:" +
         "\n======================\n" +
         ftp_obj.getwelcome() +
         "\n======================\n")

def pwd(ftp_obj):
   """ Shows current working directory """
   print("Current Working Directory: " +
         ftp_obj.pwd() + "\n")

def ls(ftp_obj):
   """ Shows directory listing """
   print("Directory Listing:"
         "\n======================")
   # show the ls result
   print(ftp_obj.retrlines("LIST") +
         "\n======================\n")

def cd(ftp_obj, directory):
   """ Moves current working directory """
   ftp_obj.cwd(directory)

def get(ftp_obj, file):
   """ Download file from the server """
   try:
      ftp_obj.retrlines('RETR passwd')
   except ftplib.all_errors:
      # if the transfer fails
      print("Error: Couldn't transfer file")
      exit()   

################################## MAIN #######################################

# set up ftp server connection
ftps = FTP("ftp.cse.buffalo.edu")

# display welcome message, current working dir and directory contents
login(ftps)
pwd(ftps)
ls(ftps)

# fetch file /etc/passwd (first go to the directory)
cd(ftps, "etc")
get(ftps, "passwd")
print("File (/etc/passwd) transferred successfully!")

################################## RUN ########################################

"""
Server Connection: 230 Guest login ok, access restrictions apply.

Welcome Message:
======================
220 ftp.cse.buffalo.edu FTP server (Version wu-2.6.2(1) Fri Aug 20 01:38:05 EDT 2010) ready.
======================

Current Working Directory: /

Directory Listing:
======================
total 28
drwxrwxrwx   2 0       0     4096 Sep  6  2015 .snapshot
drwxr-xr-x   2 202019  5564  4096 Feb  2  2018 CSE421
drwxr-xr-x   2 0       0     4096 Jul 23  2008 bin
drwxr-xr-x   2 0       0     4096 Mar 15  2007 etc
drwxr-xr-x   6 89987   546   4096 Sep  6  2015 mirror
drwxrwxr-x   7 6980    546   4096 May  4  2018 pub
drwxr-xr-x  26 0       11    4096 Apr 29  2016 users
226 Transfer complete.
======================

File (/etc/passwd) transferred successfully!
"""

