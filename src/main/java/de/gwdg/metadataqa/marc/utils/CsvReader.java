package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.api.util.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CsvReader {

	public static Map<String, String> read(String fileName) throws IOException, URISyntaxException {
		ClassLoader classLoader = CsvReader.class.getClassLoader();

		Map<String, String> dict = new HashMap<>();
		LineIterator it = IOUtils.lineIterator(classLoader.getResourceAsStream(fileName), Charset.defaultCharset());
		while (it.hasNext()) {
			String line = it.nextLine();
			if (line.equals("") || line.startsWith("#"))
				continue;
			String[] parts = line.split(";", 2);
			dict.put(parts[0], parts[1]);
		}

		return dict;
	}
}
