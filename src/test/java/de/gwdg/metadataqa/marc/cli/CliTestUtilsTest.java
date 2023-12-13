package de.gwdg.metadataqa.marc.cli;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CliTestUtilsTest {

  @Test
  public void testGetPath() {
    String expectedPath = "/src/test/resources/output".replace("/", File.separator);
    assertTrue(CliTestUtils.getPath("src/test/resources/output")
                           .contains(expectedPath));
  }

  @Test
  public void testClearOutput() throws IOException {
    String outputDir = CliTestUtils.getPath("src/test/resources/output");
    List<String> outputFiles = Arrays.asList("classifications-by-records.csv");
    File file = new File(outputDir, outputFiles.get(0));
    file.createNewFile();
    assertTrue(file.exists());
    CliTestUtils.clearOutput(outputDir, outputFiles);
    assertFalse(file.exists());
  }
}