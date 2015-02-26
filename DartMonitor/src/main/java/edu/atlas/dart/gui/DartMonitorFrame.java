package edu.atlas.dart.gui;

import edu.atlas.common.listener.ServerListener;
import edu.atlas.dart.entity.DartState;
import edu.atlas.dart.entity.DartStation;
import lombok.NonNull;

import javax.swing.*;
import java.awt.BorderLayout;
import java.util.Collection;
import java.util.List;

/**
 * @author GromHoll
 * @since  26.02.2015
 */
public class DartMonitorFrame extends JFrame implements ServerListener {

    public static final String AVAILABLE = "Available";
    public static final String NOT_AVAILABLE = "Not available";

    private Icon red_icon;
    private Icon green_icon;

    private DartStatesChart dartStatesChart = new DartStatesChart();
    private JLabel lastUpdateDate;
    private JLabel statusImage;
    private JLabel statusText;
    private JComboBox<DartStation> stationsComboBox;
    private JPanel chartPanel;
    private JPanel mainPanel;

    public DartMonitorFrame(String name) {
        super(name);
        init();
    }

    private void init() {
        red_icon   = loadIcon("/edu.atlas/images/red.png");
        green_icon = loadIcon("/edu.atlas/images/green.png");

        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        chartPanel.add(dartStatesChart, BorderLayout.CENTER);

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
        statusImage.setIcon(icon);
        statusText.setText(info);
    }

    @Override
    public void setAvailable(boolean isAvailable) {
        if(isAvailable) {
            setServerState(green_icon, AVAILABLE);
        } else {
            setServerState(red_icon, NOT_AVAILABLE);
        }
    }

    public void setDartStations(Collection<DartStation> stations) {
        stations.stream().forEach(stationsComboBox::addItem);
        stationsComboBox.setSelectedIndex(-1);
    }

    public void addChangeStationAction(@NonNull StationChangeListener listener) {
        stationsComboBox.addActionListener(e -> {
            DartStation station = (DartStation) stationsComboBox.getSelectedItem();
            listener.changed(station);
        });
    }

    public void setDartStates(List<DartState> states) {
        dartStatesChart.setDartStates(states);
    }

}
