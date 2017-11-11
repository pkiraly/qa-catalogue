package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

public class MarcToSolrParameters extends CommonParameters {

	private String solrUrl = null;
	private boolean doCommit = false;
	private boolean isOptionSet = false;

	protected void setOptions() {
		if (!isOptionSet) {
			super.setOptions();
			options.addOption("s", "solrUrl", true, "the URL of Solr server");
			options.addOption("c", "doCommit", false, "send commits to Solr regularly");
			isOptionSet = true;
		}
	}

	public MarcToSolrParameters(String[] arguments) throws ParseException {
		super(arguments);

		if (cmd.hasOption("solrUrl"))
			solrUrl = cmd.getOptionValue("solrUrl");

		if (cmd.hasOption("doCommit"))
			doCommit = true;
	}

	public String getSolrUrl() {
		return solrUrl;
	}

	public boolean doCommit() {
		return doCommit;
	}
}
