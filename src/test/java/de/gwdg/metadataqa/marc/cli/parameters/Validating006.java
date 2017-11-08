package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.Control006;
import de.gwdg.metadataqa.marc.Leader;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;

public class Validating006 {

	@Test
	public void test() {
		MarcRecord record = new MarcRecord("001441164");
		record.setLeader(new Leader("02945nam a22005657a 4500"));
		record.setControl006(new Control006("jccnn           n", Leader.Type.BOOKS));
		boolean isValid = record.validate(MarcVersion.MARC21);
		assertFalse(isValid);
		System.err.println(record.getErrors());
		System.err.println(record.getValidationErrors());

		/*
		003 BE-GnUNI
		005 20170502114956.0
		007 sd fsngnn|m|ee
		008 101123s2010    be aefh  b    101 0 mul d
		*/

	}
}
