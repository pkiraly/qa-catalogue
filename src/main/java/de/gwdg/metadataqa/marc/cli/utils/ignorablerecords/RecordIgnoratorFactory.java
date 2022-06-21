package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;

public class RecordIgnoratorFactory {
  public static RecordIgnorator create(SchemaType type, String ignorableRecordsInput) {
    if (type.equals(SchemaType.MARC21)) {
      return new RecordIgnoratorMarc21(ignorableRecordsInput);
    } else if (type.equals(SchemaType.PICA)) {
      return new RecordIgnoratorPica(ignorableRecordsInput);
    } else {
      throw new IllegalArgumentException("Unsupported schema type");
    }
  }
}
