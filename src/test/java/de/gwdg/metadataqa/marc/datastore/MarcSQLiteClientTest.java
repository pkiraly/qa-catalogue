package de.gwdg.metadataqa.marc.datastore;

import com.opencsv.CSVIterator;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;
import de.gwdg.metadataqa.api.util.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class MarcSQLiteClientTest {

  @Test
  public void connect() throws IOException, URISyntaxException {
    MarcSQLiteClient client = new MarcSQLiteClient();
    File file = new File(FileUtils.getPath("sqlite").toFile(), "test.db");
    assertTrue(file.getCanonicalPath().endsWith("test-classes/sqlite/test.db"));
    if (!file.exists())
      file.createNewFile();
    assertTrue(file.exists());

    client.connect(file.getPath());
    client.close();

    if (file.exists())
      Files.delete(file.toPath());
  }

  @Test
  public void csv2db() throws IOException, URISyntaxException {
    File csvFile = new File(FileUtils.getPath("sqlite").toFile(), "issue-summary.csv");
    CSVIterator iterator = null;
    try {
      CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(csvFile));
      iterator = new CSVIterator(reader);
      Map<String, String> map;
      while((map = reader.readMap()) != null) {
        System.err.println(map);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (CsvValidationException e) {
      e.printStackTrace();
    }
  }
}