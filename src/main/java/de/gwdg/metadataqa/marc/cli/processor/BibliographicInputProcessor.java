package de.gwdg.metadataqa.marc.cli.processor;

import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import org.apache.commons.cli.Options;
import org.marc4j.marc.Record;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface BibliographicInputProcessor {

  CommonParameters getParameters();
  void processRecord(Record marc4jRecord, int recordNumber) throws IOException;
  void processRecord(BibliographicRecord marcRecord, int recordNumber) throws IOException;
  void processRecord(BibliographicRecord marcRecord, int recordNumber, List<ValidationError> errors) throws IOException;
  void beforeIteration();
  void fileOpened(Path path);
  void fileProcessed();
  void afterIteration(int numberOfprocessedRecords, long duration);
  void printHelp(Options options);
  boolean readyToProcess();
}
