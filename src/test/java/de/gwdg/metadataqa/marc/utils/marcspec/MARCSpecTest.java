package de.gwdg.metadataqa.marc.utils.marcspec;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MARCSpecTest {

  @Test
  public void test245() {
    MARCspec marcspec = new MARCspec("245");
    assertEquals("245", marcspec.getField().getTag());
  }

  @Test
  public void test245a() {
    MARCspec marcspec = new MARCspec("245$c");
    assertEquals("245", marcspec.getField().getTag());
    // assertEquals("245", marcspec.getSubfieldList().get(0).);
  }

}
