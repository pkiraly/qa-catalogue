package de.gwdg.metadataqa.marc.analysis.functional;

import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.utils.Counter;

import java.util.List;
import java.util.Map;

public abstract class FrbrFunctionLister {
  protected Map<String, List<FRBRFunction>> functionByPath;
  protected Map<FRBRFunction, List<String>> pathByFunction;

  protected FrbrFunctionLister() {
    prepareBaseline();
  }

  /**
   * Used to store the count of each function in the baseline (i.e. the total count of each function of a given schema)
   */
  protected Counter<FRBRFunction> baselineCounter = new Counter<>();


  public abstract void prepareBaseline();

  public Map<FRBRFunction, List<String>> getPathByFunction() {
    return pathByFunction;
  }

  public Map<String, List<FRBRFunction>> getFunctionByPath() {
    return functionByPath;
  }

  public Counter<FRBRFunction> getBaselineCounter() {
    return baselineCounter;
  }
}
