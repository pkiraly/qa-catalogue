package de.gwdg.metadataqa.marc.dao.record;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.analysis.AuthorityCategory;
import de.gwdg.metadataqa.marc.analysis.ShelfReadyFieldsBooks;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.UnimarcLeader;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.utils.pica.crosswalk.Crosswalk;
import de.gwdg.metadataqa.marc.utils.pica.crosswalk.PicaMarcCrosswalkReader;

import java.util.ArrayList;
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
    for (Map.Entry<ShelfReadyFieldsBooks, Map<String, List<String>>> entry : (new Marc21Record()).getShelfReadyMap().entrySet()) {
      ShelfReadyFieldsBooks category = entry.getKey();
      shelfReadyMap.put(category, new HashMap<>());
      for (Map.Entry<String, List<String>> marcEntry : entry.getValue().entrySet()) {
        for (String code : marcEntry.getValue()) {
          for (Crosswalk crosswalk : PicaMarcCrosswalkReader.lookupMarc21(marcEntry.getKey() + " $" + code)) {
            if (!shelfReadyMap.get(category).containsKey(crosswalk.getPica()))
              shelfReadyMap.get(category).put(crosswalk.getPica(), new ArrayList<>());
            shelfReadyMap.get(category).get(crosswalk.getPica()).add(crosswalk.getPicaUf().replace("$", ""));
          }
        }
      }
    }
  }
}
