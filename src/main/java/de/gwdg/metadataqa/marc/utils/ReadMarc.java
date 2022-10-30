package de.gwdg.metadataqa.marc.utils;

import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.marc.Record;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadMarc {

  public static List<Record> read(String fileName) throws Exception {
    return read(fileName, null);
  }

  public static List<Record> read(String fileName, String encoding) throws Exception {
    InputStream in = new FileInputStream(fileName);
    MarcReader reader = new MarcStreamReader(in, encoding);

    List<Record> records = new ArrayList<>();
    while (reader.hasNext()) {
      Record marc4jRecord = reader.next();
      records.add(marc4jRecord);
    }
    return records;
  }
}
