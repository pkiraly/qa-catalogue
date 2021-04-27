package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.utils.ReadMarc;
import org.junit.Test;
import org.marc4j.marc.Record;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class ClassificationAnalysisTest {

  @Test
  public void test() throws Exception {
    Path path = FileUtils.getPath("general/0001-01.mrc");
    Record marc4jRecord = ReadMarc.read(path.toString()).get(0);
    ClassificationAnalysis analysis = new ClassificationAnalysis(new String[]{});
    MarcRecord record = MarcFactory.createFromMarc4j(marc4jRecord);
    analysis.processRecord(record, 1);
  }

  @Test
  public void testFullProcess() throws Exception {
    String outputDir = Paths.get("src/test/resources/output")
      .toAbsolutePath().toString();

    List<String> outputFiles = Arrays.asList("classifications-by-records.csv",
      "classifications-by-schema.csv", "classifications-by-schema-subfields.csv",
      "classifications-collocations.csv", "classifications-histogram.csv");
    for (String outputFile : outputFiles) {
      File output = new File(outputDir, outputFile);
      if (output.exists())
        output.delete();
    }

    String inputFile = Paths.get("src/test/resources/alephseq/alephseq-example3.txt")
                  .toAbsolutePath().toString();

    ClassificationAnalysis processor = new ClassificationAnalysis(new String[]{
      "--defaultRecordType", "BOOKS",
      "--marcVersion", "GENT",
      "--alephseq",
      "--outputDir", outputDir,
      inputFile
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();

    for (String outputFile : outputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(output.exists());
      output.delete();
    }
  }
}
