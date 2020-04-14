package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.controlsubfields.LeaderSubfields;
import de.gwdg.metadataqa.marc.definition.tags.control.*;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FrbrFunctionLister {

  private Map<FRBRFunction, Double> collector;
  private Map<FRBRFunction, Integer> baseline;
  private int elementsWithoutFunctions;
  private Map<FRBRFunction, Map<Double, Integer>> histogram;
  private int controlFields = 0;
  private int controlSubfields = 0;
  private int coreFields = 0;
  private int localFields = 0;
  private int coreIndicators = 0;
  private int localIndicators = 0;
  private int coreSubfields = 0;
  private int localSubfields = 0;

  private Map<String, List<FRBRFunction>> functionByMarcPath;

  public FrbrFunctionLister() {
    prepareBaseline();
    prepareCollector();
    prepareHistogram();
    System.err.println("Covered elements: " + functionByMarcPath.size());
    System.err.println("Uncovered elements: " + elementsWithoutFunctions);

    System.err.println("control fields: " + controlFields);
    System.err.println("control subfields: " + controlSubfields);

    System.err.println("core fields: " + coreFields);
    System.err.println("core indicators: " + coreIndicators);
    System.err.println("core subfields: " + coreSubfields);

    System.err.println("local fields: " + localFields);
    System.err.println("local indicators: " + localIndicators);
    System.err.println("local subfields: " + localSubfields);
  }

  public Map<FRBRFunction, Map<Double, Integer>> getHistogram() {
    return histogram;
  }

  public void prepareBaseline() {
    baseline = new TreeMap<>();
    elementsWithoutFunctions = 0;
    functionByMarcPath = new TreeMap<>();

    controlFields++;
    for (ControlSubfieldDefinition subfield : LeaderSubfields.getSubfieldList()) {
      controlSubfields++;
      processFunctions(subfield.getFrbrFunctions(), LeaderDefinition.getInstance().getTag()+ "/" + subfield.getPositionStart());
    }

    List<DataFieldDefinition> simpleControlFields = Arrays.asList(
      Control001Definition.getInstance(),
      Control003Definition.getInstance(),
      Control005Definition.getInstance()
    );

    for (DataFieldDefinition subfield : simpleControlFields) {
      controlFields++;
      processFunctions(subfield.getFrbrFunctions(), subfield.getTag());
    }

    List<ControlFieldDefinition> complexControlFields = Arrays.asList(
      Control006Definition.getInstance(),
      Control007Definition.getInstance(),
      Control008Definition.getInstance()
    );
    for (ControlFieldDefinition controlField : complexControlFields) {
      controlFields++;
      for (Map.Entry<String, List<ControlSubfieldDefinition>> entry : controlField.getControlSubfields().entrySet()) {
        String category = entry.getKey();
        for (ControlSubfieldDefinition subfield : entry.getValue()) {
          controlSubfields++;
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
        boolean isCore = true;
        if (fieldTag.getClass().getCanonicalName().contains("fennicatags")
           || fieldTag.getClass().getCanonicalName().contains("oclctags")
           || fieldTag.getClass().getCanonicalName().contains("genttags")
           || fieldTag.getClass().getCanonicalName().contains("dnbtags")
           || fieldTag.getClass().getCanonicalName().contains("sztetags")
           || fieldTag.getClass().getCanonicalName().contains("nkcrtags")) {
          isCore = false;
          localFields++;
        } else {
          coreFields++;
        }
        System.err.println(fieldTag.getClass());

        elementsWithoutFunctions++;

        for (Indicator indicator : fieldTag.getIndicators()) {
          if (indicator != null && StringUtils.isNotBlank(indicator.getLabel())) {
            if (isCore) coreIndicators++; else localIndicators++;
          }
          processFunctions(indicator.getFrbrFunctions(), fieldTag.getTag() + "$" + indicator.getIndicatorFlag());
        }

        for (SubfieldDefinition subfield : fieldTag.getSubfields()) {
          if (isCore) coreSubfields++; else localSubfields++;
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
    } else {
      elementsWithoutFunctions++;
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

  private void prepareHistogram() {
    histogram = new TreeMap<>();
    for (FRBRFunction key : baseline.keySet()) {
      histogram.put(key, new TreeMap<>());
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

  public void addToHistogram(Map<FRBRFunction, Double> percent) {
    for (Map.Entry<FRBRFunction, Double> entry : percent.entrySet()) {
      Utils.count(entry.getValue(), histogram.get(entry.getKey()));
    }
  }

  public Map<FRBRFunction, Integer> getBaseline() {
    return baseline;
  }
}
