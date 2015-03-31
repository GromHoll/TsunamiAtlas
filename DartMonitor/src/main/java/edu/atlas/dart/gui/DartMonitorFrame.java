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
public class DartMonitorFrame extends JFrame {

    private DartStatesChart dartStatesChart = new DartStatesChart();
    private JComboBox<DartStation> stationsComboBox;
    private JPanel chartPanel;
    private JPanel mainPanel;

    public DartMonitorFrame(String name) {
        super(name);
        init();
    }

    private void init() {
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.chartPanel.add(dartStatesChart, BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);
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
