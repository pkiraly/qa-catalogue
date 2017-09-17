package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Electronic Access Methods Code List
 * http://www.loc.gov/standards/valuelist/electronaccess.html
 */
public class ElectronicAccessMethodsCodeList extends CodeList {

	static {
		codes = Utils.generateCodes(
			"acap", "Application configuration access protocol",
			"afs", "Andrew File System global file names",
			"cid", "Content identifier",
			"data", "Data",
			"dav", "Dav",
			"fax", "Fax",
			"file", "Host-specific file names",
			"ftp", "File Transfer Protocol",
			"gopher", "Gopher Protocol",
			"http", "Hypertext Transfer Protocol",
			"https", "Hypertext Transfer Protocol Secure",
			"imap", "Internet message access protocol",
			"ldap", "Lightweight Directory Access Protocol",
			"mailserv", "Mail server access",
			"mailto", "Electronic mail address (email)",
			"mid", "Message identifier",
			"modem", "Modem (includes dialup)",
			"news", "USENET news protocol",
			"nfs", "Network file system protocol",
			"nntp", "USENET news using NNTP access",
			"olt", "Opaque Lock Token",
			"pop", "Post Office Protocol (version 3)",
			"prospero", "Prospero Directory Service",
			"rtsp", "Real time streaming protocol",
			"service", "Service location",
			"sip", "Session initiation protocol",
			"tel", "Telephone",
			"telnet", "Reference to interactive sessions",
			"tip", "Transaction Internet Protocol",
			"tn", "Interactive 3270 emulation sessions",
			"vemmi", "Versatile multimedia interface",
			"wais", "Wide Area Information Servers",
			"zr", "Z39.50 Information Retrieval single record result",
			"zs", "Z39.50 Information Retrieval session"
		);
		indexCodes();
	}

	private static ElectronicAccessMethodsCodeList uniqueInstance;

	private ElectronicAccessMethodsCodeList() {}

	public static ElectronicAccessMethodsCodeList getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new ElectronicAccessMethodsCodeList();
		return uniqueInstance;
	}
}