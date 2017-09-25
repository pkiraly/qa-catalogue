package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Function Term Source Codes
 * http://www.loc.gov/standards/sourcelist/function.html
 */
public class FunctionTermSourceCodes extends CodeList {

	private void initialize() {
		codes = Utils.generateCodes(
			"dot", "Dictionary of occupational titles (Washington: United States Dept. of Labor, Employment and Training Administration, United States Employment Service)"
		);
		indexCodes();
	}

	private static FunctionTermSourceCodes uniqueInstance;

	private FunctionTermSourceCodes() {
		initialize();
	}

	public static FunctionTermSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new FunctionTermSourceCodes();
		return uniqueInstance;
	}
}