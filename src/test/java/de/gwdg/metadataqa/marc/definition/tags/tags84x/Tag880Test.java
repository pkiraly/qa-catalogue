package de.gwdg.metadataqa.marc.definition.tags.tags84x;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.general.Linkage;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.ParserException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Tag880Test {

  @Test
  public void test1() {
    DataField field = new DataField(Tag880.getInstance(), "1", " ",
      "6", "100-01/(2/r", "a", "פריימן, חיים בן ישראל מאיר.");
    assertEquals("880", field.getTag());
    assertEquals("100-01/(2/r", field.getSubfield("6").get(0).getValue());
    try {
      Linkage linkage = LinkageParser.getInstance().create(field.getSubfield("6").get(0).getValue());
      assertNotNull(linkage);
      assertEquals("100", linkage.getLinkingTag());
      assertEquals("01", linkage.getOccurrenceNumber());
      assertEquals("(2", linkage.getScriptIdentificationCode());
      assertEquals("Hebrew", linkage.resolveScriptIdentificationCode());
      assertEquals("r", linkage.getFieldOrientationCode());
      assertEquals("right-to-left", linkage.resolveFieldOrientationCode());
    } catch (ParserException e) {
      throw new RuntimeException(e);
    }
  }
  /*
  "10 $6245-02/(2/r$aספר קיצור דיני תרומות ומעשרות /$cמאת חיים בן ישראל מאיר פריימן.",
  "14 $6246-03/(2/r$aקיצור דיני תרומות ומעשרות"
  "   $6260-04/(2/r$aבני־ברק :$bמישור,$c759 [1998 or 1999].",
  "1  $6100-01/$1$a吳正德.",
  "10 $6245-02/$1$a頭戴之硬盔 /$c[撰文・編輯吳正德].",
  "   $6250-03/$1$a初版.",
  "   $6260-04/$1$a台北縣三芝鄉 :$b財團法人李天禄布袋戲文敎基金會,$c民國87 [1998]"
  */
}
