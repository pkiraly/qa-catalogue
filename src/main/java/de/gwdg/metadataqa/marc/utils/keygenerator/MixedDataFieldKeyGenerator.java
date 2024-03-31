package de.gwdg.metadataqa.marc.utils.keygenerator;

import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

public class MixedDataFieldKeyGenerator extends DataFieldKeyGenerator {

  public MixedDataFieldKeyGenerator(DataFieldDefinition definition) {
    super(definition);
  }

  public MixedDataFieldKeyGenerator(DataFieldDefinition definition,
                                    String tag,
                                    SchemaType schemaType) {
    super(definition, tag, schemaType);
  }

  @Override
  public String forInd1() {
    if (definition == null) {
      return String.format("%sind1", tag);
    }

    return String.format("%sind1_%s_%s", tag, indexTag, definition.getInd1().getIndexTag());
  }

  @Override
  public String forInd2() {
    if (definition == null) {
      return String.format("%sind2", tag);
    }

    return String.format("%sind2_%s_%s", tag, indexTag, definition.getInd2().getIndexTag());
  }

  @Override
  public String getKeyForSubfield(String code, String codeForIndex) {
    // Make sure that the field tag does not contain any invalid characters
    String safeTag = nonValidSubfieldCode.matcher(tag).find() ? escape(tag) : tag;

    // Also make sure that the subfield code does not contain any invalid characters
    if (nonValidSubfieldCode.matcher(code).matches()) {
      code = String.format("x%x", (int) code.charAt(0));
    }

    if (schemaType != null && schemaType.equals(SchemaType.PICA)) {
      return String.format("%s%s", safeTag, code);
    }
    if (!tag.equals(indexTag) && !codeForIndex.equals(code) && !codeForIndex.isEmpty()) {
      return String.format("%s%s_%s_%s", safeTag, code, indexTag, codeForIndex);
    }
    if (!tag.equals(indexTag)) {
      return String.format("%s%s_%s", safeTag, code, indexTag);
    }
    return String.format("%s%s", safeTag, code);
  }

  @Override
  public String forEntireField() {
    String safeTag = nonValidSubfieldCode.matcher(tag).find() ? escape(tag) : tag;
    if (definition != null && !tag.equals(indexTag)) {
      String key = String.format("%s_%s", safeTag, indexTag);
      return key + "_full";
    }
    return safeTag + "_full";
  }

  @Override
  protected String addVersionSpecifier(SubfieldDefinition versionSpecificDefinition, String key) {
    if (versionSpecificDefinition != null && versionSpecificDefinition.getMarcVersion() != null) {
      key += "_" + versionSpecificDefinition.getMarcVersion().getCode();
    }
    return key;
  }
}
