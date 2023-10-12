package de.gwdg.metadataqa.marc.utils.pica.reader;

import de.gwdg.metadataqa.marc.cli.CliTestUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PicaReaderTest {

  PicaReader reader;

  @Before
  public void setUp() throws Exception {
    reader = new PicaXmlReader(new FileInputStream(CliTestUtils.getTestResource("picaxml/pica.xml")));
  }

  @Test
  public void parseIdField() {
    reader.setIdField("003@$1");
    reader.setSubfieldSeparator("$");
    reader.parseIdField();
    assertEquals("003@", reader.getIdTag());
    assertEquals("1", reader.getIdCode());
  }

  @Test
  public void setIdField() {
    reader.setIdField("003@$1");
    assertEquals("003@$1", reader.getIdField());
  }

  @Test
  public void setSubfieldSeparator() {
    reader.setSubfieldSeparator("$");
    assertEquals("$", reader.getSubfieldSeparator());
  }

  @Test
  public void setIdCode() {
    reader.setIdCode("1");
    assertEquals("1", reader.getIdCode());
  }

  @Test
  public void getIdField() {
    assertEquals("003@$0", reader.getIdField());
  }

  @Test
  public void getSubfieldSeparator() {
    assertNotNull(reader.getSubfieldSeparator());
    reader.setSubfieldSeparator("$");
    assertEquals("$", reader.getSubfieldSeparator());
  }
}