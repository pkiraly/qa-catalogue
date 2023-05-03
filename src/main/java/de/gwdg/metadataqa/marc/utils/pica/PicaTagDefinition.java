package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.bibliographic.BibliographicFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.List;
import java.util.logging.Level;
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
  private String occurrence;
  private PicaRange range;
  private String id;
  private String counter;

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
      default: logger.log(Level.SEVERE, "unhandled 'hasSheet' value: {} ({})", new Object[]{input, tag.getRaw()});
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
    for (SubfieldDefinition subfield : this.subfields)
      subfield.setParent(this);
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

  public String getOccurrence() {
    return occurrence;
  }

  public void setCounter(String counter) {
    this.counter = counter;
    if (counter != null)
      range = new PicaRange(counter);
  }

  public String getCounter() {
    return counter;
  }

  public PicaRange getRange() {
    return range;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setOccurrence(String occurrence) {
    this.occurrence = occurrence;
    if (occurrence != null)
      range = new PicaRange(occurrence);
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

}
