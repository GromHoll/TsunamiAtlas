package edu.atlas.earthquake.gui;

import edu.atlas.common.data.DataChangedListener;
import edu.atlas.common.data.event.DataChangedEvent;
import edu.atlas.common.listener.ServerListener;
import edu.atlas.earthquake.entity.Earthquake;

import javax.swing.*;
import java.util.Date;

public class EarthquakeMonitorFrame extends JFrame implements DataChangedListener<Earthquake>, ServerListener {

    public static final String AVAILABLE = "Available";
    public static final String NOT_AVAILABLE = "Not available";

    private Icon red_icon;
    private Icon green_icon;

    private JPanel mainPanel;
    private JTable infoTable;
    private JLabel imageLabel;
    private JLabel statusLabel;
    private JLabel lastUpdateLabel;
    private EarthquakeTableModel tableModel;

    public EarthquakeMonitorFrame(String name) {
        super(name);
        init();
    }

    private void init() {
        red_icon   = loadIcon("/edu.atlas/images/red.png");
        green_icon = loadIcon("/edu.atlas/images/green.png");

        tableModel = new EarthquakeTableModel();
        infoTable.setModel(tableModel);

        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setAvailable(false);

        this.pack();
        this.setVisible(true);
    }

    private Icon loadIcon(String path) {
        try {
            return new ImageIcon(getClass().getResource(path));
        } catch(Exception exc) {
            System.out.println("[ERROR] Image " + path + " not found.");
            return null;
        }
    }

    private void setServerState(Icon icon, String info) {
        imageLabel.setIcon(icon);
        statusLabel.setText(info);
    }

    @Override
    public void setAvailable(boolean isAvailable) {
        if(isAvailable) {
            setServerState(green_icon, AVAILABLE);
        } else {
            setServerState(red_icon, NOT_AVAILABLE);
        }
    }

    @Override
    public void process(DataChangedEvent<Earthquake> event) {
        lastUpdateLabel.setText(new Date().toString());
        tableModel.setData(event.getAllData());
    }

}
