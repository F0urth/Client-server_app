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

public
    enum MassiveDataSender {

    INSTANCE;

    private ExecutorService threads;
    private ServerSocket serverSocket;
    private List<MergedData> allData;

    {
        try {
            this.serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.threads = Executors.newFixedThreadPool(4);
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
                    new Client(socket)
                        .send(this.allData);
                } catch (IOException e) {e.printStackTrace();}
            });
    }

    private class Client {

        private Socket socket;
        private ObjectOutputStream out;

        private Client(Socket socket) throws IOException {
            this.socket = socket;
            out = new ObjectOutputStream(this.socket.getOutputStream());
        }

        private void send(List<MergedData> data) {
            try {
                for (var one : data) {
                    out.writeInt(1);
                    out.writeObject(one);
                    out.flush();
                }
                out.writeInt(-1);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
