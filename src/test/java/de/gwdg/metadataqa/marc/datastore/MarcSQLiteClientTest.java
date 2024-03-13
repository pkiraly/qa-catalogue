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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
    try {
      CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(csvFile));
      int i = 0;
      Map<String, String> map;
      while ((map = reader.readMap()) != null) {
        assertNotNull(map);
        if (i == 0) {
          assertEquals("2", map.get("instances"));
          assertEquals("2", map.get("records"));
          assertEquals("undetectable type", map.get("type"));
          assertEquals("Leader/06 (typeOfRecord): 'x', Leader/07 (bibliographicLevel): ' '", map.get("message"));
          assertEquals("Leader", map.get("MarcPath"));
          assertEquals("1", map.get("typeId"));
          assertEquals("1217", map.get("id"));
          assertEquals("1", map.get("categoryId"));
        }
        i++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (CsvValidationException e) {
      e.printStackTrace();
    }
  }
}