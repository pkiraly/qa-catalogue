package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.utils.parser.BooleanContainer;
import de.gwdg.metadataqa.marc.utils.parser.BooleanParser;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPath;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPathParser;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PicaFilter {
  protected static final Pattern CRITERIUM = Pattern.compile(
    "^([012\\.][A-Za-z0-9@\\./\\$\\*\\-]+?)(\\s*(==|!=|=~|!~|=\\^|=\\$)\\s*(?:'([^']+)'|\"([^\"]+)\")|\\?)$"
  );

  protected List<CriteriumPica> criteria = new ArrayList<>();
  protected BooleanContainer<CriteriumPica> booleanCriteria;

  protected void parse(String ignorableRecordsInput) {

    if (StringUtils.isNotBlank(ignorableRecordsInput)) {
      booleanCriteria = transformContainer(BooleanParser.parse(ignorableRecordsInput));
    }
  }

  private BooleanContainer<CriteriumPica> transformContainer(BooleanContainer<String> booleanCriteria) {
    BooleanContainer<CriteriumPica> container = new BooleanContainer<>();
    container.setOp(booleanCriteria.getOp());
    if (booleanCriteria.getValue() != null) {
      container.setValue(parseCriterium(booleanCriteria.getValue()));
    } else if (!booleanCriteria.getChildren().isEmpty()) {
      for (BooleanContainer<String> child : booleanCriteria.getChildren()) {
        container.getChildren().add(transformContainer(child));
      }
    }
    return container;
  }

  protected CriteriumPica parseCriterium(String rawCriterium) {
    Matcher m = CRITERIUM.matcher(rawCriterium);
    if (m.matches()) {
      String rawPath = m.group(1);
      String rawOp;
      String value = null;
      if (m.group(2).equals("?")) {
        rawOp = m.group(2);
      } else {
        rawOp = m.group(3);
        if (m.group(4) != null)
          value = m.group(4);
        else if (m.group(5) != null)
          value = m.group(5);
      }
      PicaPath p = PicaPathParser.parse(rawPath);
      Operator op = Operator.byCode(rawOp);
      return new CriteriumPica(p, op, value);
    } else {
      throw new IllegalArgumentException(String.format("The criterium does not fit to rules: '%s'", rawCriterium));
    }
  }

  public List<CriteriumPica> getCriteria() {
    return criteria;
  }

  public BooleanContainer<CriteriumPica> getBooleanCriteria() {
    return booleanCriteria;
  }

  public boolean metCriteria(BibliographicRecord marcRecord, BooleanContainer<CriteriumPica> criteria) {
    boolean passed = false;
    if (criteria.getValue() != null) {
      passed = criteria.getValue().met(marcRecord);
    } else {
      boolean hasPassed = false;
      boolean hasFailed = false;
      for (BooleanContainer<CriteriumPica> container : criteria.getChildren()) {
        boolean p = metCriteria(marcRecord, container);
        if (p && !hasPassed)
          hasPassed = true;
        if (!p && !hasFailed)
          hasFailed = true;
        if ((criteria.hasAnd() && !p) || (criteria.hasOr() && p)) {
          break;
        }
      }
      if (criteria.hasOr())
        passed = hasPassed;
      else if (criteria.hasAnd())
        passed = hasPassed && !hasFailed;
    }
    return passed;
  }

  @Override
  public String toString() {
    return "PicaFilter{" +
      "criteria=" + booleanCriteria +
      '}';
  }
}
