package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.utils.ReadMarc;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import static org.junit.Assert.*;

public class RecordIteratorTest {

  @Test
  public void gzipSingle() throws IOException {
    File file = new File("src/test/resources/gzip/test.xml.gz");
    try (InputStream stream = new GZIPInputStream(new FileInputStream(file))) {
      MarcReader reader = ReadMarc.getStreamReader(MarcFormat.XML, stream);
      Record record = reader.next();
      assertNotNull(record);
      assertEquals("990037818200205131", record.getControlNumber());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void gzipIteration() throws IOException {
    File file = new File("src/test/resources/gzip/test.xml.gz");
    try (InputStream stream = new GZIPInputStream(new FileInputStream(file))) {
      MarcReader reader = ReadMarc.getStreamReader(MarcFormat.XML, stream);
      int i = 0;
      List ids = new ArrayList();
      while (reader.hasNext()) {
        Record record = reader.next();
        assertNotNull(record);
        i++;
        ids.add(record.getControlNumber());
      }
      assertEquals(10, i);
      assertEquals("990037818200205131,990037818190205131,990037818130205131," +
        "990037818090205131,990037788010205131,990037556380205131,990037476810205131," +
        "990037475780205131,990037471340205131,990037347160205131", StringUtils.join(ids, ","));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}