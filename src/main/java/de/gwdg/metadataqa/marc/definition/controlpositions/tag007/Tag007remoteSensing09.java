package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.*;

import java.util.Arrays;

/**
 * Data type
 * https://www.loc.gov/marc/bibliographic/bd007r.html
 */
public class Tag007remoteSensing09 extends ControlfieldPositionDefinition {
  private static Tag007remoteSensing09 uniqueInstance;

  private Tag007remoteSensing09() {
    initialize();
    extractValidCodes();
  }

  public static Tag007remoteSensing09 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007remoteSensing09();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Data type";
    id = "007remoteSensing09";
    mqTag = "dataType";
    positionStart = 9;
    positionEnd = 11;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007r.html";
    codes = Utils.generateCodes(
      "aa", "Visible light",
      "da", "Near infrared",
      "db", "Middle infrared",
      "dc", "Far infrared",
      "dd", "Thermal infrared",
      "de", "Shortwave infrared (SWIR)",
      "df", "Reflective infrared",
      "dv", "Combinations",
      "dz", "Other infrared data",
      "ga", "Sidelooking airborne radar (SLAR)",
      "gb", "Synthetic aperture radar (SAR)-Single frequency",
      "gc", "SAR-multi-frequency (multichannel)",
      "gd", "SAR-like polarization",
      "ge", "SAR-cross polarization",
      "gf", "Infometric SAR",
      "gg", "polarmetric SAR",
      "gu", "Passive microwave mapping",
      "gz", "Other microwave data",
      "ja", "Far ultraviolet",
      "jb", "Middle ultraviolet",
      "jc", "Near ultraviolet",
      "jv", "Ultraviolet combinations",
      "jz", "Other ultraviolet data",
      "ma", "Multi-spectral, multidata",
      "mb", "Multi-temporal",
      "mm", "Combination of various data types",
      "nn", "Not applicable",
      "pa", "Sonar--water depth",
      "pb", "Sonar--bottom topography images, sidescan",
      "pc", "Sonar--bottom topography, near-surface",
      "pd", "Sonar--bottom topography, near-bottom",
      "pe", "Seismic surveys",
      "pz", "Other acoustical data",
      "ra", "Gravity anomalies (general)",
      "rb", "Free-air",
      "rc", "Bouger",
      "rd", "Isostatic",
      "sa", "Magnetic field",
      "ta", "radiometric surveys",
      "uu", "Unknown",
      "zz", "Other",
      "||", "No attempt to code"
    );
    functions = Arrays.asList(UseInterpret);
  }
}