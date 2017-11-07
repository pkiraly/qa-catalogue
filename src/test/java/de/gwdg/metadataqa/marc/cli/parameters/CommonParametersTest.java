package de.gwdg.metadataqa.marc.cli.parameters;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class CommonParametersTest {

	@Test
	public void testDefaults() {
		String[] arguments = new String[]{"a-marc-file.mrc"};
		try {
			CommonParameters parameters = new CommonParameters(arguments);
			assertFalse(parameters.doHelp());

			assertTrue(parameters.doLog);

			assertNotNull(parameters.getArgs());
			assertEquals(1, parameters.getArgs().length);
			assertEquals("a-marc-file.mrc", parameters.getArgs()[0]);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testHelp() {
		String[] arguments = new String[]{"--help", "a-marc-file.mrc"};
		try {
			CommonParameters parameters = new CommonParameters(arguments);
			assertTrue(parameters.doHelp());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testNoLog() {
		String[] arguments = new String[]{"--nolog", "a-marc-file.mrc"};
		try {
			CommonParameters parameters = new CommonParameters(arguments);
			assertFalse(parameters.doLog());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
