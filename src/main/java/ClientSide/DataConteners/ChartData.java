package ClientSide.DataConteners;


import java.util.Date;
import java.util.List;

/**
 * @author F0urth
 */

public final
    class ChartData {

    private final String chartTitle;
    private final List<Date> xList;
    private final List<Double> yList;

    private ChartData(ChartDataBuilder builder) {
        this.chartTitle = builder.chartTitle;
        this.xList = builder.xList;
        this.yList = builder.yList;
    }

    public List<Date> getXData() {
        return xList;
    }


    public List<Double> getYData() {
        return yList;
    }

    public String getTitle() {
        return this.chartTitle;
    }

    public
        static
            class ChartDataBuilder {

        private String chartTitle;
        private List<Date> xList;
        private List<Double> yList;

        public ChartDataBuilder setTitle(String title) {
            this.chartTitle = title;
            return this;
        }

        public ChartDataBuilder setXData(List<Date> xData) {
            this.xList = xData;
            return this;
        }

        public ChartDataBuilder setYData(List<Double> yData) {
            this.yList = yData;
            return this;
        }

        public ChartData build() {
            return new ChartData(this);
        }

    }
}
