package de.gwdg.metadataqa.marc.cli.processor;

import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import org.apache.commons.cli.Options;

import java.nio.file.Path;

public interface MinimalProcessor {

  void beforeIteration();
  void afterIteration(int numberOfprocessedRecords, long duration);
  void processRecord(BibliographicRecord marcRecord, int recordNumber);
  void notify(BibliographicRecord marcRecord);
  void fileOpened(Path path);
  void fileProcessed();
  void printHelp(Options options);
  boolean readyToProcess();

}
