package de.gwdg.metadataqa.marc.analysis.functional;

import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.utils.Counter;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public abstract class FrbrFunctionLister {

  private static final Logger logger = Logger.getLogger(FrbrFunctionLister.class.getCanonicalName());
  protected Map<String, List<FRBRFunction>> functionByPath;
  protected Map<FRBRFunction, List<String>> pathByFunction;
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
