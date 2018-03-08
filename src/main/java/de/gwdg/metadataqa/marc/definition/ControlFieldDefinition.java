package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.definition.controlsubfields.ControlSubfieldList;
import de.gwdg.metadataqa.marc.definition.controltype.ControlType;

import java.util.List;
import java.util.Map;

public abstract class ControlFieldDefinition extends DataFieldDefinition {

	protected Map<? extends ControlType, List<ControlSubfieldDefinition>> subfields;

}
