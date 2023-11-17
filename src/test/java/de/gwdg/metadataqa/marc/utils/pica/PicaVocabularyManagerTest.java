package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.TestUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PicaVocabularyManagerTest {

  @Test
  public void constructor() {
    PicaVocabularyManager manager = PicaVocabularyManager.getInstance(TestUtils.getPathFromMain("pica/vocabularies.json"));

    VocabularyEntry entry = manager.get("045A");
    assertNotNull(entry);
    VocabularyPattern sourcePattern = entry.getSource();
    assertNotNull(sourcePattern);
    assertFalse(sourcePattern.fitsSubfield("a"));

    VocabularyPattern idPattern = entry.getId();
    assertNotNull(idPattern);
    assertTrue(idPattern.fitsSubfield("a"));
    assertEquals("PG5681.B7", idPattern.extract("PG5681.B7"));
  }
}