package de.gwdg.metadataqa.marc.cli.parameters;

import org.apache.commons.cli.ParseException;

public class FormatterParameters extends CommonParameters {

	private String format = null;
	private String id = null;
	private boolean isOptionSet = false;

	protected void setOptions() {
		if (!isOptionSet) {
			super.setOptions();
			options.addOption("f", "format", true, "specify a format");
			options.addOption("i", "id", true, "MARC version ('OCLC' or 'DNB')");
			isOptionSet = true;
		}
	}

	public FormatterParameters(String[] arguments) throws ParseException {
		super(arguments);

		if (cmd.hasOption("format"))
			format = cmd.getOptionValue("format");

		if (cmd.hasOption("id"))
			id = cmd.getOptionValue("id");
	}

	public String getFormat() {
		return format;
	}

	public String getId() {
		return id;
	}
}
