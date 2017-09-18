package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Taxonomic Classification Source Codes
 * https://www.loc.gov/standards/sourcelist/taxonomic.html
 */
public class TaxonomicClassificationSourceCodes extends CodeList {
	static {
		codes = Utils.generateCodes(
			"adw", "Animal Diversity Web (University of Michigan, Museum of Zoology)",
			"ampw", "AmphibiaWeb",
			"amsw", "Amphibian Species of the World (American Museum of Natural History)",
			"antbase", "Antbase (American Museum of Natural History)",
			"antw", "AntWeb (California Academy of Sciences)",
			"avib", "Avibase: the world bird database",
			"bltc", "BirdLife Taxonomic Checklist (BirdLife International)",
			"bmna", "Butterflies and Moths of North America",
			"bmwgn", "Butterflies and Moths of the World: Generic Names and Their Type-species (London: Natural History Museum)",
			"catf", "Catalog of Fishes (California Academy of Sciences)",
			"catl", "Catalogue of Life (Species 2000)",
			"chwf", "Checklist of World Ferns",
			"cnab", "Checklist of North American Butterflies (North American Butterfly Association)",
			"cnmab", "Checklist of North and Middle American Birds (American Ornithological Society)",
			"esacn", "Common Names of Insects Database (Entomological Society of America)",
			"esccn", "Common Names Database (Entomological Society of Canada)",
			"fishbase", "FishBase (FishBase Consortium)",
			"gcc", "Global Compositae Checklist (Landcare Research, New Zealand)",
			"glni", "The Global Lepidoptera Names Index (London: Natural History Museum)",
			"grassb", "GrassBase - The Online World Grass Flora (Kew: Royal Botanic Gardens)",
			"grintp", "GRIN Taxonomy for Plants (United States Department of Agriculture Agricultural Research Service)",
			"hns", "Hymenoptera Name Server (NFJ, Ohio State University)",
			"hol", "Hymenoptera Online (HOL) (Ohio State University)",
			"ildis", "ILDIS World Database of Legumes (ILDIS (International Legume Database & Information Service))",
			"inatur", "iNaturalist.org",
			"iocb", "IOC World Bird List",
			"itis", "ITIS: Integrated Taxonomic Information System",
			"iucnrl", "The IUCN Red List of Threatened Species (International Union for Conservation of Nature and Natural Resources)",
			"mayfly", "Mayfly Central (Purdue University)",
			"msow", "Mammal Species of the World",
			"ncbitb", "NCBI Taxonomy Browser (NCBI/GenBank)",
			"nominain", "Nomina Insecta Nearctica",
			"nsexpl", "NatureServe Explorer (NatureServe)",
			"nsinfo", "InfoNatura (NatureServe)",
			"orthosf", "Orthoptera Species File Online",
			"paleobdb", "The Paleobiology Database",
			"pctenl", "Phylum Ctenophora: list of all valid species names",
			"pgrc", "Plant Gene Resources of Canada: GRIN-CA Taxonomy (Agriculture and Agri-Food Canada)",
			"plantl", "The Plant List (Royal Botanic Gardens, Kew and Missouri Botanical Garden)",
			"plantsdb", "PLANTS Database (United States Department of Agriculture)",
			"protist", "Protist Information Server",
			"reptiledb", "The Reptile Database",
			"scorpf", "The Scorpion Files",
			"sysdip", "Systema Dipterorum: the BioSystematic Database of World Diptera",
			"taxdros", "TaxoDros: the Database on Taxonomy of Drosophilidaea",
			"termitedb", "On-line Termite Database",
			"trichwc", "Trichoptera World Checklist",
			"wasterdb", "World Asteroidea Database",
			"wikispc", "Wikispecies",
			"wodonatal", "World Odonata List",
			"wophidb", "World Ophiuroidea Database",
			"worms", "WoRMS: World Register of Marine Species",
			"wporidb", "World Porifera Database",
			"wremidb", "World Remipedia Database",
			"wspiderdb", "World Spider Catalog (World Spider Catalog Association WSCA)",
			"wturtledb", "World Turtle Database"
		);
		indexCodes();
	}

	private static TaxonomicClassificationSourceCodes uniqueInstance;

	private TaxonomicClassificationSourceCodes() {
	}

	public static TaxonomicClassificationSourceCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new TaxonomicClassificationSourceCodes();
		return uniqueInstance;
	}
}
