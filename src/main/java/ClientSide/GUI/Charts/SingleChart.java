package ClientSide.GUI.Charts;

import ClientSide.DataConteners.StatisticedChartData;
import ClientSide.DataConteners.StatisticedData;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;

/**
 * @author F0urth
 */

public
    class SingleChart {

    private StatisticedChartData data;
    private XYChart chart;


    public static SingleChart getInstance(StatisticedChartData data) {
        return new SingleChart(data);
    }

    private SingleChart(StatisticedChartData data){
        this.data = data;
        this.chart = new XYChartBuilder()
            .width(500)
            .height(300)
            .title(data.getTitle())
            .yAxisTitle("Price")
            .xAxisTitle("Data")
            .build();
    }

    public void createChart() {
        StatisticedData avg = data.getAverange();
        StatisticedData q1 = data.getQ1();
        StatisticedData q2 = data.getQ2();
        StatisticedData q3 = data.getQ3();



        XYSeries series = chart.addSeries(
            this.data.getTitle(), this.data.getXData(), this.data.getYData());
        series.setMarker(SeriesMarkers.NONE);

        addToSeries(series, avg, XChartSeriesColors.YELLOW);
        addToSeries(series, q1, XChartSeriesColors.BLUE);
        addToSeries(series, q2, XChartSeriesColors.PURPLE);
        addToSeries(series, q3, XChartSeriesColors.GREEN);

        Executors.newSingleThreadExecutor().execute(
            () -> {
                JFrame jf = new SwingWrapper<>(chart).displayChart();
                jf.setTitle(data.getTitle());
                jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jf.requestFocus();
            }
        );
    }

    private void addToSeries(XYSeries series, StatisticedData stats, Color color) {
        series = chart.addSeries(
            stats.getStatTitle(), stats.getXData(), stats.getStatiYData());
        series.setMarker(SeriesMarkers.NONE);
        series.setLineColor(color);
    }

}
