package ClientSide.Communication;

import javax.management.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author F0urth
 */

public
    class CommunicationHandler {

    private SocketChannel channel;
    private Queue<String> inQueue, outQueue;
    private ByteBuffer buffer;
    public static CommunicationHandler getInstance(SocketChannel channel) throws IOException {
        return new CommunicationHandler(channel);
    }

    private CommunicationHandler(SocketChannel sc) throws IOException {
        this.channel = sc;
        this.channel.configureBlocking(false);
        this.inQueue = new ConcurrentLinkedQueue<>();
        this.outQueue= new ConcurrentLinkedQueue<>();
        this.buffer = ByteBuffer.allocate(1024);
    }

    public void run() {
        Executors.newSingleThreadScheduledExecutor()
            .schedule(
                this::oneIteration,
                1000, TimeUnit.MILLISECONDS);
    }

    private void oneIteration() {

    }

    private void read() {
        try {
            int amo_read = -1;
            try {
                amo_read = channel.read(
                    buffer.clear());
            } catch (Exception ex){
                System.out.println(ex);
            }
            if (amo_read == -1) disconnect();
            if (amo_read < 1) return;
            buffer.flip();
            var massage = new String(buffer.array()).trim();
            if (massage.matches("[0-9]+")){
                new DataLoader(Integer.valueOf(massage)).loadData();
            }

        } catch (Exception ex) {
            disconnect();
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(),
                "Exception", JOptionPane.WARNING_MESSAGE);
        }
    }



    private void disconnect() {
        try {
            this.channel.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(),
                "Exception", JOptionPane.WARNING_MESSAGE);
        }
    }
}