package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.definition.general.indexer.FieldIndexer;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPath;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PicaGroupIndexer implements FieldIndexer {
  private static final Logger logger = Logger.getLogger(PicaGroupIndexer.class.getCanonicalName());
  private PicaPath selector;

  @Override
  public Map<String, List<String>> index(DataField dataField, DataFieldKeyGenerator keyGenerator) {
    Map<String, List<String>> indexEntries = new HashMap<>();
    for (String code : selector.getSubfields().getCodes()) {
      List<MarcSubfield> subfields = dataField.getSubfield(code);
      if (subfields != null) {
        for (MarcSubfield subfield : subfields) {
          String key = subfield.getDefinition() != null
            ? keyGenerator.forSubfieldDefinition(subfield.getDefinition())
            : keyGenerator.forSubfield(subfield);
          if (subfield.getValue() != null) {
            List<String> indexTerms = Arrays.asList(subfield.getValue().split(","));
            if (!indexTerms.isEmpty()) {
              if (indexTerms.contains(key))
                indexEntries.get(key).addAll(indexTerms);
              else
                indexEntries.put(key, indexTerms);
            }
          }
        }
      }
    }
    return indexEntries;
  }

  public PicaGroupIndexer setPicaPath(PicaPath picaPath) {
    this.selector = picaPath;
    return this;
  }
}
