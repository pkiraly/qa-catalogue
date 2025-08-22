package de.gwdg.metadataqa.marc.utils.pica.reader;

import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaManager;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaReader;
import org.marc4j.MarcReader;

import java.util.regex.Pattern;

public abstract class PicaReader implements MarcReader {

  protected String idField = "003@$0";
  protected String subfieldSeparator = "$";
  protected String idTag = "003@";
  protected String idCode = "0";
  protected boolean parsed = false;

  protected PicaSchemaManager schema = PicaSchemaReader.createSchemaManager(null);

  public void parseIdField() {
    String[] parts = idField.split(Pattern.quote(subfieldSeparator));
    idTag = parts[0];
    idCode = parts[1];
    parsed = true;
  }

  public PicaReader setIdField(String idField) {
    this.idField = idField;
    return this;
  }

  public PicaReader setSubfieldSeparator(String subfieldSeparator) {
    this.subfieldSeparator = subfieldSeparator;
    return this;
  }

  public PicaReader setIdCode(String idCode) {
    this.idCode = idCode;
    return this;
  }

  public String getIdField() {
    return idField;
  }

  public String getSubfieldSeparator() {
    return subfieldSeparator;
  }

  public String getIdTag() {
    return idTag;
  }

  public String getIdCode() {
    return idCode;
  }
}
