package de.gwdg.metadataqa.marc.utils.pica.reader;

import de.gwdg.metadataqa.api.util.FileUtils;
import org.junit.Test;
import org.marc4j.marc.Record;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class PicaNormalizedReaderTest {

  @Test
  public void constructor() throws IOException, URISyntaxException {
    PicaNormalizedReader reader = new PicaNormalizedReader(FileUtils.getPath("pica/pica-normalized.dat").toString());
    Record record = null;
    if (reader.hasNext())
      record = reader.next();
    assertEquals("010000011", record.getControlNumber());
    assertEquals(37, record.getDataFields().size());
    assertEquals("001A", record.getDataFields().get(0).getTag());
    assertEquals("2000:06-11-86", record.getDataFields().get(0).getSubfield('0').getData());
  }

  @Test
  public void lineWith0xC285() throws IOException, URISyntaxException {
    Path recordsFile = FileUtils.getPath("pica/pica-line-with-0xC285.dat");
    try (BufferedReader br = new BufferedReader(new FileReader(recordsFile.toString()))) {
      String line;
      System.err.println("\u0085");
      while ((line = br.readLine()) != null) {
        // System.err.println(line);
        System.err.println(line.contains("\uc285"));
        System.err.println(line.contains("\u00C2"));
        System.err.println(line.replace("^.*\u0085", ""));
        /*
        for (Byte b : line.getBytes("UTF8")) {
          byte[] byteArray = new byte[] {b};
          System.err.println(b + " " + new String(new byte[]{b}));
        }
         */
          // byte[] bytes = new byte[] { b };


        // System.err.println(line.indexOf("\r"));
      }
    }
    // byte[] byteArray = new byte[] {b};
    String key = new String(new byte[]{-62, -123}, StandardCharsets.UTF_8);
    System.err.println(key);
  }
}