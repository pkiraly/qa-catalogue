package de.gwdg.metadataqa.marc.cli;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Version {
  private static String version;

  public Version() {
  }

  public static void main(String[] args) {
    System.err.println(getVersion());
  }

  public static String getVersion() {
    if (version == null) {
      initialize();
    }

    return version;
  }

  private static void initialize() {
    String versionCandidate = de.gwdg.metadataqa.marc.cli.Version.class.getPackage().getImplementationVersion();
    if (versionCandidate != null) {
      version = versionCandidate;
    } else {
      version = readVersionFromPropertyFile();
    }

  }

  public static String readVersionFromPropertyFile() {
    String path = "/qa-catalogue.version.prop";
    InputStream stream = de.gwdg.metadataqa.marc.cli.Version.class.getResourceAsStream(path);
    if (stream == null) {
      return "UNKNOWN";
    } else {
      Properties props = new Properties();
      try {
        props.load(stream);
        stream.close();
        return (String)props.get("version");
      } catch (IOException e) {
        return "UNKNOWN";
      }
    }
  }
}
