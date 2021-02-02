package de.gwdg.metadataqa.marc.cli.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class IgnorableFields {
  private List<String> fields;

  public void parseFields(String raw) {
    if (StringUtils.isNotBlank(raw))
      fields = Arrays.asList(raw.split(","));
  }

  public boolean isEmpty() {
    return fields == null || fields.isEmpty();
  }

  public List<String> getFields() {
    return fields;
  }

  public boolean contains(String tag) {
    return !isEmpty() && fields.contains(tag);
  }

  public String toString() {
    return (isEmpty() ? "" : StringUtils.join(fields, ", "));
  }
}
