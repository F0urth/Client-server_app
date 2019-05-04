package ClientSide.Communication;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author F0urth
 */

public
    class CommunicationHandler {

    private SocketChannel channel;
    private Selector selector;

    public static CommunicationHandler getInstance(SocketChannel channel) throws IOException {
        return new CommunicationHandler(channel);
    }

    private CommunicationHandler(SocketChannel sc) throws IOException {
        this.channel = sc;
        this.channel.configureBlocking(false);
        this.channel.register(
            this.selector = Selector.open(), SelectionKey.OP_ACCEPT);
    }

    public void run() {
        Executors.newSingleThreadScheduledExecutor()
            .schedule(
                this::oneIteration,1000, TimeUnit.MILLISECONDS);
    }

    private void oneIteration() {
        try {
            this.selector.selectNow();
            Set<SelectionKey> keys = this.selector.selectedKeys();

            for (SelectionKey key : keys) {
                if (!key.isValid()) continue;
                if (key.isReadable()) read(key);
                if (key.isWritable()) write(key);
                if (key.isAcceptable()) accept(key);
                if (key.isConnectable()) connect(key);

            }
            this.selector.selectedKeys().clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO
    private void connect(SelectionKey key) {

    }

    //TODO
    private void accept(SelectionKey key) {

    }

    //TODO
    private void write(SelectionKey key) {

    }

    //TODO
    private void read(SelectionKey key) {
        var buffer = ByteBuffer
            .allocate(1024);
        try {
            this.channel.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}