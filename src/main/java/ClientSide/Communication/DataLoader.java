package ClientSide.Communication;


import ClientSide.Controler;
import ServerSide.Databases.MergedData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.stream.Collectors;

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
                System.out.println("ReadedObject XDDDD");
            }
            var mappedData = data.stream().collect(
                Collectors.groupingBy(MergedData::getNazwa));
            Controler.INSTANCE.setData(mappedData);
            Controler.INSTANCE.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
