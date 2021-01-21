package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

public class MarcTagListerTest {

  @Test
  public void testListTag() {
    List<Class<? extends DataFieldDefinition>> tags = MarcTagLister.listTags();
    assertNotNull(tags);
    assertNotEquals(0, tags.size());
    assertEquals(399, tags.size());
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
        if (definition.getInd1().exists()) {
          assertNotEquals(
            String.format("Undefined index tag for indicator1 at %s", definition.getTag()),
            "ind1", definition.getInd1().getIndexTag());
          assertFalse(
            String.format("Undefined index tag for indicator1 at %s", definition.getTag()),
            definition.getInd1().getIndexTag().contains("ind1"));
        }
        if (definition.getInd2().exists()) {
          assertNotEquals(String.format("Undefined index tag for indicator2 at %s", definition.getTag()),
            "ind2", definition.getInd2().getIndexTag());
          assertFalse(String.format("Undefined index tag for indicator2 at %s", definition.getTag()),
            definition.getInd2().getIndexTag().contains("ind2"));
        }
      }
    }

    assertEquals(11, (int) versionCounter2.get(MarcVersion.DNB));
    assertEquals(11, (int) versionCounter.get("dnbtags"));

    assertEquals(24, (int) versionCounter2.get(MarcVersion.FENNICA));
    assertEquals(24, (int) versionCounter.get("fennicatags"));

    assertEquals(3, (int) versionCounter2.get(MarcVersion.GENT));
    assertEquals(3, (int) versionCounter.get("genttags"));

    assertEquals(39, (int) versionCounter2.get(MarcVersion.NKCR));
    assertEquals(39, (int) versionCounter.get("nkcrtags"));

    assertEquals(15, (int) versionCounter2.get(MarcVersion.OCLC));
    assertEquals(15, (int) versionCounter.get("oclctags"));

    assertEquals(15, (int) versionCounter2.get(MarcVersion.SZTE));
    assertEquals(15, (int) versionCounter.get("sztetags"));

    assertEquals(216, (int) versionCounter2.get(MarcVersion.MARC21));
    assertEquals(1, (int) versionCounter.get("holdings"));
    assertEquals(49, (int) versionCounter.get("tags01x"));
    assertEquals(4, (int) versionCounter.get("tags1xx"));
    assertEquals(8, (int) versionCounter.get("tags20x"));
    assertEquals(10, (int) versionCounter.get("tags25x"));
    assertEquals(34, (int) versionCounter.get("tags3xx"));
    assertEquals(5, (int) versionCounter.get("tags4xx"));
    assertEquals(50, (int) versionCounter.get("tags5xx"));
    assertEquals(15, (int) versionCounter.get("tags6xx"));
    assertEquals(11, (int) versionCounter.get("tags70x"));
    assertEquals(15, (int) versionCounter.get("tags76x"));
    assertEquals(4, (int) versionCounter.get("tags80x"));
    assertEquals(10, (int) versionCounter.get("tags84x"));
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
              if (!functionMap.containsKey(function)) {
                functionMap.put(function, new ArrayList<>());
              }
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