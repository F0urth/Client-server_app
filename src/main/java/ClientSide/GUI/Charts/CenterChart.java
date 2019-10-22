package ClientSide.GUI.Charts;

import ClientSide.DataConteners.ChartData;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.List;

/**
 * @author F0urth
 */

public final class CenterChart {

    private XYChart chart;

    private static final Integer LABEL_ROTATION = 45;

    public static CenterChart getInstance(int width, int height, List<ChartData> chartData) {
        return new CenterChart(width, height, chartData);
    }

    private CenterChart(int width, int height, List<ChartData> chartData) {
        buildChart(width, height);
        customize();
        drawSeries(chartData);
    }

    private void buildChart(int width, int height) {
        this.chart = new XYChartBuilder()
            .width(width)
            .height(height)
            .title("Chart of all data")
            .xAxisTitle("Data")
            .yAxisTitle("Price")
            .build();

    }

    private void customize() {
        this.chart.getStyler().setPlotGridLinesVisible(false);
        this.chart.getStyler().setXAxisTickMarkSpacingHint(100);
        this.chart.getStyler().setChartTitleVisible(true);
        this.chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        this.chart.getStyler().setYAxisLogarithmic(true);
        this.chart.getStyler().setXAxisLabelRotation(LABEL_ROTATION);

    }

    private void drawSeries(List<ChartData> chartData) {
        chartData.forEach(e -> {
            XYSeries xySeries = chart.addSeries(
                e.getTitle(), e.getXData(), e.getYData());
            xySeries.setMarker(SeriesMarkers.NONE);
        });
    }

    public XYChart getChart() {
        return this.chart;
    }
}
