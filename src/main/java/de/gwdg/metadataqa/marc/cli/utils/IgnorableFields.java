package de.gwdg.metadataqa.marc.cli.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class IgnorableFields {
  private List<String> fields;
  private List<Pattern> maskedFields;

  public void parseFields(String raw) {
    if (StringUtils.isNotBlank(raw)) {
      fields = new ArrayList<>();
      maskedFields = new ArrayList<>();
      List<String> candidates = Arrays.asList(raw.split(","));
      for (String candidate : candidates) {
        if (candidate.contains("."))
          maskedFields.add(Pattern.compile(candidate));
        else
          fields.add(candidate);
      }
    }
  }

  public boolean isEmpty() {
    return (fields == null || fields.isEmpty()) && (maskedFields == null || maskedFields.isEmpty());
  }

  public List<String> getFields() {
    return fields;
  }

  public boolean contains(String tag) {
    if (isEmpty())
      return false;
    if (fields.contains(tag))
      return true;
    if (!maskedFields.isEmpty()) {
      for (Pattern pattern : maskedFields) {
        if (pattern.matcher(tag).matches()) {
          fields.add(tag); // caching known fields
          return true;
        }
      }
    }

    return false;
  }

  public String toString() {
    if (isEmpty())
      return "";
    String value = "";
    if (!fields.isEmpty())
      value += StringUtils.join(fields, ", ");
    if (!maskedFields.isEmpty()) {
      value += value.equals("") ? "" : ", ";
      value += "masked fields: " + StringUtils.join(maskedFields, ", ");
    }
    return value;
  }
}
