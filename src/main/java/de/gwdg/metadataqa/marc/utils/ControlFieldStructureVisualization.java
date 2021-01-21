package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.definition.controlpositions.Control006Positions;
import de.gwdg.metadataqa.marc.definition.controlpositions.Control007Positions;
import de.gwdg.metadataqa.marc.definition.controlpositions.Control008Positions;
import de.gwdg.metadataqa.marc.definition.controltype.Control007Category;
import de.gwdg.metadataqa.marc.definition.controltype.Control008Type;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;

import java.util.List;
import java.util.Map;

public class ControlFieldStructureVisualization {
  public static void main(String[] args) {
    String undefPattern = "\\";

    Map<String, List<ControlfieldPositionDefinition>> allSubfields =
      Control008Positions.getInstance().getPositions();
    for (String typeKey : allSubfields.keySet()) {
      Control008Type type = Control008Type.byCode(typeKey);
      System.err.println(type.getValue());
      boolean isFirst = true;
      int lastEnd = 18;
      char chr = type.equals(Control008Type.ALL_MATERIALS) ? 'a' : 'i';
      if (!type.equals(Control008Type.ALL_MATERIALS))
        for (int i = 0; i<18; i++)
          System.err.print(" ");
      for (ControlfieldPositionDefinition subfield : allSubfields.get(type)) {

        if (lastEnd != -1 && lastEnd != subfield.getPositionStart())
          for (int i = lastEnd; i<subfield.getPositionStart(); i++)
            System.err.print(undefPattern);


        for (int i = subfield.getPositionStart(); i<subfield.getPositionEnd(); i++)
          System.err.print(subfield.isRepeatableContent() ? String.valueOf(chr).toUpperCase() : chr);
        chr++;
        lastEnd = subfield.getPositionEnd();
      }

      if (lastEnd < 35)
        for (int i = lastEnd; i<35; i++)
          System.err.print(undefPattern);

      System.err.println();
    }

    Map<String, List<ControlfieldPositionDefinition>> all007Subfields =
      Control007Positions.getInstance().getPositions();
    for (String typeKey : all007Subfields.keySet()) {
      Control007Category type = Control007Category.byCode(typeKey);
      System.err.println(type.getLabel());
      boolean isFirst = true;
      int lastEnd = -1;
      char chr = type.equals(Control007Category.COMMON) ? 'a' : 'a';
      for (ControlfieldPositionDefinition subfield : all007Subfields.get(type)) {

        if (lastEnd != -1 && lastEnd != subfield.getPositionStart())
          for (int i = lastEnd; i<subfield.getPositionStart(); i++)
            System.err.print(undefPattern);


        for (int i = subfield.getPositionStart(); i<subfield.getPositionEnd(); i++)
          System.err.print(subfield.isRepeatableContent() ? String.valueOf(chr).toUpperCase() : chr);
        chr++;
        lastEnd = subfield.getPositionEnd();
      }

      if (lastEnd < 22)
        for (int i = lastEnd; i<22; i++)
          System.err.print(undefPattern);

      System.err.println();
    }

    System.err.println("-------------------------------------");
    Map<String, List<ControlfieldPositionDefinition>> all006Subfields =
      Control006Positions.getInstance().getPositions();
    for (String typeKey : all006Subfields.keySet()) {
      Control008Type type = Control008Type.byCode(typeKey);
      System.err.println(type.getValue());
      boolean isFirst = true;
      int lastEnd = 0;
      char chr = type.equals(Control008Type.ALL_MATERIALS) ? 'a' : 'i';
      for (ControlfieldPositionDefinition subfield : all006Subfields.get(type)) {

        if (lastEnd != subfield.getPositionStart())
          for (int i = lastEnd; i<subfield.getPositionStart(); i++)
            System.err.print(undefPattern);


        for (int i = subfield.getPositionStart(); i<subfield.getPositionEnd(); i++)
          System.err.print(subfield.isRepeatableContent() ? String.valueOf(chr).toUpperCase() : chr);

        chr++;
        lastEnd = subfield.getPositionEnd();
      }

      if (lastEnd <= 17)
        for (int i = lastEnd; i<=17; i++)
          System.err.print(undefPattern);

      System.err.println();
    }

  }
}
