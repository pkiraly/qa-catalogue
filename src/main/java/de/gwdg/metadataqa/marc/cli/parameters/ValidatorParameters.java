package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.apache.commons.cli.*;

public class ValidatorParameters extends CommonParameters {
	public static final String DEFAULT_FILE_NAME = "validation-report.txt";

	private MarcVersion marcVersion = MarcVersion.MARC21;
	private int limit = -1;
	private int offset = -1;
	private String fileName = DEFAULT_FILE_NAME;
	private boolean doHelp;
	private boolean doSummary;
	private String[] args;
	private boolean useStandardOutput = false;
	private boolean isOptionSet;

	protected void setOptions() {
		if (!isOptionSet) {
			super.setOptions();
			options.addOption("s", "summary", false, "show summary instead of record level display");
			options.addOption("l", "limit", true, "limit the number of records to process");
			options.addOption("o", "offset", true, "the first record to process");
			options.addOption("f", "fileName", true,
				String.format("the report file name (default is '%s')", ValidatorParameters.DEFAULT_FILE_NAME));
			isOptionSet = true;
		}
	}

	public ValidatorParameters(String[] arguments) throws ParseException {
		super(arguments);

		if (cmd.hasOption("fileName"))
			fileName = cmd.getOptionValue("fileName");

		if (fileName.equals("stdout"))
			useStandardOutput = true;

		if (cmd.hasOption("limit"))
			limit = Integer.parseInt(cmd.getOptionValue("limit"));

		if (cmd.hasOption("offset"))
			offset = Integer.parseInt(cmd.getOptionValue("offset"));

		if (offset > -1 && limit > -1)
			limit += offset;

		doSummary = cmd.hasOption("summary");
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

	public boolean doSummary() {
		return doSummary;
	}

	public boolean useStandardOutput() {
		return useStandardOutput;
	}
}
