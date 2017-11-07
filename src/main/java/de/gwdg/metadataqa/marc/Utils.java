package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.ControlSubfield;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Utils {

	private Utils(){}

	public static List<Code> generateCodes(String... input) {
		if (input.length % 2 != 0) {
			throw new IllegalArgumentException("Number of input should be even");
		}
		List<Code> codes = new ArrayList<>();
		for (int i = 0, len = input.length; i < len; i += 2) {
			codes.add(new Code(input[i], input[i+1]));
		}
		return codes;
	}
	
	public static List<ControlSubfield> generateControlSubfieldList(ControlSubfield... input) {
		List<ControlSubfield> list = new ArrayList<>();
		list.addAll(Arrays.asList(input));
		return list;
	}

}
