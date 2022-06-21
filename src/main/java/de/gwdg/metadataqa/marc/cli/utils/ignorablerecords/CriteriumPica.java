package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.utils.pica.path.PicaPath;

public class CriteriumPica {
  private PicaPath path;
  private Operator operator;
  private String value;

  public CriteriumPica(PicaPath path, Operator operator, String value) {
    this.path = path;
    this.operator = operator;
    this.value = value;
  }

  public PicaPath getPath() {
    return path;
  }

  public Operator getOperator() {
    return operator;
  }

  public String getValue() {
    return value;
  }
}
