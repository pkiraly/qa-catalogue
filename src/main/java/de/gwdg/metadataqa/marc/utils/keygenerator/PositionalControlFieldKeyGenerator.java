package de.gwdg.metadataqa.marc.utils.keygenerator;

import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.model.SolrFieldType;

public class PositionalControlFieldKeyGenerator {
  private String tag;
  private String mqTag;
  private SolrFieldType type;
  private boolean isLeader = false;

  public PositionalControlFieldKeyGenerator(String tag, String mqTag, SolrFieldType type) {
    this.tag = tag;
    this.mqTag = mqTag;
    this.type = type;
    isLeader = tag.equals(mqTag);
  }

  public String forTag() {
    String key;

    switch (type) {
      case HUMAN: key = mqTag; break;
      case MIXED:
        key = isLeader ? tag : String.format("%s_%s", tag, mqTag);
        break;
      case MARC: default: key = tag; break;
    }

    return key;
  }

  public String forSubfield(ControlfieldPositionDefinition subfield) {
    String key;
    String code = subfield.getMqTag() != null
      ? subfield.getMqTag()
      : subfield.getId();

    switch (type) {
      case HUMAN:
        key = String.format("%s_%s", forTag(), code);
        break;
      case MIXED:
        if (isLeader) {
          // key = String.format("%s_%s_%s", tag, subfield.formatPositon(), code);
          key = String.format("%s_%s", subfield.getId(), code);
        } else {
          // key = String.format("%s_%s_%s_%s", tag, subfield.formatPositon(), mqTag, code);
          key = String.format("%s_%s_%s", subfield.getId(), mqTag, code);
        }
        break;
      case MARC: default:
        key = String.format("%s_%s", forTag(), subfield.formatPositon());
        break;
    }

    return key;
  }
}
