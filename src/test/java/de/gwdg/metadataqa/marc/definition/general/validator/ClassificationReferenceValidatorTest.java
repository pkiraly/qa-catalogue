package de.gwdg.metadataqa.marc.definition.general.validator;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.definition.ValidatorResponse;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag045;
import de.gwdg.metadataqa.marc.definition.tags.tags6xx.Tag650;
import org.junit.Test;

import static de.gwdg.metadataqa.marc.definition.general.validator.ClassificationReferenceValidator.*;
import static org.junit.Assert.assertFalse;

public class ClassificationReferenceValidatorTest {

	@Test
	public void test() {
		DataField field = new DataField(Tag650.getInstance(), " ", "7",
			"8", "3\\p",
			"0", "(DE-588)4020758-4",
			"0", "http://d-nb.info/gnd/4020758-4",
			"0", "(DE-101)040207587",
			"a", "Gesundheitsberatung");

		ValidatorResponse response = ClassificationReferenceValidator.validate(field);
		assertFalse(response.isValid());
	}

}
