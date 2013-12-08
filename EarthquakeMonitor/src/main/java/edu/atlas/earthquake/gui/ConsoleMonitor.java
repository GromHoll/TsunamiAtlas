package edu.atlas.earthquake.gui;

import edu.atlas.common.data.DataWriter;
import edu.atlas.common.listener.ServerListener;
import edu.atlas.earthquake.entity.Earthquake;

import java.util.Date;
import java.util.List;

public class ConsoleMonitor<T> implements ServerListener, DataWriter<T> {

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
    public void output(List<T> list) {
        for (T value : list) {
            System.out.print("Added : " + value.toString());
        }
    }
}
