package de.gwdg.metadataqa.marc.cli.utils.ignorablerecords;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Marc21Filter {

  protected List<Condition> conditions;

  protected void parseInput(String input) {
    if (StringUtils.isNotBlank(input)) {
      conditions = new ArrayList<>();
      for (String field : input.split(",")) {
        Condition f = parseFieldCondition(field);
        if (f != null)
          conditions.add(f);
      }
    }
  }

  public List<Condition> getConditions() {
    return conditions;
  }

  public boolean isEmpty() {
    return conditions == null || conditions.isEmpty();
  }

  /**
   * Parses the given string field condition which is in a format of "tag$subfield=value" (e.g. "001$0=123456").
   * @param field The field condition to parse
   * @return The parsed field condition, which is essentially a new DataField object
   */
  protected Condition parseFieldCondition(String field) {
    var pattern = Pattern.compile(
      "^(.{3})(?:\\$(.))?\\s?(=~|!=|!~|==|=\\^|=\\$|\\?|=|not in|in)\\s?'?(.*?)'?$"
    );
    var matcher = pattern.matcher(field);
    if (matcher.matches()) {
      String tag = matcher.group(1);
      String subfield = matcher.group(2);
      String operator = matcher.group(3);
      String value = matcher.group(4);
      try {
        return new Condition(tag, subfield, Operator.byCode(operator), value);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return null;
  }

  /**
   * Checks if the given record meets the set of criteria. If only one of the criteria is met, the method returns
   * true. If none of the criteria is met, the method returns false.
   * @param marcRecord The record to check
   * @return True if the record meets the criteria, false otherwise
   */
  protected boolean met(BibliographicRecord marcRecord) {
    for (Condition condition : conditions) {
      List<DataField> recordFields = marcRecord.getDatafieldsByTag(condition.getTag());
      if (recordFields == null || recordFields.isEmpty())
        continue;
      boolean passed = metCondition(condition, recordFields);
      if (passed)
        return true;
    }
    return false;
  }

  /**
   * Checks if the given fields meet the given condition. If the condition is met, the method returns true.
   * @param condition Condition to check the record for, for now in the form of a DataField object
   * @param recordFields Fields of the record to check
   * @return True if the fields meet the condition, false otherwise
   */
  private boolean metCondition(Condition condition, List<DataField> recordFields) {
    for (DataField recordField : recordFields) {
      String code = condition.getSubfield();
      List<MarcSubfield> recordSubfields = recordField.getSubfield(code);

      if (recordSubfields == null || recordSubfields.isEmpty())
        continue;

      for (MarcSubfield recordSubfield : recordSubfields) {
        if (condition.isUseEqual()) {
          if (recordSubfield.getValue().equals(condition.getValue()))
            return condition.isNegate() ? false : true;
        } else if (condition.isUsePattern()) {
          var matcher = condition.getPattern().matcher(recordSubfield.getValue());
          if (matcher.find())
            return condition.isNegate() ? false : true;
        }
      }
    }
    return false;
  }
}
