package ClientSide;

import ClientSide.DataConteners.ChartData;
import ClientSide.GUI.FrontGUI;
import ServerSide.Databases.MergedData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public
    enum Controler {
    INSTANCE;

    private Map<String, List<MergedData>> dataMap;
    private List<ChartData> data;



    public void setData(Map<String, List<MergedData>> data) {
        this.dataMap = data;
        this.data = new ArrayList<>();
    }

    public void start() {
        for (var entry : dataMap.entrySet()) {
            var list = entry.getValue();
            this.data.add(
                new ChartData.ChartDataBuilder()
                    .setTitle(entry.getKey())
                    .setXData(list.stream()
                        .map(MergedData::getData)
                        .collect(Collectors.toList()))
                    .setYData(list.stream()
                        .map(MergedData::getPrice)
                        .collect(Collectors.toList()))
                    .build()
            );
        }
        FrontGUI.getInstance(this.data).run();
    }
}
