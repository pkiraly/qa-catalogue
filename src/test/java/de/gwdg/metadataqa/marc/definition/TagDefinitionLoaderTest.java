package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.definition.tags01x.Tag017;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class TagDefinitionLoaderTest {

	@Test
	public void testGetClassName() {
		assertEquals("de.gwdg.metadataqa.marc.definition.tags01x.Tag017", TagDefinitionLoader.getClassName("017"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags1xx.Tag111", TagDefinitionLoader.getClassName("111"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags20x.Tag211", TagDefinitionLoader.getClassName("211"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags25x.Tag251", TagDefinitionLoader.getClassName("251"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags3xx.Tag351", TagDefinitionLoader.getClassName("351"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags4xx.Tag451", TagDefinitionLoader.getClassName("451"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags5xx.Tag551", TagDefinitionLoader.getClassName("551"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags6xx.Tag651", TagDefinitionLoader.getClassName("651"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags70x.Tag751", TagDefinitionLoader.getClassName("751"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags76x.Tag761", TagDefinitionLoader.getClassName("761"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags80x.Tag831", TagDefinitionLoader.getClassName("831"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags84x.Tag861", TagDefinitionLoader.getClassName("861"));
	}

	@Test
	public void test() {
		assertEquals("017", TagDefinitionLoader.load("017").getTag());
		assertEquals("017", TagDefinitionLoader.load("017").getTag());
		assertEquals("017", TagDefinitionLoader.load("017").getTag());
		assertEquals("111", TagDefinitionLoader.load("111").getTag());
		assertEquals("211", TagDefinitionLoader.load("211").getTag());
		assertEquals("251", TagDefinitionLoader.load("251").getTag());
		assertEquals("351", TagDefinitionLoader.load("351").getTag());
		assertEquals("451", TagDefinitionLoader.load("451").getTag());
		assertEquals("551", TagDefinitionLoader.load("551").getTag());
		assertEquals("651", TagDefinitionLoader.load("651").getTag());
		assertEquals("751", TagDefinitionLoader.load("751").getTag());
		assertEquals("761", TagDefinitionLoader.load("761").getTag());
		assertEquals("831", TagDefinitionLoader.load("831").getTag());
		assertEquals("861", TagDefinitionLoader.load("861").getTag());
	}
}
