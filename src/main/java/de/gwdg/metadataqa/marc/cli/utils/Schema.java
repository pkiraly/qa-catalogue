package de.gwdg.metadataqa.marc.cli.utils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.Map;

public class Schema {

  private static int SCHEMA_COUNTER = 0;
  private static Map<Schema, Integer> schemaCounter = new HashMap<>();

  int id;
  String field;
  String location;
  String schema;
  String abbreviation;

  public Schema(String field, String location, String schema) {
    this.field = field;
    this.location = location;
    this.schema = schema;
    setId();
  }

  public Schema(String field, String location, String abbreviation, String schema) {
    this(field, location, schema);
    this.abbreviation = abbreviation;
  }

  private void setId() {
    if (!schemaCounter.containsKey(this)) {
      schemaCounter.put(this, ++SCHEMA_COUNTER);
    }
    this.id = schemaCounter.get(this);
  }

  public String getField() {
    return field;
  }

  public String getLocation() {
    return location;
  }

  public int getId() {
    return id;
  }

  public String getSchema() {
    return schema;
  }

  public String getAbbreviation() {
    return abbreviation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    Schema schema1 = (Schema) o;

    return new EqualsBuilder()
      .append(field, schema1.field)
      .append(location, schema1.location)
      .append(schema, schema1.schema)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .append(field)
      .append(location)
      .append(schema)
      .toHashCode();
  }

  @Override
  public String toString() {
    return "Schema{" +
      "id=" + id +
      ", field='" + field + '\'' +
      ", location='" + location + '\'' +
      ", schema='" + schema + '\'' +
      ", abbreviation='" + abbreviation + '\'' +
      '}';
  }
}
