package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class Shacl4bibTest extends CliTestUtils {

  private String inputFile;
  private String outputDir;
  private List<String> outputFiles;

  @Before
  public void setUp() throws Exception {
    inputFile = getPath("src/test/resources/alephseq/alephseq-example3.txt");
    outputDir = getPath("src/test/resources/output");
    outputFiles = Arrays.asList(
      "shacl.csv"
    );
  }

  @Test
  public void contructor_kbr() throws ParseException, IOException {
    clearOutput(outputDir, outputFiles);

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

    for (String outputFile : outputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(outputFile + " should exist", output.exists());
      List<String> lines = FileUtils.readLinesFromFile(output.getAbsolutePath());
      if (outputFile.equals("shacl.csv")) {
        assertEquals(2, lines.size());
        assertEquals("id,040$a.minCount,040$a.pattern", lines.get(0).trim());
        assertEquals("000000002,1,0", lines.get(1).trim());
      }
    }
  }

  @Test
  public void main() throws ParseException, IOException {
    clearOutput(outputDir, outputFiles);

    Shacl4bib.main(new String[]{
      "--defaultRecordType", "BOOKS",
      "--marcVersion", "GENT",
      "--alephseq",
      "--outputDir", outputDir,
      "--shaclConfigurationFile", getPath("src/test/resources/shacl/kbr-small.yaml"),
      "--shaclOutputFile", "shacl.csv",
      inputFile
    });

    for (String outputFile : outputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(outputFile + " should exist", output.exists());
      List<String> lines = FileUtils.readLinesFromFile(output.getAbsolutePath());
      if (outputFile.equals("shacl.csv")) {
        assertEquals(2, lines.size());
        assertEquals("id,040$a.minCount,040$a.pattern", lines.get(0).trim());
        assertEquals("000000002,1,0", lines.get(1).trim());
      }
    }
  }

  @Test
  public void main_json() throws ParseException, IOException {
    clearOutput(outputDir, outputFiles);

    Shacl4bib.main(new String[]{
      "--defaultRecordType", "BOOKS",
      "--marcVersion", "GENT",
      "--alephseq",
      "--outputDir", outputDir,
      "--shaclConfigurationFile", getPath("src/test/resources/shacl/kbr-small.json"),
      "--shaclOutputFile", "shacl.csv",
      inputFile
    });

    for (String outputFile : outputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(outputFile + " should exist", output.exists());
      List<String> lines = FileUtils.readLinesFromFile(output.getAbsolutePath());
      if (outputFile.equals("shacl.csv")) {
        assertEquals(2, lines.size());
        assertEquals("id,040$a.1,040$a.2", lines.get(0).trim());
        assertEquals("000000002,1,0", lines.get(1).trim());
      }
    }
  }

  @Test
  public void regex_1() {
    String text = "116 p.";
    Pattern pattern = Pattern.compile("^.*\\d+ p\\.?\\s*$");
    assertTrue("should fit", pattern.matcher(text).matches());
  }

  @Test
  public void regex_2() {
    String text = "116 € p.";
    Pattern pattern = Pattern.compile("^.*€.*$");
    assertTrue("should fit", pattern.matcher(text).matches());
  }
}