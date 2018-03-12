package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.controlsubfields.Control008Subfields;
import de.gwdg.metadataqa.marc.definition.controlsubfields.LeaderSubfields;
import de.gwdg.metadataqa.marc.definition.controltype.Control008Type;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ControlValueTest {

	@Test
	public void testValidate() {
		List<ControlSubfieldDefinition> subfields = Control008Subfields.getInstance().get(Control008Type.BOOKS);
		ControlSubfieldDefinition subfield = null;
		for (ControlSubfieldDefinition _subfield : subfields) {
			if (_subfield.getId().equals("tag008book18")) {
				subfield = _subfield;
				break;
			}
		}
		ControlValue value = new ControlValue(subfield, "af  ");
		assertTrue(StringUtils.join(value.getErrors(), "; "), value.validate(null));
		assertTrue(value.validate(MarcVersion.MARC21));
		assertEquals(Arrays.asList(), value.getErrors());
		assertEquals(Arrays.asList(), value.getValidationErrors());
	}

	@Test
	public void generateCode() {
		// List<ControlSubfield> subfields = Control006Subfields.get(Control008Type.MIXED_MATERIALS);
		List<ControlSubfieldDefinition> subfields = LeaderSubfields.getSubfieldList();
		for (ControlSubfieldDefinition subfield : subfields) {
			switch (subfield.getId()) {
				case "leader00": testLeader00(subfield); break;
				case "leader05": testLeader05(subfield); break;
				case "leader06": testLeader06(subfield); break;
				case "leader07": testLeader07(subfield); break;
				case "leader08": testLeader08(subfield); break;
				case "leader09": testLeader09(subfield); break;
				case "leader10": testLeader10(subfield); break;
				case "leader11": testLeader11(subfield); break;
				case "leader12": testLeader12(subfield); break;
				case "leader17": testLeader17(subfield); break;
				case "leader18": testLeader18(subfield); break;
				case "leader19": testLeader19(subfield); break;
				case "leader20": testLeader20(subfield); break;
				case "leader21": testLeader21(subfield); break;
				case "leader22": testLeader22(subfield); break;
			}
			/*
			System.err.printf("===== [%s%s] ====\n", subfield.getId().substring(0, 1).toUpperCase(), subfield.getId().substring(1));
			System.err.printf("label = \"%s\";\n", subfield.getLabel());
			System.err.printf("id = \"%s\";\n", subfield.getId());
			System.err.printf("mqTag = \"%s\";\n", subfield.getMqTag());
			System.err.printf("positionStart = %d;\n", subfield.getPositionStart());
			System.err.printf("positionEnd = %d;\n", subfield.getPositionEnd());
			System.err.printf("descriptionUrl = \"https://www.loc.gov/marc/bibliographic/bdleader.html\";\n", subfield.getMqTag());
			if (subfield.getCodes() != null) {
				System.err.printf("codes = Utils.generateCodes(\n");
				int i = 0;
				for (Code code : subfield.getCodes()) {
					i++;
					if (i == subfield.getCodes().size())
						System.err.printf("\"%s\", \"%s\"\n", code.getCode(), code.getLabel());
					else
						System.err.printf("\"%s\", \"%s\",\n", code.getCode(), code.getLabel());
				}
				System.err.printf(");\n");
			}
			if (subfield.isRepeatableContent()) {
				System.err.printf("repeatableContent = true;\n", subfield.getPositionEnd());
				System.err.printf("unitLength = %d;\n", subfield.getUnitLength());
			}
			if (subfield.getDefaultCode() != null)
				System.err.printf("defaultCode = \"%s\";\n", subfield.getDefaultCode());
			*/
		}
	}

	@Test
	public void testGetLabel() {
		Control006 field = new Control006("e|||||||a|||||||||", Leader.Type.MAPS);
		ControlValue value = field.getTag006all00();
		assertEquals("Form of material", value.getLabel());
	}

	@Test
	public void testId() {
		Control006 field = new Control006("e|||||||a|||||||||", Leader.Type.MAPS);
		ControlValue value = field.getTag006all00();
		assertEquals("tag006all00", value.getId());
	}

	private void testLeader00(ControlSubfieldDefinition subfield) {
		assertEquals("Record length", subfield.getLabel());
		assertEquals("leader00", subfield.getId());
		assertEquals("recordLength", subfield.getMqTag());
		assertEquals(0, subfield.getPositionStart());
		assertEquals(5, subfield.getPositionEnd());
		assertEquals("https://www.loc.gov/marc/bibliographic/bdleader.html", subfield.getDescriptionUrl());
		assertNull(subfield.getCodes());
	}

	private void testLeader05(ControlSubfieldDefinition subfield) {
		assertEquals("Record status", subfield.getLabel());
		assertEquals("leader05", subfield.getId());
		assertEquals("recordStatus", subfield.getMqTag());
		assertEquals(5, subfield.getPositionStart());
		assertEquals(6, subfield.getPositionEnd());
		assertEquals("https://www.loc.gov/marc/bibliographic/bdleader.html", subfield.getDescriptionUrl());
		assertEquals(5, subfield.getCodes().size());
	}

	private void testLeader06(ControlSubfieldDefinition subfield) {
		assertEquals("Type of record", subfield.getLabel());
		assertEquals("leader06", subfield.getId());
		assertEquals("typeOfRecord", subfield.getMqTag());
		assertEquals(6, subfield.getPositionStart());
		assertEquals(7, subfield.getPositionEnd());
		assertEquals("https://www.loc.gov/marc/bibliographic/bdleader.html", subfield.getDescriptionUrl());
		assertEquals(14, subfield.getCodes().size());
	}

	private void testLeader07(ControlSubfieldDefinition subfield) {
		assertEquals("Bibliographic level", subfield.getLabel());
		assertEquals("leader07", subfield.getId());
		assertEquals("bibliographicLevel", subfield.getMqTag());
		assertEquals(7, subfield.getPositionStart());
		assertEquals(8, subfield.getPositionEnd());
		assertEquals("https://www.loc.gov/marc/bibliographic/bdleader.html", subfield.getDescriptionUrl());
		assertEquals(7, subfield.getCodes().size());
	}

	private void testLeader08(ControlSubfieldDefinition subfield) {
		assertEquals("Type of control", subfield.getLabel());
		assertEquals("leader08", subfield.getId());
		assertEquals("typeOfControl", subfield.getMqTag());
		assertEquals(8, subfield.getPositionStart());
		assertEquals(9, subfield.getPositionEnd());
		assertEquals("https://www.loc.gov/marc/bibliographic/bdleader.html", subfield.getDescriptionUrl());
		assertEquals(2, subfield.getCodes().size());
	}

	private void testLeader09(ControlSubfieldDefinition subfield) {
		assertEquals("Character coding scheme", subfield.getLabel());
		assertEquals("leader09", subfield.getId());
		assertEquals("characterCodingScheme", subfield.getMqTag());
		assertEquals(9, subfield.getPositionStart());
		assertEquals(10, subfield.getPositionEnd());
		assertEquals("https://www.loc.gov/marc/bibliographic/bdleader.html", subfield.getDescriptionUrl());
		assertEquals(2, subfield.getCodes().size());
	}

	private void testLeader10(ControlSubfieldDefinition subfield) {
		assertEquals("Indicator count", subfield.getLabel());
		assertEquals("leader10", subfield.getId());
		assertEquals("indicatorCount", subfield.getMqTag());
		assertEquals(10, subfield.getPositionStart());
		assertEquals(11, subfield.getPositionEnd());
		assertEquals("https://www.loc.gov/marc/bibliographic/bdleader.html", subfield.getDescriptionUrl());
		assertNull(subfield.getCodes());
	}

	private void testLeader11(ControlSubfieldDefinition subfield) {
		assertEquals("Subfield code count", subfield.getLabel());
		assertEquals("leader11", subfield.getId());
		assertEquals("subfieldCodeCount", subfield.getMqTag());
		assertEquals(11, subfield.getPositionStart());
		assertEquals(12, subfield.getPositionEnd());
		assertEquals("https://www.loc.gov/marc/bibliographic/bdleader.html", subfield.getDescriptionUrl());
		assertNull(subfield.getCodes());
	}

	private void testLeader12(ControlSubfieldDefinition subfield) {
		assertEquals("Base address of data", subfield.getLabel());
		assertEquals("leader12", subfield.getId());
		assertEquals("baseAddressOfData", subfield.getMqTag());
		assertEquals(12, subfield.getPositionStart());
		assertEquals(17, subfield.getPositionEnd());
		assertEquals("https://www.loc.gov/marc/bibliographic/bdleader.html", subfield.getDescriptionUrl());
		assertNull(subfield.getCodes());
	}

	private void testLeader17(ControlSubfieldDefinition subfield) {
		assertEquals("Encoding level", subfield.getLabel());
		assertEquals("leader17", subfield.getId());
		assertEquals("encodingLevel", subfield.getMqTag());
		assertEquals(17, subfield.getPositionStart());
		assertEquals(18, subfield.getPositionEnd());
		assertEquals("https://www.loc.gov/marc/bibliographic/bdleader.html", subfield.getDescriptionUrl());
		assertEquals(10, subfield.getCodes().size());
	}

	private void testLeader18(ControlSubfieldDefinition subfield) {
		assertEquals("Descriptive cataloging form", subfield.getLabel());
		assertEquals("leader18", subfield.getId());
		assertEquals("descriptiveCatalogingForm", subfield.getMqTag());
		assertEquals(18, subfield.getPositionStart());
		assertEquals(19, subfield.getPositionEnd());
		assertEquals("https://www.loc.gov/marc/bibliographic/bdleader.html", subfield.getDescriptionUrl());
		assertEquals(6, subfield.getCodes().size());
	}

	private void testLeader19(ControlSubfieldDefinition subfield) {
		assertEquals("Multipart resource record level", subfield.getLabel());
		assertEquals("leader19", subfield.getId());
		assertEquals("multipartResourceRecordLevel", subfield.getMqTag());
		assertEquals(19, subfield.getPositionStart());
		assertEquals(20, subfield.getPositionEnd());
		assertEquals("https://www.loc.gov/marc/bibliographic/bdleader.html", subfield.getDescriptionUrl());
		assertEquals(4, subfield.getCodes().size());
	}

	private void testLeader20(ControlSubfieldDefinition subfield) {
		assertEquals("Length of the length-of-field portion", subfield.getLabel());
		assertEquals("leader20", subfield.getId());
		assertEquals("lengthOfTheLengthOfFieldPortion", subfield.getMqTag());
		assertEquals(20, subfield.getPositionStart());
		assertEquals(21, subfield.getPositionEnd());
		assertEquals("https://www.loc.gov/marc/bibliographic/bdleader.html", subfield.getDescriptionUrl());
		assertNull(subfield.getCodes());
	}

	private void testLeader21(ControlSubfieldDefinition subfield) {
		assertEquals("Length of the starting-character-position portion", subfield.getLabel());
		assertEquals("leader21", subfield.getId());
		assertEquals("lengthOfTheStartingCharacterPositionPortion", subfield.getMqTag());
		assertEquals(21, subfield.getPositionStart());
		assertEquals(22, subfield.getPositionEnd());
		assertEquals("https://www.loc.gov/marc/bibliographic/bdleader.html", subfield.getDescriptionUrl());
		assertNull(subfield.getCodes());
	}

	private void testLeader22(ControlSubfieldDefinition subfield) {
		assertEquals("Length of the implementation-defined portion", subfield.getLabel());
		assertEquals("leader22", subfield.getId());
		assertEquals("lengthOfTheImplementationDefinedPortion", subfield.getMqTag());
		assertEquals(22, subfield.getPositionStart());
		assertEquals(23, subfield.getPositionEnd());
		assertEquals("https://www.loc.gov/marc/bibliographic/bdleader.html", subfield.getDescriptionUrl());
		assertNull(subfield.getCodes());
	}
}
