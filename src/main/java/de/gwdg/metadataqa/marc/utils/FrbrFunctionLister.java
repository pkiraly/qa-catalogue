package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.controlsubfields.LeaderSubfields;
import de.gwdg.metadataqa.marc.definition.tags.control.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
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

    for (ControlSubfieldDefinition subfield : LeaderSubfields.getSubfieldList()) {
      processFunctions(subfield.getFrbrFunctions(), LeaderDefinition.getInstance().getTag()+ "/" + subfield.getPositionStart());
    }

    List<DataFieldDefinition> simpleControlFields = Arrays.asList(
      Control001Definition.getInstance(),
      Control003Definition.getInstance(),
      Control005Definition.getInstance()
    );

    for (DataFieldDefinition subfield : simpleControlFields) {
      processFunctions(subfield.getFrbrFunctions(), subfield.getTag());
    }

    List<ControlFieldDefinition> controlFields = Arrays.asList(
      Control006Definition.getInstance(),
      Control007Definition.getInstance(),
      Control008Definition.getInstance()
    );
    for (ControlFieldDefinition controlField : controlFields) {
      for (Map.Entry<String, List<ControlSubfieldDefinition>> entry : controlField.getControlSubfields().entrySet()) {
        String category = entry.getKey();
        for (ControlSubfieldDefinition subfield : entry.getValue()) {
          processFunctions(subfield.getFrbrFunctions(), category+ "/" + subfield.getPositionStart());
        }
      }
    }

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
    if (functions != null) {
      for (FRBRFunction function : functions) {
        Utils.count(function, map);
      }
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
