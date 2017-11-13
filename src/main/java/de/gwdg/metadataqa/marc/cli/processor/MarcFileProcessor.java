package de.gwdg.metadataqa.marc.cli.processor;

import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;

import java.io.IOException;

public interface MarcFileProcessor {

	CommonParameters getParameters();
	void processRecord(MarcRecord marcRecord, int recordNumber) throws IOException;
	void beforeIteration();
	void fileOpened();
	void fileProcessed();
	void afterIteration();
}
