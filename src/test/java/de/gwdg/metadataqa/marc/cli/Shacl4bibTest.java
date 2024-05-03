package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.configuration.schema.Rule;
import de.gwdg.metadataqa.api.json.DataElement;
import de.gwdg.metadataqa.api.rule.RuleCatalog;
import de.gwdg.metadataqa.api.schema.BaseSchema;
import de.gwdg.metadataqa.api.schema.Schema;
import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.CsvUtils;
import de.gwdg.metadataqa.marc.RuleCatalogUtils;
import de.gwdg.metadataqa.marc.TestUtils;
import de.gwdg.metadataqa.marc.cli.utils.MarcSpecSelector;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.tags.tags20x.Tag245;
import de.gwdg.metadataqa.marc.definition.tags.tags3xx.Tag300;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class Shacl4bibTest extends CliTestUtils {

  private String inputFile;
  private String outputDir;
  private List<String> outputFiles;

  @Before
  public void setUp() throws Exception {
    inputFile = TestUtils.getPath("alephseq/alephseq-example3.txt");
    outputDir = TestUtils.getPath("output");
    outputFiles = Arrays.asList(
      "shacl.csv"
    );
  }

  @Test
  public void contructor_kbr() throws ParseException, IOException {
    clearOutput(outputDir, outputFiles);

    Shacl4bib processor = new Shacl4bib(new String[]{
      "--defaultRecordType", "BOOKS",
      "--marcVersion", "GENT",
      "--alephseq",
      "--outputDir", outputDir,
      "--shaclConfigurationFile", TestUtils.getPath("shacl/kbr-small.yaml"),
      "--shaclOutputFile", "shacl.csv",
      inputFile
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();

    for (String outputFile : outputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(outputFile + " should exist", output.exists());
      List<String> lines = FileUtils.readLinesFromFile(output.getAbsolutePath());
      if (outputFile.equals("shacl.csv")) {
        assertEquals(2, lines.size());
        assertEquals("id,040$a.minCount,040$a.pattern", lines.get(0).trim());
        assertEquals("000000002,1,0", lines.get(1).trim());
      }
    }
  }

  @Test
  public void main() throws ParseException, IOException {
    clearOutput(outputDir, outputFiles);

    Shacl4bib.main(new String[]{
      "--defaultRecordType", "BOOKS",
      "--marcVersion", "GENT",
      "--alephseq",
      "--outputDir", outputDir,
      "--shaclConfigurationFile", TestUtils.getPath("shacl/kbr-small.yaml"),
      "--shaclOutputFile", "shacl.csv",
      inputFile
    });

    for (String outputFile : outputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(outputFile + " should exist", output.exists());
      List<String> lines = FileUtils.readLinesFromFile(output.getAbsolutePath());
      if (outputFile.equals("shacl.csv")) {
        assertEquals(2, lines.size());
        assertEquals("id,040$a.minCount,040$a.pattern", lines.get(0).trim());
        assertEquals("000000002,1,0", lines.get(1).trim());
      }
    }
  }

  @Test
  public void main_json() throws ParseException, IOException {
    clearOutput(outputDir, outputFiles);

    Shacl4bib.main(new String[]{
      "--defaultRecordType", "BOOKS",
      "--marcVersion", "GENT",
      "--alephseq",
      "--outputDir", outputDir,
      "--shaclConfigurationFile", TestUtils.getPath("shacl/kbr-small.json"),
      "--shaclOutputFile", "shacl.csv",
      inputFile
    });

    for (String outputFile : outputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(outputFile + " should exist", output.exists());
      List<String> lines = FileUtils.readLinesFromFile(output.getAbsolutePath());
      if (outputFile.equals("shacl.csv")) {
        assertEquals(2, lines.size());
        assertEquals("id,040$a.1,040$a.2", lines.get(0).trim());
        assertEquals("000000002,1,0", lines.get(1).trim());
      }
    }
  }

  @Test
  public void regex_1() {
    String text = "116 p.";
    Pattern pattern = Pattern.compile("^.*\\d+ p\\.?\\s*$");
    assertTrue("should fit", pattern.matcher(text).matches());
  }

  @Test
  public void regex_2() {
    String text = "116 € p.";
    Pattern pattern = Pattern.compile("^.*€.*$");
    assertTrue("should fit", pattern.matcher(text).matches());
  }

  @Test
  public void testField_without_subfield() {
    Marc21BibliographicRecord marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag245.getInstance(),"0", "0",
      "6", "880-01",
      "a", "iPhone the Bible wan jia sheng jing."
    ));

    Schema schema = new BaseSchema().addField(new DataElement("245", "245")
      .setRule(List.of(new Rule().withId("245").withMinCount(1))));
    RuleCatalog ruleCatalog = RuleCatalogUtils.create(schema);

    MarcSpecSelector selector = new MarcSpecSelector(marcRecord);
    assertEquals("880-01 iPhone the Bible wan jia sheng jing.", selector.extract("245").get(0));
    List<Object> values = RuleCatalogUtils.extract(ruleCatalog, ruleCatalog.measure(selector));
    assertEquals("1", CsvUtils.createCsvFromObjects(values).trim());
  }

  @Test
  public void testField_depends_on_existing_fiels() {
    Marc21BibliographicRecord marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag245.getInstance(),"0", "0",
      "6", "880-01",
      "a", "iPhone the Bible wan jia sheng jing."
    ));
    marcRecord.addDataField(new DataField(Tag300.getInstance()," ", " ",
      "a", "tIII, 83 Bl.",
      "b", "graph. Darst."
    ));

    Schema schema = new BaseSchema()
      .addField(new DataElement("245", "245")
        .setRule(List.of(new Rule().withId("245").withMinCount(1).withHidden(true))))
      .addField(new DataElement("300a", "300$a")
        .setRule(List.of(new Rule().withId("300$a").withAnd(
              List.of(new Rule().withDependencies(List.of("245")), new Rule().withMinCount(1))
      ))))
    ;
    RuleCatalog ruleCatalog = RuleCatalogUtils.create(schema);

    MarcSpecSelector selector = new MarcSpecSelector(marcRecord);
    assertEquals("880-01 iPhone the Bible wan jia sheng jing.", selector.extract("245").get(0));
    List<Object> values = RuleCatalogUtils.extract(ruleCatalog, ruleCatalog.measure(selector));
    assertEquals(List.of("300$a"), ruleCatalog.getHeader());
    assertEquals("1", CsvUtils.createCsvFromObjects(values).trim());
  }

  @Test
  public void testField_fail_if_depends_on_nonexisting_field() {
    Marc21BibliographicRecord marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag245.getInstance(),"0", "0",
      "6", "880-01", "a", "iPhone the Bible wan jia sheng jing."));
    marcRecord.addDataField(new DataField(Tag300.getInstance()," ", " ",
      "a", "tIII, 83 Bl.", "b", "graph. Darst."));

    Schema schema = new BaseSchema()
      .addField(new DataElement("245", "245").setRule(List.of(new Rule().withId("245").withMinCount(1).withHidden(true))))
      .addField(new DataElement("100", "100").setRule(List.of(new Rule().withId("100").withMinCount(1).withHidden(true))))
      .addField(new DataElement("300a", "300$a").setRule(List.of(new Rule().withId("300$a").withAnd(
                    List.of(new Rule().withDependencies(List.of("100")), new Rule().withMinCount(1))
      ))))
    ;
    RuleCatalog ruleCatalog = RuleCatalogUtils.create(schema);

    MarcSpecSelector selector = new MarcSpecSelector(marcRecord);
    assertEquals("880-01 iPhone the Bible wan jia sheng jing.", selector.extract("245").get(0));
    List<Object> values = RuleCatalogUtils.extract(ruleCatalog, ruleCatalog.measure(selector));
    assertEquals(List.of("300$a"), ruleCatalog.getHeader());
    assertEquals("0", CsvUtils.createCsvFromObjects(values).trim());
  }

  @Test
  public void both_independent_and_dependent_exist() {
    Marc21BibliographicRecord marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag245.getInstance(),"0", "0",
      "6", "880-01", "a", "iPhone the Bible wan jia sheng jing."));

    Schema schema = new BaseSchema()
      .addField(new DataElement("245", "245")
        .addRule(
          new Rule()
            .withId("245")
            .withMinCount(1)
            .withHidden(true)
        ))
      .addField(new DataElement("245$a", "245$a")
        .addRule(
          new Rule()
            .withId("245$a")
            .withAnd(
              List.of(
                new Rule().withDependencies(List.of("245")),
                new Rule().withMinCount(1)
              )
            )
        ))
      ;
    RuleCatalog ruleCatalog = RuleCatalogUtils.create(schema);

    MarcSpecSelector selector = new MarcSpecSelector(marcRecord);
    List<Object> values = RuleCatalogUtils.extract(ruleCatalog, ruleCatalog.measure(selector));
    assertEquals(List.of("245$a"), ruleCatalog.getHeader());
    assertEquals("1", CsvUtils.createCsvFromObjects(values).trim());
  }

  @Test
  public void independent_exists_and_dependent_does_not_exist() {
    Marc21BibliographicRecord marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag245.getInstance(),"0", "0",
      "6", "880-01", "a", "iPhone the Bible wan jia sheng jing."));

    Schema schema = new BaseSchema()
      .addField(new DataElement("245", "245")
        .addRule(
          new Rule()
            .withId("245")
            .withMinCount(1)
            .withHidden(true)
        ))
      .addField(new DataElement("245$b", "245$b")
        .addRule(
          new Rule()
            .withId("245$b")
            .withAnd(
              List.of(
                new Rule().withDependencies(List.of("245")),
                new Rule().withMinCount(1)
              )
            )
        ))
      ;
    RuleCatalog ruleCatalog = RuleCatalogUtils.create(schema);

    MarcSpecSelector selector = new MarcSpecSelector(marcRecord);
    List<Object> values = RuleCatalogUtils.extract(ruleCatalog, ruleCatalog.measure(selector));
    assertEquals(List.of("245$b"), ruleCatalog.getHeader());
    assertEquals("NA", CsvUtils.createCsvFromObjects(values).trim());
  }

  @Test
  public void testField_fail_if_depends_on_nonexisting_field3() {
    Marc21BibliographicRecord marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag245.getInstance(),"0", "0",
      "6", "880-01", "a", "iPhone the Bible wan jia sheng jing."));

    Schema schema = new BaseSchema()
      .addField(new DataElement("246", "246")
        .addRule(
          new Rule()
            .withId("246")
            .withMinCount(1)
            .withHidden(true)
            .withDebug(true)
        ))
      .addField(new DataElement("246$a", "246$a")
        .addRule(
          new Rule()
            .withId("246$a")
            .withAnd(
              List.of(
                new Rule().withDependencies(List.of("246")).withDebug(true),
                new Rule().withMinCount(1).withDebug(true)
              )
            )
            .withDebug(true)
        ))
      ;
    RuleCatalog ruleCatalog = RuleCatalogUtils.create(schema);

    MarcSpecSelector selector = new MarcSpecSelector(marcRecord);
    List<Object> values = RuleCatalogUtils.extract(ruleCatalog, ruleCatalog.measure(selector));
    assertEquals(List.of("246$a"), ruleCatalog.getHeader());
    assertEquals("NA", CsvUtils.createCsvFromObjects(values).trim());
  }
}