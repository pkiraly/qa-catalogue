package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.utils.ReadMarc;
import org.junit.Test;
import org.marc4j.marc.Record;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class MarcRecordTest {

	private static final Pattern positionalPattern = Pattern.compile("^(Leader|00[678])/(.*)$");

	@Test
	public void test() {
		Matcher matcher = positionalPattern.matcher("006/0");
		assertTrue(matcher.matches());
	}

	@Test
	public void testFromFile() throws Exception {
		Path path = FileUtils.getPath("general/0001-01.mrc");
		List<Record> records = ReadMarc.read(path.toString());
		for (Record marc4jRecord : records) {
			MarcRecord record = MarcFactory.createFromMarc4j(marc4jRecord);
			System.err.println(record.asJson());
		}
	}
}
