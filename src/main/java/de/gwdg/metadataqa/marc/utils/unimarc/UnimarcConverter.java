package de.gwdg.metadataqa.marc.utils.unimarc;

import de.gwdg.metadataqa.marc.dao.DataField;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class UnimarcConverter {

  public static String tagFromUnimarc(String tag) {
    if (tag.equals("100")) {
      tag = "008";
    }
    return tag;
  }

  public static String contentFromUnimarc(String tag, String content) {
    String ind1 = content.substring(0, 1);
    String ind2 = content.substring(1, 2);
    List<String[]> subfields = DataField.parseSubfields(content.substring(2));
    String marc21content = null;
    if (tag.equals("100")) {
      // tag = "008";
      String source = null;
      for (String[] subfield : subfields) {
        if (subfield[0].equals("a")) {
          source = subfield[1];
          break;
        }
      }
      if (source != null)
        marc21content = convert100to008(source);
      System.err.println("contentFromUnimarc: '" + source + "'");
      System.err.println("toMarc21: '" + marc21content + "'");
    }
    return content;
  }

  private static String convert100to008(String source) {
    StringBuffer sb = new StringBuffer();
    System.err.println("0/1) " + source.substring(0, 1));
    // DATE ENTERED ON FILE
    sb.append(source.substring(2, 8));
    System.err.printf("#1 %d: '%s'\n", sb.length(), sb.toString());

    // TYPE OF DATE/PUBLICATION STATUS
    sb.append(StringUtils.replaceChars(source.substring(8, 9), "abcdefghij", "cdusrqmtpe"));
    System.err.printf("#2 %d: '%s'\n", sb.length(), sb.toString());

    // DATE 1/BEGINNING DATE OF PUBL.
    sb.append(source.substring(9, 13));
    System.err.printf("#3 %d: '%s'\n", sb.length(), sb.toString());

    // DATE 2/ENDING DATE OF PUBLICATION
    sb.append(source.substring(13, 17));
    System.err.printf("#4 %d: '%s'\n", sb.length(), sb.toString());

    // PLACE OF PUBLICATION, PROD., OR EXECUTION
    sb.append(StringUtils.replaceChars(source.substring(19, 20), "bcadekmu", "abjcdeg "));
    System.err.printf("%d: '%s'\n", sb.length(), sb.toString());

    sb.append("     ");
    System.err.printf("%d: '%s'\n", sb.length(), sb.toString());

    sb.append(StringUtils.replaceChars(source.substring(22, 23), "abcdefghy", "fsllcizo "));
    System.err.printf("%d: '%s'\n", sb.length(), sb.toString());

    sb.append("    ");
    System.err.printf("%d: '%s'\n", sb.length(), sb.toString());

    sb.append(source.substring(36, 36));
    System.err.printf("%d: '%s'\n", sb.length(), sb.toString());

    sb.append("    ");
    System.err.printf("%d: '%s'\n", sb.length(), sb.toString());

    sb.append(StringUtils.replaceChars(source.substring(23, 24), "01", " o"));
    System.err.printf("%d: '%s'\n", sb.length(), sb.toString());

    sb.append("  ");
    System.err.printf("%d: '%s'\n", sb.length(), sb.toString());

    return sb.toString();
  }

  public static String leaderFromUnimarc(String leader) {
    System.err.println(leader);
    return leader;
  }
}
