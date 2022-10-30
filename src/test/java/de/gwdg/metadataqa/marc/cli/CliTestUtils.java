package de.gwdg.metadataqa.marc.cli;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class CliTestUtils {

  protected static String getPath(String path) {
    return Paths.get(path).toAbsolutePath().toString();
  }

  public static String getTestResource(String fileName) {
    return getPath("src/test/resources/" + fileName);
  }

  protected static void clearOutput(String outputDir, List<String> outputFiles) {
    for (String outputFile : outputFiles)
      deleteFile(new File(outputDir, outputFile));

    // deleteFile(new File(outputDir));
  }

  private static void deleteFile(File file) {
    if (file.exists())
      if (!file.delete())
        System.err.format("File/directory %s hasn't been deleted!\n", file);
  }
}
