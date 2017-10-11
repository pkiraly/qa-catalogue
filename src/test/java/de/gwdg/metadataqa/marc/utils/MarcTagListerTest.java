package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.TagDefinitionLoader;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class MarcTagListerTest {

	@Test
	public void test() {
		List<Class<? extends DataFieldDefinition>> tags = MarcTagLister.listTags();
		assertNotNull(tags);
		assertNotEquals(0, tags.size());
		assertEquals(225, tags.size());
		assertEquals("Tag010", tags.get(0).getSimpleName());

		for (Class<? extends DataFieldDefinition> tag : tags) {
			DataFieldDefinition definition = TagDefinitionLoader.load(tag.getSimpleName().replace("Tag", ""));
			if (!definition.getIndexTag().equals(definition.getTag())) {
				if (definition.getInd1().exists()) {
					assertNotEquals(String.format("Undefined index tag for indicator1 at %s", definition.getTag()),
						"ind1", definition.getInd1().getIndexTag("ind1"));
				}
				if (definition.getInd2().exists()) {
					assertNotEquals(String.format("Undefined index tag for indicator2 at %s", definition.getTag()),
						"ind2", definition.getInd2().getIndexTag("ind2"));
				}
			}
		}
	}
}
