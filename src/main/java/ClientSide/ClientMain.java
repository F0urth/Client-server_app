package ClientSide;

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
        channel.bind(
            new InetSocketAddress("localhost", 1337));



        FrontGUI.getInstance(
            Collections.EMPTY_LIST)
            .run();
    }
}
