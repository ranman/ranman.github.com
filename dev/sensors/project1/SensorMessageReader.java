import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;
/**
 * Simple message reader
 */
public class SensorMessageReader implements MessageListener {
    private MoteIF moteif = new MoteIF();
    private int time;
    private int lastTime;
    private int timeCurrent;
    private int time_num;

    public SensorMessageReader() {
        moteif.registerListener(new SensorMessage(), this);
    }
    public void messageReceived(int toAddr, Message msg) {
        if (msg instanceof SensorMessage) {
            SensorMessage sm = (SensorMessage) msg;
            System.out.println("Time = "  + sm.get_time());
        }
    }

    public static void main(String[] args) {
        SensorMessageReader reader = new SensorMessageReader();
    }
}
