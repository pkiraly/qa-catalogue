package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.structure.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.MarcDefinition;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataElementsStatictics {

  private static final Logger logger = Logger.getLogger(DataElementsStatictics.class.getCanonicalName());

  public static Counter<DataElementType> count() {
    Counter<DataElementType> counter = new Counter<>();

    for (ControlfieldPositionDefinition subfield : MarcDefinition.getLeaderPositions())
      counter.count(DataElementType.controlFieldPositions);

    for (DataFieldDefinition subfield : MarcDefinition.getSimpleControlFields())
      counter.count(DataElementType.controlFields);

    for (ControlFieldDefinition controlField : MarcDefinition.getComplexControlFields()) {
      counter.count(DataElementType.controlFields);

      for (List<ControlfieldPositionDefinition> controlFieldPositions : controlField.getControlfieldPositions().values())
        for (ControlfieldPositionDefinition controlFieldPosition : controlFieldPositions)
          counter.count(DataElementType.controlFieldPositions);
    }

    for (Class<? extends DataFieldDefinition> tagClass : MarcTagLister.listTags()) {

      MarcVersion version = Utils.getVersion(tagClass);
      Method getInstance;
      DataFieldDefinition fieldTag;
      try {
        getInstance = tagClass.getMethod("getInstance");
        fieldTag = (DataFieldDefinition) getInstance.invoke(tagClass);
        boolean isCore = (version == MarcVersion.MARC21);
        if (isCore)
          counter.count(DataElementType.coreFields);
        else
          counter.count(DataElementType.localFields);

        for (Indicator indicator : fieldTag.getIndicators())
          if (indicator != null && StringUtils.isNotBlank(indicator.getLabel()))
            if (isCore)
              counter.count(DataElementType.coreIndicators);
            else
              counter.count(DataElementType.localIndicators);

        if (fieldTag.getSubfields() != null)
          for (SubfieldDefinition subfield : fieldTag.getSubfields())
            if (isCore)
              counter.count(DataElementType.coreSubfields);
            else
              counter.count(DataElementType.localSubfields);

      } catch (NoSuchMethodException
              | IllegalAccessException
              | InvocationTargetException e) {
        logger.log(Level.WARNING, "error in count()", e);
      }
    }

    return counter;
  }
}
