package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FrbrFunctionListerTest {

  @Test
  public void test() {
    Map<FRBRFunction, Integer> counter = new TreeMap<>();

    for (Class<? extends DataFieldDefinition> tagClass : MarcTagLister.listTags()) {

      Method getInstance;
      DataFieldDefinition fieldTag;
      try {
        getInstance = tagClass.getMethod("getInstance");
        fieldTag = (DataFieldDefinition) getInstance.invoke(tagClass);
        List<FRBRFunction> functions = null;

        for (Indicator indicator : fieldTag.getIndicators()) {
          functions = indicator.getFrbrFunctions();
          if (functions != null) {
            // System.err.printf("%s$%s: %s%n", fieldTag.getTag(), indicator.getIndicatorFlag(), StringUtils.join(functions));
            for (FRBRFunction function : functions) {
              count(function, counter);
            }
          }
        }

        if (fieldTag.getSubfields() != null)
          for (SubfieldDefinition subfield : fieldTag.getSubfields()) {
            functions = subfield.getFrbrFunctions();
            if (functions != null) {
              // System.err.printf("%s$%s: %s%n", fieldTag.getTag(), subfield.getCode(), StringUtils.join(functions));
              for (FRBRFunction function : functions) {
                count(function, counter);
              }
            }
          }

      } catch (NoSuchMethodException
        | IllegalAccessException
        | InvocationTargetException e) {
        e.printStackTrace();
      }
    }
    for (Map.Entry<FRBRFunction, Integer> entry : counter.entrySet()) {
      System.err.println(entry.getKey() + ": " + entry.getValue());
    }

  }

  private <T extends Object> void count(T key, Map<T, Integer> counter) {
    if (!counter.containsKey(key)) {
      counter.put(key, 0);
    }
    counter.put(key, counter.get(key) + 1);
  }

}
