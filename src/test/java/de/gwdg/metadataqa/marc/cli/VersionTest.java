package de.gwdg.metadataqa.marc.cli;

import org.junit.Test;

import static org.junit.Assert.*;

public class VersionTest {

  private static final String VERSION = "0.8.0-SNAPSHOT";

  @Test
  public void getVersion() {
    assertEquals(VERSION, Version.getVersion());
  }

  @Test
  public void readVersionFromPropertyFile() {
    assertEquals(VERSION, Version.readVersionFromPropertyFile());
  }
}