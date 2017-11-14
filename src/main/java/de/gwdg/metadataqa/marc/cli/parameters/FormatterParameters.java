package de.gwdg.metadataqa.marc.cli.parameters;

import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

public class FormatterParameters extends CommonParameters {

	private String format = null;
	private int countNr = -1;
	private String search = null;
	private String path = null;
	private String query = null;
	private boolean isOptionSet = false;

	protected void setOptions() {
		if (!isOptionSet) {
			super.setOptions();
			options.addOption("f", "format", true, "specify a format");
			options.addOption("c", "countNr", true, "count number of the record (e.g. 1 means the first record)");
			options.addOption("s", "search", true, "search string ([path]=[value]");
			isOptionSet = true;
		}
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

	@Override
	public String formatParameters() {
		String text = super.formatParameters();
		text += String.format("format: %s\n", format);
		text += String.format("countNr: %s\n", countNr);
		text += String.format("search: %s\n", search);
		return text;
	}
}
