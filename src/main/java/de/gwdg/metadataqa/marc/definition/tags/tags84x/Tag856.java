package de.gwdg.metadataqa.marc.definition.tags.tags84x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.ElectronicAccessMethodsCodeList;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

/**
 * Electronic Location and Access
 * http://www.loc.gov/marc/bibliographic/bd856.html
 */
public class Tag856 extends DataFieldDefinition {

	private static Tag856 uniqueInstance;

	private Tag856() {
		initialize();
		postCreation();
	}

	public static Tag856 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag856();
		return uniqueInstance;
	}

	private void initialize() {
		tag = "856";
		label = "Electronic Location and Access";
		mqTag = "ElectronicLocationAndAccess";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd856.html";

		ind1 = new Indicator("Access method")
			.setCodes(
				" ", "No information provided",
				"0", "Email",
				"1", "FTP",
				"2", "Remote login (Telnet)",
				"3", "Dial-up",
				"4", "HTTP",
				"7", "Method specified in subfield $2"
			)
			.setMqTag("accessMethod");
		ind2 = new Indicator("Relationship")
			.setCodes(
				" ", "No information provided",
				"0", "Resource",
				"1", "Version of resource",
				"2", "Related resource",
				"8", "No display constant generated"
			)
			.setMqTag("relationship");

		setSubfieldsWithCardinality(
			"a", "Host name", "R",
			"b", "Access number", "R",
			"c", "Compression information", "R",
			"d", "Path", "R",
			"f", "Electronic name", "R",
			"h", "Processor of request", "NR",
			"i", "Instruction", "R",
			"j", "Bits per second", "NR",
			"k", "Password", "NR",
			"l", "Logon", "NR",
			"m", "Contact for access assistance", "R",
			"n", "Name of location of host", "NR",
			"o", "Operating system", "NR",
			"p", "Port", "NR",
			"q", "Electronic format type", "NR",
			"r", "Settings", "NR",
			"s", "File size", "R",
			"t", "Terminal emulation", "R",
			"u", "Uniform Resource Identifier", "R",
			"v", "Hours access method available", "R",
			"w", "Record control number", "R",
			"x", "Nonpublic note", "R",
			"y", "Link text", "R",
			"z", "Public note", "R",
			"2", "Access method", "NR",
			"3", "Materials specified", "NR",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		getSubfield("w").setCodeList(OrganizationCodes.getInstance());
		getSubfield("2").setCodeList(ElectronicAccessMethodsCodeList.getInstance());

		getSubfield("a").setMqTag("host");
		getSubfield("b").setMqTag("accessNumber");
		getSubfield("c").setMqTag("compression");
		getSubfield("d").setMqTag("path");
		getSubfield("f").setMqTag("name");
		getSubfield("h").setMqTag("processor");
		getSubfield("i").setMqTag("instruction");
		getSubfield("j").setMqTag("bitsPerSecond");
		getSubfield("k").setMqTag("password");
		getSubfield("l").setMqTag("logon");
		getSubfield("m").setMqTag("contact");
		getSubfield("n").setMqTag("location");
		getSubfield("o").setMqTag("operatingSystem");
		getSubfield("p").setMqTag("port");
		getSubfield("q").setMqTag("format");
		getSubfield("r").setMqTag("settings");
		getSubfield("s").setMqTag("fileSize");
		getSubfield("t").setMqTag("terminalEmulation");
		getSubfield("u").setMqTag("uri");
		getSubfield("v").setMqTag("hours");
		getSubfield("w").setMqTag("recordControlNumber");
		getSubfield("x").setMqTag("nonpublicNote");
		getSubfield("y").setMqTag("linkText");
		getSubfield("z").setMqTag("publicNote");
		getSubfield("2").setMqTag("accessMethod");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");

		setHistoricalSubfields(
			"g", "Uniform Resource Name [OBSOLETE, 2000]"
		);
	}
}
