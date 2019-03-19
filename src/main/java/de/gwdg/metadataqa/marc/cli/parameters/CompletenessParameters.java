package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat;
import org.apache.commons.cli.ParseException;

import java.io.Serializable;

public class CompletenessParameters extends CommonParameters implements Serializable {
	public static final String DEFAULT_OUTPUT_DIR = ".";

	private String outputDir = DEFAULT_OUTPUT_DIR;
	private ValidationErrorFormat format = ValidationErrorFormat.COMMA_SEPARATED;
	private boolean isOptionSet;

	protected void setOptions() {
		if (!isOptionSet) {
			super.setOptions();
			options.addOption("t", "outputDir", true, "show record level display");
			options.addOption("r", "format", true, "specify a format");
			isOptionSet = true;
		}
	}

	public CompletenessParameters() {
		super();
	}

	public CompletenessParameters(String[] arguments) throws ParseException {
		super(arguments);

		if (cmd.hasOption("outputDir"))
			outputDir = cmd.getOptionValue("outputDir");

		if (cmd.hasOption("format"))
			for (ValidationErrorFormat registeredFormat : ValidationErrorFormat.values()) {
				if (registeredFormat.getNames().contains(cmd.getOptionValue("format"))) {
					format = registeredFormat;
					break;
				}
			}
	}

	public String getOutputDir() {
		return outputDir;
	}

	public ValidationErrorFormat getFormat() {
		return format;
	}

	@Override
	public String formatParameters() {
		String text = super.formatParameters();
		text += String.format("outputDir: %s%n", outputDir);
		text += String.format("format: %s%n", format.getLabel());
		return text;
	}
}
