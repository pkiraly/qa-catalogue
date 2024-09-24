package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.dao.MarcLeader;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.DataSource;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.utils.QAMarcReaderFactory;
import de.gwdg.metadataqa.marc.utils.marcreader.AlephseqMarcReader;
import de.gwdg.metadataqa.marc.utils.marcreader.ErrorAwareReader;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaManager;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaReader;
import de.gwdg.metadataqa.marc.utils.unimarc.UnimarcSchemaManager;
import de.gwdg.metadataqa.marc.utils.unimarc.UnimarcSchemaReader;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.solr.client.solrj.SolrServerException;
import org.marc4j.MarcException;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

/**
 * usage:
 * java -cp target/qa-catalogue-0.1-SNAPSHOT-jar-with-dependencies.jar de.gwdg.metadataqa.marc.cli.Validator [MARC21 file]
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class RecordIterator {

  private static final Logger logger = Logger.getLogger(RecordIterator.class.getCanonicalName());
  private final BibliographicInputProcessor processor;
  private int recordNumber = 0;
  private String lastKnownId = "";
  private CommonParameters parameters;
  private String replacementInControlFields;
  private MarcVersion marcVersion;
  private MarcLeader.Type defaultRecordType;
  private DecimalFormat decimalFormat;
  // this schema attribute could be merged with the UNIMARC one
  private PicaSchemaManager picaSchema;
  private UnimarcSchemaManager unimarcSchema;
  private String status = "waits";
  private boolean processWithErrors = false;
  private long start;

  public RecordIterator(BibliographicInputProcessor processor) {
    this.processor = processor;
    status = "initialized";
  }

  public void start() {
    start = System.currentTimeMillis();
    processor.beforeIteration();
    parameters = processor.getParameters();

    marcVersion = parameters.getMarcVersion();
    defaultRecordType = parameters.getDefaultRecordType();
    replacementInControlFields = parameters.getReplacementInControlFields();
    decimalFormat = new DecimalFormat();

    setupSchema();

    if (processor.getParameters().doLog())
      logger.info("marcVersion: " + marcVersion.getCode() + ", " + marcVersion.getLabel());

    if (parameters.getDataSource().equals(DataSource.FILE)) {
      String[] inputFileNames = processor.getParameters().getArgs();
      for (String inputFileName : inputFileNames) {
        if (!processor.readyToProcess())
          break;
        processFile(inputFileName);
      }
    } else if (parameters.getDataSource().equals(DataSource.STREAM)) {
      try {
        MarcReader reader = getMarcStreamReader(processor.getParameters());
        processContent(reader, "stream");
      } catch (Exception e) {
        logger.severe(e.getLocalizedMessage());
      }
    }

    long duration = System.currentTimeMillis() - start;
    processor.afterIteration(recordNumber, duration);

    if (parameters.doLog())
      logger.log(Level.INFO, "Bye! It took: {0}", Utils.formatDuration(duration));

    status = "done";
  }

  private void processFile(String inputFileName) {
    var path = Paths.get(inputFileName);
    String fileName = path.getFileName().toString();

    if (processor.getParameters().doLog())
      logger.log(Level.INFO, "processing: {0}", fileName);

    try {
      processor.fileOpened(path);
      MarcReader reader = getMarcFileReader(processor.getParameters(), path);
      processContent(reader, fileName);
      if (processor.getParameters().doLog())
        logger.log(Level.INFO, "Finished processing file. Processed {0} records.", new Object[]{decimalFormat.format(recordNumber)});

    } catch (SolrServerException ex) {
      if (processor.getParameters().doLog())
        logger.severe(ex.toString());
      System.exit(1);
    } catch (Exception ex) {
      if (!processor.getParameters().doLog()) {
        logger.log(Level.SEVERE, "error in processFile()", ex);
        // System.exit(1);
      }

      logger.severe("Other exception: " + ex);
      ex.printStackTrace();

      for (StackTraceElement element : ex.getStackTrace()) {
        // logger.severe(element.toString());
        System.err.println(element.toString());
      }
      Throwable exa = ex;
      while (exa.getCause() != null) {
        logger.severe("cause");
        exa = exa.getCause();
        for (StackTraceElement element : exa.getStackTrace()) {
          System.err.println(element.toString());
        }
      }
      // logger.log(Level.SEVERE, "start2", ex);
      // System.exit(1);
    }
  }

  private void processContent(MarcReader reader, String fileName) {
    try {
      while (reader.hasNext()) {
        if (!processor.readyToProcess()
          || isOverLimit(processor.getParameters().getLimit(), recordNumber)) {
          break;
        }

        try {
          IteratorResponse iteratorResponse = getNextMarc4jRecord(recordNumber, lastKnownId, reader);
          recordNumber++;
          processIteratorResponse(iteratorResponse, fileName);
        } catch (MarcException ex) {
          logger.log(Level.SEVERE, "catched MarcException", ex);
        } catch (Exception ex) {
          logger.log(Level.SEVERE, "catched Exception", ex);
        }
      }
    } catch (MarcException ex) {
      String msg = String.format("Error during processing the file content." +
          " File: %s, last known record number: %s, last known record identifier: %s",
        fileName, recordNumber, lastKnownId);
      logger.log(Level.SEVERE, msg, ex);
    }
  }

  private void processIteratorResponse(IteratorResponse iteratorResponse, String fileName) {
    Record marc4jRecord = iteratorResponse.getMarc4jRecord();
    if (marc4jRecord == null) {
      return;
    }

    if (isUnderOffset(processor.getParameters().getOffset(), recordNumber)) {
      return;
    }

    if (marc4jRecord.getControlNumber() == null) {
      logger.log(Level.SEVERE, "No record number at {0}, last known ID: {1}", new Object[]{recordNumber, lastKnownId});
      if (marc4jRecord.getLeader() != null) {
        logger.severe(marc4jRecord::toString);
      }
      if (!processWithErrors) {
        return;
      }
    } else {
      lastKnownId = marc4jRecord.getControlNumber();
    }

    if (skipRecord(iteratorResponse.getMarc4jRecord())) {
      return;
    }

    try {
      processor.processRecord(marc4jRecord, recordNumber);

      // Transform the marc4j record to a bibliographic record
      BibliographicRecord bibliographicRecord = iteratorResponse.hasBlockingError()
        ? null
        : transformMarcRecord(marc4jRecord);

      try {
        if (processWithErrors) {
          processor.processRecord(bibliographicRecord, recordNumber, iteratorResponse.getErrors());
        } else if (bibliographicRecord != null) {
          processor.processRecord(bibliographicRecord, recordNumber);
        }
      } catch(Exception e) {
        logger.log(Level.SEVERE, "Problem occured at processor.processRecord()", e);
        e.printStackTrace();
      }

      if (recordNumber % 100000 == 0 && processor.getParameters().doLog()) {
        logger.log(Level.INFO, "{0}/{1} ({2})", new Object[]{
          fileName,
          decimalFormat.format(recordNumber),
          (bibliographicRecord != null ? bibliographicRecord.getId() : "unknown")});
      }

    } catch (IllegalArgumentException e) {
      extracted(recordNumber, marc4jRecord, e, "Error (illegal argument) with record '%s'. %s");
    } catch (Exception e) {
      e.printStackTrace();
      extracted(recordNumber, marc4jRecord, e, "Error (general) with record '%s'. %s");
    }
  }

  private BibliographicRecord transformMarcRecord(Record marc4jRecord) {
    if (parameters.getSchemaType().equals(SchemaType.MARC21)) {
      return MarcFactory.createFromMarc4j(marc4jRecord, defaultRecordType, marcVersion, replacementInControlFields);
    } else if (parameters.getSchemaType().equals(SchemaType.PICA)) {
      return MarcFactory.createPicaFromMarc4j(marc4jRecord, picaSchema);
    } else {
      return MarcFactory.createUnimarcFromMarc4j(marc4jRecord, defaultRecordType, unimarcSchema);
    }
  }

  private MarcReader getMarcFileReader(CommonParameters parameters, Path path) throws Exception {
    MarcReader marcReader;
    if (path.toString().endsWith(".gz")) {
      marcReader = QAMarcReaderFactory.getStreamReader(
        parameters.getMarcFormat(),
        new GZIPInputStream(new FileInputStream(path.toFile())),
        parameters);
    } else {
      marcReader = QAMarcReaderFactory.getFileReader(parameters.getMarcFormat(), path.toString(), parameters);
    }
    if (parameters.getAlephseqLineType() != null && marcReader instanceof AlephseqMarcReader) {
      ((AlephseqMarcReader) marcReader).setLineType(parameters.getAlephseqLineType());
    }
    return marcReader;
  }

  private MarcReader getMarcStreamReader(CommonParameters parameters) {
    return QAMarcReaderFactory.getStreamReader(parameters.getMarcFormat(), parameters.getStream(), parameters);
  }

  private IteratorResponse getNextMarc4jRecord(int i, String lastKnownId, MarcReader reader) {
    IteratorResponse response = new IteratorResponse();
    try {
      response.setMarc4jRecord(reader.next());
      if (reader instanceof ErrorAwareReader) {
        ErrorAwareReader errorAwareReader = (ErrorAwareReader) reader;
        response.setErrors(errorAwareReader.getErrors());
        response.hasBlockingError(errorAwareReader.hasBlockingError());
      }
    } catch (MarcException | NegativeArraySizeException | NumberFormatException e) {
      response.addError(lastKnownId, e.getLocalizedMessage());
      String msg = String.format("MARC record parsing problem at record #%d (last known ID: %s): %s",
              (i + 1), lastKnownId, e.getLocalizedMessage());
      logger.severe(msg);
    } catch (Exception e) {
      response.addError(lastKnownId, e.getLocalizedMessage());
      logger.log(Level.SEVERE, "error in getNextMarc4jRecord()", e);
    }
    return response;
  }

  private boolean skipRecord(Record marc4jRecord) {
    return processor.getParameters().hasId()
      && !marc4jRecord.getControlNumber().trim().equals(processor.getParameters().getId());
  }

  private void extracted(int i, Record marc4jRecord, Exception e, String message) {
    if (marc4jRecord.getControlNumber() == null)
      logger.log(Level.SEVERE, "No record number at {0}", i);
    if (processor.getParameters().doLog())
      logger.log(Level.SEVERE, String.format(message, marc4jRecord.getControlNumber(), e.getMessage()));
    logger.log(Level.SEVERE, "error in extracted()", e);
  }

  private static boolean isOverLimit(int limit, int i) {
    return limit > -1 && i > limit;
  }

  private static boolean isUnderOffset(int offset, int i) {
    return offset > -1 && i < offset;
  }

  private static void printHelp(Options opions) {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("java -cp qa-catalogue.jar de.gwdg.metadataqa.marc.cli.Validator [options] [file]",
      opions);
  }

  private void setupSchema() {
    // This should probably be moved to a factory of some sort if the schema managers, field definitions and field
    // implementations get refactored in a way that they inherit from common interfaces.
    // That's a bit of a long shot though, so for now we'll just keep it here.
    if (parameters.isPica()) {
      picaSchema = PicaSchemaReader.createSchemaManager(parameters.getPicaSchemaFile());
    } else if (parameters.isUnimarc()) {
      UnimarcSchemaReader unimarcSchemaReader = new UnimarcSchemaReader();
      String schemaFilePath = parameters.getPicaSchemaFile();
      if (schemaFilePath == null) {
        schemaFilePath = "src/main/resources/unimarc/avram-unimarc.json";
      }
      unimarcSchema = unimarcSchemaReader.createSchema(schemaFilePath);
    }
  }

  public String getStatus() {
    return status;
  }

  public void setProcessWithErrors(boolean processWithErrors) {
    this.processWithErrors = processWithErrors;
  }

  public long getStart() {
    return start;
  }
}
