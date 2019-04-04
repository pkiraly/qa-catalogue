package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Name and Title Authority Source Codes
 * https://www.loc.gov/standards/sourcelist/name-title.html
 */
public class NameAndTitleAuthoritySourceCodes extends CodeList {

  private void initialize() {
    name = "Name and Title Authority Source Codes";
    url = "https://www.loc.gov/standards/sourcelist/name-title.html";
    codes = Utils.generateCodes(
      "abne", "Autoridades de la Biblioteca Nacional de España (Madrid: Biblioteca Nacional de España)",
      "banqa", "Fichier d'autorité local de Bibliothèque et Archives nationales du Québec (Bibliothèque et Archives nationales du Québec)",
      "bibalex", "Bibliotheca Alexandrina name and subject authority file (Alexandria, Egypt: Bibliotheca Alexandrina)",
      "cantic", "CANTIC (Catàleg d'autoritats de noms i títols de Catalunya) (Biblioteca de Catalunya)",
      "conorsi", "CONOR.SI [name authority file] (Maribor, Slovenia: Institut informacijskih znanosti (IZUM))",
      "gkd", "Gemeinsame Körperschaftsdatei (Leipzig, Frankfurt: Deutsche Nationalbibliothek)",
      "gnd", "Gemeinsame Normdatei (Leipzig, Frankfurt: Deutsche Nationalbibliothek)",
      "hapi", "HAPI thesaurus and name authority, 1970-2000 (Los Angeles, CA: UCLC Latin America Center Pubs.)",
      "hkcan", "Hong Kong Chinese Authority File (Name) - HKCAN (Hong Kong: JULAC)",
      "lacnaf", "Library and Archives Canada name authority file (Ottawa: ON: Library and Archives Canada)",
      "naf", "NACO authority file",
      "nalnaf", "National Agricultural Library name authority file (Beltsville, Maryland; National Agricultural Library)",
      "nliaf", "National Library of Israel Authority File (Jerusalem: National Library of Israel)",
      "nlmnaf", "National Library of Medicine name authority file (Bethesda, Maryland: National Library of Medicine)",
      "nznb", "New Zealand national bibliographic (Wellington: National Library of New Zealand)",
      "sanb", "South African national bibliography authority file",
      "viaf", "Virtual International Authority File",
      "ulan", "Union list of artist names (Los Angeles: Getty Research Institute)",
      "unbisn", "UNBIS name authority list (New York, NY: Dag Hammarskjöld Library, United Nations; [London]: Chadwyck-Healey)"
    );
    indexCodes();
  }

  private static NameAndTitleAuthoritySourceCodes uniqueInstance;

  private NameAndTitleAuthoritySourceCodes() {
    initialize();
  }

  public static NameAndTitleAuthoritySourceCodes getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new NameAndTitleAuthoritySourceCodes();
    return uniqueInstance;
  }
}
