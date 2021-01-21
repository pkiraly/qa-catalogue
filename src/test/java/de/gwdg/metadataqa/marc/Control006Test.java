package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006all00;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006continuing01;
import de.gwdg.metadataqa.marc.definition.controlpositions.tag006.Tag006visual16;
import de.gwdg.metadataqa.marc.definition.controltype.Control008Type;
import de.gwdg.metadataqa.marc.definition.tags.control.Control006Definition;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Control006Test {

  @Test
  public void testSa() {
    Control006 field = new Control006("sa", Leader.Type.CONTINUING_RESOURCES);
    Control006Definition definition = (Control006Definition)field.getDefinition();

    assertNotNull("not null", definition.getControlfieldPositions());
    assertEquals(8, definition.getControlfieldPositions().size());

    assertEquals(Leader.Type.CONTINUING_RESOURCES, field.getRecordType());

    List<ControlfieldPositionDefinition> subfields =
      definition.getControlfieldPositions()
      .get(Control008Type.CONTINUING_RESOURCES.getValue());

    assertEquals(2, field.getValuesList().size());
    assertEquals("s", field.getValuesList().get(0).getValue());
    assertEquals("tag006all00", field.getValuesList().get(0).getDefinition().getId());
    assertEquals("a", field.getValuesList().get(1).getValue());
    assertEquals("tag006continuing01", field.getValuesList().get(1).getDefinition().getId());

    assertEquals(2, field.getValueMap().size());
    assertEquals("s", field.getValueMap().get(Tag006all00.getInstance()));
    assertEquals("a", field.getValueMap().get(Tag006continuing01.getInstance()));

    assertEquals(
      11,
      definition.getControlfieldPositions()
      .get(Control008Type.CONTINUING_RESOURCES.getValue())
      .size()
    );

    assertEquals("0, 1", StringUtils.join(field.getSubfieldPositions(), ", "));
  }

  @Test
  public void testAp() {
    Control006 field = new Control006("a|||||||||||||||p|", Leader.Type.BOOKS);
    assertEquals(11, field.getValuesList().size());
    assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
    assertEquals("Language material", field.getValuesList().get(0).resolve());
    assertEquals("Illustrations", field.getValuesList().get(1).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(1).resolve());
    assertEquals("Target audience", field.getValuesList().get(2).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(2).resolve());
    assertEquals("Form of item", field.getValuesList().get(3).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(3).resolve());
    assertEquals("Nature of contents", field.getValuesList().get(4).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(4).resolve());
    assertEquals("Government publication", field.getValuesList().get(5).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(5).resolve());
    assertEquals("Conference publication", field.getValuesList().get(6).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(6).resolve());
    assertEquals("Festschrift", field.getValuesList().get(7).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(7).resolve());
    assertEquals("Index", field.getValuesList().get(8).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(8).resolve());
    assertEquals("Literary form", field.getValuesList().get(9).getDefinition().getLabel());
    assertEquals("Poetry", field.getValuesList().get(9).resolve());
    assertEquals("Biography", field.getValuesList().get(10).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(10).resolve());
  }

  @Test
  public void testSrw() {
    Control006 field = new Control006("swr n|      0   b0", Leader.Type.CONTINUING_RESOURCES);
    assertEquals(12, field.getValuesList().size());
    assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
    assertEquals("Serial/Integrating resource", field.getValuesList().get(0).resolve());
    assertEquals("Frequency", field.getValuesList().get(1).getDefinition().getLabel());
    assertEquals("Weekly", field.getValuesList().get(1).resolve());
    assertEquals("Regularity", field.getValuesList().get(2).getDefinition().getLabel());
    assertEquals("Regular", field.getValuesList().get(2).resolve());
    assertEquals("Type of continuing resource", field.getValuesList().get(3).getDefinition().getLabel());
    assertEquals("Newspaper", field.getValuesList().get(3).resolve());
    assertEquals("Form of original item", field.getValuesList().get(4).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(4).resolve());
    assertEquals("Form of item", field.getValuesList().get(5).getDefinition().getLabel());
    assertEquals("None of the following", field.getValuesList().get(5).resolve());
    assertEquals("Nature of entire work", field.getValuesList().get(6).getDefinition().getLabel());
    assertEquals("Not specified", field.getValuesList().get(6).resolve());
    assertEquals("Nature of contents", field.getValuesList().get(7).getDefinition().getLabel());
    assertEquals("Not specified", field.getValuesList().get(7).resolve());
    assertEquals("Government publication", field.getValuesList().get(8).getDefinition().getLabel());
    assertEquals("Not a government publication", field.getValuesList().get(8).resolve());
    assertEquals("Conference publication", field.getValuesList().get(9).getDefinition().getLabel());
    assertEquals("Not a conference publication", field.getValuesList().get(9).resolve());
    assertEquals("Original alphabet or script of title", field.getValuesList().get(10).getDefinition().getLabel());
    assertEquals("Extended Roman", field.getValuesList().get(10).resolve());
    assertEquals("Entry convention", field.getValuesList().get(11).getDefinition().getLabel());
    assertEquals("Successive entry", field.getValuesList().get(11).resolve());
  }

  @Test
  public void testIjf() {
    Control006 field = new Control006("i||||j ||||||f| | ", Leader.Type.MUSIC);
    assertEquals(9, field.getValuesList().size());
    assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
    assertEquals("Nonmusical sound recording", field.getValuesList().get(0).resolve());
    assertEquals("Form of composition", field.getValuesList().get(1).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(1).resolve());
    assertEquals("Format of music", field.getValuesList().get(2).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(2).resolve());
    assertEquals("Music parts", field.getValuesList().get(3).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(3).resolve());
    assertEquals("Target audience", field.getValuesList().get(4).getDefinition().getLabel());
    assertEquals("Juvenile", field.getValuesList().get(4).resolve());
    assertEquals("Form of item", field.getValuesList().get(5).getDefinition().getLabel());
    assertEquals("None of the following", field.getValuesList().get(5).resolve());
    assertEquals("Accompanying matter", field.getValuesList().get(6).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(6).resolve());
    assertEquals("Literary text for sound recordings", field.getValuesList().get(7).getDefinition().getLabel());
    assertEquals("Fiction, No attempt to code", field.getValuesList().get(7).resolve());
    assertEquals("Transposition and arrangement", field.getValuesList().get(8).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(8).resolve());
  }

  @Test
  public void testCsgg() {
    Control006 field = new Control006("csgg         nn   ", Leader.Type.MUSIC);
    assertEquals(9, field.getValuesList().size());
    assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
    assertEquals("Notated music", field.getValuesList().get(0).resolve());
    assertEquals("Form of composition", field.getValuesList().get(1).getDefinition().getLabel());
    assertEquals("Songs", field.getValuesList().get(1).resolve());
    assertEquals("Format of music", field.getValuesList().get(2).getDefinition().getLabel());
    assertEquals("Close score", field.getValuesList().get(2).resolve());
    assertEquals("Music parts", field.getValuesList().get(3).getDefinition().getLabel());
    assertEquals("No parts in hand or not specified", field.getValuesList().get(3).resolve());
    assertEquals("Target audience", field.getValuesList().get(4).getDefinition().getLabel());
    assertEquals("Unknown or unspecified", field.getValuesList().get(4).resolve());
    assertEquals("Form of item", field.getValuesList().get(5).getDefinition().getLabel());
    assertEquals("None of the following", field.getValuesList().get(5).resolve());
    assertEquals("Accompanying matter", field.getValuesList().get(6).getDefinition().getLabel());
    assertEquals("No accompanying matter", field.getValuesList().get(6).resolve());
    assertEquals("Literary text for sound recordings", field.getValuesList().get(7).getDefinition().getLabel());
    assertEquals("Not applicable", field.getValuesList().get(7).resolve());
    assertEquals("Transposition and arrangement", field.getValuesList().get(8).getDefinition().getLabel());
    assertEquals("Not arrangement or transposition or not specified", field.getValuesList().get(8).resolve());
  }

  @Test
  public void testD() {
    Control006 field = new Control006("d|||||||||||||||||", Leader.Type.MUSIC);
    assertEquals(9, field.getValuesList().size());
    assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
    assertEquals("Manuscript notated music", field.getValuesList().get(0).resolve());
    assertEquals("Form of composition", field.getValuesList().get(1).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(1).resolve());
    assertEquals("Format of music", field.getValuesList().get(2).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(2).resolve());
    assertEquals("Music parts", field.getValuesList().get(3).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(3).resolve());
    assertEquals("Target audience", field.getValuesList().get(4).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(4).resolve());
    assertEquals("Form of item", field.getValuesList().get(5).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(5).resolve());
    assertEquals("Accompanying matter", field.getValuesList().get(6).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(6).resolve());
    assertEquals("Literary text for sound recordings", field.getValuesList().get(7).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(7).resolve());
    assertEquals("Transposition and arrangement", field.getValuesList().get(8).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(8).resolve());
  }

  @Test
  public void testEa() {
    Control006 field = new Control006("e|||||||a|||||||||", Leader.Type.MAPS);
    assertEquals(8, field.getValuesList().size());
    assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
    assertEquals("Cartographic material", field.getValuesList().get(0).resolve());
    assertEquals("Relief", field.getValuesList().get(1).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(1).resolve());
    assertEquals("Projection", field.getValuesList().get(2).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(2).resolve());
    assertEquals("Type of cartographic material", field.getValuesList().get(3).getDefinition().getLabel());
    assertEquals("Single map", field.getValuesList().get(3).resolve());
    assertEquals("Government publication", field.getValuesList().get(4).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(4).resolve());
    assertEquals("Form of item", field.getValuesList().get(5).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(5).resolve());
    assertEquals("Index", field.getValuesList().get(6).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(6).resolve());
    assertEquals("Special format characteristics", field.getValuesList().get(7).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(7).resolve());
  }

  @Test
  public void testGqv() {
    Control006 field = new Control006("g|||       |q   v|", Leader.Type.VISUAL_MATERIALS);
    assertEquals(7, field.getValuesList().size());
    assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
    assertEquals("Projected medium", field.getValuesList().get(0).resolve());
    assertEquals("Running time for motion pictures and videorecordings", field.getValuesList().get(1).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(1).resolve());
    assertEquals("Target audience", field.getValuesList().get(2).getDefinition().getLabel());
    assertEquals("Unknown or not specified", field.getValuesList().get(2).resolve());
    assertEquals("Government publication", field.getValuesList().get(3).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(3).resolve());
    assertEquals("Form of item", field.getValuesList().get(4).getDefinition().getLabel());
    assertEquals("Direct electronic", field.getValuesList().get(4).resolve());
    assertEquals("Type of visual material", field.getValuesList().get(5).getDefinition().getLabel());
    assertEquals("Videorecording", field.getValuesList().get(5).resolve());
    assertEquals("Technique", field.getValuesList().get(6).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(6).resolve());
  }

  @Test
  public void testJfmnnnn() {
    Control006 field = new Control006("jfmnn        nn | ", Leader.Type.MUSIC);
    assertEquals(9, field.getValuesList().size());
    assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
    assertEquals("Musical sound recording", field.getValuesList().get(0).resolve());
    assertEquals("Form of composition", field.getValuesList().get(1).getDefinition().getLabel());
    assertEquals("Folk music", field.getValuesList().get(1).resolve());
    assertEquals("Format of music", field.getValuesList().get(2).getDefinition().getLabel());
    assertEquals("Not applicable", field.getValuesList().get(2).resolve());
    assertEquals("Music parts", field.getValuesList().get(3).getDefinition().getLabel());
    assertEquals("Not applicable", field.getValuesList().get(3).resolve());
    assertEquals("Target audience", field.getValuesList().get(4).getDefinition().getLabel());
    assertEquals("Unknown or unspecified", field.getValuesList().get(4).resolve());
    assertEquals("Form of item", field.getValuesList().get(5).getDefinition().getLabel());
    assertEquals("None of the following", field.getValuesList().get(5).resolve());
    assertEquals("Accompanying matter", field.getValuesList().get(6).getDefinition().getLabel());
    assertEquals("No accompanying matter", field.getValuesList().get(6).resolve());
    assertEquals("Literary text for sound recordings", field.getValuesList().get(7).getDefinition().getLabel());
    assertEquals("Not applicable", field.getValuesList().get(7).resolve());
    assertEquals("Transposition and arrangement", field.getValuesList().get(8).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(8).resolve());
  }

  @Test
  public void testKr() {
    Control006 field = new Control006("k|||||||||||||||r|", Leader.Type.VISUAL_MATERIALS);
    assertEquals(7, field.getValuesList().size());
    assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
    assertEquals("Two-dimensional nonprojectable graphic", field.getValuesList().get(0).resolve());
    assertEquals("Running time for motion pictures and videorecordings", field.getValuesList().get(1).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(1).resolve());
    assertEquals("Target audience", field.getValuesList().get(2).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(2).resolve());
    assertEquals("Government publication", field.getValuesList().get(3).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(3).resolve());
    assertEquals("Form of item", field.getValuesList().get(4).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(4).resolve());
    assertEquals("Type of visual material", field.getValuesList().get(5).getDefinition().getLabel());
    assertEquals("Realia", field.getValuesList().get(5).resolve());
    assertEquals("Technique", field.getValuesList().get(6).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(6).resolve());
  }

  @Test
  public void testMbqi() {
    Control006 field = new Control006("m    bq  i |      ", Leader.Type.COMPUTER_FILES);
    assertEquals(5, field.getValuesList().size());
    assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
    assertEquals("Computer file", field.getValuesList().get(0).resolve());
    assertEquals("Target audience", field.getValuesList().get(1).getDefinition().getLabel());
    assertEquals("Primary", field.getValuesList().get(1).resolve());
    assertEquals("Form of item", field.getValuesList().get(2).getDefinition().getLabel());
    assertEquals("Direct electronic", field.getValuesList().get(2).resolve());
    assertEquals("Type of computer file", field.getValuesList().get(3).getDefinition().getLabel());
    assertEquals("Interactive multimedia", field.getValuesList().get(3).resolve());
    assertEquals("Government publication", field.getValuesList().get(4).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(4).resolve());
  }

  @Test
  public void testRou() {
    Control006 field = new Control006("r|||            ou", Leader.Type.VISUAL_MATERIALS);
    assertEquals(7, field.getValuesList().size());
    assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
    assertEquals("Three-dimensional artifact or naturally occurring object", field.getValuesList().get(0).resolve());
    assertEquals("Running time for motion pictures and videorecordings", field.getValuesList().get(1).getDefinition().getLabel());
    assertEquals("No attempt to code", field.getValuesList().get(1).resolve());
    assertEquals("Target audience", field.getValuesList().get(2).getDefinition().getLabel());
    assertEquals("Unknown or not specified", field.getValuesList().get(2).resolve());
    assertEquals("Government publication", field.getValuesList().get(3).getDefinition().getLabel());
    assertEquals("Not a government publication", field.getValuesList().get(3).resolve());
    assertEquals("Form of item", field.getValuesList().get(4).getDefinition().getLabel());
    assertEquals("None of the following", field.getValuesList().get(4).resolve());
    assertEquals("Type of visual material", field.getValuesList().get(5).getDefinition().getLabel());
    assertEquals("Flash card", field.getValuesList().get(5).resolve());
    assertEquals("Technique", field.getValuesList().get(6).getDefinition().getLabel());
    assertEquals("Unknown", field.getValuesList().get(6).resolve());
  }

  @Test
  public void testPr() {
    Control006 field = new Control006("p     r           ", Leader.Type.MIXED_MATERIALS);

    assertEquals(2, field.getValuesList().size());
    assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
    assertEquals("Mixed materials", field.getValuesList().get(0).resolve());
    assertEquals("Form of item", field.getValuesList().get(1).getDefinition().getLabel());
    assertEquals("Regular print reproduction", field.getValuesList().get(1).resolve());
  }

  @Test
  public void testResolveWithDefinition() {
    Control006 field = new Control006("r|||            ou", Leader.Type.VISUAL_MATERIALS);
    assertEquals("Flash card", field.resolve(Tag006visual16.getInstance()));
  }

  @Test
  public void testMap() {
    Control006 field = new Control006("r|||            ou", Leader.Type.VISUAL_MATERIALS);
    assertEquals(field.getValueMap(), field.getMap());
  }

  @Test
  public void testGetValueByPosition() {
    Control006 field = new Control006("r|||            ou", Leader.Type.VISUAL_MATERIALS);
    assertEquals("o", field.getValueByPosition(16));
  }

  @Test
  public void testGetSubfieldByPosition() {
    Control006 field = new Control006("r|||            ou", Leader.Type.VISUAL_MATERIALS);
    assertEquals("tag006visual16", field.getSubfieldByPosition(16).getId());
    assertEquals("Type of visual material", field.getSubfieldByPosition(16).getLabel());
  }

  @Test
  public void testBookGetters() {
    Control006 field = new Control006("a|||||||||||||||p|", Leader.Type.BOOKS);
    assertEquals("ControlValue", field.getTag006book01().getClass().getSimpleName());

    // assertEquals("Form of material", field.getValuesList().get(0).getDefinition().getLabel());
    // assertEquals("Language material", field.getValuesList().get(0).resolve());

    ControlValue value;

    value = field.getTag006book01();
    assertEquals("Illustrations", value.getDefinition().getLabel());
    assertEquals("||||", value.getValue());
    assertEquals("No attempt to code", value.resolve());

    value = field.getTag006book05();
    assertEquals("Target audience", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());

    value = field.getTag006book06();
    assertEquals("Form of item", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());

    value = field.getTag006book07();
    assertEquals("Nature of contents", value.getDefinition().getLabel());
    assertEquals("||||", value.getValue());
    assertEquals("No attempt to code", value.resolve());

    value = field.getTag006book11();
    assertEquals("Government publication", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());

    value = field.getTag006book12();
    assertEquals("Conference publication", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());

    value = field.getTag006book13();
    assertEquals("Festschrift", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());

    value = field.getTag006book14();
    assertEquals("Index", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());

    value = field.getTag006book16();
    assertEquals("Literary form", value.getDefinition().getLabel());
    assertEquals("p", value.getValue());
    assertEquals("Poetry", value.resolve());

    value = field.getTag006book17();
    assertEquals("Biography", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
  }

  @Test
  public void testComputerFilesGetters() {
    Control006 field = new Control006("m    bq  i |      ", Leader.Type.COMPUTER_FILES);

    ControlValue value = field.getTag006all00();
    assertEquals("Form of material", value.getDefinition().getLabel());
    assertEquals("m", value.getValue());
    assertEquals("Computer file", value.resolve());

    value = field.getTag006computer05();
    assertEquals("Target audience", value.getDefinition().getLabel());
    assertEquals("b", value.getValue());
    assertEquals("Primary", value.resolve());

    value = field.getTag006computer06();
    assertEquals("Form of item", value.getDefinition().getLabel());
    assertEquals("q", value.getValue());
    assertEquals("Direct electronic", value.resolve());

    value = field.getTag006computer09();
    assertEquals("Type of computer file", value.getDefinition().getLabel());
    assertEquals("i", value.getValue());
    assertEquals("Interactive multimedia", value.resolve());

    value = field.getTag006computer11();
    assertEquals("Government publication", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
  }

  @Test
  public void testMapGetters() {
    Control006 field = new Control006("e|||||||a|||||||||", Leader.Type.MAPS);

    ControlValue value = field.getTag006all00();
    assertEquals("Form of material", value.getDefinition().getLabel());
    assertEquals("e", value.getValue());
    assertEquals("Cartographic material", value.resolve());

    value = field.getTag006map01();
    assertEquals("Relief", value.getDefinition().getLabel());
    assertEquals("||||", value.getValue());
    assertEquals("No attempt to code", value.resolve());

    value = field.getTag006map05();
    assertEquals("Projection", value.getDefinition().getLabel());
    assertEquals("||", value.getValue());
    assertEquals("No attempt to code", value.resolve());

    value = field.getTag006map08();
    assertEquals("Type of cartographic material", value.getDefinition().getLabel());
    assertEquals("a", value.getValue());
    assertEquals("Single map", value.resolve());

    value = field.getTag006map11();
    assertEquals("Government publication", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());

    value = field.getTag006map12();
    assertEquals("Form of item", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());

    value = field.getTag006map14();
    assertEquals("Index", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());

    value = field.getTag006map16();
    assertEquals("Special format characteristics", value.getDefinition().getLabel());
    assertEquals("||", value.getValue());
    assertEquals("No attempt to code", value.resolve());
  }

  @Test
  public void testMusicGetters() {
    Control006 field = new Control006("jfmnn        nn | ", Leader.Type.MUSIC);

    ControlValue value = field.getTag006all00();
    assertEquals("Form of material", value.getDefinition().getLabel());
    assertEquals("j", value.getValue());
    assertEquals("Musical sound recording", value.resolve());

    value = field.getTag006music01();
    assertEquals("Form of composition", value.getDefinition().getLabel());
    assertEquals("fm", value.getValue());
    assertEquals("Folk music", value.resolve());

    value = field.getTag006music03();
    assertEquals("Format of music", value.getDefinition().getLabel());
    assertEquals("n", value.getValue());
    assertEquals("Not applicable", value.resolve());

    value = field.getTag006music04();
    assertEquals("Music parts", value.getDefinition().getLabel());
    assertEquals("n", value.getValue());
    assertEquals("Not applicable", value.resolve());

    value = field.getTag006music05();
    assertEquals("Target audience", value.getDefinition().getLabel());
    assertEquals(" ", value.getValue());
    assertEquals("Unknown or unspecified", value.resolve());

    value = field.getTag006music06();
    assertEquals("Form of item", value.getDefinition().getLabel());
    assertEquals(" ", value.getValue());
    assertEquals("None of the following", value.resolve());

    value = field.getTag006music07();
    assertEquals("Accompanying matter", value.getDefinition().getLabel());
    assertEquals("      ", value.getValue());
    assertEquals("No accompanying matter", value.resolve());

    value = field.getTag006music13();
    assertEquals("Literary text for sound recordings", value.getDefinition().getLabel());
    assertEquals("nn", value.getValue());
    assertEquals("Not applicable", value.resolve());

    value = field.getTag006music16();
    assertEquals("Transposition and arrangement", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
  }

  @Test
  public void testContinuingResourcesGetters() {
    Control006 field = new Control006("swr n|      0   b0", Leader.Type.CONTINUING_RESOURCES);
    ControlValue value = field.getTag006all00();
    assertEquals("Form of material", value.getDefinition().getLabel());
    assertEquals("s", value.getValue());
    assertEquals("Serial/Integrating resource", value.resolve());

    value = field.getTag006continuing01();
    assertEquals("Frequency", value.getDefinition().getLabel());
    assertEquals("w", value.getValue());
    assertEquals("Weekly", value.resolve());

    value = field.getTag006continuing02();
    assertEquals("Regularity", value.getDefinition().getLabel());
    assertEquals("r", value.getValue());
    assertEquals("Regular", value.resolve());

    value = field.getTag006continuing04();
    assertEquals("Type of continuing resource", value.getDefinition().getLabel());
    assertEquals("n", value.getValue());
    assertEquals("Newspaper", value.resolve());

    value = field.getTag006continuing05();
    assertEquals("Form of original item", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());

    value = field.getTag006continuing06();
    assertEquals("Form of item", value.getDefinition().getLabel());
    assertEquals(" ", value.getValue());
    assertEquals("None of the following", value.resolve());

    value = field.getTag006continuing07();
    assertEquals("Nature of entire work", value.getDefinition().getLabel());
    assertEquals(" ", value.getValue());
    assertEquals("Not specified", value.resolve());

    value = field.getTag006continuing08();
    assertEquals("Nature of contents", value.getDefinition().getLabel());
    assertEquals("   ", value.getValue());
    assertEquals("Not specified", value.resolve());

    value = field.getTag006continuing11();
    assertEquals("Government publication", value.getDefinition().getLabel());
    assertEquals(" ", value.getValue());
    assertEquals("Not a government publication", value.resolve());

    value = field.getTag006continuing12();
    assertEquals("Conference publication", value.getDefinition().getLabel());
    assertEquals("0", value.getValue());
    assertEquals("Not a conference publication", value.resolve());

    value = field.getTag006continuing16();
    assertEquals("Original alphabet or script of title", value.getDefinition().getLabel());
    assertEquals("b", value.getValue());
    assertEquals("Extended Roman", value.resolve());

    value = field.getTag006continuing17();
    assertEquals("Entry convention", value.getDefinition().getLabel());
    assertEquals("0", value.getValue());
    assertEquals("Successive entry", value.resolve());
  }

  @Test
  public void testVisualMaterialsGetters() {
    Control006 field = new Control006("r|||            ou", Leader.Type.VISUAL_MATERIALS);

    ControlValue value = field.getTag006all00();
    assertEquals("Form of material", value.getDefinition().getLabel());
    assertEquals("r", value.getValue());
    assertEquals("Three-dimensional artifact or naturally occurring object", value.resolve());

    value = field.getTag006visual01();
    assertEquals("Running time for motion pictures and videorecordings", value.getDefinition().getLabel());
    assertEquals("|||", value.getValue());
    assertEquals("No attempt to code", value.resolve());

    value = field.getTag006visual05();
    assertEquals("Target audience", value.getDefinition().getLabel());
    assertEquals(" ", value.getValue());
    assertEquals("Unknown or not specified", value.resolve());

    value = field.getTag006visual11();
    assertEquals("Government publication", value.getDefinition().getLabel());
    assertEquals(" ", value.getValue());
    assertEquals("Not a government publication", value.resolve());

    value = field.getTag006visual12();
    assertEquals("Form of item", value.getDefinition().getLabel());
    assertEquals(" ", value.getValue());
    assertEquals("None of the following", value.resolve());

    value = field.getTag006visual16();
    assertEquals("Type of visual material", value.getDefinition().getLabel());
    assertEquals("o", value.getValue());
    assertEquals("Flash card", value.resolve());

    value = field.getTag006visual17();
    assertEquals("Technique", value.getDefinition().getLabel());
    assertEquals("u", value.getValue());
    assertEquals("Unknown", value.resolve());
  }

  @Test
  public void testMixedMaterialsGetters() {
    Control006 field = new Control006("p     r           ", Leader.Type.MIXED_MATERIALS);

    ControlValue value = field.getTag006all00();
    assertEquals("Form of material", value.getDefinition().getLabel());
    assertEquals("p", value.getValue());
    assertEquals("Mixed materials", value.resolve());

    value = field.getTag006mixed06();
    assertEquals("Form of item", value.getDefinition().getLabel());
    assertEquals("r", value.getValue());
    assertEquals("Regular print reproduction", value.resolve());

  }
}
