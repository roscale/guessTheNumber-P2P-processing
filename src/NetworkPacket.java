import java.util.ArrayList;

/**
 * Created by roscale on 2/27/17.
 */
public interface NetworkPacket {
    String encode();

    static String[] separate(String rawPackets)
    {
        return rawPackets.split("\n");
    }
}
