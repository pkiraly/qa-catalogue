package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * National Bibliography Number Source Codes
 * http://www.loc.gov/standards/sourcelist/national-bibliography.html
 */
public class NationalBibliographyNumberSourceCodes extends CodeList {

	private void initialize() {
		codes = Utils.generateCodes(
			"abc", "Anuario bibliográfico colombiano (Calí: Biblioteca Jorge García B.)",
			"abd", "Anuario bibliográfico dominicana (Ciudad Trujillo: Biblioteca Nacional)",
			"abe", "Anuario bibliográfico ecuatoriano (Cuenca: Banco Central del Ecuador",
			"abp", "Anuario bibliográfico peruano (Lima: Editorial Lumen)",
			"abu", "Anuario bibliográfico uruguayo (Montevideo: Biblioteca Nacional",
			"anb", "Australian national bibliography (Canberra: National Library of Australia)",
			"ba", "Bibliografía d'Andorra (Andorra La Vella: Biblioteca Nacional)",
			"bbe", "Bibliographie de Belgique (Bruxelles: H. Manceaux)",
			"bbo", "Bibliografía boliviana (Cochabamba: Amigos del Libro)",
			"bbr", "Bibliografia brasileira (Rio de Janeiro: Biblioteca Nacional)",
			"bc", "Bibliografía cubana (Habana: Biblioteca Nacional José Martá)",
			"bccb", "Brinkman's cumulatieve catalogus van boeken (Leiden: Sijthoff)",
			"bcl", "Bibliografía chilena (Santiago: Biblioteca Nacional de Chile)",
			"be", "Bibliografía española (Madrid: Servicio Nacional de Información Bibliográfica)",
			"bk", "Bulgarski knigopis (Sofiíà: Narodna biblioteka Kiril i Metodii)",
			"bkck", "Bibliograficky katalog: Ceské knihy (Praha: Národní knihovna)",
			"bksk", "Bibliograficky katalog: Slovenské knihy (Brastislava: Národní knihovna",
			"bl", "Bibliographie luxembourgeoise pour l'année (Luxembourg: Bibliothèque nationale)",
			"bm", "Bibliografía mexicana (Ciudad de México: Biblioteca Nacional)",
			"bnb", "British national bibliography (London: British Library, Bibliographic Services Division)",
			"bnc", "Bibliografia nacional de Catalunya (Barcelona: Departement de Cultura de la Generalitat de Catalunya)",
			"bne", "Bibliografía nacional de España (Madrid: Biblioteca Nacional de España)",
			"bnf", "Bibliographie nationale française (Paris: Bibliothèque nationale de France)",
			"bni", "Bibliografia nazionale italiana (Roma: Istituto centrale per il catalogo unico delle biblioteche italiane e per le informazioni)",
			"bnm", "Bibljografija nazzjonal ta' Malta = National bibliography of Malta (Valletta: National Library of Malta)",
			"bnr", "Bibliografia nationala româna (Bucuresti: Biblioteca Nationale a României)",
			"bv", "Bibliografía venezolana (Caracas: Instituto Autónomo Biblioteca Nacional y Servicios de Bibliotecas)",
			"can", "Canadiana (Ottawa: National Library of Canada)",
			"cncr", "Catálogo nacional de Costa Rica (San José: Biblioteca Nacional)",
			"db", "Deutsche Bibliographie (Frankfurt am Main: Die Deutsche Bibliothek)",
			"dbf", "Dansk bogfortegnelse (Ballerup: Dansk Bibliotekscenter)",
			"dnb", "Deutsche Nationalbibliografie External Link",
			"eev", "Ellenikê ethnikê vivliografia (Athenai: Ethnikê Vivliothêkê tês Ellados)",
			"hb", "Hrvatska bibliografija (Zagreb: Nacionalna i sveucilisna biblioteka)",
			"ib", "Islensk bókaskrá (Reykjevík: Landbókasafn Islands - Háskólabókasafn)",
			"ipr", "Irish publishing record (Dublin: University College of Dublin)",
			"jnb", "Japanese National Bibliography (Tokyo: National Diet Library)",
			"kktzm", "Kokuritsu kokkai Toshokan zosho mokuroku (Tokyo: National Diet Library)",
			"kl", "Knizhnaíà letopis' (Moskva: Rossiiskaíà knizhnaíà palata)",
			"la", "Libros argentinos (Buenos Aires: Cámara Argentina del Libro)",
			"lb", "Liechtensteinische Bibliographie (Vaduz: Liechtensteinische Landesbibliothek)",
			"ldb", "Letapis Druku Belarusi = Chronicle of the Press Belarus (Minsk: Natsyianal'naia kniznaia palata Belarusi)",
			"mnb", "Magyar nemzeti bibliográfia (Budapest: Országos Széchényi Könyvtár)",
			"nbf", "Norsk bokfortegnelse (Oslo: Norske bokhandlerforening)",
			"nznb", "New Zealand national bibliography (Wellington: National Library of New Zealand)",
			"oeb", "Oesterreichische Bibliographie (Wien: Oesterreichische Nationalbibliothek)",
			"pb", "Przewodnik bibliograficzny (Warszawa: Biblioteka Narodowa)",
			"sanb", "South African national bibliography (Pretoria: National Library of South Africa)",
			"sbf", "Svensk bokförteckning (Stockholm: Svenska bokhandlareforeningen)",
			"skl", "Suomen kirjallisuus (Helsinki: Helsingin Yliopiston Kirjasto)",
			"slb", "Slovenska bibliografija (Ljubljana: Narodna in univerzitetna knjiznica)",
			"szb", "Das Schweizer Buch: Schweizerische Nationalbibliographie (Zürich: Schweizerischer Buchhändler- und Verlegerverband)",
			"tnb", "Taiwan National Bibliography (Taipei: National Central Library)",
			"ulk", "Ukraïna: litopys knih (Kyïv: Knyzhkova palata Ukraïny)",
			"znb", "Zimbabwe national bibliography (Harare: National Archives"
		);
		indexCodes();
	}

	private static NationalBibliographyNumberSourceCodes uniqueInstance;

	private NationalBibliographyNumberSourceCodes() {
		initialize();
	}

	public static NationalBibliographyNumberSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new NationalBibliographyNumberSourceCodes();
		return uniqueInstance;
	}
}