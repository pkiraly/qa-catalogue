package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
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
}
