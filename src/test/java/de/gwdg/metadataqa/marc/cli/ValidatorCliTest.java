package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

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
      "issue-total.csv",
      "validation.params.json"
    );
    grouppedOutputFiles = Arrays.asList(
      "count.csv",
      "issue-details.csv",
      "issue-summary.csv",
      "issue-by-category.csv",
      "issue-by-type.csv",
      "issue-collector.csv",
      "issue-total.csv",
      "validation.params.json"
    );
  }

  @Test
  public void validate_pica_normal() throws Exception {
    clearOutput(outputDir, grouppedOutputFiles);

    ValidatorCli processor = new ValidatorCli(new String[]{
      "--schemaType", "PICA",
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
    assertEquals("done", iterator.getStatus());

    for (String outputFile : grouppedOutputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(outputFile + " should exist", output.exists());
      List<String> lines = FileUtils.readLinesFromFile("src/test/resources/output/" + outputFile);
      if (outputFile.equals("issue-details.csv")) {
        assertEquals(11, lines.size());
        assertEquals("010000011,1:1;2:1;3:1;4:1;5:1;6:1;7:1;8:1;9:1;10:1;11:1;12:1;13:1;14:1;15:1;16:1;17:1;18:1;19:1;20:1;21:2;22:2;23:1;24:1", lines.get(1).trim());
        assertEquals("01000002X,1:1;2:1;4:1;5:1;6:1;7:1;8:1;21:1;22:1;23:1;24:1;25:1;26:2", lines.get(2).trim());
        assertEquals("010000038,1:1;2:1;4:1;5:1;6:1;7:1;8:1;21:2;22:2;23:1;24:1;25:1;26:1;27:1;28:2", lines.get(3).trim());
        assertEquals("010000054,1:1;2:1;21:3;22:3;29:1;30:1;31:1;32:1;33:1;34:1;35:1;36:1;37:1;38:1;39:5;40:5", lines.get(4).trim());
        assertEquals("010000070,1:1;2:1;21:1;22:1;23:1;24:1;29:1;30:1;31:1;32:1;33:1;34:1;35:1;41:1;42:1;43:1;44:1;45:1;46:1;47:1;48:1;49:1", lines.get(5).trim());
        assertEquals("010000089,1:1;2:1;50:1;51:1;52:1;53:1", lines.get(6).trim());
        assertEquals("010000127,1:1;2:1;23:1;24:1", lines.get(7).trim());
        assertEquals("010000151,1:1;2:1", lines.get(8).trim());
        assertEquals("010000178,1:1;2:1;21:2;22:2;23:1;24:1;29:1;30:1;31:1;32:1;33:1;34:1;35:1;36:1;37:1;38:1;39:6;40:6;54:2;55:2;56:1", lines.get(9).trim());
        assertEquals("010000194,1:1;2:1;6:1;7:1;8:1;9:1;10:1;11:1;15:1;16:1;17:1;18:1;19:1;21:2;22:2;23:2;24:2;25:1;57:1", lines.get(10).trim());

      } else if (outputFile.equals("issue-summary.csv")) {
        assertEquals(58, lines.size());
        assertEquals("id,MarcPath,categoryId,typeId,type,message,url,instances,records", lines.get(0).trim());
        assertTrue(lines.contains("20,036F$7,5,17,repetition of non-repeatable subfield,there are 2 instances,https://format.k10plus.de/k10plushelp.pl?cmd=kat&katalog=Standard&val=4180-4189,1,1"));
        assertTrue(lines.contains("1,001@,3,9,undefined field,001@,,10,10"));
        assertTrue(lines.contains("2,001U,3,9,undefined field,001U,,10,10"));
        assertTrue(lines.contains("3,036F/01,3,9,undefined field,036F/01,,1,1"));

      } else if (outputFile.equals("issue-by-category.csv")) {
        assertEquals(3, lines.size());
        assertEquals("id,category,instances,records", lines.get(0).trim());
        assertEquals("3,data field,21,10", lines.get(1).trim());

      } else if (outputFile.equals("issue-by-type.csv")) {
        assertEquals(4, lines.size());
        assertEquals("id,categoryId,category,type,instances,records", lines.get(0).trim());
        assertEquals("9,3,data field,undefined field,21,10", lines.get(1).trim());
        assertEquals("13,5,subfield,undefined subfield,156,9", lines.get(2).trim());

      } else if (outputFile.equals("issue-collector.csv")) {
        assertEquals(58, lines.size());
        assertEquals("errorId,recordIds", lines.get(0).trim());
        assertEquals("1,010000151;010000011;010000054;010000070;010000194;01000002X;010000127;010000038;010000178;010000089", lines.get(1).trim());
        assertEquals("2,010000151;010000011;010000054;010000070;010000194;01000002X;010000127;010000038;010000178;010000089", lines.get(2).trim());
        assertEquals("3,010000011", lines.get(3).trim());
        assertEquals("4,010000011;01000002X;010000038", lines.get(4).trim());

      } else if (outputFile.equals("issue-total.csv")) {
        assertEquals(3, lines.size());
        assertEquals("type,instances,records", lines.get(0).trim());
        assertEquals("1,178,10", lines.get(1).trim());
        assertEquals("2,157,9", lines.get(2).trim());
      }

      output.delete();
      assertFalse(outputFile + " should not exist anymore", output.exists());
    }
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
      // "/home/kiru/Documents/marc21/k10plus_pica_groupped/pica-with-holdings-info-1M.dat"
      getPath("src/test/resources/pica/pica-with-holdings-info.dat")
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();
    assertEquals(iterator.getStatus(), "done");

    for (String outputFile : grouppedOutputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(outputFile + " should exist", output.exists());
      List<String> lines = FileUtils.readLinesFromFile("src/test/resources/output/" + outputFile);
      if (outputFile.equals("issue-details.csv")) {
        assertEquals(11, lines.size());
        assertEquals("recordId,errors", lines.get(0).trim());
        assertEquals("010000011,1:1;2:1;3:1;4:1;5:1;6:1;7:1;8:1;9:1;10:1;11:1;12:1;13:1;14:1;15:1;16:1;17:1;18:1;19:1;20:1;21:2;22:2;23:1;24:1", lines.get(1).trim());
        assertEquals("01000002X,1:1;2:1;4:1;5:1;6:1;7:1;8:1;21:1;22:1;23:1;24:1;25:1;26:2", lines.get(2).trim());
        assertEquals("010000038,1:1;2:1;4:1;5:1;6:1;7:1;8:1;21:2;22:2;23:1;24:1;25:1;26:1;27:1;28:2", lines.get(3).trim());
        assertEquals("010000054,1:1;2:1;21:3;22:3;29:1;30:1;31:1;32:1;33:1;34:1;35:1;36:1;37:1;38:1;39:5;40:5", lines.get(4).trim());
        assertEquals("010000070,1:1;2:1;21:1;22:1;23:1;24:1;29:1;30:1;31:1;32:1;33:1;34:1;35:1;41:1;42:1;43:1;44:1;45:1;46:1;47:1;48:1;49:1", lines.get(5).trim());
        assertEquals("010000089,1:1;2:1;50:1;51:1;52:1;53:1", lines.get(6).trim());
        assertEquals("010000127,1:1;2:1;23:1;24:1", lines.get(7).trim());
        assertEquals("010000151,1:1;2:1", lines.get(8).trim());
        assertEquals("010000178,1:1;2:1;21:2;22:2;23:1;24:1;29:1;30:1;31:1;32:1;33:1;34:1;35:1;36:1;37:1;38:1;39:6;40:6;54:2;55:2;56:1", lines.get(9).trim());
        assertEquals("010000194,1:1;2:1;6:1;7:1;8:1;9:1;10:1;11:1;15:1;16:1;17:1;18:1;19:1;21:2;22:2;23:2;24:2;25:1;57:1", lines.get(10).trim());

      } else if (outputFile.equals("issue-summary.csv")) {
        String all = StringUtils.join(lines, "\n");
        assertEquals(1049, lines.size());
        assertEquals("groupId,id,MarcPath,categoryId,typeId,type,message,url,instances,records", lines.get(0).trim());
        assertTrue(Pattern.compile("100,\\d,001@,3,9,undefined field,001@,,1,1").matcher(all).find());
        assertTrue(Pattern.compile("100,\\d,001U,3,9,undefined field,001U,,1,1").matcher(all).find());
        assertTrue(Pattern.compile("100,\\d+,044K/00-09,5,13,undefined subfield,V,https://format.k10plus.de/k10plushelp.pl\\?cmd=kat&katalog=Standard&val=5550-5559,1,1").matcher(all).find());
        assertTrue(Pattern.compile("100,\\d+,044K/00-09,5,13,undefined subfield,3,https://format.k10plus.de/k10plushelp.pl\\?cmd=kat&katalog=Standard&val=5550-5559,1,1").matcher(all).find());

      } else if (outputFile.equals("issue-by-category.csv")) {
        assertEquals(94, lines.size());
        assertEquals("groupId,id,category,instances,records", lines.get(0).trim());
        assertEquals("0,3,data field,21,10", lines.get(1).trim());
        assertEquals("0,5,subfield,157,9", lines.get(2).trim());

      } else if (outputFile.equals("issue-by-type.csv")) {
        assertEquals(99, lines.size());
        assertEquals("groupId,id,categoryId,category,type,instances,records", lines.get(0).trim());
        assertEquals("0,9,3,data field,undefined field,21,10", lines.get(1).trim());
        assertEquals("0,13,5,subfield,undefined subfield,156,9", lines.get(2).trim());

      } else if (outputFile.equals("issue-collector.csv")) {
        assertEquals(58, lines.size());
        assertEquals("errorId,recordIds", lines.get(0).trim());
        assertEquals("1,010000151;010000011;010000054;010000070;010000194;01000002X;010000127;010000038;010000178;010000089", lines.get(1).trim());
        assertEquals("2,010000151;010000011;010000054;010000070;010000194;01000002X;010000127;010000038;010000178;010000089", lines.get(2).trim());
        assertEquals("3,010000011", lines.get(3).trim());
        assertEquals("4,010000011;01000002X;010000038", lines.get(4).trim());

      } else if (outputFile.equals("issue-total.csv")) {
        assertEquals(94, lines.size());
        assertEquals("groupId,type,instances,records", lines.get(0).trim());
        assertEquals("0,1,178,10", lines.get(1).trim());
        assertEquals("0,2,157,9", lines.get(2).trim());

      } else if (outputFile.equals("count.csv")) {
        assertEquals(2, lines.size());
        assertEquals("total", lines.get(0).trim());
        assertEquals("10", lines.get(1).trim());

      } else if (outputFile.equals("validation.params.json")) {
        assertEquals(1, lines.size());
        String line = lines.get(0);
        assertTrue(line.contains("\"args\":[\""));
        assertTrue(line.contains("metadata-qa-marc/src/test/resources/pica/pica-with-holdings-info.dat\"]"));
        assertTrue(line.contains("\"marcVersion\":\"MARC21\","));
        assertTrue(line.contains("\"marcFormat\":\"PICA_NORMALIZED\","));
        assertTrue(line.contains("\"dataSource\":\"FILE\","));
        assertTrue(line.contains("\"limit\":-1,"));
        assertTrue(line.contains("\"offset\":-1,"));
        assertTrue(line.contains("\"id\":null,"));
        assertTrue(line.contains("\"defaultRecordType\":\"BOOKS\","));
        assertTrue(line.contains("\"alephseq\":false,"));
        assertTrue(line.contains("\"marcxml\":false,"));
        assertTrue(line.contains("\"lineSeparated\":false,"));
        assertTrue(line.contains("\"trimId\":true,"));
        assertTrue(line.contains("\"outputDir\":\""));
        assertTrue(line.contains("metadata-qa-marc/src/test/resources/output\","));
        assertTrue(line.contains("\"recordIgnorator\":{\"criteria\":[],\"booleanCriteria\":null,\"empty\":true},"));
        assertTrue(line.contains("\"recordFilter\":{\"criteria\":[],\"booleanCriteria\":null,\"empty\":true},"));
        assertTrue(line.contains("\"ignorableFields\":{\"fields\":null,\"empty\":true},"));
        assertTrue(line.contains("\"stream\":null,"));
        assertTrue(line.contains("\"defaultEncoding\":null,"));
        assertTrue(line.contains("\"alephseqLineType\":null,"));
        assertTrue(line.contains("\"picaIdField\":\"003@$0\","));
        assertTrue(line.contains("\"picaSubfieldSeparator\":\"$\","));
        assertTrue(line.contains("\"picaSchemaFile\":null,"));
        assertTrue(line.contains("\"picaRecordTypeField\":\"002@$0\","));
        assertTrue(line.contains("\"schemaType\":\"PICA\","));
        assertTrue(line.contains("\"groupBy\":\"001@$0\","));
        assertTrue(line.contains("\"groupListFile\":null,"));
        assertTrue(line.contains("\"detailsFileName\":\"issue-details.csv\","));
        assertTrue(line.contains("\"summaryFileName\":\"issue-summary.csv\","));
        assertTrue(line.contains("\"format\":\"COMMA_SEPARATED\","));
        assertTrue(line.contains("\"ignorableIssueTypes\":null,"));
        assertTrue(line.contains("\"pica\":true,"));
        assertTrue(line.contains("\"replacementInControlFields\":null,"));
        assertTrue(line.contains("\"marc21\":false,"));
        assertTrue(line.contains("\"mqaf.version\":\"0.9.1\","));
        assertTrue(line.contains("\"qa-catalogue.version\":\"0.7.0-rc1\"}"));

      } else {
        fail("Untested output file: " + outputFile);
      }

      output.delete();
      assertFalse(outputFile + " should not exist anymore", output.exists());
    }
  }
}
