package de.gwdg.metadataqa.marc.analysis;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShelfReadyFieldsBooksTest {

  @Test
  public void test() {
    assertEquals(2, ShelfReadyFieldsBooks.TAG015.getSelectors().size());
    assertEquals("015", ShelfReadyFieldsBooks.TAG015.getSelectors().get(0).getFieldTag());
    assertEquals(5, ShelfReadyFieldsBooks.TAG015.getScore());
    assertEquals("National Bibliography Number", ShelfReadyFieldsBooks.TAG015.getLabel());
    assertEquals("015$a,015$2", ShelfReadyFieldsBooks.TAG015.getMarcPath());
    assertEquals(null, ShelfReadyFieldsBooks.TAG015.getValue());
    assertEquals(null, ShelfReadyFieldsBooks.TAG015.getValueLabel());
  }
}
