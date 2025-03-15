package de.gwdg.metadataqa.marc.utils.marcspec;

import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.TestUtils;
import de.gwdg.metadataqa.marc.dao.Control006;
import de.gwdg.metadataqa.marc.dao.Control008;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.Marc21Leader;
import de.gwdg.metadataqa.marc.dao.MarcLeader;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.tags.tags20x.Tag245;
import de.gwdg.metadataqa.marc.utils.QAMarcReaderFactory;
import org.junit.Before;
import org.junit.Test;
import org.marc4j.MarcReader;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Testing MarcSpec2Extractor.
 *
 * Most of the test cases are the interpretation of File_MARC_Reference's ReferenceTest test cases
 * https://github.com/MARCspec/File_MARC_Reference/blob/master/test/ReferenceTest.php
 *
 * Note MarcSpec2 does not implement the concept of subspecs, and subfield range
 */
public class ExtractorTest {

  Marc21Record record;

  @Before
  public void setUp() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.ISO, TestUtils.getPath("marcspec/music.mrc"));
    record = (Marc21Record) MarcFactory.createFromMarc4j(reader.next());
    assertTrue(record instanceof BibliographicRecord);
    assertTrue(record instanceof Marc21Record);
    assertNotNull(record);
  }

  @Test
  public void testGetAll() {
    MarcSpec spec = MarcSpecParser.parse("...");
    Object extracted = MarcSpecExtractor.extract(record, spec);
    // System.err.println(extracted);
    assertEquals(17, ((List) extracted).size());
  }

  @Test
  public void testGetLeader() {
    MarcSpec spec = MarcSpecParser.parse("LDR");
    Marc21Leader extracted = (Marc21Leader) MarcSpecExtractor.extract(record, spec);
    assertEquals("01145ncm  2200277 i 4500", extracted.getLeaderString());
  }

  @Test
  public void testGetLeaderSubstring() {
    MarcSpec spec = MarcSpecParser.parse("LDR/0-3");
    String extracted = (String) MarcSpecExtractor.extract(record, spec);
    assertEquals("0114", extracted);
  }

  @Test
  public void testGetLeaderSubstringReverse() {
    MarcSpec spec = MarcSpecParser.parse("LDR/#-3");
    String extracted = (String) MarcSpecExtractor.extract(record, spec);
    assertEquals("4500", extracted);
  }

  @Test
  public void testControlFieldPosition() {
    MarcSpec spec = MarcSpecParser.parse("001/0-3");
    String extracted = (String) MarcSpecExtractor.extract(record, spec);
    assertEquals("0000", extracted);
  }

  @Test
  public void testControlFieldPositionReserve() {
    MarcSpec spec = MarcSpecParser.parse("001/#-3");
    String extracted = (String) MarcSpecExtractor.extract(record, spec);
    assertEquals("3594", extracted);
  }

  @Test
  public void testRepeatableControlFieldPosition() {
    record.setControl006(new Control006("a|||||||||||||||p|", MarcLeader.Type.BOOKS));
    MarcSpec spec = MarcSpecParser.parse("006/0-3");
    List<String> extracted = (List<String>) MarcSpecExtractor.extract(record, spec);
    assertEquals(List.of("a|||"), extracted);
  }

  @Test
  public void testRepeatableControlFieldPositionReverse() {
    record.setControl006(new Control006("a|||||||||||||||p|", MarcLeader.Type.BOOKS));
    MarcSpec spec = MarcSpecParser.parse("006/#-3");
    List<String> extracted = (List<String>) MarcSpecExtractor.extract(record, spec);
    assertEquals(List.of("||p|"), extracted);
  }

  @Test
  public void testGetSingle() {
    MarcSpec spec = MarcSpecParser.parse("245");
    List<DataField> extracted = (List<DataField>) MarcSpecExtractor.extract(record, spec);
    assertEquals("DataField{245, ind1='0', ind2='4', subfields=[MarcSubfield{code='a', value='The Modern Jazz Quartet :'}, MarcSubfield{code='b', value='The legendary profile. --'}]}", extracted.get(0).toString());
  }

  @Test
  public void testGetRepeatable() {
    MarcSpec spec = MarcSpecParser.parse("700[1-2]");
    List<DataField> extracted = (List<DataField>) MarcSpecExtractor.extract(record, spec);
    assertEquals(2, extracted.size());
    assertEquals("[DataField{700, ind1='1', ind2='2', subfields=[MarcSubfield{code='a', value='Jackson, Milt.'}, MarcSubfield{code='t', value='Martyrs.'}, MarcSubfield{code='f', value='1977.'}]}, DataField{700, ind1='1', ind2='2', subfields=[MarcSubfield{code='a', value='Jackson, Milt.'}, MarcSubfield{code='t', value='Legendary profile.'}, MarcSubfield{code='f', value='1977.'}]}]", extracted.toString());
  }

  @Test
  public void testGetRepeatableReverse() {
    MarcSpec spec = MarcSpecParser.parse("700[#-1]");
    List<DataField> extracted = (List<DataField>) MarcSpecExtractor.extract(record, spec);
    assertEquals(2, extracted.size());
    assertEquals("[DataField{700, ind1='1', ind2='2', subfields=[MarcSubfield{code='a', value='Jackson, Milt.'}, MarcSubfield{code='t', value='Martyrs.'}, MarcSubfield{code='f', value='1977.'}]}, DataField{700, ind1='1', ind2='2', subfields=[MarcSubfield{code='a', value='Jackson, Milt.'}, MarcSubfield{code='t', value='Legendary profile.'}, MarcSubfield{code='f', value='1977.'}]}]", extracted.toString());
  }

  @Test
  public void testGetRepeatableReverse2() {
    MarcSpec spec = MarcSpecParser.parse("700[#-0]");
    List<DataField> extracted = (List<DataField>) MarcSpecExtractor.extract(record, spec);
    assertEquals(1, extracted.size());
    assertEquals("[DataField{700, ind1='1', ind2='2', subfields=[MarcSubfield{code='a', value='Jackson, Milt.'}, MarcSubfield{code='t', value='Legendary profile.'}, MarcSubfield{code='f', value='1977.'}]}]", extracted.toString());
  }

  @Test
  public void testGetRepeatableReverse4() {
    MarcSpec spec = MarcSpecParser.parse("700[3-3]");
    List<DataField> extracted = (List<DataField>) MarcSpecExtractor.extract(record, spec);
    assertEquals(0, extracted.size());
  }

  @Test
  public void testGetRepeatableWildcard() {
    MarcSpec spec = MarcSpecParser.parse("0..[1]");
    List<DataField> extracted = (List<DataField>) MarcSpecExtractor.extract(record, spec);
    assertEquals(1, extracted.size());
    assertEquals("[DataField{035, ind1='9', ind2=' ', subfields=[MarcSubfield{code='a', value='AAJ5802'}]}]", extracted.toString());
  }

  @Test
  public void testGetRepeatableWildCardSubstring() {
    MarcSpec spec = MarcSpecParser.parse("00./0-1");
    List<String> extracted = (List<String>) MarcSpecExtractor.extract(record, spec);
    assertEquals(List.of("00", "20", "80"), extracted);
  }

  @Test
  public void testGetSingleSubfield() {
    MarcSpec spec = MarcSpecParser.parse("245$a");
    List<String> extracted = (List<String>) MarcSpecExtractor.extract(record, spec);
    assertEquals(1, extracted.size());
    assertEquals(List.of("The Modern Jazz Quartet :"), extracted);
  }

  @Test
  public void testGetMultiSubfields() {
    MarcSpec spec = MarcSpecParser.parse("245$a$b");
    List<String> extracted = (List<String>) MarcSpecExtractor.extract(record, spec);
    assertEquals(2, extracted.size());
    assertEquals("The Modern Jazz Quartet :", extracted.get(0));
    assertEquals("The legendary profile. --", extracted.get(1));
  }

  @Test
  public void testGetSubfieldsOfRepeatedField() {
    MarcSpec spec = MarcSpecParser.parse("700$a");
    List<String> extracted = (List<String>) MarcSpecExtractor.extract(record, spec);
    assertEquals(3, extracted.size());
    assertEquals(List.of("Lewis, John,", "Jackson, Milt.", "Jackson, Milt."), extracted);
  }

  @Test
  public void testGetSubfieldByIndex() {
    // add a subfield
    SubfieldDefinition definition = Tag245.getInstance().getSubfield("a");
    MarcSubfield extraSubfield = new MarcSubfield(definition, "a", "a test");
    record.getDatafieldsByTag("245").get(0).getSubfields().add(extraSubfield);
    record.getDatafieldsByTag("245").get(0).indexSubfields();

    MarcSpec spec = MarcSpecParser.parse("245$a[1]");
    List<String> extracted = (List<String>) MarcSpecExtractor.extract(record, spec);
    assertEquals(1, extracted.size());
    assertEquals(List.of("a test"), extracted);

    spec = MarcSpecParser.parse("245$a[2]");
    extracted = (List<String>) MarcSpecExtractor.extract(record, spec);
    assertEquals(0, extracted.size());
  }

  @Test
  public void testIndicator1() {
    MarcSpec spec = MarcSpecParser.parse("035^1");
    List<String> extracted = (List<String>) MarcSpecExtractor.extract(record, spec);
    assertEquals(2, extracted.size());
    assertEquals(List.of(" ", "9"), extracted);
  }

  @Test
  public void testIndicator2() {
    MarcSpec spec = MarcSpecParser.parse("700^2");
    List<String> extracted = (List<String>) MarcSpecExtractor.extract(record, spec);
    assertEquals(3, extracted.size());
    assertEquals(List.of("2", "2", "2"), extracted);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIndicator3() {
    MarcSpec spec = MarcSpecParser.parse("008^1");
    try {
      Control008 extracted = (Control008) MarcSpecExtractor.extract(record, spec);
      assertNull(extracted);
    } catch (IllegalArgumentException e) {
      assertEquals("Control fields should not have indicator", e.getMessage());
      throw e;
    }
  }

  @Test
  public void testAllSubfields() {
    MarcSpec spec = MarcSpecParser.parse("...$a");
    List<String> extracted = (List<String>) MarcSpecExtractor.extract(record, spec);
    assertEquals(16, extracted.size());
    assertEquals(List.of("   77771106 ",
      "(CaOTUIC)15460184",
      "AAJ5802",
      "LC",
      "M1366",
      "The Modern Jazz Quartet :",
      "New York :",
      "score (72 p.) ;",
      "For piano, vibraphone, drums, and double bass.",
      "Lewis, J. Django.--Lewis, J. Plastic dreams (music from the film Kemek).--Lewis, J. Dancing (music from the film Kemek).--Lewis, J. Blues in A minor.--Lewis, J. Blues in Bâ­.--Lewis, J. Precious joy.--Jackson, M. The martyr.--Jackson, M. The legendary profile.",
      "Jazz.",
      "Motion picture music",
      "Lewis, John,",
      "Jackson, Milt.",
      "Jackson, Milt.",
      "The legendary profile."), extracted);
  }

  @Test
  public void testSubfieldSubstrings() {
    MarcSpec spec = MarcSpecParser.parse("245$a[0]/#-3");
    List<String> extracted = (List<String>) MarcSpecExtractor.extract(record, spec);
    assertEquals(1, extracted.size());
    assertEquals(List.of("et :"), extracted);
  }
}
