package edu.atlas.dart;

import edu.atlas.dart.entity.DartState;
import edu.atlas.dart.entity.DartStation;
import edu.atlas.dart.entity.DartStations;

import java.util.List;
import java.util.Random;

public class DartController implements Runnable {

    private DartStations dartStations = new DartStations();

    @Override
    public void run() {
        // TODO create real program
        List<DartStation> darts = (List<DartStation>) dartStations.getDartStations();
        Random r = new Random();
        int index = r.nextInt(darts.size());
        DartStation dart = darts.get(index);
        List<DartState> states = dartStations.getDartStates(dart);

        System.out.println(dart.getSiteId());
        System.out.println("Size = " + states.size());
        System.out.println();

        List<DartState> subList = states.subList(0, 100);

        subList.stream().map(DartState::toString).forEach(System.out::println);
    }

}
