package de.gwdg.metadataqa.marc.utils.marcspec;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.definition.bibliographic.BibliographicFieldDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MarcSpec2Extractor {

  public static Object extract(Marc21Record marcRecord, MarcSpec2 spec) {
    if (spec.getTag().equals("LDR")) {
      return marcRecord.getLeader();
    } else if (spec.getTag().startsWith("00")) {
      if (spec.getTag().contains(".")) {
        // multiple
        return List.of(
          marcRecord.getControl001(),
          marcRecord.getControl003(),
          marcRecord.getControl005(),
          marcRecord.getControl006(),
          marcRecord.getControl007(),
          marcRecord.getControl008()
        );
      } else if (spec.getTag().equals("001")) {
        return marcRecord.getControl001();
      } else if (spec.getTag().equals("003")) {
        return marcRecord.getControl003();
      } else if (spec.getTag().equals("005")) {
        return marcRecord.getControl005();
      } else if (spec.getTag().equals("006")) {
        return marcRecord.getControl006();
      } else if (spec.getTag().equals("007")) {
        return marcRecord.getControl007();
      } else if (spec.getTag().equals("008")) {
        return marcRecord.getControl008();
      }
    } else {
      // data fields
      List<DataField> fields;
      if (spec.getTag().contains(".")) {
        Pattern pattern = Pattern.compile(spec.getTag());
        fields = new ArrayList<>();
        for (DataField dataField : marcRecord.getDatafields()) {
          if (pattern.matcher(dataField.getTag()).matches()) {
            fields.add(dataField);
          }
        }
      } else {
        fields = marcRecord.getDatafieldsByTag(spec.getTag());
      }
      if (spec.getIndicator() == null) {
        return fields;
      } else {
        return extractIndicators(spec, fields);
      }
    }
    return null;
  }

  private static List<String> extractIndicators(MarcSpec2 spec, List<DataField> fields) {
    List<String> indicators = new ArrayList<>();
    for (DataField dataField : fields) {
      if (spec.getIndicator().equals("1")) {
        indicators.add(dataField.getInd1());
      } else if (spec.getIndicator().equals("2")) {
        indicators.add(dataField.getInd2());
      }
    }
    return indicators;
  }
}
