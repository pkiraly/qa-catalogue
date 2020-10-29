package de.gwdg.metadataqa.marc.model.kos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KosRegistry {
  private static List<Kos> entries = new ArrayList<>();
  private static Map<String, Kos> index = new HashMap<>();

  public static Kos get(String abbreviation) {
    return index.getOrDefault(abbreviation, null);
  }

  static {
    add("udc", 496, KosType.CLASSIFICATION_SCHEME);
    add("ddc", 241, KosType.CLASSIFICATION_SCHEME);
    add("lcsh0", 454, KosType.SUBJECT_HEADING);
    add("lcsh", 454, KosType.SUBJECT_HEADING);
    add("fast", 707, KosType.SUBJECT_HEADING);
    add("gnd", 707, KosType.SUBJECT_HEADING);
    add("lcc", 486, KosType.CLASSIFICATION_SCHEME);
    add("vsiso", 727, KosType.CLASSIFICATION_SCHEME);
    add("bcl", 745, KosType.CLASSIFICATION_SCHEME);
    add("rvk", 533, KosType.CLASSIFICATION_SCHEME);
    add("mesh", 391, KosType.SUBJECT_HEADING);
    add("gtt", 18687, KosType.THESAURUS);
    add("agrovoc", 305, KosType.THESAURUS);
    add("rvm", 1036, KosType.THESAURUS);
    add("ram", 18, KosType.SUBJECT_HEADING);
    add("bisacsh", 961, KosType.CLASSIFICATION_SCHEME);
    add("bkl", 18785, KosType.CLASSIFICATION_SCHEME);
    add("msc", 474, KosType.CLASSIFICATION_SCHEME);
    add("ssgn", 18928, KosType.CLASSIFICATION_SCHEME);
    add("kssb", 672, KosType.CLASSIFICATION_SCHEME);
    add("sao", 1756, KosType.SUBJECT_HEADING);
    add("rasuqam", 1410, KosType.THESAURUS);
    add("lcac", 18537, KosType.SUBJECT_HEADING);
  }

  private static void add(String abbreviation, int bartocId, KosType type) {
    Kos kos = new Kos(abbreviation, bartocId, type);
    entries.add(kos);
    index.put(kos.getAbbreviation(), kos);
  }

}
