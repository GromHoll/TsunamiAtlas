package edu.atlas.earthquake.gui;

import edu.atlas.earthquake.entity.Earthquake;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EarthquakeTableModel extends DefaultTableModel {

    private List<Earthquake> earthquakes = new ArrayList<>();

    public static final String columnNames[] = {"Place", "Longitude", "Latitude", "Magnitude", "Date"};

    public void setData(Collection<Earthquake> newEarthquakes) {
        if(newEarthquakes != null) {
            earthquakes.clear();
            earthquakes.addAll(newEarthquakes);
            fireTableDataChanged();
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return String.class;
            case 1: case 2: case 3:
                return Double.class;
            case 4:
                return String.class;
            default:
                return String.class;
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if(columnIndex < 0 || columnIndex >= columnNames.length) {
            return "";
        } else {
            return columnNames[columnIndex];
        }
    }

    @Override
    public int getRowCount() {
        return (earthquakes != null) ? earthquakes.size() : 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Earthquake earthquake = earthquakes.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return earthquake.getPlace();
            case 1:
                return earthquake.getLongitude();
            case 2:
                return earthquake.getLatitude();
            case 3:
                return earthquake.getMag();
            case 4:
                return earthquake.getDate().toString();
        }

        return "";
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

}
