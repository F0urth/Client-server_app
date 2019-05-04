package ClientSide;

import ClientSide.Communication.CommunicationHandler;
import ClientSide.DataConteners.ChartData;

import ClientSide.GUI.ChatGUI.ChatGUI;

import ClientSide.GUI.FrontGUI;
import Absolute.MergedData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public
    enum Controller {
    INSTANCE;

    private Map<String, List<MergedData>> dataMap;
    private List<ChartData> data;
    private CommunicationHandler communicationHandler;
    private FrontGUI gui;
    private ChatGUI chat;
    private Queue<String> massagesBeforeLoging;

    public void setData(Map<String, List<MergedData>> data) {
        this.dataMap = data;
        this.data = new ArrayList<>();
        massagesBeforeLoging = new ConcurrentLinkedQueue<>();
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
        this.gui = FrontGUI.getInstance(this.data);
        this.gui.run();
    }

    void setCommunicationHandler(CommunicationHandler cm) {
        this.communicationHandler = cm;
    }

    public void addToMassage(String massage) {
        this.communicationHandler.addToOutQueue(massage);
    }

    public void setChat(ChatGUI chat) {
        this.chat = chat;
        this.chat.addMassiveMassges(this.massagesBeforeLoging);
        this.gui.changeActionListener();
    }

    public ChatGUI getChat() {
        return this.chat;
    }

    public void appendToChatMassages(String massage) {
        if (this.chat != null) this.chat.appendMassage(massage);
        else this.massagesBeforeLoging.add(massage);

    }
}
