package ServerSide;


import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;

/**
 * @author F0urth
 */

public class ServerMain {

    private static class FrameConsts {
        private static final Integer FRAME_WIDTH = 500;
        private static final Integer FRAME_HEIGHT = 300;
    }

    public static void main(String[] args) {
        var server = Server.INSTANCE;
        Executors.newSingleThreadExecutor().execute(
            () -> {
                var frame = new JFrame();
                frame.setSize(new Dimension(FrameConsts.FRAME_WIDTH, FrameConsts.FRAME_HEIGHT));
                var button = new JButton("RedExit");
                button.setBackground(Color.RED);
                button.addActionListener(
                    l -> System.exit(0));
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                frame.add(button);
                frame.setVisible(true);
                frame.setTitle("###RedExit###");
            }
        );
    }
}
