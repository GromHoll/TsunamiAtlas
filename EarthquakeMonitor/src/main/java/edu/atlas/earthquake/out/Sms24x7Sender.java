package edu.atlas.earthquake.out;

import edu.atlas.common.data.DataChangedListener;
import edu.atlas.common.data.event.DataChangedEvent;
import edu.atlas.earthquake.entity.Earthquake;
import edu.atlas.earthquake.out.format.OutFormat;
import edu.atlas.earthquake.out.sms24x7.SMS24x7Impl;

import java.io.IOException;
import java.util.List;

public class Sms24x7Sender implements DataChangedListener<Earthquake> {

    public static final String SENDER = "EQMonitor";

    private SMS24x7Impl sms = new SMS24x7Impl();

    private OutFormat format;
    private String login;
    private String password;
    private List<String> receivers;

    public Sms24x7Sender(String login, String password, List<String> receivers, OutFormat format) {
        this.format = format;
        this.login = login;
        this.password = password;
        this.receivers = receivers;
    }

    @Override
    public void process(DataChangedEvent<Earthquake> event) {
        for (String receiver : receivers) {
            sendAll(receiver, event.getNewData());
        }
    }

    public void sendAll(String receiver, List<Earthquake> earthquakes) {
        for (Earthquake earthquake : earthquakes) {
            send(receiver, format.getFormattedText(earthquake));
        }
    }

    private void send(String receiver, String msg) {
        try {
            System.out.println(sms.send(login, password, SENDER, receiver, msg));
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
