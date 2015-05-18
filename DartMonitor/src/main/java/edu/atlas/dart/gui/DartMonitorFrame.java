package edu.atlas.dart.gui;

import edu.atlas.dart.entity.DartState;
import edu.atlas.dart.entity.DartStateDelta;
import edu.atlas.dart.entity.DartStation;
import lombok.NonNull;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Label;
import java.util.Collection;
import java.util.List;

/**
 * @author GromHoll
 * @since  26.02.2015
 */
public class DartMonitorFrame extends JFrame {

    private DartStatesPanel dartStatesChart = new DartStatesPanel();
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

    public void setDartStates(List<DartState> states, List<DartStateDelta> delta) {
        stationsComboBox.setEnabled(true);
        chartPanel.removeAll();
        dartStatesChart.setDartStates(states, delta);
        chartPanel.add(dartStatesChart.getPanel(), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

}
