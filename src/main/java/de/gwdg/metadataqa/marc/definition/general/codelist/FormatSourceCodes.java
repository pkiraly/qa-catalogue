package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Format Source Codes
 * http://www.loc.gov/standards/sourcelist/format.html
 */
public class FormatSourceCodes extends CodeList {

	private void initialize() {
		name = "Format Source Codes";
		url = "http://www.loc.gov/standards/sourcelist/format.html";
		codes = Utils.generateCodes(
			"annamarc", "ANNAMARC: specifiche relative ai nastri magnetici contententi i record della Biblioteca Nazionale Italiana nel formato ANNAMARC ([Roma]: Istituto Centrale per il Catalogo Unico delle Biblioteche Italiane e per le Informazioni Bibliografiche)",
			"ausmarc", "AUSMARC bibliographic format (Canberra: National Library of Australia",
			"bibsysm", "BIBSYS-MARC (Trondheim: BIBSYS)",
			"calco", "Formato CALCO: monografias e publicaçöes seriadas (Brasília: Ministério da Educaçäo e Cultura)",
			"canmarc", "Canadian MARC communication format: bibliographic data (Ottawa: Canadian MARC Office)",
			"canmarca", "Canadian MARC communication format: authorities (Ottawa: Canadian MARC Office)",
			"catmarc", "Manual del CATMARC (Barcelona: Institut Català de Bibliografia",
			"ccf", "CCF: The common communication format (Paris: Unesco)",
			"ccmarc", "Zhong guo ji du mu lu ke shi shi yong shou ze = China MARC format manual (Bei jing: Hua yi chu ban she)",
			"chmarc", "Chinese MARC format (Taipei: National Central Library)",
			"danmarc", "DanMARC: edb-format til lagring og udveksling af bibliografiske data i maskinlæsbar form (Ballerup: Bibliotekscentralens Forlag)",
			"dc", "Dublin core (Dublin, OH: OCLC Research)",
			"finmarc", "FINMARC-yhtenäisformaatin sovellusohje (Helsinki: Helsingin yliopiston kirjasto, Tieteellisten kirjastojen atk-uksikkö) External Link",
			"hunmarc", "HUNMARC: a bibliográfiai rekordok adatcsere formátuma (Budapest: Országos Széchényi Könyvtár) External Link",
			"ibermarc", "Formato IBERMARC para registros bibliográficos (Madrid: Ministerio de Cultura, Biblioteca Nacional) External Link",
			"indimarc", "Indian MARC (Calcutta: Bureau of Indian Standards)",
			"indomarc", "Format MARC Indonesia (INDOMARC) untuk buku (Jakarta: Perpustakaan Nasional R.I.)",
			"intermrc", "INTERMARC (M): format bibliographique d'échange pour les monographies: manuel (Paris: Groupe INTERMARC)",
			"jpnmarc", "Japan/MARC manyuaru = Japan/MARC manual (Tokyo: Kokuritsu Kokkai Toshokan)",
			"kormarc", "Han`guk munhon chadonghwa mongnokpop (Hancamok) = Korean machine readable cataloging (KOR MARC) (Seoul: Kungnip Chungang Tosogwan)",
			"librism", "LibrisMARC (Stockholm: Kunglige biblioteket)",
			"local", "Locally defined format", "See the explanation in the Introduction.",
			"mab", "Maschinelles Austauchformat für Bibliotheken (Frankfurt a.M.: Deutsche Bibliothek, Zentrale Bibliographische Dienstleistungen)",
			"mads", "Metadata Authority Description Schema (Washington DC: Library of Congress)",
			"malmarc", "MALMARC (Penang: Universiti Sains Malaysia)",
			"marc", "MARC 21 format for bibliographic data (Washington, DC: Library of Congress)", "Used when needing to indicate a specific edition or translation",
			"marca", "MARC 21 format for authority data (Washington, DC: Library of Congress)",
			"marcal", "Manual abreviado para codificación en formato MARCAL (libros) (México: Consejo Nacional de Ciencia y Tecnología)",
			"marcc", "MARC 21 format for classification data (Washington, DC: Library of Congress)",
			"march", "MARC 21 format for holdings data (Washington, DC: Library of Congress)",
			"marci", "MARC 21 format for community information (Washington, DC: Library of Congress)",
			"marcsui", "Manuel USMARC: version suisse ([Berne]: Bibliothèque suisse)",
			"marcxml", "MARC 21 XML schema (Washington, DC: Library of Congress)",
			"mekof", "Kommunikativnyi format dlíà obmena bibliograficheskimi dannymi na magnetnoi lente (Moskva: Izdatel'stvo Standartov)",
			"mods", "Metadata object description schema (Washington, DC: Library of Congress)",
			"normarc", "NORMARC: veiledning i MARC-katalogisering og kort oversikt over arbeidsrutiner (Oslo: Universitetsbiblioteket i Oslo)",
			"onix", "ONIX (Online Information Exchange) External Link",
			"pica", "PICA+ format (s'Gravenhage: Pica-Bureau)",
			"pulmarc", "Zhong wen ji du bian mu ke shi = PUL MARC format (Bei jing: Bei jing da xue tu shu guan zi dong hua yan jiu shi)",
			"rusmarc", "Predmashinnyi format bibliograficheskoi zapisi (Moskva: Rossiskaíà Gosudarstvennaíà publichnaíà nauchno-tekhnicheskaíà biblioteka)",
			"samarc", "SAMARC: South African national format for the exchange of machine-readable bibliographic descriptions (Pretoria: National Library Advisory Council)",
			"sbn", "SBN (National Library Service, Italian Libraries Network)",
			"swemarc", "SweMARC - bibliografiska formatet (Stockholm: Kunglige biblioteket)",
			"swemarca", "SweMARC - auktoritetsformatet (Stockholm: Kunglige biblioteket)",
			"swemarck", "SweMARC - klassifikationsformatet (Stockholm: Kunglige biblioteket)",
			"swemarcs", "SweMARC - beståndsformatet (Stockholm: Kunglige biblioteket)",
			"trcmarc", "TRC MARC jinmei tenkyoroko (Tôkyô: Toshokan Ryûtsû Sentâ)",
			"ukmarc", "UK MARC manual (London: British Library, Bibliographic Services Division External Link",
			"unimarc", "UNIMARC manual (London: IFLA UBCIM Programme)",
			"unimarca", "UNIMARC/authorities: universal format for authorities (Munich: Saur)",
			"unimci", "Prirucnik za UNIMARC (Zagreb: Nacionalna i sveucilisna biblioteka)",
			"unimru", "Rukovodstvo po UNIMARC = UNIMARC Manual (Moskva: Gosudarstvennaíà publichnaíà nauchno-tekhnicheskaíà biblioteka Rossiíà)",
			"unimrua", "UNIMARC authorities: mezhdunarodnyi kommunikativnyi format UNIMARC dlíà Gosudarstvennaíà publichnaíà nauchno-tekhnicheskaíà biblioteka Rossiíà)"
		);
		indexCodes();
	}

	private static FormatSourceCodes uniqueInstance;

	private FormatSourceCodes() {
		initialize();
	}

	public static FormatSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new FormatSourceCodes();
		return uniqueInstance;
	}
}