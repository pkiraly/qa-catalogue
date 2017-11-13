package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.model.SolrFieldType;
import org.apache.commons.cli.ParseException;

public class MarcToSolrParameters extends CommonParameters {

	private String solrUrl = null;
	private boolean doCommit = false;
	private SolrFieldType solrFieldType = SolrFieldType.MARC;

	private boolean isOptionSet = false;

	protected void setOptions() {
		if (!isOptionSet) {
			super.setOptions();
			options.addOption("s", "solrUrl", true, "the URL of Solr server");
			options.addOption("c", "doCommit", false, "send commits to Solr regularly");
			options.addOption("t", "solrFieldType", true,
				"type of Solr fields, could be one of 'marc-tags', 'human-readable', or 'mixed'");
			isOptionSet = true;
		}
	}

	public MarcToSolrParameters(String[] arguments) throws ParseException {
		super(arguments);

		if (cmd.hasOption("solrUrl"))
			solrUrl = cmd.getOptionValue("solrUrl");

		if (cmd.hasOption("doCommit"))
			doCommit = true;

		if (cmd.hasOption("solrFieldType"))
			solrFieldType = SolrFieldType.byCode(cmd.getOptionValue("solrFieldType"));
	}

	public String getSolrUrl() {
		return solrUrl;
	}

	public boolean doCommit() {
		return doCommit;
	}

	public SolrFieldType getSolrFieldType() {
		return solrFieldType;
	}
}
