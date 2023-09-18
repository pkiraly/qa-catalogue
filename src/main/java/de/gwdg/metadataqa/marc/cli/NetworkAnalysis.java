package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.analysis.NetworkAnalyzer;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.NetworkAction;
import de.gwdg.metadataqa.marc.cli.parameters.NetworkParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.marc4j.marc.Record;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.gwdg.metadataqa.marc.Utils.createRow;

public class NetworkAnalysis implements BibliographicInputProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(NetworkAnalysis.class.getCanonicalName());

  private final NetworkParameters parameters;
  private final boolean readyToProcess;
  private final List<String> orphans = new ArrayList<>();
  private BufferedWriter networkWriter;

  public NetworkAnalysis(String[] args) throws ParseException {
    parameters = new NetworkParameters(args);
    readyToProcess = true;
  }

  public static void main(String[] args) {
    BibliographicInputProcessor processor = null;
    try {
      processor = new NetworkAnalysis(args);
    } catch (ParseException e) {
      System.err.println(createRow("ERROR. ", e.getLocalizedMessage()));
      System.exit(1);
    }
    NetworkParameters params = (NetworkParameters)processor.getParameters();
    NetworkAction action = params.getAction();
    logger.info("Action: " + ((NetworkParameters)processor.getParameters()).getAction());
    if (action.equals(NetworkAction.PAIRING)) {
      PairGenerator generator = new PairGenerator(params.getOutputDir(), params.getGroupLimit(),false);
      generator.generatePairs();
    } else {
      if (params.getArgs().length < 1) {
        System.err.println("Please provide a MARC file name!");
        processor.printHelp(params.getOptions());
        System.exit(0);
      }
      if (params.doHelp()) {
        processor.printHelp(params.getOptions());
        System.exit(0);
      }
      RecordIterator iterator = new RecordIterator(processor);
      iterator.start();
    }
  }

  @Override
  public CommonParameters getParameters() {
    return parameters;
  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) throws IOException {

  }

  @Override
  public void processRecord(BibliographicRecord marcRecord, int recordNumber) throws IOException {
    if (parameters.getRecordIgnorator().isIgnorable(marcRecord))
      return;

    NetworkAnalyzer analyzer = new NetworkAnalyzer(marcRecord);
    Set<DataField> collector = analyzer.process(recordNumber);
    if (!collector.isEmpty()) {
      for (DataField field : collector) {
        networkWriter.write(createRow(
          field.toString().hashCode(),
          field.getDefinition().getTag(),
          recordNumber
        ));
      }
    }
    orphans.add(marcRecord.getId(true));
  }

  @Override
  public void beforeIteration() {
    var path = Paths.get(parameters.getOutputDir(), "network.csv");
    logger.info(parameters.formatParameters());
    try {
      networkWriter = Files.newBufferedWriter(path);
      networkWriter.write(createRow("concept", "tag", "id"));
    } catch (IOException e) {
      logger.log(Level.WARNING, "document", e);
    }
  }

  @Override
  public void fileOpened(Path path) {

  }

  @Override
  public void fileProcessed() {

  }

  @Override
  public void afterIteration(int numberOfprocessedRecords) {
    try {
      networkWriter.close();
    } catch (IOException e) {
      logger.severe("Failed to close networkWriter. " + e.getLocalizedMessage());
      logger.log(Level.SEVERE, "afterIteration", e);
    }
  }

  @Override
  public void printHelp(Options options) {

  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }
}
