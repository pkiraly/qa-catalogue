package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.model.selector.JsonSelector;
import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.cli.utils.IteratorResponse;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.utils.alephseq.AlephseqLine;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.marc4j.marc.Record;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MarcFactoryTest {

  @Test
  public void mainTest() throws IOException, URISyntaxException {
    JsonSelector selector = new JsonSelector(FileUtils.readFirstLineFromResource("general/verbund-tit.001.0000000.formatted.json"));

    Marc21Record marcRecord = (Marc21Record) MarcFactory.create(selector, MarcVersion.DNB);
    assertNotNull(marcRecord);
    assertNotNull("Leader should not be null", marcRecord.getLeader());
    // System.err.println(record.format());
    // System.err.println(record.formatAsMarc());
    // System.err.println(record.formatForIndex());
    // System.err.println(record.getKeyValuePairs());
    Map<String, List<String>> pairs = marcRecord.getKeyValuePairs(SolrFieldType.HUMAN);
    assertEquals(124, pairs.size());
    Set<String> keys = pairs.keySet();
    // keys.remove("GentLocallyDefinedField");
    // keys.remove("BemerkungenZurTitelaufnahme");
    assertEquals(
      "type, Leader, Leader_recordLength, Leader_recordStatus, Leader_typeOfRecord, " +
      "Leader_bibliographicLevel, Leader_typeOfControl, Leader_characterCodingScheme, " +
      "Leader_indicatorCount, Leader_subfieldCodeCount, Leader_baseAddressOfData, " +
      "Leader_encodingLevel, Leader_descriptiveCatalogingForm, Leader_multipartResourceRecordLevel, " +
      "Leader_lengthOfTheLengthOfFieldPortion, Leader_lengthOfTheStartingCharacterPositionPortion, " +
      "Leader_lengthOfTheImplementationDefinedPortion, Leader_undefined, ControlNumber, ControlNumberIdentifier, " +
      "LatestTransactionTime, PhysicalDescription, PhysicalDescription_categoryOfMaterial, " +
      "PhysicalDescription_specificMaterialDesignation, GeneralInformation, " +
      "GeneralInformation_dateEnteredOnFile, GeneralInformation_typeOfDateOrPublicationStatus, " +
      "GeneralInformation_date1, GeneralInformation_date2, " +
      "GeneralInformation_placeOfPublicationProductionOrExecution, GeneralInformation_language, " +
      "GeneralInformation_modifiedRecord, GeneralInformation_catalogingSource, " +
      "GeneralInformation_frequency, GeneralInformation_regularity, " +
      "GeneralInformation_typeOfContinuingResource, GeneralInformation_formOfOriginalItem, " +
      "GeneralInformation_formOfItem, GeneralInformation_natureOfEntireWork, " +
      "GeneralInformation_natureOfContents, GeneralInformation_governmentPublication, " +
      "GeneralInformation_conferencePublication, " +
      "GeneralInformation_originalAlphabetOrScriptOfTitle, GeneralInformation_entryConvention, " +
      "IdIntifiedByLocal_source, IdIntifiedByLocal, IdIntifiedByLocal_agency, " +
      "Issn_levelOfInternationalInterest, Issn, " +
      "SystemControlNumber_organizationCode, " +
      "SystemControlNumber, SystemControlNumber_recordNumber, SystemControlNumber_organization, " +
      "AdminMetadata_languageOfCataloging, " +
      "AdminMetadata_descriptionConventions, AdminMetadata_transcribingAgency, AdminMetadata_catalogingAgency, " +
      "Language_translationIndication, Language, Language_sourceOfCode, Place_country, ClassificationDdc_full, " +
      "ClassificationDdc_editionType, ClassificationDdc_classificationSource, ClassificationDdc, " +
      "Classification_classificationPortion, Classification_classificationPortion_zdbs, " +
      "Classification_source, Classification_full, Title_subtitle, " +
      "Title_responsibilityStatement, Title_mainTitle, Title_titleAddedEntry, " +
      "Title_nonfilingCharacters, Title_partName, ParallelTitle_mainTitle, ParallelTitle_type, " +
      "ParallelTitle_displayText, ParallelTitle_noteAndAddedEntry, " +
      "Publication_sequenceOfPublishingStatements, Publication_agent, Publication_place, " +
      "DatesOfPublication, DatesOfPublication_format, NumberingPeculiarities, " +
      "BemerkungenZurTitelaufnahme, " +
      "RSWKKette_nummerDesKettengliedes, RSWKKette_0, RSWKKette_a, " +
      "RSWKKette_nummerDerRSWKKette, RSWKKette_D, RSWKKette_5, AddedCorporateName, AddedCorporateName_full, " +
      "AddedCorporateName_authorityRecordControlNumber, " +
      "AddedCorporateName_authorityRecordControlNumber_recordNumber, " +
      "AddedCorporateName_authorityRecordControlNumber_organization, " +
      "AddedCorporateName_authorityRecordControlNumber_organizationCode, " +
      "AddedCorporateName_entryType, AddedCorporateName_nameType, PartOf_relation, PartOf_displayConstant, " +
      "PartOf_recordControlNumber, PartOf_noteController, PartOf_recordControlNumber_recordNumber, " +
      "PartOf_recordControlNumber_organization, PartOf_recordControlNumber_organizationCode, PartOf, " +
      "PrecededBy, PrecededBy_relation, PrecededBy_recordControlNumber_organization, " +
      "PrecededBy_recordControlNumber_recordNumber, PrecededBy_typeOfRelationship, " +
      "PrecededBy_recordControlNumber, PrecededBy_recordControlNumber_organizationCode, " +
      "PrecededBy_noteController, Bestandsinformationen, Bestandsinformationen_9, " +
      "Bestandsinformationen_d, " +
      "Bestandsinformationen_artDerRessource, Bestandsinformationen_b, Bestandsinformationen_c, " +
      "Bestandsinformationen_h, Bestandsinformationen_g",
      StringUtils.join(keys, ", "));

    assertEquals("Continuing Resources", pairs.get("type").get(0));
    assertEquals("02703cas a2200481   4500", pairs.get("Leader").get(0));
    assertEquals("02703", pairs.get("Leader_recordLength").get(0));
    assertEquals("Corrected or revised", pairs.get("Leader_recordStatus").get(0));
    assertEquals("Language material", pairs.get("Leader_typeOfRecord").get(0));
    assertEquals("Serial", pairs.get("Leader_bibliographicLevel").get(0));
    assertEquals("No specified type", pairs.get("Leader_typeOfControl").get(0));
    assertEquals("UCS/Unicode", pairs.get("Leader_characterCodingScheme").get(0));
    assertEquals("Number of character positions used for indicators", pairs.get("Leader_indicatorCount").get(0));
    assertEquals("Number of character positions used for a subfield code", pairs.get("Leader_subfieldCodeCount").get(0));
    assertEquals("00481", pairs.get("Leader_baseAddressOfData").get(0));
    assertEquals("Full level", pairs.get("Leader_encodingLevel").get(0));
    assertEquals("Non-ISBD", pairs.get("Leader_descriptiveCatalogingForm").get(0));
    assertEquals("Not specified or not applicable", pairs.get("Leader_multipartResourceRecordLevel").get(0));
    assertEquals("Number of characters in the length-of-field portion of a Directory entry", pairs.get("Leader_lengthOfTheLengthOfFieldPortion").get(0));
    assertEquals("Number of characters in the starting-character-position portion of a Directory entry", pairs.get("Leader_lengthOfTheStartingCharacterPositionPortion").get(0));
    assertEquals("Number of characters in the implementation-defined portion of a Directory entry", pairs.get("Leader_lengthOfTheImplementationDefinedPortion").get(0));
    assertEquals("000000027", pairs.get("ControlNumber").get(0));
    assertEquals("DE-576", pairs.get("ControlNumberIdentifier").get(0));
    assertEquals("20150107102000.0", pairs.get("LatestTransactionTime").get(0));
    assertEquals("tu", pairs.get("PhysicalDescription").get(0));
    assertEquals("Text", pairs.get("PhysicalDescription_categoryOfMaterial").get(0));
    assertEquals("Unspecified", pairs.get("PhysicalDescription_specificMaterialDesignation").get(0));
    assertEquals("850101d19912003xx    p   b   0    0ger c", pairs.get("GeneralInformation").get(0));
    assertEquals("850101", pairs.get("GeneralInformation_dateEnteredOnFile").get(0));
    assertEquals("Continuing resource ceased publication", pairs.get("GeneralInformation_typeOfDateOrPublicationStatus").get(0));
    assertEquals("1991", pairs.get("GeneralInformation_date1").get(0));
    assertEquals("2003", pairs.get("GeneralInformation_date2").get(0));
    assertEquals("No place, unknown, or undetermined", pairs.get("GeneralInformation_placeOfPublicationProductionOrExecution").get(0));
    assertEquals("German", pairs.get("GeneralInformation_language").get(0));
    assertEquals("Not modified", pairs.get("GeneralInformation_modifiedRecord").get(0));
    assertEquals("Cooperative cataloging program", pairs.get("GeneralInformation_catalogingSource").get(0));
    assertEquals("No determinable frequency", pairs.get("GeneralInformation_frequency").get(0));
    assertEquals(" ", pairs.get("GeneralInformation_regularity").get(0));
    assertEquals("Periodical", pairs.get("GeneralInformation_typeOfContinuingResource").get(0));
    assertEquals("None of the following", pairs.get("GeneralInformation_formOfOriginalItem").get(0));
    assertEquals("None of the following", pairs.get("GeneralInformation_formOfItem").get(0));
    assertEquals("Not specified", pairs.get("GeneralInformation_natureOfEntireWork").get(0));
    assertEquals("Bibliographies, Not specified", pairs.get("GeneralInformation_natureOfContents").get(0));
    assertEquals("Not a government publication", pairs.get("GeneralInformation_governmentPublication").get(0));
    assertEquals("Not a conference publication", pairs.get("GeneralInformation_conferencePublication").get(0));
    assertEquals("No alphabet or script given/No key title", pairs.get("GeneralInformation_originalAlphabetOrScriptOfTitle").get(0));
    assertEquals("Successive entry", pairs.get("GeneralInformation_entryConvention").get(0));

    assertEquals(2, pairs.get("IdIntifiedByLocal_source").size());
    assertEquals("Deutsche Nationalbibliothek", pairs.get("IdIntifiedByLocal_source").get(0));
    assertEquals("Zeitschriftendatenbank (ZDB)", pairs.get("IdIntifiedByLocal_source").get(1));

    assertEquals(2, pairs.get("IdIntifiedByLocal").size());
    assertEquals("01588046X", pairs.get("IdIntifiedByLocal").get(0));
    assertEquals("1056377-5", pairs.get("IdIntifiedByLocal").get(1));

    assertEquals(2, pairs.get("IdIntifiedByLocal_agency").size());
    assertEquals("Source specified in subfield $2",
      pairs.get("IdIntifiedByLocal_agency").get(0));
    assertEquals("Source specified in subfield $2",
      pairs.get("IdIntifiedByLocal_agency").get(1));

    assertEquals(1, pairs.get("Issn_levelOfInternationalInterest").size());
    assertEquals("No level specified", pairs.get("Issn_levelOfInternationalInterest").get(0));

    assertEquals(1, pairs.get("Issn").size());
    assertEquals("0939-0480", pairs.get("Issn").get(0));

    assertEquals(2, pairs.get("SystemControlNumber_organizationCode").size());
    assertEquals("OCoLC", pairs.get("SystemControlNumber_organizationCode").get(0));
    assertEquals("DE-599", pairs.get("SystemControlNumber_organizationCode").get(1));

    assertEquals(2, pairs.get("SystemControlNumber").size());
    assertEquals("(OCoLC)231477039", pairs.get("SystemControlNumber").get(0));
    assertEquals("(DE-599)ZDB1056377-5", pairs.get("SystemControlNumber").get(1));

    assertEquals(2, pairs.get("SystemControlNumber_recordNumber").size());
    assertEquals("231477039", pairs.get("SystemControlNumber_recordNumber").get(0));
    assertEquals("ZDB1056377-5", pairs.get("SystemControlNumber_recordNumber").get(1));

    assertEquals(1, pairs.get("SystemControlNumber_organization").size());
    assertEquals("Arbeitsgemeinschaft der Verbundsysteme", pairs.get("SystemControlNumber_organization").get(0));

    assertEquals(1, pairs.get("AdminMetadata_languageOfCataloging").size());
    assertEquals("German", pairs.get("AdminMetadata_languageOfCataloging").get(0));

    assertEquals(1, pairs.get("AdminMetadata_descriptionConventions").size());
    assertEquals(
      "Regeln für die alphabetische Katalogisierung an wissenschaftlichen Bibliotheken (Berlin: Deutsches Bibliotheksinstitut)",
      pairs.get("AdminMetadata_descriptionConventions").get(0));

    assertEquals(1, pairs.get("AdminMetadata_transcribingAgency").size());
    assertEquals("Bibliotheksservice-Zentrum Baden-Württemberg (BSZ)",
      pairs.get("AdminMetadata_transcribingAgency").get(0));

    assertEquals(1, pairs.get("AdminMetadata_catalogingAgency").size());
    assertEquals("Bibliotheksservice-Zentrum Baden-Württemberg (BSZ)",
      pairs.get("AdminMetadata_catalogingAgency").get(0));

    assertEquals(1, pairs.get("Language_translationIndication").size());
    assertEquals("No information provided", pairs.get("Language_translationIndication").get(0));

    assertEquals(1, pairs.get("Language").size());
    assertEquals("German", pairs.get("Language").get(0));

    assertEquals(1, pairs.get("Language_sourceOfCode").size());
    assertEquals("MARC language code", pairs.get("Language_sourceOfCode").get(0));

    assertEquals(1, pairs.get("Place_country").size());
    assertEquals("XA-DE", pairs.get("Place_country").get(0));

    assertEquals(1, pairs.get("ClassificationDdc_editionType").size());
    assertEquals("Full edition", pairs.get("ClassificationDdc_editionType").get(0));

    assertEquals(1, pairs.get("ClassificationDdc_classificationSource").size());
    assertEquals("No information provided",
      pairs.get("ClassificationDdc_classificationSource").get(0));

    assertEquals(1, pairs.get("ClassificationDdc").size());
    assertEquals("010", pairs.get("ClassificationDdc").get(0));

    assertEquals(1, pairs.get("Classification_classificationPortion").size());
    assertEquals("110", pairs.get("Classification_classificationPortion").get(0));

    assertEquals(1, pairs.get("Classification_source").size());
    assertEquals("ZDB-Systematik = ZDB-Classification",
      pairs.get("Classification_source").get(0));

    assertEquals(1, pairs.get("Title_subtitle").size());
    assertEquals("Amtsblatt /", pairs.get("Title_subtitle").get(0));

    assertEquals(1, pairs.get("Title_responsibilityStatement").size());
    assertEquals("Bearb. u. Hrsg.: Die Deutsche Bibliothek (Deutsche Bücherei Leipzig, Deutsche Bibliothek Frankfurt a.M., Deutsches Musikarchiv Berlin).",
      pairs.get("Title_responsibilityStatement").get(0));

    assertEquals(1, pairs.get("Title_mainTitle").size());
    assertEquals("Deutsche Nationalbibliografie und Bibliografie der im Ausland erschienenen deutschsprachigen Veröffentlichungen :",
      pairs.get("Title_mainTitle").get(0));

    assertEquals(1, pairs.get("Title_titleAddedEntry").size());
    assertEquals("Added entry", pairs.get("Title_titleAddedEntry").get(0));

    assertEquals(1, pairs.get("Title_nonfilingCharacters").size());
    assertEquals("No nonfiling characters",
      pairs.get("Title_nonfilingCharacters").get(0));

    assertEquals(1, pairs.get("Title_partName").size());
    assertEquals("A B, Monographien und Periodika des Verlagsbuchhandels und außerhalb des Verlagsbuchhandels : wöchentliches Verzeichnis ; Register",
      pairs.get("Title_partName").get(0));

    assertEquals(4, pairs.get("ParallelTitle_mainTitle").size());
    assertEquals(
      "Deutsche Nationalbibliographie und Bibliographie der im Ausland erschienenen deutschsprachigen Veröffentlichungen",
      pairs.get("ParallelTitle_mainTitle").get(0));
    assertEquals("Register",
      pairs.get("ParallelTitle_mainTitle").get(1));
    assertEquals("Deutsche Nationalbibliographie und Bibliographie der im Ausland erschienenen deutschsprachigen Veröffentlichungen",
      pairs.get("ParallelTitle_mainTitle").get(2));
    assertEquals("Deutsche Nationalbibliografie und Bibliografie der im Ausland erschienenen deutschsprachigen Veröffentlichungen / A B",
      pairs.get("ParallelTitle_mainTitle").get(3));

    assertEquals(4, pairs.get("ParallelTitle_type").size());
    assertEquals("No type specified", pairs.get("ParallelTitle_type").get(0));
    assertEquals("No type specified", pairs.get("ParallelTitle_type").get(1));
    assertEquals("No type specified", pairs.get("ParallelTitle_type").get(2));
    assertEquals("No type specified", pairs.get("ParallelTitle_type").get(3));

    assertEquals(1, pairs.get("ParallelTitle_displayText").size());
    assertEquals("Hauptsacht. anfangs: ", pairs.get("ParallelTitle_displayText").get(0));

    assertEquals(4, pairs.get("ParallelTitle_noteAndAddedEntry").size());
    assertEquals("Note, added entry",
      pairs.get("ParallelTitle_noteAndAddedEntry").get(0));
    assertEquals("No note, added entry",
      pairs.get("ParallelTitle_noteAndAddedEntry").get(1));
    assertEquals("No note, added entry", pairs.get("ParallelTitle_noteAndAddedEntry").get(2));
    assertEquals("9", pairs.get("ParallelTitle_noteAndAddedEntry").get(3));

    assertEquals(1, pairs.get("Publication_sequenceOfPublishingStatements").size());
    assertEquals("Not applicable/No information provided/Earliest available publisher",
      pairs.get("Publication_sequenceOfPublishingStatements").get(0));

    assertEquals(1, pairs.get("Publication_agent").size());
    assertEquals("Buchhändler-Vereinigung", pairs.get("Publication_agent").get(0));

    assertEquals(1, pairs.get("Publication_place").size());
    assertEquals("Frankfurt, M. :", pairs.get("Publication_place").get(0));

    assertEquals(1, pairs.get("DatesOfPublication").size());
    assertEquals("1991 - 2003; damit Ersch. eingest.", pairs.get("DatesOfPublication").get(0));

    assertEquals(1, pairs.get("DatesOfPublication_format").size());
    assertEquals("Unformatted note", pairs.get("DatesOfPublication_format").get(0));

    assertEquals(1, pairs.get("NumberingPeculiarities").size());
    assertEquals("Ersch. wöchentlich, jedes 4.-5. Heft als Monatsregister",
      pairs.get("NumberingPeculiarities").get(0));

    assertEquals(2, pairs.get("BemerkungenZurTitelaufnahme").size());
    assertEquals("84!(09-02-04)", pairs.get("BemerkungenZurTitelaufnahme").get(0));
    assertEquals("84!(09-02-04)", pairs.get("BemerkungenZurTitelaufnahme").get(1));

    assertEquals(4, pairs.get("RSWKKette_nummerDesKettengliedes").size());
    assertEquals("Nummer des Kettengliedes: 0",
      pairs.get("RSWKKette_nummerDesKettengliedes").get(0));
    assertEquals("Nummer des Kettengliedes: 1",
      pairs.get("RSWKKette_nummerDesKettengliedes").get(1));
    assertEquals("Nummer des Kettengliedes: 2",
      pairs.get("RSWKKette_nummerDesKettengliedes").get(2));
    assertEquals("Abschluss einer RSWK-Kettenfolge, Feld enthält dann zwei $5",
      pairs.get("RSWKKette_nummerDesKettengliedes").get(3));

    assertEquals(6, pairs.get("RSWKKette_0").size());
    assertEquals(
      List.of("(DE-588)4011882-4", "(DE-576)208896155", "(DE-588)4013134-8", "(DE-576)208901906", "(DE-588)4006432-3", "(DE-576)208865578"),
      pairs.get("RSWKKette_0"));

    assertEquals(3, pairs.get("RSWKKette_a").size());
    assertEquals("Deutschland", pairs.get("RSWKKette_a").get(0));
    assertEquals("Druckwerk", pairs.get("RSWKKette_a").get(1));
    assertEquals("Bibliografie", pairs.get("RSWKKette_a").get(2));

    assertEquals(4, pairs.get("RSWKKette_nummerDerRSWKKette").size());
    assertEquals("Nummer der RSWK-Kette: 0",
      pairs.get("RSWKKette_nummerDerRSWKKette").get(0));
    assertEquals("Nummer der RSWK-Kette: 0",
      pairs.get("RSWKKette_nummerDerRSWKKette").get(1));
    assertEquals("Nummer der RSWK-Kette: 0",
      pairs.get("RSWKKette_nummerDerRSWKKette").get(2));
    assertEquals("Nummer der RSWK-Kette: 0",
      pairs.get("RSWKKette_nummerDerRSWKKette").get(3));

    assertEquals(3, pairs.get("RSWKKette_D").size());
    assertEquals("Geografikum", pairs.get("RSWKKette_D").get(0));
    assertEquals("Sachbegriff", pairs.get("RSWKKette_D").get(1));
    assertEquals("Sachbegriff", pairs.get("RSWKKette_D").get(2));

    assertEquals(1, pairs.get("RSWKKette_5").size());
    assertEquals("DE-101", pairs.get("RSWKKette_5").get(0));

    assertEquals(1, pairs.get("AddedCorporateName").size());
    assertEquals("Deutsche Bibliothek", pairs.get("AddedCorporateName").get(0));

    assertEquals(2, pairs.get("AddedCorporateName_authorityRecordControlNumber").size());
    assertEquals(
      List.of("(DE-588)7674-0", "(DE-576)190187867"),
      pairs.get("AddedCorporateName_authorityRecordControlNumber"));

    assertEquals(2, pairs.get("AddedCorporateName_authorityRecordControlNumber_recordNumber").size());
    assertEquals(
      List.of("7674-0", "190187867"),
      pairs.get("AddedCorporateName_authorityRecordControlNumber_recordNumber"));

    assertEquals(2, pairs.get("AddedCorporateName_authorityRecordControlNumber_organization").size());
    assertEquals(
      List.of("Gemeinsame Normdatei", "Bibliotheksservice-Zentrum Baden-Württemberg (BSZ)"),
      pairs.get("AddedCorporateName_authorityRecordControlNumber_organization"));

    assertEquals(2, pairs.get("AddedCorporateName_authorityRecordControlNumber_organizationCode").size());
    assertEquals(
      List.of("DE-588", "DE-576"),
      pairs.get("AddedCorporateName_authorityRecordControlNumber_organizationCode"));

    assertEquals(1, pairs.get("AddedCorporateName_entryType").size());
    assertEquals("No information provided", pairs.get("AddedCorporateName_entryType").get(0));

    assertEquals(1, pairs.get("AddedCorporateName_nameType").size());
    assertEquals("Name in direct order", pairs.get("AddedCorporateName_nameType").get(0));

    assertEquals(2, pairs.get("PartOf_relation").size());
    assertEquals("Index zu", pairs.get("PartOf_relation").get(0));
    assertEquals("Index zu", pairs.get("PartOf_relation").get(1));

    assertEquals(2, pairs.get("PartOf_displayConstant").size());
    assertEquals("No display constant generated",
      pairs.get("PartOf_displayConstant").get(0));
    assertEquals("No display constant generated",
      pairs.get("PartOf_displayConstant").get(1));

    assertEquals(4, pairs.get("PartOf_recordControlNumber").size());
    assertEquals(
      List.of("(DE-576)025087622", "(DE-600)1056339-8", "(DE-576)023001283", "(DE-600)1056366-0"),
      pairs.get("PartOf_recordControlNumber"));

    assertEquals(2, pairs.get("PartOf_noteController").size());
    assertEquals("Display note", pairs.get("PartOf_noteController").get(0));
    assertEquals("Display note", pairs.get("PartOf_noteController").get(1));

    assertEquals(4, pairs.get("PartOf_recordControlNumber_recordNumber").size());
    assertEquals(
      List.of("025087622", "1056339-8", "023001283", "1056366-0"),
      pairs.get("PartOf_recordControlNumber_recordNumber"));

    assertEquals(4, pairs.get("PartOf_recordControlNumber_organization").size());
    assertEquals(
      List.of("Bibliotheksservice-Zentrum Baden-Württemberg (BSZ)", "Zeitschriftendatenbank (ZDB)", "Bibliotheksservice-Zentrum Baden-Württemberg (BSZ)", "Zeitschriftendatenbank (ZDB)"),
      pairs.get("PartOf_recordControlNumber_organization"));

    assertEquals(4, pairs.get("PartOf_recordControlNumber_organizationCode").size());
    assertEquals(
      List.of("DE-576", "DE-600", "DE-576", "DE-600"),
      pairs.get("PartOf_recordControlNumber_organizationCode"));

    assertEquals(2, pairs.get("PartOf").size());
    assertEquals(
      List.of(
        "Deutsche Nationalbibliografie und Bibliografie der im Ausland erschienenen deutschsprachigen Veröffentlichungen / A",
        "Deutsche Nationalbibliografie und Bibliografie der im Ausland erschienenen deutschsprachigen Veröffentlichungen / B"),
      pairs.get("PartOf"));

    assertEquals(1, pairs.get("PrecededBy").size());
    assertEquals("Deutsche Bibliographie. Wöchentliches Verzeichnis. Reihe A und Reihe B, Erscheinungen des Verlagsbuchhandels und außerhalb des Verlagsbuchhandels : Register",
      pairs.get("PrecededBy").get(0));

    assertEquals(1, pairs.get("PrecededBy_relation").size());
    assertEquals("Vorg.", pairs.get("PrecededBy_relation").get(0));

    assertEquals(2, pairs.get("PrecededBy_recordControlNumber_organization").size());
    assertEquals(
      List.of("Bibliotheksservice-Zentrum Baden-Württemberg (BSZ)", "Zeitschriftendatenbank (ZDB)"),
      pairs.get("PrecededBy_recordControlNumber_organization"));

    assertEquals(2, pairs.get("PrecededBy_recordControlNumber_recordNumber").size());
    assertEquals(
      List.of("014643111", "1429255"),
      pairs.get("PrecededBy_recordControlNumber_recordNumber"));

    assertEquals(1, pairs.get("PrecededBy_typeOfRelationship").size());
    assertEquals("Continues", pairs.get("PrecededBy_typeOfRelationship").get(0));

    assertEquals(2, pairs.get("PrecededBy_recordControlNumber").size());
    assertEquals(
      List.of("(DE-576)014643111", "(DE-600)1429255"),
      pairs.get("PrecededBy_recordControlNumber"));

    assertEquals(2, pairs.get("PrecededBy_recordControlNumber_organizationCode").size());
    assertEquals(
      List.of("DE-576", "DE-600"),
      pairs.get("PrecededBy_recordControlNumber_organizationCode"));

    assertEquals(1, pairs.get("PrecededBy_noteController").size());
    assertEquals("Display note", pairs.get("PrecededBy_noteController").get(0));
  }

  @Test
  public void marc2Test() throws IOException, URISyntaxException {
    JsonSelector selector = new JsonSelector(FileUtils.readFirstLineFromResource("general/marc2.json"));

    Marc21Record marcRecord = (Marc21Record) MarcFactory.create(selector);
    assertNotNull(marcRecord);
    assertNotNull("Leader should not be null", marcRecord.getLeader());

    List<DataField> admins = marcRecord.getDatafieldsByTag("040");
    assertEquals(1, admins.size());
    DataField adminMeta = admins.get(0);
    List<MarcSubfield> subfields = adminMeta.getSubfields();
    for (MarcSubfield subfield : subfields) {
      if (subfield.getCode().equals("b")) {
        assertEquals("LanguageCodes", subfield.getDefinition().getCodeList().getClass().getSimpleName());
        assertEquals("English", subfield.resolve());
      }
    }

    Map<String, List<String>> map = marcRecord.getKeyValuePairs(SolrFieldType.HUMAN);
    System.err.println(map);
    assertEquals(Arrays.asList("English"), map.get("AdminMetadata_languageOfCataloging"));
  }

  @Test
  public void getKeyValuePairTest() throws IOException, URISyntaxException {
    JsonSelector selector = new JsonSelector(FileUtils.readFirstLineFromResource("general/verbund-tit.001.0000000.formatted.json"));

    BibliographicRecord marcRecord = MarcFactory.create(selector, MarcVersion.DNB);
    Map<String, List<String>> pairs = marcRecord.getKeyValuePairs(SolrFieldType.MIXED);
    assertEquals(124, pairs.size());

    Set<String> keys = pairs.keySet();
    keys.remove("591a_GentLocallyDefinedField");
    keys.remove("591a_BemerkungenZurTitelaufnahme");

    assertEquals(
      "type, " +
        "Leader, " +
        "leader00_recordLength, " +
        "leader05_recordStatus, " +
        "leader06_typeOfRecord, " +
        "leader07_bibliographicLevel, " +
        "leader08_typeOfControl, " +
        "leader09_characterCodingScheme, " +
        "leader10_indicatorCount, " +
        "leader11_subfieldCodeCount, " +
        "leader12_baseAddressOfData, " +
        "leader17_encodingLevel, " +
        "leader18_descriptiveCatalogingForm, " +
        "leader19_multipartResourceRecordLevel, " +
        "leader20_lengthOfTheLengthOfFieldPortion, " +
        "leader21_lengthOfTheStartingCharacterPositionPortion, " +
        "leader22_lengthOfTheImplementationDefinedPortion, " +
        "leader23_undefined, " +
        "001_ControlNumber, " +
        "003_ControlNumberIdentifier, " +
        "005_LatestTransactionTime, " +
        "007_PhysicalDescription, " +
        "007common00_PhysicalDescription_categoryOfMaterial, " +
        "007text01_PhysicalDescription_specificMaterialDesignation, " +
        "008_GeneralInformation, 008all00_GeneralInformation_dateEnteredOnFile, " +
        "008all06_GeneralInformation_typeOfDateOrPublicationStatus, " +
        "008all07_GeneralInformation_date1, " +
        "008all11_GeneralInformation_date2, " +
        "008all15_GeneralInformation_placeOfPublicationProductionOrExecution, " +
        "008all35_GeneralInformation_language, " +
        "008all38_GeneralInformation_modifiedRecord, " +
        "008all39_GeneralInformation_catalogingSource, " +
        "008continuing18_GeneralInformation_frequency, " +
        "008continuing19_GeneralInformation_regularity, " +
        "008continuing21_GeneralInformation_typeOfContinuingResource, " +
        "008continuing22_GeneralInformation_formOfOriginalItem, " +
        "008continuing23_GeneralInformation_formOfItem, " +
        "008continuing24_GeneralInformation_natureOfEntireWork, " +
        "008continuing25_GeneralInformation_natureOfContents, " +
        "008continuing28_GeneralInformation_governmentPublication, " +
        "008continuing29_GeneralInformation_conferencePublication, " +
        "008continuing33_GeneralInformation_originalAlphabetOrScriptOfTitle, " +
        "008continuing34_GeneralInformation_entryConvention, " +
        "0162_IdIntifiedByLocal_source, " +
        "016ind1_IdIntifiedByLocal_agency, " +
        "016a_IdIntifiedByLocal, " +
        "022ind1_Issn_levelOfInternationalInterest, " +
        "022a_Issn, " +
        "035a_SystemControlNumber_recordNumber, " +
        "035a_SystemControlNumber, " +
        "035a_SystemControlNumber_organizationCode, " +
        "035a_SystemControlNumber_organization, " +
        "040e_AdminMetadata_descriptionConventions, " +
        "040a_AdminMetadata_catalogingAgency, " +
        "040b_AdminMetadata_languageOfCataloging, " +
        "040c_AdminMetadata_transcribingAgency, " +
        "041a_Language, " +
        "041ind1_Language_translationIndication, " +
        "041ind2_Language_sourceOfCode, " +
        "044a_Place_country, " +
        "082ind2_ClassificationDdc_classificationSource, " +
        "082ind1_ClassificationDdc_editionType, " +
        "082a_ClassificationDdc, " +
        "082_ClassificationDdc_full, " +
        "084_Classification_full, " +
        "0842_Classification_source, " +
        "084a_Classification_classificationPortion, " +
        "084a_Classification_classificationPortion_zdbs, " +
        "245a_Title_mainTitle, " +
        "245ind1_Title_titleAddedEntry, " +
        "245ind2_Title_nonfilingCharacters, " +
        "245p_Title_partName, " +
        "245b_Title_subtitle, " +
        "245c_Title_responsibilityStatement, " +
        "246ind1_ParallelTitle_noteAndAddedEntry, " +
        "246i_ParallelTitle_displayText, " +
        "246a_ParallelTitle_mainTitle, " +
        "246ind2_ParallelTitle_type, " +
        "260b_Publication_agent, " +
        "260a_Publication_place, " +
        "260ind1_Publication_sequenceOfPublishingStatements, " +
        "362ind1_DatesOfPublication_format, " +
        "362a_DatesOfPublication, " +
        "515a_NumberingPeculiarities, " +
        "689D_RSWKKette, " +
        "6890_RSWKKette, " +
        "689ind2_RSWKKette_nummerDesKettengliedes, " +
        "689ind1_RSWKKette_nummerDerRSWKKette, " +
        "689a_RSWKKette, " +
        "6895_RSWKKette, " +
        "7100_AddedCorporateName_authorityRecordControlNumber, " +
        "7100_AddedCorporateName_authorityRecordControlNumber_recordNumber, " +
        "7100_AddedCorporateName_authorityRecordControlNumber_organization, " +
        "710_AddedCorporateName_full, " +
        "7100_AddedCorporateName_authorityRecordControlNumber_organizationCode, " +
        "710a_AddedCorporateName, " +
        "710ind1_AddedCorporateName_nameType, " +
        "710ind2_AddedCorporateName_entryType, " +
        "773w_PartOf_recordControlNumber, " +
        "773w_PartOf_recordControlNumber_organizationCode, " +
        "773ind1_PartOf_noteController, " +
        "773a_PartOf, " +
        "773i_PartOf_relation, " +
        "773ind2_PartOf_displayConstant, " +
        "773w_PartOf_recordControlNumber_organization, " +
        "773w_PartOf_recordControlNumber_recordNumber, " +
        "780ind1_PrecededBy_noteController, " +
        "780ind2_PrecededBy_typeOfRelationship, " +
        "780w_PrecededBy_recordControlNumber_organization, " +
        "780w_PrecededBy_recordControlNumber_organizationCode, " +
        "780i_PrecededBy_relation, " +
        "780w_PrecededBy_recordControlNumber_recordNumber, " +
        "780a_PrecededBy, " +
        "780w_PrecededBy_recordControlNumber, " +
        "924b_Bestandsinformationen, " +
        "924c_Bestandsinformationen, " +
        "924ind1_Bestandsinformationen_artDerRessource, " +
        "924a_Bestandsinformationen, " +
        "924d_Bestandsinformationen, " +
        "9249_Bestandsinformationen, " +
        "924h_Bestandsinformationen, " +
        "924g_Bestandsinformationen",
      StringUtils.join(pairs.keySet(), ", "));
  }

  @Test
  public void testCreateFromFormattedText() throws IOException, URISyntaxException {
    List<String> lines = FileUtils.readLinesFromResource("marctxt/010000011.mrctxt");
    assertEquals(44, lines.size());
    String marcRecordAsText = StringUtils.join(lines, "\n");
    assertEquals(1845, marcRecordAsText.length());

    Marc21Record marcRecord = (Marc21Record) MarcFactory.createFromFormattedText(marcRecordAsText);
    test01000011RecordProperties(marcRecord);
  }

  @Test
  public void testCreateFromFormattedText_asList() throws IOException, URISyntaxException {
    List<String> lines = FileUtils.readLinesFromResource("marctxt/010000011.mrctxt");
    Marc21Record marcRecord = (Marc21Record) MarcFactory.createFromFormattedText(lines);
    test01000011RecordProperties(marcRecord);
  }

  @Test
  public void testRegex() throws IOException, URISyntaxException {
    Pattern numericTag = Pattern.compile("^\\d\\d\\d$");
    assertTrue(numericTag.matcher("001").matches());
    assertFalse(numericTag.matcher("LDR").matches());
    assertFalse(numericTag.matcher("CRD").matches());
  }

  @Test
  public void testCreateFromAlephseq_MarcRecord() throws IOException, URISyntaxException {
    Path path = FileUtils.getPath("alephseq/alephseq-example1.txt");
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(path.toString()));
      String line = reader.readLine();

      BibliographicRecord marcRecord = null;
      List<AlephseqLine> lines = new ArrayList<>();
      while (line != null) {
        AlephseqLine alephseqLine = new AlephseqLine(line);
        if (alephseqLine.isValidTag()) {
          if (alephseqLine.isLeader() && !lines.isEmpty()) {
            marcRecord = MarcFactory.createFromAlephseq(lines, MarcVersion.MARC21);
            lines = new ArrayList<>();
          }
          lines.add(alephseqLine);
        }

        line = reader.readLine();
      }
      marcRecord = MarcFactory.createFromAlephseq(lines, MarcVersion.MARC21);
      List<DataField> tag700 = marcRecord.getDatafieldsByTag("700");
      assertEquals("700", tag700.get(0).getTag());
      assertEquals("1", tag700.get(0).getInd1());
      assertEquals(" ", tag700.get(0).getInd2());
      assertEquals("Chantebout, Bernard", tag700.get(0).getSubfield("a").get(0).getValue());
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    // MarcRecord marcRecord = MarcFactory.createFromFormattedText(lines);
    // test01000011RecordProperties(marcRecord);
  }

  @Test
  public void testCreateFromAlephseq_Record() throws IOException, URISyntaxException {
    Path path = FileUtils.getPath("alephseq/alephseq-example1.txt");
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(path.toString()));
      String line = reader.readLine();

      Record marc4jRecord = null;
      List<AlephseqLine> lines = new ArrayList<>();
      while (line != null) {
        AlephseqLine alephseqLine = new AlephseqLine(line);
        if (alephseqLine.isValidTag()) {
          if (alephseqLine.isLeader() && !lines.isEmpty()) {
            IteratorResponse response = MarcFactory.createRecordFromAlephseq(lines);
            marc4jRecord = response.getMarc4jRecord();
            lines = new ArrayList<>();
          }
          lines.add(alephseqLine);
        }

        line = reader.readLine();
      }
      IteratorResponse response = MarcFactory.createRecordFromAlephseq(lines);
      marc4jRecord = response.getMarc4jRecord();
      org.marc4j.marc.DataField tag700 = (org.marc4j.marc.DataField) marc4jRecord.getVariableField("700");
      assertNotNull(tag700);
      assertNotNull(tag700.getSubfield('a'));
      assertEquals("Chantebout, Bernard", tag700.getSubfield('a').getData());
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    // MarcRecord marcRecord = MarcFactory.createFromFormattedText(lines);
    // test01000011RecordProperties(marcRecord);
  }

  @Test
  public void testCreateFromAlephseq_Record_with_short_leader() throws IOException, URISyntaxException {
    Path path = FileUtils.getPath("alephseq/alephseq-example5-short-leader.txt");
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(path.toString()));
      String line = reader.readLine();

      Record marc4jRecord = null;
      List<AlephseqLine> lines = new ArrayList<>();
      while (line != null) {
        AlephseqLine alephseqLine = new AlephseqLine(line);
        if (alephseqLine.isValidTag()) {
          if (alephseqLine.isLeader() && !lines.isEmpty()) {
            marc4jRecord = MarcFactory.createRecordFromAlephseq(lines).getMarc4jRecord();
            lines = new ArrayList<>();
          }
          lines.add(alephseqLine);
        }

        line = reader.readLine();
      }
      marc4jRecord = MarcFactory.createRecordFromAlephseq(lines).getMarc4jRecord();
      assertNull(marc4jRecord.getLeader());
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testCreateFromAlephseq_Record_with_long_leader() throws IOException, URISyntaxException {
    Path path = FileUtils.getPath("alephseq/alephseq-example5-long-leader.txt");
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(path.toString()));
      String line = reader.readLine();

      Record marc4jRecord = null;
      List<AlephseqLine> lines = new ArrayList<>();
      while (line != null) {
        AlephseqLine alephseqLine = new AlephseqLine(line);
        if (alephseqLine.isValidTag()) {
          if (alephseqLine.isLeader() && !lines.isEmpty()) {
            marc4jRecord = MarcFactory.createRecordFromAlephseq(lines).getMarc4jRecord();
            lines = new ArrayList<>();
          }
          lines.add(alephseqLine);
        }

        line = reader.readLine();
      }
      marc4jRecord = MarcFactory.createRecordFromAlephseq(lines).getMarc4jRecord();
      assertNull(marc4jRecord.getLeader());
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void createUnimarcFromFormattedText() throws IOException, URISyntaxException {
    List<String> lines = FileUtils.readLinesFromResource("unimarc/unimarc.mrctxt");
    assertEquals(58, lines.size());
    String marcRecordAsText = StringUtils.join(lines, "\n");
    assertEquals(2319, marcRecordAsText.length());

    Marc21Record marcRecord = (Marc21Record) MarcFactory.createFromFormattedText(marcRecordAsText, MarcVersion.UNIMARC);
    testUnimarcRecordProperties(marcRecord);
  }

  private void testUnimarcRecordProperties(Marc21Record marcRecord) {
    assertEquals("02794cam0 2200709   450 ", marcRecord.getLeader().getLeaderString());
  }

  private void test01000011RecordProperties(Marc21Record marcRecord) {
    assertEquals("02191cam a2200541   4500", marcRecord.getLeader().getLeaderString());
    assertEquals("861106s1985    xx |||||      10| ||ger c", marcRecord.getControl008().getContent());
    assertEquals(3, marcRecord.getDatafieldsByTag("689").size());
    assertEquals(2, marcRecord.getDatafieldsByTag("810").size());
    assertEquals(" ", marcRecord.getDatafieldsByTag("810").get(1).getInd2());
    assertEquals(38, marcRecord.getDatafields().size());
  }
}
