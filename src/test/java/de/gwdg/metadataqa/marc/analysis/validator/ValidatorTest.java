package de.gwdg.metadataqa.marc.analysis.validator;

import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.cli.CliTestUtils;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import de.gwdg.metadataqa.marc.utils.QAMarcReaderFactory;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaManager;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaReader;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import static org.junit.Assert.*;

public class ValidatorTest {

  MarcReader reader;

  @Test
  public void validate() {
    PicaSchemaManager schema = PicaSchemaReader.createSchema(CliTestUtils.getTestResource("pica/k10plus.json"));
    try {
      reader = QAMarcReaderFactory.getFileReader(MarcFormat.PICA_NORMALIZED, CliTestUtils.getTestResource("pica/pica-with-holdings-info.dat"), null);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    assertNotNull(reader);
    assertEquals("PicaNormalizedReader", reader.getClass().getSimpleName());
    assertTrue(reader.hasNext());
    Record record = reader.next();
    assertEquals("org.marc4j.marc.impl.RecordImpl", record.getClass().getCanonicalName());
    assertNotNull(record);
    BibliographicRecord marcRecord = MarcFactory.createPicaFromMarc4j(record, schema);
    assertNotNull(marcRecord);

    Validator validator = new Validator(new ValidatorConfiguration().withSchemaType(SchemaType.PICA));
    boolean valid = validator.validate(marcRecord);
    assertFalse(valid);
    assertEquals(26, validator.getValidationErrors().size());
    assertEquals("001@", validator.getValidationErrors().get(0).getMarcPath());
    assertEquals(ValidationErrorType.FIELD_UNDEFINED, validator.getValidationErrors().get(0).getType());
    assertEquals(null, validator.getValidationErrors().get(0).getUrl());

    assertEquals("001U", validator.getValidationErrors().get(1).getMarcPath());
    assertEquals("036F/01", validator.getValidationErrors().get(2).getMarcPath());

    assertEquals("013D", validator.getValidationErrors().get(3).getMarcPath());
    assertEquals(ValidationErrorType.SUBFIELD_UNDEFINED, validator.getValidationErrors().get(3).getType());
    assertEquals("https://format.k10plus.de/k10plushelp.pl?cmd=kat&katalog=Standard&val=1131", validator.getValidationErrors().get(3).getUrl());
    assertEquals("013D", validator.getValidationErrors().get(3).getMarcPath());
    assertEquals("V", validator.getValidationErrors().get(3).getMessage());
  }
}