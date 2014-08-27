package edu.atlas.earthquake.out;

import edu.atlas.common.data.DataChangedListener;
import edu.atlas.common.data.event.DataChangedEvent;
import edu.atlas.earthquake.entity.Earthquake;
import edu.atlas.earthquake.out.format.OutFormat;
import edu.atlas.earthquake.out.sms24x7.SMS24x7Impl;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Collection;

@RequiredArgsConstructor
public class Sms24x7Sender implements DataChangedListener<Earthquake> {

    public static final String SENDER = "EQMonitor";

    private SMS24x7Impl sms = new SMS24x7Impl();

    private final String login;
    private final String password;
    private final Collection<String> receivers;
    private final OutFormat format;

    @Override
    public void process(DataChangedEvent<Earthquake> event) {
        receivers.forEach(receiver -> sendAll(receiver, event.getNewData()));
    }

    public void sendAll(String receiver, Collection<Earthquake> earthquakes) {
        earthquakes.forEach(earthquake -> send(receiver, format.getFormattedText(earthquake)));
    }

    private void send(String receiver, String msg) {
        try {
            System.out.println(sms.send(login, password, SENDER, receiver, msg));
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
