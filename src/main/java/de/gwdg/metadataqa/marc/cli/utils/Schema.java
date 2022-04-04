package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.marc.model.kos.Kos;
import de.gwdg.metadataqa.marc.model.kos.KosRegistry;
import de.gwdg.metadataqa.marc.model.kos.KosType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.Map;

public class Schema {

  private static int centralCounter = 0;
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
    schemaCounter.computeIfAbsent(this, s -> ++centralCounter);
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

  public Kos getKos() {
    return KosRegistry.get(abbreviation);
  }

  public KosType getType() {
    Kos kos = getKos();
    if (kos == null)
      return null;
    return kos.getType();
  }

  public int getScore() {
    KosType type = getType();
    if (type == null)
      return 0;
    return type.getScore();
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

  public static void reset() {
    centralCounter = 0;
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
