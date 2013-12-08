package edu.atlas.earthquake.out;

import edu.atlas.common.data.DataWriter;
import edu.atlas.earthquake.entity.Earthquake;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeFileWriter implements DataWriter<Earthquake> {

    private final String FILE_NAME_PREFIX = "Earthquake_";
    private final String FILE_NAME_SUFFIX = ".txt";

    private String filePath;
    private List<OutNode> format;

    public EarthquakeFileWriter(String filePath, List<OutNode> format) {
        this.filePath = filePath;
        this.format = new ArrayList<>(format);
    }

    @Override
    public void output(List<Earthquake> list) {
        for(Earthquake earthquake : list) {
            output(earthquake);
        }
    }

    private void output(Earthquake earthquake) {
        try (PrintStream ps = openStream(earthquake)) {
            for (OutNode outNode : format) {
                ps.print(outNode.getOut(earthquake));
            }
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
