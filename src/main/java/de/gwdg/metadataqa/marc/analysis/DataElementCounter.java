package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class DataElementCounter {

  public enum Basis{EXISTENCE, OCCURENCE}

  private static final Logger logger = Logger.getLogger(DataElementCounter.class.getCanonicalName());

  List<DataElement> elements = new ArrayList<>();
  Map<String, List<DataElement>> tags = new LinkedHashMap<>();
  private final String header;
  private final Basis basis;

  public DataElementCounter(String dir, String fileName, Basis basis) {
    this.basis = basis;
    File file = new File(dir, fileName);
    String firstLine = "";
    try {
      List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
      firstLine = lines.get(0);
      String[] topFields = firstLine.split(",");
      for (String field : topFields) {
        String[] parts = field.split("\\$");
        DataElement element = new DataElement(parts[0], parts[1]);
        elements.add(element);
        tags.computeIfAbsent(element.field, s -> new ArrayList<>());
        tags.get(element.field).add(element);
      }
    } catch (IOException e) {
      logger.severe(e.getLocalizedMessage());
    }
    this.header = firstLine;
  }

  public List<Integer> count(BibliographicRecord marcRecord) {
    List<Integer> counts = new ArrayList<>();
    for (Map.Entry<String, List<DataElement>> entry : tags.entrySet()) {
      List<DataField> instances = marcRecord.getDatafield(entry.getKey());
      if (instances == null || instances.isEmpty()) {
        for (int i=0; i<entry.getValue().size(); i++) {
          counts.add(0);
        }
      } else {
        Map<String, Integer> result = new LinkedHashMap<>();
        for (DataField instance : instances) {
          for (DataElement element : entry.getValue()) {
            result.computeIfAbsent(element.subfield, s -> 0);
            List<MarcSubfield> subfields = instance.getSubfield(element.subfield);
            if (subfields != null && !subfields.isEmpty()) {
              result.put(element.subfield, result.get(element.subfield) + subfields.size());
            }
          }
        }
        for (DataElement element : entry.getValue()) {
          int score = result.get(element.subfield);
          if (basis.equals(Basis.EXISTENCE) && score >= 1)
            score = 1;
          counts.add(score);
        }
      }
    }
    return counts;
  }

  public String getHeader() {
    return header;
  }

  class DataElement {
    String field;
    String subfield;
    String key;

    public DataElement(String field, String subfield) {
      this.field = field;
      this.subfield = subfield;
      this.key = field + "$" + subfield;
    }
  }
}
