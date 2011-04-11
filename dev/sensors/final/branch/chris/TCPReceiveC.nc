component TCPReceiveC {
    provides interface Receive;

    uses interface Receive;
    uses interface AMSend;
    uses interface Boot;
    uses interface SplitControl as AMControl;
    uses interface AMPacket;
 
} implementation {
    
    //
    // This implements things to be done when we receive a message.
    //
    // @param  bufPtr  The message buffer handed to us by TinyOS
    // @param  payload The message content
    // @param  len     The size of the message
    // @return         The buffer we give to the OS to use.
    //
    event message_t* Receive.receive(message_t* bufPtr, void* payload,
                                                                uint8_t len) {
        TCPMessage *msg = (TCPMessage *) payload;
        TCPMessage *ackLoad = call Packet.getPayload(&TCPMsg, sizeof(TCPMessage));

        ackLoad->fromAddress = call AMPacket.address();
        
        // if this is a message being acked...
        if (msg->ACK) {
            call Leds.led2Toggle();
            sendLocked = FALSE;
            signal AMSend.sendDone(message, SUCCESS);  // Unsure about this ...
            return bufPtr;
        } else {
            ackLoad->ACK = 1;

            if (call Send.send(msg->fromAddress, 
                                    &TCPMsg, sizeof(TCPMessage)) == SUCCESS) {
                call Leds.led2Toggle();
            }
            return bufPtr;
        }
    }
}
