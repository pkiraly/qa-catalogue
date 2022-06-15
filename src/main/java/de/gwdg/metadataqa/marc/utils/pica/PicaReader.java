package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.MarcFactory;
import org.apache.commons.lang3.StringUtils;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class PicaReader implements MarcReader {

  private static final Logger logger = Logger.getLogger(PicaReader.class.getCanonicalName());

  private BufferedReader bufferedReader = null;
  private String line = null;
  private boolean nextIsConsumed = false;
  private int lineNumber = 0;
  private List<PicaLine> lines = new ArrayList<>();
  private String idField;
  private String subfieldSeparator = "$";
  private String idTag = "003@";
  private String idCode = "0";
  private boolean parsed = false;

  public PicaReader(String fileName) {
    try {
      bufferedReader = new BufferedReader(new FileReader(fileName));
    } catch (IOException e) {
      logger.log(Level.WARNING, "error in PicaReader()", e);
    }
  }

  public PicaReader(InputStream stream, String encoding) {
    try {
      bufferedReader = new BufferedReader(new InputStreamReader(stream, encoding));
    } catch (IOException e) {
      logger.log(Level.WARNING, "error in PicaReader()", e);
    }
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
    Record marc4jRecord = null;
    boolean finished = false;
    while (line != null && !finished) {
      PicaLine picaLine = new PicaLine(line, subfieldSeparator);
      if (picaLine.isSkippable() && !lines.isEmpty()) {
        if (!parsed && StringUtils.isNotEmpty(idField) && StringUtils.isNotBlank(idField))
          parseIdField();
        marc4jRecord = MarcFactory.createRecordFromPica(lines, idTag, idCode);
        finished = true;
        lines = new ArrayList<>();
      }

      if (picaLine.isValidTag()) {
        lines.add(picaLine);
      }

      try {
        line = bufferedReader.readLine();
        lineNumber++;
      } catch (IOException e) {
        logger.log(Level.SEVERE, "next", e);
      }
    } // while

    if (line == null && !lines.isEmpty()) {
      marc4jRecord = MarcFactory.createRecordFromPica(lines, idTag, idCode);
    }
    return marc4jRecord;
  }

  public PicaReader setIdField(String idField) {
    this.idField = idField;
    return this;
  }

  public PicaReader setIdCode(String idCode) {
    this.idCode = idCode;
    return this;
  }

  public PicaReader setSubfieldSeparator(String subfieldSeparator) {
    this.subfieldSeparator = subfieldSeparator;
    return this;
  }

  private void parseIdField() {
    String[] parts = idField.split(Pattern.quote(subfieldSeparator));
    idTag = parts[0];
    idCode = parts[1];
    parsed = true;
  }
}
