package ClientSide.Communication;


import ClientSide.Controller;
import Absolute.MergedData;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author F0urth
 */

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
                System.out.println("ReadedObject From server");
            }
            System.out.println("geted data");
            var mappedData = data.stream().collect(
                Collectors.groupingBy(MergedData::getNazwa));
            Controller.INSTANCE.setData(mappedData);
            Controller.INSTANCE.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
