package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;

public class RecordIgnoratorFactory {

  private RecordIgnoratorFactory() {}

  public static RecordIgnorator create(SchemaType type, String ignorableRecordsInput) {
    // I guess that RecordIgnoratorMarc21 can be used for UNIMARC as well, as the main idea is that it uses MARCspec
    if (type.equals(SchemaType.MARC21) || type.equals(SchemaType.UNIMARC)) {
      return new RecordIgnoratorMarc21(ignorableRecordsInput);
    } else if (type.equals(SchemaType.PICA)) {
      return new RecordIgnoratorPica(ignorableRecordsInput);
    } else {
      throw new IllegalArgumentException("Unsupported schema type");
    }
  }
}
