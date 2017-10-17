package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.SubfieldDefinition;
import de.gwdg.metadataqa.marc.utils.MarcTagLister;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MappingToMarkdown {

	public static void main(String[] args) {
		List<Class<? extends DataFieldDefinition>> tags = MarcTagLister.listTags();

		for (Class<? extends DataFieldDefinition> tagClass : tags) {
			if (tagClass.getCanonicalName().contains("oclctags"))
				continue;

			Method getInstance = null;
			DataFieldDefinition tag = null;
			try {
				getInstance = tagClass.getMethod("getInstance");
				tag = (DataFieldDefinition) getInstance.invoke(tagClass);
				tagToMarkDown(tag);

			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	private static void tagToMarkDown(DataFieldDefinition tag) {
		System.out.printf("|  |  |  |\n");
		System.out.printf("| **`%s`** | **`%s`** | **%s** |\n",
			tag.getTag(),
			tag.getIndexTag(),
			tag.getLabel());
		if (tag.getInd1().exists() || tag.getInd2().exists())
			System.out.printf("|  | indicators |  |\n");
		if (tag.getInd1().exists())
			System.out.printf("| `%s$ind1` | `%s_%s` | %s |\n",
				tag.getTag(),
				tag.getIndexTag(), tag.getInd1().getIndexTag(),
				tag.getInd1().getLabel());
		if (tag.getInd2().exists())
			System.out.printf("| `%s$ind2` | `%s_%s` | %s |\n",
				tag.getTag(),
				tag.getIndexTag(), tag.getInd2().getIndexTag(),
				tag.getInd2().getLabel());
		System.out.printf("|  | data subfields |  |\n");
		for (SubfieldDefinition subfield : tag.getSubfields()) {
			System.out.printf("| `%s$%s` | `%s%s` | %s |\n",
				tag.getTag(), subfield.getCode(),
				tag.getIndexTag(), subfield.getCodeForIndex(),
				subfield.getLabel());
		}
	}
}
