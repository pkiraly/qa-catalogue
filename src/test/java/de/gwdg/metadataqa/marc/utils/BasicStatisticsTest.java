package de.gwdg.metadataqa.marc.utils;

import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

public class BasicStatisticsTest {

  @Test
  public void testBasicStatistics() {
    Map<Integer, Integer> histogram = new TreeMap<Integer, Integer>() {{
      put(1, 71429);
      put(2, 7033);
      put(3, 1362);
      put(4, 423);
      put(5, 180);
      put(6, 85);
      put(7, 56);
      put(8, 29);
      put(9, 39);
      put(10, 13);
      put(11, 12);
      put(12, 2);
      put(13, 3);
      put(14, 1);
      put(15, 4);
      put(16, 1);
      put(17, 1);
      put(20, 1);
    }};

    BasicStatistics statistics = new BasicStatistics(histogram);
    assertEquals(1, (int) statistics.getMin());
    assertEquals(20, (int) statistics.getMax());
    assertEquals(1.16654, (double) statistics.getMean(), 0.0001);
    assertEquals(5.267561587768387E-4, (double) statistics.getStdDev(), 0.0001);
  }

}
