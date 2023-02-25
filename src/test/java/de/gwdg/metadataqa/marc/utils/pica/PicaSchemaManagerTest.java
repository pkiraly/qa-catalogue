package de.gwdg.metadataqa.marc.utils.pica;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.*;

public class PicaSchemaManagerTest {

  PicaSchemaManager schema;

  @Before
  public void setUp() throws Exception {
    schema = PicaSchemaReader.createSchema(getPath("pica/k10plus.json"));
  }

  @Test
  public void lookup() {
    assertNotNull(schema.lookup("244Z/01"));
    assertNotNull(schema.lookup("244Z/01"));
    assertNull(schema.lookup("244Z/001"));
    assertNotNull(schema.lookup("022A"));
    assertNotNull(schema.lookup("022A/00"));
    assertNotNull(schema.lookup("022A/01"));
    assertNull(schema.lookup("022A/02"));

    assertNotNull(schema.lookup("201A"));
    // this is found in record, but not defined
    assertNull(schema.lookup("201A/01"));
  }

  @Test
  public void lookup2() {
    assertNotNull(schema.lookup("144Z/01"));
  }

  @Test
  public void lookup_datafield_notnull() {
    PicaDataField dataField = new PicaDataField("022A", "01");
    assertNotNull(schema.lookup(dataField));
  }

  @Test
  public void lookup_datafield_null() {
    PicaDataField dataField = new PicaDataField("201A", "01");
    assertNull(schema.lookup(dataField));
  }

  private String getPath(String fileName) {
    return Paths.get("src/test/resources/" + fileName).toAbsolutePath().toString();
  }
}