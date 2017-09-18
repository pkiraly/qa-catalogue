package de.gwdg.metadataqa.marc.definition;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TagDefinitionLoaderTest {

	@Test
	public void testGetClassName() {
		assertEquals("de.gwdg.metadataqa.marc.definition.tags01x.Tag017", TagDefinitionLoader.getClassName("017"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags1xx.Tag111", TagDefinitionLoader.getClassName("111"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags20x.Tag222", TagDefinitionLoader.getClassName("222"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags25x.Tag250", TagDefinitionLoader.getClassName("250"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags3xx.Tag351", TagDefinitionLoader.getClassName("351"));
		// assertEquals("de.gwdg.metadataqa.marc.definition.tags4xx.Tag451", TagDefinitionLoader.getClassName("451"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags5xx.Tag552", TagDefinitionLoader.getClassName("552"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags6xx.Tag651", TagDefinitionLoader.getClassName("651"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags70x.Tag751", TagDefinitionLoader.getClassName("751"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags76x.Tag760", TagDefinitionLoader.getClassName("760"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags80x.Tag830", TagDefinitionLoader.getClassName("830"));
		assertEquals("de.gwdg.metadataqa.marc.definition.tags84x.Tag856", TagDefinitionLoader.getClassName("856"));
	}

	@Test
	public void testLoadClasses() {
		assertEquals("017", TagDefinitionLoader.load("017").getTag());
		assertEquals("111", TagDefinitionLoader.load("111").getTag());
		assertEquals("222", TagDefinitionLoader.load("222").getTag());
		assertEquals("250", TagDefinitionLoader.load("250").getTag());
		assertEquals("351", TagDefinitionLoader.load("351").getTag());
		// assertEquals("451", TagDefinitionLoader.load("451").getTag());
		assertEquals("552", TagDefinitionLoader.load("552").getTag());
		assertEquals("651", TagDefinitionLoader.load("651").getTag());
		assertEquals("751", TagDefinitionLoader.load("751").getTag());
		assertEquals("760", TagDefinitionLoader.load("760").getTag());
		assertEquals("830", TagDefinitionLoader.load("830").getTag());
		assertEquals("856", TagDefinitionLoader.load("856").getTag());
	}
}
