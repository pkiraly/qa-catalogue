package de.gwdg.metadataqa.marc;

import java.nio.file.Paths;

public class TestUtils {

  protected static String getFullPath(String path) {
    return Paths.get(path).toAbsolutePath().toString();
  }

  public static String getPath(String fileName) {
    return getFullPath("src/test/resources/" + fileName);
  }

  public static String getTestResource(String fileName) {
    return getFullPath("src/test/resources/" + fileName);
  }

  public static String getPathFromMain(String fileName) {
    return getFullPath("src/main/resources/" + fileName);
  }
}
