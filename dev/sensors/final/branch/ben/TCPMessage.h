#ifndef TCP_MESSAGE_H
#define TCP_MESSAGE_H
typedef nx_struct TCPMessage {
    nx_uint8_t ACK;
    nx_uint8_t fromAddress;
    nx_uint8_t pay_load[50];
} TCPMessage;

enum {
    AM_TCPMESSAGE  = 7,
    TCPHeaderSize  = 2,
    TCPPayloadSize = 50
};

#endif
