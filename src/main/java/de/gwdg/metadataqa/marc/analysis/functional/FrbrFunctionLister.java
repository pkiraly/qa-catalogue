package de.gwdg.metadataqa.marc.analysis.functional;

import de.gwdg.metadataqa.marc.definition.FRBRFunction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class FrbrFunctionLister {
  protected Map<String, List<FRBRFunction>> functionByPath;
  protected Map<FRBRFunction, List<String>> pathByFunction;
  private Map<FRBRFunction, Integer> baselineCounterMap;

  protected FrbrFunctionLister() {
    prepareBaseline();
  }


  public abstract void prepareBaseline();

  public Map<FRBRFunction, List<String>> getPathByFunction() {
    return pathByFunction;
  }

  public Map<String, List<FRBRFunction>> getFunctionByPath() {
    return functionByPath;
  }

  public Map<FRBRFunction, Integer> getBaselineCounterMap() {
    // It can be produced from the pathByFunction map
    // pathByFunction is a map with the FRBR functions as keys and a list of paths as values
    // So we can simply create another map with the FRBR functions as keys and the size of the list of paths as values
    if (baselineCounterMap == null) {
      baselineCounterMap = pathByFunction.entrySet().stream()
        .collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue().size()), HashMap::putAll);
    }

    return baselineCounterMap;
  }
}
