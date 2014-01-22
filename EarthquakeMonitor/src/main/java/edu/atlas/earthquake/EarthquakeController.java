package edu.atlas.earthquake;

import edu.atlas.common.data.DataChangedListener;
import edu.atlas.common.data.DataParser;
import edu.atlas.common.data.DataReader;
import edu.atlas.common.data.event.DataChangedEvent;
import edu.atlas.common.data.impl.UrlDataReader;
import edu.atlas.common.listener.ServerListener;
import edu.atlas.earthquake.config.GlobalConfiguration;
import edu.atlas.earthquake.config.ValidatorConfiguration;
import edu.atlas.earthquake.data.EarthquakeGeoJsonParser;
import edu.atlas.earthquake.entity.Earthquake;
import edu.atlas.earthquake.gui.ConsoleMonitor;
import edu.atlas.earthquake.gui.EarthquakeMonitorFrame;
import edu.atlas.earthquake.out.EarthquakeFileWriter;
import edu.atlas.earthquake.out.Sms24x7Sender;
import edu.atlas.earthquake.out.format.OutFormat;
import edu.atlas.earthquake.validator.Validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class EarthquakeController extends Thread {
    public static final String FRAME_NAME = "Tsunami Atlas : Earthquake Monitor";

    private int updatePeriod;

    public static final String GLOBAL_CONFIG_PATH = "./config/global.properties";
    public static final String VALIDATOR_CONFIG_PATH = "./config/validator.properties";
    public static final String OUT_TEXT_FORMAT_CONFIG_PATH = "./config/outTextFormat.config";
    public static final String OUT_SMS_FORMAT_CONFIG_PATH = "./config/outSmsFormat.config";

    public static final int INTERRUPT_EXIT_STATUS = -1;
    public static final boolean SERVER_AVAILABLE = true;
    public static final boolean SERVER_NOT_AVAILABLE = false;

    private DataReader dataReader;
    private DataParser<Earthquake> dataParser;

    private Validator validator;

    private List<Earthquake> allEarthquake = new ArrayList<>();

    private List<ServerListener> serverListeners = new LinkedList<>();
    private List<DataChangedListener<Earthquake>> dataChangedListeners = new LinkedList<>();

    public EarthquakeController() {
        GlobalConfiguration globalConfiguration = new GlobalConfiguration(GLOBAL_CONFIG_PATH);
        updatePeriod = globalConfiguration.getUpdatePeriod();

        if (globalConfiguration.isConsoleAvailable()) {
            ConsoleMonitor<Earthquake> consoleMonitor = new ConsoleMonitor<>();
            serverListeners.add(consoleMonitor);
            dataChangedListeners.add(consoleMonitor);
        }

        if (globalConfiguration.isGuiAvailable()) {
            EarthquakeMonitorFrame monitorFrame = new EarthquakeMonitorFrame(FRAME_NAME);
            serverListeners.add(monitorFrame);
            dataChangedListeners.add(monitorFrame);
        }

        if (globalConfiguration.isFileAvailable()) {
            String fileOutPath = globalConfiguration.getFileOutPath();
            OutFormat outFormat = new OutFormat(OUT_TEXT_FORMAT_CONFIG_PATH);
            DataChangedListener<Earthquake> fileWriter = new EarthquakeFileWriter(fileOutPath, outFormat);
            dataChangedListeners.add(fileWriter);
        }

        if (globalConfiguration.isSmsAvailable()) {
            String smsLogin = globalConfiguration.getSmsLogin();
            String smsPassword = globalConfiguration.getSmsPassword();
            List<String> smsReceivers = globalConfiguration.getSmsReceivers();
            OutFormat outFormat = new OutFormat(OUT_SMS_FORMAT_CONFIG_PATH);
            DataChangedListener<Earthquake> sms48Sender = new Sms24x7Sender(smsLogin, smsPassword, smsReceivers, outFormat);
            dataChangedListeners.add(sms48Sender);
        }

        dataReader = new UrlDataReader(globalConfiguration.getDataUrl());
        dataParser = new EarthquakeGeoJsonParser();

        ValidatorConfiguration validatorConfiguration = new ValidatorConfiguration(VALIDATOR_CONFIG_PATH);
        validator = validatorConfiguration.createValidator();
    }

    @Override
    public void run() {
        while (true) {
            try {
                String[] data = dataReader.getData();
                List<Earthquake> earthquakes = dataParser.parseData(unionLines(data));
                processEarthquakes(earthquakes);
                notifyServerListener(SERVER_AVAILABLE);
            } catch (IOException exc) {
                notifyServerListener(SERVER_NOT_AVAILABLE);
            }
            sleep();
        }
    }

    private String unionLines(String[] lines) {
        StringBuilder sb = new StringBuilder();
        for (String l : lines) {
            sb.append(l).append('\n');
        }
        return sb.toString();
    }

    private void sleep() {
        try {
            sleep(updatePeriod);
        } catch(InterruptedException exc) {
            System.exit(INTERRUPT_EXIT_STATUS);
        }
    }

    private void processEarthquakes(List<Earthquake> beforeProcess) {
        List<Earthquake> afterValidation = validateEarthquakes(beforeProcess);

        List<Earthquake> newEarthquake = new ArrayList<>(afterValidation);
        newEarthquake.removeAll(allEarthquake);

        List<Earthquake> changedEarthquake = new ArrayList<>(afterValidation);
        changedEarthquake.retainAll(allEarthquake);

        allEarthquake.addAll(newEarthquake);

        notifyOutputs(new DataChangedEvent<>(allEarthquake, changedEarthquake, newEarthquake));
    }

    private List<Earthquake> validateEarthquakes(List<Earthquake> earthquakes) {
        List<Earthquake> result = new ArrayList<>(earthquakes);
        Iterator<Earthquake> it = result.iterator();
        while (it.hasNext()) {
            if (!validator.validate(it.next())) {
                it.remove();
            }
        }
        return result;
    }

    private void notifyOutputs(DataChangedEvent<Earthquake> event) {
        for(DataChangedListener<Earthquake> eqo : dataChangedListeners) {
            eqo.process(event);
        }
    }

    private void notifyServerListener(boolean isAvailable) {
        for(ServerListener sl : serverListeners) {
            sl.setAvailable(isAvailable);
        }
    }
}
