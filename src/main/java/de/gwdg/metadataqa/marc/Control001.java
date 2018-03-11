package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.ControlField;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.tags.control.Control001Definition;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;

import java.util.*;
import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Control001 extends MarcControlField implements Extractable {

	private static final Logger logger = Logger.getLogger(Control001.class.getCanonicalName());

	public Control001(String content) {
		super(Control001Definition.getInstance(), content);
		process();
	}

	private void process() {
	}

	public String getContent() {
		return content;
	}

	@Override
	public String toString() {
		return "Control001{" +
			"content='" + content + '\'' +
			'}';
	}

}
