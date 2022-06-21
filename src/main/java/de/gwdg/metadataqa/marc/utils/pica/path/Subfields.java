package de.gwdg.metadataqa.marc.utils.pica.path;

import java.util.Arrays;
import java.util.List;

public class Subfields {
  public enum Type {
    SINGLE,
    MULTI,
    ALL
  }

  private Type type;
  private String input;
  private List<String> codes;

  public Subfields(Type type, String input) {
    this.type = type;
    this.input = input;
    codes = Arrays.asList(input.split(""));
  }

  public Type getType() {
    return type;
  }

  public String getInput() {
    return input;
  }

  public List<String> getCodes() {
    return codes;
  }
}
