package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.TestUtils;
import de.gwdg.metadataqa.marc.analysis.ShelfReadyFieldsBooks;
import de.gwdg.metadataqa.marc.dao.record.PicaRecord;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static de.gwdg.metadataqa.marc.cli.CliTestUtils.getTestResource;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ShelfReadyCompletenessTest {

  private String outputDir;

  @Before
  public void setUp() throws Exception {
    outputDir = TestUtils.getPath("output");
  }

  @Test
  public void getShelfReadyMap() {
    PicaRecord record = new PicaRecord();
    Map<ShelfReadyFieldsBooks, Map<String, List<String>>> map = record.getShelfReadyMap();
    assertNotNull(map);
    assertEquals(34, map.size());
    assertEquals(1, map.get(ShelfReadyFieldsBooks.TAG250).size());
    assertTrue(map.get(ShelfReadyFieldsBooks.TAG250).containsKey("032@"));
    assertEquals(List.of("a", "h"), map.get(ShelfReadyFieldsBooks.TAG250).get("032@"));
  }

  @Test
  public void shelfReadyCompleteness_whenAlephseqExample2Record_runsSuccessfully() throws IOException {
    String[] args = {
        "--defaultRecordType", "BOOKS",
        "--marcFormat", "ALEPHSEQ",
        "--marcVersion", "GENT",
        "--outputDir", outputDir,
        TestUtils.getPath("alephseq/alephseq-example2.txt")
    };

    ShelfReadyCompleteness.main(args);

    // Now compare the output files with the expected ones
    File expectedFile = new File(getTestResource("alephseq/expected-results/shelf-ready-completeness/alephseq-example2/shelf-ready-completeness.csv"));
    File actualFile = new File(outputDir, "shelf-ready-completeness.csv");
    String expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    String actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);

    expectedFile = new File(getTestResource("alephseq/expected-results/shelf-ready-completeness/alephseq-example2/shelf-ready-completeness-fields.csv"));
    actualFile = new File(outputDir, "shelf-ready-completeness-fields.csv");

    expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);
  }


  @Test
  public void shelfReadyCompleteness_whenAlephseqExample3Record_runsSuccessfully() throws IOException {
    String[] args = {
        "--defaultRecordType", "BOOKS",
        "--marcFormat", "ALEPHSEQ",
        "--marcVersion", "GENT",
        "--outputDir", outputDir,
        TestUtils.getPath("alephseq/alephseq-example3.txt")
    };

    ShelfReadyCompleteness.main(args);

    // Now compare the output files with the expected ones
    File expectedFile = new File(getTestResource("alephseq/expected-results/shelf-ready-completeness/alephseq-example3/shelf-ready-completeness.csv"));
    File actualFile = new File(outputDir, "shelf-ready-completeness.csv");
    String expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    String actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);

    expectedFile = new File(getTestResource("alephseq/expected-results/shelf-ready-completeness/alephseq-example3/shelf-ready-completeness-fields.csv"));
    actualFile = new File(outputDir, "shelf-ready-completeness-fields.csv");

    expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void shelfReadyCompleteness_whenPicaPlainRecord_runsSuccessfully() throws IOException {
    String[] args = {
        "--schemaType", "PICA",
        "--marcFormat", "PICA_PLAIN",
        "--outputDir", outputDir,
        TestUtils.getPath("pica/k10plus-sample.pica")
    };

    ShelfReadyCompleteness.main(args);

    // Now compare the output files with the expected ones
    File expectedFile = new File(getTestResource("pica/expected-results/shelf-ready-completeness/shelf-ready-completeness.csv"));
    File actualFile = new File(outputDir, "shelf-ready-completeness.csv");
    String expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    String actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);

    expectedFile = new File(getTestResource("pica/expected-results/shelf-ready-completeness/shelf-ready-completeness-fields.csv"));
    actualFile = new File(outputDir, "shelf-ready-completeness-fields.csv");

    expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);
  }
}