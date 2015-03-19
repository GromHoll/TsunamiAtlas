package edu.atlas.dart;

import edu.atlas.dart.entity.DartStation;
import edu.atlas.dart.entity.DartStations;
import edu.atlas.dart.gui.DartMonitorFrame;

import java.util.List;

public class DartController implements Runnable {

    public static final String FRAME_NAME = "Tsunami Atlas : DART Monitor";

    private DartStations dartStations = new DartStations();

    @Override
    public void run() {
        List<DartStation> darts = (List<DartStation>) dartStations.getDartStations();
        DartMonitorFrame frame = new DartMonitorFrame(FRAME_NAME);
        frame.setDartStations(darts);
        frame.addChangeStationAction(station -> {
            if (station != null) {
                frame.setDartStates(dartStations.getDartStates(station));
            }
        });
    }

}
