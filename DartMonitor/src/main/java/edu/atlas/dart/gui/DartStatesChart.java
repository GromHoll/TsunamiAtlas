package edu.atlas.dart.gui;

import edu.atlas.dart.entity.DartState;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.JPanel;
import java.awt.*;
import java.util.List;

/**
 * @author GromHoll
 * @since 27.02.2015
 */
public class DartStatesChart extends JPanel {

    private DartStatesXYDataSet dataSet = new DartStatesXYDataSet();
    private ChartPanel chartPanel;

    public DartStatesChart() {
        JFreeChart chart = ChartFactory.createTimeSeriesChart("Dart's Data", "Time", "Height",
                                                              dataSet, false, true, false);
        this.chartPanel = new ChartPanel(chart, true);
        this.chartPanel.setMinimumDrawHeight(400);
        this.chartPanel.setMaximumDrawHeight(2000);
        this.chartPanel.setMinimumDrawWidth(400);
        this.chartPanel.setMaximumDrawWidth(2000);

        this.setLayout(new BorderLayout());
        Label label = new Label("Please, select some DART station...");
        label.setAlignment(Label.CENTER);
        this.add(label, BorderLayout.CENTER);
    }

    public void setDartStates(List<DartState> states) {
        removeAll();
        dataSet.setDartStates(states);
        add(chartPanel, BorderLayout.CENTER);
        revalidate();
    }
}
