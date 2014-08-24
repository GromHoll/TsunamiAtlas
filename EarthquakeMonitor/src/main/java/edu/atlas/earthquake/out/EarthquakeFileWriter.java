package edu.atlas.earthquake.out;

import edu.atlas.common.data.DataChangedListener;
import edu.atlas.common.data.event.DataChangedEvent;
import edu.atlas.earthquake.entity.Earthquake;
import edu.atlas.earthquake.out.format.OutFormat;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;

public class EarthquakeFileWriter implements DataChangedListener<Earthquake> {

    private final String FILE_NAME_PREFIX = "Earthquake_";
    private final String FILE_NAME_SUFFIX = ".txt";

    private String filePath;
    private OutFormat format;

    public EarthquakeFileWriter(String filePath, OutFormat format) {
        this.filePath = filePath;
        this.format = format;
    }

    @Override
    public void process(DataChangedEvent<Earthquake> event) {
        output(event.getChangedData());
        output(event.getNewData());
    }

    public void output(Collection<Earthquake> earthquakes) {
        for(Earthquake earthquake : earthquakes) {
            output(earthquake);
        }
    }

    private void output(Earthquake earthquake) {
        try (PrintStream ps = openStream(earthquake)) {
            ps.print(format.getFormattedText(earthquake));
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private PrintStream openStream(Earthquake earthquake) throws IOException {
        File outFile = new File(generateFileName(earthquake));
        outFile.getParentFile().mkdirs();
        return new PrintStream(outFile);
    }

    private String generateFileName(Earthquake earthquake) {
        return filePath + FILE_NAME_PREFIX + earthquake.getId() + FILE_NAME_SUFFIX;
    }
}
