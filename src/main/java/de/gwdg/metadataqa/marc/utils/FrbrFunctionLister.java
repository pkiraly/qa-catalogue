package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FrbrFunctionLister {

  Map<FRBRFunction, Double> collector;
  Map<FRBRFunction, Integer> baseline;
  Map<String, List<FRBRFunction>> functionByMarcPath;

  public FrbrFunctionLister() {
    getBaseline();
    prepareCollector();
  }

  public void getBaseline() {
    baseline = new TreeMap<>();
    functionByMarcPath = new TreeMap<>();

    for (Class<? extends DataFieldDefinition> tagClass : MarcTagLister.listTags()) {

      Method getInstance;
      DataFieldDefinition fieldTag;
      try {
        getInstance = tagClass.getMethod("getInstance");
        fieldTag = (DataFieldDefinition) getInstance.invoke(tagClass);

        for (Indicator indicator : fieldTag.getIndicators()) {
          processFunctions(indicator.getFrbrFunctions(), fieldTag.getTag() + "$" + indicator.getIndicatorFlag());
        }

        for (SubfieldDefinition subfield : fieldTag.getSubfields()) {
          processFunctions(subfield.getFrbrFunctions(), fieldTag.getTag() + "$" + subfield.getCode());
        }
      } catch (NoSuchMethodException
        | IllegalAccessException
        | InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }

  private void processFunctions(List<FRBRFunction> functions, String key) {
    if (functions != null) {
      functionByMarcPath.put(key, functions);
      countFunctions(functions, baseline);
    }
  }

  public static void countFunctions(List<FRBRFunction> functions, Map<FRBRFunction, Integer> map) {
    for (FRBRFunction function : functions) {
      Utils.count(function, map);
    }
  }

  private void prepareCollector() {
    collector = new TreeMap<>();
    for (FRBRFunction key : baseline.keySet()) {
      collector.put(key, 0.0);
    }
  }

  public Map<FRBRFunction, Double> percent(Map<FRBRFunction, Integer> other) {
    Map<FRBRFunction, Double> percents = new TreeMap<>();
    for (FRBRFunction key : baseline.keySet()) {
      double value = 0.0;
      if (other.containsKey(key)) {
        value = other.get(key) * 1.0 / baseline.get(key);
      }
      percents.put(key, value);
    }
    return percents;
  }

  public void add(Map<FRBRFunction, Double> other) {
    for (FRBRFunction key : other.keySet()) {
      collector.put(key, collector.get(key) + other.get(key));
    }
  }

  public Map<FRBRFunction, Double> percentOf(int total) {
    Map<FRBRFunction, Double> result = new TreeMap<>();
    for (FRBRFunction key : collector.keySet()) {
      result.put(key, collector.get(key) / total);
    }
    return result;
  }
}
