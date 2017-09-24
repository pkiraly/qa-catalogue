package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
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
		assertEquals(215, tags.size());
		assertEquals("Tag010", tags.get(0).getSimpleName());
	}
}
