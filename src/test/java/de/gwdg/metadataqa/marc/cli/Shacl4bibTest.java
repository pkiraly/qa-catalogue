package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Shacl4bibTest extends CliTestUtils {

  private String inputFile;
  private String outputDir;

  @Before
  public void setUp() throws Exception {
    inputFile = getPath("src/test/resources/alephseq/alephseq-example3.txt");
    outputDir = getPath("src/test/resources/output");
  }

  @Test
  public void main() throws ParseException {
    Shacl4bib processor = new Shacl4bib(new String[]{
      "--defaultRecordType", "BOOKS",
      "--marcVersion", "GENT",
      "--alephseq",
      "--outputDir", outputDir,
      "--shaclConfigurationFile", getPath("src/test/resources/shacl/kbr-small.yaml"),
      "--shaclOutputFile", "shacl.csv",
      inputFile
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();
  }
}