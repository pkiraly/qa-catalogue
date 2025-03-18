package de.gwdg.metadataqa.marc.cli.utils.placename;

import de.gwdg.metadataqa.marc.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.text.Normalizer;
import java.util.List;

import static org.junit.Assert.*;

public class PlaceNameNormaliserTest {
  private String translationPlaceNameDictionary = TestUtils.getPath("translation");
  private String outputDir = TestUtils.getPath("output");
  private PlaceNameNormaliser normaliser;

  @Before
  public void setUp() throws Exception {
    normaliser = new PlaceNameNormaliser(translationPlaceNameDictionary, outputDir);
  }

  @Test
  public void constructor() {
    assertNotNull(normaliser.getCoords());
    assertEquals(1831, normaliser.getCoords().size());
    assertEquals(2624886, normaliser.getCoords().get("Aalborg").getGeoid());

    assertNotNull(normaliser.getSynonyms());
    assertEquals(8274, normaliser.getSynonyms().size());
    assertEquals(List.of("Alsólendva"), normaliser.getSynonyms().get("Alsólindva"));
  }

  @Test
  public void resolve() {
    assertEquals(List.of(normaliser.getCoords().get("Alsólendva")), normaliser.resolve("Alsólindva"));
  }

  @Test
  public void normalizer() {
    assertEquals("Łódź", Normalizer.normalize("Łódź", Normalizer.Form.NFKC));
  }
}