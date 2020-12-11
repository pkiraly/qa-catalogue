package de.gwdg.metadataqa.marc.definition;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SourceSpecificationTypeTest {

  @Test
  public void getIndicators() {
    assertEquals(Arrays.asList("7"),
      SourceSpecificationType.Indicator1Is7AndSubfield2.getIndicators());
    assertEquals(Arrays.asList(" "),
      SourceSpecificationType.Indicator1IsSpaceAndSubfield2.getIndicators());
    assertEquals(Arrays.asList("7"),
      SourceSpecificationType.Indicator2AndSubfield2.getIndicators());
    assertEquals(Arrays.asList("6", "7", "8", "9"),
      SourceSpecificationType.Indicator2For055AndSubfield2.getIndicators());
    assertEquals(Arrays.asList(""),
      SourceSpecificationType.Subfield2.getIndicators());
  }

  @Test
  public void values() {
    assertEquals(
      new SourceSpecificationType[]{
        SourceSpecificationType.Indicator1Is7AndSubfield2,
        SourceSpecificationType.Indicator1IsSpaceAndSubfield2,
        SourceSpecificationType.Indicator2AndSubfield2,
        SourceSpecificationType.Indicator2For055AndSubfield2,
        SourceSpecificationType.Subfield2
      },
      SourceSpecificationType.values());
  }

  @Test
  public void valueOf() {
    assertEquals(SourceSpecificationType.Indicator1Is7AndSubfield2,
      SourceSpecificationType.valueOf("Indicator1Is7AndSubfield2"));

    assertEquals(SourceSpecificationType.Indicator1IsSpaceAndSubfield2,
      SourceSpecificationType.valueOf("Indicator1IsSpaceAndSubfield2"));

    assertEquals(SourceSpecificationType.Indicator2AndSubfield2,
      SourceSpecificationType.valueOf("Indicator2AndSubfield2"));

    assertEquals(SourceSpecificationType.Indicator2For055AndSubfield2,
      SourceSpecificationType.valueOf("Indicator2For055AndSubfield2"));

    assertEquals(SourceSpecificationType.Subfield2,
      SourceSpecificationType.valueOf("Subfield2"));
  }
}