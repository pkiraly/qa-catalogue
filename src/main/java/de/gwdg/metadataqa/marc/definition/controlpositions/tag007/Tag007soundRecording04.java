package de.gwdg.metadataqa.marc.definition.controlpositions.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryIdentify;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoveryObtain;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
import static de.gwdg.metadataqa.marc.definition.FRBRFunction.UseOperate;

/**
 * Configuration of playback channels
 * https://www.loc.gov/marc/bibliographic/bd007s.html
 */
public class Tag007soundRecording04 extends ControlfieldPositionDefinition {
  private static Tag007soundRecording04 uniqueInstance;

  private Tag007soundRecording04() {
    initialize();
  }


  public static Tag007soundRecording04 getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Tag007soundRecording04();
    return uniqueInstance;
  }

  private void initialize() {
    label = "Configuration of playback channels";
    id = "007soundRecording04";
    mqTag = "configurationOfPlaybackChannels";
    positionStart = 4;
    positionEnd = 5;
    descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007s.html";
    codes = Utils.generateCodes(
      "m", "Monaural",
      "q", "Quadraphonic, multichannel, or surround",
      "s", "Stereophonic",
      "u", "Unknown",
      "z", "Other",
      "|", "No attempt to code"
    );
    functions = Arrays.asList(DiscoveryIdentify, DiscoverySelect, DiscoveryObtain, UseOperate);
    historicalCodes = Utils.generateCodes(
      "a", "Acoustic [OBSOLETE]",
      "f", "Monaural (digital) [OBSOLETE]",
      "g", "Quadraphonic (digital) [OBSOLETE]",
      "j", "Stereophonic (digital) [OBSOLETE]",
      "k", "Other (digital) [OBSOLETE]",
      "o", "Other (electric) [OBSOLETE]"
    );
  }
}