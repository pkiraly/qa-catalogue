package de.gwdg.metadataqa.marc.cli.parameters;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class FormatterParametersTest {

	@Test
	public void testDefaults() {
		String[] arguments = new String[]{"a-marc-file.mrc"};
		try {
			FormatterParameters parameters = new FormatterParameters(arguments);

			assertNotNull(parameters.getArgs());
			assertEquals(1, parameters.getArgs().length);
			assertEquals("a-marc-file.mrc", parameters.getArgs()[0]);

			assertFalse(parameters.doHelp());

			assertNull(parameters.getId());

			assertNull(parameters.getFormat());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testHelp() {
		String[] arguments = new String[]{"--help", "a-marc-file.mrc"};
		try {
			FormatterParameters parameters = new FormatterParameters(arguments);
			assertTrue(parameters.doHelp());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testId() {
		String[] arguments = new String[]{"--id", "xyz", "a-marc-file.mrc"};
		try {
			FormatterParameters parameters = new FormatterParameters(arguments);

			assertNotNull(parameters.getId());
			assertEquals("xyz", parameters.getId());

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFormat() {
		String[] arguments = new String[]{"--format", "xyz", "a-marc-file.mrc"};
		try {
			FormatterParameters parameters = new FormatterParameters(arguments);

			assertNotNull(parameters.getFormat());
			assertEquals("xyz", parameters.getFormat());

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
