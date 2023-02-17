package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.TagDefinitionLoader;
import de.gwdg.metadataqa.marc.definition.tags.TagCategory;
import de.gwdg.metadataqa.marc.definition.tags.control.Control001Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control003Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control005Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control006Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control007Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.Control008Definition;
import de.gwdg.metadataqa.marc.definition.tags.control.LeaderDefinition;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagHierarchy {

  private static final Pattern leaderPattern = Pattern.compile("^leader(\\d+)$");
  private static final Pattern controlFieldPattern = Pattern.compile("^(00\\d)(/(\\d+|\\d+-\\d+))?$");
  private static final Pattern controlFieldIdPattern = Pattern.compile("^(00[6-8])([a-z][a-zA-Z]+)(\\d+)$");
  private static final Pattern dataSubfieldPattern = Pattern.compile("^(\\d\\d\\d)\\$(.*)$");
  private static final Pattern dataFieldPattern = Pattern.compile("^(...)$");

  private TagCategory category;
  private String tagLabel;
  private String subfieldLabel;

  public TagHierarchy() {
  }

  public TagHierarchy(TagCategory category, String tagLabel, String subfieldLabel) {
    this.category = category;
    this.tagLabel = tagLabel;
    this.subfieldLabel = subfieldLabel;
  }

  public int getPackageId() {
    return category.getId();
  }

  public String getPackageLabel() {
    return category.getLabel();
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
    System.err.println("createFromPath");
    TagHierarchy tagHierarchy = null;
    Matcher matcher;
    matcher = leaderPattern.matcher(path);
    if (matcher.matches()) {
      tagHierarchy = getLeader(path);
    } else {
      matcher = controlFieldPattern.matcher(path);
      if (matcher.matches()) {
        tagHierarchy = getControlField(path, matcher);
      } else {
        matcher = controlFieldIdPattern.matcher(path);
        if (matcher.matches()) {
          tagHierarchy = getControlField2(path, matcher);
        } else {
          matcher = dataSubfieldPattern.matcher(path);
          if (matcher.matches()) {
            tagHierarchy = getDataSubfield(path, version, matcher);
          } else {
            matcher = dataFieldPattern.matcher(path);
            if (matcher.matches()) {
              tagHierarchy = getDatafield(path, version, matcher);
            }
          }
        }
      }
    }
    return tagHierarchy;
  }

  private static TagHierarchy getDatafield(String path, MarcVersion version, Matcher matcher) {
    System.err.println("data field: " + path);
    String tag = matcher.group(1);
    DataFieldDefinition definition = TagDefinitionLoader.load(tag, version);
    if (definition != null) {
      String packageName = Utils.extractPackageName(definition);
      TagCategory category = TagCategory.getPackage(packageName);
      return new TagHierarchy(category, definition.getLabel(), null);
    }
    return null;
  }

  private static TagHierarchy getDataSubfield(String path, MarcVersion version, Matcher matcher) {
    System.err.println("dataFieldPattern: "  + path);
    String tag = matcher.group(1);
    String subfieldCode = matcher.group(2);

    DataFieldDefinition definition = TagDefinitionLoader.load(tag, version);
    if (definition != null) {
      String tagLabel = definition.getLabel();

      String subfieldLabel = "";
      if (subfieldCode.equals("ind1")) {
        Indicator indicator = definition.getInd1();
        subfieldLabel = indicator.exists() ? indicator.getLabel() : "";
      } else if (subfieldCode.equals("ind2")) {
        Indicator indicator = definition.getInd2();
        subfieldLabel = indicator.exists() ? indicator.getLabel() : "";
      } else {
        SubfieldDefinition subfield = null;
        if (version != null && definition.getVersionSpecificSubfields() != null) {
          subfield = definition.getVersionSpecificSubfield(version, subfieldCode);
          if (subfield != null)
            subfieldLabel = String.format("%s (in %s version)", subfield.getLabel(), version.getCode());
        }
        if (subfield == null)
          subfield = definition.getSubfield(subfieldCode);
        if (subfieldLabel.equals("") && subfield != null)
          subfieldLabel = subfield.getLabel();
      }

      String packageName = Utils.extractPackageName(definition);
      TagCategory category = TagCategory.getPackage(packageName);

      return new TagHierarchy(category, tagLabel, subfieldLabel);
    }
    return null;
  }

  private static TagHierarchy getControlField2(String path, Matcher matcher) {
    System.err.println("controlFieldIdPattern 2: "  + path);
    String tag = matcher.group(1);
    var definition = getDataFieldDefinition(tag);

    if (definition != null) {
      String tagLabel = definition.getLabel();
      if (definition instanceof ControlFieldDefinition) {
        ControlFieldDefinition fieldDefinition = (ControlFieldDefinition)  definition;
        ControlfieldPositionDefinition positionDefinition = fieldDefinition.getPositionDefinitionById(path);
        if (positionDefinition != null) {
          String subfieldLabel = positionDefinition.getLabel();
          return new TagHierarchy(TagCategory.TAGS_00X, tagLabel, subfieldLabel);
        }
      }
    }
    return null;
  }

  private static TagHierarchy getControlField(String path, Matcher matcher) {
    System.err.println("controlFieldPattern 1: "  + path);
    String tag = matcher.group(1);
    String position = matcher.group(3);
    var definition = getDataFieldDefinition(tag);

    if (definition != null) {
      String tagLabel = definition.getLabel();
      String subfieldLabel = "";
      if (StringUtils.isNotBlank(position)) {
        subfieldLabel = position;
      }
      return new TagHierarchy(TagCategory.TAGS_00X, tagLabel, subfieldLabel);
    }
    return null;
  }

  private static TagHierarchy getLeader(String path) {
    System.err.println("leaderPattern: "  + path);
    ControlFieldDefinition definition = LeaderDefinition.getInstance();
    ControlfieldPositionDefinition positionDefinition = definition.getPositionDefinitionById(path);
    if (positionDefinition != null) {
      String subfieldLabel = positionDefinition.getLabel();
      return new TagHierarchy(TagCategory.TAGS_00X, definition.getLabel(), subfieldLabel);
    }
    return null;
  }

  private static DataFieldDefinition getDataFieldDefinition(String tag) {
    DataFieldDefinition definition = null;
    if (tag.equals("001"))
      definition = Control001Definition.getInstance();
    else if (tag.equals("003"))
      definition = Control003Definition.getInstance();
    else if (tag.equals("005"))
      definition = Control005Definition.getInstance();
    else if (tag.equals("006"))
      definition = Control006Definition.getInstance();
    else if (tag.equals("007"))
      definition = Control007Definition.getInstance();
    else if (tag.equals("008"))
      definition = Control008Definition.getInstance();
    return definition;
  }
}
