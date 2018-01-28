package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Genre/Form Code and Term Source Codes.
 * http://www.loc.gov/standards/sourcelist/genre-form.html
 */
public class GenreFormCodeAndTermSourceCodes extends CodeList {

	private void initialize() {
		name = "Genre/Form Code and Term Source Codes";
		url = "http://www.loc.gov/standards/sourcelist/genre-form.html";
		codes = Utils.generateCodes(
			"alett", "An alphabetical list of English text types (Berlin; New York: Mouton de Gruyter)",
			"amg", "Audiovisual material glossary (Dublin, OH: Online Computer Library Center, Inc.)",
			"barngf", "Svenska ämnesord för barn - Genre/Form (Stockholm: Kungliga Biblioteket)",
			"bgtchm", "Basic genre terms for cultural heritage materials External Link",
			"cbk", "Centraal Bestand Kinderboeken",
			"cjh", "Center for Jewish History thesaurus External Link",
			"dct", "Dublin Core list of resource types External Link",
			"estc", "Eighteenth century short title catalogue, the cataloguing rules. New ed. (London: The British Library)",
			"fbg", "Films by genre (Jefferson, NC: McFarland)",
			"fgtpcm", "Form/genre terms for printed cartoon material (Bowling Green, OH: Consortium of Popular Culture Collections in the Midwest)",
			"ftamc", "Form terms for archival and manuscripts control (Stanford: The Research Libraries Group, Inc.)",
			"gatbeg", "Gattungsbegriffe (Leipzig & Frankfort: Deutsche Nationalbibliothek)",
			"gmd", "Anglo-American Cataloguing Rules general material designation (Rule 1.1C)",
			"gmgpc", "Thesaurus for graphic materials: TGM II, Genre and physical characteristic terms (Washington, DC: Library of Congress, Cataloging Distribution Service)",
			"gnd", "Gemeinsame Normdatei (Leipzig, Frankfurt: Deutsche Nationalbibliothek) External Link",
			"gnd-carrier", "Gemeinsame Normdatei: Datenträgertyp (Leipzig, Frankfurt: Deutsche Nationalbibliothek)",
			"gnd-content", "Gemeinsame Normdatei: Beschreibung des Inhalts (Leipzig, Frankfurt: Deutsche Nationalbibliothek)",
			"gnd-music", "Gemeinsame Normdatei: Musikalische Ausgabeform (Leipzig, Frankfurt: Deutsche Nationalbibliothek)",
			"gsafd", "Guidelines on subject access to individual works of fiction, drama, etc. (Chicago: American Library Association)",
			"gtlm", "Genre terms for law materials: a thesaurus (Littleton, CO: Rothman Pubs.)",
			"isbdcontent", "ISBD Area 0 (Content Form and Media Area) [content]",
			"isbdmedia", "ISBD Area 0 (Content Form and Media Area) [media]",
			"lcgft", "Library of Congress genre/form terms for library and archival materials (Washington, DC: Library of Congress, Cataloging Distribution Service)",
			"lobt", "Language of Bindings Thesaurus (London: University of Arts)",
			"marccategory", "MARC form category term list (Washington, DC: Library of Congress)",
			"marcform", "MARC form of item term list (Washington, DC: Library of Congress)",
			"marcgt", "MARC genre terms (Washington, DC: Library of Congress)",
			"marcsmd", "MARC specific material form term list (Washington, DC: Library of Congress)",
			"migfg", "Moving image genre-form guide (Washington, DC: Library of Congress. MBRS)",
			"mim", "Moving image materials: genre terms (Washington: Motion Picture Broadcasting and Recorded Sound Division, Library of Congress)",
			"muzeukv", "MuzeVideo UK DVD and UMD film genre classification (London: Muze Europe Ltd)",
			"nbdbgf", "NBD Biblion Genres Fictie (Zoetermeer: NBD Biblion) External Link",
			"ngl", "Newspaper genre list [online only] (Seattle, WA: University of Washington, Library Microform and Newspaper Collections)",
			"nimafc", "NIMA form codes (Bethesda, MD: National Imagery and Mapping Agency)",
			"nmc", "Revised nomenclature for museum cataloging: a revised and expanded version of Robert C. Chenhall's system for classifying man-made objects (Walnut Creek, Calif.: AltaMira Press)",
			"nzcoh", "New Zealand Choral and Orchestral hire form and genre terms",
			"proysen", "Prøysen: emneord for Prøysen-bibliografien (Oslo: Nasjonalbiblioteket)",
			"radfg", "Radio form / genre terms guide (Washington, DC: Library of Congress, Motion Picture, Broadcasting and Recorded Sound Division, Recorded Sound Section)",
			"rbbin", "Binding terms: a thesaurus for use in rare book and special collections cataloguing (Chicago: Association of College and Research Libraries, ALA)",
			"rbgenr", "Genre terms: a thesaurus for use in rare book and special collections cataloguing (Chicago: Association of College and Research Libraries)",
			"rbmscv", "RBMS Controlled Vocabularies (Chicago: Rare Books and Manuscripts Section, Association of College and Research Libraries)",
			"rbpap", "Paper terms: a thesaurus for use in rare book and special collections cataloging (Chicago: Association of College and Research Libraries, ALA) [used for printing terms.]",
			"rbpri", "Printing & publishing evidence: a thesaurus for use in rare book and special collections cataloging (Chicago: Association of College and Research Libraries, ALA) [used for printing terms.]",
			"rbprov", "Provenance evidence: a thesaurus for use in rare book and special collections cataloging (Chicago: Association of College and Research Libraries, ALA)",
			"rbpub", "Printing and publishing evidence: a thesaurus for use in rare book and special collections cataloging (Chicago: Association of College and Research Libraries, ALA) [used for publishing terms.]",
			"rbtyp", "Type evidence: a thesaurus for use in rare book and special collections cataloging (Chicago: Association of College and Research Libraries, ALA) [used for printing terms.]",
			"rdabf", "RDA bibliographic format",
			"rdabs", "RDA broadcast standard",
			"rdacarrier", "Term and code list for RDA carrier types",
			"rdaco", "RDA content type",
			"rdacontent", "Term and code list for RDA content types",
			"rdacpc", "RDA configuration of playback channels",
			"rdact", "RDA carrier type",
			"rdare", "RDA regional encoding",
			"rdafnm", "RDA format of notated music",
			"rdafs", "RDA font size",
			"rdaft", "RDA file type",
			"rdagen", "RDA generation",
			"rdagrp", "RDA groove pitch of an analog cylinder",
			"rdagw", "RDA groove width of an analog disc",
			"rdalay", "RDA layout",
			"rdamat", "RDA material",
			"rdamedia", "Term and code list for RDA media types",
			"rdamt", "RDA media type",
			"rdapf", "RDA presentation format",
			"rdapm", "RDA production method",
			"rdapo", "RDA polarity",
			"rdarm", "RDA recording medium",
			"rdarr", "RDA reduction ratio designation",
			"rdaspc", "RDA special playback characteristic",
			"rdatc", "RDA track configuration",
			"rdatr", "RDA type of recording",
			"rdavf", "RDA video format",
			"reveal", "REVEAL: fiction indexing and genre headings (Bath: UKOLN)",
			"rvmgf", "Thésaurus des descripteurs de genre/forme de l'Université Laval (Québec, Québec: Bibliothèque de l'Université Laval)",
			"saogf", "Svenska ämnesord - Genre/Form (Stockholm: Kungliga Biblioteket)",
			"sgp", "Svenska genrebeteckningar fr periodika (Stockholm: Kunglige Biblioteket)",
			"slm", "Suomalainen lajityyppi- ja muotosanasto",
			"tgfbne", "Términos de género/forma de la Biblioteca Nacional de España",
			"vgmsgg", "Video Game Metadata Schema: Controlled vocabulary for gameplay genre (GAME Research (GAMER) Group)",
			"vgmsng", "Video Game Metadata Schema: Controlled vocabulary for narrative genre (GAME Research (GAMER) Group)"
		);
		indexCodes();
	}

	private static GenreFormCodeAndTermSourceCodes uniqueInstance;

	private GenreFormCodeAndTermSourceCodes() {
		initialize();
	}

	public static GenreFormCodeAndTermSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new GenreFormCodeAndTermSourceCodes();
		return uniqueInstance;
	}
}
