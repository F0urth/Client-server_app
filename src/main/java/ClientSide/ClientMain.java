package ClientSide;

import ClientSide.Communication.CommunicationHandler;
import ClientSide.GUI.FrontGUI;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Collections;

/**
 * @author F0urth
 */

public
    class ClientMain {

    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(
            new InetSocketAddress("localhost", 1337));


        var commu = CommunicationHandler.getInstance(channel);
        commu.run();
        commu.addToOutQueue(">@!GetData");
    }
}
