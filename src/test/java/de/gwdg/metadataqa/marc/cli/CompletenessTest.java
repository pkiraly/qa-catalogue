package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class CompletenessTest extends CliTestUtils {

  private String inputFile;
  private String outputDir;
  private List<String> outputFiles;

  @Before
  public void setUp() throws Exception {
    inputFile = getPath("src/test/resources/alephseq/alephseq-example3.txt");
    outputDir = getPath("src/test/resources/output");
    outputFiles = Arrays.asList(
      "libraries.csv",
      "libraries003.csv",
      "marc-elements.csv",
      "packages.csv"
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
  public void testSimple() {
    Map<Integer, String> namesMap = new HashMap<>();
    namesMap.put(1, "Larry");
    namesMap.put(2, "Steve");
    namesMap.put(3, "James");

    namesMap.forEach((key, value) -> System.out.println(key + " " + value));
  }

  @Test
  public void testRegex() {
    String a = "041$_ind1";
    assertEquals("041$ind1", a.replace("_ind", "ind"));

    String b = "041$|0";
    assertEquals("041$0", b.replaceAll("\\|(\\d)$", "$1"));
  }
}