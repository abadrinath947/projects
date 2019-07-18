###############################################################################
# CS 21 B Intermediate Programming(Python)
# Description:  Basic networking server that sends an ASCII message
# Input/Output: shows client info
# Version: Python 3.6.5 
# Development Environment: Windows 10
# Developer: Anirudhan Badrinath
# Date: 11/20/18
###############################################################################

import socket, sys                                       

HOST_NAME = socket.gethostname()    # hostname                          
PORT_NUM = 51104                    # port number for config
BYTES_PER_KB = 1024                 # number of bytes in packet

######################## FUNCTION DEFINITIONS #################################

def create_socket():
    """ create server level socket """
    return socket.socket(socket.AF_INET, socket.SOCK_STREAM)

def setup_server(server_socket):
    """ setup the packet size """
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_SNDBUF, BYTES_PER_KB)

def send_message(server_socket):
    """ send message until success to client, send address to main """
    while (True):
        client_socket, details = server_socket.accept()
        # message sent to client
        message = "Demo Message - CLIENT: Anirudhan Badrinath".encode("ascii")
        client_socket.send(message)
        # make sure to close client socket
        client_socket.close()
        break
    # return details about client
    return details

###################################### MAIN ###################################

# create a server socket object
server_socket = create_socket()

# setup server socket using packet size
setup_server(server_socket)

# whether binding fails or not, catch error                                  
try:
    # bind to the local host and port
    server_socket.bind((HOST_NAME, PORT_NUM))         
except socket.error:
    print("Failed to bind to socket. Check whether port is open.")                         
    sys.exit(1)

# listen 5 times on backlog
server_socket.listen(5)

print("Connected by: " + str(send_message(server_socket)))

server_socket.close()

###################################### RUN ####################################

"""
Connected by: ('192.168.1.68', 50340)
"""
