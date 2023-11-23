package de.gwdg.metadataqa.marc.utils.marcreader;

import de.gwdg.metadataqa.marc.TestUtils;
import de.gwdg.metadataqa.marc.definition.structure.DefaultControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Marc21DataFieldDefinition;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Marc21SchemaReaderTest {

  Marc21SchemaManager authorityManager;

  @Before
  public void setUp() throws Exception {
    Marc21SchemaReader schemaReader = new Marc21SchemaReader(TestUtils.getPathFromMain("marc/authority-schema.json"));
    assertNotNull(schemaReader);
    assertNotNull(schemaReader.getMap());
    assertTrue(schemaReader.getMap().containsKey("001"));
    authorityManager = new Marc21SchemaManager(schemaReader.getMap());
  }

  @Test
  public void testControlField() {
    assertEquals("001", authorityManager.lookup("001").getTag());
    assertEquals("Control Number", authorityManager.lookup("001").getLabel());
  }

  @Test
  public void testDataField() {
    assertEquals("883", authorityManager.lookup("883").getTag());
    assertEquals("Metadata Provenance", authorityManager.lookup("883").getLabel());
  }

  @Test
  public void testIndicator() {
    assertNotNull("883/ind1 should not be null", authorityManager.lookup("883").getInd1());
    assertEquals("Method of assignment", authorityManager.lookup("883").getInd1().getLabel());
    assertNotNull("883/ind1 should have a code list", authorityManager.lookup("883").getInd1().getCodes());
    assertEquals(4, authorityManager.lookup("883").getInd1().getCodes().size());
    System.err.println(authorityManager.lookup("883").getInd1().getCode(" "));
    assertEquals("No information provided/not applicable", authorityManager.lookup("883").getInd1().getCode(" ").getLabel());
  }

  @Test
  public void testSubfield() {
    assertNotNull("883/ind1 should not be null", authorityManager.lookup("710"));
    assertNotNull(authorityManager.lookup("710").getSubfields());
    assertNotNull("710$w should not be null", authorityManager.lookup("710").getSubfield("w"));
    assertNotNull("883/ind1 should not be null", authorityManager.lookup("710").getSubfield("w").getCodes());
    assertEquals(2, authorityManager.lookup("710").getSubfield("w").getCodes().size());
    assertEquals("Link display", authorityManager.lookup("710").getSubfield("w").getCode("0").getLabel());
  }

  @Test
  public void testLeader() {
    assertNotNull("883/ind1 should not be null", authorityManager.lookup("leader"));
    assertNull("Leader does not have subfields", authorityManager.lookup("leader").getSubfields());

    DefaultControlFieldDefinition leader = (DefaultControlFieldDefinition) authorityManager.lookup("leader");

    assertNotNull("710$w should not be null", leader.getPositions());
    assertEquals(15, leader.getPositions().size());

    assertEquals("Record length", leader.getPositions().get(0).getLabel());
    assertEquals(0, leader.getPositions().get(0).getPositionStart());
    assertEquals(5, leader.getPositions().get(0).getPositionEnd());
    assertEquals(0, leader.getPositions().get(0).getCodes().size());

    assertEquals("Record status", leader.getPositions().get(1).getLabel());
    assertEquals(5, leader.getPositions().get(1).getPositionStart());
    assertEquals(6, leader.getPositions().get(1).getPositionEnd());
    assertEquals(7, leader.getPositions().get(1).getCodes().size());
    assertEquals("Increase in encoding level", leader.getPositions().get(1).getCode("a").getLabel());
  }
}