package ServerSide;

import ServerSide.Communication.MassiveDataSender;
import ServerSide.Communication.ServerCommunicationHandler;
import ServerSide.Databases.Database;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @author F0urth
 */

public enum Server {

    INSTANCE;

    private final Integer PORT_NUMBER = 1337;

    private MassiveDataSender dataSender;
    private Database database;
    private ServerCommunicationHandler communicationHandler;

    Server() {
        try {
            var serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            var addres = new InetSocketAddress("localhost", PORT_NUMBER);
            var selector = Selector.open();
            serverChannel.bind(addres);
            serverChannel.register(
                selector, SelectionKey.OP_ACCEPT);
            setup(serverChannel, selector);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setup(ServerSocketChannel serverSocketChannel, Selector selector) {
        this.database = Database.getInstance();
        this.dataSender = MassiveDataSender.INSTANCE;
        this.dataSender.setAllData(database.getMergetData());
        this.communicationHandler = ServerCommunicationHandler
            .getInstance(serverSocketChannel, selector);
        this.communicationHandler.run();
    }

    public void sendDataQueueLike() {
        this.dataSender.run();
    }
}
