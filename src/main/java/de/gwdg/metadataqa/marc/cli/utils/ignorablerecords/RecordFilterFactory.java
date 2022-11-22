package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;

import java.util.Base64;

public class RecordFilterFactory {

  private RecordFilterFactory() {}

  public static RecordFilter create(SchemaType type, String allowableRecordsInput) {
    if (allowableRecordsInput.startsWith("base64:"))
      allowableRecordsInput = Utils.base64decode(allowableRecordsInput);

    if (type.equals(SchemaType.MARC21)) {
      return new RecordFilterMarc21(allowableRecordsInput);
    } else if (type.equals(SchemaType.PICA)) {
      return new RecordFilterPica(allowableRecordsInput);
    } else {
      throw new IllegalArgumentException("Unsupported schema type");
    }
  }
}
