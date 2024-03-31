package de.gwdg.metadataqa.marc.utils.keygenerator;

import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

public class MarcDataFieldKeyGenerator extends DataFieldKeyGenerator {

  public MarcDataFieldKeyGenerator(DataFieldDefinition definition) {
    super(definition);
  }

  public MarcDataFieldKeyGenerator(DataFieldDefinition definition,
                                   String tag,
                                   SchemaType schemaType) {
    super(definition, tag, schemaType);
  }

  @Override
  public String forInd1() {
    return String.format("%sind1", tag);
  }

  @Override
  public String forInd2() {
    return String.format("%sind2", tag);
  }

  @Override
  public String getKeyForSubfield(String code, String codeForIndex) {
    // Make sure that the field tag does not contain any invalid characters
    String safeTag = nonValidSubfieldCode.matcher(tag).find() ? escape(tag) : tag;

    // Also make sure that the subfield code does not contain any invalid characters
    if (nonValidSubfieldCode.matcher(code).matches()) {
      code = String.format("x%x", (int) code.charAt(0));
    }

    return String.format("%s%s", safeTag, code);
  }

  @Override
  public String forEntireField() {
    String safeTag = nonValidSubfieldCode.matcher(tag).find() ? escape(tag) : tag;
    return safeTag + "_full";
  }

  @Override
  protected String addVersionSpecifier(SubfieldDefinition versionSpecificDefinition, String key) {
    return key;
  }
}
