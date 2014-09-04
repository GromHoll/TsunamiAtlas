package edu.atlas.common.data.impl;

import junit.framework.TestCase;

import java.util.List;

public class FileReaderTest extends TestCase {

    private static final String TEST_FILE_NAME = "./TsunamiAtlasCommon/src/test/resources/testReaderFile.txt";

    private static final String FIRST_ROW = "First row";
    private static final String SECOND_ROW = "Second row";
    private static final String THIRDS_ROW = "Thirds row";

    private FileReader reader;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        reader = new FileReader(TEST_FILE_NAME);
    }

    public void testGetData() throws Exception {
        List<String> data = reader.getData();

        assertNotNull(data);
        assertEquals(3, data.size());
        assertEquals(FIRST_ROW, data.get(0));
        assertEquals(SECOND_ROW, data.get(1));
        assertEquals(THIRDS_ROW, data.get(2));
    }

    public void testGetAllData() throws Exception {
        String data = reader.getAllData();

        assertNotNull(data);
        assertEquals(FIRST_ROW + "\n" + SECOND_ROW + "\n" + THIRDS_ROW, data);
    }
}