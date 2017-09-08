package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

public class TemporalTermSourceCodes  extends CodeList {

	static {
		codes = Utils.generateCodes(
		);
		indexCodes();
	}

	private static TemporalTermSourceCodes uniqueInstance;

	private TemporalTermSourceCodes() {}

	public static TemporalTermSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new TemporalTermSourceCodes();
		return uniqueInstance;
	}

}
