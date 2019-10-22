package ServerSide.Communication;

import ServerSide.Server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

/**
 * @author F0urth
 */

class ClientHandler {

    private SocketChannel channel;
    private SelectionKey readKey;
    private String nick;

    private static class ReadConsts {
        private static final Integer END_OF_COMMUNICATION = -1;
        private static final Integer MINIMUM_MESSAGE_SIZE = 1;
        private static final Integer BUFFER_SIZE = 1024;
    }

    ClientHandler(SelectionKey readKey, SocketChannel channel) {
        this.readKey = readKey;
        this.channel = channel;
    }

    void read() throws IOException {
        var buffer = ByteBuffer.allocate(ReadConsts.BUFFER_SIZE);
        int readed = channel.read(buffer.clear());
        if (readed == ReadConsts.END_OF_COMMUNICATION) {
            disconect();
        }
        if (readed < ReadConsts.MINIMUM_MESSAGE_SIZE) {
            return;
        }
        buffer.flip();
        var massage = new String(buffer.array()).trim();
        if (massage.equals(">@!GetData")) {
            System.out.println("Asked for DATA");
            this.channel.write(
                ByteBuffer.wrap(String.valueOf(MassiveDataSender.MassiveDataSenderConst.PORT).getBytes()));
            delegateProtocol();
            return;
        }
        if (massage.startsWith("<Nick>")) {
            this.nick = massage.replaceAll("<Nick>", "");
        } else {
            var targets = new HashMap<>(ServerCommunicationHandler.clientMap);
            targets.remove(this.readKey);
            for (var target : targets.entrySet()) {
                target.getValue().channel.write(ByteBuffer.wrap((this.nick + " => " + massage).getBytes()));
            }
        }
    }

    private void delegateProtocol() {
        Server.INSTANCE.sendDataQueueLike();
    }

    private void disconect() throws IOException {
        ServerCommunicationHandler.clientMap.remove(this.readKey);
        if (this.readKey != null) {
            this.readKey.cancel();
        }
        if (this.channel == null) {
            return;
        }
        System.out.println("Disconnected " + this.channel.getRemoteAddress());
        this.channel.close();

    }
}