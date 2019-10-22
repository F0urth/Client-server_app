package ClientSide.DataConteners;

import java.util.Date;
import java.util.List;

/**
 * @author F0urth
 */

public class StatisticedData {

    private ChartData data;

    static StatisticedData getInstance(String statTitle, List<Double> statiYData, List<Date> timeLine) {
        return new StatisticedData(statTitle, statiYData, timeLine);
    }

    private StatisticedData(String statTitle, List<Double> statiY, List<Date> timeLine) {
        this.data = new ChartData
            .ChartDataBuilder()
            .setTitle(statTitle)
            .setYData(statiY)
            .setXData(timeLine).build();
    }

    public List<Double> getStatiYData() {
        return this.data.getYData();
    }

    public String getStatTitle() {
        return this.data.getTitle();
    }

    public List<Date> getXData() {
        return this.data.getXData();
    }
}
