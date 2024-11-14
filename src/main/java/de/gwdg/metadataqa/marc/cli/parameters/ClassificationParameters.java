package de.gwdg.metadataqa.marc.cli.parameters;

import org.apache.commons.cli.ParseException;

import java.io.Serializable;

public class ClassificationParameters extends CommonParameters implements Serializable {
  private boolean isOptionSet;
  private boolean collectCollocations = false;

  @Override
  protected void setOptions() {
    if (!isOptionSet) {
      super.setOptions();
      options.addOption("A", "collectCollocations", false, "collect collocation of schemas");
      isOptionSet = true;
    }
  }

  public ClassificationParameters() {
    super();
  }

  public ClassificationParameters(String[] arguments) throws ParseException {
    super(arguments);

    if (cmd.hasOption("collectCollocations"))
      collectCollocations = true;
  }


  public boolean doCollectCollocations() {
    return collectCollocations;
  }

  public void setCollectCollocations(boolean collectCollocations) {
    this.collectCollocations = collectCollocations;
  }

  @Override
  public String formatParameters() {
    String text = super.formatParameters();
    text += String.format("collectCollocations: %s%n", collectCollocations);
    return text;
  }
}
