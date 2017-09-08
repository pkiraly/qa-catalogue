package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Abbreviated Title Source Codes
 * http://www.loc.gov/standards/sourcelist/abbreviated-title.html
 * used in Bibliographic records 210 $2 (Abbreviated Title / Source)
 */
public class AbbreviatedTitleSourceCodes extends CodeList {

	static {
		codes = Utils.generateCodes(
				"din1430", "Key Title nach DIN 1430 (Berlin: Beuth)",
				"din1502", "Regeln für das Kürzen von Wörtern in Titeln und für das Kürzen der Titel von Veröffentlichungen: DIN 1502 (Berlin; Köln: Beuth)",
				"dnlm", "National Library of Medicine Locatorplus (Bethesda, MD: National Library of Medicine)",
				"hlasja", "HLAS journal abbreviations (Washington, DC: Library of Congress, Hispanic Division",
				"inisaljt", "INIS: authority list for journal titles",
				"issnkey", "Abbreviated Key Titles"
		);
		indexCodes();
	}

	private static AbbreviatedTitleSourceCodes uniqueInstance;

	private AbbreviatedTitleSourceCodes() {}

	public static AbbreviatedTitleSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new AbbreviatedTitleSourceCodes();
		return uniqueInstance;
	}
}