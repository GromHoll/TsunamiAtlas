package edu.atlas.earthquake.output;

import edu.atlas.common.data.DataChangedListener;
import edu.atlas.common.data.event.DataChangedEvent;
import edu.atlas.earthquake.entity.Earthquake;
import edu.atlas.earthquake.output.format.OutFormat;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;

@RequiredArgsConstructor
public class EarthquakeFileWriter implements DataChangedListener<Earthquake> {

    private final String FILE_NAME_PREFIX = "Earthquake_";
    private final String FILE_NAME_SUFFIX = ".txt";

    private final String filePath;
    private final OutFormat format;

    @Override
    public void process(DataChangedEvent<Earthquake> event) {
        output(event.getChangedData());
        output(event.getNewData());
    }

    public void output(Collection<Earthquake> earthquakes) {
        earthquakes.forEach(this::output);
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
