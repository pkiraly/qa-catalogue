package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.controlsubfields.tag008.Tag008all06;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import de.gwdg.metadataqa.marc.definition.controlsubfields.tag007.Tag007nonprojected01;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ValidationErrorTest {

  @Test
  public void testBasics() {
    ValidationError error = new ValidationError("123", "007/01", ValidationErrorType.CONTROL_SUBFIELD_OBSOLETE_CODE,
      "c", Tag007nonprojected01.getInstance().getDescriptionUrl());
    assertEquals("123", error.getRecordId());
    assertEquals("007/01", error.getMarcPath());
    assertEquals(ValidationErrorType.CONTROL_SUBFIELD_OBSOLETE_CODE, error.getType());
    assertEquals("c", error.getMessage());
    assertEquals("https://www.loc.gov/marc/bibliographic/bd007k.html", error.getUrl());
  }

  @Test
  public void testComparision() {
    ValidationError error1 = new ValidationError("123", "007/01", ValidationErrorType.CONTROL_SUBFIELD_OBSOLETE_CODE,
      "c", Tag007nonprojected01.getInstance().getDescriptionUrl());
    ValidationError error2 = new ValidationError("123", "007/01", ValidationErrorType.CONTROL_SUBFIELD_OBSOLETE_CODE,
      "c", Tag007nonprojected01.getInstance().getDescriptionUrl());
    assertEquals(error1.hashCode(), error2.hashCode());
    assertTrue(error1.equals(error2));
  }

  @Test
  public void testComparision_givenDifferentIds() {
    Map<ValidationError, Integer> vErrorCounter = new HashMap<>();

    ValidationError error1 = new ValidationError("123", "008/06 (tag008all06)", ValidationErrorType.CONTROL_SUBFIELD_INVALID_VALUE,
      " ", Tag008all06.getInstance().getDescriptionUrl());
    ValidationError error2 = new ValidationError("124", "008/06 (tag008all06)", ValidationErrorType.CONTROL_SUBFIELD_INVALID_VALUE,
      " ", Tag008all06.getInstance().getDescriptionUrl());

    assertEquals(error1, error2);
  }

  @Test
  public void testCollector() {
    Map<ValidationError, Integer> vErrorCounter = new HashMap<>();

    ValidationError error1 = new ValidationError("123", "007/01", ValidationErrorType.CONTROL_SUBFIELD_OBSOLETE_CODE,
      "c", Tag007nonprojected01.getInstance().getDescriptionUrl());

    if (!vErrorCounter.containsKey(error1))
      error1.setId(1);
    Utils.count(error1, vErrorCounter);

    ValidationError error2 = new ValidationError("124", "007/01", ValidationErrorType.CONTROL_SUBFIELD_OBSOLETE_CODE,
      "c", Tag007nonprojected01.getInstance().getDescriptionUrl());

    if (!vErrorCounter.containsKey(error2)) {
      error2.setId(2);
    }
    Utils.count(error2, vErrorCounter);

    assertEquals(1, vErrorCounter.size());
    assertEquals(2, (int) vErrorCounter.get(error2));
    assertEquals(1, (int) ((ValidationError) vErrorCounter.keySet().toArray()[0]).getId());

    System.err.println(error1.hashCode());
  }

}

