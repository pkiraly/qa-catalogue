package de.gwdg.metadataqa.marc.analysis.functional;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcControlField;
import de.gwdg.metadataqa.marc.dao.MarcPositionalControlField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.utils.FunctionValue;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Analyzes a MARC record and counts the FRBR functions. In other words, it counts how many FRBR user tasks are
 * supported by the provided record.
 * In the case of MARC21, the functions are already defined with the definition of the fields, subfields and indicators
 * in the respective MARC21 definition classes within this project.
 */
public class Marc21FunctionalAnalyzer extends FunctionalAnalyzer {

  private static final Logger logger = Logger.getLogger(Marc21FunctionalAnalyzer.class.getCanonicalName());

  public Marc21FunctionalAnalyzer(FrbrFunctionLister frbrFunctionLister) {
    super(frbrFunctionLister);
  }

  /**
   * Analyzes a MARC21 record and counts the FRBR functions. Creates the recordCounter map and stores the counts of the
   * FRBR functions (user tasks) in it. The map is then returned.
   *
   * @param bibliographicRecord The record to be analyzed and counted.
   */
  @Override
  protected void analyzeRecord(BibliographicRecord bibliographicRecord) {

    if (!(bibliographicRecord instanceof Marc21Record)) {
      logger.severe("The provided record is not a MARC21 record. The analysis will not be performed.");
      return;
    }

    Marc21Record marc21Record = (Marc21Record) bibliographicRecord;

    Map<DataFieldDefinition, Boolean> cache = new HashMap<>();

    // Count functions for the leader
    countPositionalControlField(recordCounter, marc21Record.getLeader());

    // Count functions for the control fields
    countControlFields(recordCounter, marc21Record.getControlfields());

    // Count functions for the data fields
    countDataFields(recordCounter, marc21Record.getDatafields(), cache);

  }


  /**
   * Analyzes a positional control field (a field that is made up of positions) and counts the FRBR functions.
   * In other words, it counts how many FRBR user tasks are supported by the control values of the analyzed field
   * (e.g. for leader type of record it is the Data Management > Process task).
   * @param recordCounter The map that will store the counts of the FRBR functions.
   * @param positionalControlField The positional control field to be analyzed.
   */
  private void countPositionalControlField(Map<FRBRFunction, FunctionValue> recordCounter,
                                           MarcPositionalControlField positionalControlField) {
    for (ControlValue controlValue : positionalControlField.getValuesList()) {
      countFunctions(controlValue.getDefinition().getFrbrFunctions(), recordCounter);
    }
  }

  private void countControlFields(Map<FRBRFunction, FunctionValue> recordCounter,
                                  List<MarcControlField> controlFields) {
    for (MarcControlField controlField : controlFields) {
      if (controlField == null) {
        continue;
      }
      if (controlField instanceof MarcPositionalControlField) {
        // If it's a control field that is made up of positions, then count the functions for each position.
        countPositionalControlField(recordCounter, (MarcPositionalControlField) controlField);
      } else {
        countFunctions(controlField.getDefinition().getFrbrFunctions(), recordCounter);
      }
    }
  }

  @Override
  protected void countDataField(DataFieldDefinition definition,
                                DataField dataField,
                                Map<FRBRFunction, FunctionValue> recordCounter) {
    if (definition != null) {
      countIndicator(recordCounter, definition.getInd1(), dataField.getInd1());
      countIndicator(recordCounter, definition.getInd2(), dataField.getInd2());
    }

    for (MarcSubfield subfield : dataField.getSubfields()) {
      if (subfield.getDefinition() != null && subfield.getDefinition().getFrbrFunctions() != null) {
        countFunctions(subfield.getDefinition().getFrbrFunctions(), recordCounter);
      }
    }
  }

  /**
   * Counts the FRBR functions for a given indicator.
   * @param recordCounter
   * @param indicatorDefinition
   * @param value
   */
  private void countIndicator(Map<FRBRFunction, FunctionValue> recordCounter,
                              Indicator indicatorDefinition,
                              String value) {
    if (indicatorDefinition.getFrbrFunctions() == null || !StringUtils.isNotBlank(value)) {
      return;
    }

    countFunctions(indicatorDefinition.getFrbrFunctions(), recordCounter);
  }


}
