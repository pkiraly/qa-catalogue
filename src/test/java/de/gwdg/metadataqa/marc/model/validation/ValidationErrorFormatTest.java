package de.gwdg.metadataqa.marc.model.validation;

import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ValidationErrorFormatTest {

  @Test
  public void byFormat() {
    assertEquals(ValidationErrorFormat.COMMA_SEPARATED, ValidationErrorFormat.byFormat("comma-separated"));
  }

  @Test
  public void getName() {
    assertEquals("csv", ValidationErrorFormat.COMMA_SEPARATED.getName());
  }

  @Test
  public void getNames() {
    assertEquals(List.of("csv", "comma-separated"), ValidationErrorFormat.COMMA_SEPARATED.getNames());
  }

  @Test
  public void getLabel() {
    assertEquals("comma separated", ValidationErrorFormat.COMMA_SEPARATED.getLabel());
  }

  @Test
  public void values() {
    assertEquals(4, ValidationErrorFormat.values().length);
  }

  @Test
  public void valueOf() {
    assertEquals(ValidationErrorFormat.COMMA_SEPARATED, ValidationErrorFormat.valueOf("COMMA_SEPARATED"));
  }
}