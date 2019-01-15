package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat;
import org.apache.commons.cli.*;

import java.io.Serializable;

public class ValidatorParameters extends CommonParameters implements Serializable {
	public static final String DEFAULT_FILE_NAME = "validation-report.txt";

	private String fileName = DEFAULT_FILE_NAME;
	private boolean doSummary = false;
	private ValidationErrorFormat format = ValidationErrorFormat.TEXT;
	private boolean useStandardOutput = false;
	private boolean isOptionSet;

	protected void setOptions() {
		if (!isOptionSet) {
			super.setOptions();
			options.addOption("s", "summary", false, "show summary instead of record level display");
			options.addOption("f", "fileName", true,
				String.format("the report file name (default is '%s')", ValidatorParameters.DEFAULT_FILE_NAME));
			options.addOption("r", "format", true, "specify a format");
			isOptionSet = true;
		}
	}

	public ValidatorParameters() {
		super();
	}

	public ValidatorParameters(String[] arguments) throws ParseException {
		super(arguments);

		if (cmd.hasOption("fileName"))
			fileName = cmd.getOptionValue("fileName");

		if (fileName.equals("stdout"))
			useStandardOutput = true;

		if (cmd.hasOption("format"))
			for (ValidationErrorFormat registeredFormat : ValidationErrorFormat.values()) {
				if (registeredFormat.getNames().contains(cmd.getOptionValue("format"))) {
					format = registeredFormat;
					break;
				}
			}

		doSummary = cmd.hasOption("summary");
	}

	public String getFileName() {
		return fileName;
	}

	public boolean doSummary() {
		return doSummary;
	}

	public boolean useStandardOutput() {
		return useStandardOutput;
	}

	public ValidationErrorFormat getFormat() {
		return format;
	}

	@Override
	public String formatParameters() {
		String text = super.formatParameters();
		text += String.format("summary: %s%n", doSummary);
		text += String.format("fileName: %s%n", fileName);
		text += String.format("format: %s%n", format.getLabel());
		return text;
	}
}
