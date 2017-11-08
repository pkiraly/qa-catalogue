package de.gwdg.metadataqa.marc;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;

public class MarcRecordTest {

	private static final Pattern positionalPattern = Pattern.compile("^(Leader|00[678])/(.*)$");

	@Test
	public void test() {
		Matcher matcher = positionalPattern.matcher("006/0");
		assertTrue(matcher.matches());
	}
}
