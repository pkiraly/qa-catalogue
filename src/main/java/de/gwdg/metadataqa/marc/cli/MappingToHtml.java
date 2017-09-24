package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;
import de.gwdg.metadataqa.marc.utils.MarcTagLister;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MappingToHtml {

	public static void main(String[] args) {
		List<Class<? extends DataFieldDefinition>> tags = MarcTagLister.listTags();

		System.out.println("<table>");
		System.out.println("<thead>" +
			"<tr>" +
			"<th>MARC code</th>" +
			"<th>Self descriptive code</th>" +
			"<th>Description</th>" +
			"</tr>" +
			"</thead>");

		System.out.println("<tbody>");
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

	private static void tagToHtml(DataFieldDefinition tag) {
		String text = String.format("<tr><td colspan=\"3\"><strong>%s</strong></td></tr>\n",
			tag.getTag());
		text += row(tag.getTag(), tag.getIndexTag(), tag.getLabel());
		if (tag.getInd1().exists() || tag.getInd2().exists())
			text += "<tr><td colspan=\"3\"><em>indicators</em></td></tr>\n";
		if (tag.getInd1().exists())
			text += row(
				String.format("%s$ind1", tag.getTag()),
				String.format("%s_%s", tag.getIndexTag(), tag.getInd1().getIndexTag("ind1")),
				tag.getInd1().getLabel()
			);
		if (tag.getInd2().exists())
			text += row(
				String.format("%s$ind2", tag.getTag()),
				String.format("%s_%s", tag.getIndexTag(), tag.getInd2().getIndexTag("ind2")),
				tag.getInd2().getLabel()
			);
		text += "<tr><td colspan=\"3\"><em>data subfields</em></td></tr>\n";
		for (SubfieldDefinition subfield : tag.getSubfields()) {
			text += row(
				String.format("%s$%s", tag.getTag(), subfield.getCode()),
				String.format("%s%s", tag.getIndexTag(), subfield.getCodeForIndex()),
				subfield.getLabel()
			);
		}

		System.out.print(text);
	}

	private static String row(String marc, String mq, String label) {
		return String.format(
			"<tr>" +
				"<td>%s</td>" +
				"<td>%s</td>" +
				"<td>%s</td>" +
				"</tr>\n",
			marc, mq, label);
	}
}
