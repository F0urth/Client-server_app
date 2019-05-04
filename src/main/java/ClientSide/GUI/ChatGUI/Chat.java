package ClientSide.GUI.ChatGUI;

import javax.swing.*;
import java.awt.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

/**
 * @author F0urth
 * trzeba powalczyć żeby działało
 * dobra poddaje się
 * @deprecated 
 */

public
    class Chat
        extends JFrame {

    private JTextArea chat;
    private JButton send;
    private JTextArea fromUser;
    private JPanel root;
    private JPanel chatPanel;
    private JPanel userPanel;
    private JLabel massageLabel;
    private JLabel label;
    private JScrollPane fromUserScrollPane;
    private JScrollPane chatScrollPane;

    private Queue<String> userMassage;

    public static Chat getInstance() {
        return new Chat();
    }

    private Chat() {
        this.userMassage = new ConcurrentLinkedQueue<>();
        this.chat = new JTextArea();
        this.chat.setEnabled(false);
        this.send = new JButton("Send");
        this.send.addActionListener(
            l -> {
                String val = this.fromUser.getText();
                if (!val.equals("")) {
                    this.userMassage.add(val);
                    this.fromUser.setText("");
                }

            });
        this.fromUser = new JTextArea();
        this.root = new JPanel();
        this.massageLabel = new JLabel("Type Massage: ");
        this.label = new JLabel("Massage");
        this.fromUserScrollPane = new JScrollPane();
        this.chatScrollPane = new JScrollPane();

        this.chatPanel = new JPanel();
        this.userPanel = new JPanel();

        //***Configure gui***

        this.chatScrollPane.setSize(new Dimension(500, 400));
        this.chatScrollPane.add(this.chat);
        this.chatPanel.setSize(new Dimension(500,400));

        this.chatPanel.add(chatScrollPane, BorderLayout.CENTER);
        this.chatPanel.add(label, BorderLayout.NORTH);


        this.userPanel.setSize(
            new Dimension(300,400));

        this.fromUserScrollPane.setSize(
            new Dimension(300,400));

        //this.fromUserScrollPane.add(this.fromUser);

        this.userPanel.add(massageLabel, BorderLayout.NORTH);
        this.userPanel.add(fromUser, BorderLayout.CENTER);

        this.root.setSize(
            new Dimension(1024, 768));
        this.root.add(chatPanel, BorderLayout.CENTER);
        this.root.add(userPanel, BorderLayout.SOUTH);
        this.root.add(send, BorderLayout.EAST);
    }

    public void addMassage(String inMassage) {
        this.chat.append("\n"+inMassage);
    }

    public String getFromQueue() {
        return userMassage.poll();
    }

    public void run() {
        Executors.newSingleThreadExecutor().execute(
            () -> {
                this.setSize(
                    new Dimension(1024,768));
                JRootPane jRootPane = new JRootPane();
                jRootPane.setContentPane(root);
                this.setRootPane(jRootPane);
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setResizable(false);
                this.setVisible(true);
                this.requestFocus();
                //this.pack();
            }
        );
    }
}
