package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Curriculum Objective Term and Code Source Codes
 * http://www.loc.gov/standards/sourcelist/curriculum-objective.html
 * used in
 * Bibliographic records 658$2 (Index Term - Curriculum Objective / Source of term or code)
 * Community Information records 658$2 (Index Term - Curriculum Objective / Source of term or code)
 */
public class CurriculumObjectiveTermAndCodeSourceCodes extends CodeList {

	static {
		codes = Utils.generateCodes(
			"abledata", "ABLEDATA thesaurus (Silver Spring, MD: National Rehabilitation Information Center)",
			"acccp", "Australian Cross-curriculum Priorities (Australian Curriculum, Assessment and Reporting Authority (ACARA))",
			"accd", "Australian Curriculum Content Description (Australian Curriculum, Assessment and Reporting Authority (ACARA))",
			"accssd", "ACCS subject directory (West Lafayette, IN: Kappa Delta Pi)",
			"acfr", "Australian Curriculum Framework (Australian Curriculum, Assessment and Reporting Authority (ACARA))",
			"acgc", "Australian Curriculum General Capability (Australian Curriculum, Assessment and Reporting Authority (ACARA))",
			"acsl", "Australian School level (Australian Curriculum, Assessment and Reporting Authority (ACARA))",
			"hdsetl", "HyperAble Data special ed terms list (Madison, WI: Trace Research and Development)",
			"moss", "Missouri show-me standards (Jefferson City, MO: Dept. of Elementary and Secondary Education",
			"ohco", "Ohio curriculum objectives (Columbus, OH: Department of Education)",
			"paas", "Pennsylvania Academic Standards",
			"rehabdat", "REHABDAT thesaurus (Silver Spring, MD: National Rehabilitation Information Center)",
			"slvps", "Standards of Learning for Virginia Public Schools (Richmond, VA: Virginia Department of Education)",
			"teks", "Texas essential knowledge and skills (TEKS) (Austin, TX: Texas Education Agency, Office of Curriculum and Professional Development)",
			"txac", "Texas administrative code. Volume II, chapter 75. Curriculum (Austin, TX: Texas Education Agency, Policy Planning and Evaluation)",
			"udir", "Norwegian curriculum (Utdanningsdirektoratet (Udir)/The Norwegian directorate for education and training)"
		);
		indexCodes();
	}

	private static CurriculumObjectiveTermAndCodeSourceCodes uniqueInstance;

	private CurriculumObjectiveTermAndCodeSourceCodes() {}

	public static CurriculumObjectiveTermAndCodeSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new CurriculumObjectiveTermAndCodeSourceCodes();
		return uniqueInstance;
	}
}