package de.gwdg.metadataqa.marc;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Control008Test {
	
	public Control008Test() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void hello() {
		Map<String, Leader.Type> inputs = new LinkedHashMap<>();
		inputs.put("801003s1958    ja            000 0 jpn  ", Leader.Type.MAPS);
		inputs.put("981123p19981996enkmun   efhi           d", Leader.Type.BOOKS);

		Control008 field;
		for (Map.Entry<String, Leader.Type> input : inputs.entrySet()){
			field = new Control008(input.getKey(), input.getValue());
			System.err.println(String.format("[%s]", input.getKey()));
			for (ControlSubfield subfield : field.getValueMap().keySet()) {
				System.err.println(String.format("%s: %s", 
					subfield.getLabel(), field.resolve(subfield)));
			}
		}
	}
}
