package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.TagDefinitionLoader;
import de.gwdg.metadataqa.marc.definition.structure.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
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
    TagHierarchy tagHierarchy = null;
    Matcher matcher;
    matcher = leaderPattern.matcher(path);
    if (matcher.matches()) {
      tagHierarchy = getLeader(path);
      return tagHierarchy;
    }

    matcher = controlFieldPattern.matcher(path);
    if (matcher.matches()) {
      tagHierarchy = getControlField(matcher);
      return tagHierarchy;
    }

    matcher = controlFieldIdPattern.matcher(path);
    if (matcher.matches()) {
      tagHierarchy = getControlField2(path, matcher);
      return tagHierarchy;
    }

    matcher = dataSubfieldPattern.matcher(path);
    if (matcher.matches()) {
      tagHierarchy = getDataSubfield(version, matcher);
      return tagHierarchy;
    }

    matcher = dataFieldPattern.matcher(path);
    if (matcher.matches()) {
      tagHierarchy = getDatafield(version, matcher);
    }
    return tagHierarchy;
  }

  private static TagHierarchy getDatafield(MarcVersion version, Matcher matcher) {
    String tag = matcher.group(1);
    DataFieldDefinition definition = TagDefinitionLoader.load(tag, version);
    if (definition != null) {
      String packageName = Utils.extractPackageName(definition);
      TagCategory category = TagCategory.getPackage(packageName);
      return new TagHierarchy(category, definition.getLabel(), null);
    }
    return null;
  }

  private static TagHierarchy getDataSubfield(MarcVersion version, Matcher matcher) {
    String tag = matcher.group(1);
    String subfieldCode = matcher.group(2);

    DataFieldDefinition definition = TagDefinitionLoader.load(tag, version);
    if (definition == null) {
      return null;
    }

    String tagLabel = definition.getLabel();
    String packageName = Utils.extractPackageName(definition);
    TagCategory category = TagCategory.getPackage(packageName);

    String subfieldLabel = "";
    if (subfieldCode.equals("ind1")) {
      Indicator indicator = definition.getInd1();
      subfieldLabel = indicator.exists() ? indicator.getLabel() : "";
      return new TagHierarchy(category, tagLabel, subfieldLabel);
    }
    if (subfieldCode.equals("ind2")) {
      Indicator indicator = definition.getInd2();
      subfieldLabel = indicator.exists() ? indicator.getLabel() : "";
      return new TagHierarchy(category, tagLabel, subfieldLabel);
    }

    // This now deals with regular subfields instead of indicators
    SubfieldDefinition subfield;

    // Version-specific subfields
    if (version != null && definition.getVersionSpecificSubfields() != null) {
      subfield = definition.getVersionSpecificSubfield(version, subfieldCode);
      if (subfield != null) {
        subfieldLabel = String.format("%s (in %s version)", subfield.getLabel(), version.getCode());
        return new TagHierarchy(category, tagLabel, subfieldLabel);
      }
    }

    // General subfields that are not version-specific
    subfield = definition.getSubfield(subfieldCode);
    if (subfield != null) {
      subfieldLabel = subfield.getLabel();
    }

    return new TagHierarchy(category, tagLabel, subfieldLabel);
  }

  private static TagHierarchy getControlField2(String path, Matcher matcher) {
    String tag = matcher.group(1);
    var definition = getDataFieldDefinition(tag);

    if (definition == null) {
      return null;
    }

    String tagLabel = definition.getLabel();
    if (!(definition instanceof ControlFieldDefinition)) {
      return null;
    }

    ControlFieldDefinition fieldDefinition = (ControlFieldDefinition)  definition;
    ControlfieldPositionDefinition positionDefinition = fieldDefinition.getPositionDefinitionById(path);

    if (positionDefinition == null) {
      return null;
    }

    String subfieldLabel = positionDefinition.getLabel();
    return new TagHierarchy(TagCategory.TAGS_00X, tagLabel, subfieldLabel);
  }

  private static TagHierarchy getControlField(Matcher matcher) {
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
