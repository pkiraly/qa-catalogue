package de.gwdg.metadataqa.marc.utils.marcreader;

import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.marc.Record;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LineSeparatedMarcReader extends ErrorAwareReader implements MarcReader {

  private static final Logger logger = Logger.getLogger(LineSeparatedMarcReader.class.getCanonicalName());

  private BufferedReader bufferedReader = null;
  private String line = null;
  private boolean nextIsConsumed = false;
  private int lineNumber = 0;

  public LineSeparatedMarcReader(String lineSeparatedMarc) {
    try {
      bufferedReader = new BufferedReader(new FileReader(lineSeparatedMarc));
    } catch (FileNotFoundException e) {
      logger.log(Level.WARNING, "error in LineSeparatedMarcReader()", e);
    }
  }

  public LineSeparatedMarcReader(InputStream in) {
    bufferedReader = new BufferedReader(new InputStreamReader(in));
  }

  @Override
  public boolean hasNext() {
    if (lineNumber == 0 || nextIsConsumed) {
      try {
        line = bufferedReader.readLine();
      } catch (IOException e) {
        logger.log(Level.WARNING, "error in hasNext()", e);
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
