package de.gwdg.metadataqa.marc.utils.pica.path;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PicaSpecTest {

  @Test
  public void getPath() {
    PicaSpec spec = new PicaSpec("001A$0|extractPicaDate");
    assertEquals("001A$0", spec.getPath().getPath());
    assertEquals("001A", spec.getPath().getTag());
    assertEquals(List.of("0"), spec.getPath().getSubfields().getCodes());
  }

  @Test
  public void getFunction_withFunction() {
    PicaSpec spec = new PicaSpec("001A$0|extractPicaDate");
    assertEquals("extractPicaDate", spec.getFunction());
  }

  @Test
  public void getFunction_withoutFunction() {
    PicaSpec spec = new PicaSpec("001A$0");
    assertNull(spec.getFunction());
  }

  @Test
  public void encode_withFunction() {
    PicaSpec spec = new PicaSpec("001A$0|extractPicaDate");
    assertEquals("001A$0", spec.encode());
  }

  @Test
  public void encode_withoutFunction() {
    PicaSpec spec = new PicaSpec("001A$0");
    assertEquals("001A$0", spec.encode());
  }
}