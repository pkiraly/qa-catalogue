package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Subject Category Code Source Codes
 * http://www.loc.gov/standards/sourcelist/subject-category.html
 */
public class SubjectCategoryCodeSourceCodes extends CodeList {

	static {
		codes = Utils.generateCodes(
			"aat", "Art & architecture thesaurus (Los Angeles, CA: Getty Research Institute, Getty Vocabulary Program",
			"agricola", "AGRICOLA subject category codes (Agriculture Network Information Center)",
			"agrissc", "AGRIS: subject categories (Rome: AGRIS Coordinating Centre)",
			"biccbmc", "BIC Children's Books Marketing Classifications (London: Book Industry Communication)",
			"bicssc", "BIC standard subject categories (London: Book Industry Communication)",
			"biosissg", "BIOSIS search guide (Philadelphia: BioSciences Info. Service, Biological Abstracts)",
			"bisacmt", "BISAC Merchandising Themes",
			"bisacrt", "BISAC Regional Themes",
			"bisacsh", "BISAC Subject Headings",
			"cosatisc", "COSATI subject category list (Washington: Federal Council for Science and Technology, Committee on Scientific and Technical Information)",
			"dissao", "\"Dissertation abstracts online\" in Search tools: the guide to UNI/Data Courier Online (Louisville, KY: UNI/Data Courier Online)",
			"dit", "Defense intelligence thesaurus (Washington, DC: Defense Intelligence Agency)",
			"dtict", "Defense Technical Information Center thesaurus (Fort Belvoir, VA: DTIC)",
			"edbsc", "Energy data base: subject categories and scope (Oak Ridge, Tenn.: Dept. of Energy, Office of Scientific and Technical Information)",
			"eflch", "E4Libraries Category Headings",
			"ekz", "Systematiken der ekz",
			"electre", "Electre [online database] (Paris: Editions du Cercle de la librarie)",
			"fiaf", "Moulds, Michael. Classification scheme for literature on film and television (London: International Federation of Film Archives)",
			"francis", "Base de donnés FRANCIS: plan de classement = FRANCIS database classification scheme (Vandoeuvre: Centre national de la recherche scientifique)",
			"georeft", "GeoRef thesaurus (Alexandria, VA: American Geological Institute)",
			"hornsach", "\"Hornbostel-Sachs classification\" in Galpin Society journal, no. 14, March 1961 (Leamington, Eng.: Galpin Society)",
			"hraf", "\"Human Relations Area Files classification\" in Outline of world cultures, 6th rev. ed. (New Haven, Conn.: Human Relations Area Files)",
			"idas", "ID-Archivschlüssel [Informationsdienst zur Verbreitung unterbliebener Nachrichten] (Amsterdam: Internationales Institut für Sozialgeschichte)",
			"iescs", "International energy subject categories and scope (Paris: International Energy Agency)",
			"ilot", "ILO thesaurus: labour, employment and training terminology = Thésaurus BIT: terminologie du travail, de l'emploi et de la formation = Tesauro OIT: terminología de trabajo, empleo y formación (Geneva: International Labour Office)",
			"inissc", "INIS: subject categories and scope descriptions (Vienna: IAEA)",
			"inspec", "INSPEC classification (London: Institution of Electrical Engineers)",
			"inspect", "INSPEC thesaurus (London: Institution of Electrical Engineers)",
			"ipsp", "Defense intelligence production schedule (Washington, DC: Defense Intelligence Agency)",
			"kdm", "Khung dê muc hê thông thông tin khoa hoc và ky thuât quôc gia (Hà Nôi: Viên Thông Tin Khoa Hoc Và Ky Thuât Trung Uong)",
			"kfsb", "Klassifikationssystem för svenska bibliotek (Stockholm: Bibliotekstjänst)",
			"kkaa", "Kokoelmien kuvailun aihealueet (Kokoelmakartta) [PDF: 9 KB]",
			"lacc", "Library and Archives Canada collection codes (Ottawa : Library and Archives Canada)",
			"lcco", "LC classification outline (Washington, DC: Library of Congress, Cataloging Distribution Service)",
			"lcg", "Table IV, \"Table of subject subdivisions\" in Library of Congress classification schedule: Class G (Washington, DC: LC)",
			"lcmd", "Library of Congress, Manuscript Division field of history codes (Washington, DC: LC)",
			"lcsh", "Library of Congress subject headings (Washington, DC: LC, Cataloging Distribution Service)",
			"mesh", "Medical subject headings (Bethesda, Md.: National Library of Medicine)",
			"mmm", "\"Subject key\" in Marxism and the mass media (New York: International General)",
			"nasasscg", "NASA scope and subject category guide (Hanover, MD: NASA, Scientific and Technical Information Program)",
			"nimacsc", "NIMA cartographic subject categories (Bethesda, MD: National Imagery and Mapping Agency)",
			"nsnr", "Nomenklatura spetsial'nostei nauchnykh rabotnikov (Moskva: Vysshaíà attestatsionnaíà komissiíà Ministerstva obrazovaniíà Rossiiskoi Federatsii)",
			"ntcpsc", "\"National Translations Center primary subject classification\" in National Translations Center primary subject classification and secondary descriptors (Washington, DC: National Translations Center, Cataloging Distribution Service, Library of Congress)",
			"ntissc", "NTIS subject categories (Springfield, VA: National Technical Information Service)",
			"pascal", "Base de données PASCAL: plan de classement = PASCAL database classification scheme (Vandoeuvre: Centre national de la recherche scientifique)",
			"quiding", "Quiding, Nils Herman. Svenskt allmänt författningsregister för tiden från år 1522 till och med år 1862 (Stockholm: Norstedt)",
			"rero", "RERO code sujets (Martigny: Réseau des bibliothèques de Suisse occidentale)",
			"sao", "Svenska ämnesord (Stockholm: Kungliga Biblioteket, LIBRIS-avdelningen)",
			"scgdst", "Subject categorization guide for defense science and technology (Alexandria, VA: Defense Technical Information Center, Defense Logistics Agency)",
			"sddoeur", "Standard distribution for unclassified scientific and technical reports arranged by category number (Oak Ridge, TN: Dept. of Energy, Office of Scientific and Technical Info.)",
			"sicm", "Standard industrial classification manual (Springfield, VA: National Technical Information Service)",
			"sigle", "SIGLE [System for Information on Grey Literature in Europe] manual, Part 2, Subject category list (Den Haag: European Association for Grey Literature Exploitation)",
			"skon", "Att indexera skönlitteratur: Ämnesordslista, vuxenlitteratur (Stockholm: Svensk biblioteksförening)",
			"thema", "Thema (Editeur.org)",
			"ukslc", "UK Standard Library Categories (London: BIC)",
			"wsb", "Warengruppen-Systematik des Buchhandels (Frankfurt am Main: Buchhändler-Vereinigung GmbH)"
		);
		indexCodes();
	}

	private static SubjectCategoryCodeSourceCodes uniqueInstance;

	private SubjectCategoryCodeSourceCodes() {}

	public static SubjectCategoryCodeSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new SubjectCategoryCodeSourceCodes();
		return uniqueInstance;
	}
}
