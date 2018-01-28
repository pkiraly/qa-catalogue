package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * MARC Authentication Action Code List
 * http://www.loc.gov/standards/valuelist/marcauthen.html
 */
public class AuthenticationActionCodes extends CodeList {

	private void initialize() {
		name = "MARC Authentication Action Code List";
		url = "http://www.loc.gov/standards/valuelist/marcauthen.html";
		codes = Utils.generateCodes(
			"anuc", "Australian National Union Catalog",
			"croatica", "Croatian National Bibliography",
			"dc", "Dublin Core",
			"dhca", "Dance Heritage Coalition Access Project",
			"dlr", "Digital library registry",
			"gamma", "Georgia Archives & Manuscripts Automated Access Project",
			"gils", "Government Information Location Service",
			"gnd1", "GND authenticated by curation team of a library network",
			"gnd2", "GND authenticated by a local curation team",
			"gnd3", "GND authenticated by trained users",
			"gnd4", "GND authenticated by untrained users",
			"gnd5", "GND authenticated by other non-librarian users",
			"gnd6", "GND legacy data, not authenticated",
			"gnd7", "GND automatically generated record, not authenticated",
			"isds/c", "ISSN Canada",
			"issnuk", "ISSN UK Centre",
			"lacderived", "Library and Archives Canada derived cataloging",
			"lc", "Library of Congress",
			"lcac", "Library of Congress Children's and Young Adults' Cataloging Program",
			"lccopycat", "LC Copy Cataloging",
			"lccopycat-nm", "LC Copy Cataloging-Near Match",
			"lcd", "CONSER full authority application",
			"lcderive", "LC derived cataloging",
			"lchlas", "LC Handbook of Latin American Studies",
			"lcllh", "LC Law Library Hispanic",
			"lcnccp", "LC National Coordinated Cataloging Program",
			"lcnitrate", "LC Nitrate Film",
			"lcnuc", "National Union Catalog",
			"lcode", "LC Overseas Data Entry",
			"msc", "CONSER minimal authority application",
			"natgaz", "U.S. National Gazetteer Geographic Feature Name",
			"nbr", "National Bibliography Resource",
			"nlc", "Library and Archives Canada",
			"nlmcopyc", "National Library of Medicine Copy Cataloging",
			"norbibl", "National Library of Norway (Nasjonalbiblioteket)",
			"nsdp", "National Serials Data Program",
			"nst", "New Serial Titles",
			"ntccf", "LC National Translations Center Citation File",
			"nznb", "New Zealand National Bibliography",
			"pcc", "Program for Cooperative Cataloging",
			"premarc", "LC PreMARC Retrospective Conversion Project",
			"reveal", "REVEAL Union Catalog Project",
			"sanb", "South African National Bibliography Project",
			"scipio", "SCIPIO: Art and Rare Book Sales Catalogs",
			"toknb", "Tokelau National Bibliography.",
			"ukblcatcopy", "British Library copy cataloging",
			"ukblderived", "British Library derived cataloging",
			"ukblproject", "British Library project",
			"ukblsr", "British Library Standard Record",
			"ukscp", "UK Legal Deposit Libraries' Shared Cataloguing Programme Record",
			"xissnuk", "Unverified by ISSN UK Centre",
			"xlc", "LC does not consider item a serial",
			"xnlc", "NLC does not consider item a serial",
			"xnsdp", "NSDP does not consider item a serial"
		);
		indexCodes();
	}

	private static AuthenticationActionCodes uniqueInstance;

	private AuthenticationActionCodes() {
		initialize();
	}

	public static AuthenticationActionCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new AuthenticationActionCodes();
		return uniqueInstance;
	}
}