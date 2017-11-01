package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.util.FileUtils;
import org.junit.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

		MarcRecord record = MarcFactory.create(cache);
		assertNotNull(record);
		assertNotNull("Leader should not be null", record.getLeader());
		// System.err.println(record.format());
		// System.err.println(record.formatAsMarc());
		// System.err.println(record.formatForIndex());
		// System.err.println(record.getKeyValuePairs());
		Map<String, List<String>> pairs = record.getKeyValuePairs();
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
		assertEquals("0048", pairs.get("Leader_baseAddressOfData").get(0));
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
		assertEquals("xx ", pairs.get("GeneralInformation_placeOfPublicationProductionOrExecution").get(0));
		assertEquals("ger", pairs.get("GeneralInformation_language").get(0));
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
		assertEquals("Zeitschriftendatenbank (ZDB)", pairs.get("IdIntifiedByLocal_source").get(0));
		assertEquals("1056377-5", pairs.get("IdIntifiedByLocal").get(0));
		assertEquals("Source specified in subfield $2", pairs.get("IdIntifiedByLocal_agency").get(0));
		assertEquals("No level specified", pairs.get("Issn_levelOfInternationalInterest").get(0));
		assertEquals("0939-0480", pairs.get("Issn").get(0));
		assertEquals("DE-599", pairs.get("SystemControlNumber_organizationCode").get(0));
		assertEquals("(DE-599)ZDB1056377-5", pairs.get("SystemControlNumber").get(0));
		assertEquals("ZDB1056377-5", pairs.get("SystemControlNumber_recordNumber").get(0));
		assertEquals("Arbeitsgemeinschaft der Verbundsysteme", pairs.get("SystemControlNumber_organization").get(0));
		assertEquals("German", pairs.get("AdminMetadata_languageOfCataloging").get(0));
		assertEquals("Regeln für die alphabetische Katalogisierung an wissenschaftlichen Bibliotheken (Berlin: Deutsches Bibliotheksinstitut)", pairs.get("AdminMetadata_descriptionConventions").get(0));
		assertEquals("Bibliotheksservice-Zentrum Baden-Württemberg (BSZ)", pairs.get("AdminMetadata_transcribingAgency").get(0));
		assertEquals("Bibliotheksservice-Zentrum Baden-Württemberg (BSZ)", pairs.get("AdminMetadata_catalogingAgency").get(0));
		assertEquals("No information provided", pairs.get("Language_translationIndication").get(0));
		assertEquals("German", pairs.get("Language").get(0));
		assertEquals("MARC language code", pairs.get("Language_sourceOfCode").get(0));
		assertEquals("XA-DE", pairs.get("Place_country").get(0));
		assertEquals("Full edition", pairs.get("ClassificationDdc_editionType").get(0));
		assertEquals("No information provided", pairs.get("ClassificationDdc_classificationSource").get(0));
		assertEquals("010", pairs.get("ClassificationDdc").get(0));
		assertEquals("110", pairs.get("Classification_classificationPortion").get(0));
		assertEquals("ZDB-Systematik = ZDB-Classification", pairs.get("Classification_source").get(0));
		assertEquals("Amtsblatt /", pairs.get("Title_subtitle").get(0));
		assertEquals("Bearb. u. Hrsg.: Die Deutsche Bibliothek (Deutsche Bücherei Leipzig, Deutsche Bibliothek Frankfurt a.M., Deutsches Musikarchiv Berlin).", pairs.get("Title_responsibilityStatement").get(0));
		assertEquals("Deutsche Nationalbibliografie und Bibliografie der im Ausland erschienenen deutschsprachigen Veröffentlichungen :", pairs.get("Title_mainTitle").get(0));
		assertEquals("Added entry", pairs.get("Title_titleAddedEntry").get(0));
		assertEquals("No nonfiling characters", pairs.get("Title_nonfilingCharacters").get(0));
		assertEquals("A B, Monographien und Periodika des Verlagsbuchhandels und außerhalb des Verlagsbuchhandels : wöchentliches Verzeichnis ; Register", pairs.get("Title_partName").get(0));
		assertEquals("Deutsche Nationalbibliografie und Bibliografie der im Ausland erschienenen deutschsprachigen Veröffentlichungen / A B", pairs.get("ParallelTitle_mainTitle").get(0));
		assertEquals("No type specified", pairs.get("ParallelTitle_type").get(0));
		assertEquals("Hauptsacht. anfangs: ", pairs.get("ParallelTitle_displayText").get(0));
		assertEquals("9", pairs.get("ParallelTitle_noteAndAddedEntry").get(0));
		assertEquals("Not applicable/No information provided/Earliest available publisher", pairs.get("Publication_sequenceOfPublishingStatements").get(0));
		assertEquals("Buchhändler-Vereinigung", pairs.get("Publication_agent").get(0));
		assertEquals("Frankfurt, M. :", pairs.get("Publication_place").get(0));
		assertEquals("1991 - 2003; damit Ersch. eingest.", pairs.get("DatesOfPublication").get(0));
		assertEquals("Unformatted note", pairs.get("DatesOfPublication_format").get(0));
		assertEquals("Ersch. wöchentlich, jedes 4.-5. Heft als Monatsregister", pairs.get("NumberingPeculiarities").get(0));
		assertEquals("84!(09-02-04)", pairs.get("BemerkungenZurTitelaufnahme").get(0));
		assertEquals("Abschluss einer RSWK-Kettenfolge, Feld enthält dann zwei $5", pairs.get("RSWKKette_nummerDesKettengliedes").get(0));
		assertEquals("(DE-576)208865578", pairs.get("RSWKKette_0").get(0));
		assertEquals("Bibliografie", pairs.get("RSWKKette_a").get(0));
		assertEquals("Nummer der RSWK-Kette: 0", pairs.get("RSWKKette_nummerDerRSWKKette").get(0));
		assertEquals("s", pairs.get("RSWKKette_D").get(0));
		assertEquals("DE-101", pairs.get("RSWKKette_5").get(0));
		assertEquals("Deutsche Bibliothek", pairs.get("AddedCorporateName").get(0));
		assertEquals("(DE-576)190187867", pairs.get("AddedCorporateName_authorityRecordControlNumber").get(0));
		assertEquals("190187867", pairs.get("AddedCorporateName_authorityRecordControlNumber_recordNumber").get(0));
		assertEquals("Bibliotheksservice-Zentrum Baden-Württemberg (BSZ)", pairs.get("AddedCorporateName_authorityRecordControlNumber_organization").get(0));
		assertEquals("DE-576", pairs.get("AddedCorporateName_authorityRecordControlNumber_organizationCode").get(0));
		assertEquals("No information provided", pairs.get("AddedCorporateName_entryType").get(0));
		assertEquals("Name in direct order", pairs.get("AddedCorporateName_nameType").get(0));
		assertEquals("Index zu", pairs.get("PartOf_relation").get(0));
		assertEquals("No display constant generated", pairs.get("PartOf_displayConstant").get(0));
		assertEquals("(DE-600)1056366-0", pairs.get("PartOf_recordControlNumber").get(0));
		assertEquals("Display note", pairs.get("PartOf_noteController").get(0));
		assertEquals("1056366-0", pairs.get("PartOf_recordControlNumber_recordNumber").get(0));
		assertEquals("Zeitschriftendatenbank (ZDB)", pairs.get("PartOf_recordControlNumber_organization").get(0));
		assertEquals("DE-600", pairs.get("PartOf_recordControlNumber_organizationCode").get(0));
		assertEquals("Deutsche Nationalbibliografie und Bibliografie der im Ausland erschienenen deutschsprachigen Veröffentlichungen / B", pairs.get("PartOf").get(0));
		assertEquals("Deutsche Bibliographie. Wöchentliches Verzeichnis. Reihe A und Reihe B, Erscheinungen des Verlagsbuchhandels und außerhalb des Verlagsbuchhandels : Register", pairs.get("PrecededBy").get(0));
		assertEquals("Vorg.", pairs.get("PrecededBy_relation").get(0));
		assertEquals("Zeitschriftendatenbank (ZDB)", pairs.get("PrecededBy_recordControlNumber_organization").get(0));
		assertEquals("1429255", pairs.get("PrecededBy_recordControlNumber_recordNumber").get(0));
		assertEquals("Continues", pairs.get("PrecededBy_typeOfRelationship").get(0));
		assertEquals("(DE-600)1429255", pairs.get("PrecededBy_recordControlNumber").get(0));
		assertEquals("DE-600", pairs.get("PrecededBy_recordControlNumber_organizationCode").get(0));
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
		List<MarcSubfield> subfields = adminMeta.getSubfields();
		for (MarcSubfield subfield : subfields) {
			if (subfield.getCode().equals("b")) {
				assertEquals("LanguageCodes", subfield.getDefinition().getCodeList().getClass().getSimpleName());
				assertEquals("English", subfield.resolve());
			}
		}

		assertEquals(Arrays.asList("English"), record.getKeyValuePairs().get("AdminMetadata_languageOfCataloging"));
	}

}
