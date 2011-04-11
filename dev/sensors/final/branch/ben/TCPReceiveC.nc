component TCPReceiveC {
    provides interface Receive;

    uses interface AMSend;
    uses interface Boot;
    uses interface SplitControl as AMControl;
    uses interface AMPacket;
 
} implementation {
    
}
