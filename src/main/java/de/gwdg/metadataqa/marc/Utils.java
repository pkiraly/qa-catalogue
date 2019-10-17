package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Utils {

  private Utils() {
  }

  public static List<Code> generateCodes(String... input) {
    if (input.length % 2 != 0) {
      throw new IllegalArgumentException("Number of input should be even");
    }
    List<Code> codes = new ArrayList<>();
    for (int i = 0, len = input.length; i < len; i += 2) {
      codes.add(new Code(input[i], input[i + 1]));
    }
    return codes;
  }

  public static List<ControlSubfieldDefinition> generateControlSubfieldList(ControlSubfieldDefinition... input) {
    List<ControlSubfieldDefinition> list = new ArrayList<>();
    list.addAll(Arrays.asList(input));
    return list;
  }

  public static String extractPackageName(DataField field) {
    return extractPackageName(field.getDefinition());
  }

  public static String extractPackageName(DataFieldDefinition field) {
    String packageName = field.getClass().getPackage().getName()
      .replace("de.gwdg.metadataqa.marc.definition.tags.", "");
    return packageName;
  }

  public static List<Object> quote(List<? extends Serializable> values) {
    List<Object> quoted = new ArrayList<>();
    for (Serializable value : values) {
      quoted.add(Utils.quote(value));
    }
    return quoted;
  }

  public static Object quote(Object value) {
    if (value instanceof String) {
      return '"' + ((String) value).replace("\\", "\\\\") + '"';
    }
    return value;
  }

  public static <T extends Object> void count(T key, Map<T, Integer> counter) {
    if (!counter.containsKey(key)) {
      counter.put(key, 0);
    }
    counter.put(key, counter.get(key) + 1);
  }

  public static String solarize(String abbreviation) {
    abbreviation = StringUtils.stripAccents(abbreviation);
    abbreviation = abbreviation.replaceAll("\\W", "_").toLowerCase();
    return abbreviation;
  }
}
