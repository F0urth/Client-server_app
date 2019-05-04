package ServerSide;

import ServerSide.Communication.MassiveDataSender;
import ServerSide.Communication.ServerCommunicationHandler;
import ServerSide.Databases.Database;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public
    enum Server {
    INSTANCE;

    private MassiveDataSender dataSender;
    private Database database;
    private ServerCommunicationHandler communicationHandler;


    {
        try {
            var serverChannel = ServerSocketChannel.open();
            var addres = new InetSocketAddress("localhost", 1337);
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
        this.dataSender.setAllData(
            database.getMergetData());
        this.communicationHandler = ServerCommunicationHandler
            .getInstance(serverSocketChannel, selector);
        this.communicationHandler.run();
    }

    public void sendDataQueueLike() {
        this.dataSender.run();
    }
}
