package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.apache.commons.cli.CommandLine;

public class ValidatorParameters {
	public static final String DEFAULT_FILE_NAME = "validation-report.txt";

	private MarcVersion marcVersion = null;
	private int limit = -1;
	private int offset = -1;
	private String fileName = DEFAULT_FILE_NAME;

	public ValidatorParameters(CommandLine cmd) {
		if (cmd.hasOption("marcVersion"))
			marcVersion = MarcVersion.byCode(cmd.getOptionValue("marcVersion"));

		if (cmd.hasOption("fileName"))
			fileName = cmd.getOptionValue("fileName");

		if (cmd.hasOption("limit"))
			limit = Integer.parseInt(cmd.getOptionValue("limit"));

		if (cmd.hasOption("offset"))
			offset = Integer.parseInt(cmd.getOptionValue("offset"));

		if (offset > -1 && limit > -1)
			limit += offset;
	}

	public MarcVersion getMarcVersion() {
		return marcVersion;
	}

	public String getFileName() {
		return fileName;
	}

	public int getLimit() {
		return limit;
	}

	public int getOffset() {
		return offset;
	}
}
