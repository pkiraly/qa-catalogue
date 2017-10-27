package de.gwdg.metadataqa.marc.definition.tags.oclctags;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class Tag029Test {

	@Test
	public void test() {
		DataField field = new DataField(Tag029.getInstance(), "a", "a", "a", "0170-1967");
		assertFalse("029$ind=a should be invalid in normal case", field.validate(null));
		assertTrue("029$ind=a should be valid in DNB", field.validate(MarcVersion.DNB));
	}
}
