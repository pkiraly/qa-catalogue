package de.gwdg.metadataqa.marc.dao.record;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.analysis.AuthorityCategory;
import de.gwdg.metadataqa.marc.analysis.ShelfReadyFieldsBooks;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.UnimarcLeader;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UnimarcRecord extends MarcRecord {

  protected static final List<String> allowedControlFieldTags = Arrays.asList("001", "003", "005");

  // TODO for now I essentially have no idea what to do with these attributes such as authority tags, subjectTags etc.
  private static List<String> authorityTags;
  private static Map<String, Boolean> authorityTagsIndex;
  private static Map<String, Boolean> subjectTagIndex;
  private static Map<String, Map<String, Boolean>> skippableAuthoritySubfields;
  private static Map<String, Map<String, Boolean>> skippableSubjectSubfields;
  /**
   * Key-value pairs of AuthorityCategory and tags
   */
  private static Map<AuthorityCategory, List<String>> authorityTagsMap;
  private static Map<ShelfReadyFieldsBooks, Map<String, List<String>>> shelfReadyMap;

  private UnimarcLeader leader;

  public UnimarcRecord() {
    super();
    schemaType = SchemaType.UNIMARC;
  }

  public UnimarcRecord(String id) {
    super(id);
    schemaType = SchemaType.UNIMARC;
  }

  public List<DataField> getAuthorityFields() {
    if (authorityTags == null) {
      initializeAuthorityTags();
    }
    return getAuthorityFields(authorityTags);
  }

  @Override
  public List<String> getAllowedControlFieldTags() {
    return allowedControlFieldTags;
  }

  public boolean isAuthorityTag(String tag) {
    if (authorityTagsIndex == null) {
      initializeAuthorityTags();
    }
    return authorityTagsIndex.getOrDefault(tag, false);
  }

  public boolean isSkippableAuthoritySubfield(String tag, String code) {
    if (authorityTagsIndex == null)
      initializeAuthorityTags();

    if (!skippableAuthoritySubfields.containsKey(tag))
      return false;

    return skippableAuthoritySubfields.get(tag).getOrDefault(code, false);
  }

  public boolean isSubjectTag(String tag) {
    if (subjectTagIndex == null) {
      initializeAuthorityTags();
    }
    return subjectTagIndex.getOrDefault(tag, false);
  }

  public boolean isSkippableSubjectSubfield(String tag, String code) {
    if (subjectTagIndex == null)
      initializeAuthorityTags();

    if (!skippableSubjectSubfields.containsKey(tag))
      return false;

    return skippableSubjectSubfields.get(tag).getOrDefault(code, false);
  }

  public Map<DataField, AuthorityCategory> getAuthorityFieldsMap() {
    if (authorityTags == null)
      initializeAuthorityTags();

    return getAuthorityFields(authorityTagsMap);
  }

  public Map<ShelfReadyFieldsBooks, Map<String, List<String>>> getShelfReadyMap() {
    if (shelfReadyMap == null)
      initializeShelfReadyMap();

    return shelfReadyMap;
  }

  private static void initializeAuthorityTags() {
    authorityTags = Arrays.asList(
    );
    authorityTagsIndex = Utils.listToMap(authorityTags);

    skippableAuthoritySubfields = new HashMap<>();

    List<String> subjectTags = Arrays.asList(
      "045A", "045B", "045F", "045R", "045C", "045E", "045G"
    );
    subjectTagIndex = Utils.listToMap(subjectTags);
    skippableSubjectSubfields = new HashMap<>();

    authorityTagsMap = new EnumMap<>(AuthorityCategory.class);

  }

  private static void initializeShelfReadyMap() {
    shelfReadyMap = new LinkedHashMap<>();
    Map<ShelfReadyFieldsBooks, String> raw = new LinkedHashMap<>();
    raw.put(ShelfReadyFieldsBooks.LDR06, "LDR~06");
    raw.put(ShelfReadyFieldsBooks.LDR07, "LDR~07");
    raw.put(ShelfReadyFieldsBooks.LDR1718, "LDR~17-18");
    raw.put(ShelfReadyFieldsBooks.TAG00600, "006~00");
    raw.put(ShelfReadyFieldsBooks.TAG010, "010$a");
    raw.put(ShelfReadyFieldsBooks.TAG015, "015$a,015$2");
    raw.put(ShelfReadyFieldsBooks.TAG020, "020$a,020$z,020$q");
    raw.put(ShelfReadyFieldsBooks.TAG035, "035$a,035$z");
    raw.put(ShelfReadyFieldsBooks.TAG040, "040$a,040$b,040$c,040$d,040$e");
    raw.put(ShelfReadyFieldsBooks.TAG041, "041$a,041$b,041$h");
    raw.put(ShelfReadyFieldsBooks.TAG050, "050$a,050$b");
    raw.put(ShelfReadyFieldsBooks.TAG082, "082$a,082$2");
    raw.put(ShelfReadyFieldsBooks.TAG1XX, "100$a,110$a,111$a,130$a");
    raw.put(ShelfReadyFieldsBooks.TAG240, "240$a,240$l,240$n,240$p");
    raw.put(ShelfReadyFieldsBooks.TAG245, "245$a,245$b,245$n,245$p,245$c");
    raw.put(ShelfReadyFieldsBooks.TAG246, "246$a,246$b,246$n,246$p");
    raw.put(ShelfReadyFieldsBooks.TAG250, "250$a,250$b");
    raw.put(ShelfReadyFieldsBooks.TAG264, "264$a,264$b,264$c");
    raw.put(ShelfReadyFieldsBooks.TAG300, "300$a,300$b,300$c");
    raw.put(ShelfReadyFieldsBooks.TAG336, "336$a,336$b,336$2");
    raw.put(ShelfReadyFieldsBooks.TAG337, "337$a,337$b,337$2");
    raw.put(ShelfReadyFieldsBooks.TAG338, "338$a,338$b,338$2");
    raw.put(ShelfReadyFieldsBooks.TAG490, "490$a,490$v");
    raw.put(ShelfReadyFieldsBooks.TAG500, "500$a");
    raw.put(ShelfReadyFieldsBooks.TAG504, "504$a");
    raw.put(ShelfReadyFieldsBooks.TAG505, "505$a,505$t,505$r");
    raw.put(ShelfReadyFieldsBooks.TAG520, "520$a");
    raw.put(ShelfReadyFieldsBooks.TAG546, "546$a");
    raw.put(ShelfReadyFieldsBooks.TAG588, "588$a");
    raw.put(ShelfReadyFieldsBooks.TAG6XX, "600$a,610$a,611$a,630$a,647$a,648$a,650$a,651$a,653$a,654$a,655$a,656$a,657$a,658$a,662$a");
    raw.put(ShelfReadyFieldsBooks.TAG7XX, "700$a,710$a,711$a,720$a,730$a,740$a,751$a,752$a,753$a,754$a");
    raw.put(ShelfReadyFieldsBooks.TAG776, "776$a");
    raw.put(ShelfReadyFieldsBooks.TAG856, "856$u");
    raw.put(ShelfReadyFieldsBooks.TAG8XX, "800$a,810$a,811$a,830$a");
  }
}
