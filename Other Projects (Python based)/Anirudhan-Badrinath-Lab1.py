################################################
# CS 21B Intermediate Python Programming Lab #1
# Topics: Arithmetic, Data Types, User Input,
#         Formatting Output, Importing Modules
# Description:  To evaluate arithmetic expressions
#               using Python.
# User Input: student ID, name, various other vars
# Output: evaluates expressions
# Version: 3.6.5
# Development Environment:  Windows 10
# Developer:  Anirudhan Badrinath
# Date:  10/02/18
################################################

import datetime as dt

########## FUNCTIONS ###########################

def get_student_id():
    """ gets valid student ID input """
    REQUIRED_LENGTH = 8
    
    while (True):
        # make sure it is an integer
        try:
            student_id = int(input("Enter your Student ID: "))
        except ValueError:
            print("Try again, ID should contain 8 digits & be numeric")
            continue
        # make sure it has the right length
        if (len(str(student_id)) != REQUIRED_LENGTH):
            print("Try again, ID should contain 8 digits & be numeric")
        # return if conditions are met
        else:
            return student_id

def get_family_name():
    """ gets a valid family name input """
    MIN_LENGTH = 2
    MAX_LENGTH = 15
    
    while (True):
        family_name = input("Enter your family name: ")
        # check alpha and length
        if (not family_name.isalpha() or
            not MIN_LENGTH <= len(family_name) <= MAX_LENGTH):
            print("Try again, family name should be only " +
                  "characters and between 2 and 15 chars long")
        else:
            return len(family_name)

def sum_of_digits(num):
    """ gets sum of all the digits in the number passed in """
    sum = 0
    while (num != 0):
        # sum last digit and divide by 10
        sum += num % 10
        num //= 10
    return sum

def print_output(arr):
    """ prints output in the requested format using array structure """
    for i in range(0, len(arr)):
        print("expression ", i + 1, ": ", arr[i])

def calculate_exprs(my_id, n_let):
    """ calculate each of the 10 expressions """
    expressions = []

    # evaluate the 9 expressions and store in array
    expressions.append(format(my_id / 2, '.2f'))
    expressions.append(my_id % 2)
    expressions.append(sum(range(2, n_let + 1)))
    expressions.append(my_id + n_let)
    expressions.append(abs(n_let - my_id))
    expressions.append(format(my_id / (n_let + 1100), '.2f'))
    expressions.append((n_let % n_let) and (my_id * my_id))
    expressions.append(1 or (my_id / 0))
    expressions.append(format(round(3.15, 1), '.2f'))

    return expressions

########### MAIN PROGRAM #######################

# get the family name letters and sum of digits of stut ID
n_let = get_family_name()
my_id = sum_of_digits(get_student_id())

print(" my_id is: ", my_id, "\n" +
      " n_let is: ", n_let)

# calculate the 9 expressions and store in array
expressions = calculate_exprs(my_id, n_let)

# iterate through printing all expressions
print_output(expressions)

# print date and time as timestamp
print("Today's date is ", dt.datetime.now().strftime("%Y-%m-%d"))

################################################

"""
Output:

Enter your Student ID: 20346732
Enter your family name: Badrinath
 my_id is:  27 
 n_let is:  9
expression  1 :  13.50
expression  2 :  1
expression  3 :  44
expression  4 :  36
expression  5 :  18
expression  6 :  0.02
expression  7 :  0
expression  8 :  1
expression  9 :  3.10
Today's date is  2018-10-02
"""
