package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.TestUtils;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class ValidatorCliTest extends CliTestUtils {

  private String outputDir;
  private List<String> outputFiles;
  private List<String> groupedOutputFiles;

  @Before
  public void setUp() throws Exception {
    outputDir = TestUtils.getPath("output");
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
    groupedOutputFiles = Arrays.asList(
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
    clearOutput(outputDir, groupedOutputFiles);

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
      TestUtils.getPath("pica/pica-with-holdings-info.dat")
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.setProcessWithErrors(true);
    iterator.start();
    assertEquals("done", iterator.getStatus());

    for (String outputFile : groupedOutputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(outputFile + " should exist", output.exists());
      List<String> lines = FileUtils.readLinesFromFile(TestUtils.getPath("output/" + outputFile));
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
    clearOutput(outputDir, groupedOutputFiles);

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
      // "/home/kiru/Documents/marc21/k10plus_pica_grouped/pica-with-holdings-info-1M.dat"
      TestUtils.getPath("pica/pica-with-holdings-info.dat")
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.setProcessWithErrors(true);
    iterator.start();
    assertEquals(iterator.getStatus(), "done");

    for (String outputFile : groupedOutputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(outputFile + " should exist", output.exists());
      List<String> lines = FileUtils.readLinesFromFile(TestUtils.getPath("output/" + outputFile));
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
        assertTrue(line.contains("qa-catalogue/src/test/resources/pica/pica-with-holdings-info.dat\"]"));
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
        assertTrue(line.contains("qa-catalogue/src/test/resources/output\","));
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
        assertTrue(line.contains("\"mqaf.version\":\"0.9.4\","));
        assertTrue(line.contains("\"qa-catalogue.version\":\"0.8.0-SNAPSHOT\""));
        assertTrue(line.contains("\"duration\":\"00:00:00\""));
        assertTrue(line.contains("\"numberOfprocessedRecords\":10"));

      } else {
        fail("Untested output file: " + outputFile);
      }

      output.delete();
      assertFalse(outputFile + " should not exist anymore", output.exists());
    }
  }

  @Test
  public void validate_alephseq() throws Exception {
    clearOutput(outputDir, groupedOutputFiles);

    ValidatorCli processor = new ValidatorCli(new String[]{
            "--schemaType", "MARC21",
            "--marcFormat", "ALEPHSEQ",
            "--marcVersion", "GENT",
            // "--alephseq",
            "--outputDir", outputDir,
            "--details",
            "--trimId",
            "--summary",
            "--format", "csv",
            "--defaultRecordType", "BOOKS",
            "--detailsFileName", "issue-details.csv",
            "--summaryFileName", "issue-summary.csv",
      TestUtils.getPath("alephseq/alephseq-example6-error.txt")
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.setProcessWithErrors(true);
    iterator.start();
    assertEquals("done", iterator.getStatus());

    for (String outputFile : outputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(outputFile + " should exist", output.exists());
      List<String> lines = FileUtils.readLinesFromFile(TestUtils.getPath("output/" + outputFile));
      if (outputFile.equals("issue-details.csv")) {
        assertEquals(6, lines.size());
        assertEquals("003141910,1:1;2:1", lines.get(1).trim());
        assertEquals("003141911,1:1;2:1", lines.get(2).trim());
        assertEquals("unknown,3:1;4:1", lines.get(3).trim());
        assertEquals("003141913,1:1;2:1", lines.get(4).trim());
        assertEquals("003141914,1:1;2:1", lines.get(5).trim());

      } else if (outputFile.equals("issue-summary.csv")) {
        assertEquals(5, lines.size());
        assertEquals("id,MarcPath,categoryId,typeId,type,message,url,instances,records", lines.get(0).trim());
        assertTrue(lines.contains("1,852,5,13,undefined subfield,4,https://www.loc.gov/marc/bibliographic/bd852.html,4,4"));
        assertTrue(lines.contains("2,852,5,13,undefined subfield,5,https://www.loc.gov/marc/bibliographic/bd852.html,4,4"));
        assertTrue(lines.contains("4,leader,1,23,parsing error,missing,,1,1"));
        assertTrue(lines.contains("3,record,1,23,parsing error,\"Leader length is not 24 char long, but 23\",,1,1"));

      } else if (outputFile.equals("issue-by-category.csv")) {
        assertEquals(3, lines.size());
        assertEquals("id,category,instances,records", lines.get(0).trim());
        assertEquals("1,record,2,1", lines.get(1).trim());
        assertEquals("5,subfield,8,4", lines.get(2).trim());

      } else if (outputFile.equals("issue-by-type.csv")) {
        assertEquals(3, lines.size());
        assertEquals("id,categoryId,category,type,instances,records", lines.get(0).trim());
        assertEquals("13,5,subfield,undefined subfield,8,4", lines.get(1).trim());
        assertEquals("23,1,record,parsing error,2,1", lines.get(2).trim());

      } else if (outputFile.equals("issue-collector.csv")) {
        assertEquals(5, lines.size());
        assertEquals("errorId,recordIds", lines.get(0).trim());
        assertEquals("1,003141910;003141911;003141913;003141914", lines.get(1).trim());
        assertEquals("2,003141910;003141911;003141913;003141914", lines.get(2).trim());
        assertEquals("3,unknown", lines.get(3).trim());
        assertEquals("4,unknown", lines.get(4).trim());

      } else if (outputFile.equals("issue-total.csv")) {
        assertEquals(3, lines.size());
        assertEquals("type,instances,records", lines.get(0).trim());
        assertEquals("1,10,5", lines.get(1).trim());
        assertEquals("2,10,5", lines.get(2).trim());

      } else if (outputFile.equals("count.csv")) {
        assertEquals(2, lines.size());
        assertEquals("total", lines.get(0).trim());
        assertEquals("5", lines.get(1).trim());

      } else if (outputFile.equals("validation.params.json")) {
        // System.err.println(lines);
        // assertEquals(4, lines.size());
      } else {
        System.err.println(outputFile + " IS NOT HANDLED");
      }

      output.delete();
      assertFalse(outputFile + " should not exist anymore", output.exists());
    }
  }

  @Test
  public void validate_whenUnimarc() throws Exception {
    clearOutput(outputDir, groupedOutputFiles);

    ValidatorCli processor = new ValidatorCli(new String[]{
        "--schemaType", "UNIMARC",
        "--marcFormat", "MARC_LINE",
        "--outputDir", outputDir,
        "--details",
        "--trimId",
        "--summary",
        "--format", "csv",
        "--defaultRecordType", "BOOKS",
        "--detailsFileName", "issue-details.csv",
        "--summaryFileName", "issue-summary.csv",
        TestUtils.getPath("unimarc/unimarc.mrctxt")
    });

    RecordIterator iterator = new RecordIterator(processor);
    iterator.setProcessWithErrors(true);
    iterator.start();

    assertEquals("done", iterator.getStatus());

    List<String> lines = getFileLines("issue-details.csv");
    assertEquals("recordId,errors", lines.get(0).trim());
    assertEquals("000000124,1:1;2:1;3:3;4:1;5:1;6:1;7:6", lines.get(1).trim());

    lines = getFileLines("issue-summary.csv");
    assertEquals(8, lines.size());

    assertEquals("id,MarcPath,categoryId,typeId,type,message,url,instances,records", lines.get(0).trim());
    assertEquals("2,005,2,6,invalid value,The field value does not match the expected pattern in '20191011224100.000',https://www.loc.gov/marc/bibliographic/bd005.html,1,1", lines.get(1).trim());
    assertEquals("1,359,3,9,undefined field,359,,1,1", lines.get(2).trim());
    assertEquals("6,410$ind2,4,12,invalid value,|,,1,1", lines.get(3).trim());
    assertEquals("7,606$ind1,4,12,invalid value, ,,6,1", lines.get(4).trim());
    assertEquals("3,035,5,13,undefined subfield,9,,3,1", lines.get(5).trim());
    assertEquals("4,181$a/01,5,22,invalid value,invalid code for 'Extent of Applicability': '#' at position 01 in 'i#',,1,1", lines.get(6).trim());
    assertEquals("5,181$b/03-05,5,22,invalid value,invalid code for 'Sensory Specification': 'e##' at position 03-05 in 'xxxe##',,1,1", lines.get(7).trim());

    lines = getFileLines("issue-by-category.csv");
    assertEquals(5, lines.size());
    assertEquals("id,category,instances,records", lines.get(0).trim());
    assertEquals("2,control field,1,1", lines.get(1).trim());
    assertEquals("3,data field,1,1", lines.get(2).trim());
    assertEquals("4,indicator,7,1", lines.get(3).trim());
    assertEquals("5,subfield,5,1", lines.get(4).trim());

    lines = getFileLines("issue-by-type.csv");
    assertEquals(6, lines.size());
    assertEquals("id,categoryId,category,type,instances,records", lines.get(0).trim());
    assertEquals("6,2,control field,invalid value,1,1", lines.get(1).trim());
    assertEquals("9,3,data field,undefined field,1,1", lines.get(2).trim());
    assertEquals("12,4,indicator,invalid value,7,1", lines.get(3).trim());
    assertEquals("13,5,subfield,undefined subfield,3,1", lines.get(4).trim());
    assertEquals("22,5,subfield,invalid value,2,1", lines.get(5).trim());

    // Won't check issue-collector.csv as it there are no record ids

    lines = getFileLines("issue-total.csv");
    assertEquals(3, lines.size());
    assertEquals("type,instances,records", lines.get(0).trim());
    assertEquals("1,14,1", lines.get(1).trim());
    assertEquals("2,13,1", lines.get(2).trim());

    lines = getFileLines("count.csv");
    assertEquals(2, lines.size());
    assertEquals("total", lines.get(0).trim());
    assertEquals("1", lines.get(1).trim());
  }

  @Test
  public void validate_whenHbz() throws Exception {
    clearOutput(outputDir, outputFiles);

    ValidatorCli processor = new ValidatorCli(new String[]{
      "--schemaType", "MARC21",
      "--marcVersion", "HBZ",
      "--marcxml",
      "--outputDir", outputDir,
      "--fixAlma",
      "--ignorableRecords", "DEL$a=Y",
      "--ignorableFields", "964,940,941,942,944,945,946,947,948,949,950,951,952,955,956,957,958,959,966,967,970,971,972,973,974,975,976,977,978,978,979",
      "--details",
      "--trimId",
      "--summary",
      TestUtils.getPath("marcxml/990082522550206441_missing_validation_custom_subfield_9_core_710.xml"),
      TestUtils.getPath("marcxml/990171082050206441_missing_validation_custom_ind2_9_core_246.xml"),
      TestUtils.getPath("marcxml/991000922029706482_missing_subfield_validation_t_in_customfield_GKT.xml"),
    });

    RecordIterator iterator = new RecordIterator(processor);
    iterator.setProcessWithErrors(true);
    iterator.start();

    List<String> lines = getFileLines("issue-summary.csv");
    System.err.println(StringUtils.join(lines, "\n"));
    assertEquals(3, lines.size());
    List<String> undefinedFields = lines.stream()
      .filter(line -> line.contains("undefined field"))
      .collect(Collectors.toList());
    assertEquals(0, undefinedFields.size());
    // Pattern pattern = Pattern.compile("^\\d+,952,\\d+,\\d+,undefined field");
    // assertTrue(pattern.matcher(undefinedFields.get(0)).find());
  }

  private List<String> getFileLines(String outputFile) throws IOException {
    File output = new File(outputDir, outputFile);
    assertTrue(outputFile + " should exist", output.exists());
    return FileUtils.readLinesFromFile(TestUtils.getPath("output/" + outputFile));
  }
}
