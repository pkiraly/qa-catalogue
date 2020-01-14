package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.utils.AlephseqLine;
import org.apache.commons.lang3.StringUtils;
import org.junit.*;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class MarcFactoryTest {

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  @Test
  public void mainTest() throws IOException, URISyntaxException {
    JsonPathCache cache = new JsonPathCache(FileUtils.readFirstLine("general/verbund-tit.001.0000000.formatted.json"));

    MarcRecord record = MarcFactory.create(cache, MarcVersion.DNB);
    assertNotNull(record);
    assertNotNull("Leader should not be null", record.getLeader());
    // System.err.println(record.format());
    // System.err.println(record.formatAsMarc());
    // System.err.println(record.formatForIndex());
    // System.err.println(record.getKeyValuePairs());
    Map<String, List<String>> pairs = record.getKeyValuePairs(SolrFieldType.HUMAN);
    assertEquals(126, pairs.size());
    Set<String> keys = pairs.keySet();
    // keys.remove("GentLocallyDefinedField");
    // keys.remove("BemerkungenZurTitelaufnahme");
    assertEquals(
      "type, Leader, Leader_recordLength, Leader_recordStatus, Leader_typeOfRecord, " +
      "Leader_bibliographicLevel, Leader_typeOfControl, Leader_characterCodingScheme, " +
      "Leader_indicatorCount, Leader_subfieldCodeCount, Leader_baseAddressOfData, " +
      "Leader_encodingLevel, Leader_descriptiveCatalogingForm, Leader_multipartResourceRecordLevel, " +
      "Leader_lengthOfTheLengthOfFieldPortion, Leader_lengthOfTheStartingCharacterPositionPortion, " +
      "Leader_lengthOfTheImplementationDefinedPortion, ControlNumber, ControlNumberIdentifier, " +
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
      "Issn_levelOfInternationalInterest, Issn, SystemControlNumber_organizationCode, " +
      "SystemControlNumber_ind1, " +
      "SystemControlNumber, SystemControlNumber_recordNumber, SystemControlNumber_organization, " +
      "AdminMetadata_ind1, " +
      "AdminMetadata_languageOfCataloging, AdminMetadata_descriptionConventions, " +
      "AdminMetadata_transcribingAgency, AdminMetadata_catalogingAgency, " +
      "Language_translationIndication, Language, Language_sourceOfCode, Place_country, " +
      "Place_ind1, " +
      "ClassificationDdc_editionType, ClassificationDdc_classificationSource, ClassificationDdc, " +
      "Classification_classificationPortion, Classification_classificationPortion_zdbs, " +
      "Classification_source, Classification_ind1, Title_subtitle, " +
      "Title_responsibilityStatement, Title_mainTitle, Title_titleAddedEntry, " +
      "Title_nonfilingCharacters, Title_partName, ParallelTitle_mainTitle, ParallelTitle_type, " +
      "ParallelTitle_displayText, ParallelTitle_noteAndAddedEntry, " +
      "Publication_sequenceOfPublishingStatements, Publication_agent, Publication_place, " +
      "DatesOfPublication, DatesOfPublication_format, NumberingPeculiarities, " +
      "NumberingPeculiarities_ind1, " +
      "BemerkungenZurTitelaufnahme, BemerkungenZurTitelaufnahme_ind1, " +
      "RSWKKette_nummerDesKettengliedes, RSWKKette_0, RSWKKette_a, " +
      "RSWKKette_nummerDerRSWKKette, RSWKKette_D, RSWKKette_5, AddedCorporateName, " +
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
      "PrecededBy_noteController, Bestandsinformationen, Bestandsinformationen_9, Bestandsinformationen_d, " +
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
    assertEquals("2", pairs.get("Leader_indicatorCount").get(0));
    assertEquals("2", pairs.get("Leader_subfieldCodeCount").get(0));
    assertEquals("00481", pairs.get("Leader_baseAddressOfData").get(0));
    assertEquals("Full level", pairs.get("Leader_encodingLevel").get(0));
    assertEquals("Non-ISBD", pairs.get("Leader_descriptiveCatalogingForm").get(0));
    assertEquals("Not specified or not applicable", pairs.get("Leader_multipartResourceRecordLevel").get(0));
    assertEquals("4", pairs.get("Leader_lengthOfTheLengthOfFieldPortion").get(0));
    assertEquals("5", pairs.get("Leader_lengthOfTheStartingCharacterPositionPortion").get(0));
    assertEquals("0", pairs.get("Leader_lengthOfTheImplementationDefinedPortion").get(0));
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

    assertEquals(3, pairs.get("RSWKKette_0").size());
    assertEquals("(DE-576)208896155", pairs.get("RSWKKette_0").get(0));
    assertEquals("(DE-576)208901906", pairs.get("RSWKKette_0").get(1));
    assertEquals("(DE-576)208865578", pairs.get("RSWKKette_0").get(2));

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

    assertEquals(1, pairs.get("AddedCorporateName_authorityRecordControlNumber").size());
    assertEquals("(DE-576)190187867",
      pairs.get("AddedCorporateName_authorityRecordControlNumber").get(0));

    assertEquals(1, pairs.get("AddedCorporateName_authorityRecordControlNumber_recordNumber").size());
    assertEquals("190187867", pairs.get("AddedCorporateName_authorityRecordControlNumber_recordNumber").get(0));

    assertEquals(1, pairs.get("AddedCorporateName_authorityRecordControlNumber_organization").size());
    assertEquals("Bibliotheksservice-Zentrum Baden-Württemberg (BSZ)",
      pairs.get("AddedCorporateName_authorityRecordControlNumber_organization").get(0));

    assertEquals(1, pairs.get("AddedCorporateName_authorityRecordControlNumber_organizationCode").size());
    assertEquals("DE-576",
      pairs.get("AddedCorporateName_authorityRecordControlNumber_organizationCode").get(0));

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

    assertEquals(2, pairs.get("PartOf_recordControlNumber").size());
    assertEquals("(DE-600)1056339-8", pairs.get("PartOf_recordControlNumber").get(0));
    assertEquals("(DE-600)1056366-0", pairs.get("PartOf_recordControlNumber").get(1));

    assertEquals(2, pairs.get("PartOf_noteController").size());
    assertEquals("Display note", pairs.get("PartOf_noteController").get(0));
    assertEquals("Display note", pairs.get("PartOf_noteController").get(1));

    assertEquals(2, pairs.get("PartOf_recordControlNumber_recordNumber").size());
    assertEquals("1056339-8",
      pairs.get("PartOf_recordControlNumber_recordNumber").get(0));
    assertEquals("1056366-0",
      pairs.get("PartOf_recordControlNumber_recordNumber").get(1));

    assertEquals(2, pairs.get("PartOf_recordControlNumber_organization").size());
    assertEquals("Zeitschriftendatenbank (ZDB)",
      pairs.get("PartOf_recordControlNumber_organization").get(0));
    assertEquals("Zeitschriftendatenbank (ZDB)",
      pairs.get("PartOf_recordControlNumber_organization").get(1));

    assertEquals(2, pairs.get("PartOf_recordControlNumber_organizationCode").size());
    assertEquals("DE-600",
      pairs.get("PartOf_recordControlNumber_organizationCode").get(0));
    assertEquals("DE-600",
      pairs.get("PartOf_recordControlNumber_organizationCode").get(1));

    assertEquals(2, pairs.get("PartOf").size());
    assertEquals("Deutsche Nationalbibliografie und Bibliografie der im Ausland erschienenen deutschsprachigen Veröffentlichungen / A",
      pairs.get("PartOf").get(0));
    assertEquals("Deutsche Nationalbibliografie und Bibliografie der im Ausland erschienenen deutschsprachigen Veröffentlichungen / B",
      pairs.get("PartOf").get(1));

    assertEquals(1, pairs.get("PrecededBy").size());
    assertEquals("Deutsche Bibliographie. Wöchentliches Verzeichnis. Reihe A und Reihe B, Erscheinungen des Verlagsbuchhandels und außerhalb des Verlagsbuchhandels : Register",
      pairs.get("PrecededBy").get(0));

    assertEquals(1, pairs.get("PrecededBy_relation").size());
    assertEquals("Vorg.", pairs.get("PrecededBy_relation").get(0));

    assertEquals(1, pairs.get("PrecededBy_recordControlNumber_organization").size());
    assertEquals("Zeitschriftendatenbank (ZDB)",
      pairs.get("PrecededBy_recordControlNumber_organization").get(0));

    assertEquals(1, pairs.get("PrecededBy_recordControlNumber_recordNumber").size());
    assertEquals("1429255", pairs.get("PrecededBy_recordControlNumber_recordNumber").get(0));

    assertEquals(1, pairs.get("PrecededBy_typeOfRelationship").size());
    assertEquals("Continues", pairs.get("PrecededBy_typeOfRelationship").get(0));

    assertEquals(1, pairs.get("PrecededBy_recordControlNumber").size());
    assertEquals("(DE-600)1429255", pairs.get("PrecededBy_recordControlNumber").get(0));

    assertEquals(1, pairs.get("PrecededBy_recordControlNumber_organizationCode").size());
    assertEquals("DE-600", pairs.get("PrecededBy_recordControlNumber_organizationCode").get(0));

    assertEquals(1, pairs.get("PrecededBy_noteController").size());
    assertEquals("Display note", pairs.get("PrecededBy_noteController").get(0));
  }

  @Test
  public void marc2Test() throws IOException, URISyntaxException {
    JsonPathCache cache = new JsonPathCache(FileUtils.readFirstLine("general/marc2.json"));

    MarcRecord record = MarcFactory.create(cache);
    assertNotNull(record);
    assertNotNull("Leader should not be null", record.getLeader());

    List<DataField> admins = record.getDatafield("040");
    assertEquals(1, admins.size());
    DataField adminMeta = admins.get(0);
    List<MarcSubfield> subfields = adminMeta.parseSubfields();
    for (MarcSubfield subfield : subfields) {
      if (subfield.getCode().equals("b")) {
        assertEquals("LanguageCodes", subfield.getDefinition().getCodeList().getClass().getSimpleName());
        assertEquals("English", subfield.resolve());
      }
    }

    assertEquals(Arrays.asList("English"), record
      .getKeyValuePairs(SolrFieldType.HUMAN)
      .get("AdminMetadata_languageOfCataloging"));
  }

  @Test
  public void getKeyValuePairTest() throws IOException, URISyntaxException {
    JsonPathCache cache = new JsonPathCache(FileUtils.readFirstLine("general/verbund-tit.001.0000000.formatted.json"));

    MarcRecord record = MarcFactory.create(cache, MarcVersion.DNB);
    Map<String, List<String>> pairs = record.getKeyValuePairs(SolrFieldType.MIXED);
    assertEquals(126, pairs.size());

    Set<String> keys = pairs.keySet();
    keys.remove("591a_GentLocallyDefinedField");
    keys.remove("591a_BemerkungenZurTitelaufnahme");

    assertEquals(
      "type, " +
        "Leader, " +
        "Leader_00-04_recordLength, " +
        "Leader_05_recordStatus, " +
        "Leader_06_typeOfRecord, " +
        "Leader_07_bibliographicLevel, " +
        "Leader_08_typeOfControl, " +
        "Leader_09_characterCodingScheme, " +
        "Leader_10_indicatorCount, " +
        "Leader_11_subfieldCodeCount, " +
        "Leader_12-16_baseAddressOfData, " +
        "Leader_17_encodingLevel, " +
        "Leader_18_descriptiveCatalogingForm, " +
        "Leader_19_multipartResourceRecordLevel, " +
        "Leader_20_lengthOfTheLengthOfFieldPortion, " +
        "Leader_21_lengthOfTheStartingCharacterPositionPortion, " +
        "Leader_22_lengthOfTheImplementationDefinedPortion, " +
        "001_ControlNumber, " +
        "003_ControlNumberIdentifier, " +
        "005_LatestTransactionTime, " +
        "007_PhysicalDescription, " +
        "007_00_PhysicalDescription_categoryOfMaterial, " +
        "007_01_PhysicalDescription_specificMaterialDesignation, " +
        "008_GeneralInformation, " +
        "008_00-05_GeneralInformation_dateEnteredOnFile, " +
        "008_06_GeneralInformation_typeOfDateOrPublicationStatus, " +
        "008_07-10_GeneralInformation_date1, " +
        "008_11-14_GeneralInformation_date2, " +
        "008_15-17_GeneralInformation_placeOfPublicationProductionOrExecution, " +
        "008_35-37_GeneralInformation_language, " +
        "008_38_GeneralInformation_modifiedRecord, " +
        "008_39_GeneralInformation_catalogingSource, " +
        "008_18_GeneralInformation_frequency, " +
        "008_19_GeneralInformation_regularity, " +
        "008_21_GeneralInformation_typeOfContinuingResource, " +
        "008_22_GeneralInformation_formOfOriginalItem, " +
        "008_23_GeneralInformation_formOfItem, " +
        "008_24_GeneralInformation_natureOfEntireWork, " +
        "008_25-27_GeneralInformation_natureOfContents, " +
        "008_28_GeneralInformation_governmentPublication, " +
        "008_29_GeneralInformation_conferencePublication, " +
        "008_33_GeneralInformation_originalAlphabetOrScriptOfTitle, " +
        "008_34_GeneralInformation_entryConvention, " +
        "0162_IdIntifiedByLocal_source, " +
        "016ind1_IdIntifiedByLocal_agency, " +
        "016a_IdIntifiedByLocal, " +
        "022ind1_Issn_levelOfInternationalInterest, " +
        "022a_Issn, " +
        "035a_SystemControlNumber_recordNumber, " +
        "035a_SystemControlNumber, " +
        "035ind1_SystemControlNumber_ind1, " +
        "035a_SystemControlNumber_organizationCode, " +
        "035a_SystemControlNumber_organization, " +
        "040e_AdminMetadata_descriptionConventions, " +
        "040ind1_AdminMetadata_ind1, " +
        "040a_AdminMetadata_catalogingAgency, " +
        "040b_AdminMetadata_languageOfCataloging, " +
        "040c_AdminMetadata_transcribingAgency, " +
        "041a_Language, " +
        "041ind1_Language_translationIndication, " +
        "041ind2_Language_sourceOfCode, " +
        "044ind1_Place_ind1, " +
        "044a_Place_country, " +
        "082ind2_ClassificationDdc_classificationSource, " +
        "082ind1_ClassificationDdc_editionType, " +
        "082a_ClassificationDdc, " +
        "084ind1_Classification_ind1, " +
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
        "515ind1_NumberingPeculiarities_ind1, " +
        "515a_NumberingPeculiarities, " +
        "591ind1_BemerkungenZurTitelaufnahme_ind1, " +
        // "591a_GentLocallyDefinedField, " +
        "689D_RSWKKette, " +
        "6890_RSWKKette, " +
        "689ind2_RSWKKette_nummerDesKettengliedes, " +
        "689ind1_RSWKKette_nummerDerRSWKKette, " +
        "689a_RSWKKette, " +
        "6895_RSWKKette, " +
        "7100_AddedCorporateName_authorityRecordControlNumber, " +
        "7100_AddedCorporateName_authorityRecordControlNumber_recordNumber, " +
        "7100_AddedCorporateName_authorityRecordControlNumber_organization, " +
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
    List<String> lines = FileUtils.readLines("general/010000011.mrctxt");
    assertEquals(44, lines.size());
    String marcRecordAsText = StringUtils.join(lines, "\n");
    assertEquals(1845, marcRecordAsText.length());

    MarcRecord record = MarcFactory.createFromFormattedText(marcRecordAsText);
    test01000011RecordProperties(record);
  }

  @Test
  public void testCreateFromFormattedText_asList() throws IOException, URISyntaxException {
    List<String> lines = FileUtils.readLines("general/010000011.mrctxt");
    MarcRecord record = MarcFactory.createFromFormattedText(lines);
    test01000011RecordProperties(record);
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
    Path path = FileUtils.getPath("general/alephseq-example.txt");
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(path.toString()));
      String line = reader.readLine();

      MarcRecord record = null;
      List<AlephseqLine> lines = new ArrayList<>();
      while (line != null) {
        AlephseqLine alephseqLine = new AlephseqLine(line);
        if (alephseqLine.isValidTag()) {
          if (alephseqLine.isLeader() && !lines.isEmpty()) {
            record = MarcFactory.createFromAlephseq(lines, MarcVersion.MARC21);
            lines = new ArrayList<>();
          }
          lines.add(alephseqLine);
        }

        line = reader.readLine();
      }
      record = MarcFactory.createFromAlephseq(lines, MarcVersion.MARC21);
      List<DataField> tag700 = record.getDatafield("700");
      assertEquals("Chantebout, Bernard", tag700.get(0).getSubfield("a").get(0).getValue());
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    // MarcRecord record = MarcFactory.createFromFormattedText(lines);
    // test01000011RecordProperties(record);
  }

  @Test
  public void testCreateFromAlephseq_Record() throws IOException, URISyntaxException {
    Path path = FileUtils.getPath("general/alephseq-example.txt");
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(path.toString()));
      String line = reader.readLine();

      Record record = null;
      List<AlephseqLine> lines = new ArrayList<>();
      while (line != null) {
        AlephseqLine alephseqLine = new AlephseqLine(line);
        if (alephseqLine.isValidTag()) {
          if (alephseqLine.isLeader() && !lines.isEmpty()) {
            record = MarcFactory.createRecordFromAlephseq(lines);
            lines = new ArrayList<>();
          }
          lines.add(alephseqLine);
        }

        line = reader.readLine();
      }
      record = MarcFactory.createRecordFromAlephseq(lines);
      org.marc4j.marc.DataField tag700 = (org.marc4j.marc.DataField) record.getVariableField("700");
      assertNotNull(tag700);
      assertNotNull(tag700.getSubfield('a'));
      assertEquals("Chantebout, Bernard", tag700.getSubfield('a').getData());
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    // MarcRecord record = MarcFactory.createFromFormattedText(lines);
    // test01000011RecordProperties(record);
  }

  private void test01000011RecordProperties(MarcRecord record) {
    assertEquals("02191cam a2200541   4500", record.getLeader().getLeaderString());
    assertEquals("861106s1985    xx |||||      10| ||ger c", record.getControl008().getContent());
    assertEquals(3, record.getDatafield("689").size());
    assertEquals(2, record.getDatafield("810").size());
    assertEquals(" ", record.getDatafield("810").get(1).getInd2());
    assertEquals(38, record.getDatafields().size());
  }


}
