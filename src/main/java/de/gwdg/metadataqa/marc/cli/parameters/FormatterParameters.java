package de.gwdg.metadataqa.marc.cli.parameters;

import org.apache.commons.cli.ParseException;

public class FormatterParameters extends CommonParameters {

	private String format = null;
	private String id = null;
	private int countNr = -1;
	private boolean isOptionSet = false;

	protected void setOptions() {
		if (!isOptionSet) {
			super.setOptions();
			options.addOption("f", "format", true, "specify a format");
			options.addOption("i", "id", true, "the MARC identifier (content of 001)");
			options.addOption("c", "countNr", true, "count number of the record (e.g. 1 means the first record)");
			isOptionSet = true;
		}
	}

	public FormatterParameters(String[] arguments) throws ParseException {
		super(arguments);

		if (cmd.hasOption("format"))
			format = cmd.getOptionValue("format");

		if (cmd.hasOption("id"))
			id = cmd.getOptionValue("id");

		if (cmd.hasOption("countNr"))
			countNr = Integer.parseInt(cmd.getOptionValue("countNr"));
	}

	public String getFormat() {
		return format;
	}

	public String getId() {
		return id;
	}

	public int getCountNr() {
		return countNr;
	}
}
