package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.structure.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.MarcDefinition;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FrbrFunctionLister {

  private Counter<FRBRFunction> baselineCounter = new Counter<>();
  private int elementsWithoutFunctions;
  private MarcVersion marcVersion;

  private Map<FRBRFunction, FunctionValue> collector;
  private Map<FRBRFunction, Counter<FunctionValue>> histogram;

  private Map<String, List<FRBRFunction>> functionByMarcPath;
  private AppendableHashMap<FRBRFunction, String> marcPathByfunction;

  public FrbrFunctionLister(MarcVersion marcVersion) {
    this.marcVersion = marcVersion;

    prepareBaseline();
    prepareCollector();
    prepareHistogram();

    System.err.println("Covered elements: " + functionByMarcPath.size());
    System.err.println("Uncovered elements: " + elementsWithoutFunctions);

    /*
    System.err.println("control fields: " + controlFields);
    System.err.println("control subfields: " + controlSubfields);

    System.err.println("core fields: " + coreFields);
    System.err.println("core indicators: " + coreIndicators);
    System.err.println("core subfields: " + coreSubfields);

    System.err.println("local fields: " + localFields);
    System.err.println("local indicators: " + localIndicators);
    System.err.println("local subfields: " + localSubfields);
     */
  }

  public Map<FRBRFunction, Counter<FunctionValue>> getHistogram() {
    return histogram;
  }

  public void prepareBaseline() {
    elementsWithoutFunctions = 0;
    functionByMarcPath = new TreeMap<>();
    marcPathByfunction = new AppendableHashMap<>();

    for (ControlfieldPositionDefinition subfield : MarcDefinition.leaderPositions)
      registerFunctions(subfield.getFrbrFunctions(), subfield.getPath(false));

    for (DataFieldDefinition subfield : MarcDefinition.simpleControlFields)
      registerFunctions(subfield.getFrbrFunctions(), subfield.getTag());

    for (ControlFieldDefinition controlField : MarcDefinition.complexControlFields)
      for (List<ControlfieldPositionDefinition> positions : controlField.getControlfieldPositions().values())
        for (ControlfieldPositionDefinition position : positions)
          registerFunctions(position.getFrbrFunctions(), position.getId().replace("tag", ""));

    for (Class<? extends DataFieldDefinition> tagClass : MarcTagLister.listTags()) {
      MarcVersion currentVersion = Utils.getVersion(tagClass);
      Method getInstance;
      DataFieldDefinition fieldTag;
      try {
        getInstance = tagClass.getMethod("getInstance");
        fieldTag = (DataFieldDefinition) getInstance.invoke(tagClass);
        if (currentVersion != MarcVersion.MARC21 && currentVersion != marcVersion)
          continue;

        elementsWithoutFunctions++;
        for (Indicator indicator : fieldTag.getIndicators())
          registerFunctions(indicator.getFrbrFunctions(), indicator.getPath());

        if (fieldTag.getSubfields() != null)
          for (SubfieldDefinition subfield : fieldTag.getSubfields())
            registerFunctions(subfield.getFrbrFunctions(), subfield.getPath());

      } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }

  private void registerFunctions(List<FRBRFunction> functions, String marcPath) {
    if (functions != null && !functions.isEmpty()) {
      functionByMarcPath.put(marcPath, functions);
      for (FRBRFunction function : functions) {
        marcPathByfunction.append(function, marcPath);
        baselineCounter.count(function);
      }
    } else {
      elementsWithoutFunctions++;
    }
  }

  public static void countFunctions(List<FRBRFunction> functions,
                                    Map<FRBRFunction, FunctionValue> map) {
    if (functions != null && !functions.isEmpty()) {
      for (FRBRFunction function : functions) {
        if (!map.containsKey(function))
          map.put(function, new FunctionValue());
        map.get(function).count();
      }
    }
  }

  private void prepareCollector() {
    collector = new TreeMap<>();
    for (FRBRFunction key : baselineCounter.keys())
      collector.put(key, new FunctionValue());
  }

  private void prepareHistogram() {
    histogram = new TreeMap<>();
    for (FRBRFunction key : baselineCounter.keys()) {
      histogram.put(key, new Counter<>());
    }
  }

  public void calculatePercent(Map<FRBRFunction, FunctionValue> other) {
    for (FRBRFunction key : baselineCounter.keys())
      if (other.containsKey(key))
        other.get(key).calculatePercent(baselineCounter.get(key));
  }

  public void add(Map<FRBRFunction, FunctionValue> other) {
    for (FRBRFunction key : other.keySet()) {
      if (!collector.containsKey(key))
        collector.put(key, new FunctionValue());
      collector.get(key).add(other.get(key));
    }
  }

  public Map<FRBRFunction, List<Double>> percentOf(int total) {
    Map<FRBRFunction, List<Double>> result = new TreeMap<>();
    for (FRBRFunction key : collector.keySet()) {
      double avgCount = collector.get(key).getCount() * 1.0 / total;
      double avgPerc = collector.get(key).getPercent() * 1.0 / total;
      result.put(key, Arrays.asList(avgCount, avgPerc));
    }
    return result;
  }

  public void addToHistogram(Map<FRBRFunction, FunctionValue> other) {
    for (Map.Entry<FRBRFunction, FunctionValue> entry : other.entrySet()) {
      FRBRFunction function = entry.getKey();
      FunctionValue value = entry.getValue();
      if (!histogram.containsKey(function))
        histogram.put(function, new Counter<FunctionValue>());
      histogram.get(function).count(value);
    }
  }

  public Map<FRBRFunction, Integer> getBaseline() {
    return baselineCounter.getMap();
  }

  public Map<FRBRFunction, List<String>> getMarcPathByfunction() {
    return marcPathByfunction.getMap();
  }
}
