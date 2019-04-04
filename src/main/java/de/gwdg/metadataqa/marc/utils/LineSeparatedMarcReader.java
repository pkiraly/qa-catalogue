package de.gwdg.metadataqa.marc.utils;

import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.marc.Record;

import java.io.*;

public class LineSeparatedMarcReader implements MarcReader {

  private BufferedReader bufferedReader = null;
  private String line = null;
  private boolean nextIsConsumed = false;
  private int lineNumber = 0;

  public LineSeparatedMarcReader(String lineSeparatedMarc) {
    try {
      bufferedReader = new BufferedReader(new FileReader(lineSeparatedMarc));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean hasNext() {
    if (lineNumber == 0 || nextIsConsumed) {
      try {
        line = bufferedReader.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
      lineNumber++;
      nextIsConsumed = false;
    }
    return (line != null);
  }

  @Override
  public Record next() {
    InputStream is = new ByteArrayInputStream(line.getBytes());
    MarcReader reader = new MarcStreamReader(is);
    Record marc4jRecord = null;
    if (reader.hasNext()) {
      marc4jRecord = reader.next();
    }
    nextIsConsumed = true;
    return marc4jRecord;
  }
}
