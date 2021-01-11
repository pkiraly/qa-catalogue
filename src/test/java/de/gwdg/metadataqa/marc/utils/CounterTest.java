package de.gwdg.metadataqa.marc.utils;

import junit.framework.TestCase;

public class CounterTest extends TestCase {

  Counter<String> counter = new Counter();

  public void testCount() {
    counter.count("dummy");
    assertEquals(1, counter.get("dummy"));
  }

  public void testAdd() {
    counter.add("dummy", 3);
    assertEquals(3, counter.get("dummy"));
  }

  public void testGet_one() {
    counter.count("dummy");
    assertEquals(1, counter.get("dummy"));
  }

  public void testKeys() {
    counter.count("dummy");
    assertEquals(1, counter.keys().size());
  }
}