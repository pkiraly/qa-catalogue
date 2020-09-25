package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.Utils;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class NetworkAnalysisTest {

  @Test
  public void scientificNotation() {
    assertEquals(4000000, Utils.scientificNotationToInt("4e+06"));
  }

}
