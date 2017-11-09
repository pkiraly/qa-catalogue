package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.apache.commons.cli.*;

public class CommonParameters {

	protected String[] args;

	protected MarcVersion marcVersion = MarcVersion.MARC21;
	protected boolean doHelp;
	protected boolean doLog = true;

	protected Options options = new Options();
	protected static CommandLineParser parser = new DefaultParser();
	protected CommandLine cmd;
	private boolean isOptionSet = false;

	protected void setOptions() {
		if (!isOptionSet) {
			options.addOption("m", "marcVersion", true, "MARC version ('OCLC' or 'DNB')");
			options.addOption("h", "help", false, "display help");
			options.addOption("n", "nolog", false, "do not display log messages");
			isOptionSet = true;
		}
	}

	public CommonParameters() {}

	public CommonParameters(String[] arguments)  throws ParseException {
		cmd = parser.parse(getOptions(), arguments);

		if (cmd.hasOption("marcVersion"))
			marcVersion = MarcVersion.byCode(cmd.getOptionValue("marcVersion"));

		doHelp = cmd.hasOption("help");

		doLog = !cmd.hasOption("nolog");

		args = cmd.getArgs();
	}

	public Options getOptions() {
		if (!isOptionSet)
			setOptions();
		return options;
	}

	public MarcVersion getMarcVersion() {
		return marcVersion;
	}

	public boolean doHelp() {
		return doHelp;
	}

	public boolean doLog() {
		return doLog;
	}

	public String[] getArgs() {
		return args;
	}
}
