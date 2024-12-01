package de.gwdg.metadataqa.marc.analysis.functional;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.UnimarcRecord;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.utils.FunctionValue;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Analyzes a UNIMARC record and counts the FRBR functions. In other words, it counts how many FRBR user tasks are
 * supported by the provided record.
 */
public class UnimarcFunctionalAnalyzer extends FunctionalAnalyzer {

  private static final Logger logger = Logger.getLogger(UnimarcFunctionalAnalyzer.class.getCanonicalName());

  public UnimarcFunctionalAnalyzer(FrbrFunctionLister frbrFunctionLister) {
    super(frbrFunctionLister);
  }

  @Override
  protected void analyzeRecord(BibliographicRecord bibliographicRecord) {

    if (!(bibliographicRecord instanceof UnimarcRecord)) {
      logger.severe("The provided record is not a UNIMARC record. The analysis will not be performed.");
      return;
    }

    UnimarcRecord unimarcRecord = (UnimarcRecord) bibliographicRecord;

    Map<DataFieldDefinition, Boolean> cache = new HashMap<>();

    // Count functions for the data fields
    countDataFields(recordCounter, unimarcRecord.getDatafields(), cache);

  }

  @Override
  protected void countDataField(DataFieldDefinition definition,
                                DataField dataField,
                                Map<FRBRFunction, FunctionValue> recordCounter) {

    for (MarcSubfield subfield : dataField.getSubfields()) {
      String key = dataField.getTag() + "$" + subfield.getCode();
      if (frbrFunctionLister.getFunctionByPath().containsKey(key)) {
        countFunctions(frbrFunctionLister.getFunctionByPath().get(key), recordCounter);
      }
    }
  }
}
