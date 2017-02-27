import processing.core.PApplet;
import processing.net.Client;
import processing.net.Server;

/**
 * Created by roscale on 2/27/17.
 */
public class Network {

    Server s;
    Client c;

    ConnectionType connectionType = ConnectionType.UNDEFINED;

    // Create Server
    Network(PApplet p, int port)
    {
        s = new Server(p, port);
        connectionType = ConnectionType.SERVER;
    }

    Network(PApplet p, String ip, int port)
    {
        c = new Client(p, ip, port);
        connectionType = ConnectionType.CLIENT;
    }

    String receive()
    {
        if (connectionType == ConnectionType.SERVER)
        {
            Client availableClient = s.available();

            if (availableClient != null)
            {
                String rawPackets = availableClient.readString();
                if (rawPackets != null)
                    return rawPackets;
            }
        }

        else if (connectionType == ConnectionType.CLIENT)
            if (c.available() > 0)
            {
                String rawPackets = c.readString();
                if (rawPackets != null)
                    return rawPackets;
            }

        return null;
    }

    void send(NetworkPacket packet)
    {
        String data = packet.encode() + "\n";

        if (connectionType == ConnectionType.SERVER)
            s.write(data);
        else if (connectionType == ConnectionType.CLIENT)
            c.write(data);
    }

}
