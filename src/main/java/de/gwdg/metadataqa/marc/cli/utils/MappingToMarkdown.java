package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.utils.MarcTagLister;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MappingToMarkdown {

  private static final Logger logger = Logger.getLogger(MappingToMarkdown.class.getCanonicalName());

  public static void main(String[] args) {
    List<Class<? extends DataFieldDefinition>> tags = MarcTagLister.listTags();

    for (Class<? extends DataFieldDefinition> tagClass : tags) {
      if (tagClass.getCanonicalName().contains("oclctags"))
        continue;

      Method getInstance = null;
      DataFieldDefinition tag = null;
      try {
        getInstance = tagClass.getMethod("getInstance");
        tag = (DataFieldDefinition) getInstance.invoke(tagClass);
        tagToMarkDown(tag);

      } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
        logger.log(Level.WARNING, "document", e);
      }
    }
  }

  private static void tagToMarkDown(DataFieldDefinition tag) {
    System.out.printf("|  |  |  |%n");
    System.out.printf("| **`%s`** | **`%s`** | **%s** |%n",
      tag.getTag(),
      tag.getIndexTag(),
      tag.getLabel());
    if (tag.getInd1().exists() || tag.getInd2().exists())
      System.out.printf("|  | indicators |  |%n");
    if (tag.getInd1().exists())
      System.out.printf("| `%s$ind1` | `%s_%s` | %s |%n",
        tag.getTag(),
        tag.getIndexTag(), tag.getInd1().getIndexTag(),
        tag.getInd1().getLabel());
    if (tag.getInd2().exists())
      System.out.printf("| `%s$ind2` | `%s_%s` | %s |%n",
        tag.getTag(),
        tag.getIndexTag(), tag.getInd2().getIndexTag(),
        tag.getInd2().getLabel());
    System.out.printf("|  | data subfields |  |%n");
    for (SubfieldDefinition subfield : tag.getSubfields()) {
      System.out.printf("| `%s$%s` | `%s%s` | %s |%n",
        tag.getTag(), subfield.getCode(),
        tag.getIndexTag(), subfield.getCodeForIndex(),
        subfield.getLabel());
    }
  }
}
