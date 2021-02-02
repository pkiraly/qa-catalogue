package de.gwdg.metadataqa.marc.cli.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class IgnorableFieldsTest {

  private IgnorableFields fields = new IgnorableFields();

  @Test
  public void parseFields() {
    fields.parseFields("");
    assertTrue(fields.isEmpty());

    fields.parseFields("200");
    assertFalse(fields.isEmpty());
    assertEquals(1, fields.getFields().size());

    fields.parseFields("200,201");
    assertEquals(2, fields.getFields().size());

    fields.parseFields("200;201");
    assertEquals(1, fields.getFields().size());
  }

  @Test
  public void isEmpty() {
    assertTrue(fields.isEmpty());
    fields.parseFields("");
    assertTrue(fields.isEmpty());
    fields.parseFields("200");
    assertFalse(fields.isEmpty());
  }

  @Test
  public void getFields() {
    fields.parseFields("200");
    assertFalse(fields.isEmpty());
    assertEquals(1, fields.getFields().size());
  }

  @Test
  public void contains_ifEmpty() {
    assertFalse(fields.contains("200"));
  }

  @Test
  public void contains_ifSingleton() {
    fields.parseFields("200");
    assertTrue(fields.contains("200"));
  }

  @Test
  public void contains_ifMultiple() {
    fields.parseFields("200,300");
    assertTrue(fields.contains("200"));
    assertTrue(fields.contains("300"));
  }

  @Test
  public void testToString() {
    assertEquals("", fields.toString());
  }

  @Test
  public void testToString_ifSingleton() {
    fields.parseFields("200");
    assertEquals("200", fields.toString());
  }

  @Test
  public void testToString_ifMultiple() {
    fields.parseFields("200,300");
    assertEquals("200, 300", fields.toString());
  }
}