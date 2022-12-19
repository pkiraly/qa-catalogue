package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ValidatorCliTest extends CliTestUtils {

  private String outputDir;
  private List<String> outputFiles;
  private List<String> grouppedOutputFiles;

  @Before
  public void setUp() throws Exception {
    outputDir = getPath("src/test/resources/output");
    outputFiles = Arrays.asList(
      "count.csv",
      "issue-details.csv",
      "issue-summary.csv",
      "issue-by-category.csv",
      "issue-by-type.csv",
      "issue-collector.csv",
      "issue-total.csv"
    );
    grouppedOutputFiles = Arrays.asList(
      "count.csv",
      "issue-details.csv",
      "issue-summary.csv",
      "issue-by-category.csv",
      "issue-by-type.csv",
      "issue-collector.csv",
      "issue-total.csv"
    );
  }

  @Test
  public void validate_pica_groupBy() throws Exception {
    clearOutput(outputDir, grouppedOutputFiles);

    ValidatorCli processor = new ValidatorCli(new String[]{
      "--schemaType", "PICA",
      "--groupBy", "001@$0",
      "--marcFormat", "PICA_NORMALIZED",
      "--outputDir", outputDir,
      "--details",
      "--trimId",
      "--summary",
      "--format", "csv",
      "--defaultRecordType", "BOOKS",
      "--detailsFileName", "issue-details.csv",
      "--summaryFileName", "issue-summary.csv",
      getPath("src/test/resources/pica/pica-with-holdings-info.dat")
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();

    for (String outputFile : grouppedOutputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(outputFile + " should exist", output.exists());
      if (outputFile.equals("issue-details.csv")) {
        List<String> lines = FileUtils.readLinesFromResource("output/" + outputFile);
        assertEquals(11, lines.size());
        assertEquals("010000011,1:1;2:1;3:1", lines.get(1).trim());
        assertEquals("01000002X,1:1;2:1", lines.get(2).trim());
        assertEquals("010000038,1:1;2:1", lines.get(3).trim());
        assertEquals("010000054,1:1;2:1", lines.get(4).trim());
        assertEquals("010000070,1:1;2:1", lines.get(5).trim());
        assertEquals("010000089,1:1;2:1", lines.get(6).trim());
        assertEquals("010000127,1:1;2:1", lines.get(7).trim());
        assertEquals("010000151,1:1;2:1", lines.get(8).trim());
        assertEquals("010000178,1:1;2:1;4:1", lines.get(9).trim());
        assertEquals("010000194,1:1;2:1", lines.get(10).trim());
      } else if (outputFile.equals("issue-summary.csv")) {
        List<String> lines = FileUtils.readLinesFromResource("output/" + outputFile);
        assertEquals(5, lines.size());
        assertEquals("\"id\",\"MarcPath\",\"categoryId\",\"typeId\",\"type\",\"message\",\"url\",\"instances\",\"records\"", lines.get(0).trim());
        assertTrue(lines.contains("4,041A,3,8,repetition of non-repeatable field,there are 2 instances,,1,1"));
        assertTrue(lines.contains("1,001@,3,9,undefined field,001@,,10,10"));
        assertTrue(lines.contains("2,001U,3,9,undefined field,001U,,10,10"));
        assertTrue(lines.contains("3,036F/01,3,9,undefined field,036F/01,,1,1"));
      } else if (outputFile.equals("issue-by-category.csv")) {
        List<String> lines = FileUtils.readLinesFromResource("output/" + outputFile);
        assertEquals(2, lines.size());
        assertEquals("3,data field,22,10", lines.get(1).trim());
      } else if (outputFile.equals("issue-by-type.csv")) {
        List<String> lines = FileUtils.readLinesFromResource("output/" + outputFile);
        assertEquals(3, lines.size());
        assertEquals("8,3,data field,\"repetition of non-repeatable field\",1,1", lines.get(1).trim());
        assertEquals("9,3,data field,\"undefined field\",21,10", lines.get(2).trim());
      } else if (outputFile.equals("issue-collector.csv")) {
        List<String> lines = FileUtils.readLinesFromResource("output/" + outputFile);
        assertEquals(5, lines.size());
        assertEquals("1,010000151;010000011;010000054;010000070;010000194;01000002X;010000127;010000038;010000178;010000089", lines.get(1).trim());
        assertEquals("2,010000151;010000011;010000054;010000070;010000194;01000002X;010000127;010000038;010000178;010000089", lines.get(2).trim());
        assertEquals("3,010000011", lines.get(3).trim());
        assertEquals("4,010000178", lines.get(4).trim());
      } else if (outputFile.equals("issue-total.csv")) {
        List<String> lines = FileUtils.readLinesFromResource("output/" + outputFile);
        assertEquals(3, lines.size());
        assertEquals("1,22,10", lines.get(1).trim());
        assertEquals("2,1,1", lines.get(2).trim());
      }

      // output.delete();
      // assertFalse(outputFile + " should not exist anymore", output.exists());
    }
  }

}
