package de.gwdg.metadataqa.marc.analysis.functional;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.PicaRecord;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.utils.FunctionValue;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Analyzes a PICA record and counts the FRBR functions. In other words, it counts how many FRBR user tasks are
 * supported by the provided record.
 */
public class PicaFunctionalAnalyzer extends FunctionalAnalyzer {

  private static final Logger logger = Logger.getLogger(PicaFunctionalAnalyzer.class.getCanonicalName());

  public PicaFunctionalAnalyzer(FrbrFunctionLister frbrFunctionLister) {
    super(frbrFunctionLister);
  }

  @Override
  protected void analyzeRecord(BibliographicRecord bibliographicRecord) {

    if (!(bibliographicRecord instanceof PicaRecord)) {
      logger.severe("The provided record is not a PICA record. The analysis will not be performed.");
      return;
    }

    PicaRecord marc21Record = (PicaRecord) bibliographicRecord;

    Map<FRBRFunction, FunctionValue> recordCounter = initializeCounter();
    Map<DataFieldDefinition, Boolean> cache = new HashMap<>();

    // Count functions for the data fields
    countDataFields(recordCounter, marc21Record.getDatafields(), cache);

  }

  @Override
  protected void countDataField(DataFieldDefinition definition,
                                DataField dataField,
                                Map<FRBRFunction, FunctionValue> recordCounter) {
    PicaFrbrFunctionLister picaFrbrFunctionLister = (PicaFrbrFunctionLister) frbrFunctionLister;

    for (MarcSubfield subfield : dataField.getSubfields()) {
      String key = dataField.getTag() + "$" + subfield.getCode();
      if (picaFrbrFunctionLister.getFunctionByPath().containsKey(key)) {
        countFunctions(picaFrbrFunctionLister.getFunctionByPath().get(key), recordCounter);
      }
    }
  }
}
