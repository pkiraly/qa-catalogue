package de.gwdg.metadataqa.marc.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class FunctionValueTest {

  @Test
  public void constructor_withoutParams() {
    FunctionValue value = new FunctionValue();
    assertEquals(0, value.getCount());
    assertEquals(0.0, value.getPercent(), 0.00000001);
  }

  @Test
  public void constructor_withParams() {
    FunctionValue value = new FunctionValue(8, 9.1);
    assertEquals(8, value.getCount());
    assertEquals(9.1, value.getPercent(), 0.00000001);
  }

  @Test
  public void count() {
    FunctionValue value = new FunctionValue(8, 9.1);
    value.count();
    assertEquals(9, value.getCount());
    assertEquals(9.1, value.getPercent(), 0.00000001);
  }

  @Test
  public void calculatePercent() {
    FunctionValue value = new FunctionValue(8, 9.1);
    value.calculatePercent(16);
    assertEquals(8, value.getCount());
    assertEquals(0.5, value.getPercent(), 0.00000001);
  }

  @Test
  public void add() {
    FunctionValue value = new FunctionValue(8, 9.1);
    value.add(new FunctionValue(2, 0.9));
    assertEquals(10, value.getCount());
    assertEquals(10.0, value.getPercent(), 0.00000001);
  }
}