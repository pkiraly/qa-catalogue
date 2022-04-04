package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.utils.marcreader.LineSeparatedMarcReader;
import org.junit.Before;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.marc.Record;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LineReaderTest {
  File file;
  List<String> expectedIds = Arrays.asList(
    "010000178", "010000364", "010000372", "010000380", "010000615",
    "010000925", "010001115", "010001646", "010002081", "010002103",
    "010002596", "010002774", "010003002", "010003126", "010003142",
    "010004033", "010004041", "010004114", "01000436X", "010004378"
  );

  @Before
  public void setUp() throws IOException, URISyntaxException {
    file = FileUtils.getPath("general/000-line-seperated.mrc").toFile();
  }

  @Test
  public void test1() {
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      List<String> collectedIds = new ArrayList<>();
      while ((line = br.readLine()) != null) {
        InputStream is = new ByteArrayInputStream(line.getBytes());
        MarcReader reader = new MarcStreamReader(is);

        while (reader.hasNext()) {
          Record marc4jRecord = reader.next();
          collectedIds.add(marc4jRecord.getControlNumber());
        }
      }
      assertEquals(expectedIds, collectedIds);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void test2() throws FileNotFoundException {
    LineSeparatedMarcReader reader = new LineSeparatedMarcReader(file.getAbsolutePath());
    List<String> collectedIds = new ArrayList<>();
    while (reader.hasNext()) {
      Record marc4jRecord = reader.next();
      collectedIds.add(marc4jRecord.getControlNumber());
    }
    assertEquals(expectedIds, collectedIds);
  }
}
