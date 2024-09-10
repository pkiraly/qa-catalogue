package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.TagDefinitionLoader;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MarcTagListerTest {

  @Test
  public void testListTag() {
    List<Class<? extends DataFieldDefinition>> tags = MarcTagLister.listTags();
    assertNotNull(tags);
    assertNotEquals(0, tags.size());
    assertEquals(566, tags.size());
    assertEquals("Tag010", tags.get(0).getSimpleName());
    Map<String, Integer> versionCounter = new HashMap<>();
    Map<MarcVersion, Integer> versionCounter2 = new HashMap<>();

    for (Class<? extends DataFieldDefinition> tag : tags) {
      MarcVersion version = Utils.getVersion(tag);
      DataFieldDefinition definition = TagDefinitionLoader.load(
        tag.getSimpleName().replace("Tag", ""), version);
      assertNotNull("The definition for tag " + tag.getCanonicalName() + " should not be null", definition);
      assertEquals(tag.getSimpleName() + " should have the same tag", "Tag" + definition.getTag(), tag.getSimpleName());

      assertEquals(version, definition.getMarcVersion());
      Utils.count(Utils.extractPackageName(definition), versionCounter);
      Utils.count(definition.getMarcVersion(), versionCounter2);
      assertNotNull(tag.getSimpleName(), definition);
      if (definition.getMqTag() != null) {
        assertEquals(
          definition.getTag() + ": the mqTag should be upper case",
          definition.getMqTag().substring(0, 1),
          definition.getMqTag().substring(0, 1).toUpperCase()
        );
      }

      if (!definition.getIndexTag().equals(definition.getTag())) {
        if (definition.getInd1() != null && definition.getInd1().exists()) {
          assertNotEquals(
            String.format("Undefined index tag for indicator1 at %s", definition.getTag()),
            "ind1", definition.getInd1().getIndexTag());
          assertFalse(
            String.format("Undefined index tag for indicator1 at %s", definition.getTag()),
            definition.getInd1().getIndexTag().contains("ind1"));
        }
        if (definition.getInd2() != null && definition.getInd2().exists()) {
          assertNotEquals(String.format("Undefined index tag for indicator2 at %s", definition.getTag()),
            "ind2", definition.getInd2().getIndexTag());
          assertFalse(String.format("Undefined index tag for indicator2 at %s", definition.getTag()),
            definition.getInd2().getIndexTag().contains("ind2"));
        }
      }
    }

    List<MarcVersion> testedVersions = List.of(
      MarcVersion.MARC21, MarcVersion.DNB, MarcVersion.FENNICA, MarcVersion.GENT, MarcVersion.NKCR, MarcVersion.OCLC,
      MarcVersion.SZTE, MarcVersion.KBR, MarcVersion.ZB, MarcVersion.BL, MarcVersion.MARC21NO, MarcVersion.UVA,
      MarcVersion.B3KAT, MarcVersion.OGYK, MarcVersion.HBZ,
      MarcVersion.UNIMARC // special case
    );
    for (MarcVersion version : MarcVersion.values()) {
      assertTrue(String.format("%s should be tested", version), testedVersions.contains(version));
    }

    assertEquals( 11, (int) versionCounter2.get(MarcVersion.DNB));
    assertEquals( 11, (int) versionCounter.get("dnbtags"));

    assertEquals( 24, (int) versionCounter2.get(MarcVersion.FENNICA));
    assertEquals( 24, (int) versionCounter.get("fennicatags"));

    assertEquals(  3, (int) versionCounter2.get(MarcVersion.GENT));
    assertEquals(  3, (int) versionCounter.get("genttags"));

    assertEquals( 39, (int) versionCounter2.get(MarcVersion.NKCR));
    assertEquals( 39, (int) versionCounter.get("nkcrtags"));

    assertEquals( 15, (int) versionCounter2.get(MarcVersion.OCLC));
    assertEquals( 15, (int) versionCounter.get("oclctags"));

    assertEquals( 15, (int) versionCounter2.get(MarcVersion.SZTE));
    assertEquals( 15, (int) versionCounter.get("sztetags"));

    assertEquals( 1, (int) versionCounter2.get(MarcVersion.KBR));
    assertEquals( 1, (int) versionCounter.get("kbrtags"));

    assertEquals( 1, (int) versionCounter2.get(MarcVersion.ZB));
    assertEquals( 1, (int) versionCounter.get("zbtags"));

    assertEquals( 76, (int) versionCounter2.get(MarcVersion.BL));
    assertEquals( 76, (int) versionCounter.get("bltags"));

    assertFalse( versionCounter2.containsKey(MarcVersion.MARC21NO));

    assertEquals( 26, (int) versionCounter2.get(MarcVersion.UVA));
    assertEquals( 26, (int) versionCounter.get("uvatags"));

    assertEquals( 5, (int) versionCounter2.get(MarcVersion.B3KAT));
    assertEquals( 5, (int) versionCounter.get("b3kattags"));

    assertEquals( 12, (int) versionCounter2.get(MarcVersion.OGYK));
    assertEquals( 12, (int) versionCounter.get("ogyktags"));

    assertEquals( 109, (int) versionCounter2.get(MarcVersion.HBZ));
    assertEquals( 109, (int) versionCounter.get("hbztags"));    

    assertEquals(229, (int) versionCounter2.get(MarcVersion.MARC21));
    assertEquals(  2, (int) versionCounter.get("holdings"));
    assertEquals( 49, (int) versionCounter.get("tags01x"));
    assertEquals(  4, (int) versionCounter.get("tags1xx"));
    assertEquals(  8, (int) versionCounter.get("tags20x"));
    assertEquals( 11, (int) versionCounter.get("tags25x"));
    assertEquals( 40, (int) versionCounter.get("tags3xx"));
    assertEquals(  5, (int) versionCounter.get("tags4xx"));
    assertEquals( 51, (int) versionCounter.get("tags5xx"));
    assertEquals( 16, (int) versionCounter.get("tags6xx"));
    assertEquals( 11, (int) versionCounter.get("tags70x"));
    assertEquals( 16, (int) versionCounter.get("tags76x"));
    assertEquals(  4, (int) versionCounter.get("tags80x"));
    assertEquals( 12, (int) versionCounter.get("tags84x"));
  }

  @Test
  public void testDateCalculations() {
    assertEquals("00:00:59", LocalTime.MIN.plusSeconds(59).toString());
    assertEquals("00:01:01", LocalTime.MIN.plusSeconds(61).toString());
    assertEquals("00:10:01", LocalTime.MIN.plusSeconds(601).toString());
    assertEquals("00:13:28", LocalTime.MIN.plusSeconds(808).toString());
  }

  @Test
  public void testListFunctions() {
    Map<FRBRFunction, List<String>> functionMap = new HashMap<>();
    List<Class<? extends DataFieldDefinition>> tags = MarcTagLister.listTags();
    for (Class<? extends DataFieldDefinition> tag : tags) {
      MarcVersion version = Utils.getVersion(tag);
      DataFieldDefinition definition = TagDefinitionLoader.load(
        tag.getSimpleName().replace("Tag", ""), version);
      if (definition == null) {
        System.err.println(tag.getSimpleName());
        continue;
      }
      if (definition.getSubfields() == null) {
        System.err.println(tag.getSimpleName() + " // subfields");
        continue;
      }
      for (SubfieldDefinition subfield : definition.getSubfields()) {
        List<FRBRFunction> functions = subfield.getFrbrFunctions();
        if (functions != null && !functions.isEmpty()) {
          for (FRBRFunction function : functions) {
            if (version == MarcVersion.MARC21) {
              functionMap.computeIfAbsent(function, s -> new ArrayList<>());
              functionMap.get(function).add(subfield.getPath());
            } else {
              System.err.println(version + " " + subfield.getPath());
            }
          }
        }
      }
    }
    functionMap.keySet().stream().sorted().forEach(
      function -> {
        List<String> fields = functionMap.get(function);
        System.err.println(function + "," + fields.size() + "," + StringUtils.join(fields, ";"));
      }
    );

  }
}