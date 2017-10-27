package de.gwdg.metadataqa.marc.cli.parameters;

import org.apache.commons.cli.CommandLine;

public class FormatterParameters {

	private String format = null;
	private String id = null;

	public FormatterParameters(CommandLine cmd) {
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
