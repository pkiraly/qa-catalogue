package de.gwdg.metadataqa.marc.utils.keygenerator;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.regex.Pattern;

public abstract class DataFieldKeyGenerator {
  public static final Pattern nonValidSubfieldCode = Pattern.compile("[^0-9a-zA-Z]");
  protected final DataFieldDefinition definition;
  protected final String tag;
  protected final String indexTag;
  protected SchemaType schemaType;
  private MarcVersion marcVersion;

  protected DataFieldKeyGenerator(DataFieldDefinition definition) {
    this.definition = definition;
    tag = definition.getTag();
    indexTag = definition.getIndexTag();
  }

  protected DataFieldKeyGenerator(DataFieldDefinition definition,
                               String tag,
                               SchemaType schemaType) {
    this.definition = definition;
    this.schemaType = schemaType;
    if (schemaType.equals(SchemaType.PICA)) {
      this.tag = tag;
      this.indexTag = escape(tag);
      return;
    }

    if (definition != null) {
      this.tag = definition.getTag();
      this.indexTag = definition.getIndexTag();
      return;
    }

    this.tag = tag;
    this.indexTag = tag;
  }

  /**
   * Escapes characters in the tag so that none are matched by the nonValidSubfieldCode pattern. Such characters are
   * replaced by an underscore.
   * @param tag The tag to escape.
   * @return The escaped tag. For example: "%$a" -> "__a".
   */
  public static String escape(String tag) {
    return tag.replaceAll(nonValidSubfieldCode.pattern(), "_");
  }

  public abstract String forInd1();

  public abstract String forInd2();

  public String forSubfield(MarcSubfield subfield) {
    String code = subfield.getCode();
    SubfieldDefinition versionSpecificDefinition = null;
    if (definition != null) {
      versionSpecificDefinition = definition.getVersionSpecificDefinition(marcVersion, code);
    }

    String codeForIndex = versionSpecificDefinition != null
      ? versionSpecificDefinition.getCodeForIndex()
      : subfield.getCodeForIndex();

    String key = getKeyForSubfield(code, codeForIndex);

    return addVersionSpecifier(versionSpecificDefinition, key);
  }

  public String forSubfieldDefinition(SubfieldDefinition subfieldDefinition) {
    String key = getKeyForSubfield(subfieldDefinition.getCode(), subfieldDefinition.getCodeForIndex());
    return addVersionSpecifier(subfieldDefinition, key);
  }

  protected abstract String addVersionSpecifier(SubfieldDefinition versionSpecificDefinition, String key);

  /**
   * Generate a key for the given subfield code and index code. Depending on the fieldType chosen, either the code or
   * the index code is used.
   * @param code The subfield code.
   * @param codeForIndex The index code of the subfield.
   * @return The key for the subfield.
   */
  protected abstract String getKeyForSubfield(String code, String codeForIndex);

  public String getIndexTag() {
    return indexTag;
  }

  /**
   * Generates a key for the entire (full) field. That means that the key is essentially the same as the tag in the
   * given representation (HUMAN, MIXED, MARC), with an additional "_full" suffix.
   */
  public abstract String forEntireField();

  public MarcVersion getMarcVersion() {
    return marcVersion;
  }

  public void setMarcVersion(MarcVersion marcVersion) {
    this.marcVersion = marcVersion;
  }

  public String getTag() {
    return tag;
  }
}
