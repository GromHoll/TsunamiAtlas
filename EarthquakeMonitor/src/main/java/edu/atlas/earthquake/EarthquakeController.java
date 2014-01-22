package edu.atlas.earthquake;

import edu.atlas.common.data.ConfigLoader;
import edu.atlas.common.data.DataParser;
import edu.atlas.common.data.DataReader;
import edu.atlas.common.data.DataChangedListener;
import edu.atlas.common.data.event.DataChangedEvent;
import edu.atlas.common.data.impl.UrlDataReader;
import edu.atlas.common.listener.ServerListener;
import edu.atlas.earthquake.config.GlobalConfigConstants;
import edu.atlas.earthquake.config.ValidatorConfigConstants;
import edu.atlas.earthquake.data.EarthquakeGeoJsonParser;
import edu.atlas.earthquake.entity.Earthquake;
import edu.atlas.earthquake.gui.ConsoleMonitor;
import edu.atlas.earthquake.gui.EarthquakeMonitorFrame;
import edu.atlas.earthquake.out.EarthquakeFileWriter;
import edu.atlas.earthquake.out.Sms24x7Sender;
import edu.atlas.earthquake.out.format.OutFormat;
import edu.atlas.earthquake.validator.Validator;

import java.io.IOException;
import java.util.*;


// XXX maybe not thread
public class EarthquakeController extends Thread {

    public static final String GLOBAL_CONFIG_PATH = "./config/global.properties";
    public static final String VALIDATOR_CONFIG_PATH = "./config/validator.properties";
    public static final String OUT_TEXT_FORMAT_CONFIG_PATH = "./config/outTextFormat.config";
    public static final String OUT_SMS_FORMAT_CONFIG_PATH = "./config/outSmsFormat.config";

    private String dataUrl = GlobalConfigConstants.DEFAULT_DATA_URL;
    private int updatePeriod = GlobalConfigConstants.DEFAULT_UPDATE_PERIOD;
    private boolean consoleAvailable = GlobalConfigConstants.DEFAULT_CONSOLE_AVAILABLE;
    private boolean guiAvailable = GlobalConfigConstants.DEFAULT_GUI_AVAILABLE;

    private boolean fileAvailable = GlobalConfigConstants.DEFAULT_FILE_AVAILABLE;
    private String fileOutPath = GlobalConfigConstants.DEFAULT_FILE_OUT_PATH;

    private boolean smsAvailable = GlobalConfigConstants.DEFAULT_SMS48_AVAILABLE;
    private String[] smsReceivers = null;
    private String smsLogin = null;
    private String smsPassword = null;

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
        loadGlobalConfiguration();

        if (consoleAvailable) {
            ConsoleMonitor<Earthquake> consoleMonitor = new ConsoleMonitor<>();
            serverListeners.add(consoleMonitor);
            dataChangedListeners.add(consoleMonitor);
        }

        if (guiAvailable) {
            EarthquakeMonitorFrame monitorFrame = new EarthquakeMonitorFrame(GlobalConfigConstants.FRAME_NAME);
            serverListeners.add(monitorFrame);
            dataChangedListeners.add(monitorFrame);
        }

        if (fileAvailable) {
            OutFormat outFormat = new OutFormat(OUT_TEXT_FORMAT_CONFIG_PATH);
            DataChangedListener<Earthquake> fileWriter = new EarthquakeFileWriter(fileOutPath, outFormat);
            dataChangedListeners.add(fileWriter);
        }

        if (smsAvailable) {
            OutFormat outFormat = new OutFormat(OUT_SMS_FORMAT_CONFIG_PATH);
            DataChangedListener<Earthquake> sms48Sender = new Sms24x7Sender(smsLogin, smsPassword, smsReceivers, outFormat);
            dataChangedListeners.add(sms48Sender);
        }

        dataReader = new UrlDataReader(dataUrl);
        dataParser = new EarthquakeGeoJsonParser();

        validator = createValidator();
    }

    private void loadGlobalConfiguration() {
        ConfigLoader loader = new ConfigLoader(GLOBAL_CONFIG_PATH);
        Map<String, String> config = loader.getConfig();

        if (config.get(GlobalConfigConstants.DATA_URL) != null) {
            dataUrl = config.get(GlobalConfigConstants.DATA_URL);
        }
        if (config.get(GlobalConfigConstants.UPDATE_PERIOD) != null) {
            updatePeriod = Integer.valueOf(config.get(GlobalConfigConstants.UPDATE_PERIOD));
        }
        if (config.get(GlobalConfigConstants.CONSOLE_AVAILABLE) != null) {
            consoleAvailable = Boolean.valueOf(config.get(GlobalConfigConstants.CONSOLE_AVAILABLE));
        }
        if (config.get(GlobalConfigConstants.GUI_AVAILABLE) != null) {
            guiAvailable = Boolean.valueOf(config.get(GlobalConfigConstants.GUI_AVAILABLE));
        }
        if (config.get(GlobalConfigConstants.FILE_AVAILABLE) != null) {
            fileAvailable = Boolean.valueOf(config.get(GlobalConfigConstants.FILE_AVAILABLE));
        }
        if (config.get(GlobalConfigConstants.FILE_OUT_PATH) != null) {
            fileOutPath = config.get(GlobalConfigConstants.FILE_OUT_PATH);
        }
        if (config.get(GlobalConfigConstants.SMS_AVAILABLE) != null &&
                config.get(GlobalConfigConstants.SMS_LOGIN) != null &&
                config.get(GlobalConfigConstants.SMS_PASSWORD) != null &&
                config.get(GlobalConfigConstants.SMS_RECEIVERS) != null) {
            smsAvailable = Boolean.valueOf(config.get(GlobalConfigConstants.SMS_AVAILABLE));
            smsLogin = config.get(GlobalConfigConstants.SMS_LOGIN);
            smsPassword = config.get(GlobalConfigConstants.SMS_PASSWORD);
            smsReceivers = config.get(GlobalConfigConstants.SMS_RECEIVERS).split(",");
        }
    }

    private Validator createValidator() {
        Validator newValidator = new Validator();
        ConfigLoader loader = new ConfigLoader(VALIDATOR_CONFIG_PATH);
        Map<String, String> config = loader.getConfig();

        if (config.get(ValidatorConfigConstants.MIN_LATITUDE) != null) {
            newValidator.setMinLatitude(Double.valueOf(config.get(ValidatorConfigConstants.MIN_LATITUDE)));
        }
        if (config.get(ValidatorConfigConstants.MAX_LATITUDE) != null) {
            newValidator.setMaxLatitude(Double.valueOf(config.get(ValidatorConfigConstants.MAX_LATITUDE)));
        }
        if (config.get(ValidatorConfigConstants.MIN_LONGITUDE) != null) {
            newValidator.setMinLongitude(Double.valueOf(config.get(ValidatorConfigConstants.MIN_LONGITUDE)));
        }
        if (config.get(ValidatorConfigConstants.MAX_LONGITUDE) != null) {
            newValidator.setMaxLongitude(Double.valueOf(config.get(ValidatorConfigConstants.MAX_LONGITUDE)));
        }
        if (config.get(ValidatorConfigConstants.MIN_MAG) != null) {
            newValidator.setMinMag(Double.valueOf(config.get(ValidatorConfigConstants.MIN_MAG)));
        }
        return newValidator;
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
