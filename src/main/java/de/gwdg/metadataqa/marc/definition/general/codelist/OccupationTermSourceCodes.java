package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Occupation Term Source Codes
 * http://www.loc.gov/standards/sourcelist/occupation.html
 */
public class OccupationTermSourceCodes extends CodeList {

	private void initialize() {
		codes = Utils.generateCodes(
			"dot", "Dictionary of occupational titles (Washington: United States Dept. of Labor, Employment and Training Administration, United States Employment Service)",
			"iaat", "IAA thesaurus: terminologie der Arbeit, Beschüftigung und Ausbildung (Genf: Internationales arbeitsamt)",
			"ilot", "ILO thesaurus: labour, employment and training terminology (Geneva: International Labour Office)",
			"itoamc", "Index terms for occupations in archival and manuscript collections (Washington, DC: Library of Congress, Manuscript Division)",
			"lcdgt", "LC Demographic Group Terms (Washington, DC: Library of Congress)",
			"onet", "ONET - Occupational Information Network",
			"raam", "Register of Australian Archives & Manuscripts Occupations Thesaurus (Canberra: National Library of Australia)",
			"rvmgd", "Thésaurus des descripteurs de groupes démographiques de l'Université Laval Québec: Université Laval)",
			"tbit", "Thésaurus BIT: terminologie du travail, de l'emploi et de la formation (Genève: Bureau international du travail)",
			"toit", "Tesauro OIT: terminología del trabajo, el empleo y la formación (Geneva: Oficina Internacional del Trabajo)",
			"trot", "Thesaurus of Religious Occupational Terms (TROT) (Chicago, Illinois: American Theological Library Association (ATLA))"
		);
		indexCodes();
	}

	private static OccupationTermSourceCodes uniqueInstance;

	private OccupationTermSourceCodes() {
		initialize();
	}

	public static OccupationTermSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new OccupationTermSourceCodes();
		return uniqueInstance;
	}
}