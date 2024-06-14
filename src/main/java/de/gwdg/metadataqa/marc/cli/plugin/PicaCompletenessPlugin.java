package de.gwdg.metadataqa.marc.cli.plugin;

import de.gwdg.metadataqa.marc.cli.parameters.CompletenessParameters;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.tags.TagCategory;
import de.gwdg.metadataqa.marc.utils.TagHierarchy;
import de.gwdg.metadataqa.marc.utils.pica.FieldPath;
import de.gwdg.metadataqa.marc.utils.pica.PicaFieldDefinition;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaManager;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaReader;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class PicaCompletenessPlugin implements CompletenessPlugin, Serializable {
  private static final long serialVersionUID = 2002980948561227741L;

  private final CompletenessParameters parameters;
  private final String typeField;
  private final String typeSubfield;
  private final String separator;
  private final PicaSchemaManager picaSchema;
  private static final Map<String, String> types = Map.ofEntries(
    Map.entry("A", "Druckschriften (einschließlich Bildbänden)"),
    Map.entry("B", "Tonträger, Videodatenträger, Bildliche Darstellungen"),
    Map.entry("C", "Blindenschriftträger und andere taktile Materialien"),
    Map.entry("E", "Mikroform"),
    Map.entry("H", "Handschriftliches Material"),
    Map.entry("L", "Lokales Katalogisat (nur GBV)"),
    Map.entry("O", "Elektronische Ressource im Fernzugriff"),
    Map.entry("S", "Elektronische Ressource auf Datenträger"),
    Map.entry("V", "Objekt"),
    Map.entry("Z", "Medienkombination"),
    Map.entry("a", "Mailboxsatz")
  );

  public PicaCompletenessPlugin(CompletenessParameters parameters) {
    this.parameters = parameters;
    separator = Pattern.quote(parameters.getPicaSubfieldSeparator());
    FieldPath path = parse(parameters.getPicaRecordTypeField());
    typeField = path.getField();
    typeSubfield = path.getSubfield();
    picaSchema = PicaSchemaReader.createSchemaManager(parameters.getPicaSchemaFile());
  }

  @Override
  public String getDocumentType(BibliographicRecord marcRecord) {
    List<DataField> fieldInstances = marcRecord.getDatafieldsByTag(typeField);
    if (fieldInstances == null || fieldInstances.isEmpty())
      return "invalid";
    String code = fieldInstances.get(0).getSubfield(typeSubfield).get(0).getValue().substring(0, 1);
    return types.getOrDefault(code, "invalid");
  }

  @Override
  public TagHierarchy getTagHierarchy(String rawpath) {
    FieldPath path = parse(rawpath);
    String fieldLabel = "";
    String subfieldLabel = "";
    PicaFieldDefinition fieldDefinition = picaSchema.lookup(path.getField());
    TagCategory category = TagCategory.OTHER;
    if (fieldDefinition != null) {
      category = getTagCategory(fieldDefinition);
      fieldLabel = fieldDefinition.getLabel();
      if (!path.getSubfield().equals("")){
        SubfieldDefinition subfieldDefinition = fieldDefinition.getSubfield(path.getSubfield());
        subfieldLabel = subfieldDefinition != null ? subfieldDefinition.getLabel() : "";
      }
    }

    return new TagHierarchy(category, fieldLabel, subfieldLabel);
  }

  @Override
  public String getPackageName(DataField field) {
    if (field.getDefinition() != null) {
      return getTagCategory((PicaFieldDefinition) field.getDefinition()).getPackageName();
    }
    return TagCategory.OTHER.getPackageName();
  }

  private TagCategory getTagCategory(PicaFieldDefinition field) {
    switch (field.getTag().substring(0, 1)) {
      case "0": return TagCategory.PICA_0;
      case "1": return TagCategory.PICA_1;
      case "2": return TagCategory.PICA_2;
      default:
        return TagCategory.OTHER;
    }
  }

  private FieldPath parse(String path) {
    String[] parts = path.split(separator);
    if (parts.length == 1) {
      return new FieldPath(parts[0], "");
    }
    return new FieldPath(parts[0], parts[1]);
  }
}
