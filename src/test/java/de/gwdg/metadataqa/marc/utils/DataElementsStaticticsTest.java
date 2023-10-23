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
    assertEquals( 225, statistics.get(DataElementType.coreFields));
    assertEquals( 179, statistics.get(DataElementType.coreIndicators));
    assertEquals(2493, statistics.get(DataElementType.coreSubfields));
    assertEquals( 214, statistics.get(DataElementType.localFields));
    assertEquals(  28, statistics.get(DataElementType.localIndicators));
    assertEquals(1731, statistics.get(DataElementType.localSubfields));
    assertEquals(5088, statistics.total());
  }
}