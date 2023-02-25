package de.gwdg.metadataqa.marc.analysis.validator;

import de.gwdg.metadataqa.marc.cli.utils.IgnorableFields;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ValidatorConfigurationTest {

  @Test
  public void empty() {
    ValidatorConfiguration config = new ValidatorConfiguration();
    assertEquals(MarcVersion.MARC21, config.getMarcVersion());
    assertNull(config.getIgnorableFields());
    assertNull(config.getIgnorableIssueTypes());
    assertFalse(config.doSummary());
  }

  @Test
  public void withMarcVersion_marc() {
    ValidatorConfiguration config = new ValidatorConfiguration().withMarcVersion(MarcVersion.MARC21);
    assertEquals(MarcVersion.MARC21, config.getMarcVersion());
  }

  @Test
  public void withMarcVersion_szte() {
    ValidatorConfiguration config = new ValidatorConfiguration().withMarcVersion(MarcVersion.SZTE);
    assertEquals(MarcVersion.SZTE, config.getMarcVersion());
  }

  @Test
  public void withDoSummary_default() {
    ValidatorConfiguration config = new ValidatorConfiguration();
    assertEquals(false, config.doSummary());
  }

  @Test
  public void withDoSummary_false() {
    ValidatorConfiguration config = new ValidatorConfiguration().withDoSummary(false);
    assertEquals(false, config.doSummary());
  }

  @Test
  public void withDoSummary_true() {
    ValidatorConfiguration config = new ValidatorConfiguration().withDoSummary(true);
    assertEquals(true, config.doSummary());
  }

  @Test
  public void withIgnorableFields() {
    IgnorableFields fields = new IgnorableFields();
    fields.parseFields("200");

    ValidatorConfiguration config = new ValidatorConfiguration().withIgnorableFields(fields);
    assertEquals(List.of("200"), config.getIgnorableFields().getFields());
  }

  @Test
  public void withIgnorableIssueTypes() {
    List<ValidationErrorType> ignorableIssueTypes = new ArrayList<>();
    ignorableIssueTypes.add(ValidationErrorType.FIELD_NONREPEATABLE);

    ValidatorConfiguration config = new ValidatorConfiguration().withIgnorableIssueTypes(ignorableIssueTypes);
    assertEquals(1, config.getIgnorableIssueTypes().size());
    assertEquals(ValidationErrorType.FIELD_NONREPEATABLE, config.getIgnorableIssueTypes().get(0));
  }
}