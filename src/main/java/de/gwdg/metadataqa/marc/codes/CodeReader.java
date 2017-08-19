package de.gwdg.metadataqa.marc.codes;

import de.gwdg.metadataqa.api.util.FileUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class CodeReader {

	public static Map<String, StandardIdentifier> readStandardIdentifiers() 
			throws URISyntaxException, IOException {
		List<String> lines = FileUtils.readLines("standard-identifier.csv");
		Map<String, StandardIdentifier> standardIdentifiers = new HashMap<>();

		for (String line : lines) {
			String[] fields = line.split(";");
			standardIdentifiers.put(
				fields[0],
				new StandardIdentifier(
					fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]));
		}

		return standardIdentifiers;
	}
}
