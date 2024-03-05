package de.gwdg.metadataqa.marc.cli.parameters;

import org.apache.commons.cli.ParseException;

import java.io.Serializable;

public class NetworkParameters extends CommonParameters implements Serializable {
  public static final String DEFAULT_FILE_NAME = "validation-report.txt";

  private NetworkAction action = NetworkAction.PRIMARY;
  private int groupLimit = 1000;
  private boolean isOptionSet;

  @Override
  protected void setOptions() {
    if (!isOptionSet) {
      super.setOptions();
      options.addOption("a", "action", true, "action: 'primary' (default), 'pairing'");
      options.addOption("l", "group-limit", true, "pair creation limit");
      isOptionSet = true;
    }
  }

  public NetworkParameters() {
    super();
  }

  public NetworkParameters(String[] arguments) throws ParseException {
    super(arguments);

    if (cmd.hasOption("action")) {
      for (NetworkAction registeredAction : NetworkAction.values()) {
        if (registeredAction.getLabel().equals(cmd.getOptionValue("action"))) {
          action = registeredAction;
          break;
        }
      }
    }

    if (cmd.hasOption("group-limit")) {
      groupLimit = Integer.parseInt(cmd.getOptionValue("group-limit"));
    }
  }

  public NetworkAction getAction() {
    return action;
  }

  public int getGroupLimit() {
    return groupLimit;
  }

  @Override
  public String formatParameters() {
    String text = super.formatParameters();
    text += String.format("action: %s%n", action);
    text += String.format("group-limit: %d%n", groupLimit);
    return text;
  }
}
