package de.gwdg.metadataqa.marc.utils.pica;

import org.junit.Test;
import org.marc4j.marc.impl.SubfieldImpl;

import static org.junit.Assert.assertEquals;

public class PicaDataFieldTest {

  @Test
  public void constructor_withTag() {
    PicaDataField dataField = new PicaDataField("029F");
    assertEquals("029F", dataField.getTag());
    assertEquals(null, dataField.getOccurrence());
    assertEquals("029F", dataField.toString());
  }

  @Test
  public void constructor_withTagAndOccurence() {
    PicaDataField dataField = new PicaDataField("029F", "01");
    assertEquals("029F", dataField.getTag());
    assertEquals("01", dataField.getOccurrence());
    assertEquals("029F/01", dataField.toString());
  }

  @Test
  public void addSubfields() {
    PicaDataField dataField = new PicaDataField("029F", "01");
    dataField.addSubfield(new SubfieldImpl('a', "something"));
    assertEquals("029F", dataField.getTag());
    assertEquals("01", dataField.getOccurrence());
    assertEquals("029F/01 $asomething", dataField.toString());
  }
}