package de.gwdg.metadataqa.marc.utils.keygenerator;

import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.model.SolrFieldType;

public class DataFieldKeyGeneratorFactory {

  private DataFieldKeyGeneratorFactory() {
    // Prevent instantiation
  }

  public static DataFieldKeyGenerator create(SolrFieldType fieldType, DataFieldDefinition definition, String tag, SchemaType schemaType) {
    if (fieldType == SolrFieldType.MIXED) {
      return new MixedDataFieldKeyGenerator(definition, tag, schemaType);
    }
    if (fieldType == SolrFieldType.HUMAN) {
      return new HumanDataFieldKeyGenerator(definition, tag, schemaType);
    }

    // In any other case, return a MarcDataFieldKeyGenerator
    return new MarcDataFieldKeyGenerator(definition, tag, schemaType);
  }

  public static DataFieldKeyGenerator create(SolrFieldType fieldType, DataFieldDefinition definition) {
    if (fieldType == SolrFieldType.MIXED) {
      return new MixedDataFieldKeyGenerator(definition);
    }
    if (fieldType == SolrFieldType.HUMAN) {
      return new HumanDataFieldKeyGenerator(definition);
    }

    // In any other case, return a MarcDataFieldKeyGenerator
    return new MarcDataFieldKeyGenerator(definition);
  }
}
