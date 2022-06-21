package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPath;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPathParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecordIgnoratorPica implements RecordIgnorator {

  private static final Pattern CRITERIUM = Pattern.compile("^([012\\.][A-Za-z0-9@\\./\\$]+?)(\\s*(==|!=|=~|!~|=\\^|=\\$)\\s*'([^']+)'|\\?)$");

  private List<CriteriumPica> criteria = new ArrayList<>();

  public RecordIgnoratorPica(String ignorableRecordsInput) {
    parse(ignorableRecordsInput);
  }

  private void parse(String ignorableRecordsInput) {
    String[] rawCriteria = ignorableRecordsInput.split(",");
    for (String rawCriterium : rawCriteria) {
      if (!rawCriterium.isEmpty())
        criteria.add(parseCriterium(rawCriterium));
    }
  }

  private CriteriumPica parseCriterium(String rawCriterium) {
    Matcher m = CRITERIUM.matcher(rawCriterium);
    if (m.matches()) {
      String rawPath = m.group(1);
      String rawOp = null;
      String value = null;
      if (m.group(2).equals("?")) {
        rawOp = m.group(2);
      } else {
        rawOp = m.group(3);
        value = m.group(4);
      }
      PicaPath p = PicaPathParser.parse(rawPath);
      Operator op = Operator.byCode(rawOp);
      return new CriteriumPica(p, op, value);
    } else {
      throw new IllegalArgumentException(String.format("The criterium does not fit to rules: '%s'", rawCriterium));
    }
  }

  @Override
  public boolean isEmpty() {
    return criteria.isEmpty();
  }

  @Override
  public boolean isIgnorable(MarcRecord marcRecord) {
    for (CriteriumPica criterium : criteria) {
      boolean passed = criterium.met(marcRecord);
      if (passed)
        return passed;
    }
    return false;
  }

  public List<CriteriumPica> getCriteria() {
    return criteria;
  }
}
