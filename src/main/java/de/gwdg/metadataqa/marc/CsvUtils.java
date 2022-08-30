package de.gwdg.metadataqa.marc;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class CsvUtils {

  public static String createCsv(List<? extends Serializable> values) {
    return createCsv(asArray(values));
  }

  public static String createCsv(String[] values) {
    String csv = null;

    try (StringWriter sw = new StringWriter(); CSVWriter csvWriter = new CSVWriter(sw)) {
      csvWriter.writeNext(cleanRow(values), false);

      csv = sw.toString();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return csv;
  }

  private static String[] asArray(List<? extends Serializable> values) {
    List<String> strings = new ArrayList<>();
    for (Serializable value : values) {
      if (value instanceof String) {
        strings.add((String) value);
      } else if (value == null) {
        strings.add("");
      } else {
        strings.add(value.toString());
      }
    }
    return strings.toArray(new String[strings.size()]);
  }


  private static String[] cleanRow(String[] values) {
    List<Object> quoted = new ArrayList<>();
    for (String value : values) {
      quoted.add(cleanCell(value));
    }
    return quoted.toArray(new String[quoted.size()]);
  }

  private static Object cleanCell(Object value) {
    if (value instanceof String) {
      return ((String) value).replace("\n", "\\n");
    }
    return value;
  }

}
