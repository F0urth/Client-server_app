package ClientSide;

import ClientSide.Communication.CommunicationHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author F0urth
 */

public class ClientMain {

    private static final Integer PORT = 1337;

    public static void main(String[] args) throws IOException {
        var channel = SocketChannel.open();
        channel.connect(
            new InetSocketAddress("localhost", PORT));
        var commu = CommunicationHandler.getInstance(channel);
        commu.run();
        commu.addToOutQueue(">@!GetData");
        Controller.INSTANCE.setCommunicationHandler(commu);
    }
}
