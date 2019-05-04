package ClientSide.GUI;

import ClientSide.Controller;
import ClientSide.DataConteners.ChartData;
import ClientSide.DataConteners.StatisticedChartData;
import ClientSide.GUI.Charts.CenterChart;
import ClientSide.GUI.Charts.SingleChart;
import ClientSide.GUI.ChatGUI.Login;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.*;
import java.util.concurrent.Executors;

/**
 * @author F0urth
 */

public
    class FrontGUI
        extends JFrame {

    private CenterChart centerChart;
    private List<ChartData> data;
    private JMenuBar menuBar;
    private JMenu menu, submenu;
    private JMenuItem chat;
    private ActionListener listener;
    public static FrontGUI getInstance(List<ChartData> data) {
        return new FrontGUI(data);
    }

    private FrontGUI(List<ChartData> data) {
        this.centerChart = CenterChart.getInstance(500, 300, data);
        this.data = data;
    }

    public void run() {
        Executors.newSingleThreadExecutor()
            .execute(
                () -> {
                    this.setSize(
                        new Dimension(1024,768));
                    this.setDefaultCloseOperation(
                        JFrame.EXIT_ON_CLOSE);
                    this.setTitle("ChartGUI");

                    //***MenusSetup***
                    this.menuBar = new JMenuBar();
                    this.menu = new JMenu("Menu");
                    this.submenu = new JMenu("Chart of");
                    this.menu.add(submenu);
                    this.menuBar.add(menu);

                    //***Add JMenuItems***

                    this.menu.addSeparator();
                    for (ChartData chartData : this.data) {
                        JMenuItem jmi = new JMenuItem(chartData.getTitle());
                        jmi.addActionListener(
                            l -> SingleChart.getInstance(StatisticedChartData.getInstance(chartData)).createChart()
                        );
                        submenu.add(jmi);
                    }

                    this.chat = new JMenuItem("Chat");
                    chat.addActionListener(
                        (this.listener = l -> Login.getInstance())
                    );

                    menu.add(chat);
                    //***Add Chart to the center***
                    XChartPanel<XYChart> chartPanel = new XChartPanel<>(
                        this.centerChart.getChart());
                    this.setJMenuBar(this.menuBar);
                    this.add(chartPanel, BorderLayout.CENTER);
                    this.setVisible(true);
                });
    }

    public void changeActionListener() {
        this.chat.removeActionListener(this.listener);
        this.listener = l -> Controller.INSTANCE.getChat();
        this.chat.addActionListener(this.listener);
    }
}
