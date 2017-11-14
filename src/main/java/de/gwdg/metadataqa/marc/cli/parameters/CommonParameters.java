package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.Leader;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

public class CommonParameters {

	protected String[] args;

	protected MarcVersion marcVersion = MarcVersion.MARC21;
	protected boolean doHelp;
	protected boolean doLog = true;
	private int limit = -1;
	private int offset = -1;
	private String id = null;
	protected Leader.Type defaultRecordType = null;

	protected Options options = new Options();
	protected static CommandLineParser parser = new DefaultParser();
	protected CommandLine cmd;
	private boolean isOptionSet = false;

	protected void setOptions() {
		if (!isOptionSet) {
			options.addOption("m", "marcVersion", true, "MARC version ('OCLC' or 'DNB')");
			options.addOption("h", "help", false, "display help");
			options.addOption("n", "nolog", false, "do not display log messages");
			options.addOption("l", "limit", true, "limit the number of records to process");
			options.addOption("o", "offset", true, "the first record to process");
			options.addOption("i", "id", true, "the MARC identifier (content of 001)");
			options.addOption("d", "defaultRecordType", true, "the default record type if the record's type is undetectable");
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

		if (cmd.hasOption("limit"))
			limit = Integer.parseInt(cmd.getOptionValue("limit"));

		if (cmd.hasOption("offset"))
			offset = Integer.parseInt(cmd.getOptionValue("offset"));

		if (offset > -1 && limit > -1)
			limit += offset;

		if (cmd.hasOption("id"))
			id = cmd.getOptionValue("id");

		if (cmd.hasOption("defaultRecordType"))
			defaultRecordType = Leader.Type.valueOf(cmd.getOptionValue("defaultRecordType"));

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

	public int getLimit() {
		return limit;
	}

	public int getOffset() {
		return offset;
	}

	public boolean hasId() {
		return StringUtils.isNotBlank(id);
	}

	public String getId() {
		return id;
	}

	public Leader.Type getDefaultRecordType() {
		return defaultRecordType;
	}

	public String formatParameters() {
		String text = "";
		text += String.format("marcVersion: %s, %s\n", marcVersion.getCode(), marcVersion.getLabel());
		text += String.format("limit: %d\n", limit);
		text += String.format("offset: %s\n", offset);
		text += String.format("MARC files: %s\n", StringUtils.join(args, ", "));
		text += String.format("id: %s\n", id);
		text += String.format("defaultRecordType: %s\n", defaultRecordType);

		return text;
	}
}