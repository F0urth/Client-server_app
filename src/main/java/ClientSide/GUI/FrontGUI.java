package ClientSide.GUI;

import ClientSide.DataConteners.ChartData;
import ClientSide.DataConteners.StatisticedChartData;
import ClientSide.GUI.Charts.CenterChart;
import ClientSide.GUI.Charts.SingleChart;
import ClientSide.GUI.ChatGUI.Login;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
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
                    this.setResizable(false);
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

                    JMenuItem chat = new JMenuItem("Chat");
                    chat.addActionListener(
                        l -> Login.getInstance());

                    //***Add Chart to the center***
                    XChartPanel<XYChart> chartPanel = new XChartPanel<>(
                        this.centerChart.getChart());
                    this.add(chartPanel, BorderLayout.CENTER);
                    this.setVisible(true);
                });
    }
}
