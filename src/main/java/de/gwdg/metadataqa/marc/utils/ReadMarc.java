package de.gwdg.metadataqa.marc.utils;

import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.marc.Record;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ReadMarc {

	public static List<Record> read(String fileName) throws Exception {
		InputStream in = new FileInputStream(fileName);
		MarcReader reader = new MarcStreamReader(in);

		List<Record> records = new ArrayList<>();
		while (reader.hasNext()) {
			Record marc4jRecord = reader.next();
			records.add(marc4jRecord);
			// System.out.println(record.toString());
		}
		return records;
	}

	public static MarcReader getReader(String fileName) throws Exception {
		InputStream in = new FileInputStream(fileName);
		MarcReader reader = new MarcStreamReader(in);
		return reader;
	}

}
