package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.dao.DataField;

public class PicaDatafieldFactory {
  private static PicaSchemaManager picaSchemaManager;

  private static void initialize() {
    initialize(null);
  }

  private static void initialize(String fileName) {
    if (picaSchemaManager == null) {
      picaSchemaManager = PicaSchemaReader.createSchemaManager(fileName);
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
