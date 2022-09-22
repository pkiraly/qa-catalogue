package de.gwdg.metadataqa.marc.utils.pica;

import net.minidev.json.parser.ParseException;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class PicaVocabularyManagerTest {

  @Test
  public void constructor() throws FileNotFoundException, ParseException {
    PicaVocabularyManager manager = new PicaVocabularyManager(getPath("pica/vocabularies.json"));

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

  private String getPath(String fileName) {
    return Paths.get("src/test/resources/" + fileName).toAbsolutePath().toString();
  }

}