package de.gwdg.metadataqa.marc.definition.tags.holdings;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Tag841Test extends TestCase {

  @Test
  public void test1() {
    DataField field = new DataField(Tag841.getInstance(), " ", " ",
      "a", "y###",
      "b", "8312124p####8###1001uabul0831017",
      "e", "4"
    );
    MarcSubfield subfield = field.getSubfield("a").get(0);
    SubfieldDefinition def = field.getSubfield("a").get(0).getDefinition();
    Map<String, String> extra = def.resolvePositional(subfield.getValue());
    Map<String, String> expected = new HashMap<String, String>();
    expected.put("0", "y");
    expected.put("1", "##");
    expected.put("2", "#");
    assertEquals(3, extra.size());
    assertEquals(expected, extra);
  }

}