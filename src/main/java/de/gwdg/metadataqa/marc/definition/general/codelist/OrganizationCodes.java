package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.utils.CsvReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

/**
 * MARC Organization Codes
 * http://www.loc.gov/marc/organizations/orgshome.html
 * <p>
 * Note: this is not a full list!
 */
public class OrganizationCodes extends CodeList {

	private void initialize() {
		Map<String, String> dict = null;
		try {
			dict = CsvReader.read("marc/organization-codes.csv");
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		codes = new ArrayList<>();
		for (String key : dict.keySet()) {
			codes.add((new Code(key, dict.get(key))));
		}

		indexCodes();
	}

	private static OrganizationCodes uniqueInstance;

	private OrganizationCodes() {
		initialize();
	}

	public static OrganizationCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new OrganizationCodes();
		return uniqueInstance;
	}
}
