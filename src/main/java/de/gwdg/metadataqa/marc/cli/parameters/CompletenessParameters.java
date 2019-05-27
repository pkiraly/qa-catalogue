package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat;
import org.apache.commons.cli.ParseException;

import java.io.Serializable;

public class CompletenessParameters extends CommonParameters implements Serializable {

  private ValidationErrorFormat format = ValidationErrorFormat.COMMA_SEPARATED;
  private boolean advanced = false;
  private boolean onlyPackages = false;
  private boolean isOptionSet;

  protected void setOptions() {
    if (!isOptionSet) {
      super.setOptions();
      options.addOption("r", "format", true, "specify a format");
      options.addOption("v", "advanced", false, "advanced mode");
      options.addOption("p", "onlyPackages", false, "only packages");
      isOptionSet = true;
    }
  }

  public CompletenessParameters() {
    super();
  }

  public CompletenessParameters(String[] arguments) throws ParseException {
    super(arguments);

    if (cmd.hasOption("advanced"))
      advanced = true;

    if (cmd.hasOption("onlyPackages"))
      onlyPackages = true;

    if (cmd.hasOption("format"))
      for (ValidationErrorFormat registeredFormat : ValidationErrorFormat.values()) {
        if (registeredFormat.getNames().contains(cmd.getOptionValue("format"))) {
          format = registeredFormat;
          break;
        }
      }
  }

  public ValidationErrorFormat getFormat() {
    return format;
  }

  public boolean isAdvanced() {
    return advanced;
  }

  public boolean isOnlyPackages() {
    return onlyPackages;
  }

  @Override
  public String formatParameters() {
    String text = super.formatParameters();
    text += String.format("format: %s%n", format.getLabel());
    text += String.format("advanced: %s%n", advanced);
    return text;
  }
}
