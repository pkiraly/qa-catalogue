package de.gwdg.metadataqa.marc.utils.marcspec;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcControlField;
import de.gwdg.metadataqa.marc.dao.MarcLeader;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.dao.record.MarcRecord;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MarcSpecExtractor {

  public static Object extract(MarcRecord marcRecord, MarcSpec spec) {
    if (spec.getTag().equals("LDR")) {
      MarcLeader leader = marcRecord.getLeader();
      if (spec.hasIndicator())
        throw new IllegalArgumentException("Leader should not have indicator");
      if (spec.getPosition() != null)
        return extractPosition(spec.getPosition(), leader.getLeaderString());
      else
        return leader;
    } else if (spec.getTag().startsWith("00")) {
      Object marcControlField = null;
      if (spec.getTag().contains(".")) {
        // multiple
        List<MarcControlField> controls = marcRecord.getControlfields();
        if (spec.hasPosition())
          return extractControlFieldsPosition(spec.getPosition(), controls);

        return controls;
      } else if (spec.getTag().equals("001")) {
        marcControlField = marcRecord.getControl001();
      } else if (spec.getTag().equals("003")) {
        marcControlField = marcRecord.getControl003();
      } else if (spec.getTag().equals("005")) {
        marcControlField = marcRecord.getControl005();
      } else {
        if (marcRecord instanceof Marc21Record) {
          if (spec.getTag().equals("006")) {
            marcControlField = ((Marc21Record)marcRecord).getControl006();
          } else if (spec.getTag().equals("007")) {
            marcControlField = ((Marc21Record)marcRecord).getControl007();
          } else if (spec.getTag().equals("008")) {
            marcControlField = ((Marc21Record)marcRecord).getControl008();
          }
        }
      }
      if (spec.hasIndicator())
        throw new IllegalArgumentException("Control fields should not have indicator");
      if (spec.hasPosition()) {
        if (marcControlField instanceof List)
          return extractControlFieldsPosition(spec.getPosition(), (List<MarcControlField>) marcControlField);
        else
          return extractPosition(spec.getPosition(), ((MarcControlField) marcControlField).getContent());
      }
      return marcControlField;
    } else {
      // data fields
      List<DataField> fields;
      if (spec.isMasked()) {
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

      if (fields == null || fields.isEmpty())
        return null;

      if (spec.getIndex() != null) {
        if (spec.isMasked()) {
          fields = groupByTagAndExtractIndices(fields, spec.getIndex());
        } else {
          fields = extractIndices(fields, spec.getIndex());
        }
      }

      if (spec.hasIndicator()) {
        return extractIndicators(spec, fields);
      } else if (spec.hasSubfields()) {
        return extractSubfields(spec.getSubfields(), fields);
      } else {
        return fields;
        /*
        return fields.stream()
          .map(e -> {
            return e.getSubfields().stream()
              .map(b -> b.getValue())
              .collect(Collectors.joining(" "));
          })
          .collect(Collectors.toList());
         */
      }
    }
  }

  private static List<String> extractSubfields(List<Subfield> subfieldSpecs,
                                               List<DataField> fields) {
    List<String> extracted = new ArrayList<>();
    for (DataField field : fields) {
      for (Subfield subfieldSpec : subfieldSpecs) {
        List<MarcSubfield> subfields = field.getSubfield(subfieldSpec.getSubfield());
        if (subfields != null) {
          List<String> local = new ArrayList<>();
          for (MarcSubfield subfield : subfields) {
            local.add(subfield.getValue());
          }
          if (subfieldSpec.hasIndex()) {
            local = extractIndices(local, subfieldSpec.getIndex());
          }
          if (subfieldSpec.hasPosition()) {
            local = extractSubfieldsPosition(subfieldSpec.getPosition(), local);
          }
          extracted.addAll(local);
        }
      }
    }
    return extracted;
  }

  private static List<String> extractSubfieldsPosition(Range position,
                                                       List<String> inputs) {
    return inputs.stream()
      .map(s -> extractPosition(position, s))
      .collect(Collectors.toList());
  }

  private static Object extractControlFieldsPosition(Range position,
                                                     List<MarcControlField> controls) {
    return controls.stream()
      .map(s -> extractPosition(position, s.getContent()))
      .collect(Collectors.toList());
  }

  private static List<DataField> groupByTagAndExtractIndices(List<DataField> fields,
                                                             Range index) {
    Map<String, List<DataField>> map = new LinkedHashMap<>();
    for (DataField dataField : fields) {
      String tag = dataField.getTag();
      if (!map.containsKey(tag)) {
        map.put(tag, new ArrayList<>());
      }
      map.get(tag).add(dataField);
    }
    List<DataField> result = new ArrayList<>();
    for (Map.Entry<String, List<DataField>> entry : map.entrySet()) {
      result.addAll(extractIndices(entry.getValue(), index));
    }
    return result;
  }

  private static <T extends Object> List<T> extractIndices(List<T> elements, Range index) {
    List<T> result = new ArrayList<>();
    int[] positions = getRange(elements.size(), index.getStart(), index.getEnd());
    for (int i = 0; i < elements.size(); i++) {
      if (i >= positions[0] && i <= positions[1])
        result.add(elements.get(i));
    }
    return result;
  }

  private static String extractPosition(Range position, String input) {
    int[] positions = getRange(input.length(), position.getStart(), position.getEnd());
    return input.substring(positions[0], positions[1]);
  }

  private static List<String> extractIndicators(MarcSpec spec, List<DataField> fields) {
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

  private static int[] getRange(int length, String start, String end) {
    int first = 0;
    int last = length;
    if (end == null) {
      if (start.equals("#"))
        first = last;
      else
        first = Integer.parseInt(start);
    } else {
      if (start.equals("#"))
        first = length - Integer.parseInt(end) - 1;
      else {
        first = Integer.parseInt(start);
        if (!end.equals("#"))
          last = Integer.parseInt(end) + 1;
      }
    }
    return new int[]{first, last};
  }
}
