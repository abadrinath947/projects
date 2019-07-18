###############################################################################
# CS 21 B Intermediate Programming(Python)
# Description:  Searches an online database for some search terms and 
#               determines number of matches.
# Input/Output: none, prints number of matches
# Version: Python 3.6.5 32-Bit
# Development Environment: Windows 10
# Developer: Anirudhan Badrinath
# Date: 11/6/18
###############################################################################

import sys, re, urllib.request, urllib.error, datetime

# access URL for the website - symbolic constant
ACCESS_URL = "http://nasonline.org"

# list that is provided in the spec and my list, concatenated
SPEC_LIST = ["research", "climate", "evolution", "cultural", "leadership"]
MY_LIST = ["election"]
CONCAT_LIST = SPEC_LIST + MY_LIST

################################## FUNC #######################################

def access_webpage(url):
    """ Access given webpage and error if there is a problem decoding """
    try:
        byte_str = urllib.request.urlopen(url) # byte string
        page_read = byte_str.read().decode("utf-8") # decoded byte string
    # page is unable to be read
    except urllib.error.URLError:
        print("ERROR: unable to open or decode given website")
        # exit program
        sys.exit(1)
    return page_read

def find_matches(topic, web_url):
    """ Report number of matches in webpage by accessing webpage """
    pattern = re.compile(topic)
    iterator = re.finditer(pattern, access_webpage(web_url))
    # count number of matches in iterated results (case sensitive)
    count = 0
    for match in iterator:
        count += 1

    # return result string to client
    return (topic + " appears " + str(count) + " times.")

################################# MAIN ########################################

# print date using datetime module
print("Today's date is: ", datetime.datetime.now().date(), "\n")

# iterate through the list of topics
for topic in CONCAT_LIST:
    print(find_matches(topic, ACCESS_URL))
    
################################# RUN #########################################
"""
Today's date is:  2018-11-06 

research appears 5 times.
climate appears 4 times.
evolution appears 3 times.
cultural appears 4 times.
leadership appears 4 times.
election appears 1 times.
"""
