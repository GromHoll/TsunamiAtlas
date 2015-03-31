package edu.atlas.dart.gui;

import edu.atlas.dart.entity.DartState;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.List;

/**
 * @author GromHoll
 * @since 27.02.2015
 */
public class DartStatesChart extends JPanel {

    private DartStatesXYDataSet dataSet = new DartStatesXYDataSet();

    public DartStatesChart() {
        JFreeChart chart = ChartFactory.createTimeSeriesChart("Dart's Data", "Time", "Height",
                                                              dataSet, false, true, false);
        ChartPanel chartPanel = new ChartPanel(chart, true);
        chartPanel.setMinimumDrawHeight(400);
        chartPanel.setMaximumDrawHeight(2000);
        chartPanel.setMinimumDrawWidth(400);
        chartPanel.setMaximumDrawWidth(2000);

        this.setLayout(new BorderLayout());
        this.add(chartPanel, BorderLayout.CENTER);
    }

    public void setDartStates(List<DartState> states) {
        dataSet.setDartStates(states);
    }
}
