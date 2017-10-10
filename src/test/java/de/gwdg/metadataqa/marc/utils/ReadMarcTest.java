package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.Control001;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcRecord;
import org.junit.Test;
import org.marc4j.marc.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReadMarcTest {
	private static ClassLoader classLoader = ReadMarcTest.class.getClassLoader();

	@Test
	public void test() throws Exception {
		Path path = FileUtils.getPath("general/0001-01.mrc");
		System.err.println(path);
		List<Record> records = ReadMarc.read(path.toString());
		for (Record marc4jRecord : records) {
			MarcRecord record = MarcFactory.createFromMarc4j(marc4jRecord);
			assertEquals(marc4jRecord.getLeader().marshal(), record.getLeader().getLeaderString());
			System.err.println(record.getLeader().getLeaderString());
			System.err.printf("%s: %s\n", Control001.getMqTag(), record.getControl001().getContent());
			System.err.println(record.formatForIndex());
		}
	}
}
