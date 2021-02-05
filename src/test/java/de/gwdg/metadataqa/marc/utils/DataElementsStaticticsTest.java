package de.gwdg.metadataqa.marc.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DataElementsStaticticsTest {

  @Test
  public void testStatistics() {
    Counter<DataElementType> statistics = DataElementsStatictics.count();
    assertEquals(   8, statistics.keys().size());
    assertEquals(   6, statistics.get(DataElementType.controlFields));
    assertEquals( 226, statistics.get(DataElementType.controlFieldPositions));
    assertEquals( 219, statistics.get(DataElementType.coreFields));
    assertEquals( 177, statistics.get(DataElementType.coreIndicators));
    assertEquals(2425, statistics.get(DataElementType.coreSubfields));
    assertEquals( 183, statistics.get(DataElementType.localFields));
    assertEquals(  28, statistics.get(DataElementType.localIndicators));
    assertEquals( 865, statistics.get(DataElementType.localSubfields));
    assertEquals(4129, statistics.total());
  }

}