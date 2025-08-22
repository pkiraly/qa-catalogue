package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.TestUtils;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.utils.QAMarcReaderFactory;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaManager;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaReader;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaSpec;
import org.junit.Before;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FormatterTest extends CliTestUtils {

  private String inputFile;
  private String outputDir;
  private List<String> outputFiles;

  @Before
  public void setUp() throws Exception {
    inputFile = TestUtils.getPath("alephseq/alephseq-example3.txt");
    outputDir = TestUtils.getPath("output");
    outputFiles = List.of("marc-history.csv");
    for (String outputFile : outputFiles) {
      File file = new File(outputDir, "marc-history.csv");
      if (file.exists())
        file.delete();
    }
  }

  @Test
  public void pica() throws Exception {
    PicaSchemaManager schema = PicaSchemaReader.createSchema(CliTestUtils.getTestResource("pica/schema/k10plus.json"));
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.PICA_PLAIN, CliTestUtils.getTestResource("pica/k10plus-sample.pica"), null);
    reader.hasNext();
    Record record = reader.next();
    BibliographicRecord marcRecord = MarcFactory.createPicaFromMarc4j(record, schema);
    PicaSpec spec = new PicaSpec("001A$0");
    List<String> results = marcRecord.select(spec);
    assertEquals(1, results.size());
    assertEquals("2000:06-11-86", results.get(0));
    assertEquals("861106", Formatter.extractPicaDate(results.get(0)));

    assertEquals("861106", Formatter.extractPicaDate("2000:06-11-86"));
  }

  @Test
  public void formatter_pica() throws Exception {
    File file = new File(outputDir, "marc-history.csv");
    assertFalse(file.exists());

    Formatter processor = new Formatter(new String[]{
      "--schemaType", "PICA",
      "--marcForma",  "PICA_PLAIN",
      "--outputDir",  outputDir,
      "--selector",   "001A$0|extractPicaDate;011@$a",
      "--separator",  ",",
      "--fileName",   "marc-history.csv",
      TestUtils.getPath("pica/k10plus-sample.pica")
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();

    assertTrue(file.exists());
    List<String> content = FileUtils.readLinesFromFile(file.getCanonicalPath());
    assertEquals(List.of("001A$0,011@$a", "861106,1985", "861106,1986", "861106,1986", "861106,1981", "861106,1982", "861106,1981"), content);
    file.delete();
    assertFalse(file.exists());
  }

  @Test
  public void unimarc_whenExtractingDataElements_runsSuccessfully() throws Exception {
    File file = new File(outputDir, "unimarc-results.csv");
    assertFalse(file.exists());

    Formatter processor = new Formatter(new String[]{
      "--schemaType", "UNIMARC",
      "--outputDir",  outputDir,
      "--selector",   "010$a;101$a",
      "--separator",  ",",
      "--fileName",   "unimarc-results.csv",
      TestUtils.getPath("unimarc/short.bnr.1993.mrc")
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();

    assertTrue(file.exists());
    List<String> results = FileUtils.readLinesFromFile(file.getCanonicalPath());
    List<String> expectedResults = List.of("010$a,101$a", "975-19-0787-X,tur", "0-395-67346-1,eng",
      "973-95777-1-7,rum", "973-95795-6-6,ger", "2-203-60504-9,fre", "973-95988-2-X,rum", "4-87893-180-9,jpn",
      ",eng", "973-95056-3-5,rum", "2-501-01782-X,fre");
    assertEquals(expectedResults, results);
    file.delete();
    assertFalse(file.exists());
  }
}