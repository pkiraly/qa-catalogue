package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.controlpositions.LeaderPositions;

import java.util.List;

public class CodeGenerator {

  public void generateCode() {
    // List<ControlSubfield> subfields = Control006Subfields.get(Control008Type.MIXED_MATERIALS);
    List<ControlfieldPositionDefinition> subfields = LeaderPositions.getInstance().getPositionList();
    for (ControlfieldPositionDefinition subfield : subfields) {
      System.err.printf("===== [%s%s] ====%n", subfield.getId().substring(0, 1).toUpperCase(), subfield.getId().substring(1));
      System.err.printf("label = \"%s\";%n", subfield.getLabel());
      System.err.printf("id = \"%s\";%n", subfield.getId());
      System.err.printf("mqTag = \"%s\";%n", subfield.getMqTag());
      System.err.printf("positionStart = %d;%n", subfield.getPositionStart());
      System.err.printf("positionEnd = %d;%n", subfield.getPositionEnd());
      System.err.printf("descriptionUrl = \"https://www.loc.gov/marc/bibliographic/bdleader.html\";%n", subfield.getMqTag());
      if (subfield.getCodes() != null) {
        System.err.printf("codes = Utils.generateCodes(%n");
        int i = 0;
        for (Code code : subfield.getCodes()) {
          i++;
          if (i == subfield.getCodes().size())
            System.err.printf("\"%s\", \"%s\"%n", code.getCode(), code.getLabel());
          else
            System.err.printf("\"%s\", \"%s\",%n", code.getCode(), code.getLabel());
        }
        System.err.printf(");%n");
      }
      if (subfield.isRepeatableContent()) {
        System.err.printf("repeatableContent = true;%n", subfield.getPositionEnd());
        System.err.printf("unitLength = %d;%n", subfield.getUnitLength());
      }
      if (subfield.getDefaultCode() != null)
        System.err.printf("defaultCode = \"%s\";%n", subfield.getDefaultCode());
    }
  }

}
