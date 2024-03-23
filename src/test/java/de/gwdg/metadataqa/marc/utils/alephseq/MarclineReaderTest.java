package de.gwdg.metadataqa.marc.utils.alephseq;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcControlField;
import de.gwdg.metadataqa.marc.dao.MarcLeader;
import de.gwdg.metadataqa.marc.dao.MarcPositionalControlField;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.utils.marcreader.MarclineReader;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MarclineReaderTest {

  @Test
  public void testMarcRecordFunctions() {
    Path path = null;
    try {
      path = FileUtils.getPath("marctxt/010000011.mrctxt");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    MarcReader reader = new MarclineReader(path.toString());
    Record marc4jRecord = null;
    if (reader.hasNext())
      marc4jRecord = reader.next();
    assertNotNull(marc4jRecord);
    Marc21Record marcRecord = (Marc21Record) MarcFactory.createFromMarc4j(marc4jRecord, MarcLeader.Type.BOOKS, MarcVersion.GENT, "^");
    assertNotNull(marcRecord);

    assertEquals("010000011", marcRecord.getId());
    List<DataField> fields = marcRecord.getDatafieldsByTag("035");
    assertNotNull(fields);
    assertEquals(3, fields.size());
    DataField field = fields.get(0);
    assertEquals("035", field.getTag());
    assertEquals(" ", field.getInd1());
    assertEquals(" ", field.getInd2());
    assertEquals("(DE-627)010000011", field.getSubfield("a").get(0).getValue());

    assertEquals("DE-627", marcRecord.getControl003().getContent());
    assertEquals("20180502143346.0", marcRecord.getControl005().getContent());
    assertTrue(marcRecord.getControl006().isEmpty());
    assertTrue(!marcRecord.getControl007().isEmpty());
    assertEquals(
      "861106s1985    xx |||||      10| ||ger c",
      marcRecord.getControl008().getContent()
    );

    assertEquals("010000011", marcRecord.getId(true));
    assertEquals("010000011", marcRecord.getId(false));

    List<MarcControlField> simpleControlFields = marcRecord.getSimpleControlfields();
    assertEquals(3, simpleControlFields.size());
    assertEquals("010000011", simpleControlFields.get(0).getContent());

    List<MarcPositionalControlField> positionalControlFields = marcRecord.getPositionalControlfields();
    assertEquals(2, positionalControlFields.size());
    assertNotNull(positionalControlFields.get(0).getContent());

    assertFalse(marcRecord.exists("100"));

    List<String> tags = marcRecord.getUnhandledTags();
    assertNotNull(tags);
    assertEquals(13, tags.size());

    String formatted = marcRecord.format();
    String expected = "[035: System Control Number]\n" +
      "System control number: (DE-627)010000011\n" +
      "[035: System Control Number]\n" +
      "System control number: (DE-599)GBV010000011\n" +
      "[035: System Control Number]\n" +
      "System control number: (OCoLC)255971256\n" +
      "[040: Cataloging Source]\n" +
      "Original cataloging agency: DE-627\n" +
      "Language of cataloging: German\n" +
      "Transcribing agency: DE-627\n" +
      "Description conventions: Regeln für die alphabetische Katalogisierung an wissenschaftlichen Bibliotheken (Berlin: Deutsches Bibliotheksinstitut)\n" +
      "[041: Language Code]\n" +
      "Translation indication: No information provided\n" +
      "Source of code: MARC language code\n" +
      "Language code of text/sound track or separate title: German\n" +
      "[044: Country of Publishing/Producing Entity Code]\n" +
      "ISO country code: XA-DE\n" +
      "[084: Other Classificaton Number]\n" +
      "Classification number: 58.55\n" +
      "Number source: Basisklassifikation\n" +
      "[245: Title Statement]\n" +
      "Title added entry: Added entry\n" +
      "Nonfiling characters: No nonfiling characters\n" +
      "Title: Neues Luftreinhalterecht\n" +
      "Statement of responsibility, etc.: Institut für Gewerbliche Wasserwirtschaft und Luftreinhaltung\n" +
      "[264: Production, Publication, Distribution, Manufacture, and Copyright Notice]\n" +
      "Sequence of statements: Not applicable/No information provided/Earliest\n" +
      "Function of entity: Publication\n" +
      "Place of production, publication, distribution, manufacture: Köln\n" +
      "Date of production, publication, distribution, manufacture, or copyright notice: 1985\n" +
      "[300: Physical Description]\n" +
      "Extent: 108 S\n" +
      "[336: Content Type]\n" +
      "Content type term: Text\n" +
      "Content type code: txt\n" +
      "Source: Term and code list for RDA content types\n" +
      "[337: Media Type]\n" +
      "Media type term: ohne Hilfsmittel zu benutzen\n" +
      "Media type code: n\n" +
      "Source: Term and code list for RDA media types\n" +
      "[338: Carrier Type]\n" +
      "Carrier type term: Band\n" +
      "Carrier type code: nc\n" +
      "Source: Term and code list for RDA carrier types\n" +
      "[490: Series Statement]\n" +
      "Series tracing policy: Series traced\n" +
      "Series statement: IWL-Forum\n" +
      "Volume/sequential designation: 1985,3\n" +
      "[490: Series Statement]\n" +
      "Series tracing policy: Series traced\n" +
      "Series statement: Berichte über die IWL-Kolloquien\n" +
      "Volume/sequential designation: 22,3\n" +
      "[655: Index Term - Genre/Form]\n" +
      "Type of heading: Basic\n" +
      "Thesaurus: Source specified in subfield $2\n" +
      "Genre/form data or focus term: Konferenzschrift\n" +
      "Authority record control number: (DE-588)1071861417\n" +
      "Authority record control number: (DE-627)826484824\n" +
      "Authority record control number: (DE-576)433375485\n" +
      "Source of term: Gemeinsame Normdatei: Beschreibung des Inhalts (Leipzig, Frankfurt: Deutsche Nationalbibliothek)\n" +
      "[689]\n" +
      "ind1: 0\n" +
      "ind2: 0\n" +
      "D: s\n" +
      "0: (DE-588)4036582-7\n" +
      "0: (DE-627)106241761\n" +
      "0: (DE-576)209019352\n" +
      "a: Luftreinhaltung\n" +
      "2: gnd\n" +
      "[689]\n" +
      "ind1: 0\n" +
      "ind2: 1\n" +
      "D: s\n" +
      "0: (DE-588)4048737-4\n" +
      "0: (DE-627)106189719\n" +
      "0: (DE-576)20907907X\n" +
      "a: Recht\n" +
      "2: gnd\n" +
      "[689]\n" +
      "ind1: 0\n" +
      "5: (DE-627)\n" +
      "[710: Added Entry - Corporate Name]\n" +
      "Type of corporate name entry element: Name in direct order\n" +
      "Type of added entry: No information provided\n" +
      "Corporate name or jurisdiction name as entry element: Institut für Gewerbliche Wasserwirtschaft und Luftreinhaltung\n" +
      "Authority record control number or standard number: (DE-588)2013822-2\n" +
      "Authority record control number or standard number: (DE-627)101809441\n" +
      "Authority record control number or standard number: (DE-576)19168161X\n" +
      "[810: Series Added Entry - Corporate Name]\n" +
      "Type of corporate name entry element: Name in direct order\n" +
      "Corporate name or jurisdiction name as entry element: Institut für Gewerbliche Wasserwirtschaft und Luftreinhaltung\n" +
      "Title of a work: IWL-Forum\n" +
      "Volume/sequential designation: 1985,3\n" +
      "9: 198503\n" +
      "Bibliographic record control number: (DE-627)13071562X\n" +
      "Bibliographic record control number: (DE-576)016263235\n" +
      "Bibliographic record control number: (DE-600)967681-8\n" +
      "International Standard Serial Number: 0537-796x\n" +
      "[810: Series Added Entry - Corporate Name]\n" +
      "Type of corporate name entry element: Name in direct order\n" +
      "Corporate name or jurisdiction name as entry element: Institut für Gewerbliche Wasserwirtschaft und Luftreinhaltung\n" +
      "Title of a work: Berichte über die IWL-Kolloquien\n" +
      "Volume/sequential designation: 22,3\n" +
      "9: 2203\n" +
      "Bibliographic record control number: (DE-627)129649562\n" +
      "Bibliographic record control number: (DE-576)015133893\n" +
      "Bibliographic record control number: (DE-600)253370-4\n" +
      "[912: Work identifier]\n" +
      "a: GBV_ILN_20\n" +
      "[912: Work identifier]\n" +
      "a: SYSFLAG_1\n" +
      "[912: Work identifier]\n" +
      "a: GBV_KXP\n" +
      "[912: Work identifier]\n" +
      "a: GBV_ILN_70\n" +
      "[912: Work identifier]\n" +
      "a: GBV_ILN_77\n" +
      "[936: CONSER/OCLC Miscellaneous Data]\n" +
      "ind1: b\n" +
      "ind2: k\n" +
      "CONSER/OCLC miscellaneous data: 58.55\n" +
      "j: Luftreinhaltung\n" +
      "0: (DE-627)106420798\n" +
      "[951]\n" +
      "a: BO\n" +
      "[980]\n" +
      "2: 20\n" +
      "1: 01\n" +
      "b: 018046525\n" +
      "d: 2648-9058\n" +
      "x: 0084\n" +
      "y: n\n" +
      "z: 01-01-71\n" +
      "[980]\n" +
      "2: 70\n" +
      "1: 01\n" +
      "b: 154540237X\n" +
      "f: Haus2\n" +
      "d: ZA 5253(1985,3)\n" +
      "e: f\n" +
      "x: 0089\n" +
      "y: r\n" +
      "z: 16-05-15\n" +
      "[980]\n" +
      "2: 77\n" +
      "1: 01\n" +
      "b: 158516338\n" +
      "d: 86.539\n" +
      "e: u\n" +
      "x: 3077\n" +
      "y: r\n" +
      "z: 26-08-03\n" +
      "[983]\n" +
      "2: 20\n" +
      "1: 00\n" +
      "8: 00\n" +
      "a: 628.512\n" +
      "[983]\n" +
      "2: 20\n" +
      "1: 00\n" +
      "8: 00\n" +
      "a: 349.6\n" +
      "[984]\n" +
      "2: 20\n" +
      "1: 01\n" +
      "a: 84\n" +
      "0: 26489058\n" +
      "[984]\n" +
      "2: 70\n" +
      "1: 01\n" +
      "a: 89\n" +
      "1: 39292950\n" +
      "[984]\n" +
      "2: 77\n" +
      "1: 01\n" +
      "a: HV14\n" +
      "0: 45138X\n" +
      "[985]\n" +
      "2: 77\n" +
      "1: 01\n" +
      "a: 86.539\n";
    expected = expected.replace("\n", System.lineSeparator());
    assertEquals(expected, formatted);

    formatted = marcRecord.formatAsMarc();
    expected = "035_a: (DE-627)010000011\n" +
      "035_a: (DE-599)GBV010000011\n" +
      "035_a: (OCoLC)255971256\n" +
      "040_a: DE-627\n" +
      "040_b: German\n" +
      "040_c: DE-627\n" +
      "040_e: Regeln für die alphabetische Katalogisierung an wissenschaftlichen Bibliotheken (Berlin: Deutsches Bibliotheksinstitut)\n" +
      "041_ind1: No information provided\n" +
      "041_ind2: MARC language code\n" +
      "041_a: German\n" +
      "044_c: XA-DE\n" +
      "084_a: 58.55\n" +
      "084_2: Basisklassifikation\n" +
      "245_ind1: Added entry\n" +
      "245_ind2: No nonfiling characters\n" +
      "245_a: Neues Luftreinhalterecht\n" +
      "245_c: Institut für Gewerbliche Wasserwirtschaft und Luftreinhaltung\n" +
      "264_ind1: Not applicable/No information provided/Earliest\n" +
      "264_ind2: Publication\n" +
      "264_a: Köln\n" +
      "264_c: 1985\n" +
      "300_a: 108 S\n" +
      "336_a: Text\n" +
      "336_b: txt\n" +
      "336_2: Term and code list for RDA content types\n" +
      "337_a: ohne Hilfsmittel zu benutzen\n" +
      "337_b: n\n" +
      "337_2: Term and code list for RDA media types\n" +
      "338_a: Band\n" +
      "338_b: nc\n" +
      "338_2: Term and code list for RDA carrier types\n" +
      "490_ind1: Series traced\n" +
      "490_a: IWL-Forum\n" +
      "490_v: 1985,3\n" +
      "490_ind1: Series traced\n" +
      "490_a: Berichte über die IWL-Kolloquien\n" +
      "490_v: 22,3\n" +
      "655_ind1: Basic\n" +
      "655_ind2: Source specified in subfield $2\n" +
      "655_a: Konferenzschrift\n" +
      "655_0: (DE-588)1071861417\n" +
      "655_0: (DE-627)826484824\n" +
      "655_0: (DE-576)433375485\n" +
      "655_2: Gemeinsame Normdatei: Beschreibung des Inhalts (Leipzig, Frankfurt: Deutsche Nationalbibliothek)\n" +
      "689_D: s\n" +
      "689_0: (DE-588)4036582-7\n" +
      "689_0: (DE-627)106241761\n" +
      "689_0: (DE-576)209019352\n" +
      "689_a: Luftreinhaltung\n" +
      "689_2: gnd\n" +
      "689_D: s\n" +
      "689_0: (DE-588)4048737-4\n" +
      "689_0: (DE-627)106189719\n" +
      "689_0: (DE-576)20907907X\n" +
      "689_a: Recht\n" +
      "689_2: gnd\n" +
      "689_5: (DE-627)\n" +
      "710_ind1: Name in direct order\n" +
      "710_ind2: No information provided\n" +
      "710_a: Institut für Gewerbliche Wasserwirtschaft und Luftreinhaltung\n" +
      "710_0: (DE-588)2013822-2\n" +
      "710_0: (DE-627)101809441\n" +
      "710_0: (DE-576)19168161X\n" +
      "810_ind1: Name in direct order\n" +
      "810_a: Institut für Gewerbliche Wasserwirtschaft und Luftreinhaltung\n" +
      "810_t: IWL-Forum\n" +
      "810_v: 1985,3\n" +
      "810_9: 198503\n" +
      "810_w: (DE-627)13071562X\n" +
      "810_w: (DE-576)016263235\n" +
      "810_w: (DE-600)967681-8\n" +
      "810_x: 0537-796x\n" +
      "810_ind1: Name in direct order\n" +
      "810_a: Institut für Gewerbliche Wasserwirtschaft und Luftreinhaltung\n" +
      "810_t: Berichte über die IWL-Kolloquien\n" +
      "810_v: 22,3\n" +
      "810_9: 2203\n" +
      "810_w: (DE-627)129649562\n" +
      "810_w: (DE-576)015133893\n" +
      "810_w: (DE-600)253370-4\n" +
      "912_a: GBV_ILN_20\n" +
      "912_a: SYSFLAG_1\n" +
      "912_a: GBV_KXP\n" +
      "912_a: GBV_ILN_70\n" +
      "912_a: GBV_ILN_77\n" +
      "936_a: 58.55\n" +
      "936_j: Luftreinhaltung\n" +
      "936_0: (DE-627)106420798\n" +
      "951_a: BO\n" +
      "980_2: 20\n" +
      "980_1: 01\n" +
      "980_b: 018046525\n" +
      "980_d: 2648-9058\n" +
      "980_x: 0084\n" +
      "980_y: n\n" +
      "980_z: 01-01-71\n" +
      "980_2: 70\n" +
      "980_1: 01\n" +
      "980_b: 154540237X\n" +
      "980_f: Haus2\n" +
      "980_d: ZA 5253(1985,3)\n" +
      "980_e: f\n" +
      "980_x: 0089\n" +
      "980_y: r\n" +
      "980_z: 16-05-15\n" +
      "980_2: 77\n" +
      "980_1: 01\n" +
      "980_b: 158516338\n" +
      "980_d: 86.539\n" +
      "980_e: u\n" +
      "980_x: 3077\n" +
      "980_y: r\n" +
      "980_z: 26-08-03\n" +
      "983_2: 20\n" +
      "983_1: 00\n" +
      "983_8: 00\n" +
      "983_a: 628.512\n" +
      "983_2: 20\n" +
      "983_1: 00\n" +
      "983_8: 00\n" +
      "983_a: 349.6\n" +
      "984_2: 20\n" +
      "984_1: 01\n" +
      "984_a: 84\n" +
      "984_0: 26489058\n" +
      "984_2: 70\n" +
      "984_1: 01\n" +
      "984_a: 89\n" +
      "984_1: 39292950\n" +
      "984_2: 77\n" +
      "984_1: 01\n" +
      "984_a: HV14\n" +
      "984_0: 45138X\n" +
      "985_2: 77\n" +
      "985_1: 01\n" +
      "985_a: 86.539\n";
    expected = expected.replace("\n", System.lineSeparator());
    assertEquals(expected, formatted);

    Map<String, List<String>> pairs = marcRecord.getKeyValuePairs();
    assertEquals(140, pairs.size());

    List<String> hits = marcRecord.search("001", "010000011");
    assertEquals(1, hits.size());
    assertEquals("010000011", hits.get(0));

    hits = marcRecord.search("912$a", "GBV_ILN_20");
    assertEquals(1, hits.size());
    assertEquals("GBV_ILN_20", hits.get(0));
  }

  @Test
  public void testAlmaReplacement() {
    Path path = null;
    try {
      path = FileUtils.getPath("marctxt/with-alma-character.mrctxt");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    MarcReader reader = new MarclineReader(path.toString());
    Record marc4jRecord = null;
    if (reader.hasNext())
      marc4jRecord = reader.next();
    assertNotNull(marc4jRecord);
    Marc21Record marcRecord = (Marc21Record) MarcFactory.createFromMarc4j(marc4jRecord, MarcLeader.Type.BOOKS, MarcVersion.GENT, "#");
    assertNotNull(marcRecord);
    assertEquals("tu   ", marcRecord.getControl007().get(0).getContent());
  }
}
