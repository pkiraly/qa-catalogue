package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.utils.SchemaSpec;
import de.gwdg.metadataqa.marc.utils.marcspec.legacy.MarcSpec;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaSpec;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FormatterParameters extends CommonParameters {
  public static final String DEFAULT_FILE_NAME = "extracted.csv";

  private String format = null;
  private int countNr = -1;
  private String search = null;
  private String path = null;
  private String query = null;
  private List<SchemaSpec> selector = null;
  private boolean withId = false;
  private String separator = "\t";
  private String fileName = DEFAULT_FILE_NAME;

  private boolean isOptionSet = false;

  @Override
  protected void setOptions() {
    if (!isOptionSet) {
      super.setOptions();
      options.addOption("f", "format", true, "specify a format");
      options.addOption("c", "countNr", true, "count number of the record (e.g. 1 means the first record)");
      options.addOption("s", "search", true, "search string ([path]=[value])");
      options.addOption("l", "selector", true, "selectors");
      options.addOption("w", "withId", false, "the generated CSV should contain record ID as first field");
      options.addOption("p", "separator", true, "separator between the parts (default: TAB)");
      options.addOption("e", "fileName", true, String.format("output file (default: %s)", DEFAULT_FILE_NAME));
      isOptionSet = true;
    }
  }

  public FormatterParameters() {
    super();
  }

  public FormatterParameters(String[] arguments) throws ParseException {
    super(arguments);

    if (cmd.hasOption("format"))
      format = cmd.getOptionValue("format");

    if (cmd.hasOption("countNr"))
      countNr = Integer.parseInt(cmd.getOptionValue("countNr"));

    if (cmd.hasOption("search")) {
      search = cmd.getOptionValue("search");
      String[] parts = search.split("=", 2);
      path = parts[0];
      query = parts[1];
    }

    if (cmd.hasOption("selector")) {
      String rawSelector = cmd.getOptionValue("selector");
      String[] rawSelectors = rawSelector.split(";");
      selector = new ArrayList<>();
      if (getSchemaType().equals(SchemaType.PICA)) {
        for (String _rawSelector : rawSelectors)
          selector.add(new PicaSpec(_rawSelector));
      } else {
        for (String _rawSelector : rawSelectors)
          selector.add(new MarcSpec(_rawSelector));
      }
    }

    withId = cmd.hasOption("withId");

    if (cmd.hasOption("separator"))
      separator = cmd.getOptionValue("separator");

    if (cmd.hasOption("fileName"))
      fileName = cmd.getOptionValue("fileName");
  }

  public String getFormat() {
    return format;
  }

  public int getCountNr() {
    return countNr;
  }

  public String getSearch() {
    return search;
  }

  public boolean hasSearch() {
    return StringUtils.isNotBlank(path) && StringUtils.isNotBlank(query);
  }

  public String getPath() {
    return path;
  }

  public String getQuery() {
    return query;
  }

  public List<SchemaSpec> getSelector() {
    return selector;
  }

  public boolean hasSelector() {
    return selector != null && !selector.isEmpty();
  }

  public boolean withId() {
    return withId;
  }

  public String getSeparator() {
    return separator;
  }

  public String getFileName() {
    return fileName;
  }

  @Override
  public String formatParameters() {
    String text = super.formatParameters();
    text += String.format("format: %s%n", format);
    text += String.format("countNr: %s%n", countNr);
    text += String.format("search: %s%n", search);
    text += String.format("withId: %s%n", withId);
    text += String.format("separator: %s%n", separator);
    text += String.format("outputFile: %s%n", fileName);
    return text;
  }

}
