package de.gwdg.metadataqa.marc.definition.tags.nkcrtags;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.analysis.validator.DataFieldValidator;
import de.gwdg.metadataqa.marc.analysis.validator.ValidatorConfiguration;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.Leader;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import de.gwdg.metadataqa.marc.utils.marcreader.AlephseqMarcReader;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class NkcrMarcReaderTest {

  @Test
  public void testFieldValidation() {
    Path path = null;
    try {
      path = FileUtils.getPath("marc/nkcr-sample-records.txt");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    var i = 0;
    Record marc4jRecord = null;
    MarcReader reader = new AlephseqMarcReader(path.toString());
    while (reader.hasNext()) {
      i++;
      marc4jRecord = reader.next();
      assertNotNull(marc4jRecord);
      BibliographicRecord marcRecord = MarcFactory.createFromMarc4j(
        marc4jRecord, Leader.Type.BOOKS, MarcVersion.NKCR, "^"
      );
      assertNotNull(marcRecord);

      List<String> nkrcFields = Arrays.asList(
        "591", "902", "903", "910", "964", "967", "975", "982", "984"
      );
      DataFieldValidator validator = new DataFieldValidator(new ValidatorConfiguration().withMarcVersion(MarcVersion.NKCR));
      for (String nkrcField : nkrcFields) {
        if (marcRecord.hasDatafield(nkrcField)) {
          for (DataField field : marcRecord.getDatafield(nkrcField)) {
            boolean isValid = validator.validate(field);
            if (i == 5 && nkrcField.equals("910")) {
              assertEquals(1, validator.getValidationErrors().size());
              ValidationError error = validator.getValidationErrors().get(0);
              assertEquals(ValidationErrorType.SUBFIELD_INVALID_VALUE, error.getType());
              assertEquals("910$k", error.getMarcPath());
              assertEquals("r-dod", error.getMessage());
            } else if (i == 7 && nkrcField.equals("982")) {
              assertEquals(1, validator.getValidationErrors().size());
              ValidationError error = validator.getValidationErrors().get(0);
              assertEquals(ValidationErrorType.SUBFIELD_UNPARSABLE_CONTENT, error.getType());
              assertEquals("982$6", error.getMarcPath());
              // assertEquals("r-dod", error.getMessage());
            } else if (i == 11 && nkrcField.equals("910")) {
              assertEquals(1, validator.getValidationErrors().size());
              ValidationError error = validator.getValidationErrors().get(0);
              assertEquals(ValidationErrorType.SUBFIELD_INVALID_VALUE, error.getType());
              assertEquals("910$k", error.getMarcPath());
              assertEquals("r-dod", error.getMessage());
            } else {
              assertTrue(isValid);
            }
          }
        }
      }
    }
  }
}