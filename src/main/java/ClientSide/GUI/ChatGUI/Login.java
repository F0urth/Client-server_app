package ClientSide.GUI.ChatGUI;

import ClientSide.Controller;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

/**
 * @author F0urth
 */

public
class Login
    extends JFrame {

    private JPanel rootPanel;
    private JButton sendNickButton;
    private JTextArea userNick;
    private JLabel nickLabel;


    public static Login getInstance() {
        return new Login();
    }

    private Login() {
        this.setSize(300, 200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Login");
        this.setVisible(true);
        this.add(rootPanel);

        sendNickButton.addActionListener(
            l -> {
                String nick = userNick.getText();
                this.dispose();
                Controller.INSTANCE
                    .addToMassage("<Nick>" + nick);
                Controller.INSTANCE
                    .setChat(new ChatGUI(nick));
            });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(2, 2, new Insets(10, 10, 10, 10), -1, -1));
        sendNickButton = new JButton();
        sendNickButton.setText("Send Nick");
        rootPanel.add(sendNickButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        userNick = new JTextArea();
        rootPanel.add(userNick, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 50), null, 0, false));
        nickLabel = new JLabel();
        nickLabel.setText("Nick: ");
        rootPanel.add(nickLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

}
