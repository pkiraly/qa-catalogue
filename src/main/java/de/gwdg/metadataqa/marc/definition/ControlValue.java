package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.io.Serializable;

public class ControlValue implements Serializable {
  private static final long serialVersionUID = 5359828688351437413L; // Validatable

  private ControlfieldPositionDefinition definition;
  private String value;
  private BibliographicRecord marcRecord;

  public ControlValue(ControlfieldPositionDefinition definition, String value) {
    this.definition = definition;
    this.value = value;
  }

  public void setMarcRecord(BibliographicRecord marcRecord) {
    this.marcRecord = marcRecord;
  }

  public String getLabel() {
    return definition.getLabel();
  }

  public String getId() {
    return definition.getId();
  }

  public String resolve() {
    return definition.resolve(value);
  }

  public ControlfieldPositionDefinition getDefinition() {
    return definition;
  }

  public String getValue() {
    return value;
  }

  public BibliographicRecord getMarcRecord() {
    return marcRecord;
  }
}
