################################################
# CS 21B Intermediate Python Programming Lab #3
#
# Description: Client/driver for the bank account
#              project.
# User Input: None
# Output: Bank Balances
# Version: 3.6.5
# Development Environment:  Windows 10
# Developer:  Anirudhan Badrinath
# Date:  10/16/18
################################################

from anirudhanBadrinathBank import BankAccount
from decimal import *

def print_bal(bank_acct, descr):
   print("BANK ACCOUNT BALANCE after", descr, ":", bank_acct.get_balance())

################ MAIN ##########################

bank_account = BankAccount(Decimal("1000.00"))
print_bal(bank_account, "setting initial balance to $1000")

bank_account.deposit(Decimal("500.00"))
print_bal(bank_account, "depositing $500")

bank_account.withdraw(Decimal("2000.00"))
print_bal(bank_account, "withdrawing $2000")

bank_account.add_interest(Decimal("1.00"))
print_bal(bank_account, "adding 1% interest")

bank_account.add_interest(Decimal("2.00"))
print_bal(bank_account, "adding 2% interest")

bank_account.deposit(Decimal("125000.99"))
print_bal(bank_account, "depositing $125,000.99")

bank_account.withdraw(Decimal("0.99"))
print_bal(bank_account, "withdrawing $0.99")

bank_account.withdraw(Decimal("126500.00"))
print_bal(bank_account, "withdrawing $126,500")

bank_account.withdraw(Decimal("10.00"))
print_bal(bank_account, "withdrawing $10")

bank_account.add_interest(Decimal("1.00"))
print_bal(bank_account, "adding 1% interest")

################ RUN ###########################

"""
BANK ACCOUNT BALANCE after setting initial balance to $1000 : $1000.00
BANK ACCOUNT BALANCE after depositing $500 : $1500.00
BANK ACCOUNT BALANCE after withdrawing $2000 : $1490.00
BANK ACCOUNT BALANCE after adding 1% interest : $1504.90
Interest has already been applied this month; try again next month.
BANK ACCOUNT BALANCE after adding 2% interest : $1504.90
BANK ACCOUNT BALANCE after depositing $125,000.99 : $126505.89
BANK ACCOUNT BALANCE after withdrawing $0.99 : $126504.90
BANK ACCOUNT BALANCE after withdrawing $126,500 : $4.90
BANK ACCOUNT BALANCE after withdrawing $10 : -$5.10
Your balance needs to be positive to accumulate interest.
BANK ACCOUNT BALANCE after adding 1% interest : -$5.10
"""
