package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Content Advice Classification Source Codes
 * https://www.loc.gov/standards/sourcelist/content-advice.html
 * used in Bibliographic records 520 $2 (Summary, Etc. / Source)
 */
public class ContentAdviceClassificationSourceCodes extends CodeList {

	static {
		codes = Utils.generateCodes(
				"bbfc", "British Board of Film Classification",
				"mpaa", "Motion Picture Association of America Film Ratings"
		);
		indexCodes();
	}

	private static ContentAdviceClassificationSourceCodes uniqueInstance;

	private ContentAdviceClassificationSourceCodes() {}

	public static ContentAdviceClassificationSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new ContentAdviceClassificationSourceCodes();
		return uniqueInstance;
	}
}