package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.DataField;
import org.junit.Test;

public class Tag950Test extends BLTagTest {

  public Tag950Test() {
    super(Tag950.getInstance());
  }

  @Test
  public void testValidFields() {
    validField(new DataField(tag, " ", " ", "a", "Mudia (Indic people)",
      "s", "See",
      "a", "Muria (Indic people)"));
    validField(new DataField(tag, " ", " ", "a", "Yanadi (Indic people)",
      "s", "See also",
      "a", "Nakkala (Indic people)"));
    validField(new DataField(tag, " ", " ", "a", "Ethiopian-Italian War, 1935-1936",
      "s", "See",
      "a", "Italo-Ethiopian War, 1935-1936"));
    validField(new DataField(tag, " ", " ", "a", "Italy",
      "x", "History",
      "y", "War in Ethiopia, 1935-1936",
      "s", "See",
      "a", "Italo-Ethiopian War, 1935-1936"));
    validField(new DataField(tag, " ", " ", "a", "African Americans",
      "s", "See",
      "a", "Afro-Americans"));
    validField(new DataField(tag, " ", " ", "a", "Black Americans",
      "s", "See",
      "a", "Afro-Americans"));
    validField(new DataField(tag, " ", " ", "a", "Colored people (United States)",
      "s", "See",
      "a", "Afro-Americans"));
    validField(new DataField(tag, " ", " ", "a", "Arabs",
      "x", "History",
      "y", "622-1517",
      "s", "See",
      "a", "Islamic Empire"));
    validField(new DataField(tag, " ", " ", "a", "Kuchipudi Bharatam",
      "s", "See",
      "a", "Kuchipudi (Dance)"));
    validField(new DataField(tag, " ", " ", "a", "Dance",
      "z", "India",
      "s", "See also",
      "a", "Bharata natyam; Kathak (Dance); Kuchipudi (Dance); Odissi dance"));
  }

  @Test
  public void testInvalidFields() {
    invalidField("2", "a", "Priority processing");
    invalidField("b", "Priority processing");
  }
}
