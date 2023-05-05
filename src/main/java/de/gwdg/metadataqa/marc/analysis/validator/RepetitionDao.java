package de.gwdg.metadataqa.marc.analysis.validator;

import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;

import java.util.Objects;

public class RepetitionDao {
  private String extendedTag;
  private DataFieldDefinition fieldDefinition;

  public RepetitionDao(String extendedTag, DataFieldDefinition fieldDefinition) {
    this.extendedTag = extendedTag;
    this.fieldDefinition = fieldDefinition;
  }

  public String getExtendedTag() {
    return extendedTag;
  }

  public DataFieldDefinition getFieldDefinition() {
    return fieldDefinition;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RepetitionDao that = (RepetitionDao) o;

    if (!Objects.equals(extendedTag, that.extendedTag)) return false;
    return Objects.equals(fieldDefinition, that.fieldDefinition);
  }

  @Override
  public int hashCode() {
    int result = extendedTag != null ? extendedTag.hashCode() : 0;
    result = 31 * result + (fieldDefinition != null ? fieldDefinition.hashCode() : 0);
    return result;
  }
}
