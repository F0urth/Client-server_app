package ClientSide.DataConteners;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author F0urth
 */

public final
    class StatisticedChartData {

    private ChartData data;

    public static StatisticedChartData getInstance(ChartData data) {
        return new StatisticedChartData(data);
    }

    private StatisticedChartData(ChartData data) {
        this.data = data;
    }

    public List<Date> getXData() {
        return data.getXData();
    }

    public List<Double> getYData() {
        return data.getYData();
    }

    public String getTitle() {
        return data.getTitle();
    }

    public StatisticedData getAverange() {
        var avg = getYData()
            .stream()
            .mapToDouble(e -> e)
            .average()
            .getAsDouble();
        List<Double> statData = mapValue(avg);

        return StatisticedData.getInstance("Averange", statData, getXData());
    }

    public StatisticedData getQ1() {
        var aDouble = getYData()
            .stream()
            .sorted()
            .limit(getYData().size() / 4)
            .max(Double::compareTo)
            .get();
        List<Double> statData = mapValue(aDouble);
        return StatisticedData.getInstance("Q1", statData, getXData());
    }
    public StatisticedData getQ2() {
        var aDouble = getYData()
            .stream()
            .sorted()
            .limit(getYData().size() / 2)
            .max(Double::compareTo)
            .get();
        List<Double> statData = mapValue(aDouble);
        return StatisticedData.getInstance("Q2", statData, getXData());
    }

    public StatisticedData getQ3() {
        var aDouble = getYData()
            .stream()
            .sorted()
            .limit((getYData().size() / 4) * 3)
            .max(Double::compareTo)
            .get();
        List<Double> statData = mapValue(aDouble);
        return StatisticedData.getInstance("Q3", statData, getXData());
    }

    private List<Double> mapValue(Double val) {
        return getYData()
            .stream()
            .map(e -> val)
            .collect(Collectors.toList());
    }
}
