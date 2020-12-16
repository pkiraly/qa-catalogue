package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.ControlValue;
import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
  public void test801003s1958ja0000jpn() {
    Control008 field = new Control008("801003s1958    ja            000 0 jpn  ", Leader.Type.MAPS);

    ControlSubfieldDefinition subfield;
    subfield = field.getSubfieldByPosition(0);
    assertEquals("Date entered on file", subfield.getLabel());
    assertEquals("801003", field.getMap().get(subfield));
    assertEquals("801003", field.getTag008all00().resolve());

    subfield = field.getSubfieldByPosition(6);
    assertEquals("Type of date/Publication status", subfield.getLabel());
    assertEquals("Single known date/probable date", field.resolve(subfield));
    assertEquals("Single known date/probable date", field.getTag008all06().resolve());

    subfield = field.getSubfieldByPosition(7);
    assertEquals("Date 1", subfield.getLabel());
    assertEquals("1958", field.resolve(subfield));
    assertEquals("1958", field.getTag008all07().resolve());

    subfield = field.getSubfieldByPosition(11);
    assertEquals("Date 2", subfield.getLabel());
    assertEquals("    ", field.resolve(subfield));
    assertEquals("    ", field.getTag008all11().resolve());

    subfield = field.getSubfieldByPosition(15);
    assertEquals("Place of publication, production, or execution", subfield.getLabel());
    assertEquals("Japan", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(35);
    assertEquals("Language", subfield.getLabel());
    assertEquals("Japanese", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(38);
    assertEquals("Modified record", subfield.getLabel());
    assertEquals("Not modified", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(39);
    assertEquals("Cataloging source", subfield.getLabel());
    assertEquals("National bibliographic agency", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(18);
    assertEquals("Relief", subfield.getLabel());
    assertEquals("No relief shown", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(22);
    assertEquals("Projection", subfield.getLabel());
    assertEquals("Projection not specified", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(25);
    assertEquals("Type of cartographic material", subfield.getLabel());
    assertEquals(" ", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(28);
    assertEquals("Government publication", subfield.getLabel());
    assertEquals("Not a government publication", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(29);
    assertEquals("Form of item", subfield.getLabel());
    assertEquals("0", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(31);
    assertEquals("Index", subfield.getLabel());
    assertEquals("No index", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(33);
    assertEquals("Special format characteristics", subfield.getLabel());
    assertEquals("0, No specified special format characteristics", field.resolve(subfield));
  }

  @Test
  public void test981123p19981996enkmunefhid() {
    Control008 field = new Control008("981123p19981996enkmun   efhi           d", Leader.Type.BOOKS);

    ControlSubfieldDefinition subfield;
    subfield = field.getSubfieldByPosition(0);
    assertEquals("Date entered on file", subfield.getLabel());
    assertEquals("981123", field.getMap().get(subfield));

    subfield = field.getSubfieldByPosition(6);
    assertEquals("Type of date/Publication status", subfield.getLabel());
    assertEquals("Date of distribution/release/issue and production/recording session when different", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(7);
    assertEquals("Date 1", subfield.getLabel());
    assertEquals("1998", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(11);
    assertEquals("Date 2", subfield.getLabel());
    assertEquals("1996", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(15);
    assertEquals("Place of publication, production, or execution", subfield.getLabel());
    assertEquals("England", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(35);
    assertEquals("Language", subfield.getLabel());
    assertEquals("   ", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(38);
    assertEquals("Modified record", subfield.getLabel());
    assertEquals("Not modified", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(39);
    assertEquals("Cataloging source", subfield.getLabel());
    assertEquals("Other", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(18);
    assertEquals("Illustrations", subfield.getLabel());
    assertEquals("Phonodisc, phonowire, etc., u, n, No illustrations", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(22);
    assertEquals("Target audience", subfield.getLabel());
    assertEquals("Unknown or not specified", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(23);
    assertEquals("Form of item", subfield.getLabel());
    assertEquals("None of the following", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(24);
    assertEquals("Nature of contents", subfield.getLabel());
    assertEquals("Encyclopedias, Handbooks, h, Indexes", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(28);
    assertEquals("Government publication", subfield.getLabel());
    assertEquals("Not a government publication", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(29);
    assertEquals("Conference publication", subfield.getLabel());
    assertEquals(" ", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(30);
    assertEquals("Festschrift", subfield.getLabel());
    assertEquals(" ", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(31);
    assertEquals("Index", subfield.getLabel());
    assertEquals(" ", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(33);
    assertEquals("Literary form", subfield.getLabel());
    assertEquals(" ", field.resolve(subfield));

    subfield = field.getSubfieldByPosition(34);
    assertEquals("Biography", subfield.getLabel());
    assertEquals("No biographical material", field.resolve(subfield));
  }

  @Test
  public void getMap() {
    Control008 field = new Control008(
      "981123p19981996enkmun   efhi           d",
      Leader.Type.BOOKS
    );
    assertEquals(18, field.getMap().size());
    assertEquals(
      Arrays.asList(
        "tag008all00", "tag008all06", "tag008all07", "tag008all11", "tag008all15",
        "tag008all35", "tag008all38", "tag008all39", "tag008book18", "tag008book22",
        "tag008book23", "tag008book24", "tag008book28", "tag008book29", "tag008book30",
        "tag008book31", "tag008book33", "tag008book34"
      ),
      field.getMap().keySet().stream()
        .map(x -> x.getId())
        .collect(Collectors.toList())
    );

    assertEquals(
      Arrays.asList(
        "981123", "p", "1998", "1996", "enk", "   ", " ", "d", "mun ",
        " ", " ", "efhi", " ", " ", " ", " ", " ", " "
      ),
      field.getMap().values().stream()
        .collect(Collectors.toList())
    );
  }

  @Test
  public void getLabel() {
    Control008 field = new Control008(
      "981123p19981996enkmun   efhi           d",
      Leader.Type.BOOKS
    );
    assertEquals("General Information", field.getLabel());
  }

  @Test
  public void getCardinality() {
    Control008 field = new Control008(
      "981123p19981996enkmun   efhi           d",
      Leader.Type.BOOKS
    );
    assertEquals(Cardinality.Nonrepeatable, field.getCardinality());
  }

  @Test
  public void getControlValueByPosition() {
    Control008 field = new Control008(
      "981123p19981996enkmun   efhi           d",
      Leader.Type.BOOKS
    );
    ControlValue value = field.getControlValueByPosition(0);
    assertEquals("981123", value.getValue());
    assertEquals("tag008all00", value.getId());
    assertEquals("Date entered on file", value.getLabel());
    assertNull(value.getValidationErrors());
  }
}
