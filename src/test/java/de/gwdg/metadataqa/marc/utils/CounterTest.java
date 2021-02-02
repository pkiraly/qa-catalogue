package de.gwdg.metadataqa.marc.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CounterTest {

  Counter<String> counter = new Counter();

  @Test
  public void testCount() {
    counter.count("dummy");
    assertEquals(1, counter.get("dummy"));
  }

  @Test
  public void testAdd() {
    counter.add("dummy", 3);
    assertEquals(3, counter.get("dummy"));
  }

  @Test
  public void testGet_one() {
    counter.count("dummy");
    assertEquals(1, counter.get("dummy"));
  }

  @Test
  public void testKeys() {
    counter.count("dummy");
    assertEquals(1, counter.keys().size());
  }

  @Test
  public void testMap() {
    counter.count("dummy");
    assertEquals("{dummy=1}", counter.getMap().toString());
  }

  @Test
  public void testTotal() {
    counter.count("foo");
    counter.add("bar", 8);
    assertEquals(9, counter.total());
  }


}