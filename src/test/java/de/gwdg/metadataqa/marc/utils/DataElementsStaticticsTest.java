package de.gwdg.metadataqa.marc.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DataElementsStaticticsTest {

  @Test
  public void testStatistics() {
    Counter<DataElementType> statistics = DataElementsStatictics.count();
    assertEquals(   8, statistics.keys().size());
    assertEquals(   6, statistics.get(DataElementType.controlFields));
    assertEquals( 212, statistics.get(DataElementType.controlFieldPositions));
    assertEquals( 229, statistics.get(DataElementType.coreFields));
    assertEquals( 184, statistics.get(DataElementType.coreIndicators));
    assertEquals(2667, statistics.get(DataElementType.coreSubfields));
    assertEquals( 217, statistics.get(DataElementType.localFields));
    assertEquals(  28, statistics.get(DataElementType.localIndicators));
    assertEquals(1756, statistics.get(DataElementType.localSubfields));
    assertEquals(5299, statistics.total());
  }
}