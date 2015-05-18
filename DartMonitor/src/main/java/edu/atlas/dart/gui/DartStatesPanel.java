package edu.atlas.dart.gui;

import edu.atlas.dart.entity.DartState;
import edu.atlas.dart.entity.DartStateDelta;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DartStatesPanel {
    private JTabbedPane tabbedPane;
    private JPanel mainPanel;
    private JPanel chartStatesPanel;
    private JPanel tablePanel;
    private JTable dartStatesTable;

    private DartStatesXYDataSet dataSet = new DartStatesXYDataSet();
    private DataStatesTableModel tableModel = new DataStatesTableModel();

    public DartStatesPanel() {
        JFreeChart chart = ChartFactory.createTimeSeriesChart("Dart's Data", "Time", "Height",
                                                              dataSet, false, true, false);
        ChartPanel chartPanel = new ChartPanel(chart, true);
        chartPanel.setMinimumDrawHeight(400);
        chartPanel.setMaximumDrawHeight(2000);
        chartPanel.setMinimumDrawWidth(400);
        chartPanel.setMaximumDrawWidth(2000);

        chartStatesPanel.add(chartPanel, BorderLayout.CENTER);
        dartStatesTable.setModel(tableModel);
    }

    public JPanel getPanel() {
        return mainPanel;
    }


    public void setDartStates(List<DartState> states, List<DartStateDelta> delta) {
        dataSet.setDartStates(states, delta);
        tableModel.setDartStates(states);
    }

}
