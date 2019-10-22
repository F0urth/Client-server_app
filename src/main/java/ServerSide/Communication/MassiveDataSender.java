package ServerSide.Communication;

import Absolute.MergedData;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author F0urth
 */

public enum MassiveDataSender {

    INSTANCE;

    private ExecutorService threads;
    private ServerSocket serverSocket;
    private List<MergedData> allData;

    public static class MassiveDataSenderConst {
        public static final Integer PORT = 4444;
        private static final Integer NUMBER_OF_THREADS = 4;
    }

    MassiveDataSender() {
        try {
            this.serverSocket = new ServerSocket(MassiveDataSenderConst.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.threads = Executors.newFixedThreadPool(MassiveDataSenderConst.NUMBER_OF_THREADS);
    }

    public void setAllData(List<MergedData> data) {
        this.allData = data;
    }

    public void run() {
        threads.execute(
            () -> {
                try {
                    var socket = serverSocket.accept();
                    System.out.println("Client gonna get Data");
                    new Client(socket).send(this.allData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }

    private static class Client {

        private Socket socket;
        private ObjectOutputStream out;

        private static final Integer GONNA_CONTINUE_NUMBER = 1;
        private static final Integer END_OF_SENDING = -1;

        private Client(Socket socket) throws IOException {
            this.socket = socket;
            out = new ObjectOutputStream(this.socket.getOutputStream());
        }

        private void send(List<MergedData> data) {
            try {
                for (var one : data) {
                    out.writeInt(GONNA_CONTINUE_NUMBER);
                    out.writeObject(one);
                    out.flush();
                }
                out.writeInt(END_OF_SENDING);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
