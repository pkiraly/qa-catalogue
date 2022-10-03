package de.gwdg.metadataqa.marc.utils.pica.crosswalk;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PicaMarcCrosswalkReaderTest {

  @Test
  public void test() {
    List<Crosswalk> mapping = PicaMarcCrosswalkReader.read();
    assertEquals(1319, mapping.size());
  }
}
