package edu.atlas.dart.gui;

import edu.atlas.dart.entity.DartState;

import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStatesTableModel extends DefaultTableModel {

    private List<DartState> dartStates = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public static final String columnNames[] = {"Date", "Real height", "Cleared Height", "Delta Height"};

    public void setDartStates(Collection<DartState> newDartStates) {
        if(newDartStates != null) {
            dartStates.clear();
            dartStates.addAll(newDartStates);
            fireTableDataChanged();
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
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
        return (dartStates != null) ? dartStates.size() : 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DartState dartState = dartStates.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return dateFormat.format(dartState.getDate().getTime());
            case 1:
                return dartState.getHeight();
            case 2:
                return dartState.getClearedHeight();
            case 3:
                return dartState.getDelta();
        }
        return "";
    }

}
