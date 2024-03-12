package de.gwdg.metadataqa.marc.analysis.shelfready;

import de.gwdg.metadataqa.marc.utils.marcspec.legacy.MarcSpec;

import java.util.ArrayList;
import java.util.List;

public enum ShelfReadyFieldsBooks {
  LDR06("LDR~06", "Type of record", 38, "a", "language material"),
  LDR07("LDR~07", "Bibliographic level", 40, "m", "monograph"),
  LDR1718("LDR~17-18", "Encoding level and Descriptive cataloguing form", 29),
  TAG00600("006~00", "Additional characteristics", 9, "m", "computer file"),
  // ...
  TAG010("010$a", "Library of Congress Control Number", 6),
  TAG015("015$a,015$2", "National Bibliography Number", 5),
  TAG020("020$a,020$z,020$q", "International Standard Book Number", 45),
  TAG035("035$a,035$z", "System Control Number", 19),
  TAG040("040$a,040$b,040$c,040$d,040$e", "Cataloguing source data", 29),
  TAG041("041$a,041$b,041$h", "Language code", 31),
  TAG050("050$a,050$b", "Library of Congress Call Number", 13),
  TAG082("082$a,082$2", "Dewey Decimal Classification Number", 28),
  TAG1XX("100$a,110$a,111$a,130$a", "Subject Headings", 44, true),
  TAG240("240$a,240$l,240$n,240$p", "Uniform Title", 34), // TODO: if applicable
  TAG245("245$a,245$b,245$n,245$p,245$c", "Title Statement", 44),
  TAG246("246$a,246$b,246$n,246$p", "Varying Form of Title", 39), // TODO: if applicable
  TAG250("250$a,250$b", "Edition Statement", 44),
  TAG264("264$a,264$b,264$c", "Publication, Copyright etc.", 45),
  TAG300("300$a,300$b,300$c", "Physical Description", 43),
  TAG336("336$a,336$b,336$2", "Content term(s) e.g. text, still image", 35),
  TAG337("337$a,337$b,337$2", "Media term e.g. unmediated or computer", 35),
  TAG338("338$a,338$b,338$2", "Carrier term e.g. volume or online resource", 34),
  TAG490("490$a,490$v", "Series Statement(s)", 40), // TODO: if applicable
  TAG500("500$a", "General note(s)", 30),
  TAG504("504$a", "Bibliography note", 19),
  TAG505("505$a,505$t,505$r", "Contents note", 14, true), // TODO: a or t
  TAG520("520$a", "Summary note(s)", 11),
  TAG546("546$a", "Language note(s)", 19),
  TAG588("588$a", "Source of descriptive metadata", 4),
  TAG6XX("600$a,610$a,611$a,630$a,647$a,648$a,650$a,651$a,653$a,654$a,655$a,656$a,657$a,658$a,662$a",
    "Subject Headings", 44, true),
  TAG7XX("700$a,710$a,711$a,720$a,730$a,740$a,751$a,752$a,753$a,754$a",
    "Added Entity Name/Title Heading(s) APP(s)", 44, true),
  TAG776("776$a", "Additional Physical format(s)", 12),
  TAG856("856$u", "Electronic access URL", 9),
  TAG8XX("800$a,810$a,811$a,830$a", "Series Heading(s) (traced) AAP(s) (if applicable)", 32, true),
  ;

  private List<MarcSpec> selectors = new ArrayList<>();
  private String marcPath;
  private String label;
  private String value;
  private String valueLabel;
  private int score;
  private double normalizedScore;
  private boolean oneOf = false;

  ShelfReadyFieldsBooks(String marcPath, String label, int score) {
    this.marcPath = marcPath;
    this.label = label;
    this.score = score;
    this.normalizedScore = score / 50.0;
    processMarcPath();
  }

  ShelfReadyFieldsBooks(String marcPath, String label, int score, boolean oneOf) {
    this(marcPath, label, score);
    this.oneOf = oneOf;
  }

  ShelfReadyFieldsBooks(String marcPath, String label, int score, String value, String valueLabel) {
    this(marcPath, label, score);
    this.value = value;
    this.valueLabel = valueLabel;
  }

  private void processMarcPath() {
    String[] marcPaths = this.marcPath.split(",");
    for (String path : marcPaths)
      selectors.add(new MarcSpec(path));
  }

  public String getMarcPath() {
    return marcPath;
  }

  public String getLabel() {
    return label;
  }

  public String getValue() {
    return value;
  }

  public String getValueLabel() {
    return valueLabel;
  }

  public int getScore() {
    return score;
  }

  public List<MarcSpec> getSelectors() {
    return selectors;
  }

  /**
   * Determines if it's sufficient for there to be at least one of the selectors present.
   * If the value is false, then the scores are calculated proportionally to the number of selectors present.
   * If the value is true, then the score is set to the maximum score for the category if at least one selector is present.
   */
  public boolean isOneOf() {
    return oneOf;
  }

  public double getNormalizedScore() {
    return normalizedScore;
  }

  @Override
  public String toString() {
    return "ShelfReadyFieldsBooks{" +
      "marcPath='" + marcPath + '\'' +
      ", label='" + label + '\'' +
      ", value='" + value + '\'' +
      ", valueLabel='" + valueLabel + '\'' +
    '}';
  }
}
