package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.dao.DataField;
import org.junit.Test;

public class Tag690Test extends BLTagTest {

  public Tag690Test() {
    super(Tag690.getInstance());
  }

  @Test
  public void testValidFields() {
    validField(new DataField(tag, "7", " ", "a", "Management and Business Studies", "2", "blcoll"));
    validField(new DataField(tag, "7", " ", "a", "Social Welfare", "2", "blcoll"));
  }

  @Test
  public void testInvalidFields() {
    invalidField("c", "NLS copy dimensions: 16 cm.");
    invalidField("1", "a", "NLS copy dimensions: 16 cm.");
  }
}
