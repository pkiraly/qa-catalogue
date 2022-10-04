package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.structure.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.MarcDefinition;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.utils.pica.crosswalk.Crosswalk;
import de.gwdg.metadataqa.marc.utils.pica.crosswalk.PicaMarcCrosswalkReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrbrFunctionLister {

  private static final Logger logger = Logger.getLogger(FrbrFunctionLister.class.getCanonicalName());

  private Counter<FRBRFunction> baselineCounter = new Counter<>();
  private int elementsWithoutFunctions;
  private MarcVersion marcVersion;

  private Map<FRBRFunction, FunctionValue> collector;
  private Map<FRBRFunction, Counter<FunctionValue>> histogram;

  private Map<String, List<FRBRFunction>> functionByMarcPath;
  private Map<String, List<FRBRFunction>> functionByPicaPath;
  private AppendableHashMap<FRBRFunction, String> marcPathByFunction;
  private Map<FRBRFunction, List<String>> picaPathByFunction;

  public FrbrFunctionLister(MarcVersion marcVersion) {
    this.marcVersion = marcVersion;

    prepareBaseline();
    prepareCollector();
    prepareHistogram();
  }

  public Map<FRBRFunction, Counter<FunctionValue>> getHistogram() {
    return histogram;
  }

  public void prepareBaseline() {
    elementsWithoutFunctions = 0;
    functionByMarcPath = new TreeMap<>();
    marcPathByFunction = new AppendableHashMap<>();

    for (ControlfieldPositionDefinition subfield : MarcDefinition.getLeaderPositions())
      registerFunctions(subfield.getFrbrFunctions(), subfield.getPath(false));

    for (DataFieldDefinition subfield : MarcDefinition.getSimpleControlFields())
      registerFunctions(subfield.getFrbrFunctions(), subfield.getTag());

    for (ControlFieldDefinition controlField : MarcDefinition.getComplexControlFields())
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
          if (indicator != null)
            registerFunctions(indicator.getFrbrFunctions(), indicator.getPath());

        if (fieldTag.getSubfields() != null)
          for (SubfieldDefinition subfield : fieldTag.getSubfields())
            registerFunctions(subfield.getFrbrFunctions(), subfield.getPath());

      } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
        logger.log(Level.WARNING, "document", e);
      }
    }
  }

  private void registerFunctions(List<FRBRFunction> functions, String marcPath) {
    if (functions != null && !functions.isEmpty()) {
      functionByMarcPath.put(marcPath, functions);
      for (FRBRFunction function : functions) {
        marcPathByFunction.append(function, marcPath);
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
        map.computeIfAbsent(function, s -> new FunctionValue());
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
      collector.computeIfAbsent(key, s -> new FunctionValue());
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
      histogram.computeIfAbsent(function, s -> new Counter<>());
      histogram.get(function).count(value);
    }
  }

  public Map<FRBRFunction, Integer> getBaseline() {
    return baselineCounter.getMap();
  }

  public Map<FRBRFunction, List<String>> getMarcPathByFunction() {
    return marcPathByFunction.getMap();
  }

  public Map<FRBRFunction, List<String>> getPicaPathByFunction() {
    if (picaPathByFunction == null) {
      initializePica();
    }
    return picaPathByFunction;
  }

  public Map<String, List<FRBRFunction>> getFunctionByPicaPath() {
    if (functionByPicaPath == null) {
      initializePica();
    }
    return functionByPicaPath;
  }

  private void initializePica() {
    picaPathByFunction = new HashMap<>();
    functionByPicaPath = new HashMap<>();
    for (Map.Entry<FRBRFunction, List<String>> entry : marcPathByFunction.entrySet()) {
      for (String address : entry.getValue()) {
        if (address.contains("$")) {
          FRBRFunction function = entry.getKey();
          String key = address.replace("$", " $");
          for (Crosswalk crosswalk : PicaMarcCrosswalkReader.lookupMarc21(key)) {
            String pica = crosswalk.getPica();
            if (!picaPathByFunction.containsKey(function))
              picaPathByFunction.put(function, new ArrayList<>());
            picaPathByFunction.get(function).add(pica);

            if (!functionByPicaPath.containsKey(pica))
              functionByPicaPath.put(pica, new ArrayList<>());
            functionByPicaPath.get(pica).add(function);
          }
        }
      }
    }
  }
}
