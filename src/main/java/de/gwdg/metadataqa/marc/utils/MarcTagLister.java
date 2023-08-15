package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class MarcTagLister {

  public static List<Class<? extends DataFieldDefinition>> listTags() {
    Reflections reflections = new Reflections("de.gwdg.metadataqa.marc.definition.tags");

    Set<Class<? extends DataFieldDefinition>> subTypes = reflections
      .getSubTypesOf(DataFieldDefinition.class);

    Comparator<Class<? extends DataFieldDefinition>> byTag = (e1, e2) ->
      e1.getSimpleName().compareTo(e2.getSimpleName());

    List<Class<? extends DataFieldDefinition>> tags = new ArrayList<>();

    subTypes
      .stream()
      .filter((Class tagClass) ->
           !tagClass.getCanonicalName().endsWith("ControlFieldDefinition")
        && !tagClass.getCanonicalName().contains("tags.control.")
      )
      .sorted(byTag)
      .forEach(tags::add);

    return tags;
  }
}
