package de.gwdg.metadataqa.marc.utils;

import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.MarcXmlReader;
import org.marc4j.marc.Record;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadMarc {

  public static List<Record> read(String fileName) throws Exception {
    InputStream in = new FileInputStream(fileName);
    MarcReader reader = new MarcStreamReader(in);

    List<Record> records = new ArrayList<>();
    while (reader.hasNext()) {
      Record marc4jRecord = reader.next();
      records.add(marc4jRecord);
    }
    return records;
  }

  public static MarcReader getStreamReader(String fileName) throws Exception {
    InputStream in = new FileInputStream(fileName);
    MarcReader reader = new MarcStreamReader(in);
    return reader;
  }

  public static MarcReader getXmlReader(String fileName) throws Exception {
    InputStream in = new FileInputStream(fileName);
    MarcReader reader = new MarcXmlReader(in);
    return reader;
  }

  public static MarcReader getLineSeparatedMarcReader(String fileName) throws Exception {
    MarcReader reader = new LineSeparatedMarcReader(fileName);
    return reader;
  }

  public static MarcReader getMarcStringReader(String content) throws Exception {
    InputStream is = new ByteArrayInputStream(content.getBytes());
    MarcReader reader = new MarcStreamReader(is);
    return reader;
  }

  public static MarcReader getReader(String fileName, boolean isMarcxml) throws Exception {
    return getReader(fileName, isMarcxml, false);
  }

  public static MarcReader getReader(String fileName, boolean isMarcxml, boolean isLineSeaparated) throws Exception {
    MarcReader reader = null;
    if (isLineSeaparated)
      reader = ReadMarc.getLineSeparatedMarcReader(fileName);
    else
      reader = (isMarcxml) ? ReadMarc.getXmlReader(fileName) : ReadMarc.getStreamReader(fileName);

    return reader;
  }
}
