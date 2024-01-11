package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.utils.MarcTagLister;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagDefinitionLoader {

  private static final Logger logger = Logger.getLogger(TagDefinitionLoader.class.getCanonicalName());

  private static final Pattern PATTERN_20x = Pattern.compile("^2[0-4]\\d$");
  private static final Pattern PATTERN_25x = Pattern.compile("^2[5-9]\\d$");
  private static final Pattern PATTERN_70x = Pattern.compile("^7[0-5]\\d$");
  private static final Pattern PATTERN_76x = Pattern.compile("^7[6-9]\\d$");
  private static final Pattern PATTERN_80x = Pattern.compile("^8[0-3]\\d$");
  private static final Pattern PATTERN_84x = Pattern.compile("^8[4-9]\\d$");

  private static final List<String> OCLC_TAGS = Arrays.asList(
    "012", "019", "029", "090", "092", "096", "366", "539", "891", "911",
    "912", "936", "938", "994"
  );
  private static Map<String, DataFieldDefinition> commonCache = new HashMap<>();
  private static Map<String, Map<MarcVersion, DataFieldDefinition>> versionedCache = new HashMap<>();

  static {
    findAndCacheTags();
  }

  private static void findAndCacheTags() {
    List<Class<? extends DataFieldDefinition>> tags = MarcTagLister.listTags();
    for (Class<? extends DataFieldDefinition> definitionClazz : tags) {
      loadAndCacheTag(definitionClazz);
    }
  }

  private static void loadAndCacheTag(Class<? extends DataFieldDefinition> definitionClazz) {
    DataFieldDefinition dataFieldDefinition = null;
    Method getInstance = null;
    try {
      getInstance = definitionClazz.getMethod("getInstance");
      dataFieldDefinition = (DataFieldDefinition) getInstance.invoke(definitionClazz);
      if (dataFieldDefinition != null) {
        String tag = dataFieldDefinition.getTag();
        commonCache.put(tag, dataFieldDefinition);
        MarcVersion version = Utils.getVersion(definitionClazz);
        versionedCache.computeIfAbsent(tag, s -> new EnumMap<>(MarcVersion.class));
        versionedCache.get(tag).put(version, dataFieldDefinition);
      }
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      logger.log(Level.SEVERE, "loadAndCacheTag", e);
    }
  }

  public static DataFieldDefinition load(String tag) {
    return load(tag, MarcVersion.MARC21);
  }

  /**
   * Load tag definition for the given tag and MARC version. This is due to all MARC21 tags being
   * defined in code, instead of being loaded from a file.
   * @param tag Tag to load, e.g. 245
   * @param marcVersion MARC version for which to load the tag definition, since some tags are
   *                    defined differently in different MARC versions.
   * @return Tag definition, or null if not found.
   */
  public static DataFieldDefinition load(String tag, MarcVersion marcVersion) {
    Map<MarcVersion, DataFieldDefinition> map = versionedCache.get(tag);

    if (map == null)
      return null;

    if (map.containsKey(marcVersion))
      return map.get(marcVersion);

    if (marcVersion.equals(MarcVersion.MARC21)) {
      // no fallback for MARC21
      return null;
    } else {
      // fallbacks for other MARC versions
      if (map.containsKey(MarcVersion.MARC21))
        return map.get(MarcVersion.MARC21);
      if (map.containsKey(MarcVersion.OCLC))
        return map.get(MarcVersion.OCLC);
    }

    return null;
  }

  public static List<DataFieldDefinition> findPatterns(String tagPattern, MarcVersion marcVersion) {
    Matcher matcher = Pattern.compile("^" + tagPattern.replaceAll("X", ".") + "$").matcher("");
    List<DataFieldDefinition> definitions = new ArrayList<>();
    for (Map.Entry<String, Map<MarcVersion, DataFieldDefinition>> entry : versionedCache.entrySet()) {
      if (matcher.reset(entry.getKey()).matches()) {
        Map<MarcVersion, DataFieldDefinition> map = entry.getValue();

        if (map == null)
          continue;

        if (map.containsKey(marcVersion))
          definitions.add(map.get(marcVersion));
        else {
            // fallbacks for other MARC versions
          if (map.containsKey(MarcVersion.MARC21))
            definitions.add(map.get(MarcVersion.MARC21));
          if (map.containsKey(MarcVersion.OCLC))
            definitions.add(map.get(MarcVersion.OCLC));
        }
      }
    }

    return definitions;
  }

  public static String getClassName(String tag) {
    String packageName = null;
    if (OCLC_TAGS.contains(tag)) {
      packageName = "oclctags";
    } else if (tag.startsWith("0")) {
      packageName = "tags01x";
    } else if (tag.startsWith("1")) {
      packageName = "tags1xx";
    } else if (PATTERN_20x.matcher(tag).matches()) {
      packageName = "tags20x";
    } else if (PATTERN_25x.matcher(tag).matches()) {
      packageName = "tags25x";
    } else if (tag.startsWith("3")) {
      packageName = "tags3xx";
    } else if (tag.startsWith("4")) {
      packageName = "tags4xx";
    } else if (tag.startsWith("5")) {
      packageName = "tags5xx";
    } else if (tag.startsWith("6")) {
      packageName = "tags6xx";
    } else if (PATTERN_70x.matcher(tag).matches()) {
      packageName = "tags70x";
    } else if (PATTERN_76x.matcher(tag).matches()) {
      packageName = "tags76x";
    } else if (PATTERN_80x.matcher(tag).matches()) {
      packageName = "tags80x";
    } else if (PATTERN_84x.matcher(tag).matches()) {
      packageName = "tags84x";
    }

    if (packageName == null)
      return null;

    return String.format("de.gwdg.metadataqa.marc.definition.tags.%s.Tag%s", packageName, tag);
  }
}
