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

	@Test
	public void testCsgg() {
		Control006 field = new Control006("csgg         nn   ", Leader.Type.MUSIC);
		assertEquals(9, field.getValuesList().size());
		assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
		assertEquals("Notated music", field.getValuesList().get(0).resolve());
		assertEquals("Form of composition", field.getValuesList().get(1).getDefinition().getLabel());
		assertEquals("Songs", field.getValuesList().get(1).resolve());
		assertEquals("Format of music", field.getValuesList().get(2).getDefinition().getLabel());
		assertEquals("Close score", field.getValuesList().get(2).resolve());
		assertEquals("Music parts", field.getValuesList().get(3).getDefinition().getLabel());
		assertEquals("No parts in hand or not specified", field.getValuesList().get(3).resolve());
		assertEquals("Target audience", field.getValuesList().get(4).getDefinition().getLabel());
		assertEquals("Unknown or unspecified", field.getValuesList().get(4).resolve());
		assertEquals("Form of item", field.getValuesList().get(5).getDefinition().getLabel());
		assertEquals("None of the following", field.getValuesList().get(5).resolve());
		assertEquals("Accompanying matter", field.getValuesList().get(6).getDefinition().getLabel());
		assertEquals("No accompanying matter", field.getValuesList().get(6).resolve());
		assertEquals("Literary text for sound recordings", field.getValuesList().get(7).getDefinition().getLabel());
		assertEquals("Not applicable", field.getValuesList().get(7).resolve());
		assertEquals("Transposition and arrangement", field.getValuesList().get(8).getDefinition().getLabel());
		assertEquals("Not arrangement or transposition or not specified", field.getValuesList().get(8).resolve());
	}

	@Test
	public void testD() {
		Control006 field = new Control006("d|||||||||||||||||", Leader.Type.MUSIC);
		assertEquals(9, field.getValuesList().size());
		assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
		assertEquals("Manuscript notated music", field.getValuesList().get(0).resolve());
		assertEquals("Form of composition", field.getValuesList().get(1).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(1).resolve());
		assertEquals("Format of music", field.getValuesList().get(2).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(2).resolve());
		assertEquals("Music parts", field.getValuesList().get(3).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(3).resolve());
		assertEquals("Target audience", field.getValuesList().get(4).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(4).resolve());
		assertEquals("Form of item", field.getValuesList().get(5).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(5).resolve());
		assertEquals("Accompanying matter", field.getValuesList().get(6).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(6).resolve());
		assertEquals("Literary text for sound recordings", field.getValuesList().get(7).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(7).resolve());
		assertEquals("Transposition and arrangement", field.getValuesList().get(8).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(8).resolve());
	}

	@Test
	public void testEa() {
		Control006 field = new Control006("e|||||||a|||||||||", Leader.Type.MAPS);
		assertEquals(8, field.getValuesList().size());
		assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
		assertEquals("Cartographic material", field.getValuesList().get(0).resolve());
		assertEquals("Relief", field.getValuesList().get(1).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(1).resolve());
		assertEquals("Projection", field.getValuesList().get(2).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(2).resolve());
		assertEquals("Type of cartographic material", field.getValuesList().get(3).getDefinition().getLabel());
		assertEquals("Single map", field.getValuesList().get(3).resolve());
		assertEquals("Government publication", field.getValuesList().get(4).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(4).resolve());
		assertEquals("Form of item", field.getValuesList().get(5).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(5).resolve());
		assertEquals("Index", field.getValuesList().get(6).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(6).resolve());
		assertEquals("Special format characteristics", field.getValuesList().get(7).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(7).resolve());
	}

	@Test
	public void testGqv() {
		Control006 field = new Control006("g|||       |q   v|", Leader.Type.VISUAL_MATERIALS);
		assertEquals(7, field.getValuesList().size());
		assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
		assertEquals("Projected medium", field.getValuesList().get(0).resolve());
		assertEquals("Running time for motion pictures and videorecordings", field.getValuesList().get(1).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(1).resolve());
		assertEquals("Target audience", field.getValuesList().get(2).getDefinition().getLabel());
		assertEquals("Unknown or not specified", field.getValuesList().get(2).resolve());
		assertEquals("Government publication", field.getValuesList().get(3).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(3).resolve());
		assertEquals("Form of item", field.getValuesList().get(4).getDefinition().getLabel());
		assertEquals("Direct electronic", field.getValuesList().get(4).resolve());
		assertEquals("Type of visual material", field.getValuesList().get(5).getDefinition().getLabel());
		assertEquals("Videorecording", field.getValuesList().get(5).resolve());
		assertEquals("Technique", field.getValuesList().get(6).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(6).resolve());
	}

	@Test
	public void testJfmnnnn() {
		Control006 field = new Control006("jfmnn        nn | ", Leader.Type.MUSIC);
		assertEquals(9, field.getValuesList().size());
		assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
		assertEquals("Musical sound recording", field.getValuesList().get(0).resolve());
		assertEquals("Form of composition", field.getValuesList().get(1).getDefinition().getLabel());
		assertEquals("Folk music", field.getValuesList().get(1).resolve());
		assertEquals("Format of music", field.getValuesList().get(2).getDefinition().getLabel());
		assertEquals("Not applicable", field.getValuesList().get(2).resolve());
		assertEquals("Music parts", field.getValuesList().get(3).getDefinition().getLabel());
		assertEquals("Not applicable", field.getValuesList().get(3).resolve());
		assertEquals("Target audience", field.getValuesList().get(4).getDefinition().getLabel());
		assertEquals("Unknown or unspecified", field.getValuesList().get(4).resolve());
		assertEquals("Form of item", field.getValuesList().get(5).getDefinition().getLabel());
		assertEquals("None of the following", field.getValuesList().get(5).resolve());
		assertEquals("Accompanying matter", field.getValuesList().get(6).getDefinition().getLabel());
		assertEquals("No accompanying matter", field.getValuesList().get(6).resolve());
		assertEquals("Literary text for sound recordings", field.getValuesList().get(7).getDefinition().getLabel());
		assertEquals("Not applicable", field.getValuesList().get(7).resolve());
		assertEquals("Transposition and arrangement", field.getValuesList().get(8).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(8).resolve());
	}

	@Test
	public void testKr() {
		Control006 field = new Control006("k|||||||||||||||r|", Leader.Type.VISUAL_MATERIALS);
		assertEquals(7, field.getValuesList().size());
		assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
		assertEquals("Two-dimensional nonprojectable graphic", field.getValuesList().get(0).resolve());
		assertEquals("Running time for motion pictures and videorecordings", field.getValuesList().get(1).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(1).resolve());
		assertEquals("Target audience", field.getValuesList().get(2).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(2).resolve());
		assertEquals("Government publication", field.getValuesList().get(3).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(3).resolve());
		assertEquals("Form of item", field.getValuesList().get(4).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(4).resolve());
		assertEquals("Type of visual material", field.getValuesList().get(5).getDefinition().getLabel());
		assertEquals("Realia", field.getValuesList().get(5).resolve());
		assertEquals("Technique", field.getValuesList().get(6).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(6).resolve());
	}

	@Test
	public void testMbqi() {
		Control006 field = new Control006("m    bq  i |      ", Leader.Type.COMPUTER_FILES);
		assertEquals(5, field.getValuesList().size());
		assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
		assertEquals("Computer file", field.getValuesList().get(0).resolve());
		assertEquals("Target audience", field.getValuesList().get(1).getDefinition().getLabel());
		assertEquals("Primary", field.getValuesList().get(1).resolve());
		assertEquals("Form of item", field.getValuesList().get(2).getDefinition().getLabel());
		assertEquals("Direct electronic", field.getValuesList().get(2).resolve());
		assertEquals("Type of computer file", field.getValuesList().get(3).getDefinition().getLabel());
		assertEquals("Interactive multimedia", field.getValuesList().get(3).resolve());
		assertEquals("Government publication", field.getValuesList().get(4).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(4).resolve());
	}

	@Test
	public void testRou() {
		Control006 field = new Control006("r|||            ou", Leader.Type.VISUAL_MATERIALS);
		assertEquals(7, field.getValuesList().size());
		assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
		assertEquals("Three-dimensional artifact or naturally occurring object", field.getValuesList().get(0).resolve());
		assertEquals("Running time for motion pictures and videorecordings", field.getValuesList().get(1).getDefinition().getLabel());
		assertEquals("No attempt to code", field.getValuesList().get(1).resolve());
		assertEquals("Target audience", field.getValuesList().get(2).getDefinition().getLabel());
		assertEquals("Unknown or not specified", field.getValuesList().get(2).resolve());
		assertEquals("Government publication", field.getValuesList().get(3).getDefinition().getLabel());
		assertEquals("Not a government publication", field.getValuesList().get(3).resolve());
		assertEquals("Form of item", field.getValuesList().get(4).getDefinition().getLabel());
		assertEquals("None of the following", field.getValuesList().get(4).resolve());
		assertEquals("Type of visual material", field.getValuesList().get(5).getDefinition().getLabel());
		assertEquals("Flash card", field.getValuesList().get(5).resolve());
		assertEquals("Technique", field.getValuesList().get(6).getDefinition().getLabel());
		assertEquals("Unknown", field.getValuesList().get(6).resolve());
	}
}
