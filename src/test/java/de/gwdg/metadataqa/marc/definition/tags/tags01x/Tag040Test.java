package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Tag040Test {

  @Test
  public void testCStH() {
    BibliographicRecord marcRecord = new Marc21BibliographicRecord("test");
    DataField field = new DataField(Tag040.getInstance(), " ", " ", "a", "CSt-H", "b", "eng", "c", "CSt-H", "e", "appm");
    field.setMarcRecord(marcRecord);

    Map<String, List<String>> map = field.getHumanReadableMap();

    assertEquals(4, map.size());
    assertEquals("Stanford University, Hoover Institution on War, Revolution, and Peace",
        map.get("Original cataloging agency").get(0));
    assertEquals("English",
      map.get("Language of cataloging").get(0));
    assertEquals("Stanford University, Hoover Institution on War, Revolution, and Peace",
        map.get("Transcribing agency").get(0));
    assertEquals("Hensen, Steven L. Archives, personal papers, and manuscripts (Washington: Library of Congress)",
        map.get("Description conventions").get(0));
  }

  @Test
  public void testMt() {
    DataField field = new DataField(Tag040.getInstance(), " ", " ", "a", "Mt", "c", "Mt");
    Map<String, List<String>> map = field.getHumanReadableMap();

    assertEquals(2, map.size());
    assertEquals("Montana State Library",
        map.get("Original cataloging agency").get(0));
    assertEquals("Montana State Library",
        map.get("Transcribing agency").get(0));
  }
}
