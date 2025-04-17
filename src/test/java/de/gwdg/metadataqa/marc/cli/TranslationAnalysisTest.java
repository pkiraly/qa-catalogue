package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.TestUtils;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.cli.utils.placename.PlaceNameNormaliser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TranslationAnalysisTest extends CliTestUtils {
  private String inputFile;
  private String outputDir;
  private List<String> outputFiles;

  @Before
  public void setUp() throws Exception {
    inputFile = TestUtils.getPath("translation/thomas-mann.xml.gz");
    System.err.println(inputFile);
    outputDir = TestUtils.getPath("output");
    outputFiles = new ArrayList<>();
    outputFiles.add(
      "translations.csv"
    );

    assertTrue(String.format("Input file %s should exist", inputFile), new File(inputFile).exists());
    for (String outputFile : outputFiles) {
      assertFalse(
        String.format("Output file %s/%s not should exist", outputDir, outputFile),
        new File(outputDir, outputFile).exists());
    }
  }

  @After
  public void tearDown() throws Exception {
    System.err.println(outputFiles);
    clearOutput(outputDir, outputFiles);
  }

  @Test
  public void testCli() throws ParseException, IOException {
    TranslationAnalysis processor = new TranslationAnalysis(new String[]{
      "--defaultRecordType", "BOOKS",
      "--marcxml",
      "--defaultEncoding", "UTF8",
      "--outputDir", outputDir,
      "--shaclConfigurationFile", TestUtils.getPath("translation/translations-shacl.yml"),
      "--shaclOutputFile", "translations.csv",
      "--translationDebugFailedRules", "245c,7004",
      "--translationPlaceNameDictionaryDir", TestUtils.getPath("translation"),
      "--translationExport", "translations-export.json",
      inputFile
    });

    outputFiles.addAll(List.of(
      "translations-deubg-245c.txt",
      PlaceNameNormaliser.UNRESOLVED_PLACE_NAMES_FILE
    ));

    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();

    for (String outputFile : outputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(outputFile + " should exist", output.exists());
      List<String> lines = FileUtils.readLinesFromFile(output.getAbsolutePath());
      if (outputFile.equals("translations.csv")) {
        assertEquals(353, lines.size());
        assertEquals("id,00835-37,041ind1,041a,041h,245c,7004,500a,240a,240l,765t,765s,765d,translator,sourceLanguage,targetLanguage,originalTitle,originalPublication,translation", lines.get(0).trim());
        assertEquals("15552,1,1,1,0,1,NA,0,1,0,0,0,0,1,0,1,1,0,1", lines.get(1).trim());
      } else if (outputFile.equals(PlaceNameNormaliser.UNRESOLVED_PLACE_NAMES_FILE)) {
        // System.err.println(StringUtils.join(lines, "\n"));
        assertEquals(5, lines.size());
        assertEquals("resolved: 290, unresolved: 5", lines.get(0).trim());
        assertEquals("UNKNOWN: 2", lines.get(1).trim());
        assertEquals("Arnheim: 1", lines.get(2).trim());
        assertEquals("United States: 1", lines.get(3).trim());
        assertEquals("University, Ala.: 1", lines.get(4).trim());
      }
    }

    // System.err.println(Arrays.asList(listDir(new File(outputDir))));
  }
}
