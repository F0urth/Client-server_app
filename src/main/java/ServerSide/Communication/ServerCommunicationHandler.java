package ServerSide.Communication;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author F0urth
 */

public
    class ServerCommunicationHandler {

    private ServerSocketChannel socketChannel;
    private Selector selector;
    private ExecutorService thread;

    static Map<SelectionKey, ClientHandler> clientMap;

    static {
        clientMap = new HashMap<>();
    }

    public static ServerCommunicationHandler getInstance(ServerSocketChannel socketChannel, Selector selector) {
        return new ServerCommunicationHandler(socketChannel, selector);
    }

    {
        this.thread = Executors.newSingleThreadExecutor();
    }

    private ServerCommunicationHandler(ServerSocketChannel socketChannel, Selector selector) {
        this.socketChannel = socketChannel;
        this.selector = selector;
    }


    public void run() {
        this.thread.execute(
            this::run2
        );
    }

    private void oneIteration() {
        try {
            this.selector.selectNow();
            var keys = this.selector.selectedKeys();
            for (var key: keys) {
                if (!key.isValid()) continue;
                if (key.isAcceptable()) {
                    var channel = this.socketChannel.accept();
                    if (channel == null) continue;
                    channel.configureBlocking(false);
                    var readKey = channel.register(this.selector, SelectionKey.OP_READ);
                    clientMap.put(readKey, new ClientHandler(readKey, channel));
//                    JOptionPane.showMessageDialog(new JFrame(), "new Client Addded",
//                        "Yupi", JOptionPane.INFORMATION_MESSAGE);
                }
                if (key.isReadable()) clientMap.get(key).read();
            }
            this.selector.selectedKeys().clear();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void run2() {
        while (true) oneIteration();
    }
}
