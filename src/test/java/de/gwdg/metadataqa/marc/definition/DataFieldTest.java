package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag040;
import de.gwdg.metadataqa.marc.definition.tags.tags20x.Tag245;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import de.gwdg.metadataqa.marc.utils.SubfieldParser;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DataFieldTest {

  @Test
  public void testMultiDefinition() {
    DataFieldDefinition tag040 = Tag040.getInstance();
    DataFieldDefinition tag245 = Tag245.getInstance();
    assertEquals("040", tag040.getTag());
    assertEquals("245", tag245.getTag());
  }

  @Test
  public void testMultiFields() {
    DataField tag245 = SubfieldParser.parseField(Tag245.getInstance(), "10$aAdvanced calculus.$pStudent handbook.");
    DataField tag040 = SubfieldParser.parseField(Tag040.getInstance(), "  $aMt$cMt");
    assertEquals("1", tag245.getInd1());
    assertEquals(" ", tag040.getInd1());
    assertEquals("Advanced calculus.", tag245.getSubfield("a").get(0).getValue());
    assertEquals("Mt", tag040.getSubfield("a").get(0).getValue());
  }

  @Test
  public void testSimpleFormat() {
    DataField tag040 = SubfieldParser.parseField(Tag040.getInstance(), "  $aMt$cMt");
    assertEquals("   $aMt$cMt", tag040.simpleFormat());
  }

  @Test
  public void testGetKeyValuePairs() {
    DataField tag040 = SubfieldParser.parseField(Tag040.getInstance(), "  $aMt$cMt");
    Map<String, List<String>> map = tag040.getKeyValuePairs();
    assertEquals(2, map.size());
    assertEquals(1, map.get("040c").size());
    assertEquals("Montana State Library", map.get("040c").get(0));

    assertEquals(1, map.get("040a").size());
    assertEquals("Montana State Library", map.get("040a").get(0));
  }

  @Test
  public void testUnhandledSubfields() {
    DataField tag040 = SubfieldParser.parseField(Tag040.getInstance(), "  $aMt$cMt$xMt");
    tag040.setRecord(new MarcRecord("123"));
    boolean valid = tag040.validate(MarcVersion.MARC21);
    assertFalse(valid);
    assertFalse(tag040.getValidationErrors().isEmpty());
    assertEquals(1, tag040.getValidationErrors().size());
    ValidationError error = tag040.getValidationErrors().get(0);
    assertEquals(ValidationErrorType.SUBFIELD_UNDEFINED, error.getType());
    assertEquals("040", error.getMarcPath());
    assertEquals("x", error.getMessage());
    assertEquals("123", error.getRecordId());
  }

}
