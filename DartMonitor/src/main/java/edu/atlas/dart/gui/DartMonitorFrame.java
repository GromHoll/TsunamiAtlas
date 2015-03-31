package edu.atlas.dart.gui;

import edu.atlas.dart.entity.DartState;
import edu.atlas.dart.entity.DartStation;
import lombok.NonNull;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
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
    private Label loadingLabel;

    public DartMonitorFrame(String name) {
        super(name);
        init();
    }

    private void init() {
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        loadingLabel = new Label("Please, wait a moment...");
        loadingLabel.setAlignment(Label.CENTER);

        Label emptyDataLabel = new Label("Please, select some DART station...");
        emptyDataLabel.setAlignment(Label.CENTER);
        this.chartPanel.add(emptyDataLabel, BorderLayout.CENTER);

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

    public void setLoading() {
        stationsComboBox.setEnabled(false);
        chartPanel.removeAll();
        chartPanel.add(loadingLabel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void setDartStates(List<DartState> states) {
        stationsComboBox.setEnabled(true);
        chartPanel.removeAll();
        dartStatesChart.setDartStates(states);
        chartPanel.add(dartStatesChart, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

}
