package de.gwdg.metadataqa.marc.dao.record;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.analysis.contextual.authority.AuthorityCategory;
import de.gwdg.metadataqa.marc.analysis.shelfready.ShelfReadyFieldsBooks;
import de.gwdg.metadataqa.marc.dao.Control008;
import de.gwdg.metadataqa.marc.dao.MarcPositionalControlField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Marc21BibliographicRecord extends Marc21Record {

  /**
   * Key-value pairs of ShelfReadyFieldsBooks (the category) and a map of tags and its subfields.
   */
  private static Map<ShelfReadyFieldsBooks, Map<String, List<String>>> shelfReadyMap;

  public Marc21BibliographicRecord() {
    super();
  }

  public Marc21BibliographicRecord(String id) {
    super(id);
  }

  @Override
  public Control008 getControl008() {
    return (Control008) this.control008;
  }

  @Override
  public void setControl008(MarcPositionalControlField control008) {
    this.control008 = control008;
    control008.setMarcRecord(this);
    controlfieldIndex.put(control008.getDefinition().getTag(), Arrays.asList(control008));
  }

  protected void initializeAuthorityTags() {
    subjectTagIndex = Utils.listToMap(MARC21_SUBJECT_TAGS);
    skippableSubjectSubfields = new HashMap<>();

    authorityTagsMap = new EnumMap<>(AuthorityCategory.class);
    authorityTagsMap.put(AuthorityCategory.PERSONAL, List.of("100", "700", "800"));
    authorityTagsMap.put(AuthorityCategory.CORPORATE, List.of("110", "710", "810"));
    authorityTagsMap.put(AuthorityCategory.MEETING, List.of("111", "711", "811"));
    authorityTagsMap.put(AuthorityCategory.GEOGRAPHIC, List.of("751", "752"));
    authorityTagsMap.put(AuthorityCategory.TITLES, List.of("130", "730", "740", "830"));
    authorityTagsMap.put(AuthorityCategory.OTHER, List.of("720", "753", "754"));

    authorityTags = authorityTagsMap.values().stream().flatMap(List::stream).collect(Collectors.toList());
    authorityTagsIndex = Utils.listToMap(authorityTags);
    skippableAuthoritySubfields = new HashMap<>();
  }

  @Override
  public Map<ShelfReadyFieldsBooks, Map<String, List<String>>> getShelfReadyMap() {
    if (shelfReadyMap == null) {
      initializeShelfReadyMap();
    }

    return shelfReadyMap;
  }

  /**
   * Initialize the shelf ready map. This is a map of ShelfReadyFieldsBooks (the category) and a map of tags and its
   * subfields.
   * In case the field path doesn't have a subfield specified, the subfield list is empty.
   * E.g. "LDR~06" = "LDR~06" -> []
   * Otherwise, the subfield list contains the subfield codes.
   * E.g. "600$a"  "600" -> ["a"]
   */
  private static void initializeShelfReadyMap() {
    shelfReadyMap = new LinkedHashMap<>();

    for (Map.Entry<ShelfReadyFieldsBooks, String> entry : getRawShelfReadyMap().entrySet()) {
      shelfReadyMap.put(entry.getKey(), new TreeMap<>());

      // Split the raw path comma-separated list into individual paths
      String[] paths = entry.getValue().split(",");

      for (String path : paths) {
        if (!path.contains("$")) {
          shelfReadyMap.get(entry.getKey()).put(path, new ArrayList<>());
          continue;
        }

        // If the path contains a $ (has a subfield specified), split it into the tag and subfield
        String[] parts = path.split("\\$");

        // If the map doesn't contain the tag, add it, with an empty list of subfields
        if (!shelfReadyMap.get(entry.getKey()).containsKey(parts[0])) {
          shelfReadyMap.get(entry.getKey()).put(parts[0], new ArrayList<>());
        }

        // Add the subfield to the list of subfields for the tag
        shelfReadyMap.get(entry.getKey()).get(parts[0]).add(parts[1]);
      }
    }
  }

  public static Map<ShelfReadyFieldsBooks, String> getRawShelfReadyMap() {
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

    return raw;
  }
}
