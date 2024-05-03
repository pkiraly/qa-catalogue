package de.gwdg.metadataqa.marc.definition.general.indexer.subject;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.cli.Completeness;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Used to extract the key and values from the entry subfield ($a) of the data field.
 */
public class SubjectIndexExtractor {
  private final DataField dataField;
  private final DataFieldKeyGenerator keyGenerator;
  private final String schemaAbbreviation;
  private String key;
  private List<String> values;
  private boolean success = false;
  private static final Logger logger = Logger.getLogger(Completeness.class.getCanonicalName());

  /**
   * Creates a new instance of the SubjectIndexExtractor.
   * @param dataField The data field to extract the key and values from.
   * @param keyGenerator The key generator to use. It is used all across the application to generate keys needed for
   *                     indexing.
   * @param schemaAbbreviation The abbreviation of the source schema.
   */
  public SubjectIndexExtractor(DataField dataField,
                               DataFieldKeyGenerator keyGenerator,
                               String schemaAbbreviation) {
    this.dataField = dataField;
    this.keyGenerator = keyGenerator;
    this.schemaAbbreviation = schemaAbbreviation;
  }

  public String getKey() {
    return key;
  }

  public List<String> getValues() {
    return values;
  }

  /**
   * Extracts the key and values from the data field.
   * The key consists of two parts separated by an underscore:
   * <ul>
   *   <li>Entry subfield ($a) key which is generated using the key generator provided in the constructor.</li>
   *   <li>Schema abbreviation solarized using the Utils.solarize method.</li>
   * </ul>
   */
  public void extract() {
    List<MarcSubfield> entrySubfields = dataField.getSubfield("a");

    if (entrySubfields == null || entrySubfields.isEmpty()) {
      success = false;
      if (!dataField.getDefinition().getTag().equals("852")) {
        logger.warning(() -> "No subfield $a in the field: " + dataField);
      }
      return;
    }

    key = keyGenerator.forSubfield(entrySubfields.get(0)) + "_" + Utils.solarize(schemaAbbreviation);

    values = new ArrayList<>();
    for (MarcSubfield subfield : entrySubfields) {
      addUniqueValues(values, subfield);
    }
    success = true;
  }

  public boolean hadSuccess() {
    return success;
  }

  protected void addUniqueValues(List<String> values, MarcSubfield subfield) {
    String value = subfield.resolve();
    if (!values.contains(value)) {
      values.add(value);
    }
  }
}

