package de.gwdg.metadataqa.marc.utils.keygenerator;

import de.gwdg.metadataqa.marc.dao.Control008;
import de.gwdg.metadataqa.marc.dao.MarcLeader;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008all00;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008all06;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008all07;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008all11;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008all15;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008all35;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008all38;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008all39;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book18;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book22;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book23;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book24;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book28;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book29;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book30;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book31;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book33;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag008.Tag008book34;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ControlFieldKeyGeneratorTest {

  Control008 field = new Control008(
    "981123p19981996enkmun   efhi           d",
    MarcLeader.Type.BOOKS
  );

  @Test
  public void testMarc() {
    var generator = new PositionalControlFieldKeyGenerator(
      field.getTag(),
      field.getMqTag(),
      SolrFieldType.MARC);
    assertNotNull(generator.forTag());
    assertEquals("008", generator.forTag());

    assertEquals("008_00-05", generator.forSubfield(Tag008all00.getInstance()));
    assertEquals("008_06", generator.forSubfield(Tag008all06.getInstance()));
    assertEquals("008_07-10", generator.forSubfield(Tag008all07.getInstance()));
    assertEquals("008_11-14", generator.forSubfield(Tag008all11.getInstance()));
    assertEquals("008_15-17", generator.forSubfield(Tag008all15.getInstance()));
    assertEquals("008_35-37", generator.forSubfield(Tag008all35.getInstance()));
    assertEquals("008_38", generator.forSubfield(Tag008all38.getInstance()));
    assertEquals("008_39", generator.forSubfield(Tag008all39.getInstance()));
    assertEquals("008_18-21", generator.forSubfield(Tag008book18.getInstance()));
    assertEquals("008_22", generator.forSubfield(Tag008book22.getInstance()));
    assertEquals("008_23", generator.forSubfield(Tag008book23.getInstance()));
    assertEquals("008_24-27", generator.forSubfield(Tag008book24.getInstance()));
    assertEquals("008_28", generator.forSubfield(Tag008book28.getInstance()));
    assertEquals("008_29", generator.forSubfield(Tag008book29.getInstance()));
    assertEquals("008_30", generator.forSubfield(Tag008book30.getInstance()));
    assertEquals("008_31", generator.forSubfield(Tag008book31.getInstance()));
    assertEquals("008_33", generator.forSubfield(Tag008book33.getInstance()));
    assertEquals("008_34", generator.forSubfield(Tag008book34.getInstance()));
  }

  @Test
  public void testHuman() {
    PositionalControlFieldKeyGenerator generator = new PositionalControlFieldKeyGenerator(
      field.getTag(),
      field.getMqTag(),
      SolrFieldType.HUMAN);
    assertNotNull(generator.forTag());
    assertEquals("GeneralInformation", generator.forTag());

    assertEquals("GeneralInformation_dateEnteredOnFile", generator.forSubfield(Tag008all00.getInstance()));
    assertEquals("GeneralInformation_typeOfDateOrPublicationStatus", generator.forSubfield(Tag008all06.getInstance()));
    assertEquals("GeneralInformation_date1", generator.forSubfield(Tag008all07.getInstance()));
    assertEquals("GeneralInformation_date2", generator.forSubfield(Tag008all11.getInstance()));
    assertEquals("GeneralInformation_placeOfPublicationProductionOrExecution", generator.forSubfield(Tag008all15.getInstance()));
    assertEquals("GeneralInformation_language", generator.forSubfield(Tag008all35.getInstance()));
    assertEquals("GeneralInformation_modifiedRecord", generator.forSubfield(Tag008all38.getInstance()));
    assertEquals("GeneralInformation_catalogingSource", generator.forSubfield(Tag008all39.getInstance()));
    assertEquals("GeneralInformation_illustrations", generator.forSubfield(Tag008book18.getInstance()));
    assertEquals("GeneralInformation_targetAudience", generator.forSubfield(Tag008book22.getInstance()));
    assertEquals("GeneralInformation_formOfItem", generator.forSubfield(Tag008book23.getInstance()));
    assertEquals("GeneralInformation_natureOfContents", generator.forSubfield(Tag008book24.getInstance()));
    assertEquals("GeneralInformation_governmentPublication", generator.forSubfield(Tag008book28.getInstance()));
    assertEquals("GeneralInformation_conferencePublication", generator.forSubfield(Tag008book29.getInstance()));
    assertEquals("GeneralInformation_festschrift", generator.forSubfield(Tag008book30.getInstance()));
    assertEquals("GeneralInformation_index", generator.forSubfield(Tag008book31.getInstance()));
    assertEquals("GeneralInformation_literaryForm", generator.forSubfield(Tag008book33.getInstance()));
    assertEquals("GeneralInformation_biography", generator.forSubfield(Tag008book34.getInstance()));
  }

  @Test
  public void testMixed() {
    PositionalControlFieldKeyGenerator generator = new PositionalControlFieldKeyGenerator(
      field.getTag(),
      field.getMqTag(),
      SolrFieldType.MIXED);
    assertNotNull(generator.forTag());
    assertEquals("008_GeneralInformation", generator.forTag());

    assertEquals("008all00_GeneralInformation_dateEnteredOnFile", generator.forSubfield(Tag008all00.getInstance()));
    assertEquals("008all06_GeneralInformation_typeOfDateOrPublicationStatus", generator.forSubfield(Tag008all06.getInstance()));
    assertEquals("008all07_GeneralInformation_date1", generator.forSubfield(Tag008all07.getInstance()));
    assertEquals("008all11_GeneralInformation_date2", generator.forSubfield(Tag008all11.getInstance()));
    assertEquals("008all15_GeneralInformation_placeOfPublicationProductionOrExecution", generator.forSubfield(Tag008all15.getInstance()));
    assertEquals("008all35_GeneralInformation_language", generator.forSubfield(Tag008all35.getInstance()));
    assertEquals("008all38_GeneralInformation_modifiedRecord", generator.forSubfield(Tag008all38.getInstance()));
    assertEquals("008all39_GeneralInformation_catalogingSource", generator.forSubfield(Tag008all39.getInstance()));
    assertEquals("008book18_GeneralInformation_illustrations", generator.forSubfield(Tag008book18.getInstance()));
    assertEquals("008book22_GeneralInformation_targetAudience", generator.forSubfield(Tag008book22.getInstance()));
    assertEquals("008book23_GeneralInformation_formOfItem", generator.forSubfield(Tag008book23.getInstance()));
    assertEquals("008book24_GeneralInformation_natureOfContents", generator.forSubfield(Tag008book24.getInstance()));
    assertEquals("008book28_GeneralInformation_governmentPublication", generator.forSubfield(Tag008book28.getInstance()));
    assertEquals("008book29_GeneralInformation_conferencePublication", generator.forSubfield(Tag008book29.getInstance()));
    assertEquals("008book30_GeneralInformation_festschrift", generator.forSubfield(Tag008book30.getInstance()));
    assertEquals("008book31_GeneralInformation_index", generator.forSubfield(Tag008book31.getInstance()));
    assertEquals("008book33_GeneralInformation_literaryForm", generator.forSubfield(Tag008book33.getInstance()));
    assertEquals("008book34_GeneralInformation_biography", generator.forSubfield(Tag008book34.getInstance()));
  }
}
