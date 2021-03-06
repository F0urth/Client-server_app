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
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author F0urth
 */

public class FrontGUI extends JFrame {

    private CenterChart centerChart;
    private List<ChartData> data;
    private JMenuBar menuBar;
    private JMenu menu, submenu;
    private JMenuItem chat;
    private ActionListener listener;

    private static class Consts {
        private static final Integer CENTER_CHART_WIDTH = 500;
        private static final Integer CENTER_CHART_HEIGHT = 300;
        private static final Integer FRAME_WIDTH = 1024;
        private static final Integer FRAME_HEIGHT = 768;
    }


    public static FrontGUI getInstance(List<ChartData> data) {
        return new FrontGUI(data);
    }

    private FrontGUI(List<ChartData> data) {
        this.centerChart = CenterChart.getInstance(Consts.CENTER_CHART_WIDTH, Consts.CENTER_CHART_HEIGHT, data);
        this.data = data;
    }

    public void run() {
        Executors.newSingleThreadExecutor()
            .execute(() -> {
                this.setSize(
                    new Dimension(Consts.FRAME_WIDTH, Consts.FRAME_HEIGHT));
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
                        l -> SingleChart.getInstance(StatisticedChartData.getInstance(chartData)).createChart());
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
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.add(chartPanel, BorderLayout.CENTER);
                this.setVisible(true);
            });
    }

    public void changeActionListener() {
        this.chat.removeActionListener(this.listener);
        this.listener = l -> Controller.INSTANCE.getChat().setVisible(true);
        this.chat.addActionListener(this.listener);

    }
}
