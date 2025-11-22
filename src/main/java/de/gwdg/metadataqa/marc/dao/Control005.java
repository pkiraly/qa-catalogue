package de.gwdg.metadataqa.marc.dao;

import de.gwdg.metadataqa.marc.Extractable;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.tags.control.Control005Definition;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class Control005  extends SimpleControlField implements Extractable {
  private static final Logger logger = Logger.getLogger(Control005.class.getCanonicalName());
  private static final Pattern DATE_TIME = Pattern.compile(
      "^(?<year>\\d{4})(?<month>\\d{2})(?<day>\\d{2})(?<hour>\\d{2})" +
      "(?<minute>\\d{2})(?<second>\\d{2})\\.(?<decisecond>\\d)$");

  // TODO discuss why this contains two spaces at the end. I guess it's because of the remaining tenth of a second
  private static final Pattern DATE_ONLY = Pattern.compile("^(?<year>\\d{4})(?<month>\\d{2})(?<day>\\d{2})(?<hour>[\\d ]{2})(?<minute>[\\d ]{2})(?<second>[\\d ]{2}) {2}$");
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

  public Control005(String content, BibliographicRecord marcRecord) {
    super(Control005Definition.getInstance(), content);
    this.marcRecord = marcRecord;
    processContent();
  }

  protected void processContent() {
    // do nothing, this string should not be parsed
    Matcher matcher = DATE_TIME.matcher(content);
    if (matcher.matches()) {
      parseDateAndTime(matcher);
      return;
    }
    initializationErrors.add(createError("The field value does not match the expected pattern"));

    // TODO: handle values such as '20131127        '. Not sure if this TODO has been addressed, as it seems that the code below actually handles this case
    // I'm also not sure why there's an initialization error before this, and I'm not sure why this hasn't been used
    // for the previous case, too
    matcher = DATE_ONLY.matcher(content);
    if (matcher.matches()) {
      parseDateAndOptionalTime(matcher);
      return;
    }

    // The following removes all (at most 10) dots and spaces from the end of the string
    // and also removes all dots from within the string
    String cleanContent = content.replaceAll("[. ]{1,10}$", "").replace(".", "");

    // This has the potential for the following bug:
    // In case the cleanContent is just '20' and all spaces behind, it will try to extract the month 20,
    // which doesn't seem like the desired behaviour
    if (cleanContent.length() >= 4) {
      year = extractRaw(cleanContent, 4, "year");
      cleanContent = cleanContent.substring(4);
    }
    if (cleanContent.length() >= 2) {
      month = extractRaw(cleanContent, 2, "month");
      cleanContent = cleanContent.substring(2);
    }
    if (cleanContent.length() >= 2) {
      day = extractRaw(cleanContent, 2, "day");
      cleanContent = cleanContent.substring(2);
    }
    if (cleanContent.length() >= 2) {
      hour = extractRaw(cleanContent, 2, "hour");
      cleanContent = cleanContent.substring(2);
    }
    if (cleanContent.length() >= 2) {
      min = extractRaw(cleanContent, 2, "min");
      cleanContent = cleanContent.substring(2);
    }
    if (cleanContent.length() >= 2) {
      sec = extractRaw(cleanContent, 2, "sec");
    }
  }

  private void parseDateAndTime(Matcher matcher) {
    year = Integer.parseInt(matcher.group("year"));
    month = Integer.parseInt(matcher.group("month"));
    day = Integer.parseInt(matcher.group("day"));
    hour = Integer.parseInt(matcher.group("hour"));
    min = Integer.parseInt(matcher.group("minute"));
    sec = Integer.parseInt(matcher.group("second"));
    ms = Integer.parseInt(matcher.group("decisecond"));
  }

  private void parseDateAndOptionalTime(Matcher matcher) {
    year = Integer.parseInt(matcher.group("year"));
    month = Integer.parseInt(matcher.group("month"));
    day = Integer.parseInt(matcher.group("day"));

    String stringHour = matcher.group("hour");
    if (StringUtils.isNumeric(stringHour)) {
      hour = Integer.parseInt(stringHour);
    }

    String stringMinute = matcher.group("minute");
    if (StringUtils.isNumeric(stringMinute)) {
      min = Integer.parseInt(stringMinute);
    }

    String stringSecond = matcher.group("second");
    if (StringUtils.isNumeric(stringSecond)) {
      sec = Integer.parseInt(stringSecond);
    }
  }

  /**
   * Extracts an integer from the beginning of the string until the given end position.
   * @param cleanContent The string to extract the integer from
   * @param end The end position of the extracted integer
   * @param field The name of the field to be used in the error message
   * @return The extracted integer
   */
  private Integer extractRaw(String cleanContent, int end, String field) {
    String text = cleanContent.substring(0, end);
    Integer data = null;
    try {
      data = Integer.parseInt(text);
    } catch (NumberFormatException e) {
      String id = marcRecord != null ? String.format("#%s) ", marcRecord.getId()) : "";
      logger.log(Level.SEVERE, "{0}Bad input for {1}: {2}", new Object[]{id, field, text});
      initializationErrors.add(createError(String.format("invalid %s: %s", field, text)));
    }
    return data;
  }

  @Override
  public boolean validate(MarcVersion marcVersion) {
    validationErrors = new ArrayList<>();
    if (!initializationErrors.isEmpty())
      validationErrors.addAll(initializationErrors);
    return isValidMonth() && isValidDay()
      && isValidHour() && isValidMin() && isValidSec();
  }

  private boolean isValidMonth() {
    boolean valid = month != null && month >= 1 && month <= 12;
    if (!valid)
      addError(String.format("invalid month: %d", month));

    return valid;
  }

  private boolean isValidDay() {
    boolean valid = day != null && day >= 1 && day <= 31;
    if (!valid)
      addError(String.format("invalid day: %d", day));

    return valid;
  }

  private boolean isValidHour() {
    boolean valid = hour != null && hour >= 0 && hour <= 23;
    if (!valid)
      addError(String.format("invalid hour: %d", hour));

    return valid;
  }

  private boolean isValidMin() {
    boolean valid = min != null && min >= 0 && min <= 59;
    if (!valid)
      addError(String.format("invalid minute: %d", min));

    return valid;
  }

  private boolean isValidSec() {
    boolean valid = sec != null && sec >= 0 && sec <= 59;
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
    validationErrors.add(createError(msg));
  }

  private ValidationError createError(String msg) {
    String id = marcRecord != null ? marcRecord.getId() : null;
    return new ValidationError(
      id,
      definition.getTag(),
      ValidationErrorType.CONTROL_POSITION_INVALID_VALUE,
      String.format("%s in '%s'", msg, content),
      definition.getDescriptionUrl()
    );
  }

  @Override
  public String toString() {
    return "Control005{" +
      "content='" + content + '\'' +
      '}';
  }
}