package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

public class DescriptionConventionSourceCodes extends CodeList {

	private void initialize() {
		codes = Utils.generateCodes(
				"aacr", "Anglo-American cataloguing rules (Chicago: American Library Association)",
				"amim", "Archival moving image materials (Washington: Library of Congress)",
				"amremm", "Pass, Gregory A. Descriptive cataloging of ancient, medieval, renaissance, and early-modern manuscripts (Chicago: Association of College & Research Libraries)",
				"appm", "Hensen, Steven L. Archives, personal papers, and manuscripts (Washington: Library of Congress)",
				"bdrb", "Bibliographic description of rare books (Washington: Library of Congress, Cataloging Distribution Service)",
				"bps", "McCrum, Blanche Prichard. Bibliographical procedures and style (Washington: Library of Congress, General Reference and Bibliography Division)",
				"cco", "Cataloging cultural objects: a guide to describing cultural works and their images (Chicago: Visual Resources Association, American Library Association)",
				"ccr", "Chinese cataloging rules (Taiwan: National Central Library)",
				"cgcrb", "Cataloging Guidelines for Creating Chinese Rare Book Records in Machine-Readable form = 中文善本書機讀目錄編目規則 (Research Library Group)",
				"crlp", "Hallam, Adele. Cataloging rules for the description of looseleaf publications: with special emphasis on legal materials (Washington: Library of Congress)",
				"dacs", "Describing archives: a content standard (Chicago: Society of American Archivists)",
				"dcgpm", "Descriptive cataloging guidelines for pre-Meiji Japanese books (Subcommittee on Japanese Rare Books, Committee on Japanese Materials, Council on East Asian Libraries)",
				"dcrb", "Descriptive cataloging of rare books (Washington, DC: Cataloging Distribution Service, Library of Congress)",
				"dcrmb", "Descriptive cataloging of rare materials (Books). (Washington, DC: Cataloging Distribution Service, Library of Congress)",
				"dcrmc", "Descriptive cataloging of rare materials (Cartographic) (Chicago: Rare Books and Manuscripts Section of the Association of College and Research Libraries)",
				"dcrmg", "DCRM(G): Descriptive cataloging of rare materials (Graphics) (Rare Books and Manuscripts Section, Association of College & Research Libraries)",
				"dcrmm", "DCRM(M): Descriptive cataloging of rare materials (Music) (Rare Books and Manuscripts Section of the Association of College and Research Libraries)",
				"dcrmmss", "DCRM(MSS): Descriptive cataloging of rare materials (Manuscripts) (Chicago: Rare Books and Manuscripts Section of the Association of College and Research Libraries)",
				"dcrms", "Descriptive cataloging of rare materials (Serials) (Washington, DC: Cataloging Distribution Service, Library of Congress)",
				"din1505", "Titelangaben von Dokumenten (Berlin: Beuth)",
				"dmbsb", "Dokumentation av materialets behandling i SB 1700-1829 (Stockholm: Kungl. Biblioteket)",
				"enol", "Ekspertiza i nauchno-tekhnicheskaia obrabotka lichnykh arkhivnykh fondov: metodicheskiie rekomendatsii (Moskva: Gosudarstvennaia biblioteka SSSR im. V. I. Lenin)",
				"estc", "Eighteenth century short title catalogue, the cataloguing rules (London: The British Library)",
				"fobidrtb", "Federatie van Organisaties op het Gebied van het Bibliotheek-Informatie-en Dokumentatiewezen (FOBID) Regels voor de titelbeschrijving (Den Haag: Nederlands Bibliotheek en Lektuur Centrum)",
				"gihc", "Betz, Elisabeth W. Graphic materials (Washington: Library of Congress)",
				"hmstcn", "Handleiding voor de medewerkers aan de STCN ('s Gravenhage: Koninklijke Bibliotheek) [Short title catalog of the Netherlands]",
				"iosr", "“Instruktsiia po opisaniiu slaviano-russkikh rukopisei XI-XIV vv. dlia Svodnogo kataloga rukopisei, khraniashchikhsia v SSSR” in Arkheologicheskii ezhegodnik za 1975 god. (Moskva: Izd-vo Akademii)",
				"isbd", "ISBD: International Standard Bibliographic Description (ISBD Review Group/Standing Committee of the IFLA Cataloguing Section)",
				"katreg", "Katalogiseringsregler: Anglo-American cataloguing rules, second edition /oversatt og bearbiedet for Norske forhold ved Inger Cathrine Spangen (Oslo: Nasjonalbiblioteket)",
				"kbsdb", "Katalogiseringsregler og bibliografisk standard for danske biblioteker (Ballerup: Dansk BiblioteksCenter)",
				"kids", "Katalogisierungsregeln Informationsverbund Deutschschweiz (Zürich: Informationsverbund Deutschschweiz)",
				"krsb", "Katalogiseringsregler for svenska bibliotek (Lund: Bibliotekstjanst)",
				"lcmpt", "Library of Congress medium of performance thesaurus for music (LCMPT) (Washington, DC: Library of Congress)",
				"local", "Locally defined or unknown cataloging rules",
				"mmlcc", "Manual of map library classification and cataloguing (London: Ministry of Defence)",
				"ncafnor", "Normes de catalogage publiées par l'Association française de normalisation (Paris: AFNOR)",
				"ncr", "Nippon cataloging rules (Tokyo: National Diet Library)",
				"ncs", "Norme per il catalogo degli stampati (Città del Vaticano: Biblioteca apostolica vaticana)",
				"ohcm", "Matters, Marion E. Oral history cataloging manual (Chicago: Society of American Archivists)",
				"pi", "Instruktionen für die alphabetischen Kataloge der preussischen Bibliotheken (Wiesbaden: Otto Harrasowitz)",
				"pn", "Provider-Neutral E-Resource MARC Record Guidelines (Library of Congress, Program for Cooperative Cataloging)",
				"ppiak", "Verona, Eva. Pravilnik i prirucnik za izradbu abecednih kataloga (Zagreb: Hrvatsko bibliotekarsko drustvo)",
				"psbo", "Pravila sostavleníìa bibliograficheskogo opisaníìa (Moskva: Kniga)",
				"rad", "Rules for archival description (Ottawa: Bureau of Canadian Archivists)",
				"rak", "Regeln für die alphabetische Katalogisierung (Wiesbaden: Reichert)",
				"rakddb", "Ansetzungsform gemaess der RAK - Anwendung Der Deutschen Bibliothek",
				"rakwb", "Regeln für die alphabetische Katalogisierung an wissenschaftlichen Bibliotheken (Berlin: Deutsches Bibliotheksinstitut)",
				"-rcaa2", "Règles de catalogage anglo-américaines. 2e édition (Montréal: ASTED) [Prior to the implementation of a technique for identifying editions and translations of description convention sources, this code was used for the French edition of AACR2]",
				"rda", "Resource Description and Access (Chicago, IL: American Library Association)",
				"rdc", "Reglas de catalogación (Madrid: Dirección General del Libro, Archivos y Bibliotecas)",
				"rica", "Regole italiane di catalogazione per autori (Roma: Istituto central per il catalogo unico delle biblioteche italiane e per le informazioni bibliografiche)",
				"rna", "Regeln zur Erschließung von Nachlässen und Autographen (RNA)",
				"rpk", "Rossiiskiie pravila katalogizatsii (Moskva: Rossiiskaíà Gosudarstvennaíà Biblioteka)",
				"sscde", "SCIS Standards for Cataloguing and Data Entry (Education Services Australia Ltd.)",
				"vd16", "Formalerschliessung nach dem Verzeichnis der Drucke des 16. Jahrhunderts (VD 16)",
				"vd17", "Formalerschliessung nach dem Verzeichnis der Drucke des 17. Jahrhunderts (VD 17)",
				"-yuppiak", "USE ppiak"
		);
		indexCodes();
	}

	private static DescriptionConventionSourceCodes uniqueInstance;

	private DescriptionConventionSourceCodes() {
		initialize();
	}

	public static DescriptionConventionSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new DescriptionConventionSourceCodes();
		return uniqueInstance;
	}

}
