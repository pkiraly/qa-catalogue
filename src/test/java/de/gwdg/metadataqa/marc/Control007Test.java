package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.Control007Category;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Control007Test {

	public Control007Test() {
	}

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
	public void testTu() {
		Control007 field = new Control007("tu");
		assertEquals("tu", field.getContent());
		assertEquals(Control007Category.Text, field.getCategory());
		assertEquals("Text", field.getCategoryOfMaterial());

		ControlSubfield subfield;
		subfield = field.getSubfieldByPosition(0);
		assertEquals("Category of material", subfield.getLabel());
		assertEquals("Text", field.getMap().get(subfield));

		subfield = field.getSubfieldByPosition(1);
		assertEquals("Specific material designation", subfield.getLabel());
		assertEquals("u", field.getMap().get(subfield));
		assertEquals("Unspecified", field.resolve(subfield));
	}

	@Test
	public void testCruuuuuuuu() {
		Control007 field = new Control007("cr uuu---uuuuu");
		assertEquals("cr uuu---uuuuu", field.getContent());

		assertEquals(Control007Category.ElectronicResource, field.getCategory());
		assertEquals("Electronic resource", field.getCategoryOfMaterial());

		assertEquals(11, field.getMap().size());
		Set<Integer> subfieldPositions = field.getSubfieldPositions();
		assertEquals("0, 1, 3, 4, 5, 6, 9, 10, 11, 12, 13",
				StringUtils.join(subfieldPositions, ", "));

		ControlSubfield subfield;
		subfield = field.getSubfieldByPosition(0);
		assertEquals("Category of material", subfield.getLabel());
		assertEquals("Electronic resource", field.getMap().get(subfield));

		subfield = field.getSubfieldByPosition(1);
		assertEquals("Specific material designation", subfield.getLabel());
		assertEquals("r", field.getMap().get(subfield));
		assertEquals("Remote", field.resolve(subfield));

		subfield = field.getSubfieldByPosition(3);
		assertEquals("Color", subfield.getLabel());
		assertEquals("u", field.getMap().get(subfield));
		assertEquals("Unknown", field.resolve(subfield));

		subfield = field.getSubfieldByPosition(4);
		assertEquals("Dimensions", subfield.getLabel());
		assertEquals("u", field.getMap().get(subfield));
		assertEquals("Unknown", field.resolve(subfield));

		subfield = field.getSubfieldByPosition(5);
		assertEquals("Sound", subfield.getLabel());
		assertEquals("u", field.getMap().get(subfield));
		assertEquals("Unknown", field.resolve(subfield));

		subfield = field.getSubfieldByPosition(6);
		assertEquals("Image bit depth", subfield.getLabel());
		assertEquals("---", field.getMap().get(subfield));
		assertEquals("Unknown", field.resolve(subfield));

		subfield = field.getSubfieldByPosition(9);
		assertEquals("File formats", subfield.getLabel());
		assertEquals("u", field.getMap().get(subfield));
		assertEquals("Unknown", field.resolve(subfield));

		subfield = field.getSubfieldByPosition(10);
		assertEquals("Quality assurance targets", subfield.getLabel());
		assertEquals("u", field.getMap().get(subfield));
		assertEquals("Unknown", field.resolve(subfield));

		subfield = field.getSubfieldByPosition(11);
		assertEquals("Antecedent/source", subfield.getLabel());
		assertEquals("u", field.getMap().get(subfield));
		assertEquals("Unknown", field.resolve(subfield));

		subfield = field.getSubfieldByPosition(12);
		assertEquals("Level of compression", subfield.getLabel());
		assertEquals("u", field.getMap().get(subfield));
		assertEquals("Unknown", field.resolve(subfield));

		subfield = field.getSubfieldByPosition(13);
		assertEquals("Reformatting quality", subfield.getLabel());
		assertEquals("u", field.getMap().get(subfield));
		assertEquals("Unknown", field.resolve(subfield));
	}

	@Test
	/**
	 * see K. Coyle: MARC21 as Data: A Start
	 * (http://journal.code4lib.org/articles/5468)
	 */
	public void testSdfsngnned() {
		Control007 field = new Control007("sd fsngnn   ed");
		assertEquals("sd fsngnn   ed", field.getContent());

		assertEquals(Control007Category.SoundRecording, field.getCategory());
		assertEquals("Sound recording", field.getCategoryOfMaterial());

		assertEquals(13, field.getMap().size());
		Set<Integer> subfieldPositions = field.getSubfieldPositions();
		assertEquals("0, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13",
				StringUtils.join(subfieldPositions, ", "));

		ControlSubfield subfield;
		subfield = field.getSubfieldByPosition(0);
		assertEquals("Category of material", subfield.getLabel());
		assertEquals("Sound recording", field.getMap().get(subfield));

		subfield = field.getSubfieldByPosition(1);
		assertEquals("Specific material designation", subfield.getLabel());
		assertEquals("d", field.getMap().get(subfield));
		assertEquals("Sound disc", field.resolve(subfield));
		assertEquals("d", field.getSoundRecording01().getValue());
		assertEquals("Sound disc", field.getSoundRecording01().resolve());

		subfield = field.getSubfieldByPosition(3);
		assertEquals("Speed", subfield.getLabel());
		assertEquals("f", field.getMap().get(subfield));
		assertEquals("1.4 m. per second (discs)", field.resolve(subfield));
		assertEquals("f", field.getSoundRecording03().getValue());
		assertEquals("1.4 m. per second (discs)", field.getSoundRecording03().resolve());

		subfield = field.getSubfieldByPosition(4);
		assertEquals("Configuration of playback channels", subfield.getLabel());
		assertEquals("s", field.getMap().get(subfield));
		assertEquals("Stereophonic", field.resolve(subfield));
		assertEquals("s", field.getSoundRecording04().getValue());
		assertEquals("Stereophonic", field.getSoundRecording04().resolve());

		subfield = field.getSubfieldByPosition(5);
		assertEquals("Groove width/groove pitch", subfield.getLabel());
		assertEquals("n", field.getMap().get(subfield));
		assertEquals("Not applicable", field.resolve(subfield));

		subfield = field.getSubfieldByPosition(6);
		assertEquals("Dimensions", subfield.getLabel());
		assertEquals("g", field.getMap().get(subfield));
		assertEquals("4 3/4 in. or 12 cm. diameter", field.resolve(subfield));

		subfield = field.getSubfieldByPosition(7);
		assertEquals("Tape width", subfield.getLabel());
		assertEquals("n", field.getMap().get(subfield));
		assertEquals("Not applicable", field.resolve(subfield));

		subfield = field.getSubfieldByPosition(8);
		assertEquals("Tape configuration", subfield.getLabel());
		assertEquals("n", field.getMap().get(subfield));
		assertEquals("Not applicable", field.resolve(subfield));

		subfield = field.getSubfieldByPosition(9);
		assertEquals("Kind of disc, cylinder, or tape", subfield.getLabel());
		assertEquals(" ", field.getMap().get(subfield));
		assertEquals(" ", field.resolve(subfield));

		subfield = field.getSubfieldByPosition(10);
		assertEquals("Kind of material", subfield.getLabel());
		assertEquals(" ", field.getMap().get(subfield));
		assertEquals(" ", field.resolve(subfield));

		subfield = field.getSubfieldByPosition(11);
		assertEquals("Kind of cutting", subfield.getLabel());
		assertEquals(" ", field.getMap().get(subfield));
		assertEquals(" ", field.resolve(subfield));

		subfield = field.getSubfieldByPosition(12);
		assertEquals("Special playback characteristics", subfield.getLabel());
		assertEquals("e", field.getMap().get(subfield));
		assertEquals("Digital recording", field.resolve(subfield));

		subfield = field.getSubfieldByPosition(13);
		assertEquals("Capture and storage technique", subfield.getLabel());
		assertEquals("d", field.getMap().get(subfield));
		assertEquals("Digital storage", field.resolve(subfield));
	}
}
