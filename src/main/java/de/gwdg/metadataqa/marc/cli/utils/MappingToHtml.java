package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.controlsubfields.Control006Subfields;
import de.gwdg.metadataqa.marc.definition.controlsubfields.Control007Subfields;
import de.gwdg.metadataqa.marc.definition.controlsubfields.Control008Subfields;
import de.gwdg.metadataqa.marc.definition.controlsubfields.LeaderSubfields;
import de.gwdg.metadataqa.marc.definition.tags.control.*;
import de.gwdg.metadataqa.marc.utils.MarcTagLister;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MappingToHtml {

  public static void main(String[] args) {
    List<Class<? extends DataFieldDefinition>> tags = MarcTagLister.listTags();

    System.out.println("<table id=\"mapping\">");
    System.out.println("<thead>" +
      "<tr>" +
      "<th>MARC code</th>" +
      "<th>Self descriptive code</th>" +
      "<th>Description</th>" +
      "</tr>" +
      "</thead>");

    System.out.println("<tbody>");
    System.out.printf("<tr><td colspan=\"3\"><strong>%s</strong></td></tr>%n", "Leader");
    for (ControlSubfieldDefinition subfield : LeaderSubfields.getSubfieldList()) {
      ControlSubfieldToHtml(subfield, "Leader", "Leader");
    }

    System.out.printf("<tr><td colspan=\"3\"><strong>%s</strong></td></tr>%n", "001");
    System.out.print(row("001",
      Control001Definition.getInstance().getMqTag(),
      Control001Definition.getInstance().getLabel()));

    System.out.printf("<tr><td colspan=\"3\"><strong>%s</strong></td></tr>%n", "003");
    System.out.print(row("003",
      Control003Definition.getInstance().getMqTag(),
      Control003Definition.getInstance().getLabel()));

    System.out.printf("<tr><td colspan=\"3\"><strong>%s</strong></td></tr>%n", "005");
    System.out.print(row("005",
      Control005Definition.getInstance().getMqTag(),
      Control005Definition.getInstance().getLabel()));

    System.out.printf("<tr><td colspan=\"3\"><strong>%s</strong></td></tr>%n", "006");
    System.out.print(row("006", Control006Definition.getInstance().getMqTag(), Control006Definition.getInstance().getLabel()));
    for (String type : Control006Subfields.getInstance().getSubfields().keySet()) {
      System.out.printf("<tr><td colspan=\"3\"><em>%s</em></td></tr>%n", type);
      for (ControlSubfieldDefinition subfield : Control006Subfields.getInstance().getSubfields().get(type))
        ControlSubfieldToHtml(subfield, "006", Control006Definition.getInstance().getMqTag());
    }

    System.out.printf("<tr><td colspan=\"3\"><strong>%s</strong></td></tr>%n", "007");
    System.out.print(row("007", Control007Definition.getInstance().getMqTag(), Control007Definition.getInstance().getLabel()));
    for (String category : Control007Subfields.getInstance().getSubfields().keySet()) {
      System.out.printf("<tr><td colspan=\"3\"><em>%s</em></td></tr>%n", category);
      for (ControlSubfieldDefinition subfield : Control007Subfields.getInstance().getSubfields().get(category))
        ControlSubfieldToHtml(subfield, "007", Control007Definition.getInstance().getMqTag());
    }

    System.out.printf("<tr><td colspan=\"3\"><strong>%s</strong></td></tr>%n", "008");
    System.out.print(row("008", Control008Definition.getInstance().getMqTag(), Control008Definition.getInstance().getLabel()));
    for (String type : Control008Subfields.getInstance().getSubfields().keySet()) {
      System.out.printf("<tr><td colspan=\"3\"><em>%s</em></td></tr>%n", type);
      for (ControlSubfieldDefinition subfield : Control008Subfields.getInstance().getSubfields().get(type))
        ControlSubfieldToHtml(subfield, "008", Control008Definition.getInstance().getMqTag());
    }


    for (Class<? extends DataFieldDefinition> tagClass : tags) {
      if (tagClass.getCanonicalName().contains("oclctags"))
        continue;

      Method getInstance = null;
      DataFieldDefinition tag = null;
      try {
        getInstance = tagClass.getMethod("getInstance");
        tag = (DataFieldDefinition) getInstance.invoke(tagClass);
        tagToHtml(tag);
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
    System.out.println("</tbody>");
    System.out.println("</table>");
  }

  private static void ControlSubfieldToHtml(ControlSubfieldDefinition subfield, String marcTag, String mqTag) {
    int start = subfield.getPositionStart();
    int end = subfield.getPositionEnd() - 1;
    String suffix = (start == end) ? String.format("%02d", start) : String.format("%02d-%02d", start, end);
    String code = subfield.getMqTag() != null ? subfield.getMqTag() : subfield.getId();
    System.out.print(row(marcTag + "/" + suffix, mqTag + "_" + code, subfield.getLabel()));
  }

  private static void tagToHtml(DataFieldDefinition tag) {
    StringBuffer text = new StringBuffer(
      String.format(
        "<tr><td colspan=\"3\"><strong>%s</strong></td></tr>%n",
        tag.getTag()
      )
    );
    text.append(row(tag.getTag(), tag.getIndexTag(), tag.getLabel()));
    if (tag.getInd1().exists() || tag.getInd2().exists())
      text.append("<tr><td colspan=\"3\"><em>indicators</em></td></tr>\n");
    if (tag.getInd1().exists())
      text.append(row(
        String.format("%s$ind1", tag.getTag()),
        String.format("%s_%s", tag.getIndexTag(), tag.getInd1().getIndexTag()),
        tag.getInd1().getLabel()
      ));
    if (tag.getInd2().exists())
      text.append(row(
        String.format("%s$ind2", tag.getTag()),
        String.format("%s_%s", tag.getIndexTag(), tag.getInd2().getIndexTag()),
        tag.getInd2().getLabel()
      ));
    text.append("<tr><td colspan=\"3\"><em>data subfields</em></td></tr>%n");
    for (SubfieldDefinition subfield : tag.getSubfields()) {
      text.append(row(
        String.format("%s$%s", tag.getTag(), subfield.getCode()),
        String.format("%s%s", tag.getIndexTag(), subfield.getCodeForIndex()),
        subfield.getLabel()
      ));
    }

    System.out.print(text.toString());
  }

  private static String row(String marc, String mq, String label) {
    return String.format(
      "<tr>" +
        "<td class=\"marc\">%s</td>" +
        "<td class=\"mq\">%s</td>" +
        "<td>%s</td>" +
        "</tr>%n",
      marc, mq, label);
  }
}
