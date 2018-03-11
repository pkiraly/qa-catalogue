package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.controlsubfields.Control006Subfields;
import de.gwdg.metadataqa.marc.definition.controlsubfields.ControlSubfieldList;
import de.gwdg.metadataqa.marc.definition.controlsubfields.tag006.Tag006all00;
import de.gwdg.metadataqa.marc.definition.controlsubfields.tag006.Tag006continuing01;
import de.gwdg.metadataqa.marc.definition.controltype.Control008Type;
import de.gwdg.metadataqa.marc.definition.controltype.ControlType;
import de.gwdg.metadataqa.marc.definition.tags.control.Control006Definition;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Control006Test {

	@Test
	public void testSa() {
		Control006 field = new Control006("sa", Leader.Type.CONTINUING_RESOURCES);
		Control006Definition definition = (Control006Definition)field.getDefinition();

		assertNotNull("not null", definition.getControlSubfields());
		assertEquals(8, definition.getControlSubfields().size());

		assertEquals(Leader.Type.CONTINUING_RESOURCES, field.getRecordType());

		List<ControlSubfieldDefinition> subfields =
			definition.getControlSubfields()
			.get(Control008Type.CONTINUING_RESOURCES.getValue());

		assertEquals(2, field.getValuesList().size());
		assertEquals("s", field.getValuesList().get(0).getValue());
		assertEquals("tag006all00", field.getValuesList().get(0).getDefinition().getId());
		assertEquals("a", field.getValuesList().get(1).getValue());
		assertEquals("tag006continuing01", field.getValuesList().get(1).getDefinition().getId());

		assertEquals(2, field.getValueMap().size());
		assertEquals("s", field.getValueMap().get(Tag006all00.getInstance()));
		assertEquals("a", field.getValueMap().get(Tag006continuing01.getInstance()));

		assertEquals(
			11,
			definition.getControlSubfields()
			.get(Control008Type.CONTINUING_RESOURCES.getValue())
			.size()
		);

		assertEquals("0, 1", StringUtils.join(field.getSubfieldPositions(), ", "));
	}

	@Test
	public void testAp() {
		Control006 field = new Control006("a|||||||||||||||p|", Leader.Type.BOOKS);
		assertEquals(11, field.getValuesList().size());
		assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
		assertEquals("Language material", field.getValuesList().get(0).resolve());
		assertEquals("Illustrations", field.getValuesList().get(1).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(1).resolve());
		assertEquals("Target audience", field.getValuesList().get(2).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(2).resolve());
		assertEquals("Form of item", field.getValuesList().get(3).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(3).resolve());
		assertEquals("Nature of contents", field.getValuesList().get(4).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(4).resolve());
		assertEquals("Government publication", field.getValuesList().get(5).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(5).resolve());
		assertEquals("Conference publication", field.getValuesList().get(6).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(6).resolve());
		assertEquals("Festschrift", field.getValuesList().get(7).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(7).resolve());
		assertEquals("Index", field.getValuesList().get(8).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(8).resolve());
		assertEquals("Literary form", field.getValuesList().get(9).getDefinition().getLabel());
		assertEquals("Poetry", field.getValuesList().get(9).resolve());
		assertEquals("Biography", field.getValuesList().get(10).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(10).resolve());
	}

	@Test
	public void testSrw() {
		Control006 field = new Control006("swr n|      0   b0", Leader.Type.CONTINUING_RESOURCES);
		assertEquals(12, field.getValuesList().size());
		for (ControlValue controlValue : field.getValuesList()) {
			System.err.println(controlValue.getDefinition().getLabel()
				+ " " + controlValue.resolve());
		}
		assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
		assertEquals("Serial/Integrating resource", field.getValuesList().get(0).resolve());
		assertEquals("Frequency", field.getValuesList().get(1).getDefinition().getLabel());
		assertEquals("Weekly", field.getValuesList().get(1).resolve());
		assertEquals("Regularity", field.getValuesList().get(2).getDefinition().getLabel());
		assertEquals("Regular", field.getValuesList().get(2).resolve());
		assertEquals("Type of continuing resource", field.getValuesList().get(3).getDefinition().getLabel());
		assertEquals("Newspaper", field.getValuesList().get(3).resolve());
		assertEquals("Form of original item", field.getValuesList().get(4).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(4).resolve());
		assertEquals("Form of item", field.getValuesList().get(5).getDefinition().getLabel());
		assertEquals("None of the following", field.getValuesList().get(5).resolve());
		assertEquals("Nature of entire work", field.getValuesList().get(6).getDefinition().getLabel());
		assertEquals("Not specified", field.getValuesList().get(6).resolve());
		assertEquals("Nature of contents", field.getValuesList().get(7).getDefinition().getLabel());
		assertEquals("Not specified", field.getValuesList().get(7).resolve());
		assertEquals("Government publication", field.getValuesList().get(8).getDefinition().getLabel());
		assertEquals("Not a government publication", field.getValuesList().get(8).resolve());
		assertEquals("Conference publication", field.getValuesList().get(9).getDefinition().getLabel());
		assertEquals("Not a conference publication", field.getValuesList().get(9).resolve());
		assertEquals("Original alphabet or script of title", field.getValuesList().get(10).getDefinition().getLabel());
		assertEquals("Extended Roman", field.getValuesList().get(10).resolve());
		assertEquals("Entry convention", field.getValuesList().get(11).getDefinition().getLabel());
		assertEquals("Successive entry", field.getValuesList().get(11).resolve());
	}

	@Test
	public void testIjf() {
		Control006 field = new Control006("i||||j ||||||f| | ", Leader.Type.MUSIC);
		assertEquals(9, field.getValuesList().size());
		assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
		assertEquals("Nonmusical sound recording", field.getValuesList().get(0).resolve());
		assertEquals("Form of composition", field.getValuesList().get(1).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(1).resolve());
		assertEquals("Format of music", field.getValuesList().get(2).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(2).resolve());
		assertEquals("Music parts", field.getValuesList().get(3).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(3).resolve());
		assertEquals("Target audience", field.getValuesList().get(4).getDefinition().getLabel());
		assertEquals("Juvenile", field.getValuesList().get(4).resolve());
		assertEquals("Form of item", field.getValuesList().get(5).getDefinition().getLabel());
		assertEquals("None of the following", field.getValuesList().get(5).resolve());
		assertEquals("Accompanying matter", field.getValuesList().get(6).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(6).resolve());
		assertEquals("Literary text for sound recordings", field.getValuesList().get(7).getDefinition().getLabel());
		assertEquals("Fiction, No attempt to code", field.getValuesList().get(7).resolve());
		assertEquals("Transposition and arrangement", field.getValuesList().get(8).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(8).resolve());
	}
}
