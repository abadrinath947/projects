################################################
# CS 21B Intermediate Python Programming Lab #3
#
# Description:  Simulates a bank, can withdraw,
#               deposit, add interest
# User Input: None
# Output: Bank Balance
# Version: 3.6.5
# Development Environment:  Windows 10
# Developer:  Anirudhan Badrinath
# Date:  10/16/18
################################################

from datetime import datetime
from decimal import *

class BankAccount:

    MIN_RATE = 1 # minimum interest rate
    MAX_RATE = 2 # maximum interest rate
    OVERCHARGE_FEE = Decimal(10.00) # fine for overwithdrawing
    ROUND = Decimal("0.01") # rounding to hundredths

    intr_month = -1 # static variable to store month

    def __init__(self, initial_bal = Decimal("0.0")):
        """ constructor initializes bank balance """
        try:
            self.balance = Decimal(initial_bal)
        except:
            print("Initial balance should be a decimal.")

    def withdraw(self, amt):
        """ withdraws a valid set amount of money """
        try:
            amt = Decimal(amt)
        except:
            print("Amount should be a decimal.")
            return False

        # balance will go into negative -- overcharge
        if (self.balance - amt < 0):
            self.balance -= BankAccount.OVERCHARGE_FEE
        else:
            # normal withdrawal
            self.balance -= amt
        return True
    
    def deposit(self, amt):
        """ deposits a valid set amount of money """
        try:
            amt = Decimal(amt)
        except:
            print("Amount should be a decimal.")
            return False

        # append amount to balance
        self.balance += amt
        return False

    def get_balance(self):
        """ returns current balance to client """
        if (self.balance < 0):
            # special formatting needed
            return "-$" + str(abs(self.balance.quantize(BankAccount.ROUND)))
        else:
            return "$" + str(self.balance.quantize(BankAccount.ROUND))
            
    def add_interest(self, rate):
        """ adds interest provided certain conditions are met """
        if (not BankAccount.MIN_RATE <= rate <= BankAccount.MAX_RATE):
            print("The interest rate should be from 1% to 2% (inclusive).")
            return
        # validity check
        elif (self.balance < 0):
            print("Your balance needs to be positive to accumulate interest.")
            return
        else:
            # they are the same -- interest is done for this month
            if (not BankAccount.intr_month - datetime.now().month):
                print("Interest has already been applied this month;" +
                      " try again next month.")
            # not done -- add some interest given valid rate
            else:
                self.balance = self.balance * (Decimal(1) +
                                               Decimal(rate / 100))
                BankAccount.intr_month = datetime.now().month

    
