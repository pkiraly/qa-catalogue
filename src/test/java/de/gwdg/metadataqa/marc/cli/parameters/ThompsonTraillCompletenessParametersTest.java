package de.gwdg.metadataqa.marc.cli.parameters;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ThompsonTraillCompletenessParametersTest {

  @Test
  public void testDefaults() {
    String[] arguments = new String[]{"a-marc-file.mrc"};
    try {
      ThompsonTraillCompletenessParameters parameters = new ThompsonTraillCompletenessParameters(arguments);

      assertNotNull(parameters.getArgs());
      assertEquals(1, parameters.getArgs().length);
      assertEquals("a-marc-file.mrc", parameters.getArgs()[0]);

      assertFalse(parameters.doHelp());

      assertEquals(-1, parameters.getLimit());
      assertEquals(-1, parameters.getOffset());
      assertEquals("tt-completeness.csv", parameters.getFileName());

      assertFalse(parameters.useStandardOutput());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testLimit() {
    String[] arguments = new String[]{"--limit", "3", "a-marc-file.mrc"};
    try {
      ThompsonTraillCompletenessParameters parameters = new ThompsonTraillCompletenessParameters(arguments);
      assertEquals(3, parameters.getLimit());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testOffset() {
    String[] arguments = new String[]{"--offset", "3", "a-marc-file.mrc"};
    try {
      ThompsonTraillCompletenessParameters parameters = new ThompsonTraillCompletenessParameters(arguments);
      assertEquals(3, parameters.getOffset());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testLimitAndOffset() {
    String[] arguments = new String[]{"--limit", "3", "--offset", "3", "a-marc-file.mrc"};
    try {
      ThompsonTraillCompletenessParameters parameters = new ThompsonTraillCompletenessParameters(arguments);
      assertEquals(3, parameters.getOffset());
      assertEquals(6, parameters.getLimit());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testFileName() {
    String[] arguments = new String[]{"--fileName", "3", "a-marc-file.mrc"};
    try {
      ThompsonTraillCompletenessParameters parameters = new ThompsonTraillCompletenessParameters(arguments);
      assertEquals("3", parameters.getFileName());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testFileNameStdOut() {
    String[] arguments = new String[]{"--fileName", "stdout", "a-marc-file.mrc"};
    try {
      ThompsonTraillCompletenessParameters parameters = new ThompsonTraillCompletenessParameters(arguments);
      assertEquals("stdout", parameters.getFileName());
      assertTrue(parameters.useStandardOutput());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testHelp() {
    String[] arguments = new String[]{"--help", "a-marc-file.mrc"};
    try {
      ThompsonTraillCompletenessParameters parameters = new ThompsonTraillCompletenessParameters(arguments);
      assertTrue(parameters.doHelp());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
