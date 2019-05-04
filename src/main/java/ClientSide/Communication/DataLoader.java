package ClientSide.Communication;


import ServerSide.Databases.MergedData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class DataLoader {

    private Socket socket = null;

    DataLoader(Integer integer) {
        try {
            this.socket = new Socket(InetAddress.getLocalHost(), integer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadData() {
        try {
            var input = new ObjectInputStream(this.socket.getInputStream());
            var data = new ArrayList<MergedData>();
            while (input.readInt() != -1) {
                data.add((MergedData) input.readObject());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
