package de.gwdg.metadataqa.marc.cli.parameters;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class FormatterParametersTest {

  @Test
  public void testDefaults() {
    String[] arguments = new String[]{"a-marc-file.mrc"};
    try {
      FormatterParameters parameters = new FormatterParameters(arguments);

      assertNotNull(parameters.getArgs());
      assertEquals(1, parameters.getArgs().length);
      assertEquals("a-marc-file.mrc", parameters.getArgs()[0]);

      assertFalse(parameters.doHelp());

      assertNull(parameters.getId());

      assertNull(parameters.getFormat());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testHelp() {
    String[] arguments = new String[]{"--help", "a-marc-file.mrc"};
    try {
      FormatterParameters parameters = new FormatterParameters(arguments);
      assertTrue(parameters.doHelp());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testId() {
    String[] arguments = new String[]{"--id", "xyz", "a-marc-file.mrc"};
    try {
      FormatterParameters parameters = new FormatterParameters(arguments);

      assertNotNull(parameters.getId());
      assertEquals("xyz", parameters.getId());

    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testFormat() {
    String[] arguments = new String[]{"--format", "xyz", "a-marc-file.mrc"};
    try {
      FormatterParameters parameters = new FormatterParameters(arguments);

      assertNotNull(parameters.getFormat());
      assertEquals("xyz", parameters.getFormat());

    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testSearch() {
    String[] arguments = new String[]{"--search", "920$a=book", "a-marc-file.mrc"};
    try {
      FormatterParameters parameters = new FormatterParameters(arguments);

      assertNotNull(parameters.getSearch());
      assertTrue(parameters.hasSearch());
      assertEquals("920$a=book", parameters.getSearch());
      assertEquals("920$a", parameters.getPath());
      assertEquals("book", parameters.getQuery());

    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testSearchWithSpace() {
    String[] arguments = new String[]{"--search", "920$a=color book", "a-marc-file.mrc"};
    try {
      FormatterParameters parameters = new FormatterParameters(arguments);

      assertNotNull(parameters.getSearch());
      assertTrue(parameters.hasSearch());
      assertEquals("920$a=color book", parameters.getSearch());
      assertEquals("920$a", parameters.getPath());
      assertEquals("color book", parameters.getQuery());

    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
