package de.gwdg.metadataqa.marc.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DataElementsStaticticsTest {

  @Test
  public void testStatistics() {
    Counter<DataElementType> statistics = DataElementsStatictics.count();
    assertEquals(   8, statistics.keys().size());
    assertEquals("There should be 6 defined control fields",   6, statistics.get(DataElementType.controlFields));
    assertEquals("There should be 212 defined control field positions",  212, statistics.get(DataElementType.controlFieldPositions));
    assertEquals("There should be 230 defined fields in MARC core",  230, statistics.get(DataElementType.coreFields));
    assertEquals("There should be 185 defined indicators in MARC core",  185, statistics.get(DataElementType.coreIndicators));
    assertEquals("There should be 2674 defined subfields in MARC core", 2674, statistics.get(DataElementType.coreSubfields));
    assertEquals("There should be 228 defined locally defined fields",  228, statistics.get(DataElementType.localFields));
    assertEquals("There should be 30 defined locally defined indicators",   30, statistics.get(DataElementType.localIndicators));
    assertEquals("There should be 1807 defined locally defined subfields", 1807, statistics.get(DataElementType.localSubfields));
    assertEquals("There should be 5372 defined data elements", 5372, statistics.total());
  }
}