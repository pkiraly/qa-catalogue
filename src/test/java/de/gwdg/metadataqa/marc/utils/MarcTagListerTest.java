package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.TagDefinitionLoader;
import org.junit.Test;

import java.time.LocalTime;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

public class MarcTagListerTest {

  @Test
  public void test() {
    List<Class<? extends DataFieldDefinition>> tags = MarcTagLister.listTags();
    assertNotNull(tags);
    assertNotEquals(0, tags.size());
    assertEquals(284, tags.size());
    assertEquals("Tag010", tags.get(0).getSimpleName());

    for (Class<? extends DataFieldDefinition> tag : tags) {
      DataFieldDefinition definition = TagDefinitionLoader.load(tag.getSimpleName().replace("Tag", ""));
      assertNotNull(tag.getSimpleName(), definition);
      if (!definition.getIndexTag().equals(definition.getTag())) {
        if (definition.getInd1().exists()) {
          assertNotEquals(String.format("Undefined index tag for indicator1 at %s", definition.getTag()),
            "ind1", definition.getInd1().getIndexTag());
          assertFalse(String.format("Undefined index tag for indicator1 at %s", definition.getTag()),
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
  }

  @Test
  public void testDate() {
    assertEquals("00:00:59", LocalTime.MIN.plusSeconds(59).toString());
    assertEquals("00:01:01", LocalTime.MIN.plusSeconds(61).toString());
    assertEquals("00:10:01", LocalTime.MIN.plusSeconds(601).toString());
    assertEquals("00:13:28", LocalTime.MIN.plusSeconds(808).toString());
  }
}
