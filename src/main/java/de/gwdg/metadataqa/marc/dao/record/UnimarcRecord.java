package de.gwdg.metadataqa.marc.dao.record;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.analysis.contextual.authority.AuthorityCategory;
import de.gwdg.metadataqa.marc.analysis.shelfready.ShelfReadyFieldsBooks;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class UnimarcRecord extends MarcRecord {
  protected static final List<String> UNIMARC_SUBJECT_TAGS = Arrays.asList("600", "601", "602", "604", "605", "606",
    "607", "608", "610", "615", "616", "617", "620", "621", "623", "626", "631", "632", "660", "661", "670", "675",
    "676", "680", "686");

  protected static final List<String> allowedControlFieldTags = Arrays.asList("001", "003", "005");
  private static Map<ShelfReadyFieldsBooks, Map<String, List<String>>> shelfReadyMap;

  public UnimarcRecord() {
    super();
    schemaType = SchemaType.UNIMARC;
  }

  public UnimarcRecord(String id) {
    super(id);
    schemaType = SchemaType.UNIMARC;
  }

  private static void initializeShelfReadyMap() {
    shelfReadyMap = new LinkedHashMap<>();

    for (Map.Entry<ShelfReadyFieldsBooks, String> entry : getRawShelfReadyMap().entrySet()) {
      shelfReadyMap.put(entry.getKey(), new TreeMap<>());

      String rawSelectors = entry.getValue();

      if (rawSelectors == null) {
        continue;
      }

      // Split the raw path comma-separated list into individual paths
      String[] paths = rawSelectors.split(",");

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
    // Previous leader fields are quite similar to MARC21

    raw.put(ShelfReadyFieldsBooks.TAG00600, null); // Seems like this would be 106$a==s (which is deprecated),
    // or 182$a~00==b
    raw.put(ShelfReadyFieldsBooks.TAG010, null); // Not there
    raw.put(ShelfReadyFieldsBooks.TAG015, "015$a,015$b,015$c"); // 015 -> 020 | $a -> $b or $c, $2 -> $a
    raw.put(ShelfReadyFieldsBooks.TAG020, "020$a,020$z,020$b"); // 020 -> 010 | $a -> $a, $z -> $z, $q -> $b
    raw.put(ShelfReadyFieldsBooks.TAG035, "035$a,035$z"); // Same
    raw.put(ShelfReadyFieldsBooks.TAG040, "801"); // It seems that the entire field 801 replaces separate subfields for 040
    raw.put(ShelfReadyFieldsBooks.TAG041, "041$a,041$d,041$h"); // 041 -> 101 | $a -> $a, $b -> $d, $h -> $h
    raw.put(ShelfReadyFieldsBooks.TAG050, "680$a,680b"); // 050 -> 680 | $a -> $a, $b -> $b
    raw.put(ShelfReadyFieldsBooks.TAG082, "676$a,676$v"); // 082 -> 676 | $a -> $a, $2 -> $v
    raw.put(ShelfReadyFieldsBooks.TAG1XX, "700$a,710$a"); // 100 -> 700, 110 and 111 -> 710, ?130 -> 500? but also 240 -> 500
    raw.put(ShelfReadyFieldsBooks.TAG240, "500$a,500$m,500$h,500$i"); // as said 240 -> 500 | $a -> $a, $l -> $m, $n -> $h, $p -> $i
    raw.put(ShelfReadyFieldsBooks.TAG245, "200$a,200$d,200$h,200$i,200$f"); // 245 -> 200 | $a -> $a, $b -> $d, $n -> $h, $p -> $i, $c -> $f
    raw.put(ShelfReadyFieldsBooks.TAG246, "510$a,510$e,510$h,510$i"); // 246 -> 510 or 512, 513, 514, 515, 516, 517 as well. But 510 will be used only
    raw.put(ShelfReadyFieldsBooks.TAG250, "205$a"); // 250 -> 205
    raw.put(ShelfReadyFieldsBooks.TAG264, "214$a,214$c,214$d"); // 264 -> 214 | $a -> $a, $b -> $c, $c -> $d
    raw.put(ShelfReadyFieldsBooks.TAG300, "215$a,215$c,215$d"); // 300 -> 215 | $a -> $a, $b -> $c, $c -> $d
    raw.put(ShelfReadyFieldsBooks.TAG336, "181$a"); // Could have to do with 181, but I'm not sure
    raw.put(ShelfReadyFieldsBooks.TAG337, "182$a"); // Seems to be 182, but I can only identify $b -> $a
    raw.put(ShelfReadyFieldsBooks.TAG338, "183$a"); // Analogous to 181 and 182
    raw.put(ShelfReadyFieldsBooks.TAG490, "225$a,225$v"); // 490 -> 225 | $a -> $a, $v -> $v
    raw.put(ShelfReadyFieldsBooks.TAG500, "300$a"); // 500 -> 300 | $a -> $a
    raw.put(ShelfReadyFieldsBooks.TAG504, "320$a"); // 504 -> 320 | $a -> $a
    raw.put(ShelfReadyFieldsBooks.TAG505, "327$a,327$b"); // 505 -> 327 | $a -> $a, $t -> $b, $r -> /
    raw.put(ShelfReadyFieldsBooks.TAG520, "330$a"); // 520 -> 330 | $a -> $a
    raw.put(ShelfReadyFieldsBooks.TAG546, null); // Not sure
    raw.put(ShelfReadyFieldsBooks.TAG588, null); // Not sure
    // 600 -> 600; 610 or 611 -> 601; 605 -> 630; 647 -> /; 648 -> /; 650 -> 606; 651 -> 607; 653 -> 610; 654 -> /; 655 -> 608; 656 -> 631; 657 -> 632; 658 -> /; 662 -> 617
    raw.put(ShelfReadyFieldsBooks.TAG6XX, "600$a,601$a,630$a,606$a,607$a,610$a,608$a,631$a,632$a,617$a");
    // 700 -> 701, 702, 721, 722; 710 or 711 -> 711, 712; 720 -> /; 730 -> 730; 740 -> /; 751 -> /; 752 -> 620; 753 -> / because 626 deprecated in favor of 337; 754 -> /
    raw.put(ShelfReadyFieldsBooks.TAG7XX, "701$a,702$a,721$a,722$a,711$a,712$a,730$a,620$a");
    raw.put(ShelfReadyFieldsBooks.TAG776, "452$a"); // 776 -> 452
    raw.put(ShelfReadyFieldsBooks.TAG856, "856$u"); // 856 -> 856 | $u -> $u
    // 800, 810, 811 and 830 could all be what's in 410, so I'm going to add 410$a only
    raw.put(ShelfReadyFieldsBooks.TAG8XX, "410$a");

    return raw;
  }

  @Override
  public List<String> getAllowedControlFieldTags() {
    return allowedControlFieldTags;
  }

  public Map<ShelfReadyFieldsBooks, Map<String, List<String>>> getShelfReadyMap() {
    if (shelfReadyMap == null)
      initializeShelfReadyMap();

    return shelfReadyMap;
  }

  protected List<String> getSubjectTags() {
    return UNIMARC_SUBJECT_TAGS;
  }


  @Override
  protected void initializeAuthorityTags() {

    skippableAuthoritySubfields = new HashMap<>();

    subjectTagIndex = Utils.listToMap(UNIMARC_SUBJECT_TAGS);
    skippableSubjectSubfields = new HashMap<>();

    authorityTagsMap = new EnumMap<>(AuthorityCategory.class);
    authorityTagsMap.put(AuthorityCategory.PERSONAL, List.of("700", "701", "702"));
    authorityTagsMap.put(AuthorityCategory.CORPORATE, List.of("710$ind1=0", "711$ind1=0", "712$ind1=0"));
    authorityTagsMap.put(AuthorityCategory.MEETING, List.of("710$ind1=1", "711$ind1=1", "712$ind1=1"));
    authorityTagsMap.put(AuthorityCategory.GEOGRAPHIC, List.of("620"));
    authorityTagsMap.put(AuthorityCategory.TITLES, List.of("500", "501", "506", "507", "576", "577"));
    authorityTagsMap.put(AuthorityCategory.OTHER, List.of("730"));
    authorityTags = authorityTagsMap.values().stream().flatMap(List::stream).collect(Collectors.toList());
    // Filter out the corporate and meeting tags and then later add separate "710", "711", "712" tags
    // This is so that we can have the separate statistics of corporate/meeting bodies in the authority analysis resluts
    // but not to index them separately, because that can be accessed in a different way
    authorityTagsIndex = Utils.listToMap(authorityTags.stream().filter(tag -> !tag.contains("$")).collect(Collectors.toList()));
    authorityTagsIndex.put("710", true);
    authorityTagsIndex.put("711", true);
    authorityTagsIndex.put("712", true);
    skippableAuthoritySubfields = new HashMap<>();

  }
}
