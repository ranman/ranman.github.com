#ifndef TCP_MESSAGE_H
#define TCP_MESSAGE_H

#define TCP_NOACK 20

typedef nx_struct TCPMessage {
    nx_uint16_t ACK;
    nx_uint16_t fromAddress;
    nx_uint16_t payloadLength;
    nx_uint8_t payload[50];
} TCPMessage;

enum {
    AM_TCPMESSAGE  = 7,
    TCPHeaderSize  = 4,
    TCPPayloadSize = 50,
};

#endif
