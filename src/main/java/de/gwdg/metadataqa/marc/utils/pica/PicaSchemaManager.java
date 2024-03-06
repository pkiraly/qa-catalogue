package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.utils.marcreader.SchemaManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PicaSchemaManager implements SchemaManager, Serializable {

  private static final long serialVersionUID = -2944103213221378653L;
  Map<String, PicaFieldDefinition> directory = new HashMap<>();
  Map<String, List<String>> tagIndex = new HashMap<>();

  public void add(PicaFieldDefinition definition) {
    String id = definition.getId();
    if (directory.containsKey(id)) {
      System.err.println("Tag is already defined! " + definition.getTag() + " " + directory.get(id));
    }
    directory.put(id, definition);
    if (!tagIndex.containsKey(definition.getTag())) {
      tagIndex.put(definition.getTag(), new ArrayList<>());
    }
    tagIndex.get(definition.getTag()).add(id);
  }

  public PicaFieldDefinition lookup(PicaDataField dataField) {
    String tag = dataField.getTag();
    String occurrence = dataField.getOccurrence();
    if (occurrence != null) {
      return getPicaFieldDefinition(tag, occurrence);
    } else {
      return lookup(tag);
    }
  }

  @Override
  public PicaFieldDefinition lookup(String searchTerm) {
    if (directory.containsKey(searchTerm))
      return directory.get(searchTerm);

    if (searchTerm.contains("/")) {
      String[] parts = searchTerm.split("/");
      String tag = parts[0];
      String occurrence = parts[1];
      return getPicaFieldDefinition(tag, occurrence);
    } else {
      if (tagIndex.containsKey(searchTerm) && tagIndex.get(searchTerm).size() == 1) {
        String id = tagIndex.get(searchTerm).get(0);
        return directory.get(id);
      }
    }
    return null;
  }

  private PicaFieldDefinition getPicaFieldDefinition(String tag, String occurrence) {
    if (tagIndex.containsKey(tag)) {
      for (String id : tagIndex.get(tag)) {
        var candidate = directory.get(id);
        if (candidate.inRange(occurrence))
          return candidate;
      }
    }
    return null;
  }

  @Override
  public int size() {
    return directory.size();
  }

}
