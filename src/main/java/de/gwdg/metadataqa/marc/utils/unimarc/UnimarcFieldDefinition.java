package de.gwdg.metadataqa.marc.utils.unimarc;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;

import java.util.List;
import java.util.Map;


/**
 * Represents a field definition in the Unimarc schema.
 */
public class UnimarcFieldDefinition extends DataFieldDefinition {

    private final boolean required;

    public UnimarcFieldDefinition(String tag, String label, boolean repeatable, boolean required) {
        this.tag = tag;
        this.label = label;
        this.cardinality = repeatable ? Cardinality.Repeatable : Cardinality.Nonrepeatable;
        this.required = required;
    }

    public void setSubfieldDefinitions(List<SubfieldDefinition> subfieldDefinitions) {
        this.subfields = subfieldDefinitions;
        for (SubfieldDefinition subfieldDefinition : this.subfields) {
            subfieldDefinition.setParent(this);
        }

        this.indexSubfields();
    }

    public Map<String, SubfieldDefinition> getSubfieldDefinitions() {
        return subfieldIndex;
    }

    public void setInd1(Indicator ind1) {
        this.ind1 = ind1;

        if (ind1 != null) {
            ind1.setParent(this);
            ind1.setIndicatorFlag("ind1");
        }
    }

    public void setInd2(Indicator ind2) {
        this.ind2 = ind2;

        if (ind2 != null) {
            ind2.setParent(this);
            ind2.setIndicatorFlag("ind2");
        }
    }

    public boolean isRepeatable() {
        return cardinality == Cardinality.Repeatable;
    }

    public boolean isRequired() {
        return required;
    }
}
