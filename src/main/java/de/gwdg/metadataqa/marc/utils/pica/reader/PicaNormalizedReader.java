package de.gwdg.metadataqa.marc.utils.pica.reader;

import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.utils.pica.reader.model.PicaLine;
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

public class PicaNormalizedReader extends PicaReader {

  private static final Logger logger = Logger.getLogger(PicaNormalizedReader.class.getCanonicalName());

  private BufferedReader bufferedReader = null;
  private String line = null;
  private List<PicaLine> lines = new ArrayList<>();
  private String fieldSeparator = "\u001E";
  protected String subfieldSeparator = "\u001F";
  private String defauultSubfieldSeparator = "\u001F";

  public PicaNormalizedReader(String fileName) {
    try {
      bufferedReader = new BufferedReader(new FileReader(fileName));
    } catch (IOException e) {
      logger.log(Level.WARNING, "error in PicaReader()", e);
    }
  }

  public PicaNormalizedReader(InputStream stream, String encoding) {
    try {
      bufferedReader = new BufferedReader(new InputStreamReader(stream, encoding));
      // parseIdField();
    } catch (IOException e) {
      logger.log(Level.WARNING, "error in PicaReader()", e);
    }
  }

  @Override
  public boolean hasNext() {
    if (line == null)
      checkSubfieldSeparator();
    try {
      line = bufferedReader.readLine();
     } catch (IOException e) {
      logger.log(Level.WARNING, "error in hasNext()", e);
    }
    return (line != null);
  }

  @Override
  public Record next() {
    Record marc4jRecord = null;
    String[] fields = line.split(Pattern.quote(fieldSeparator));
    lines = new ArrayList<>();
    for (String field : fields) {
      PicaLine picaLine = new PicaLine(field, subfieldSeparator);
      if (picaLine.isValidTag())
        lines.add(picaLine);
    }
    marc4jRecord = MarcFactory.createRecordFromPica(lines, idTag, idCode, schema);

    return marc4jRecord;
  }

  public String getFieldSeparator() {
    return fieldSeparator;
  }

  private void checkSubfieldSeparator() {
    if (subfieldSeparator == null)
      subfieldSeparator = defauultSubfieldSeparator;
  }
}
