package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.tags.control.Control005Definition;

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
    return isValidMonth() && isValidDay()
      && isValidHour() && isValidMin() && isValidSec();
  }

  private boolean isValidMonth() {
    return month >= 1 && month <= 12;
  }

  private boolean isValidDay() {
    return day >= 1 && day <= 31;
  }

  private boolean isValidHour() {
    return day >= 1 && day <= 24;
  }

  private boolean isValidMin() {
    return min >= 0 && min <= 59;
  }

  private boolean isValidSec() {
    return min >= 0 && min <= 59;
  }

  @Override
  public String toString() {
    return "Control005{" +
        "content='" + content + '\'' +
        '}';
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
}