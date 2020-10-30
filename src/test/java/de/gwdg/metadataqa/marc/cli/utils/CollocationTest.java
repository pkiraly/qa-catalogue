package de.gwdg.metadataqa.marc.cli.utils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CollocationTest {


  @Test
  public void header() {
    assertEquals("abbreviations,recordcount,percent\n", Collocation.header());
  }

  @Test
  public void formatRow() {
    Collocation collocation = new Collocation(Arrays.asList("lcsh", "udc"), 3416, 5000);
    assertEquals("lcsh;udc,3416,68.32%\n", collocation.formatRow());

    collocation = new Collocation(Arrays.asList("lcsh", "udc"), 3416, 5632);
    assertEquals("lcsh;udc,3416,60.65%", collocation.formatRow().trim());
  }

  @Test
  public void compareTo_equals() {
    Collocation a = new Collocation(Arrays.asList("lcsh", "udc"), 3416, 5000);
    Collocation b = new Collocation(Arrays.asList("lcsh", "udc"), 3416, 5000);
    assertEquals(0, a.compareTo(b));
  }

  @Test
  public void compareTo_firstHasMore() {
    Collocation a = new Collocation(Arrays.asList("lcsh", "udc"), 4416, 5000);
    Collocation b = new Collocation(Arrays.asList("lcsh", "udc"), 3416, 5000);
    assertTrue(a.compareTo(b) > 0);
  }

  @Test
  public void compareTo_firstHasLess() {
    Collocation a = new Collocation(Arrays.asList("lcsh", "udc"), 3416, 5000);
    Collocation b = new Collocation(Arrays.asList("lcsh", "udc"), 4416, 5000);
    assertTrue(a.compareTo(b) < 0);
  }

  @Test
  public void compareTo_firstHasLowerKey() {
    Collocation a = new Collocation(Arrays.asList("acsh", "udc"), 4416, 5000);
    Collocation b = new Collocation(Arrays.asList("lcsh", "udc"), 4416, 5000);
    assertTrue(a.compareTo(b) < 0);
  }

  @Test
  public void compareTo_firstHasLargerKey() {
    Collocation a = new Collocation(Arrays.asList("xcsh", "udc"), 4416, 5000);
    Collocation b = new Collocation(Arrays.asList("lcsh", "udc"), 4416, 5000);
    assertTrue(a.compareTo(b) > 0);
  }

  @Test
  public void getKey() {
    Collocation a = new Collocation(Arrays.asList("xcsh", "udc"), 4416, 5000);
    assertEquals("xcsh;udc", a.getKey());
  }

  @Test
  public void getCount() {
    Collocation a = new Collocation(Arrays.asList("xcsh", "udc"), 4416, 5000);
    assertEquals(4416, a.getCount().intValue());
  }
}