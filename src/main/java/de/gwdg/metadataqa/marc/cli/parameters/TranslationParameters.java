package de.gwdg.metadataqa.marc.cli.parameters;

import org.apache.commons.cli.ParseException;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class TranslationParameters extends Shacl4bibParameters {

  private static final Logger logger = Logger.getLogger(TranslationParameters.class.getCanonicalName());

  // default values
  protected static final String DEFAULT_TRANSLATION_OUTPUT_FILE = "translations.csv";
  // private String translationOutputFile = DEFAULT_TRANSLATION_OUTPUT_FILE;
  private List<String> debugFailedRules;
  private String translationPlaceNameDictionaryDir;
  private String translationExport;

  // protected String shaclOutputFile = DEFAULT_TRANSLATION_OUTPUT_FILE;

  protected void setOptions() {
    if (!isOptionSet) {
      super.setOptions();
      // options.addOption("O", "translationOutputFile", true, "output file");
      options.addOption("D", "translationDebugFailedRules", true, "log values that are not matched with the specified rule ID");
      options.addOption("E", "translationPlaceNameDictionaryDir", true, "A directory where place-synonyms-normalized.csv and coords.csv are located (see https://github.com/pkiraly/place-names)");
      options.addOption("F", "translationExport", true, "The exported translations file");
    }
  }

  public TranslationParameters() {
    super();
  }

  public TranslationParameters(String[] arguments) throws ParseException {
    super(arguments);
    if (!cmd.hasOption("shaclOutputFile")) {
      shaclOutputFile = DEFAULT_TRANSLATION_OUTPUT_FILE;
    }

    /*
    if (cmd.hasOption("translationOutputFile"))
      translationOutputFile = cmd.getOptionValue("translationOutputFile");

     */

    if (cmd.hasOption("translationDebugFailedRules"))
      debugFailedRules = Arrays.asList(cmd.getOptionValue("translationDebugFailedRules").split(","));

    if (cmd.hasOption("translationPlaceNameDictionaryDir"))
      translationPlaceNameDictionaryDir = cmd.getOptionValue("translationPlaceNameDictionaryDir");

    if (cmd.hasOption("translationExport"))
      translationExport = cmd.getOptionValue("translationExport");
  }

  public List<String> getDebugFailedRules() {
    return debugFailedRules;
  }

  /*
  public String getTranslationOutputFile() {
    return translationOutputFile;
  }
   */

  @Override
  public String getShaclOutputFile() {
    return shaclOutputFile;
  }

  public String getTranslationPlaceNameDictionaryDir() {
    return translationPlaceNameDictionaryDir;
  }

  public String getTranslationExport() {
    return translationExport;
  }

  @Override
  public String formatParameters() {
    String text = super.formatParameters();
    // text += String.format("translationOutputFile: %s%n", getTranslationOutputFile());
    text += String.format("translationDebugFailedRules: %s%n", getDebugFailedRules());
    text += String.format("translationPlaceNameDictionaryDir: %s%n", getTranslationPlaceNameDictionaryDir());
    text += String.format("translationExport: %s%n", getTranslationExport());
    return text;
  }
}