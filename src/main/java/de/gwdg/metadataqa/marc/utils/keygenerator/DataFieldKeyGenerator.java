package de.gwdg.metadataqa.marc.utils.keygenerator;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DataFieldKeyGenerator {
  private DataFieldDefinition definition;
  private SolrFieldType type;
  private String tag;
  private String indexTag;
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
    } else {
      if (definition != null) {
        this.tag = definition.getTag();
        this.indexTag = definition.getIndexTag();
      } else {
        this.tag = tag;
        this.indexTag = tag;
      }
    }
  }

  public String forInd1() {
    String key = "";
    switch (type) {
      case HUMAN:
        key = String.format(
          "%s_%s",
          indexTag, definition.getInd1().getIndexTag());
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
    if (subfieldDefinition == null && definition != null)
      subfieldDefinition = definition.getVersionSpecificSubfield(marcVersion, code);
    String codeForIndex = (subfieldDefinition != null) ? subfieldDefinition.getCodeForIndex(schemaType) : code;
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

  private String forSubfield(String code, String codeForIndex) {
    String safeTag = nonValidSubfieldCode.matcher(tag).find() ? escape(tag) : tag;
    if (nonValidSubfieldCode.matcher(code).matches()) {
      code = String.format("x%x", (int) code.charAt(0));
    }

    String key = "";
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

  private String escape(String tag) {
    List<String> safe = new ArrayList<>();
    for (int i = 0; i < tag.length(); i++) {
      String code = tag.substring(i, i+1);
      if (nonValidSubfieldCode.matcher(code).matches()) {
        code = "_"; // code = String.format("x%x", (int) code.charAt(0));
      }
      safe.add(code);
    }
    return StringUtils.join(safe, "");
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
