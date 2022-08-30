package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.utils.QAMarcReaderFactory;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaManager;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaReader;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPath;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPathParser;
import org.junit.Before;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FormatterTest extends CliTestUtils {

  private String inputFile;
  private String outputDir;
  private List<String> outputFiles;

  @Before
  public void setUp() throws Exception {
    inputFile = getPath("src/test/resources/alephseq/alephseq-example3.txt");
    outputDir = getPath("src/test/resources/output");
    /*
    outputFiles = Arrays.asList(
      "libraries.csv",
      "libraries003.csv",
      "marc-elements.csv",
      "packages.csv"
    );
     */
  }

  @Test
  public void pica() throws Exception {
    PicaSchemaManager schema = PicaSchemaReader.createSchema(CliTestUtils.getTestResource("pica/k10plus.json"));
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.PICA_PLAIN, CliTestUtils.getTestResource("pica/k10plus-sample.pica"), null);
    reader.hasNext();
    Record record = reader.next();
    MarcRecord marcRecord = MarcFactory.createPicaFromMarc4j(record, schema);
    PicaPath path = PicaPathParser.parse("001A$0");
    List<String> results = marcRecord.select(path);
    assertEquals(1, results.size());
    assertEquals("2000:06-11-86", results.get(0));
    assertEquals("861106", Formatter.extractPicaDate(results.get(0)));

    assertEquals("861106", Formatter.extractPicaDate("2000:06-11-86"));
  }

  @Test
  public void formatter_pica() throws Exception {

    Formatter processor = new Formatter(new String[]{
      "--schemaType", "PICA",
      "--marcForma",  "PICA_PLAIN",
      "--outputDir",  outputDir,
      "--selector",   "001A$0|extractPicaDate;011@$a",
      "--separator",  ",",
      "--fileName",   "marc-history.csv",
      getPath("src/test/resources/pica/k10plus-sample.pica")
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();

  }

}