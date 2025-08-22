package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.TestUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ThompsonTraillCompletenessTest extends CliTestUtils {

  private String outputDir;
  private List<String> outputFiles;

  @Before
  public void setUp() throws Exception {
    outputDir = TestUtils.getPath("output");
    outputFiles = Arrays.asList(
        "tt-completeness.csv",
        "tt-completeness-fields.csv"
    );
  }


  @Test
  public void whenAlephSeq_runsSuccessfully() throws IOException {
    clearOutput(outputDir, outputFiles);

    String[] args = {
        "--defaultRecordType", "BOOKS",
        "--marcVersion", "GENT",
        "--alephseq",
        "--outputDir", outputDir,
        TestUtils.getPath("alephseq/alephseq-example3.txt")
    };

    ThompsonTraillCompleteness.main(args);

    // Now compare the output files with the expected ones
    File expectedFile = new File(getTestResource("alephseq/expected-results/tt-completeness/tt-completeness.csv"));
    File actualFile = new File(outputDir, "tt-completeness.csv");
    String expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    String actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);

    expectedFile = new File(getTestResource("alephseq/expected-results/tt-completeness/tt-completeness-fields.csv"));
    actualFile = new File(outputDir, "tt-completeness-fields.csv");

    expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void whenPica_runsSuccessfully() throws IOException {
    clearOutput(outputDir, outputFiles);

    String[] args = {
        "--schemaType", "PICA",
        "--marcFormat", "PICA_PLAIN",
        "--outputDir", outputDir,
        TestUtils.getPath("pica/k10plus-sample.pica")
    };

    ThompsonTraillCompleteness.main(args);

    // Now compare the output files with the expected ones
    File expectedFile = new File(getTestResource("pica/expected-results/tt-completeness/tt-completeness.csv"));
    File actualFile = new File(outputDir, "tt-completeness.csv");
    String expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    String actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);

    expectedFile = new File(getTestResource("pica/expected-results/tt-completeness/tt-completeness-fields.csv"));
    actualFile = new File(outputDir, "tt-completeness-fields.csv");

    expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void whenUnimarc_runsSuccessfully() throws IOException {
    clearOutput(outputDir, outputFiles);

    String[] args = {
        "--schemaType", "UNIMARC",
        "--marcFormat", "MARC_LINE",
        "--outputDir", outputDir,
        "--defaultRecordType", "BOOKS",
        TestUtils.getPath("unimarc/unimarc.mrctxt")
    };

    ThompsonTraillCompleteness.main(args);

    // Now compare the output files with the expected ones
    File expectedFile = new File(getTestResource("unimarc/expected-results/tt-completeness/tt-completeness.csv"));
    File actualFile = new File(outputDir, "tt-completeness.csv");
    String expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    String actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);

    expectedFile = new File(getTestResource("unimarc/expected-results/tt-completeness/tt-completeness-fields.csv"));
    actualFile = new File(outputDir, "tt-completeness-fields.csv");

    expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);
  }
}
