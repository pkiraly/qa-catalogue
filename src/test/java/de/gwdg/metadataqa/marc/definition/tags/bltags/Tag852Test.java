package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.tags.tags84x.Tag852;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Tag852Test extends BLTagTest {

  public Tag852Test() {
    super(Tag852.getInstance());
  }

  @Test
  public void testValidFields() {
    SubfieldDefinition subfield = tag.getSubfield("a");
    assertEquals(1, subfield.getLocalCodes().size());
    assertEquals(32, subfield.getLocalCodes(MarcVersion.BL).size());
    assertEquals("Bodleian Library", subfield.getLocalCode(MarcVersion.BL, "BODBL").getLabel());
  }
}
