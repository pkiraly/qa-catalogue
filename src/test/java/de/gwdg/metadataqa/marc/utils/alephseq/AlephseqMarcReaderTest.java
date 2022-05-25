package de.gwdg.metadataqa.marc.utils.alephseq;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.Leader;
import de.gwdg.metadataqa.marc.dao.MarcControlField;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.MarcPositionalControlField;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.utils.marcreader.AlephseqMarcReader;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class AlephseqMarcReaderTest {

  @Test
  public void testMarcRecordFunctions() {
    Path path = null;
    try {
      path = FileUtils.getPath("alephseq/alephseq-example1.txt");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    MarcReader reader = new AlephseqMarcReader(path.toString());
    Record marc4jRecord = null;
    if (reader.hasNext())
      marc4jRecord = reader.next();
    assertNotNull(marc4jRecord);
    MarcRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord, Leader.Type.BOOKS, MarcVersion.GENT, "^");
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
    assertTrue(marcRecord.getControl006().isEmpty());
    assertTrue(marcRecord.getControl007().isEmpty());
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
    assertEquals(1, positionalControlFields.size());
    assertNotNull(positionalControlFields.get(0).getContent());

    assertTrue(marcRecord.exists("100"));

    List<String> subfields = marcRecord.extract("100", "a");
    assertEquals(1, subfields.size());
    assertEquals("Katz, Jerrold J.,", subfields.get(0));

    subfields = marcRecord.extract("100", "a", MarcRecord.RESOLVE.NONE);
    assertEquals(1, subfields.size());
    assertEquals("Katz, Jerrold J.,", subfields.get(0));

    subfields = marcRecord.extract("100", "a", MarcRecord.RESOLVE.RESOLVE);
    assertEquals(1, subfields.size());
    assertEquals("Katz, Jerrold J.,", subfields.get(0));

    subfields = marcRecord.extract("100", "a", MarcRecord.RESOLVE.BOTH);
    assertEquals(1, subfields.size());
    assertEquals("Katz, Jerrold J.,##Katz, Jerrold J.,", subfields.get(0));

    subfields = marcRecord.extract("100", "ind1");
    assertEquals(1, subfields.size());
    assertEquals("Surname", subfields.get(0));

    subfields = marcRecord.extract("100", "ind2");
    assertEquals(1, subfields.size());
    assertEquals(" ", subfields.get(0));

    List<String> tags = marcRecord.getUnhandledTags();
    assertNotNull(tags);
    assertEquals(0, tags.size());

    String formatted = marcRecord.format();
    String expected = "[010: Library of Congress Control Number]\n" +
      "LC control number: 78307846\n" +
      "[015: National Bibliography Number]\n" +
      "National bibliography number: GB***\n" +
      "[020: International Standard Book Number]\n" +
      "International Standard Book Number: 0855275103\n" +
      "[035: System Control Number]\n" +
      "System control number: (DLC) 78307846\n" +
      "[040: Cataloging Source]\n" +
      "Original cataloging agency: BE-GnUNI\n" +
      "[050: Library of Congress Call Number]\n" +
      "Existence in LC collection: Item is in LC\n" +
      "Source of call number: Assigned by LC\n" +
      "Classification number: P325\n" +
      "Item number: .K34 1977b\n" +
      "[082: Dewey Decimal Classification Number]\n" +
      "Type of edition: Full edition\n" +
      "Source of classification number: Assigned by LC\n" +
      "Classification number: 415\n" +
      "[100: Main Entry - Personal Name]\n" +
      "Type of personal name entry element: Surname\n" +
      "Personal name: Katz, Jerrold J.,\n" +
      "Dates associated with a name: 1932-2001\n" +
      "Authority record control number or standard number: (viaf)108327082\n" +
      "[245: Title Statement]\n" +
      "Title added entry: Added entry\n" +
      "Nonfiling characters: No nonfiling characters\n" +
      "Title: Propositional structure and illocutionary force :\n" +
      "Remainder of title: a study of the contribution of sentence meaning to speech acts /\n" +
      "Statement of responsibility, etc.: Jerrold J. Katz.\n" +
      "[260: Publication, Distribution, etc. (Imprint)]\n" +
      "Sequence of publishing statements: Not applicable/No information provided/Earliest available publisher\n" +
      "Place of publication, distribution, etc.: Hassocks :\n" +
      "Name of publisher, distributor, etc.: Harvester press,\n" +
      "Date of publication, distribution, etc.: 1977.\n" +
      "[300: Physical Description]\n" +
      "Extent: Xv, 249 p. ;\n" +
      "Dimensions: 25 cm.\n" +
      "[490: Series Statement]\n" +
      "Series tracing policy: Series not traced\n" +
      "Series statement: The Language and thought series\n" +
      "[504: Bibliography, etc. Note]\n" +
      "Bibliography, etc. note: Includes bibliographical references and index.\n" +
      "[650: Subject Added Entry - Topical Term]\n" +
      "Level of subject: No information provided\n" +
      "Thesaurus: Source specified in subfield $2\n" +
      "Topical term or geographic name entry element: Competence and performance (Linguistics).\n" +
      "Source of heading or term: Library of Congress subject headings (Washington, DC: LC, Cataloging Distribution Service)\n" +
      "[650: Subject Added Entry - Topical Term]\n" +
      "Level of subject: No information provided\n" +
      "Thesaurus: Source specified in subfield $2\n" +
      "Topical term or geographic name entry element: Generative grammar.\n" +
      "Source of heading or term: Library of Congress subject headings (Washington, DC: LC, Cataloging Distribution Service)\n" +
      "[650: Subject Added Entry - Topical Term]\n" +
      "Level of subject: No information provided\n" +
      "Thesaurus: Source specified in subfield $2\n" +
      "Topical term or geographic name entry element: Proposition (Logic).\n" +
      "Source of heading or term: Library of Congress subject headings (Washington, DC: LC, Cataloging Distribution Service)\n" +
      "[650: Subject Added Entry - Topical Term]\n" +
      "Level of subject: No information provided\n" +
      "Thesaurus: Source specified in subfield $2\n" +
      "Topical term or geographic name entry element: Semantics.\n" +
      "Source of heading or term: Faceted application of subject terminology (Dublin, Ohio: OCLC)\n" +
      "Authority record control number or standard number: (OCoLC)fst01112079\n" +
      "[650: Subject Added Entry - Topical Term]\n" +
      "Level of subject: No information provided\n" +
      "Thesaurus: Source specified in subfield $2\n" +
      "Topical term or geographic name entry element: Speech acts (Linguistics).\n" +
      "Source of heading or term: Library of Congress subject headings (Washington, DC: LC, Cataloging Distribution Service)\n" +
      "[852: Location]\n" +
      "Shelving scheme: Shelving control number\n" +
      "Shelving order: No information provided\n" +
      "Nonpublic note: LW\n" +
      "Sublocation or collection: LW55\n" +
      "Shelving location: L17\n" +
      "Shelving control number: LWBIB.L17.XIX.02.01.0242.03\n" +
      "Piece designation: 000010400338\n" +
      "[852: Location]\n" +
      "Shelving scheme: Shelving control number\n" +
      "Shelving order: No information provided\n" +
      "Nonpublic note: LW\n" +
      "Sublocation or collection: LW55\n" +
      "Shelving location: L18\n" +
      "Shelving control number: LWBIB.L18.L010.F.0128\n" +
      "Piece designation: 902-20\n" +
      "[852: Location]\n" +
      "Shelving scheme: Shelving control number\n" +
      "Shelving order: No information provided\n" +
      "Nonpublic note: LW\n" +
      "Sublocation or collection: LW55\n" +
      "Shelving location: L27\n" +
      "Shelving control number: LWBIB.L27.18.M.0201\n" +
      "Piece designation: 000010206368\n" +
      "[852: Location]\n" +
      "Shelving scheme: Shelving control number\n" +
      "Shelving order: No information provided\n" +
      "Nonpublic note: LW\n" +
      "Sublocation or collection: LW55\n" +
      "Shelving location: L33\n" +
      "Shelving control number: LWBIB.L33.D01.006.0063\n" +
      "Piece designation: 000010184913\n" +
      "[920: Used in the union catalog of Belgium]\n" +
      "Value: book\n";
    assertEquals(expected, formatted);

    formatted = marcRecord.formatAsMarc();
    expected = "010_a: 78307846\n" +
      "015_a: GB***\n" +
      "020_a: 0855275103\n" +
      "035_a: (DLC) 78307846\n" +
      "040_a: BE-GnUNI\n" +
      "050_ind1: Item is in LC\n" +
      "050_ind2: Assigned by LC\n" +
      "050_a: P325\n" +
      "050_b: .K34 1977b\n" +
      "082_ind1: Full edition\n" +
      "082_ind2: Assigned by LC\n" +
      "082_a: 415\n" +
      "100_ind1: Surname\n" +
      "100_a: Katz, Jerrold J.,\n" +
      "100_d: 1932-2001\n" +
      "100_0: (viaf)108327082\n" +
      "245_ind1: Added entry\n" +
      "245_ind2: No nonfiling characters\n" +
      "245_a: Propositional structure and illocutionary force :\n" +
      "245_b: a study of the contribution of sentence meaning to speech acts /\n" +
      "245_c: Jerrold J. Katz.\n" +
      "260_ind1: Not applicable/No information provided/Earliest available publisher\n" +
      "260_a: Hassocks :\n" +
      "260_b: Harvester press,\n" +
      "260_c: 1977.\n" +
      "300_a: Xv, 249 p. ;\n" +
      "300_c: 25 cm.\n" +
      "490_ind1: Series not traced\n" +
      "490_a: The Language and thought series\n" +
      "504_a: Includes bibliographical references and index.\n" +
      "650_ind1: No information provided\n" +
      "650_ind2: Source specified in subfield $2\n" +
      "650_a: Competence and performance (Linguistics).\n" +
      "650_2: Library of Congress subject headings (Washington, DC: LC, Cataloging Distribution Service)\n" +
      "650_ind1: No information provided\n" +
      "650_ind2: Source specified in subfield $2\n" +
      "650_a: Generative grammar.\n" +
      "650_2: Library of Congress subject headings (Washington, DC: LC, Cataloging Distribution Service)\n" +
      "650_ind1: No information provided\n" +
      "650_ind2: Source specified in subfield $2\n" +
      "650_a: Proposition (Logic).\n" +
      "650_2: Library of Congress subject headings (Washington, DC: LC, Cataloging Distribution Service)\n" +
      "650_ind1: No information provided\n" +
      "650_ind2: Source specified in subfield $2\n" +
      "650_a: Semantics.\n" +
      "650_2: Faceted application of subject terminology (Dublin, Ohio: OCLC)\n" +
      "650_0: (OCoLC)fst01112079\n" +
      "650_ind1: No information provided\n" +
      "650_ind2: Source specified in subfield $2\n" +
      "650_a: Speech acts (Linguistics).\n" +
      "650_2: Library of Congress subject headings (Washington, DC: LC, Cataloging Distribution Service)\n" +
      "852_ind1: Shelving control number\n" +
      "852_ind2: No information provided\n" +
      "852_x: LW\n" +
      "852_b: LW55\n" +
      "852_c: L17\n" +
      "852_j: LWBIB.L17.XIX.02.01.0242.03\n" +
      "852_p: 000010400338\n" +
      "852_ind1: Shelving control number\n" +
      "852_ind2: No information provided\n" +
      "852_x: LW\n" +
      "852_b: LW55\n" +
      "852_c: L18\n" +
      "852_j: LWBIB.L18.L010.F.0128\n" +
      "852_p: 902-20\n" +
      "852_ind1: Shelving control number\n" +
      "852_ind2: No information provided\n" +
      "852_x: LW\n" +
      "852_b: LW55\n" +
      "852_c: L27\n" +
      "852_j: LWBIB.L27.18.M.0201\n" +
      "852_p: 000010206368\n" +
      "852_ind1: Shelving control number\n" +
      "852_ind2: No information provided\n" +
      "852_x: LW\n" +
      "852_b: LW55\n" +
      "852_c: L33\n" +
      "852_j: LWBIB.L33.D01.006.0063\n" +
      "852_p: 000010184913\n" +
      "920_a: book\n";
    assertEquals(expected, formatted);

    Map<String, List<String>> pairs = marcRecord.getKeyValuePairs();
    assertEquals(92, pairs.size());

    List<String> hits = marcRecord.search("001", "000000002");
    assertEquals(1, hits.size());
    assertEquals("000000002", hits.get(0));

    hits = marcRecord.search("920$a", "book");
    assertEquals(1, hits.size());
    assertEquals("book", hits.get(0));

  }

  @Test
  public void testDeletedRecords() {
    Path path = null;
    try {
      path = FileUtils.getPath("alephseq/alephseq-example2.txt");
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
    MarcReader reader = new AlephseqMarcReader(path.toString());
    Record marc4jRecord = null;
    int activeRecords = 0;
    while (reader.hasNext()) {
      marc4jRecord = reader.next();
      assertNotNull(marc4jRecord.getControlNumber());
      activeRecords++;
    }
    assertEquals(7, ((AlephseqMarcReader)reader).getSkippedRecords());
    assertEquals(93, activeRecords);
  }

  @Test
  public void testUtf8() {
    Path path = null;
    try {
      path = FileUtils.getPath("alephseq/alephseq-example2.txt");
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
    MarcReader reader = new AlephseqMarcReader(path.toString());
    Record marc4jRecord = null;
    while (reader.hasNext()) {
      marc4jRecord = reader.next();
      if (marc4jRecord.getControlNumber().equals("000000008")) {
        MarcRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord, Leader.Type.BOOKS, MarcVersion.GENT, "^");
        assertEquals("München :", marcRecord.getDatafield("260").get(0).getSubfield("a").get(0).getValue());
      }
    }
  }

  @Test
  public void testNli() {
    Path path = null;
    try {
      path = FileUtils.getPath("alephseq/alephseq-example4.txt");
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
    MarcReader reader = new AlephseqMarcReader(path.toString(), AlephseqLine.TYPE.WITHOUT_L);
    Record marc4jRecord = null;
    while (reader.hasNext()) {
      marc4jRecord = reader.next();
      if (marc4jRecord.getControlNumber().equals("990017782740205171")) {
        MarcRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord, Leader.Type.BOOKS, MarcVersion.GENT, "^");
        assertEquals("1993.", marcRecord.getDatafield("260").get(0).getSubfield("c").get(0).getValue());
      }
    }
  }
}
