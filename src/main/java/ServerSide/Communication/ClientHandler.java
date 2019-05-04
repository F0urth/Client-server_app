package ServerSide.Communication;

import ServerSide.Server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

class ClientHandler {


    private SocketChannel channel;
    private SelectionKey readKey;
    private String nick;

    ClientHandler(SelectionKey readKey, SocketChannel channel) {
        this.readKey = readKey;
        this.channel = channel;
    }

    void read() throws IOException {
        var buffer = ByteBuffer.allocate(1024);
        int readed = channel.read(buffer.clear());
        if (readed == -1) disconect();
        if (readed < 1) return;
        buffer.flip();
        var massage = new String(buffer.array()).trim();
        if (massage.equals(">@!GetData")) {
            System.out.println("Asked for DATA");
            this.channel.write(
                ByteBuffer.wrap(String.valueOf(4444).getBytes()));
            delegateProtocol();
            return;
        }
        if (massage.startsWith("<Nick>")) this.nick = massage.replaceAll("<Nick>", "");
        else {
            var targets = new HashMap<>(ServerCommunicationHandler.clientMap);
            targets.remove(this.readKey);
            for (var target : targets.entrySet()) {
                target.getValue().channel.write(
                    ByteBuffer.wrap((this.nick + massage).getBytes()));
            }
        }
    }


    private void delegateProtocol() {
        Server.INSTANCE.sendDataQueueLike();
    }

    private void disconect() throws IOException {
        ServerCommunicationHandler.clientMap.remove(this.readKey);
        if (this.readKey != null) this.readKey.cancel();
        if (this.channel == null) return;
        System.out.println("Disconnected " + this.channel.getRemoteAddress());
        this.channel.close();

    }
}