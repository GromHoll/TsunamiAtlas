package edu.atlas.earthquake.gui;

import edu.atlas.common.data.DataChangedListener;
import edu.atlas.common.data.event.DataChangedEvent;
import edu.atlas.common.listener.ServerListener;

import java.util.Date;

public class ConsoleMonitor<T> implements ServerListener, DataChangedListener<T> {

    public static final String SERVER_AVAILABLE = "Server available : ";
    public static final String SERVER_NOT_AVAILABLE = "Server not available : ";

    @Override
    public void setAvailable(boolean isAvailable) {
        if (isAvailable) {
            System.out.print(SERVER_AVAILABLE);
        } else {
            System.out.print(SERVER_NOT_AVAILABLE);
        }
        System.out.println(new Date());
    }

    @Override
    public void process(DataChangedEvent<T> event) {
        for (T value : event.getNewData()) {
            System.out.println("Added : " + value.toString());
        }
    }
}
