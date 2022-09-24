package de.gwdg.metadataqa.marc.utils.keygenerator;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.model.SolrFieldType;

import java.util.regex.Pattern;

public class DataFieldKeyGenerator {
  private DataFieldDefinition definition;
  private SolrFieldType type;
  private String tag;
  private String indexTag;
  private static final Pattern nonValidSubfieldCode = Pattern.compile("[^0-9a-zA-Z]");
  private MarcVersion marcVersion;

  public DataFieldKeyGenerator(DataFieldDefinition definition, SolrFieldType type) {
    this.definition = definition;
    this.type = type;
    tag = definition.getTag();
    indexTag = definition.getIndexTag();
  }

  public DataFieldKeyGenerator(DataFieldDefinition definition,
                               SolrFieldType type,
                               String tag) {
    this.definition = definition;
    this.type = type;
    if (definition != null) {
      this.tag = definition.getTag();
      indexTag = definition.getIndexTag();
    } else {
      this.tag = tag;
      indexTag = tag;
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
    String codeForIndex = (subfieldDefinition != null) ? subfieldDefinition.getCodeForIndex() : code;
    String key = forSubfield(code, codeForIndex);

    return addVersion(subfieldDefinition, key);
  }

  public String forSubfield(SubfieldDefinition subfield) {
    String key = forSubfield(subfield.getCode(), subfield.getCodeForIndex());
    return addVersion(subfield, key);
  }

  private String addVersion(SubfieldDefinition subfieldDefinition, String key) {
    if (subfieldDefinition != null && subfieldDefinition.getMarcVersion() != null && type != SolrFieldType.MARC)
      key += "_" + subfieldDefinition.getMarcVersion().getCode();
    return key;
  }

  private String forSubfield(String code, String codeForIndex) {
    if (nonValidSubfieldCode.matcher(code).matches())
      code = String.format("x%x", (int) code.charAt(0));

    String key = "";
    switch (type) {
      case HUMAN:
        key = String.format("%s%s", indexTag, codeForIndex); break;
      case MIXED:
        if (!tag.equals(indexTag) && !codeForIndex.equals("_" + code))
          key = String.format("%s%s_%s%s", tag, code, indexTag, codeForIndex);
        else if (!tag.equals(indexTag) && codeForIndex.equals("_" + code))
          key = String.format("%s%s_%s", tag, code, indexTag);
        else
          key = String.format("%s%s", tag, code);
        break;
      case MARC:
      default:
        key = String.format("%s%s", tag, code);
        break;
    }

    return key;
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
    String key = "";
    switch (type) {
      case HUMAN:
        key = indexTag; break;
      case MIXED:
        if (definition != null && !tag.equals(indexTag)) {
          key = String.format("%s_%s", tag, indexTag);
        } else {
          key = tag;
        }
        break;
      case MARC:
      default:
        key = tag;
        break;
    }
    key += "_full";
    return key;
  }

}
