package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPath;
import de.gwdg.metadataqa.marc.utils.pica.path.Subfields;

import java.util.List;
import java.util.regex.Pattern;

public class CriteriumPica {
  private PicaPath path;
  private Operator operator;
  private String value;
  private Pattern pattern;

  public CriteriumPica(PicaPath path, Operator operator, String value) {
    System.err.printf("path: %s, operator: %s, value: %s\n", path, operator, value);
    this.path = path;
    this.operator = operator;
    this.value = value;
    if (operator.equals(Operator.MATCH) || operator.equals(Operator.NOT_MATCH)) {
      pattern = Pattern.compile(value);
    }
  }

  public PicaPath getPath() {
    return path;
  }

  public Operator getOperator() {
    return operator;
  }

  public String getValue() {
    return value;
  }

  public boolean met(BibliographicRecord marcRecord) {
    List<DataField> fields = marcRecord.getDatafield(path.getTag());
    boolean passed = false;
    if (fields != null && !fields.isEmpty()) {
      Subfields subfieldsSelector = path.getSubfields();
      if (subfieldsSelector != null) {
        if (subfieldsSelector.getType().equals(Subfields.Type.ALL)) {
          passed = metFields(fields, null);
        } else {
          passed = metFields(fields, subfieldsSelector.getCodes());
        }
      }
    }
    return passed;
  }

  public boolean metFields(List<DataField> fields, List<String> codes) {
    for (DataField field : fields) {
      boolean passed = false;
      if (codes == null) {
        passed = metSubfields(field.getSubfields());
        if (passed)
          return true;
      }
      for (String code : codes) {
        passed = metSubfields(field.getSubfield(code));
        if (passed)
          return true;
      }
    }
    return false;
  }

  public boolean metSubfields(List<MarcSubfield> subfieldInstances) {
    switch (operator) {
      case EXIST: return (subfieldInstances != null && !subfieldInstances.isEmpty());
      case MATCH: return subfieldMatches(subfieldInstances);
      case NOT_MATCH: return !subfieldMatches(subfieldInstances);
      case EQUAL: return subfieldEquals(subfieldInstances);
      case NOT_EQUAL: return !subfieldEquals(subfieldInstances);
      case START_WITH: return subfieldStartsWith(subfieldInstances);
      case END_WITH: return subfieldEndsWith(subfieldInstances);
      default:
        return false;
    }
  }

  private boolean subfieldMatches(List<MarcSubfield> instances) {
    if (instances != null && !instances.isEmpty()) {
      for (MarcSubfield instance : instances) {
        if (pattern.matcher(instance.getValue()).find()) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean subfieldEquals(List<MarcSubfield> instances) {
    if (instances != null && !instances.isEmpty()) {
      for (MarcSubfield instance : instances) {
        if (value.equals(instance.getValue())) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean subfieldStartsWith(List<MarcSubfield> instances) {
    if (instances != null && !instances.isEmpty()) {
      for (MarcSubfield instance : instances) {
        if (instance.getValue().startsWith(value)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean subfieldEndsWith(List<MarcSubfield> instances) {
    if (instances != null && !instances.isEmpty()) {
      for (MarcSubfield instance : instances) {
        if (instance.getValue().endsWith(value)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return "CriteriumPica{" +
      "path=" + path.getPath() +
      ", operator=" + operator +
      ", value='" + value + '\'' +
      '}';
  }
}
