package de.gwdg.metadataqa.marc.definition.general;

import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

public class FieldLinkAndSequenceNumberParserTest {
  @Test
  public void test11a() {
    FieldLinkAndSequenceNumberParser link = new FieldLinkAndSequenceNumberParser("1.1\\a");
    assertEquals(1, (int)link.getLinkingNumber());
    assertEquals(1, (int)link.getSequenceNumber());
    assertEquals("a", link.getFieldLinkTypeChar());
    assertEquals(LinkType.Action, link.getFieldLinkType());
  }

  @Test
  public void test12a() {
    FieldLinkAndSequenceNumberParser link = new FieldLinkAndSequenceNumberParser("1.2\\a");
    assertEquals(1, (int)link.getLinkingNumber());
    assertEquals(2, (int)link.getSequenceNumber());
    assertEquals("a", link.getFieldLinkTypeChar());
    assertEquals(LinkType.Action, link.getFieldLinkType());
  }

  @Test
  public void test1a() {
    FieldLinkAndSequenceNumberParser link = new FieldLinkAndSequenceNumberParser("1\\a");
    assertEquals(1, (int)link.getLinkingNumber());
    assertNull(link.getSequenceNumber());
    assertEquals("a", link.getFieldLinkTypeChar());
    assertEquals(LinkType.Action, link.getFieldLinkType());
  }

  @Test
  public void test1c() {
    FieldLinkAndSequenceNumberParser link = new FieldLinkAndSequenceNumberParser("1\\c");
    assertEquals(1, (int)link.getLinkingNumber());
    assertNull(link.getSequenceNumber());
    assertEquals("c", link.getFieldLinkTypeChar());
    assertEquals(LinkType.ConstituentItem, link.getFieldLinkType());
  }

  @Test
  public void test2c() {
    FieldLinkAndSequenceNumberParser link = new FieldLinkAndSequenceNumberParser("2\\c");
    assertEquals(2, (int)link.getLinkingNumber());
    assertNull(link.getSequenceNumber());
    assertEquals("c", link.getFieldLinkTypeChar());
    assertEquals(LinkType.ConstituentItem, link.getFieldLinkType());
  }

  @Test
  public void test2p() {
    FieldLinkAndSequenceNumberParser link = new FieldLinkAndSequenceNumberParser("2\\p");
    assertEquals(2, (int)link.getLinkingNumber());
    assertNull(link.getSequenceNumber());
    assertEquals("p", link.getFieldLinkTypeChar());
    assertEquals(LinkType.MetadataProvenance, link.getFieldLinkType());
  }

  @Test
  public void test4r() {
    FieldLinkAndSequenceNumberParser link = new FieldLinkAndSequenceNumberParser("4\\r");
    assertEquals(4, (int)link.getLinkingNumber());
    assertNull(link.getSequenceNumber());
    assertEquals("r", link.getFieldLinkTypeChar());
    assertEquals(LinkType.Reproduction, link.getFieldLinkType());
  }

  @Test
  public void test1u() {
    FieldLinkAndSequenceNumberParser link = new FieldLinkAndSequenceNumberParser("1\\u");
    assertEquals(1, (int)link.getLinkingNumber());
    assertNull(link.getSequenceNumber());
    assertEquals("u", link.getFieldLinkTypeChar());
    assertEquals(LinkType.GeneralLinking, link.getFieldLinkType());
  }

  @Test
  public void test13x() {
    FieldLinkAndSequenceNumberParser link = new FieldLinkAndSequenceNumberParser("1.3\\x");
    assertEquals(1, (int)link.getLinkingNumber());
    assertEquals(3, (int)link.getSequenceNumber());
    assertEquals("x", link.getFieldLinkTypeChar());
    assertEquals(LinkType.GeneralSequencing, link.getFieldLinkType());
  }


}
