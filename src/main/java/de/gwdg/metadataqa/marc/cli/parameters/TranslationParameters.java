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

  // protected String shaclOutputFile = DEFAULT_TRANSLATION_OUTPUT_FILE;

  protected void setOptions() {
    if (!isOptionSet) {
      super.setOptions();
      // options.addOption("O", "translationOutputFile", true, "output file");
      options.addOption("D", "translationDebugFailedRules", true, "log values that are not matched with the specified rule ID");
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

    if (cmd.hasOption("translationDebugFailedRules")) {
      debugFailedRules = Arrays.asList(cmd.getOptionValue("translationDebugFailedRules").split(","));
    }
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

  @Override
  public String formatParameters() {
    String text = super.formatParameters();
    // text += String.format("translationOutputFile: %s%n", getTranslationOutputFile());
    text += String.format("translationDebugFailedRules: %s%n", getDebugFailedRules());
    return text;
  }
}