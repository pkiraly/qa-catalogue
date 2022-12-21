package de.gwdg.metadataqa.marc.cli;

import com.opencsv.CSVReader;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class CompletenessTest extends CliTestUtils {

  private String inputFile;
  private String outputDir;
  private List<String> outputFiles;
  private List<String> grouppedOutputFiles;

  @Before
  public void setUp() throws Exception {
    inputFile = getPath("src/test/resources/alephseq/alephseq-example3.txt");
    outputDir = getPath("src/test/resources/output");
    outputFiles = Arrays.asList(
      "libraries.csv",
      "libraries003.csv",
      "marc-elements.csv",
      "packages.csv",
      "completeness.params.json"
    );
    grouppedOutputFiles = Arrays.asList(
      "libraries.csv",
      "libraries003.csv",
      "completeness-groups.csv",
      "completeness-groupped-marc-elements.csv",
      "completeness-groupped-packages.csv",
      "completeness.params.json"
    );
  }

  @Test
  public void completeness_alephseq() throws Exception {
    clearOutput(outputDir, outputFiles);

    Completeness processor = new Completeness(new String[]{
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
      assertFalse(output.exists());
    }
  }

  @Test
  public void completeness_pica() throws Exception {
    clearOutput(outputDir, outputFiles);

    Completeness processor = new Completeness(new String[]{
      "--schemaType", "PICA",
      "--marcForma", "PICA_PLAIN",
      "--outputDir", outputDir,
      getPath("src/test/resources/pica/k10plus-sample.pica")
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();

    for (String outputFile : outputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(output.exists());
      output.delete();
      assertFalse(output.exists());
    }
  }

  @Test
  public void testRegex() {
    String a = "041$_ind1";
    assertEquals("041$ind1", a.replace("_ind", "ind"));

    String b = "041$|0";
    assertEquals("041$0", b.replaceAll("\\|(\\d)$", "$1"));
  }

  @Test
  public void completeness_pica_groupBy() throws Exception {
    clearOutput(outputDir, grouppedOutputFiles);

    Completeness processor = new Completeness(new String[]{
      "--schemaType", "PICA",
      "--groupBy", "001@$0",
      "--marcFormat", "PICA_NORMALIZED",
      "--outputDir", outputDir,
      getPath("src/test/resources/pica/pica-with-holdings-info.dat")
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();

    for (String outputFile : grouppedOutputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(outputFile + " should exist", output.exists());
      if (outputFile.equals("completeness-groupped-marc-elements.csv")) {
        CSVReader reader = new CSVReader(new FileReader(output));
        String[] record = null;
        int lineNr = 0;
        while ((record = reader.readNext()) != null) {
          if (lineNr > 0) {
            int records = Integer.parseInt(record[7]);
            int occurences = Integer.parseInt(record[8]);
            assertTrue(records <= occurences);
            int total = 0;
            for (String expr : record[13].split("; ")) {
              String[] parts = expr.split("=");
              total += Integer.parseInt(parts[0]) * Integer.parseInt(parts[1]);
            }
            assertEquals(occurences, total);
          }
          lineNr++;
        }
      }
      output.delete();
      assertFalse(outputFile + " should not exist anymore", output.exists());
    }
  }
}