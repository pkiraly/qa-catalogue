package de.gwdg.metadataqa.marc.utils.alephseq;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.Leader;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AlephseqMarcReaderTest {

  @Test
  public void test() {
    Path path = null;
    try {
      path = FileUtils.getPath("general/alephseq-example.txt");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    MarcReader reader = new AlephseqMarcReader(path.toString());
    Record record = null;
    if (reader.hasNext())
      record = reader.next();
    assertNotNull(record);
    MarcRecord marcRecord = MarcFactory.createFromMarc4j(record, Leader.Type.BOOKS, MarcVersion.GENT, true);
    assertNotNull(marcRecord);

    assertEquals("000000002", marcRecord.getId());
    List<DataField> fields = marcRecord.getDatafield("100");
    assertNotNull(fields);
    assertEquals(1, fields.size());
    DataField field = fields.get(0);
    assertEquals("100", field.getTag());
    assertEquals("1", field.getInd1());
    assertEquals(" ", field.getInd2());
    assertEquals("Katz, Jerrold J.,", field.getSubfield("a").get(0).getValue());
    assertEquals("1932-2001", field.getSubfield("d").get(0).getValue());
    assertEquals("(viaf)108327082", field.getSubfield("0").get(0).getValue());
  }
}
