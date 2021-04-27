package de.gwdg.metadataqa.marc.dao;

import de.gwdg.metadataqa.marc.Extractable;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.tags.control.Control005Definition;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Control005  extends MarcControlField implements Extractable {

  private static final Logger logger = Logger.getLogger(Control005.class.getCanonicalName());
  private static final Pattern DATE_TIME = Pattern.compile("^(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})\\.(\\d)$");
  private Integer year;
  private Integer month;
  private Integer day;
  private Integer hour;
  private Integer min;
  private Integer sec;
  private Integer ms;

  public Control005(String content) {
    super(Control005Definition.getInstance(), content);
    processContent();
  }

  protected void processContent() {
    // do nothing, this string should not be parsed
    Matcher matcher = DATE_TIME.matcher(content);
    if (matcher.matches()) {
      year = Integer.parseInt(matcher.group(1));
      month = Integer.parseInt(matcher.group(2));
      day = Integer.parseInt(matcher.group(3));
      hour = Integer.parseInt(matcher.group(4));
      min = Integer.parseInt(matcher.group(5));
      sec = Integer.parseInt(matcher.group(6));
      ms = Integer.parseInt(matcher.group(7));
    } else {
      logger.log(Level.WARNING, "005 ({}) does not match the expected pattern", content);
    }
  }

  @Override
  public boolean validate(MarcVersion marcVersion) {
    validationErrors = new ArrayList();
    return isValidMonth() && isValidDay()
      && isValidHour() && isValidMin() && isValidSec();
  }

  private boolean isValidMonth() {
    boolean valid = month >= 1 && month <= 12;
    if (!valid)
      addError(String.format("invalid month: %d", month));

    return valid;
  }

  private boolean isValidDay() {
    boolean valid = day >= 1 && day <= 31;
    if (!valid)
      addError(String.format("invalid day: %d", day));

    return valid;
  }

  private boolean isValidHour() {
    boolean valid = hour >= 1 && hour <= 24;
    if (!valid)
      addError(String.format("invalid hour: %d", hour));

    return valid;
  }

  private boolean isValidMin() {
    boolean valid = min >= 0 && min <= 59;
    if (!valid)
      addError(String.format("invalid minute: %d", min));

    return valid;
  }

  private boolean isValidSec() {
    boolean valid = sec >= 0 && sec <= 59;
    if (!valid)
      addError(String.format("invalid second: %d", sec));

    return valid;
  }

  public Integer getYear() {
    return year;
  }

  public Integer getMonth() {
    return month;
  }

  public Integer getDay() {
    return day;
  }

  public Integer getHour() {
    return hour;
  }

  public Integer getMin() {
    return min;
  }

  public Integer getSec() {
    return sec;
  }

  public Integer getMs() {
    return ms;
  }

  private void addError(String msg) {
    String id = marcRecord != null ? marcRecord.getId() : null;

    validationErrors.add(
      new ValidationError(
        id,
        definition.getTag(),
        ValidationErrorType.CONTROL_POSITION_INVALID_VALUE,
        String.format("%s in '%s'", msg, content),
        definition.getDescriptionUrl())
    );
  }

  @Override
  public String toString() {
    return "Control005{" +
      "content='" + content + '\'' +
      '}';
  }
}