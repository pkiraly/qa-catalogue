package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;

public class SubfieldParser {

  public static <T extends DataFieldDefinition> DataField parseField(T dataFieldDefinition, String text) {
    String ind1 = text.substring(0,1);
    String ind2 = text.substring(1,2);
    String[] rawParts = text.substring(3).split("\\$");
    String[] subfields = new String[rawParts.length * 2];
    for (int j = 0, i = 0; i < rawParts.length; i++) {
      j = i * 2;
      subfields[j] = rawParts[i].substring(0, 1);
      subfields[j+1] = rawParts[i].substring(1);
    }
    return new DataField(dataFieldDefinition, ind1, ind2, subfields);
  }

}
