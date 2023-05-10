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
import java.util.Map;

public class MarcSQLiteClientTest {

  @Test
  public void connect() throws IOException, URISyntaxException {
    MarcSQLiteClient client = new MarcSQLiteClient();
    File file = new File(FileUtils.getPath("sqlite").toFile(), "test.db");
    System.err.println(file.getAbsolutePath());
    // if (file.exists())
    //  file.delete();

    client.connect(file.getPath());
    System.err.println(file.lastModified());
    System.err.println(file.length());

    // if (file.exists())
    //  file.delete();
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