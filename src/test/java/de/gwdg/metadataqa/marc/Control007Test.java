package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.controltype.Control007Category;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Control007Test {

  @Test
  public void testMap() {
    Control007 field = new Control007("ad|aacz ");

    List<ControlValue> values = field.getValuesList();
    assertEquals(7, values.size());
    ControlValue value;

    value = values.get(0);
    assertEquals("Category of material", value.getLabel());
    assertEquals("a", value.getValue());
    assertEquals("Map", value.resolve());
    assertEquals(value, field.getMap00());

    value = values.get(1);
    assertEquals("Specific material designation", value.getDefinition().getLabel());
    assertEquals("d", value.getValue());
    assertEquals("Atlas", value.resolve());
    assertEquals(value, field.getMap01());

    value = values.get(2);
    assertEquals("Color", value.getDefinition().getLabel());
    assertEquals("a", value.getValue());
    assertEquals("One color", value.resolve());
    assertEquals(value, field.getMap03());

    value = values.get(3);
    assertEquals("Physical medium", value.getDefinition().getLabel());
    assertEquals("a", value.getValue());
    assertEquals("Paper", value.resolve());
    assertEquals(value, field.getMap04());

    value = values.get(4);
    assertEquals("Type of reproduction", value.getDefinition().getLabel());
    assertEquals("c", value.getValue());
    assertEquals("c", value.resolve());
    assertEquals(value, field.getMap05());

    value = values.get(5);
    assertEquals("Production/reproduction details", value.getDefinition().getLabel());
    assertEquals("z", value.getValue());
    assertEquals("Other", value.resolve());
    assertEquals(value, field.getMap06());

    value = values.get(6);
    assertEquals("Positive/negative aspect", value.getDefinition().getLabel());
    assertEquals(" ", value.getValue());
    assertEquals(" ", value.resolve());
    assertEquals(value, field.getMap07());

    assertFalse(field.validate(MarcVersion.MARC21));
    assertEquals(2, field.getValidationErrors().size());
    assertEquals("007/05 (007map05)",
      field.getValidationErrors().get(0).getMarcPath());
    assertEquals("c",
      field.getValidationErrors().get(0).getMessage());
    assertEquals("hasInvalidValue",
      field.getValidationErrors().get(0).getType().getCode());
    assertEquals(ValidationErrorType.CONTROL_POSITION_INVALID_VALUE,
      field.getValidationErrors().get(0).getType());
    assertEquals("https://www.loc.gov/marc/bibliographic/bd007a.html",
      field.getValidationErrors().get(0).getUrl());

    assertEquals("007/07 (007map07)",
      field.getValidationErrors().get(1).getMarcPath());
    assertEquals(" ",
      field.getValidationErrors().get(1).getMessage());
    assertEquals("hasInvalidValue",
      field.getValidationErrors().get(1).getType().getCode());
    assertEquals(ValidationErrorType.CONTROL_POSITION_INVALID_VALUE,
      field.getValidationErrors().get(1).getType());
    assertEquals("https://www.loc.gov/marc/bibliographic/bd007a.html",
      field.getValidationErrors().get(1).getUrl());
  }

  @Test
  public void testGlobe() {
    Control007 field = new Control007("du||||");

    List<ControlValue> values = field.getValuesList();
    assertEquals(5, values.size());
    ControlValue value;

    value = values.get(0);
    assertEquals("Category of material", value.getLabel());
    assertEquals("d", value.getValue());
    assertEquals("Globe", value.resolve());
    assertEquals(value, field.getGlobe00());

    value = values.get(1);
    assertEquals("Specific material designation", value.getDefinition().getLabel());
    assertEquals("u", value.getValue());
    assertEquals("Unspecified", value.resolve());
    assertEquals(value, field.getGlobe01());

    value = values.get(2);
    assertEquals("Color", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getGlobe03());

    value = values.get(3);
    assertEquals("Physical medium", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getGlobe04());

    value = values.get(4);
    assertEquals("Type of reproduction", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals(value, field.getGlobe05());

    assertTrue(field.validate(MarcVersion.MARC21));
    assertEquals(0, field.getValidationErrors().size());
  }

  @Test
  public void testTactileMaterial() {
    Control007 field = new Control007("fb||||||||");

    List<ControlValue> values = field.getValuesList();
    assertEquals(6, values.size());
    ControlValue value;

    value = values.get(0);
    assertEquals("Category of material", value.getLabel());
    assertEquals("f", value.getValue());
    assertEquals("Tactile material", value.resolve());
    assertEquals(value, field.getTactile00());

    value = values.get(1);
    assertEquals("Specific material designation", value.getDefinition().getLabel());
    assertEquals("b", value.getValue());
    assertEquals("Braille", value.resolve());
    assertEquals(value, field.getTactile01());

    value = values.get(2);
    assertEquals("Class of braille writing", value.getDefinition().getLabel());
    assertEquals("||", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getTactile03());

    value = values.get(3);
    assertEquals("Level of contraction", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getTactile05());

    value = values.get(4);
    assertEquals("Braille music format", value.getDefinition().getLabel());
    assertEquals("|||", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getTactile06());

    value = values.get(5);
    assertEquals("Special physical characteristics", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getTactile09());

    assertTrue(field.validate(MarcVersion.MARC21));
    assertEquals(0, field.getValidationErrors().size());
  }

  @Test
  public void testProjectedGraphic() {
    Control007 field = new Control007("gs ||||| ");

    List<ControlValue> values = field.getValuesList();
    assertEquals(8, values.size());
    ControlValue value;

    value = values.get(0);
    assertEquals("Category of material", value.getLabel());
    assertEquals("g", value.getValue());
    assertEquals("Projected graphic", value.resolve());
    assertEquals(value, field.getProjected00());

    value = values.get(1);
    assertEquals("Specific material designation", value.getDefinition().getLabel());
    assertEquals("s", value.getValue());
    assertEquals("Slide", value.resolve());
    assertEquals(value, field.getProjected01());

    value = values.get(2);
    assertEquals("Color", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getProjected03());

    value = values.get(3);
    assertEquals("Base of emulsion", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getProjected04());

    value = values.get(4);
    assertEquals("Sound on medium or separate", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getProjected05());

    value = values.get(5);
    assertEquals("Medium for sound", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getProjected06());

    value = values.get(6);
    assertEquals("Dimensions", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getProjected07());

    value = values.get(7);
    assertEquals("Secondary support material", value.getDefinition().getLabel());
    assertEquals(" ", value.getValue());
    assertEquals("No secondary support", value.resolve());
    assertEquals(value, field.getProjected08());

    assertTrue(field.validate(MarcVersion.MARC21));
    assertEquals(0, field.getValidationErrors().size());
  }

  @Test
  public void testMicroform() {
    Control007 field = new Control007("hu|am|||||a||");

    List<ControlValue> values = field.getValuesList();
    assertEquals(10, values.size());
    ControlValue value;

    value = values.get(0);
    assertEquals("Category of material", value.getLabel());
    assertEquals("h", value.getValue());
    assertEquals("Microform", value.resolve());
    assertEquals(value, field.getMicroform00());

    value = values.get(1);
    assertEquals("Specific material designation", value.getDefinition().getLabel());
    assertEquals("u", value.getValue());
    assertEquals("Unspecified", value.resolve());
    assertEquals(value, field.getMicroform01());

    value = values.get(2);
    assertEquals("Positive/negative aspect", value.getDefinition().getLabel());
    assertEquals("a", value.getValue());
    assertEquals("Positive", value.resolve());
    assertEquals(value, field.getMicroform03());

    value = values.get(3);
    assertEquals("Dimensions", value.getDefinition().getLabel());
    assertEquals("m", value.getValue());
    assertEquals("4x6 in. or 11x15 cm.", value.resolve());
    assertEquals(value, field.getMicroform04());

    value = values.get(4);
    assertEquals("Reduction ratio range", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getMicroform05());

    value = values.get(5);
    assertEquals("Reduction ratio", value.getDefinition().getLabel());
    assertEquals("|||", value.getValue());
    assertEquals("|||", value.resolve());
    assertEquals(value, field.getMicroform06());

    value = values.get(6);
    assertEquals("Color", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getMicroform09());

    value = values.get(7);
    assertEquals("Emulsion on film", value.getDefinition().getLabel());
    assertEquals("a", value.getValue());
    assertEquals("Silver halide", value.resolve());
    assertEquals(value, field.getMicroform10());

    value = values.get(8);
    assertEquals("Generation", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getMicroform11());

    value = values.get(9);
    assertEquals("Base of film", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getMicroform12());

    assertTrue(field.validate(MarcVersion.MARC21));
    assertEquals(0, field.getValidationErrors().size());
  }

  @Test
  public void testNonprojectedGraphic() {
    Control007 field = new Control007("kh|bo|");

    List<ControlValue> values = field.getValuesList();
    assertEquals(5, values.size());
    ControlValue value;

    value = values.get(0);
    assertEquals("Category of material", value.getLabel());
    assertEquals("k", value.getValue());
    assertEquals("Nonprojected graphic", value.resolve());
    assertEquals(value, field.getNonprojected00());

    value = values.get(1);
    assertEquals("Specific material designation", value.getDefinition().getLabel());
    assertEquals("h", value.getValue());
    assertEquals("Photoprint", value.resolve());
    assertEquals(value, field.getNonprojected01());

    value = values.get(2);
    assertEquals("Color", value.getDefinition().getLabel());
    assertEquals("b", value.getValue());
    assertEquals("Black-and-white", value.resolve());
    assertEquals(value, field.getNonprojected03());

    value = values.get(3);
    assertEquals("Primary support material", value.getDefinition().getLabel());
    assertEquals("o", value.getValue());
    assertEquals("Paper", value.resolve());
    assertEquals(value, field.getNonprojected04());

    value = values.get(4);
    assertEquals("Secondary support material", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getNonprojected05());

    assertTrue(field.validate(MarcVersion.MARC21));
    assertEquals(0, field.getValidationErrors().size());
  }

  @Test
  public void testMotionPicture() {
    Control007 field = new Control007("mu|||||||||||||||||||||");

    List<ControlValue> values = field.getValuesList();
    assertEquals(17, values.size());
    ControlValue value;

    value = values.get(0);
    assertEquals("Category of material", value.getLabel());
    assertEquals("m", value.getValue());
    assertEquals("Motion picture", value.resolve());
    assertEquals(value, field.getMotionPicture00());

    value = values.get(1);
    assertEquals("Specific material designation", value.getDefinition().getLabel());
    assertEquals("u", value.getValue());
    assertEquals("Unspecified", value.resolve());
    assertEquals(value, field.getMotionPicture01());

    value = values.get(2);
    assertEquals("Color", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getMotionPicture03());

    value = values.get(3);
    assertEquals("Motion picture presentation format", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getMotionPicture04());

    value = values.get(4);
    assertEquals("Sound on medium or separate", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getMotionPicture05());

    value = values.get(5);
    assertEquals("Medium for sound", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getMotionPicture06());

    value = values.get(6);
    assertEquals("Dimensions", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getMotionPicture07());

    value = values.get(7);
    assertEquals("Configuration of playback channels", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getMotionPicture08());

    value = values.get(8);
    assertEquals("Production elements", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getMotionPicture09());

    value = values.get(9);
    assertEquals("Positive/negative aspect", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getMotionPicture10());

    value = values.get(10);
    assertEquals("Generation", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getMotionPicture11());

    value = values.get(11);
    assertEquals("Base of film", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getMotionPicture12());

    value = values.get(12);
    assertEquals("Refined categories of color", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getMotionPicture13());

    value = values.get(13);
    assertEquals("Kind of color stock or print", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getMotionPicture14());

    value = values.get(14);
    assertEquals("Deterioration stage", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getMotionPicture15());

    value = values.get(15);
    assertEquals("Completeness", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getMotionPicture16());

    value = values.get(16);
    assertEquals("Film inspection date", value.getDefinition().getLabel());
    assertEquals("||||||", value.getValue());
    assertEquals("||||||", value.resolve());
    assertEquals(value, field.getMotionPicture17());

    assertTrue(field.validate(MarcVersion.MARC21));
    assertEquals(0, field.getValidationErrors().size());
  }

  @Test
  public void testKit() {
    Control007 field = new Control007("ou");

    List<ControlValue> values = field.getValuesList();
    assertEquals(2, values.size());
    ControlValue value;

    value = values.get(0);
    assertEquals("Category of material", value.getLabel());
    assertEquals("o", value.getValue());
    assertEquals("Kit", value.resolve());
    assertEquals(value, field.getKit00());

    value = values.get(1);
    assertEquals("Specific material designation", value.getDefinition().getLabel());
    assertEquals("u", value.getValue());
    assertEquals("Unspecified", value.resolve());
    assertEquals(value, field.getKit01());

    assertTrue(field.validate(MarcVersion.MARC21));
    assertEquals(0, field.getValidationErrors().size());
  }

  @Test
  public void testNotatedMusic() {
    Control007 field = new Control007("qu");

    List<ControlValue> values = field.getValuesList();
    assertEquals(2, values.size());
    ControlValue value;

    value = values.get(0);
    assertEquals("Category of material", value.getLabel());
    assertEquals("q", value.getValue());
    assertEquals("Notated music", value.resolve());
    assertEquals(value, field.getMusic00());

    value = values.get(1);
    assertEquals("Specific material designation", value.getDefinition().getLabel());
    assertEquals("u", value.getValue());
    assertEquals("Unspecified", value.resolve());
    assertEquals(value, field.getMusic01());

    assertTrue(field.validate(MarcVersion.MARC21));
    assertEquals(0, field.getValidationErrors().size());
  }

  @Test
  public void testVideorecording() {
    Control007 field = new Control007("vd cvadz|");

    List<ControlValue> values = field.getValuesList();
    assertEquals(8, values.size());
    ControlValue value;

    value = values.get(0);
    assertEquals("Category of material", value.getLabel());
    assertEquals("v", value.getValue());
    assertEquals("Videorecording", value.resolve());
    assertEquals(value, field.getVideo00());

    value = values.get(1);
    assertEquals("Specific material designation", value.getLabel());
    assertEquals("d", value.getValue());
    assertEquals("Videodisc", value.resolve());
    assertEquals(value, field.getVideo01());

    value = values.get(2);
    assertEquals("Color", value.getDefinition().getLabel());
    assertEquals("c", value.getValue());
    assertEquals("Multicolored", value.resolve());
    assertEquals(value, field.getVideo03());

    value = values.get(3);
    assertEquals("Videorecording format", value.getDefinition().getLabel());
    assertEquals("v", value.getValue());
    assertEquals("DVD", value.resolve());
    assertEquals(value, field.getVideo04());

    value = values.get(4);
    assertEquals("Sound on medium or separate", value.getDefinition().getLabel());
    assertEquals("a", value.getValue());
    assertEquals("Sound on medium", value.resolve());
    assertEquals(value, field.getVideo05());

    value = values.get(5);
    assertEquals("Medium for sound", value.getDefinition().getLabel());
    assertEquals("d", value.getValue());
    assertEquals("Sound disc", value.resolve());
    assertEquals(value, field.getVideo06());

    value = values.get(6);
    assertEquals("Dimensions", value.getDefinition().getLabel());
    assertEquals("z", value.getValue());
    assertEquals("Other", value.resolve());
    assertEquals(value, field.getVideo07());

    value = values.get(7);
    assertEquals("Configuration of playback channels", value.getDefinition().getLabel());
    assertEquals("|", value.getValue());
    assertEquals("No attempt to code", value.resolve());
    assertEquals(value, field.getVideo08());

    assertTrue(field.validate(MarcVersion.MARC21));
    assertEquals(0, field.getValidationErrors().size());
  }

  @Test
  public void testText() {
    Control007 field = new Control007("tu");

    assertEquals(2, field.getMap().size());
    assertEquals("0, 1", StringUtils.join(field.getSubfieldPositions(), ", "));

    assertEquals("tu", field.getContent());
    assertEquals(Control007Category.TEXT, field.getCategory());
    assertEquals("Text", field.getCategoryOfMaterial());

    ControlfieldPositionDefinition subfield;
    subfield = field.getSubfieldByPosition(0);
    assertEquals("Category of material", subfield.getLabel());
    assertEquals("t", field.getMap().get(subfield));
    assertEquals("Text", field.resolve(subfield));
    assertEquals("Text", field.getText00().resolve());

    subfield = field.getSubfieldByPosition(1);
    assertEquals("Specific material designation", subfield.getLabel());
    assertEquals("u", field.getMap().get(subfield));
    assertEquals("Unspecified", field.resolve(subfield));
    assertEquals("Unspecified", field.getText01().resolve());
  }

  @Test
  public void testElectricResource() {
    Control007 field = new Control007("cr uuu---uuuuu");

    assertEquals("cr uuu---uuuuu", field.getContent());

    assertEquals(Control007Category.ELECTRONIC_RESOURCE, field.getCategory());
    assertEquals("Electronic resource", field.getCategoryOfMaterial());

    assertEquals(11, field.getMap().size());
    Set<Integer> subfieldPositions = field.getSubfieldPositions();
    assertEquals("0, 1, 3, 4, 5, 6, 9, 10, 11, 12, 13",
        StringUtils.join(subfieldPositions, ", "));

    ControlfieldPositionDefinition subfield;
    subfield = field.getSubfieldByPosition(0);
    assertEquals("Category of material", subfield.getLabel());
    assertEquals("c", field.getMap().get(subfield));
    assertEquals("Electronic resource", field.resolve(subfield));
    assertEquals("Electronic resource", field.getElectro00().resolve());

    subfield = field.getSubfieldByPosition(1);
    assertEquals("Specific material designation", subfield.getLabel());
    assertEquals("r", field.getMap().get(subfield));
    assertEquals("Remote", field.resolve(subfield));
    assertEquals("Remote", field.getElectro01().resolve());

    subfield = field.getSubfieldByPosition(3);
    assertEquals("Color", subfield.getLabel());
    assertEquals("u", field.getMap().get(subfield));
    assertEquals("Unknown", field.resolve(subfield));
    assertEquals("Unknown", field.getElectro03().resolve());

    subfield = field.getSubfieldByPosition(4);
    assertEquals("Dimensions", subfield.getLabel());
    assertEquals("u", field.getMap().get(subfield));
    assertEquals("Unknown", field.resolve(subfield));
    assertEquals("Unknown", field.getElectro04().resolve());

    subfield = field.getSubfieldByPosition(5);
    assertEquals("Sound", subfield.getLabel());
    assertEquals("u", field.getMap().get(subfield));
    assertEquals("Unknown", field.resolve(subfield));
    assertEquals("Unknown", field.getElectro05().resolve());

    subfield = field.getSubfieldByPosition(6);
    assertEquals("Image bit depth", subfield.getLabel());
    assertEquals("---", field.getMap().get(subfield));
    assertEquals("Unknown", field.resolve(subfield));
    assertEquals("Unknown", field.getElectro06().resolve());

    subfield = field.getSubfieldByPosition(9);
    assertEquals("File formats", subfield.getLabel());
    assertEquals("u", field.getMap().get(subfield));
    assertEquals("Unknown", field.resolve(subfield));
    assertEquals("Unknown", field.getElectro09().resolve());

    subfield = field.getSubfieldByPosition(10);
    assertEquals("Quality assurance targets", subfield.getLabel());
    assertEquals("u", field.getMap().get(subfield));
    assertEquals("Unknown", field.resolve(subfield));
    assertEquals("Unknown", field.getElectro10().resolve());

    subfield = field.getSubfieldByPosition(11);
    assertEquals("Antecedent/source", subfield.getLabel());
    assertEquals("u", field.getMap().get(subfield));
    assertEquals("Unknown", field.resolve(subfield));
    assertEquals("Unknown", field.getElectro11().resolve());

    subfield = field.getSubfieldByPosition(12);
    assertEquals("Level of compression", subfield.getLabel());
    assertEquals("u", field.getMap().get(subfield));
    assertEquals("Unknown", field.resolve(subfield));
    assertEquals("Unknown", field.getElectro12().resolve());

    subfield = field.getSubfieldByPosition(13);
    assertEquals("Reformatting quality", subfield.getLabel());
    assertEquals("u", field.getMap().get(subfield));
    assertEquals("Unknown", field.resolve(subfield));
    assertEquals("Unknown", field.getElectro13().resolve());

    assertTrue(field.validate(MarcVersion.MARC21));
    assertEquals(0, field.getValidationErrors().size());
  }

  @Test
  /**
   * see K. Coyle: MARC21 as Data: A Start
   * (http://journal.code4lib.org/articles/5468)
   */
  public void testSoundRecording() {
    Control007 field = new Control007("sd fsngnn   ed");
    assertEquals("sd fsngnn   ed", field.getContent());

    assertEquals(Control007Category.SOUND_RECORDING, field.getCategory());
    assertEquals("Sound recording", field.getCategoryOfMaterial());

    assertEquals(13, field.getMap().size());
    Set<Integer> subfieldPositions = field.getSubfieldPositions();
    assertEquals("0, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13",
        StringUtils.join(subfieldPositions, ", "));

    ControlfieldPositionDefinition subfield;
    subfield = field.getSubfieldByPosition(0);
    assertEquals("Category of material", subfield.getLabel());
    assertEquals("s", field.getMap().get(subfield));
    assertEquals("Sound recording", field.resolve(subfield));
    assertEquals("Sound recording", field.getSoundRecording00().resolve());

    subfield = field.getSubfieldByPosition(1);
    assertEquals("Specific material designation", subfield.getLabel());
    assertEquals("d", field.getMap().get(subfield));
    assertEquals("Sound disc", field.resolve(subfield));
    assertEquals("d", field.getSoundRecording01().getValue());
    assertEquals("Sound disc", field.getSoundRecording01().resolve());

    subfield = field.getSubfieldByPosition(3);
    assertEquals("Speed", subfield.getLabel());
    assertEquals("f", field.getMap().get(subfield));
    assertEquals("1.4 m. per second (discs)", field.resolve(subfield));
    assertEquals("f", field.getSoundRecording03().getValue());
    assertEquals("1.4 m. per second (discs)", field.getSoundRecording03().resolve());

    subfield = field.getSubfieldByPosition(4);
    assertEquals("Configuration of playback channels", subfield.getLabel());
    assertEquals("s", field.getMap().get(subfield));
    assertEquals("Stereophonic", field.resolve(subfield));
    assertEquals("s", field.getSoundRecording04().getValue());
    assertEquals("Stereophonic", field.getSoundRecording04().resolve());

    subfield = field.getSubfieldByPosition(5);
    assertEquals("Groove width/groove pitch", subfield.getLabel());
    assertEquals("n", field.getMap().get(subfield));
    assertEquals("Not applicable", field.resolve(subfield));
    assertEquals("Not applicable", field.getSoundRecording05().resolve());

    subfield = field.getSubfieldByPosition(6);
    assertEquals("Dimensions", subfield.getLabel());
    assertEquals("g", field.getMap().get(subfield));
    assertEquals("4 3/4 in. or 12 cm. diameter", field.resolve(subfield));
    assertEquals("4 3/4 in. or 12 cm. diameter", field.getSoundRecording06().resolve());

    subfield = field.getSubfieldByPosition(7);
    assertEquals("Tape width", subfield.getLabel());
    assertEquals("n", field.getMap().get(subfield));
    assertEquals("Not applicable", field.resolve(subfield));
    assertEquals("Not applicable", field.getSoundRecording07().resolve());

    subfield = field.getSubfieldByPosition(8);
    assertEquals("Tape configuration", subfield.getLabel());
    assertEquals("n", field.getMap().get(subfield));
    assertEquals("Not applicable", field.resolve(subfield));
    assertEquals("Not applicable", field.getSoundRecording08().resolve());

    subfield = field.getSubfieldByPosition(9);
    assertEquals("Kind of disc, cylinder, or tape", subfield.getLabel());
    assertEquals(" ", field.getMap().get(subfield));
    assertEquals(" ", field.resolve(subfield));
    assertEquals(" ", field.getSoundRecording09().resolve());

    subfield = field.getSubfieldByPosition(10);
    assertEquals("Kind of material", subfield.getLabel());
    assertEquals(" ", field.getMap().get(subfield));
    assertEquals(" ", field.resolve(subfield));
    assertEquals(" ", field.getSoundRecording10().resolve());

    subfield = field.getSubfieldByPosition(11);
    assertEquals("Kind of cutting", subfield.getLabel());
    assertEquals(" ", field.getMap().get(subfield));
    assertEquals(" ", field.resolve(subfield));
    assertEquals(" ", field.getSoundRecording11().resolve());

    subfield = field.getSubfieldByPosition(12);
    assertEquals("Special playback characteristics", subfield.getLabel());
    assertEquals("e", field.getMap().get(subfield));
    assertEquals("Digital recording", field.resolve(subfield));
    assertEquals("Digital recording", field.getSoundRecording12().resolve());

    subfield = field.getSubfieldByPosition(13);
    assertEquals("Capture and storage technique", subfield.getLabel());
    assertEquals("d", field.getMap().get(subfield));
    assertEquals("Digital storage", field.resolve(subfield));
    assertEquals("Digital storage", field.getSoundRecording13().resolve());

    assertFalse(field.validate(MarcVersion.MARC21));
    assertEquals(3, field.getValidationErrors().size());

    assertEquals("007/09 (007soundRecording09)",
      field.getValidationErrors().get(0).getMarcPath());
    assertEquals(" ",
      field.getValidationErrors().get(0).getMessage());
    assertEquals("hasInvalidValue",
      field.getValidationErrors().get(0).getType().getCode());
    assertEquals(ValidationErrorType.CONTROL_POSITION_INVALID_VALUE,
      field.getValidationErrors().get(0).getType());
    assertEquals("https://www.loc.gov/marc/bibliographic/bd007s.html",
      field.getValidationErrors().get(0).getUrl());

    assertEquals("007/10 (007soundRecording10)",
      field.getValidationErrors().get(1).getMarcPath());
    assertEquals(" ",
      field.getValidationErrors().get(1).getMessage());
    assertEquals("hasInvalidValue",
      field.getValidationErrors().get(1).getType().getCode());
    assertEquals(ValidationErrorType.CONTROL_POSITION_INVALID_VALUE,
      field.getValidationErrors().get(1).getType());
    assertEquals("https://www.loc.gov/marc/bibliographic/bd007s.html",
      field.getValidationErrors().get(1).getUrl());

    assertEquals("007/11 (007soundRecording11)",
      field.getValidationErrors().get(2).getMarcPath());
    assertEquals(" ",
      field.getValidationErrors().get(2).getMessage());
    assertEquals("hasInvalidValue",
      field.getValidationErrors().get(2).getType().getCode());
    assertEquals(ValidationErrorType.CONTROL_POSITION_INVALID_VALUE,
      field.getValidationErrors().get(2).getType());
    assertEquals("https://www.loc.gov/marc/bibliographic/bd007s.html",
      field.getValidationErrors().get(2).getUrl());
  }
}
