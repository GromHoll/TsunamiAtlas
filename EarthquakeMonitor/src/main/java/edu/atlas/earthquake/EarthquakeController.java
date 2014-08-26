package edu.atlas.earthquake;

import edu.atlas.common.config.Configuration;
import edu.atlas.common.config.SystemConfiguration;
import edu.atlas.common.data.DataChangedListener;
import edu.atlas.common.data.DataParser;
import edu.atlas.common.data.DataReader;
import edu.atlas.common.data.event.DataChangedEvent;
import edu.atlas.common.data.impl.UrlDataReader;
import edu.atlas.common.listener.ServerListener;
import edu.atlas.earthquake.data.EarthquakeGeoJsonParser;
import edu.atlas.earthquake.entity.Earthquake;
import edu.atlas.earthquake.gui.ConsoleMonitor;
import edu.atlas.earthquake.gui.EarthquakeMonitorFrame;
import edu.atlas.earthquake.out.EarthquakeFileWriter;
import edu.atlas.earthquake.out.EmailSender;
import edu.atlas.earthquake.out.Sms24x7Sender;
import edu.atlas.earthquake.out.format.OutFormat;
import edu.atlas.earthquake.validator.Validator;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

import static edu.atlas.earthquake.ConfigPathConst.*;
import static edu.atlas.earthquake.config.GlobalKeys.*;

public class EarthquakeController extends Thread {

    public static final String FRAME_NAME = "Tsunami Atlas : Earthquake Monitor";

    private int updatePeriod;

    public static final int INTERRUPT_EXIT_STATUS = -1;
    public static final boolean SERVER_AVAILABLE = true;
    public static final boolean SERVER_NOT_AVAILABLE = false;

    private DataReader dataReader;
    private DataParser<Earthquake> dataParser;

    private Validator validator;

    private Collection<Earthquake> allEarthquake = new CircularFifoQueue<>(1000);

    private List<ServerListener> serverListeners = new LinkedList<>();
    private List<DataChangedListener<Earthquake>> dataChangedListeners = new LinkedList<>();

    public EarthquakeController() {
        loadProperties();

        Configuration config = new SystemConfiguration();

        updatePeriod = config.getProperty(UPDATE_PERIOD);

        if (config.getProperty(CONSOLE_AVAILABLE)) {
            ConsoleMonitor<Earthquake> consoleMonitor = new ConsoleMonitor<>();
            serverListeners.add(consoleMonitor);
            dataChangedListeners.add(consoleMonitor);
        }

        if (config.getProperty(GUI_AVAILABLE)) {
            EarthquakeMonitorFrame monitorFrame = new EarthquakeMonitorFrame(FRAME_NAME);
            serverListeners.add(monitorFrame);
            dataChangedListeners.add(monitorFrame);
        }

        if (config.getProperty(FILE_AVAILABLE)) {
            String fileOutPath = config.getProperty(FILE_OUT_PATH);
            OutFormat outFormat = new OutFormat(OUT_TEXT_FORMAT_CONFIG_PATH);
            DataChangedListener<Earthquake> fileWriter = new EarthquakeFileWriter(fileOutPath, outFormat);
            dataChangedListeners.add(fileWriter);
        }

        if (config.getProperty(EMAIL_AVAILABLE)) {
            OutFormat outFormat = new OutFormat(OUT_EMAIL_FORMAT_CONFIG_PATH);
            DataChangedListener<Earthquake> emailSender = new EmailSender(outFormat);
            dataChangedListeners.add(emailSender);
        }

        if (config.getProperty(SMS_AVAILABLE)) {
            String smsLogin = config.getProperty(SMS_LOGIN);
            String smsPassword = config.getProperty(SMS_PASSWORD);
            Collection<String> smsReceivers = config.getProperty(SMS_RECEIVERS);
            OutFormat outFormat = new OutFormat(OUT_SMS_FORMAT_CONFIG_PATH);
            DataChangedListener<Earthquake> sms48Sender =
                    new Sms24x7Sender(smsLogin, smsPassword, smsReceivers, outFormat);
            dataChangedListeners.add(sms48Sender);
        }

        dataReader = new UrlDataReader(config.getProperty(DATA_URL));
        dataParser = new EarthquakeGeoJsonParser();

        validator = Validator.createFromConfiguration(config);
    }

    private void loadProperties() {
        try (Reader globalConfigReader = new FileReader(GLOBAL_CONFIG_PATH);
                Reader validatorConfigReader = new FileReader(VALIDATOR_CONFIG_PATH)) {
            System.getProperties().load(globalConfigReader);
            System.getProperties().load(validatorConfigReader);
        } catch (IOException exc) {
            System.out.println("Configuration files missed.");
            exc.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String data = dataReader.getAllData();
                Collection<Earthquake> earthquakes = dataParser.parseData(data);
                processEarthquakes(earthquakes);
                notifyServerListener(SERVER_AVAILABLE);
            } catch (IOException exc) {
                notifyServerListener(SERVER_NOT_AVAILABLE);
            }
            sleep();
        }
    }

    private void sleep() {
        try {
            sleep(updatePeriod);
        } catch(InterruptedException exc) {
            System.exit(INTERRUPT_EXIT_STATUS);
        }
    }

    private void processEarthquakes(Collection<Earthquake> beforeProcess) {
        Collection<Earthquake> afterValidation = validateEarthquakes(beforeProcess);

        Collection<Earthquake> newEarthquake = new ArrayList<>(afterValidation);
        newEarthquake.removeAll(allEarthquake);

        Collection<Earthquake> changedEarthquake = new ArrayList<>(afterValidation);
        changedEarthquake.retainAll(allEarthquake);

        allEarthquake.addAll(newEarthquake);

        notifyOutputs(new DataChangedEvent<>(allEarthquake, changedEarthquake, newEarthquake));
    }

    private Collection<Earthquake> validateEarthquakes(Collection<Earthquake> earthquakes) {
        return earthquakes.stream().filter(validator::validate).collect(Collectors.toList());
    }

    private void notifyOutputs(DataChangedEvent<Earthquake> event) {
        dataChangedListeners.forEach(listener -> listener.process(event));
    }

    private void notifyServerListener(boolean isAvailable) {
        serverListeners.forEach(listener -> listener.setAvailable(isAvailable));
    }
}
