package de.gwdg.metadataqa.marc.cli.processor;

import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import org.apache.commons.cli.Options;
import org.marc4j.marc.Record;

import java.io.IOException;
import java.nio.file.Path;

public interface BibliographicInputProcessor {

  CommonParameters getParameters();
  void processRecord(Record marc4jRecord, int recordNumber) throws IOException;
  void processRecord(MarcRecord marcRecord, int recordNumber) throws IOException;
  void beforeIteration();
  void fileOpened(Path path);
  void fileProcessed();
  void afterIteration(int numberOfprocessedRecords);
  void printHelp(Options options);
  boolean readyToProcess();
}
