package de.gwdg.metadataqa.marc.utils.pica;

import net.minidev.json.parser.ParseException;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class PicaVocabularyManagerTest {

  @Test
  public void name() throws FileNotFoundException, ParseException {
    PicaVocabularyManager manager = new PicaVocabularyManager(getPath("pica/vocabularies.json"));

  }

  private String getPath(String fileName) {
    return Paths.get("src/test/resources/" + fileName).toAbsolutePath().toString();
  }

}