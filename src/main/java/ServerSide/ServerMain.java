package ServerSide;


import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;

public
    class ServerMain {

    public static void main(String[] args){
        var server = Server.INSTANCE;

        Executors.newSingleThreadExecutor().execute(
            () -> {
                var frame = new JFrame();
                frame.setSize(new Dimension(500,300));
                var button = new JButton("RedExit");
                button.setBackground(Color.RED);
                button.addActionListener(
                    l -> {
                        System.exit(0);
                    }
                );
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                frame.add(button);
                frame.setVisible(true);
                frame.setTitle("###RedExit###");
            }
        );
    }
}
