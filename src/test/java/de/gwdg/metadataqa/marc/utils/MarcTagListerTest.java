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
    assertEquals(458, tags.size());
    assertEquals("Tag010", tags.get(0).getSimpleName());
    Map<String, Integer> versionCounter = new HashMap<>();
    Map<MarcVersion, Integer> versionCounter2 = new HashMap<>();

    for (Class<? extends DataFieldDefinition> tag : tags) {
      MarcVersion version = Utils.getVersion(tag);
      DataFieldDefinition definition = TagDefinitionLoader.load(
        tag.getSimpleName().replace("Tag", ""), version);
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
      MarcVersion.B3KAT, MarcVersion.OGYK, MarcVersion.HUNMARC,
      MarcVersion.UNIMARC // special case
    );
    for (MarcVersion version : MarcVersion.values()) {
      assertTrue(String.format("%s should be tested", version), testedVersions.contains(version));
    }

    assertEquals("There should be 11 defined tags in DNB",  11, (int) versionCounter2.get(MarcVersion.DNB));
    assertEquals("There should be 11 defined tags in DNB",  11, (int) versionCounter.get("dnbtags"));

    assertEquals("There should be 24 defined tags in FENNICA",  24, (int) versionCounter2.get(MarcVersion.FENNICA));
    assertEquals("There should be 24 defined tags in FENNICA",  24, (int) versionCounter.get("fennicatags"));

    assertEquals("There should be 3 defined tags in GENT",   3, (int) versionCounter2.get(MarcVersion.GENT));
    assertEquals("There should be 3 defined tags in GENT",   3, (int) versionCounter.get("genttags"));

    assertEquals("There should be 39 defined tags in NKCR",  39, (int) versionCounter2.get(MarcVersion.NKCR));
    assertEquals("There should be 39 defined tags in NKCR",  39, (int) versionCounter.get("nkcrtags"));

    assertEquals("There should be 15 defined tags in OCLC",  15, (int) versionCounter2.get(MarcVersion.OCLC));
    assertEquals("There should be 15 defined tags in OCLC",  15, (int) versionCounter.get("oclctags"));

    assertEquals("There should be 15 defined tags in SZTE",  15, (int) versionCounter2.get(MarcVersion.SZTE));
    assertEquals("There should be 15 defined tags in SZTE",  15, (int) versionCounter.get("sztetags"));

    assertEquals("There should be 1 defined tags in KBR",  1, (int) versionCounter2.get(MarcVersion.KBR));
    assertEquals("There should be 1 defined tags in KBR",  1, (int) versionCounter.get("kbrtags"));

    assertEquals("There should be 1 defined tags in ZB",  1, (int) versionCounter2.get(MarcVersion.ZB));
    assertEquals("There should be 1 defined tags in ZB",  1, (int) versionCounter.get("zbtags"));

    assertEquals("There should be 76 defined tags in BL",  76, (int) versionCounter2.get(MarcVersion.BL));
    assertEquals("There should be 76 defined tags in BL",  76, (int) versionCounter.get("bltags"));

    assertFalse( versionCounter2.containsKey(MarcVersion.MARC21NO));

    assertEquals("There should be 26 defined tags in UVA",  26, (int) versionCounter2.get(MarcVersion.UVA));
    assertEquals("There should be 26 defined tags in UVA",  26, (int) versionCounter.get("uvatags"));

    assertEquals("There should be 5 defined tags in B3KAT",  5, (int) versionCounter2.get(MarcVersion.B3KAT));
    assertEquals("There should be 5 defined tags in B3KAT",  5, (int) versionCounter.get("b3kattags"));

    assertEquals("There should be 12 defined tags in OGYK",  12, (int) versionCounter2.get(MarcVersion.OGYK));
    assertEquals("There should be 12 defined tags in OGYK",  12, (int) versionCounter.get("ogyktags"));

    assertEquals("There should be 230 defined tags in MARC21", 230, (int) versionCounter2.get(MarcVersion.MARC21));
    assertEquals("There should be 230 defined tags in MARC21 holdings",   2, (int) versionCounter.get("holdings"));
    assertEquals("There should be 50 defined tags in 01x group",  50, (int) versionCounter.get("tags01x"));
    assertEquals("There should be 50 defined tags in 1xx group",   4, (int) versionCounter.get("tags1xx"));
    assertEquals("There should be 50 defined tags in 20x group",   8, (int) versionCounter.get("tags20x"));
    assertEquals("There should be 50 defined tags in 25x group",  11, (int) versionCounter.get("tags25x"));
    assertEquals("There should be 50 defined tags in 3xx group",  40, (int) versionCounter.get("tags3xx"));
    assertEquals("There should be 50 defined tags in 4xx group",   5, (int) versionCounter.get("tags4xx"));
    assertEquals("There should be 50 defined tags in 5xx group",  51, (int) versionCounter.get("tags5xx"));
    assertEquals("There should be 50 defined tags in 6xx group",  16, (int) versionCounter.get("tags6xx"));
    assertEquals("There should be 50 defined tags in 70x group",  11, (int) versionCounter.get("tags70x"));
    assertEquals("There should be 50 defined tags in 76x group",  16, (int) versionCounter.get("tags76x"));
    assertEquals("There should be 50 defined tags in 80x group",   4, (int) versionCounter.get("tags80x"));
    assertEquals("There should be 50 defined tags in 84x group",  12, (int) versionCounter.get("tags84x"));
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