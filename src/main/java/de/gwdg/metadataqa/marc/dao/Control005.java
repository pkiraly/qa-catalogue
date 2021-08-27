package de.gwdg.metadataqa.marc.dao;

import de.gwdg.metadataqa.marc.Extractable;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.tags.control.Control005Definition;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Control005  extends SimpleControlField implements Extractable {

  private static final Logger logger = Logger.getLogger(Control005.class.getCanonicalName());
  private static final Pattern DATE_TIME = Pattern.compile("^(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})\\.(\\d)$");
  private static final Pattern DATE_ONLY = Pattern.compile("^(\\d{4})(\\d{2})(\\d{2})([\\d ]{2})([\\d ]{2})([\\d ]{2})  $");
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

  public Control005(String content, MarcRecord marcRecord) {
    super(Control005Definition.getInstance(), content);
    this.marcRecord = marcRecord;
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
      initializationErrors.add(createError("The field value does not match the expected pattern"));

      // TODO: handle values such as '20131127        '
      matcher = DATE_ONLY.matcher(content);
      if (matcher.matches()) {
        year = Integer.parseInt(matcher.group(1));
        month = Integer.parseInt(matcher.group(2));
        day = Integer.parseInt(matcher.group(3));
        if (StringUtils.isNumeric(matcher.group(4)))
          hour = Integer.parseInt(matcher.group(4));
        if (StringUtils.isNumeric(matcher.group(5)))
          min = Integer.parseInt(matcher.group(5));
        if (StringUtils.isNumeric(matcher.group(6)))
          sec = Integer.parseInt(matcher.group(6));
      } else {
        String cleanContent = content.replaceAll("[\\. ]*$", "").replaceAll("\\.", "");
        // logger.warning(String.format("005 ('%s') does not match the expected pattern", content));
        if (cleanContent.length() >= 4) {
          year = extractRaw(cleanContent, 4, "year");
          cleanContent = cleanContent.substring(4);
        }
        if (cleanContent.length() >= 2) {
          month = extractRaw(cleanContent, 2, "month"); // Integer.parseInt(cleanContent.substring(0, 2));
          cleanContent = cleanContent.substring(2);
        }
        if (cleanContent.length() >= 2) {
          day = extractRaw(cleanContent, 2, "day"); // Integer.parseInt(cleanContent.substring(0, 2));
          cleanContent = cleanContent.substring(2);
        }
        if (cleanContent.length() >= 2) {
          hour = extractRaw(cleanContent, 2, "hour"); // Integer.parseInt(cleanContent.substring(0, 2));
          cleanContent = cleanContent.substring(2);
        }
        if (cleanContent.length() >= 2) {
          min = extractRaw(cleanContent, 2, "min"); // Integer.parseInt(cleanContent.substring(0, 2));
          cleanContent = cleanContent.substring(2);
        }
        if (cleanContent.length() >= 2) {
          sec = extractRaw(cleanContent, 2, "sec"); // Integer.parseInt(cleanContent.substring(0, 2));
          cleanContent = cleanContent.substring(2);
        }
      }
    }
  }

  private Integer extractRaw(String cleanContent, int end, String field) {
    String text = cleanContent.substring(0, end);
    Integer data = null;
    try {
      data = Integer.parseInt(text);
    } catch (NumberFormatException e) {
      String id = marcRecord != null ? String.format("#%s) ", marcRecord.getId()) : "";
      logger.severe(String.format("%sBad input for %s: %s", id, field, text));
      initializationErrors.add(createError(String.format("invalid %s: %s", field, text)));
    }
    return data;
  }

  @Override
  public boolean validate(MarcVersion marcVersion) {
    validationErrors = new ArrayList();
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
    boolean valid = hour != null && hour >= 1 && hour <= 24;
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