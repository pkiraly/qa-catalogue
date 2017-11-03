package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.apache.commons.cli.*;

public class ValidatorParameters {
	public static final String DEFAULT_FILE_NAME = "validation-report.txt";

	private MarcVersion marcVersion = MarcVersion.MARC21;
	private int limit = -1;
	private int offset = -1;
	private String fileName = DEFAULT_FILE_NAME;
	private boolean doHelp;
	private boolean doSummary;
	private String[] args;

	private static Options options = new Options();
	private static CommandLineParser parser = new DefaultParser();

	static {
		options.addOption("s", "summary", false, "show summary instead of record level display");
		options.addOption("m", "marcVersion", true, "MARC version ('OCLC' or DNB')");
		options.addOption("l", "limit", true, "limit the number of records to process");
		options.addOption("o", "offset", true, "the first record to process");
		options.addOption("f", "fileName", true,
			String.format("the report file name (default is %s)", ValidatorParameters.DEFAULT_FILE_NAME));
		options.addOption("h", "help", false, "display help");
	}

	public ValidatorParameters(String[] arguments) throws ParseException {
		CommandLine cmd = parser.parse(options, arguments);

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

		doHelp = cmd.hasOption("help");
		args = cmd.getArgs();
		doSummary = cmd.hasOption("summary");
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

	public boolean doHelp() {
		return doHelp;
	}

	public boolean doSummary() {
		return doSummary;
	}

	public String[] getArgs() {
		return args;
	}

	public static Options getOptions() {
		return options;
	}
}
