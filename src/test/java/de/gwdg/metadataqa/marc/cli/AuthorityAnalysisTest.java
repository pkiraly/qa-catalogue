package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.analysis.AuthorithyAnalyzer;
import de.gwdg.metadataqa.marc.analysis.AuthorityStatistics;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.utils.QAMarcReaderFactory;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaManager;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaReader;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class AuthorityAnalysisTest extends CliTestUtils {

  private String outputDir;
  private List<String> outputFiles;

  @Before
  public void setUp() throws Exception {
    // inputFile = getPath("src/test/resources/alephseq/alephseq-example3.txt");
    outputDir = getPath("src/test/resources/output");
    outputFiles = Arrays.asList(
      "authorities-by-categories.csv",
      "authorities-by-records.csv",
      "authorities-by-schema.csv",
      "authorities-by-schema-subfields.csv",
      "authorities-frequency-examples.csv",
      "authorities-histogram.csv"
    );
  }

  @Test
  public void pica() throws Exception {
    PicaSchemaManager schema = PicaSchemaReader.createSchema(CliTestUtils.getTestResource("pica/schema/k10plus.json"));
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.PICA_PLAIN, CliTestUtils.getTestResource("pica/k10plus-sample.pica"), null);
    reader.hasNext();
    Record record = reader.next();
    BibliographicRecord marcRecord = MarcFactory.createPicaFromMarc4j(record, schema);
    assertEquals(SchemaType.PICA, marcRecord.getSchemaType());
    List<DataField> fields = marcRecord.getAuthorityFields();
    assertEquals(1, fields.size());
    assertEquals("029F", fields.get(0).getTag());
    assertEquals("Institut für Gewerbliche Wasserwirtschaft und Luftreinhaltung", fields.get(0).getSubfield("A").get(0).getValue());
    for (MarcSubfield subfield : fields.get(0).getSubfields()) {
      System.err.println(subfield);
    }
    AuthorityStatistics statistics = new AuthorityStatistics();
    var analyzer = new AuthorithyAnalyzer(marcRecord, statistics);
    int count = analyzer.process();
    System.err.println(count);
    System.err.println(statistics.getSubfields());

    // T - Feldzuordnung - field mapping
    // U - Schriftcode - writing code
    // L - Sprachencode - language code
    // 9 - PPN - PPN
    // V - undef
    // 7 - Vorläufiger Link - Tentative link
    // 3 - undef
    // A - a - Bevorzugter Name (nur in Importdaten) - preferred name
    // b - Untergeordnete Einheit - Untergeordnete Einheit
    // n - Zählung - count
  }

  @Test
  public void picaCli() throws IOException {
    clearOutput(outputDir, outputFiles);

    var args = new String[]{
      "--schemaType", "PICA",
      "--marcForma", "PICA_PLAIN",
      "--outputDir", outputDir,
      getPath("src/test/resources/pica/k10plus-sample.pica")
    };
    AuthorityAnalysis.main(args);

    File output = new File(outputDir, "authorities-by-categories.csv");
    assertTrue(output.exists());
    String actual = Files.readString(output.toPath());
    TestCase.assertEquals(
      "category,recordcount,instancecount\n" +
        "\"Personal names\",3,4\n" +
        "\"Corporate names\",3,3\n" +
        "\"Titles\",1,1\n",
      actual);

    output = new File(outputDir, "authorities-by-records.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    TestCase.assertEquals(
      "records-with-authorities,count\n" +
        "true,6\n",
      actual);

    output = new File(outputDir, "authorities-by-schema.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    assertTrue(actual.contains("id,field,location,scheme,abbreviation,abbreviation4solr,recordcount,instancecount\n"));
    assertTrue(actual.contains("022A/01,$7,\"undetectable\",undetectable,undetectable,1,1\n"));
    assertTrue(actual.contains("028A,$7,\"Gemeinsame Normdatei (Leipzig, Frankfurt: Deutsche Nationalbibliothek)\",gnd,gnd,2,2\n"));
    assertTrue(actual.contains("028A,$7,\"undetectable\",undetectable,undetectable,1,1\n"));
    assertTrue(actual.contains("028C,$7,\"Gemeinsame Normdatei (Leipzig, Frankfurt: Deutsche Nationalbibliothek)\",gnd,gnd,1,1\n"));
    assertTrue(actual.contains("029F,$7,\"Gemeinsame Normdatei (Leipzig, Frankfurt: Deutsche Nationalbibliothek)\",gnd,gnd,3,3\n"));

    output = new File(outputDir, "authorities-by-schema-subfields.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    assertTrue(actual.contains("id,subfields,count\n"));
    assertTrue(actual.contains(",a,1\n"));
    assertTrue(actual.contains(",B;a;d;4,1\n"));
    assertTrue(actual.contains(",A;B;D;E;M;V;w;3;4;7;9,2\n"));
    assertTrue(actual.contains(",A;D;E;V;w;3;7;9,1\n"));
    assertTrue(actual.contains(",A;F+;V;w;3;7;9,1\n"));
    assertTrue(actual.contains(",A;F;G;V;w;3;7;9,1\n"));
    assertTrue(actual.contains(",A;V;3;7;9,1\n"));

    output = new File(outputDir, "authorities-histogram.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    TestCase.assertEquals(
      "count,frequency\n" +
        "1,4\n" +
        "2,2\n",
      actual
    );

    output = new File(outputDir, "authorities-frequency-examples.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    TestCase.assertEquals(
      "count,id\n" +
        "1,010000011\n" +
        "2,010000054\n",
      actual
    );

    clearOutput(outputDir, outputFiles);
  }

  protected static void clearOutput(String outputDir, List<String> outputFiles) {
    for (String outputFile : outputFiles)
      deleteFile(new File(outputDir, outputFile));

    // deleteFile(new File(outputDir));
  }

  private static void deleteFile(File file) {
    if (file.exists())
      if (!file.delete())
        System.err.format("File/directory %s hasn't been deleted!\n", file);
  }
}