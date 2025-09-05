package de.gwdg.metadataqa.marc.utils.marcspec;

import de.gwdg.metadataqa.marc.dao.Control001;
import de.gwdg.metadataqa.marc.dao.Control003;
import de.gwdg.metadataqa.marc.dao.Control005;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.Marc21Leader;
import de.gwdg.metadataqa.marc.dao.MarcControlField;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag020;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag080;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag084;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MarcSpecExtractorTest {
  @Test
  public void leader1() {
    MarcSpec spec = MarcSpecParser.parse("ldr");

    Marc21Record marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.setLeader("02157cas a2200517Ka 4500");
    marcRecord.addDataField(new DataField(Tag080.getInstance(), " ", " ", "a", "821.124"));

    Object extracted = MarcSpecExtractor.extract(marcRecord, spec);
    assertNotNull("Leader should not be null", extracted);
    assertEquals(Marc21Leader.class, extracted.getClass());
    Marc21Leader leader = (Marc21Leader) extracted;
    assertEquals("02157cas a2200517Ka 4500", leader.getLeaderString());
  }

  @Test
  public void leader2() {
    MarcSpec spec = MarcSpecParser.parse("LDR");

    Marc21Record marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.setLeader("02157cas a2200517Ka 4500");
    marcRecord.addDataField(new DataField(Tag080.getInstance(), " ", " ", "a", "821.124"));

    Object extracted = MarcSpecExtractor.extract(marcRecord, spec);
    assertNotNull("Leader should not be null", extracted);
    assertEquals(Marc21Leader.class, extracted.getClass());
    Marc21Leader leader = (Marc21Leader) extracted;
    assertEquals("02157cas a2200517Ka 4500", leader.getLeaderString());
  }

  @Test
  public void leader2_position() {
    MarcSpec spec = MarcSpecParser.parse("LDR/6");

    Marc21Record marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.setLeader("02157cas a2200517Ka 4500");
    marcRecord.addDataField(new DataField(Tag080.getInstance(), " ", " ", "a", "821.124"));

    String extracted = (String) MarcSpecExtractor.extract(marcRecord, spec);
    assertNotNull("Leader should not be null", extracted);
    assertEquals(String.class, extracted.getClass());
    assertEquals("a", extracted);
  }

  @Test
  public void leader2_range() {
    MarcSpec spec = MarcSpecParser.parse("LDR/0-4");

    Marc21Record marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.setLeader("02157cas a2200517Ka 4500");
    marcRecord.addDataField(new DataField(Tag080.getInstance(), " ", " ", "a", "821.124"));

    String extracted = (String) MarcSpecExtractor.extract(marcRecord, spec);
    assertNotNull("Leader should not be null", extracted);
    assertEquals(String.class, extracted.getClass());
    assertEquals("02157", extracted);
  }

  @Test
  public void controlField() {
    MarcSpec spec = MarcSpecParser.parse("001");

    Marc21Record marcRecord = new Marc21BibliographicRecord();
    marcRecord.setControl001(new Control001("010000178"));
    marcRecord.setControl003(new Control003("SIRSI"));
    marcRecord.setControl005(new Control005("20080331162830.0"));

    Control001 extracted = (Control001) MarcSpecExtractor.extract(marcRecord, spec);
    assertEquals(Control001.class, extracted.getClass());
    assertEquals("010000178", extracted.getContent());
  }

  @Test
  public void controlField_mask() {
    MarcSpec spec = MarcSpecParser.parse("00.");

    Marc21Record marcRecord = new Marc21BibliographicRecord();
    marcRecord.setControl001(new Control001("010000178"));
    marcRecord.setControl003(new Control003("SIRSI"));
    marcRecord.setControl005(new Control005("20080331162830.0"));

    List<MarcControlField> extracted = (List<MarcControlField>) MarcSpecExtractor.extract(marcRecord, spec);
    assertEquals(ArrayList.class, extracted.getClass());
    assertEquals(3, extracted.size());
    assertEquals("010000178", extracted.get(0).getContent());
    assertEquals("SIRSI", extracted.get(1).getContent());
    assertEquals("20080331162830.0", extracted.get(2).getContent());
  }

  @Test
  public void controlField_position() {
    MarcSpec spec = MarcSpecParser.parse("001/0");

    Marc21Record marcRecord = new Marc21BibliographicRecord();
    marcRecord.setControl001(new Control001("010000178"));
    marcRecord.setControl003(new Control003("SIRSI"));
    marcRecord.setControl005(new Control005("20080331162830.0"));

    String extracted = (String) MarcSpecExtractor.extract(marcRecord, spec);
    assertEquals(String.class, extracted.getClass());
    assertEquals("0", extracted);
  }

  @Test
  public void controlField_position_end() {
    MarcSpec spec = MarcSpecParser.parse("001/#");

    Marc21Record marcRecord = new Marc21BibliographicRecord();
    marcRecord.setControl001(new Control001("010000178"));
    marcRecord.setControl003(new Control003("SIRSI"));
    marcRecord.setControl005(new Control005("20080331162830.0"));

    String extracted = (String) MarcSpecExtractor.extract(marcRecord, spec);
    assertEquals(String.class, extracted.getClass());
    assertEquals("8", extracted);
  }

  @Test
  public void controlField_range() {
    MarcSpec spec = MarcSpecParser.parse("001/1-#");

    Marc21Record marcRecord = new Marc21BibliographicRecord();
    marcRecord.setControl001(new Control001("010000178"));
    marcRecord.setControl003(new Control003("SIRSI"));
    marcRecord.setControl005(new Control005("20080331162830.0"));

    String extracted = (String) MarcSpecExtractor.extract(marcRecord, spec);
    assertEquals(String.class, extracted.getClass());
    assertEquals("10000178", extracted);
  }

  @Test
  public void dataField() {
    MarcSpec spec = MarcSpecParser.parse("080");

    Marc21Record marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag020.getInstance(), " ", " ", "a", "0491001304"));
    marcRecord.addDataField(new DataField(Tag080.getInstance(), " ", " ", "a", "821.124"));

    List<DataField> extracted = (List<DataField>) MarcSpecExtractor.extract(marcRecord, spec);
    assertEquals(ArrayList.class, extracted.getClass());
    assertEquals(1, extracted.size());
    assertEquals(DataField.class, extracted.get(0).getClass());
    assertEquals("821.124", extracted.get(0).getSubfield("a").get(0).getValue());
  }

  @Test
  public void dataField_pattern1() {
    MarcSpec spec = MarcSpecParser.parse("08.");

    Marc21Record marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag020.getInstance(), " ", " ", "a", "0491001304"));
    marcRecord.addDataField(new DataField(Tag080.getInstance(), " ", " ", "a", "821.124"));

    Object extracted = MarcSpecExtractor.extract(marcRecord, spec);
    assertEquals(ArrayList.class, extracted.getClass());
    List<DataField> fields1 = (List<DataField>) extracted;
    assertEquals(1, fields1.size());
    assertEquals(DataField.class, fields1.get(0).getClass());
    List<DataField> fields = (List<DataField>) extracted;
    assertEquals("821.124", fields.get(0).getSubfield("a").get(0).getValue());
  }

  @Test
  public void dataField_pattern2() {
    MarcSpec spec = MarcSpecParser.parse("0.0");

    Marc21Record marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag020.getInstance(), " ", " ", "a", "0491001304"));
    marcRecord.addDataField(new DataField(Tag080.getInstance(), " ", " ", "a", "821.124"));

    Object extracted = MarcSpecExtractor.extract(marcRecord, spec);
    assertEquals(ArrayList.class, extracted.getClass());
    List<DataField> fields1 = (List<DataField>) extracted;
    assertEquals(2, fields1.size());
    assertEquals(DataField.class, fields1.get(0).getClass());
    List<DataField> fields = (List<DataField>) extracted;
    assertEquals("0491001304", fields.get(0).getSubfield("a").get(0).getValue());
    assertEquals("821.124", fields.get(1).getSubfield("a").get(0).getValue());
  }

  @Test
  public void dataField_pattern3() {
    MarcSpec spec = MarcSpecParser.parse("0.0");

    Marc21Record marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag020.getInstance(), " ", " ", "a", "0491001304"));
    marcRecord.addDataField(new DataField(Tag080.getInstance(), " ", " ", "a", "821.124"));
    marcRecord.addDataField(new DataField(Tag084.getInstance(), " ", " ", "a", "014"));

    Object extracted = MarcSpecExtractor.extract(marcRecord, spec);
    assertEquals(ArrayList.class, extracted.getClass());
    List<DataField> fields1 = (List<DataField>) extracted;
    assertEquals(2, fields1.size());
    assertEquals(DataField.class, fields1.get(0).getClass());
    List<DataField> fields = (List<DataField>) extracted;
    assertEquals("0491001304", fields.get(0).getSubfield("a").get(0).getValue());
    assertEquals("821.124", fields.get(1).getSubfield("a").get(0).getValue());
  }

  @Test
  public void indicator() {
    MarcSpec spec = MarcSpecParser.parse("080^1");

    Marc21Record marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag080.getInstance(), " ", " ", "a", "821.124"));
    marcRecord.addDataField(new DataField(Tag084.getInstance(), " ", " ", "a", "014"));

    Object extracted = MarcSpecExtractor.extract(marcRecord, spec);
    assertEquals(ArrayList.class, extracted.getClass());
    List<Object> fields1 = (List<Object>) extracted;
    assertEquals(1, fields1.size());
    assertEquals(String.class, fields1.get(0).getClass());
    List<String> values = (List<String>) extracted;
    assertEquals(" ", StringUtils.join(values, ", "));
  }

  @Test
  public void subfield() {
    MarcSpec spec = MarcSpecParser.parse("245$a");

    Marc21Record marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag080.getInstance(), " ", " ", "a", "821.124"));
    marcRecord.addDataField(new DataField(Tag084.getInstance(), " ", " ", "a", "014"));

    Object extracted = MarcSpecExtractor.extract(marcRecord, spec);
    assertEquals(ArrayList.class, extracted.getClass());
    List<Object> fields1 = (List<Object>) extracted;
    assertEquals(1, fields1.size());
    assertEquals(String.class, fields1.get(0).getClass());
    List<String> values = (List<String>) extracted;
    assertEquals(" ", StringUtils.join(values, ", "));
  }

  // 245$a
  // 245$a$b$c
  // ...$_$$
  // 300[0]
  // 300[1]
  // 300[0-2]
  // 300[1-#]
  // 300[#]
  // 300[#-1]
  // 300[0]$a
  // 300$a[0]
  // 300$a[#]
  // 300$a[#-1]
  // 880[1]^2
}
