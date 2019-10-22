package ClientSide.Communication;

import ClientSide.Controller;

import javax.swing.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

/**
 * @author F0urth
 */

public class CommunicationHandler {

    private SocketChannel channel;
    private Queue<String> outQueue;
    private ByteBuffer buffer;

    private static class ReadConsts {
        private static final Integer END_OF_COMMUNICATION = -1;
        private static final Integer MINIMUM_MESSAGE = 1;
        private static final Integer BUFFER_SIZE = 1024;
    }

    public static CommunicationHandler getInstance(SocketChannel channel) throws IOException {
        return new CommunicationHandler(channel);
    }

    private CommunicationHandler(SocketChannel sc) throws IOException {
        this.channel = sc;
        this.channel.configureBlocking(false);
        this.outQueue = new ConcurrentLinkedQueue<>();
        this.buffer = ByteBuffer.allocate(ReadConsts.BUFFER_SIZE);
    }

    public void run() {
        Executors.newSingleThreadExecutor()
            .execute(this::runner);
    }

    private void oneIteration() {
        if (!outQueue.isEmpty()) {
            try {
                this.channel.write(ByteBuffer.wrap(outQueue.poll().getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        read();
    }


    private void read() {
        try {
            int amo_read = channel.read(buffer.clear());
            if (amo_read == ReadConsts.END_OF_COMMUNICATION) {
                disconnect();
            }
            if (amo_read < ReadConsts.MINIMUM_MESSAGE) {
                return;
            }
            buffer.flip();
            var massage = new String(buffer.array()).trim();
            if (massage.matches("[0-9]+")) {
                new DataLoader(Integer.valueOf(massage)).loadData();
            } else {
                Controller.INSTANCE.appendToChatMassages(massage);
            }

        } catch (IOException ex) {
            disconnect();
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(),
                "Exception", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void addToOutQueue(String massage) {
        this.outQueue.add(massage);
    }


    private void disconnect() {
        try {
            this.channel.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(),
                "Exception", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void runner() {
        while (true) {
            oneIteration();
        }
    }
}