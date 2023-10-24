package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.analysis.ShelfReadyFieldsBooks;
import de.gwdg.metadataqa.marc.dao.record.PicaRecord;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ShelfReadyCompletenessTest {

  @Test
  public void getShelfReadyMap() {
    PicaRecord record = new PicaRecord();
    Map<ShelfReadyFieldsBooks, Map<String, List<String>>> map = record.getShelfReadyMap();
    assertNotNull(map);
    assertEquals(34, map.size());
    assertEquals(1, map.get(ShelfReadyFieldsBooks.TAG250).size());
    assertTrue(map.get(ShelfReadyFieldsBooks.TAG250).containsKey("032@"));
    assertEquals(List.of("a", "h"), map.get(ShelfReadyFieldsBooks.TAG250).get("032@"));
  }
}