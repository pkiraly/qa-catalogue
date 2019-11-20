package de.gwdg.metadataqa.marc.model.validation;

import com.opencsv.CSVWriter;
import de.gwdg.metadataqa.marc.Utils;
import org.apache.commons.lang3.StringUtils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidationErrorFormatter {

  public static String format(List<ValidationError> errors, ValidationErrorFormat format) {
    return format(errors, format, false);
  }

  public static String format(List<ValidationError> errors, ValidationErrorFormat format, boolean trimId) {
    StringBuffer message = new StringBuffer();
    switch (format) {
      case TAB_SEPARATED:
      case COMMA_SEPARATED:
        for (ValidationError error : errors) {
          error.setTrimId(trimId);
          message.append(format(error, format) + "\n");
        }
        break;
      case TEXT:
        String id = errors.get(0).getRecordId();
        if (trimId)
          id = id.trim();
        message.append(String.format("%s in '%s':%n",
          (errors.size() == 1 ? "Error" : "Errors"),
          id
        ));
        for (ValidationError error : errors) {
          message.append(String.format("\t%s%n", formatTextWithoutId(error)));
        }
        break;
      default:
        break;
    }

    return message.toString();
  }

  /**
   * Creates a [recordId]<separator>[errorId1];[errorId2]...\n string
   * @param recordId
   * @param format
   * @param errorIds
   * @return
   */
  public static String formatSimple(String recordId, ValidationErrorFormat format, List<Integer> errorIds) {
    char separator = format.equals(ValidationErrorFormat.TAB_SEPARATED) ? '\t' : ',';
    return Utils.createRow(separator, recordId, StringUtils.join(errorIds, ';'));
  }

  public static String formatHeader(ValidationErrorFormat format) {
    StringBuffer message = new StringBuffer();
    if (format.equals(ValidationErrorFormat.TAB_SEPARATED)) {
      message.append(createCvsRow(headerArray(), '\t'));
    } else if (format.equals(ValidationErrorFormat.COMMA_SEPARATED)) {
      message.append(createCvsRow(headerArray(), ','));
    }
    return message.toString();
  }


  public static String format(ValidationError error, ValidationErrorFormat format) {
    StringBuffer message = new StringBuffer();
    if (format.equals(ValidationErrorFormat.TAB_SEPARATED)) {
      message.append(createCvsRow(asArray(error), '\t'));
    } else if (format.equals(ValidationErrorFormat.COMMA_SEPARATED)) {
      message.append(createCvsRow(asArray(error), ','));
    }
    return message.toString();
  }

  public static List<String> formatForSummary(List<ValidationError> validationErrors,
                                ValidationErrorFormat format) {
    List<String> messages = new ArrayList<>();

    for (ValidationError error : validationErrors)
      messages.add(formatForSummary(error, format));

    return messages;
  }

  public static String formatHeaderForSummary(ValidationErrorFormat format) {
    String message = "";
    switch (format) {
      case TAB_SEPARATED:
        message = createCvsRow(headerForSummary(), '\t');
        break;
      case COMMA_SEPARATED:
      case TEXT:
        message = createCvsRow(headerForSummary(), ',');
      default:
        break;
    }
    return message;
  }

  public static String formatHeaderForCollector(ValidationErrorFormat format) {
    String message = "";
    switch (format) {
      case TAB_SEPARATED:
        message = createCvsRow(headerForCollector(), '\t');
        break;
      case COMMA_SEPARATED:
      case TEXT:
        message = createCvsRow(headerForCollector(), ',');
      default:
        break;
    }
    return message;
  }

  public static String formatForSummary(ValidationError error, ValidationErrorFormat format) {
    String message = "";
    switch (format) {
      case TAB_SEPARATED:
        message = createCvsRow(asArrayWithoutId(error), '\t');
        break;
      case COMMA_SEPARATED:
        message = createCvsRow(asArrayWithoutId(error), ',');
        break;
      case TEXT:
        message = formatTextWithoutId(error);
      default:
        break;
    }
    return message;
  }

  private static String createCvsRow(String[] strings, char separator) {
    StringWriter stringWriter = new StringWriter();
    CSVWriter csvWriter = new CSVWriter(stringWriter, separator, '"');
    csvWriter.writeNext(strings);
    String row = stringWriter.toString().trim();
    if (row.contains("\\")) {
      row = row.replace("\\", "\\\\");
    }
    return row;
  }

  private static String formatTextWithoutId(ValidationError error) {
    return String.format("%s: %s '%s' (%s)",
      error.getMarcPath(),
      error.getType().getMessage(),
      error.getMessage(),
      error.getUrl()
    );
  }

  private static String[] headerForSummary() {
    return new String[]{"id", "MarcPath", "type", "message", "url", "count"};
  }

  private static String[] headerForCollector() {
    return new String[]{"errorId", "recordIds"};
  }

  private static String[] asArrayWithoutId(ValidationError error) {
    return new String[]{
      error.getMarcPath(),
      error.getType().getMessage(),
      error.getMessage(),
      error.getUrl()
    };
  }

  private static List<String> asListWithoutId(ValidationError error) {
    return Arrays.asList(
      error.getMarcPath(),
      error.getType().getMessage(),
      error.getMessage(),
      error.getUrl()
    );
  }

  private static List<String> asList(ValidationError error) {
    return Arrays.asList(
      error.getRecordId(),
      error.getMarcPath(),
      error.getType().getMessage(),
      error.getMessage(),
      error.getUrl()
    );
  }

  private static String[] asArray(ValidationError error) {
    return new String[]{
      error.getRecordId(),
      error.getMarcPath(),
      error.getType().getMessage(),
      error.getMessage(),
      error.getUrl()
    };
  }

  private static String[] headerArray() {
    return new String[]{"recordId", "MarcPath", "type", "message", "url"};
  }
}
