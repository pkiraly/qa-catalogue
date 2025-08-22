package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.analysis.FieldWithScheme;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PicaSubjectManager {
  private static List<FieldWithScheme> fields;
  private static List<String> tags;
  private static final String schemaFile = Paths.get("src/main/resources/pica/k10plus-subjects.tsv")
                                                .toAbsolutePath().toString();

  public static List<FieldWithScheme> readFieldsWithScheme() {
    if (fields == null)
      read();
    return fields;
  }

  public static List<String> getTags() {
    if (tags == null)
      read();
    return tags;
  }

  public static String getSchemaFile() {
    return schemaFile;
  }

  private static void read() {
    fields = new ArrayList<>();
    tags = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(schemaFile))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split("\\t");
        String tag = parts[0];
        if (!parts[1].equals(""))
          tag += "/" + parts[1];
        tags.add(tag);
        fields.add(new FieldWithScheme(tag, parts[2]));
      }
    } catch (IOException e) {
      e.getLocalizedMessage();
    }
  }
}
