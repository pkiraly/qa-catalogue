package de.gwdg.metadataqa.marc.utils.unimarc;

import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.definition.general.codelist.CodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UnimarcCodeList extends CodeList {

    public UnimarcCodeList() {
        this.codes = new ArrayList<>();

        // TODO ask why we have both codes and index
        this.index = new HashMap<>();
    }
    public void setCodes(List<EncodedValue> codes) {
        this.codes = codes;
        indexCodes();
    }
}
