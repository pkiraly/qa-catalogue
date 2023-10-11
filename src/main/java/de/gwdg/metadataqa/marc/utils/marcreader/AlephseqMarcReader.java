package de.gwdg.metadataqa.marc.utils.marcreader;

import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.cli.utils.IteratorResponse;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.utils.alephseq.AlephseqLine;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import javax.xml.transform.ErrorListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlephseqMarcReader extends ErrorAwareReader implements MarcReader {

  private static final Logger logger = Logger.getLogger(AlephseqMarcReader.class.getCanonicalName());

  private enum LEVEL {
    WARN, SEVERE
  }

  private BufferedReader bufferedReader = null;
  private String line = null;
  private boolean nextIsConsumed = false;
  private int lineNumber = 0;
  private int skippedRecords = 0;
  private List<AlephseqLine> lines = new ArrayList<>();
  private String currentId = null;

  private AlephseqLine.TYPE lineType = AlephseqLine.TYPE.WITH_L;

  public AlephseqMarcReader(String alephseqMarc) {
    try {
      bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(alephseqMarc), StandardCharsets.UTF_8));
    } catch (IOException e) {
      logger.log(Level.WARNING, "AlephseqMarcReader", e);
    }
  }

  public AlephseqMarcReader(String alephseqMarc, AlephseqLine.TYPE lineType) {
    this(alephseqMarc);
    this.lineType = lineType;
  }

  public AlephseqMarcReader(InputStream stream) {
    bufferedReader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
  }

  @Override
  public boolean hasNext() {
    if (lineNumber == 0 || nextIsConsumed) {
      try {
        line = bufferedReader.readLine();
      } catch (IOException e) {
        logger.log(Level.WARNING, "hasNext", e);
      }
      lineNumber++;
      nextIsConsumed = false;
    }
    return (line != null);
  }

  @Override
  public Record next() {
    Record marc4jRecord = null;
    errors = new ArrayList<>();
    boolean deleted = false;
    boolean finished = false;
    while (line != null && !finished) {
      AlephseqLine alephseqLine = new AlephseqLine(line, lineNumber, lineType);
      String recordID = alephseqLine.getRecordID();
      if (recordID == null) {
        logger.log(Level.WARNING, "line {0}) does not have line number: \"{1}\"", new Object[]{lineNumber, line});
      } else {
        if (currentId != null
          && !recordID.equals(currentId)
          && !lines.isEmpty())
        {
          if (deleted) {
            logSkipped(LEVEL.WARN, "has been deleted");
            deleted = false;
          } else {
            IteratorResponse response = MarcFactory.createRecordFromAlephseq(lines);
            marc4jRecord = response.getMarc4jRecord();
            if (marc4jRecord.getControlNumber() == null) {
              response.addError(currentId, "001", "missing");
              logSkipped("does not have a control number field (001)");
            } else if (marc4jRecord.getLeader() == null) {
              response.addError(currentId, "leader", "missing");
              logSkipped("does not have a leader");
            } else {
              finished = true;
            }
            errors.addAll(response.getErrors());
          }
          lines = new ArrayList<>();
        }

        if (alephseqLine.isValidTag()) {
          lines.add(alephseqLine);
        } else if (alephseqLine.getTag().equals("DEL")) {
          deleted = true;
        }
        currentId = alephseqLine.getRecordID();
      }

      try {
        line = bufferedReader.readLine();
        lineNumber++;
      } catch (IOException e) {
        logger.log(Level.WARNING, "next", e);
      }
    }
    if (line == null && !lines.isEmpty()) {
      IteratorResponse response = MarcFactory.createRecordFromAlephseq(lines);
      marc4jRecord = response.getMarc4jRecord();
    }
    return marc4jRecord;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public int getSkippedRecords() {
    return skippedRecords;
  }

  private void logSkipped(String message) {
    logSkipped(LEVEL.SEVERE, message);
  }

  private void logSkipped(LEVEL level, String message) {
    String entry = String.format(
      "line #%d: record id: %s %s. Skipped.",
      lineNumber, currentId, message
    );

    if (level.equals(LEVEL.WARN)) {
      // logger.warning(entry);
    } else {
      logger.severe(entry);
    }

    skippedRecords++;
  }

  public void setLineType(AlephseqLine.TYPE lineType) {
    this.lineType = lineType;
  }
}
