package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.dao.DataField;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Paths;

public class PicaDatafieldFactory {
  private static PicaSchemaManager picaSchemaManager;

  private static void initialize() {
    initialize(null);
  }

  private static void initialize(String fileName) {
    if (picaSchemaManager == null) {
      String schemaFile = fileName != null && StringUtils.isNotEmpty(fileName) && new File(fileName).exists()
        ? fileName
        : Paths.get("src/main/resources/pica/avram-k10plus.json").toAbsolutePath().toString();
      picaSchemaManager = PicaSchemaReader.createSchema(schemaFile);
    }
  }

  public static DataField create(String tagWithOccurrence, String... subfields) {
    initialize();
    DataField dataField = new DataField(picaSchemaManager.lookup(tagWithOccurrence), null, null, subfields);
    String[] parts = tagWithOccurrence.split("/");
    if (parts.length == 2)
      dataField.setOccurrence(parts[1]);

    return dataField;
  }
}
