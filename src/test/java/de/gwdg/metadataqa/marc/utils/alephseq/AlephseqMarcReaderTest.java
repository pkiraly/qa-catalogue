package de.gwdg.metadataqa.marc.utils.alephseq;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.*;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.*;

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

    assertEquals("BE-GnUNI", marcRecord.getControl003().getContent());
    assertEquals("20171113130949.0", marcRecord.getControl005().getContent());
    assertNull(marcRecord.getControl006());
    assertNull(marcRecord.getControl007());
    assertEquals(
      "780804s1977    enk||||||b||||001 0|eng||",
      marcRecord.getControl008().getContent()
    );

    assertEquals("000000002", marcRecord.getId(true));
    assertEquals("000000002", marcRecord.getId(false));

    List<MarcControlField> simpleControlFields = marcRecord.getSimpleControlfields();
    assertEquals(3, simpleControlFields.size());
    assertEquals("000000002", simpleControlFields.get(0).getContent());

    List<MarcPositionalControlField> positionalControlFields = marcRecord.getPositionalControlfields();
    assertEquals(3, positionalControlFields.size());
    assertNull(positionalControlFields.get(0));
    assertNull(positionalControlFields.get(1));
    assertNotNull(positionalControlFields.get(2).getContent());

    assertTrue(marcRecord.exists("100"));

    List<String> subfields = marcRecord.extract("100", "a");
    assertEquals(1, subfields.size());
    assertEquals("Katz, Jerrold J.,", subfields.get(0));

    subfields = marcRecord.extract("100", "a", MarcRecord.RESOLVE.NONE);
    assertEquals(1, subfields.size());
    assertEquals("Katz, Jerrold J.,", subfields.get(0));

    List<String> tags = marcRecord.getUnhandledTags();
    assertNotNull(tags);
    assertEquals(0, tags.size());
  }
}
