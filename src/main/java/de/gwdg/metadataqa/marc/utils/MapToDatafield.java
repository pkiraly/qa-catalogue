package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.api.util.Converter;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.TagDefinitionLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapToDatafield {

  public static DataField parse(Map<String, Object> fieldInstance, MarcVersion version) {
    DataFieldDefinition definition = TagDefinitionLoader.load((String)fieldInstance.get("tag"), version);
    if (definition == null)
      return null;

    String ind1 = (String)fieldInstance.get("ind1");
    String ind2 = (String)fieldInstance.get("ind2");
    List<Map<String, String>> subfields = toMap(
      Converter.jsonObjectToList(fieldInstance.get("subfield")));

    return new DataField(definition, ind1, ind2, subfields);
  }

  private static List<Map<String, String>> toMap(List<Object> objectList) {
    List<Map<String, String>> mapList = new ArrayList<>();
    for (Object subfield : objectList) {
      if (subfield instanceof Map) {
        mapList.add((Map)subfield);
      }
    }
    return mapList;
  }
}
