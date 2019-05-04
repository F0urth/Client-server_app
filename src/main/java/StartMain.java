import ClientSide.ClientMain;
import ServerSide.ServerMain;

import java.io.IOException;

public
    class StartMain {

    public static void main(String[] args) throws IOException {
        ServerMain.main(args);

        ClientMain.main(args);
        ClientMain.main(args);
    }

}
