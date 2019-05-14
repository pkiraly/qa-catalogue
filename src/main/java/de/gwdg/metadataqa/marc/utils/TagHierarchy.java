package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.TagDefinitionLoader;
import de.gwdg.metadataqa.marc.definition.tags.TagCategories;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagHierarchy {

  private static final Pattern dataFieldPattern = Pattern.compile("^(\\d\\d\\d)\\$(.*)$");

  private String packageLabel;
  private String tagLabel;
  private String subfieldLabel;

  public TagHierarchy() {
  }

  public TagHierarchy(String packageLabel, String tagLabel, String subfieldLabel) {
    this.packageLabel = packageLabel;
    this.tagLabel = tagLabel;
    this.subfieldLabel = subfieldLabel;
  }

  public String getPackageLabel() {
    return packageLabel;
  }

  public String getTagLabel() {
    return tagLabel;
  }

  public String getSubfieldLabel() {
    return subfieldLabel;
  }

  public static TagHierarchy createFromPath(String path) {
    return createFromPath(path, MarcVersion.MARC21);
  }

  public static TagHierarchy createFromPath(String path, MarcVersion version) {
    Matcher matcher = dataFieldPattern.matcher(path);
    if (matcher.matches()) {
      String tag = matcher.group(1);
      String subfieldCode = matcher.group(2);

      DataFieldDefinition definition = TagDefinitionLoader.load(tag, version);
      if (definition != null) {
        String tagLabel = definition.getLabel();

        SubfieldDefinition subfield = definition.getSubfield(subfieldCode);
        String subfieldLabel = subfield != null ? subfield.getLabel() : "";

        String packageName = Utils.extractPackageName(definition);
        String packageLabel = TagCategories.getPackage(packageName);

        return new TagHierarchy(packageLabel, tagLabel, subfieldLabel);
      }
    }
    return null;
  }
}
