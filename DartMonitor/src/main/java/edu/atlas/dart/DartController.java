package edu.atlas.dart;

import edu.atlas.dart.entity.DartStations;

public class DartController implements Runnable {

    private DartStations dartStations = new DartStations();

    @Override
    public void run() {
        // TODO create real program
        dartStations.getDartStations().forEach(System.out::println);
    }

}
