package de.gwdg.metadataqa.marc.definition.tags.bltags;

import de.gwdg.metadataqa.marc.dao.DataField;
import org.junit.Test;

import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;

public class Tag039Test extends BLTagTest {

  public Tag039Test() {
    super(Tag039.getInstance());
  }

  @Test
  public void testRegex() {
    assertTrue(Pattern.compile("^\\d\\d(\\d{2}|0[1-3])$").matcher("1785").matches());
  }

  @Test
  public void testFieldDefinition() {
    validField("0", "a", "1785");
    validField("0", "a", "8901");
    validField("1", "a", "8903");
    validField("0", "a", "1754");
    validField("0", "a", "9602");
    validField(new DataField(tag, "0", " ", "p", "1627", "a", "1608"));
    validField("0", "a", "8901");
    validField("1", "a", "8903");
  }
}
