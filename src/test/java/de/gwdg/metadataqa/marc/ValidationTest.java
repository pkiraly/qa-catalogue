package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.analysis.validator.Validator;
import de.gwdg.metadataqa.marc.analysis.validator.ValidatorConfiguration;
import de.gwdg.metadataqa.marc.dao.Control003;
import de.gwdg.metadataqa.marc.dao.Control005;
import de.gwdg.metadataqa.marc.dao.Control008;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.Marc21Leader;
import de.gwdg.metadataqa.marc.dao.MarcLeader;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.tags.oclctags.Tag090;
import de.gwdg.metadataqa.marc.definition.tags.sztetags.Tag596;
import de.gwdg.metadataqa.marc.definition.tags.tags01x.Tag010;
import de.gwdg.metadataqa.marc.definition.tags.tags20x.Tag245;
import de.gwdg.metadataqa.marc.definition.tags.tags20x.Tag246;
import de.gwdg.metadataqa.marc.definition.tags.tags5xx.Tag546;
import de.gwdg.metadataqa.marc.definition.tags.tags6xx.Tag630;
import de.gwdg.metadataqa.marc.definition.tags.tags6xx.Tag650;
import de.gwdg.metadataqa.marc.definition.tags.tags84x.Tag880;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormatter;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class ValidationTest {

  @Test
  public void testStructureDefinitionReader() throws URISyntaxException, IOException {
    MarcStructureDefinitionReader reader = new MarcStructureDefinitionReader("multiline.txt");
    List<MarcField> fields = reader.getFields();
    assertEquals(2, fields.size());
  }

  @Test
  public void testFullStructure() throws URISyntaxException, IOException {
    MarcStructureDefinitionReader reader = new MarcStructureDefinitionReader("general/marc-structure.txt");
    List<MarcField> fields = reader.getFields();
    assertEquals(238, fields.size());
  }

  @Test
  public void test246_6() throws URISyntaxException, IOException {
    Marc21Record marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.setLeader(new Marc21Leader("00860cam a22002774a 45 0"));
    marcRecord.setControl003(new Control003("SIRSI"));
    marcRecord.setControl005(new Control005("20080331162830.0"));
    marcRecord.setControl008(new Control008("070524s2007    cc a          001 0 chi", MarcLeader.Type.BOOKS));

    List<DataField> fields = Arrays.asList(
      new DataField(Tag010.getInstance(), " ", " ", "a", "  2007929507"),
      new DataField(Tag090.getInstance(), " ", " ", "a", "  004.16 IPH"),
      new DataField(Tag245.getInstance(), "0", "0",
        "6", "880-01",
        "a", "iPhone the Bible wan jia sheng jing."),
      new DataField(Tag246.getInstance(), "3", "0",
        "6", "880-02",
        "a", "Wan jia sheng jing"),
      new DataField(Tag245.getInstance(), "3", "0",
        "6", "880-03",
        "a", "iPhone the bible"),
      new DataField(Tag546.getInstance(), " ", " ",
        "a", "Text in traditional Chinese characters."),
      new DataField(Tag630.getInstance(), "0", "0",
        "a", "iPhoto (Computer file)"),
      new DataField(Tag650.getInstance(), " ", "0", "a", "iPhoto (Smartphone)"),
      new DataField(Tag650.getInstance(), " ", "0", "a", "Cell phones."),
      new DataField(Tag650.getInstance(), " ", "0", "a", "Digital music players."),
      new DataField(Tag650.getInstance(), " ", "0", "a", "Pocket computers."),
      new DataField(Tag650.getInstance(), " ", "4", "a", "Chinese books (Traditional characters)"),
      new DataField(Tag596.getInstance(), " ", " ", "a", "4"),
      new DataField(Tag880.getInstance(), " ", " ", "6", "245-01/$1",
        "a", "iphone the Bible 玩家聖經."),
      new DataField(Tag880.getInstance(), " ", " ", "6", "246-02/$1",
        "a", "玩家聖經"),
      new DataField(Tag880.getInstance(), "3", "0", "6", "246-03", "6", "880-03",
        "a", "iphone wan jia sheng jing"),
      new DataField(Tag880.getInstance(), " ", " ", "6", "246-03/$1",
        "a", "iphonewan 玩家聖經")
    );

    for (DataField field : fields) {
      marcRecord.addDataField(field);
      field.setBibliographicRecord(marcRecord);
    }

    Validator validator = new Validator(new ValidatorConfiguration().withMarcVersion(MarcVersion.GENT).withDoSummary(false));
    boolean isValid = validator.validate(marcRecord);
    if (!isValid) {
      String message = ValidationErrorFormatter.format(validator.getValidationErrors(), ValidationErrorFormat.TEXT);
      assertTrue(message.contains("880$6: 3 - ambiguous linkage 'There are multiple $6'"));
    }
  }

  @Test
  public void testAFile() throws URISyntaxException, IOException {
    List<String> lines = FileUtils.readLinesFromResource("marctxt/010000011.mrctxt");
    BibliographicRecord marcRecord = MarcFactory.createFromFormattedText(lines, MarcVersion.MARC21);
    Validator validator = new Validator(new ValidatorConfiguration().withMarcVersion(MarcVersion.GENT).withDoSummary(true));
    boolean isValid = validator.validate(marcRecord);
    assertFalse(validator.validate(marcRecord));
    assertEquals(21, validator.getValidationErrors().size());
  }

  @Test
  public void testABLFile() throws URISyntaxException, IOException {
    List<String> lines = FileUtils.readLinesFromResource("bl/006013122.mrctxt");
    BibliographicRecord marcRecord = MarcFactory.createFromFormattedText(lines, MarcVersion.BL);
    assertEquals(Arrays.asList(
      "FMT", "019", "020", "040", "100", "245", "260", "336", "337", "338", "590",
      "966", "979", "CAT", "CAT", "CAT", "CAT", "FIN", "LEO", "SRC", "STA", "LAS"),
      marcRecord.getDatafields().stream().map(DataField::getTag).collect(Collectors.toList()));
    assertTrue(marcRecord.hasDatafield("STA"));
  }
}
