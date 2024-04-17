package de.gwdg.metadataqa.marc.utils.keygenerator;

import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

public class HumanDataFieldKeyGenerator extends DataFieldKeyGenerator {

  public HumanDataFieldKeyGenerator(DataFieldDefinition definition) {
    super(definition);
  }

  public HumanDataFieldKeyGenerator(DataFieldDefinition definition,
                                    String tag,
                                    SchemaType schemaType) {
    super(definition, tag, schemaType);
  }

  @Override
  public String forInd1() {
    return String.format(
      "%s_%s",
      indexTag, definition.getInd1().getIndexTag());
  }

  @Override
  public String forInd2() {
    return String.format(
      "%s_%s",
      indexTag, definition.getInd2().getIndexTag());
  }

  @Override
  protected String addVersionSpecifier(SubfieldDefinition versionSpecificDefinition, String key) {
    if (versionSpecificDefinition != null && versionSpecificDefinition.getMarcVersion() != null) {
      key += "_" + versionSpecificDefinition.getMarcVersion().getCode();
    }
    return key;
  }

  @Override
  public String getKeyForSubfield(String code, String codeForIndex) {
    if (schemaType != null && schemaType.equals(SchemaType.PICA)) {
      return String.format("%s%s", indexTag, codeForIndex);
    }
    if (!codeForIndex.isEmpty()) {
      return String.format("%s_%s", indexTag, codeForIndex);
    }
    return String.format("%s", indexTag);
  }

  @Override
  public String forFullField() {
    return indexTag + "_full";
  }
}
