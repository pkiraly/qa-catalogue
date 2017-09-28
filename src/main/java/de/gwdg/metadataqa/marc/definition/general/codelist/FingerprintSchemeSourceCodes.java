package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Fingerprint Scheme Source Codes
 * http://www.loc.gov/standards/sourcelist/fingerprint.html
 * used in
 * Bibliographic records 026 $2 (Fingerprint Identifier / Source)
 */
public class FingerprintSchemeSourceCodes extends CodeList {

	private void initialize() {
		codes = Utils.generateCodes(
			"fei", "Fingerprints = Empreintes = Impronte (Paris: Institut de recherche et d'histoire des textes)",
			"stcnf", "Vriesma, P.C.A. The STCN [Short title catalogue Netherlands] fingerprint (in Studies in bibliography, v. 39, 1986, p. 93-100) (s'-Gravenhage: Koninklijke Bibliotheek)"
		);
		indexCodes();
	}

	private static FingerprintSchemeSourceCodes uniqueInstance;

	private FingerprintSchemeSourceCodes() {
		initialize();
	}

	public static FingerprintSchemeSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new FingerprintSchemeSourceCodes();
		return uniqueInstance;
	}
}