package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.MarcFactory;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PicaReader implements MarcReader {

  private static final Logger logger = Logger.getLogger(PicaReader.class.getCanonicalName());

  private BufferedReader bufferedReader = null;
  private String line = null;
  private boolean nextIsConsumed = false;
  private int lineNumber = 0;
  private List<PicaLine> lines = new ArrayList<>();
  private String currentId = null;

  public PicaReader(String alephseqMarc) {
    try {
      bufferedReader = new BufferedReader(new FileReader(alephseqMarc));
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
      PicaLine alephseqLine = new PicaLine(line, lineNumber);
      if (currentId != null
        && !alephseqLine.getRecordID().equals(currentId)
        && !lines.isEmpty())
      {
        marc4jRecord = MarcFactory.createRecordFromPica(lines);
        if (marc4jRecord.getLeader() == null) {
          logger.severe(String.format(
            "Record #%s #%s does not have a leader\n",
            marc4jRecord.getControlNumberField().getData(),
            lineNumber
          ));
        } else {
          finished = true;
        }
        lines = new ArrayList<>();
      }

      if (alephseqLine.isValidTag()) {
        lines.add(alephseqLine);
      }
      currentId = alephseqLine.getRecordID();

      try {
        line = bufferedReader.readLine();
        lineNumber++;
      } catch (IOException e) {
        logger.log(Level.SEVERE, "next", e);
      }
    }
    if (line == null && !lines.isEmpty()) {
      marc4jRecord = MarcFactory.createRecordFromPica(lines);
    }
    return marc4jRecord;
  }
}
