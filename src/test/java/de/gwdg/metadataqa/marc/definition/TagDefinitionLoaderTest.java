package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TagDefinitionLoaderTest {

  @Test
  public void testGetClassName() {
    assertEquals("de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag017", TagDefinitionLoader.getClassName("017"));
    assertEquals("de.gwdg.metadataqa.marc.definition.tags.tags1xx.Tag111", TagDefinitionLoader.getClassName("111"));
    assertEquals("de.gwdg.metadataqa.marc.definition.tags.tags20x.Tag222", TagDefinitionLoader.getClassName("222"));
    assertEquals("de.gwdg.metadataqa.marc.definition.tags.tags25x.Tag250", TagDefinitionLoader.getClassName("250"));
    assertEquals("de.gwdg.metadataqa.marc.definition.tags.tags3xx.Tag351", TagDefinitionLoader.getClassName("351"));
    // assertEquals("de.gwdg.metadataqa.marc.definition.tags.tags4xx.Tag451", TagDefinitionLoader.getClassName("451"));
    assertEquals("de.gwdg.metadataqa.marc.definition.tags.tags5xx.Tag552", TagDefinitionLoader.getClassName("552"));
    assertEquals("de.gwdg.metadataqa.marc.definition.tags.tags6xx.Tag651", TagDefinitionLoader.getClassName("651"));
    assertEquals("de.gwdg.metadataqa.marc.definition.tags.tags70x.Tag751", TagDefinitionLoader.getClassName("751"));
    assertEquals("de.gwdg.metadataqa.marc.definition.tags.tags76x.Tag760", TagDefinitionLoader.getClassName("760"));
    assertEquals("de.gwdg.metadataqa.marc.definition.tags.tags80x.Tag830", TagDefinitionLoader.getClassName("830"));
    assertEquals("de.gwdg.metadataqa.marc.definition.tags.tags84x.Tag856", TagDefinitionLoader.getClassName("856"));
  }

  @Test
  public void testLoadClasses() {
    assertEquals("017", TagDefinitionLoader.load("017", MarcVersion.MARC21).getTag());
    assertEquals("111", TagDefinitionLoader.load("111", MarcVersion.MARC21).getTag());
    assertEquals("222", TagDefinitionLoader.load("222", MarcVersion.MARC21).getTag());
    assertEquals("250", TagDefinitionLoader.load("250", MarcVersion.MARC21).getTag());
    assertEquals("351", TagDefinitionLoader.load("351", MarcVersion.MARC21).getTag());
    // assertEquals("451", TagDefinitionLoader.load("451").getTag());
    assertEquals("552", TagDefinitionLoader.load("552", MarcVersion.MARC21).getTag());
    assertEquals("651", TagDefinitionLoader.load("651", MarcVersion.MARC21).getTag());
    assertEquals("751", TagDefinitionLoader.load("751", MarcVersion.MARC21).getTag());
    assertEquals("760", TagDefinitionLoader.load("760", MarcVersion.MARC21).getTag());
    assertEquals("830", TagDefinitionLoader.load("830", MarcVersion.MARC21).getTag());
    assertEquals("856", TagDefinitionLoader.load("856", MarcVersion.MARC21).getTag());
  }

  @Test
  public void testAllClasses() {
    List<String> tags = Arrays.asList(
      "010", "013", "015", "016", "017", "018", "019", "020", "022", "024", "025",
      "026", "027", "028", "029", "030", "031", "032", "033", "034", "035", "036",
      "037", "038", "040", "041", "042", "043", "044", "045", "046", "047", "048",
      "050", "051", "052", "055", "060", "061", "066", "070", "071", "072", "074",
      "080", "082", "083", "084", "085", "086", "088", "100", "110", "111", "130",
      "210", "222", "240", "242", "243", "245", "246", "247", "250", "254", "255",
      "256", "257", "258", "260", "263", "264", "270", "300", "306", "307", "310",
      "321", "336", "337", "338", "340", "342", "343", "344", "345", "346", "347",
      "348", "351", "352", "355", "357", "362", "363", "365", "366", "370", "377",
      "380", "381", "382", "383", "384", "385", "386", "388", "490", "500", "501",
      "502", "504", "505", "506", "507", "508", "510", "511", "513", "514", "515",
      "516", "518", "520", "521", "522", "524", "525", "526", "530", "533", "534",
      "535", "536", "538", "539", "540", "541", "542", "544", "545", "546", "547",
      "550", "552", "555", "556", "561", "562", "563", "565", "567", "580", "581",
      "583", "584", "585", "586", "588", "600", "610", "611", "630", "647", "648",
      "650", "651", "653", "654", "655", "656", "657", "658", "662", "700", "710",
      "711", "720", "730", "740", "751", "752", "753", "754", "760", "762", "765",
      "767", "770", "772", "773", "774", "775", "776", "777", "780", "785", "786",
      "787", "800", "810", "811", "830", "850", "852", "856", "882", "883", "884",
      "885", "886", "887", "911", "912", "938"
    );
    List<String> oclcTags = Arrays.asList("019", "029", "539", "911", "912", "938");

    for (String tag : tags) {
      if (oclcTags.contains(tag))
        assertEquals(tag, TagDefinitionLoader.load(tag, MarcVersion.OCLC).getTag());
      else {
        assertEquals(tag, TagDefinitionLoader.load(tag).getTag());
      }
    }
  }

  @Test
  public void test591Gent() {
    DataFieldDefinition definition = TagDefinitionLoader.load("591", MarcVersion.GENT);
    assertEquals("de.gwdg.metadataqa.marc.definition.tags.genttags.Tag591", definition.getClass().getCanonicalName());
  }

  @Test
  public void test591DNB() {
    DataFieldDefinition definition = TagDefinitionLoader.load("591", MarcVersion.DNB);
    assertEquals("de.gwdg.metadataqa.marc.definition.tags.dnbtags.Tag591", definition.getClass().getCanonicalName());
  }

  @Test
  public void test591MARC21() {
    DataFieldDefinition definition = TagDefinitionLoader.load("591", MarcVersion.MARC21);
    assertNull(definition);
  }
}
