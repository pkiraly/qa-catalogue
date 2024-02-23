package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.TestUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static de.gwdg.metadataqa.marc.cli.CliTestUtils.getTestResource;

public class FunctionalAnalysisTest {

  private String outputDir;

  @Before
  public void setUp() throws Exception {
    outputDir = TestUtils.getPath("output");
  }


//  @Test
//  public void test() throws Exception {
//    String path = getTestResource("general/0001-01.mrc");
//    Record marc4jRecord = ReadMarc.read(path).get(0);
//    FunctionalAnalysis analysis = new FunctionalAnalysis(new String[]{});
//    BibliographicRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord);
//    analysis.processRecord(marcRecord, 1);
//    FrbrFunctionLister lister = analysis.getFrbrFunctionLister();
//    assertNotNull(lister);
//    assertNotNull(lister.getHistogram().get(FRBRFunction.DiscoverySearch));
//    Counter<FunctionValue> counter = lister.getHistogram().get(FRBRFunction.DiscoverySearch);
//    assertEquals(1, counter.keys().size());
//    FunctionValue value = (FunctionValue) counter.keys().toArray()[0];
//    assertEquals(7, value.getCount());
//    assertEquals(0.015086206896551725, value.getPercentage(), 0.00001);
//  }

  @Test
  public void functionalAnalysis_whenAlephseq_runsSuccessfully() throws IOException {
    String[] args = {
      "--defaultRecordType", "BOOKS",
      "--marcFormat", "ALEPHSEQ",
      "--marcVersion", "GENT",
      "--outputDir", outputDir,
      TestUtils.getPath("alephseq/alephseq-example3.txt")
    };

    FunctionalAnalysis.main(args);

    // Now compare the output files with the expected ones
    File expectedFile = new File(getTestResource("alephseq/expected-results/functional-analysis/functional-analysis.csv"));
    File actualFile = new File(outputDir, "functional-analysis.csv");
    String expected = org.apache.commons.io.FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    String actual = org.apache.commons.io.FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);

    expectedFile = new File(getTestResource("alephseq/expected-results/functional-analysis/functional-analysis-histogram.csv"));
    actualFile = new File(outputDir, "functional-analysis-histogram.csv");

    expected = org.apache.commons.io.FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    actual = org.apache.commons.io.FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);

    expectedFile = new File(getTestResource("alephseq/expected-results/functional-analysis/functional-analysis-mapping.csv"));
    actualFile = new File(outputDir, "functional-analysis-mapping.csv");

    expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void functionalAnalysis_whenPica_runsSuccessfully() throws IOException {
    String[] args = {
      "--schemaType", "PICA",
      "--marcFormat", "PICA_PLAIN",
      "--outputDir", outputDir,
      TestUtils.getPath("pica/k10plus-sample.pica")
    };

    FunctionalAnalysis.main(args);

    // Now compare the output files with the expected ones
    File expectedFile = new File(getTestResource("pica/expected-results/functional-analysis/functional-analysis.csv"));
    File actualFile = new File(outputDir, "functional-analysis.csv");
    String expected = org.apache.commons.io.FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    String actual = org.apache.commons.io.FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);

    expectedFile = new File(getTestResource("pica/expected-results/functional-analysis/functional-analysis-histogram.csv"));
    actualFile = new File(outputDir, "functional-analysis-histogram.csv");

    expected = org.apache.commons.io.FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    actual = org.apache.commons.io.FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);

    expectedFile = new File(getTestResource("alephseq/expected-results/functional-analysis/functional-analysis-mapping.csv"));
    actualFile = new File(outputDir, "functional-analysis-mapping.csv");

    expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);
  }
}
