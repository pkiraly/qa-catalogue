package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.model.SolrFieldType;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MappingParametersTest {

  private MappingParameters parameters;
  private String errorMessage;

  @Before
  public void setUp() throws Exception {
    parameters = null;
    errorMessage = null;
  }

  @Test
  public void doExportSubfieldCodes() {
    String[] arguments = new String[]{"--withSubfieldCodelists", "a-marc-file.mrc"};
    try {
      parameters = new MappingParameters(arguments);
    } catch (ParseException e) {
      errorMessage = null;
    }
    assertNotNull("The parameters should not be null", parameters);
    assertNull("The error messages should be null", errorMessage);
    assertTrue(parameters.doExportSubfieldCodes());
  }

  @Test
  public void doExportSelfDescriptiveCodes() {
    String[] arguments = new String[]{"--withSelfDescriptiveCode", "a-marc-file.mrc"};
    try {
      parameters = new MappingParameters(arguments);
    } catch (ParseException e) {
      errorMessage = null;
    }
    assertNotNull("The parameters should not be null", parameters);
    assertNull("The error messages should be null", errorMessage);
    assertTrue(parameters.doExportSelfDescriptiveCodes());
  }

  @Test
  public void getSolrFieldType() {
    String[] arguments = new String[]{"--solrFieldType", "marc-tags", "a-marc-file.mrc"};
    try {
      parameters = new MappingParameters(arguments);
    } catch (ParseException e) {
      errorMessage = null;
    }
    assertNotNull("The parameters should not be null", parameters);
    assertNull("The error messages should be null", errorMessage);
    assertEquals(SolrFieldType.MARC, parameters.getSolrFieldType());
  }

  @Test
  public void doExportFrbrFunctions() {
    String[] arguments = new String[]{"--withFrbrFunctions", "a-marc-file.mrc"};
    try {
      parameters = new MappingParameters(arguments);
    } catch (ParseException e) {
      errorMessage = null;
    }
    assertNotNull("The parameters should not be null", parameters);
    assertNull("The error messages should be null", errorMessage);
    assertTrue(parameters.doExportFrbrFunctions());
  }

  @Test
  public void doExportCompilanceLevel() {
    String[] arguments = new String[]{"--withComplianceLevel", "a-marc-file.mrc"};
    try {
      parameters = new MappingParameters(arguments);
    } catch (ParseException e) {
      errorMessage = null;
    }
    assertNotNull("The parameters should not be null", parameters);
    assertNull("The error messages should be null", errorMessage);
    assertTrue(parameters.doExportCompilanceLevel());
  }

  @Test
  public void isWithLocallyDefinedFields() {
    String[] arguments = new String[]{"--withLocallyDefinedFields", "a-marc-file.mrc"};
    try {
      parameters = new MappingParameters(arguments);
    } catch (ParseException e) {
      errorMessage = null;
    }
    assertNotNull("The parameters should not be null", parameters);
    assertNull("The error messages should be null", errorMessage);
    assertTrue(parameters.isWithLocallyDefinedFields());
  }
}