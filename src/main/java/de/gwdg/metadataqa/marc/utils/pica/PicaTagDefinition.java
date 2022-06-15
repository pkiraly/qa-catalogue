package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.bibliographic.BibliographicFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class PicaTagDefinition implements BibliographicFieldDefinition {

  private static final Logger logger = Logger.getLogger(PicaTagDefinition.class.getCanonicalName());
  private static final Pattern rangePattern = Pattern.compile("^(\\d+)-(\\d+)$");

  private PicaplusTag tag;
  private final String pica3;
  private Boolean repeatable;
  private Cardinality cardinality;
  private Boolean hasSheet;
  private final String label;
  protected String descriptionUrl;
  protected List<SubfieldDefinition> subfields;
  private String modified;

  public PicaTagDefinition(String pica3, String picaplus, boolean repeatable, boolean sheet, String label) {
    this.pica3 = pica3;
    tag = new PicaplusTag(picaplus);
    this.repeatable = repeatable;
    cardinality = repeatable ? Cardinality.Repeatable : Cardinality.Nonrepeatable;
    this.hasSheet = sheet;
    this.label = label;
  }

  public PicaTagDefinition(String[] input) {
    this.pica3 = input[0];
    tag = new PicaplusTag(input[1]);
    this.label = input[4];
    parseRepeatable(input[2]);
    parseSheet(input[3]);
  }

  public PicaplusTag getPicaplusTag() {
    return tag;
  }

  private void parseSheet(String input) {
    switch (input) {
      case "":
      case "-": this.hasSheet = false; break;
      case "+": this.hasSheet = true; break;
      default: logger.severe(String.format("unhandled 'hasSheet' value: %s (%s)", input, tag.getRaw()));
    }
  }

  private void parseRepeatable(String input) {
    switch (input) {
      case "": this.repeatable = false; break;
      case "*": this.repeatable = true; break;
      default: logger.severe("unhandled 'repeatable' value: " + input);
    }
    cardinality = repeatable ? Cardinality.Repeatable : Cardinality.Nonrepeatable;
  }

  public String getPica3() {
    return pica3;
  }

  public boolean isRepeatable() {
    return repeatable;
  }

  public boolean isHasSheet() {
    return hasSheet;
  }

  public String getLabel() {
    return label;
  }

  @Override
  public String toString() {
    return "PicaTagDefinition{" +
      "pica3='" + pica3 + '\'' +
      ", picaplus='" + tag.getRaw() + '\'' +
      ", repeatable=" + repeatable +
      ", hasSheet=" + hasSheet +
      ", description='" + label + '\'' +
      '}';
  }

  @Override
  public String getTag() {
    return tag.getTag();
  }

  @Override
  public Cardinality getCardinality() {
    return cardinality;
  }

  public List<SubfieldDefinition> getSubfields() {
    return subfields;
  }

  public void setSubfields(List<SubfieldDefinition> subfields) {
    this.subfields = subfields;
  }

  public String getDescriptionUrl() {
    return descriptionUrl;
  }

  public void setDescriptionUrl(String descriptionUrl) {
    this.descriptionUrl = descriptionUrl;
  }

  public String getModified() {
    return modified;
  }

  public void setModified(String modified) {
    this.modified = modified;
  }
}
