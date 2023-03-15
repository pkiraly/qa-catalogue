package de.gwdg.metadataqa.marc.utils.marcreader;

import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.utils.alephseq.MarcMakerLine;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

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

public class MarcMakerReader implements MarcReader {

  private static final Logger logger = Logger.getLogger(MarcMakerReader.class.getCanonicalName());

  private enum LEVEL {
    WARN, SEVERE
  };

  private BufferedReader bufferedReader = null;
  private String line = null;
  private boolean nextIsConsumed = false;
  private int lineNumber = 0;
  private int skippedRecords = 0;
  private List<MarcMakerLine> lines = new ArrayList<>();
  private String currentId = null;

  public MarcMakerReader(String content) {
    try {
      bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(content), StandardCharsets.UTF_8));
    } catch (IOException e) {
      logger.log(Level.WARNING, "MarcMakerReader", e);
    }
  }

  public MarcMakerReader(InputStream stream) {
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
    boolean deleted = false;
    boolean finished = false;
    while (line != null && !finished) {
      MarcMakerLine marcMakerLine = new MarcMakerLine(line, lineNumber);
      if (marcMakerLine.isLeader() && !lines.isEmpty()) {
        marc4jRecord = MarcFactory.createRecordFromMarcMaker(lines);
        if (marc4jRecord.getControlNumber() == null) {
          logSkipped("does not have a control number field (001)");
        } else if (marc4jRecord.getLeader() == null) {
          logSkipped("does not have a leader");
        } else {
          finished = true;
        }
        lines = new ArrayList<>();
      }

      if (!marcMakerLine.isLeader() && marcMakerLine.getTag() == null) {
        lines.get(lines.size() - 1).appendContent(marcMakerLine.getContent());
      } else {
        if (marcMakerLine.isValidTag()) {
          lines.add(marcMakerLine);
        }
        currentId = marcMakerLine.getRecordID();
      }

      try {
        line = bufferedReader.readLine();
        lineNumber++;
      } catch (IOException e) {
        logger.log(Level.WARNING, "next", e);
      }
    }
    if (line == null && !lines.isEmpty()) {
      marc4jRecord = MarcFactory.createRecordFromMarcMaker(lines);
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
    logSkipped(MarcMakerReader.LEVEL.SEVERE, message);
  }

  private void logSkipped(MarcMakerReader.LEVEL level, String message) {
    String entry = String.format(
      "line #%d: record %s %s. Skipped.",
      lineNumber, currentId, message
    );

    if (level.equals(MarcMakerReader.LEVEL.WARN)) {
      // logger.warning(entry);
    } else {
      logger.severe(entry);
    }

    skippedRecords++;
  }
}
