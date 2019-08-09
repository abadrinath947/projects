################################################
# CS 21B Intermediate Python Programming Lab #4
#
# Description:  change the format from mm/dd/yyyy
#               to a string version
# Output: stringized version of the date
# Version: 3.6.5
# Development Environment: Windows 10
# Developer:  Anirudhan Badrinath
# Date:  10/23/18
################################################

import re as r, sys

# used to store constant data such as month index conversions
DAYS_MAX = ["January", "March", "May", "July", "August", "October", "December"]
DAYS_MID = ["April", "June", "September", "November"]
MONTHS = ["January", "February", "March", "April", "May", "June", "July",
          "August", "September", "October", "November", "December"]

# symbolic constants
MIN_YEAR = 1000
MAX_YEAR = 2999
NUM_DATES = 5

def validate_input(user_input):
    """ Validate date the user inputs against the Gregorian calendar """
    day, month, year = fetch_data(user_input)
    # validate each aspect of the input separately
    if (not (validate_month(month) and
             validate_year(year) and
             validate_day(day, MONTHS[month - 1], year))):
        exit()
    # send it to main to print
    return [day, MONTHS[month - 1], year]

def fetch_data(user_input):
    """ Fetch and process user data using regular expressions """
    try:
        # parse each aspect of user data using "/" delimiter
        month = int(r.compile("^\d*").search(user_input).group(0))
        day, year = [rep.replace("/","") for rep in
                     r.compile("\/\d*").findall(user_input)]
    # in case it fails, error in user input
    except (IndexError, ValueError) as err:
        exit()
    return [day, month, year]

def validate_month(month):
    """ Validates the month (checks it is 1-12) """
    try:
        # basically checks whether 1-12 (valid)
        converted_month = MONTHS[month - 1]
    except IndexError:
        # invalid if out of bounds
        return False
    return True

def validate_year(yr):
    """ Validates the year (checks from 1000-2999) """
    return MIN_YEAR <= int(yr) <= MAX_YEAR

def validate_day(day, month, year):
    """ Validates the day (checks leap year as well) """
    if (month in DAYS_MAX):
        # 1 to 31 (inclusive) is valid
        day_pattern = r.compile("^0[1-9]|[12]\d|3[01]$")
    elif (month in DAYS_MID):
        # 1 to 30 (inclusive) is valid
        day_pattern = r.compile("^0[1-9]|[12]\d|3[0]$")
    else:
        if (int(year) % 4):
            # February -> not leap year -> 1 to 28 is valid
            day_pattern = r.compile("^0[1-9]|[1]\d|2[1-8]$")
        else:
            # leap year -> 1 to 29 is valid
            day_pattern = r.compile("^0[1-9]|[1]\d|2[1-9]$")

    return (day_pattern.match(day) != None)
    

def print_date(result):
    """ Print date in new format """
    print("The converted date is:", result[1], result[0] + ",",
          result[2])

def exit():
    """ Exit if user input is not valid """
    print("Valid dates are in the format mm/dd/yyyy with a valid" +
          " day, month, and year. Please try again.")
    # quit immediately
    sys.exit(1)

################## MAIN ########################

for i in range(NUM_DATES):
    user_input = input("Enter a date (mm/dd/yyyy): ")
    # get output array, process and print it
    res = validate_input(user_input)
    print_date(res)

################## RUN #########################

"""
Enter a date (mm/dd/yyyy): 04/29/2001
The converted date is: April 29, 2001
Enter a date (mm/dd/yyyy): 12/19/2001
The converted date is: December 19, 2001
Enter a date (mm/dd/yyyy): 01/01/1000
The converted date is: January 01, 1000
Enter a date (mm/dd/yyyy): 02/29/2004
The converted date is: February 29, 2004
Enter a date (mm/dd/yyyy): 02/29/3000
Valid dates are in the format mm/dd/yyyy with a valid day, month, and year. Please try again.
"""
