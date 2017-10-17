package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

public class CartographicDataSourceCodes extends CodeList {

	private void initialize() {
		codes = Utils.generateCodes(
			"aadcg", "Australian Antarctic Data Centre Antarctic Gazetteer (Australian Antarctic Data Centre)",
			"acgms", "Atlas of Canada Gazetteer Map Service (Natural Resources Canada)",
			"ahcb", "Atlas of Historical County Boundaries (Newberry Library)",
			"bcgnis", "BC Geographical Names Information System (BCGNIS) (Government of British Columbia)",
			"bound", "Bounding Box (Klokan Technologies)",
			"cbf", "Cartographic Boundary Files (U.S. Census Bureau)",
			"cga", "Composite Gazetteer of Antarctica (Scientific Committee on Antarctic Research (SCAR))",
			"cgndb", "Canadian Geographical Names Database (Natural Resources Canada)",
			"cgotw", "Columbia Gazetteer of the World (New York: Columbia University Press)",
			"csa", "Tirion, Wil. The Cambridge star atlas (Cambridge, UK ; New York: Cambridge University Press)",
			"cwg", "Cambridge world gazetteer: a geographical dictionary (Cambridge, UK ; New York: Cambridge University Press)",
			"dkcaw", "DK complete atlas of the world (New York: Dorling Kindersley Publishing)",
			"edm", "Enciclopedia de México (Ciudad de México: Enciclopedia de México)",
			"erpn", "Scott, Andrew. The encyclopedia of raincoast place names: a complete reference to coastal British Columbia (Madeira Park, BC: Harbour Publishing)",
			"esriarc", "ESRI ArcView",
			"fnib", "Foreign names information bulletin (Washington, D.C.: Defense Mapping Agency)",
			"gbos", "Ordnance Survey (Great Britain Ordnance Survey",
			"geoapn", "GeoScience Australia Place Names (Geoscience Australia)",
			"geobase", "geoba.se",
			"geogndb", "Geographic Names Database (GeoNameBase.com)",
			"geonames", "GeoNames",
			"geonet", "NGA GEOnet Names Server (GNS) (National Geospatial-Intelligence Agency)",
			"gettytgn", "Getty Thesaurus of Geographic Names Online (J. Paul Getty Trust)",
			"ghpn", "Ghana Place Names (Ghana Place Names Project)",
			"glogaz", "Global Gazetteer (Falling Rain Genomics)",
			"gnis", "Geographic Names Information System (GNIS) (United States Geological Survey, Board on Geographic Names)",
			"gnrnsw", "Geographical Names Register of NSW (New South Wales Government)",
			"gnt", "Gazetteer of the Northwest Territories (Government of the Northwest Territories)",
			"goj", "Gazetteer of Japan (Geographical Survey Institute, and the Hydrographic and Oceanographic Department of the Japan Coast Guard)",
			"gooearth", "Google Earth",
			"gpn", "Gazetteer of Planetary Nomenclature",
			"gufn", "GEBCO Gazetteer of Undersea Feature Names - General Bathymetric Chart of the Oceans (GEBCO)",
			"inmun", "Index Mundi (IndexMundi)",
			"knab", "Kohanimeandmebaas (KNAB) (Eesti Keele Instituut)",
			"lwip", "Ramsar List of Wetlands of International Importance (Ramsar)",
			"mapland", "Maplandia.com: Google maps world gazetteer",
			"mwgd", "Merriam-Webster's Geographical Dictionary (Springfield, MA: Merriam-Webster, Inc.)",
			"natmap", "The National Map-Boundaries (USGS) (U.S. Geological Survey)",
			"nlaci", "Newfoundland and Labrador abandoned communities index",
			"nsgn", "Nova Scotia Geographical Names (Province of Nova Scotia)",
			"ntpnr", "NT Place Names Register (Place Names Committee for the Northern Territory)",
			"nws", "Northwest Waterfall Survey",
			"nzggn", "New Zealand gazetteer of official geographic names (New Zealand Geographic Board Nga Pou Taunaha o Aotearoa (NZGB))",
			"nzpnd", "New Zealand place names database (New Zealand Geographic Board Nga Pou Taunaha o Aotearoa (NZGB)) [Archived]",
			"other", "Other [A source other than one for which a unique code has been established]",
			"peakbag", "Peakbagger.com",
			"pnosa", "PlaceNames Online: South Australian State Gazetteer (Government of South Australia, Department for Transport, Energy and Infrastructure)",
			"pwme", "Peakware World Mountain Encyclopedia (Peakware)",
			"qpns", "Queensland Place Names Search (Queensland Government)",
			"rsd", "Ramsar Sites Database (Ramsar)",
			"sagns", "South African Geographical Names System (South African Department of Arts and Culture)",
			"taw", "The Times atlas of the world (New York: Times Books)",
			"usdp", "The United States dictionary of places (New York: Somerset Publishers)",
			"volwrld", "Volcano World (Oregon Space Grant Consortium)",
			"vow", "Volcanoes of the World",
			"wdpa", "World Database on Protected Areas (World Commission on Protected Areas)",
			"wfbcia", "The World Factbook (Washington, D.C.: Central Intelligence Agency)",
			"whl", "World Heritage List (UNESCO)",
			"wikiped", "Wikipedia",
			"wld", "World Lake Database (International Lake Environment Committee (ILEC)) formerly called World Lakes Database",
			"wpntpl", "Washington Place Names (Tacoma Public Library)"
		);
		indexCodes();
	}

	private static CartographicDataSourceCodes uniqueInstance;

	private CartographicDataSourceCodes() {
		initialize();
	}

	public static CartographicDataSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new CartographicDataSourceCodes();
		return uniqueInstance;
	}
}
