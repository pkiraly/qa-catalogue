package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.MarcSubfield;
import org.junit.*;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Tag045Test {

  private static final Pattern BC = Pattern.compile("^([a-d])(-)$");
  private static final Pattern CE = Pattern.compile("^([e-y])(\\d|-)$");

  @Test
  public void testCStH() {
    MarcRecord record = new MarcRecord("test");

    List<String> values = Arrays.asList(
      "d7d9",
      "0-0-", "2209668", "a-cc---", "a-w1", "a0b0", "a0t4", "b0d3", "c5v8", "d1764", "d4i-",
      "d5d6", "d6d7", "d9e9", "d9g0", "d9y9", "j0n9", "j0t0", "j0v0", "k0t9", "l0x0", "m0w5",
      "n-us---", "n0x9", "n2t9", "o0s9", "o0t0", "p0p9", "p0r0", "q1", "q5v9", "q6q8", "r0s9",
      "r0t0", "r0u0", "s0s9", "s0t2", "s0t9", "s0w0", "s4v5", "s5t1", "s7t0", "s9s9", "s9t1",
      "s9w0", "s9w9", "t-t-", "t0u0", "t0u9", "t0x9", "t1t5", "t4v7", "u-u-", "u-v-", "u-v4",
      "u-v7", "u0u9", "u0v7", "u0w2", "u0w6", "u2w9", "u5w0", "u5x2", "u6v4", "u6v6", "u8u8",
      "u8v2", "u8x8", "u9v8", "v v", "v-v-", "v0w1", "v0x2", "v1v6", "v1w0"
      /* */
    );

    Tag045 tag = Tag045.getInstance();
    List<String> badValues = Arrays.asList("0-0-", "2209668", "a-cc---", "d1764", "n-us---", "q1", "v v");
    for (String value : values) {
      DataField field = new DataField(Tag045.getInstance(), " ", " ", "a", value);
      field.setRecord(record);
      MarcSubfield subfield = field.getSubfield("a").get(0);
      if (badValues.contains(value))
        assertFalse(value + " should be invalid", subfield.validate(null));
      else
        assertTrue(value + " should be valid", subfield.validate(null));
    }
  }
}
