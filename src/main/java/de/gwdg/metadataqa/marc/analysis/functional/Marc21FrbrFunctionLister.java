package de.gwdg.metadataqa.marc.analysis.functional;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.MarcDefinition;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.utils.MarcTagLister;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Marc21FrbrFunctionLister extends FrbrFunctionLister {

  private static final Logger logger = Logger.getLogger(Marc21FrbrFunctionLister.class.getCanonicalName());
  private final MarcVersion marcVersion;

  public Marc21FrbrFunctionLister(MarcVersion marcVersion) {
    super();

    this.marcVersion = marcVersion == null ? MarcVersion.MARC21 : marcVersion;
  }

  public void prepareBaseline() {
    functionByPath = new TreeMap<>();
    pathByFunction = new TreeMap<>();

    prepareBaselineForMarc21();
  }

  private void prepareBaselineForMarc21() {
    // Register functions for the leader
    for (ControlfieldPositionDefinition subfield : MarcDefinition.getLeaderPositions()) {
      registerFunctionsMarc21(subfield.getFrbrFunctions(), subfield.getPath(false));
    }

    // Register functions for the simple control fields (001, 003, 005...)
    for (DataFieldDefinition subfield : MarcDefinition.getSimpleControlFields()) {
      registerFunctionsMarc21(subfield.getFrbrFunctions(), subfield.getTag());
    }

    // Register functions for the complex control fields which consist of positions
    for (ControlFieldDefinition controlField : MarcDefinition.getComplexControlFields()) {
      for (List<ControlfieldPositionDefinition> positions : controlField.getControlfieldPositions().values()) {
        for (ControlfieldPositionDefinition position : positions) {
          registerFunctionsMarc21(position.getFrbrFunctions(), position.getId().replace("tag", ""));
        }
      }
    }

    // Register functions for the data fields, or as here called, tags
    for (Class<? extends DataFieldDefinition> tagClass : MarcTagLister.listTags()) {
      MarcVersion currentVersion = Utils.getVersion(tagClass);
      Method getInstance;
      DataFieldDefinition fieldTag;
      try {
        getInstance = tagClass.getMethod("getInstance");
        fieldTag = (DataFieldDefinition) getInstance.invoke(tagClass);
        if (currentVersion != MarcVersion.MARC21 && currentVersion != marcVersion) {
          continue;
        }

        for (Indicator indicator : fieldTag.getIndicators()) {
          if (indicator != null) {
            registerFunctionsMarc21(indicator.getFrbrFunctions(), indicator.getPath());
          }
        }

        if (fieldTag.getSubfields() != null) {
          for (SubfieldDefinition subfield : fieldTag.getSubfields()) {
            registerFunctionsMarc21(subfield.getFrbrFunctions(), subfield.getPath());
          }
        }

      } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
        logger.log(Level.WARNING, "document", e);
      }
    }
  }

  private void registerFunctionsMarc21(List<FRBRFunction> functions, String marcPath) {
    if (functions == null || functions.isEmpty()) {
      return;
    }

    functionByPath.put(marcPath, functions);
    for (FRBRFunction function : functions) {
      pathByFunction.putIfAbsent(function, new ArrayList<>());
      pathByFunction.get(function).add(marcPath);

      // Add function to the baseline counter
      baselineCounter.count(function);
    }
  }
}
