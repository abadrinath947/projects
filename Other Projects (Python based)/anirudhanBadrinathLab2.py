################################################
# CS 21B Intermediate Python Programming Lab #2
#
# Description: Processes address entry file, provides
#              insights and writes to a new file as
#              as another address book.
# User Input: file name to process
# Output: file with address book and number of entries
# Version: 3.6.5
# Development Environment:  Windows 10
# Developer:  Anirudhan Badrinath
# Date:  10/9/18
################################################

import sys

def process_file(file_name):
   """ Checks formatting of the file and processes it """
   # symbolic constants 
   LAST_NAME = 1
   FIRST_NAME = 2
   ADDRESS_ONE = 3
   ADDRESS_TWO = 4
   PHONE_NUMBER = 5

   # open file
   with open(file_name) as file_loop:
      
      current_line = file_loop.readline().strip()
      arr_list = [] # for writing to phonebook.txt later
      address_count = 0 # number of addresses
      line_type = LAST_NAME # first line is last name

      # loop through all lines till empty line
      while (current_line != ""):
         if (line_type == LAST_NAME):
             # makes sure name is not a number, etc.
             if (not is_alpha(current_line)):
                 return False
             arr_list.append(current_line)
         elif (line_type == FIRST_NAME):
            # makes sure name is not a number, etc.
            if (not is_alpha(current_line)):
              return False
            arr_list[len(arr_list) - 1] += ", " + current_line
         elif (line_type == ADDRESS_ONE):
            if (not check_address_one(current_line)):
               return False
         elif (line_type == ADDRESS_TWO):
            if (not check_address_two(current_line)):
               return False
         elif (line_type == PHONE_NUMBER):
             # makes sure the phone number matches format
             if (not check_phone_number(current_line)):
                 return False
             arr_list.append(current_line)
             line_type = 0
             address_count += 1

         line_type += 1
         current_line = file_loop.readline().strip()
      # make sure full loops are completed
      if (line_type != 1):
         return False
      # use list to write to the file, return the count to client
      create_phonebook(arr_list)
      return address_count

def is_alpha(string):
   """ checks if name is valid (not a number) """
   return all(not x.isdigit() for x in string)

def check_phone_number(string):
   """ checks if phone number is valid """
   for i in range(len(string)):
      # account for dashes in middle
      if ((i != 3 and i != 7) and not string[i].isdigit()):
         return False
      if ((i == 3 or i == 7) and string[i] != "-"):
         return False
   return True

def check_address_one(string):
   """ checks if first line of address is valid """
   return (string[0].isdigit() and
           all(x.isdigit() or x.isalpha() or x.isspace() for x in string))

def check_address_two(string):
   """ checks if second line of address is valid """
   return string[-5:].isdigit() and "," in string

def create_phonebook(arr_list):
   """ writes data to phonebook.txt file while checking for duplicates """
   # open as append to not erase old stuff
   with open('phonebook.txt', 'a') as outfile:
      # to check for duplicates
      temp_read = open("phonebook.txt", "r")
      contents = temp_read.read()
      i = 0
      while (i < len(arr_list)):
         # if not duplicated, append, otherwise skip
         if (arr_list[i] not in contents):
            outfile.write("%s\n" % arr_list[i])
            outfile.write("%s\n" % arr_list[i + 1])
         i += 2

def get_input_file():
   """ get an input file that exists, or exit """
   while (True):    
      file_name = input("Please enter file name to read" +
                        " <Hit Enter to Quit>: ")         
      # if user wants to quit
      if (not file_name):
          return ""
      # check if file exists
      try:
         file = open(str(file_name), '+r')
      except FileNotFoundError:
         print("  File not found, please try again")
         continue
      # close and return if file is found
      file.close()
      return file_name

#################### MAIN ######################

while (True):
   input_file = get_input_file()
   # if user wants to quit
   if (input_file == ""):
      print("  Quitting...")
      sys.exit(0)
   # find number of entries and print
   count = process_file(input_file)
   if (not count):
      print("  Your file is not in the phonebook format, please try again.")
   else:
      print("  You have", count, "entries in your demo address book")
      
################################################

"""
RUN:

Please enter file name to read <Hit Enter to Quit>: nofile.txt
  File not found, please try again
Please enter file name to read <Hit Enter to Quit>: doesnt_exist
  File not found, please try again
Please enter file name to read <Hit Enter to Quit>: err.txt
  Your file is not in the phonebook format, please try again.
Please enter file name to read <Hit Enter to Quit>: main.cpp
  Your file is not in the phonebook format, please try again.
Please enter file name to read <Hit Enter to Quit>: demo.txt
  You have 2 entries in your demo address book
Please enter file name to read <Hit Enter to Quit>: addressbook.txt
  You have 6 entries in your demo address book
Please enter file name to read <Hit Enter to Quit>: i_want_to_quit
  File not found, please try again
Please enter file name to read <Hit Enter to Quit>: 
  Quitting...

"""
