import socket, sys                                       

HOST_NAME = socket.gethostname()    # hostname                          
PORT_NUM = 51104                    # port number for config
BYTES_PER_KB = 1024                 # number of bytes in packet

class Server:
    def __init__(self):
        """ create server level socket """
        self.socket = socket.socket(socket.AF_INET, 
                                    socket.SOCK_STREAM)

    def setup(self, bytes_per_kb = BYTES_PER_KB):
        """ setup the packet size """
        self.socket.setsockopt(socket.SOL_SOCKET,
                               socket.SO_SNDBUF, 
                               bytes_per_kb)
    def bind_local(self, host=HOST_NAME, port=PORT_NUM):
        """ bind to a hostname (only local) """
        assert host == socket.gethostname()
        try:
            self.socket.bind((host, port))
        except:
            raise ValueError("incorrect host/port config")

    def bind(self, host, port):
        try:
            self.socket.bind((host, port))
        except:
            raise ValueError("incorrect port config")

    def send(self, msg, encoding="ascii", verbose=False):
        """ send message until success to client, send address to main """
        while (True):
            client_socket, details = server_socket.accept()
            client_socket.send(msg.encode(encrypt(encoding)))
            break
        client_socket.close()
        # return details about client
        return details if verbose else None

def encrypt(msg, forward=True):
    n = 0
    msg = msg.lower()
    def num():
        nonlocal n
        n += 1
        return n
    def shifttext():
        strs = 'abcdefghijklmnopqrstuvwxyz' 
        data = []
        for i in inp:                     
            if i.strip() and i in strs:    
                data.append(strs[(strs.index(i) + num()) % 26])    
            else:
                data.append(i)          
        output = ''.join(data)
        return output 
    return shifttext()

