package de.gwdg.metadataqa.marc.utils.pica.path;

import de.gwdg.metadataqa.marc.utils.SchemaSpec;

import java.io.Serializable;

public class PicaSpec implements SchemaSpec, Serializable {
  private PicaPath path;
  private String function;

  public PicaSpec(String input) {
    String[] parts = input.split("\\|", 2);
    path = PicaPathParser.parse(parts[0]);
    if (parts.length == 2)
      function = parts[1];
  }

  public PicaPath getPath() {
    return path;
  }

  public String getFunction() {
    return function;
  }

  @Override
  public String encode() {
    String encoded = path.getPath();
    // if (function != null)
    //   encoded += "|" + function;
    return encoded;
  }
}
