package de.gwdg.metadataqa.marc.cli.plugin;

import de.gwdg.metadataqa.marc.cli.parameters.CompletenessParameters;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcLeader;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.UnimarcRecord;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.tags.TagCategory;
import de.gwdg.metadataqa.marc.utils.TagHierarchy;
import de.gwdg.metadataqa.marc.utils.unimarc.UnimarcFieldDefinition;
import de.gwdg.metadataqa.marc.utils.unimarc.UnimarcLeaderDefinition;
import de.gwdg.metadataqa.marc.utils.unimarc.UnimarcSchemaManager;

import java.io.Serializable;

public class UnimarcCompletenessPlugin implements CompletenessPlugin, Serializable {

  private final CompletenessParameters parameters;
  private final UnimarcSchemaManager unimarcSchema;

  public UnimarcCompletenessPlugin(CompletenessParameters parameters, UnimarcSchemaManager unimarcSchema) {
    this.parameters = parameters;
    this.unimarcSchema = unimarcSchema;
  }

  @Override
  public String getDocumentType(BibliographicRecord marcRecord) {

    // marcRecord != null checked with this instanceof condition
    if (!(marcRecord instanceof UnimarcRecord)) {
      return MarcLeader.Type.BOOKS.getValue();
    }

    return ((UnimarcRecord) marcRecord)
        .getLeader()
        .getType()
        .getValue();
  }

  @Override
  public TagHierarchy getTagHierarchy(String path) {
    // There isn't the same detailed distinction as with MARC21.
    // Here the main distinction is if the tag is a:
    // - leader - The category could be defined as
    // - any other field

    if (path.startsWith("leader")) {
      UnimarcLeaderDefinition leaderDefinition = unimarcSchema.getLeaderDefinition();
      ControlfieldPositionDefinition positionDefinition = leaderDefinition.getPositionDefinitionById(path);
      return new TagHierarchy(TagCategory.UNIMARC_0, path, positionDefinition.getLabel());
    }

    // Get one of unimarc categories from the TagCategory enum. The first character of the path is the digit the enum
    // name ends with.

    TagCategory category = TagCategory.valueOf("UNIMARC_" + path.charAt(0));
    String[] paths = path.split("\\$");
    String fieldTag = paths[0];

    UnimarcFieldDefinition fieldDefinition = unimarcSchema.lookup(fieldTag);

    if (fieldDefinition == null) {
      return null;
    }
    String fieldLabel = fieldDefinition.getLabel();
    if (paths.length == 1) {
      return new TagHierarchy(category, fieldLabel, null);
    }

    String subfieldCode = paths[1];

    // This subfield code is either indicator or subfield code. E.g. "ind1" or "a"
    // This is an indicator
    if (subfieldCode.startsWith("ind")) {
      String indicatorNumber = subfieldCode.substring(3);
      int indicatorIndex = Integer.parseInt(indicatorNumber) - 1;

      String indicatorLabel = fieldDefinition.getIndicators().get(indicatorIndex).getLabel();
      return new TagHierarchy(category, fieldLabel, indicatorLabel);
    }

    // This is a subfield
    SubfieldDefinition subfieldDefinition = fieldDefinition.getSubfield(subfieldCode);
    if (subfieldDefinition == null) {
      return new TagHierarchy(category, fieldLabel, "");
    }

    String subfieldLabel = subfieldDefinition.getLabel();

    return new TagHierarchy(category, fieldLabel, subfieldLabel);
  }

  @Override
  public String getPackageName(DataField field) {
    TagCategory category = TagCategory.valueOf("UNIMARC_" + field.getTag().charAt(0));
    return category.getPackageName();
  }
}
