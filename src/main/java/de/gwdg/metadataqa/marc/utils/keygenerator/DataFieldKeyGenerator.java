package de.gwdg.metadataqa.marc.utils.keygenerator;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.model.SolrFieldType;

import java.util.regex.Pattern;

public class DataFieldKeyGenerator {
  private final DataFieldDefinition definition;
  private final SolrFieldType type;
  private final String tag;
  private final String indexTag;
  public static final Pattern nonValidSubfieldCode = Pattern.compile("[^0-9a-zA-Z]");
  private MarcVersion marcVersion;
  private SchemaType schemaType;

  public DataFieldKeyGenerator(DataFieldDefinition definition, SolrFieldType type) {
    this.definition = definition;
    this.type = type;
    tag = definition.getTag();
    indexTag = definition.getIndexTag();
  }

  public DataFieldKeyGenerator(DataFieldDefinition definition,
                               SolrFieldType type,
                               String tag,
                               SchemaType schemaType) {
    this.definition = definition;
    this.type = type;
    this.schemaType = schemaType;
    if (schemaType.equals(SchemaType.PICA)) {
      this.tag = tag;
      this.indexTag = escape(tag);
      return;
    }

    if (definition != null) {
      this.tag = definition.getTag();
      this.indexTag = definition.getIndexTag();
      return;
    }

    this.tag = tag;
    this.indexTag = tag;
  }

  /**
   * Generate key for indicator 1. Depending on the type of the field, the key can be in different formats:
   * - "ind1_ind1" for human-readable fields
   * -
   * @return
   */
  public String forInd1() {
    String key = "";
    switch (type) {
      case HUMAN:
        key = String.format(
          "%s_%s",
          indexTag,
          definition.getInd1().getIndexTag());
        break;
      case MIXED:
        if (definition == null) {
          key = String.format("%sind1", tag);
        } else {
          key = String.format(
            "%sind1_%s_%s",
            tag, indexTag, definition.getInd1().getIndexTag());
        }
        break;
      case MARC:
      default:
        key = String.format("%sind1", tag);
        break;
    }
    return key;
  }

  public String forInd2() {
    String key = "";
    switch (type) {
      case HUMAN:
        key = String.format(
          "%s_%s",
          indexTag, definition.getInd2().getIndexTag()); break;
      case MIXED:
        if (definition == null) {
          key = String.format("%sind2", tag);
        } else {
          key = String.format(
            "%sind2_%s_%s",
            tag, indexTag, definition.getInd2().getIndexTag());
        }
        break;
      case MARC:
      default:
        key = String.format("%sind2", tag);
        break;
    }
    return key;
  }

  public String forSubfield(MarcSubfield subfield) {
    String code = subfield.getCode();
    SubfieldDefinition subfieldDefinition = subfield.getDefinition();
    if (subfieldDefinition == null && definition != null) {
      subfieldDefinition = definition.getVersionSpecificSubfield(marcVersion, code);
    }

    String codeForIndex = (subfieldDefinition != null)
      ? subfieldDefinition.getCodeForIndex(schemaType)
      : code;

    String key = forSubfield(code, codeForIndex);

    return addVersion(subfieldDefinition, key);
  }

  public String forSubfield(SubfieldDefinition subfield) {
    String key = forSubfield(subfield.getCode(), subfield.getCodeForIndex(schemaType));
    return addVersion(subfield, key);
  }

  private String addVersion(SubfieldDefinition subfieldDefinition, String key) {
    if (subfieldDefinition != null && subfieldDefinition.getMarcVersion() != null && type != SolrFieldType.MARC)
      key += "_" + subfieldDefinition.getMarcVersion().getCode();
    return key;
  }

  /**
   * Generate a key for the given subfield code and index code. Depending on the fieldType chosen, either the code or
   * the index code is used.
   * @param code The subfield code.
   * @param codeForIndex The index code of the subfield.
   * @return The key for the subfield.
   */
  private String forSubfield(String code, String codeForIndex) {
    // Make sure that the field tag does not contain any invalid characters
    String safeTag = nonValidSubfieldCode.matcher(tag).find() ? escape(tag) : tag;

    // Also make sure that the subfield code does not contain any invalid characters
    if (nonValidSubfieldCode.matcher(code).matches()) {
      code = String.format("x%x", (int) code.charAt(0));
    }

    String key;
    switch (type) {
      case HUMAN:
        key = String.format("%s%s", indexTag, codeForIndex);
        break;
      case MIXED:
        if (schemaType != null && schemaType.equals(SchemaType.PICA)) {
          key = String.format("%s%s", safeTag, code);
        } else {
          if (!tag.equals(indexTag) && !codeForIndex.equals("_" + code))
            key = String.format("%s%s_%s%s", safeTag, code, indexTag, codeForIndex);
          else if (!tag.equals(indexTag) && codeForIndex.equals("_" + code))
            key = String.format("%s%s_%s", safeTag, code, indexTag);
          else
            key = String.format("%s%s", safeTag, code);
        }
        break;
      case MARC:
      default:
        key = String.format("%s%s", safeTag, code);
        break;
    }

    return key;
  }

  /**
   * Escapes characters in the tag so that none are matched by the nonValidSubfieldCode pattern. Such characters are
   * replaced by an underscore.
   * @param tag The tag to escape.
   * @return The escaped tag. For example: "%$a" -> "__a".
   */
  public static String escape(String tag) {
    return tag.replaceAll(nonValidSubfieldCode.pattern(), "_");
  }

  public String getIndexTag() {
    return indexTag;
  }

  public String forSubfield(MarcSubfield subfield, String extra) {
    return String.format("%s_%s", forSubfield(subfield), extra);
  }

  public void setMarcVersion(MarcVersion marcVersion) {
    this.marcVersion = marcVersion;
  }

  public String forFull() {
    String safeTag = nonValidSubfieldCode.matcher(tag).find() ? escape(tag) : tag;

    String key = "";
    switch (type) {
      case HUMAN:
        key = indexTag; break;
      case MIXED:
        if (definition != null && !tag.equals(indexTag)) {
          key = String.format("%s_%s", safeTag, indexTag);
        } else {
          key = safeTag;
        }
        break;
      case MARC:
      default:
        key = safeTag;
        break;
    }
    key += "_full";
    return key;
  }

  public SolrFieldType getType() {
    return type;
  }

  public MarcVersion getMarcVersion() {
    return marcVersion;
  }

  public String getTag() {
    return tag;
  }
}
