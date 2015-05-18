package edu.atlas.dart;

import edu.atlas.dart.cleaner.DartCleaner;
import edu.atlas.dart.cleaner.TTideCleaner;
import edu.atlas.dart.entity.DartState;
import edu.atlas.dart.entity.DartStateDelta;
import edu.atlas.dart.entity.DartStation;
import edu.atlas.dart.entity.DartStations;
import edu.atlas.dart.gui.DartMonitorFrame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class DartController implements Runnable {

    public static final String FRAME_NAME = "Tsunami Atlas : DART Monitor";

    // TODO maybe move cleaner to DartStations or create facade?
    private DartStations dartStations = new DartStations();
    private DartCleaner cleaner = new TTideCleaner();

    @Override
    public void run() {
        List<DartStation> darts = (List<DartStation>) dartStations.getDartStations();
        DartMonitorFrame frame = new DartMonitorFrame(FRAME_NAME);
        frame.setDartStations(darts);
        frame.addChangeStationAction(station -> {
            if (station != null) {
                frame.setLoading();
                Thread loadingThread = new Thread(() -> {
                    List<DartState> states = dartStations.getDartStates(station);
                    List<DartStateDelta> delta = cleaner.clear(station, states);
                    frame.setDartStates(states, delta);
                });
                loadingThread.start();
            }
        });
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cleaner.dispose();
                frame.dispose();
                System.exit(0);
            }
        });
    }
}
