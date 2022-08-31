package de.gwdg.metadataqa.marc.utils.alephseq;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.Leader;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.utils.marcreader.MarcMakerReader;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MarcMakerReaderTest {

  @Test
  public void testFile01() {
    Path path = null;
    try {
      path = FileUtils.getPath("marcmaker/01.marcmaker");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    MarcReader reader = new MarcMakerReader(path.toString());
    Record marc4jRecord = null;
    if (reader.hasNext())
      marc4jRecord = reader.next();
    assertNotNull(marc4jRecord);
    BibliographicRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord, Leader.Type.BOOKS, MarcVersion.GENT, "^");
    assertNotNull(marcRecord);

    assertEquals("987874829", marcRecord.getId());
    List<DataField> fields = marcRecord.getDatafield("022");
    assertNotNull(fields);
    assertEquals(1, fields.size());
    DataField field = fields.get(0);
    assertEquals("022", field.getTag());
    assertEquals(" ", field.getInd1());
    assertEquals(" ", field.getInd2());
    assertEquals("1940-5758", field.getSubfield("a").get(0).getValue());
  }

  @Test
  public void testMultiline() {
    Path path = null;
    try {
      path = FileUtils.getPath("marcmaker/02.marcmaker");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    MarcReader reader = new MarcMakerReader(path.toString());
    Record marc4jRecord = null;
    if (reader.hasNext())
      marc4jRecord = reader.next();
    assertNotNull(marc4jRecord);
    BibliographicRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord, Leader.Type.BOOKS, MarcVersion.GENT, "^");
    assertNotNull(marcRecord);

    assertEquals("0123456789", marcRecord.getId());
    List<DataField> fields = marcRecord.getDatafield("245");
    assertNotNull(fields);
    assertEquals(1, fields.size());
    DataField field = fields.get(0);
    assertEquals("245", field.getTag());
    assertEquals("1", field.getInd1());
    assertEquals("0", field.getInd2());
    assertEquals(
      "by John W. Smith III ; with an introduction and commentary by Spencer Yarborough.",
      field.getSubfield("c").get(0).getValue());
  }

  @Test
  public void testMultiline2() {
    Path path = null;
    try {
      path = FileUtils.getPath("marcmaker/03.marcmaker");
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
    MarcReader reader = new MarcMakerReader(path.toString());
    Record marc4jRecord = null;
    if (reader.hasNext())
      marc4jRecord = reader.next();
    assertNotNull(marc4jRecord);
    BibliographicRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord, Leader.Type.BOOKS, MarcVersion.GENT, "^");
    assertNotNull(marcRecord);

    assertEquals("rb1993000850", marcRecord.getId());
    List<DataField> fields = marcRecord.getDatafield("505");
    assertEquals(
      " 1. Everlasting -- 2. Jump start -- 3. The urge to merge -- 4. Split decision -- 5. When I fall in love -- 6. Pink Cadillac -- 7. I live for your love -- 8. In my reality.",
      fields.get(0).getSubfield("a").get(0).getValue());
  }

}
