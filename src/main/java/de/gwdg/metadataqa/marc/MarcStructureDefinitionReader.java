package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.util.FileUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class MarcStructureDefinitionReader {

  List<MarcField> fields = new ArrayList<>();
  Map<String, SubfieldDefinition> qualifiedSubfields = new HashMap<>();

  public MarcStructureDefinitionReader(String fileName) 
      throws URISyntaxException, IOException {
    List<String> lines = FileUtils.readLines(fileName);
    lines2fields(lines);
    extractQualifiedSubfields();
  }

  private void lines2fields(List<String> lines) {
    MarcField field = null;
    for (String line : lines) {
      if (line.equals("")) {
        field = null;
      } else {
        String[] parts = line.split("\\s+", 3);
        if (field == null) {
          field = new MarcField(parts[0], parts[1], parts[2]);
          fields.add(field);
        } else {
          field.addSubfield(parts[0], parts[1], parts[2]);
        }
      }
    }
  }

  public List<MarcField> getFields() {
    return fields;
  }

  public Map<String, SubfieldDefinition> getQualifiedSubfields() {
    return qualifiedSubfields;
  }

  private void extractQualifiedSubfields() {
    for (MarcField field : getFields()) {
      String code = field.getCode();
      qualifiedSubfields.put(code + "$ind1", field.getIndicator1());
      qualifiedSubfields.put(code + "$ind2", field.getIndicator2());
      for (SubfieldDefinition subfield : field.getSubfieldList()) {
        qualifiedSubfields.put(code + "$" + subfield.getCode(), subfield);
      }
    }
  }
}
