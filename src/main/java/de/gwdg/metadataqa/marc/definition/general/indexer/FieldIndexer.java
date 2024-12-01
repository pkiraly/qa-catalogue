package de.gwdg.metadataqa.marc.definition.general.indexer;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;

import java.util.List;
import java.util.Map;

public interface FieldIndexer {
  /**
   * Indexes the given data field by extracting the entry subfields along with schema sources. The schema source is
   * determined by the implementation.
   * @param dataField The data field to be indexed.
   * @param keyGenerator The key generator to be used to generate the index key. Used primarily for creating the
   *                     key for the entry subfield.
   * @return A map containing the index key and the list of values extracted from the entry subfield.
   */
  Map<String, List<String>> index(DataField dataField, DataFieldKeyGenerator keyGenerator);
}
