package edu.atlas.dart.gui;

import edu.atlas.dart.entity.DartState;
import edu.atlas.dart.entity.DartStateDelta;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

public class DartStatesPanel {
    private JTabbedPane tabbedPane;
    private JPanel mainPanel;
    private JPanel chartStatesPanel;
    private JPanel tablePanel;
    private JTable dartStatesTable;

    private DartStatesXYDataSet dataSet = new DartStatesXYDataSet();
    private HeightDeltaXYDataSet deltaDataSet = new HeightDeltaXYDataSet();
    private DataStatesTableModel tableModel = new DataStatesTableModel();

    public DartStatesPanel() {
        JFreeChart chart = ChartFactory.createTimeSeriesChart("Dart's Data", "Time", "Height",
                                                              dataSet, true, true, false);
        ChartPanel chartPanel = new ChartPanel(chart, true);
        chartPanel.setMinimumDrawHeight(400);
        chartPanel.setMaximumDrawHeight(2000);
        chartPanel.setMinimumDrawWidth(400);
        chartPanel.setMaximumDrawWidth(2000);

        NumberAxis deltaAxis = new NumberAxis("Height delta");
        deltaAxis.setAutoRangeMinimumSize(3);
        XYPlot plot = chart.getXYPlot();
        plot.setRangeAxis(1, deltaAxis);
        plot.setDataset(1, deltaDataSet);
        plot.mapDatasetToRangeAxis(1, 1);

        StandardXYItemRenderer deltaRenderer = new StandardXYItemRenderer();
        deltaRenderer.setSeriesPaint(0, Color.YELLOW);
        plot.setRenderer(1, deltaRenderer);

        chartStatesPanel.add(chartPanel, BorderLayout.CENTER);
        dartStatesTable.setModel(tableModel);
    }

    public JPanel getPanel() {
        return mainPanel;
    }


    public void setDartStates(List<DartState> states, List<DartStateDelta> delta) {
        dataSet.setDartStates(states, delta);
        deltaDataSet.setHeightDelta(delta);
        tableModel.setDartStates(states);
    }

}
