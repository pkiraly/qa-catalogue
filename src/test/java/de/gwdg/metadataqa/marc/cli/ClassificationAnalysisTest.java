package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.TestUtils;
import de.gwdg.metadataqa.marc.analysis.contextual.classification.ClassificationStatistics;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.utils.ReadMarc;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.marc4j.marc.Record;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class ClassificationAnalysisTest extends CliTestUtils {

  private String inputFile;
  private String outputDir;
  private List<String> outputFiles;

  @Before
  public void setUp() throws Exception {
    inputFile = TestUtils.getPath("alephseq/alephseq-example3.txt");
    outputDir = TestUtils.getPath("output");
    outputFiles = Arrays.asList(
      "classifications-by-records.csv",
      "classifications-by-schema.csv",
      "classifications-by-schema-subfields.csv",
      "classifications-collocations.csv",
      "classifications-histogram.csv",
      "classifications-frequency-examples.csv"
    );
  }

  @Test
  public void test() throws Exception {
    String path = getTestResource("general/0001-01.mrc");

    Record marc4jRecord = ReadMarc.read(path).get(0);
    assertNotNull(marc4jRecord);
    ClassificationAnalysis analysis = new ClassificationAnalysis(new String[]{});
    BibliographicRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord);
    assertNotNull(marcRecord);
    analysis.processRecord(marcRecord, 1);
    ClassificationStatistics statistics = analysis.getStatistics();
    assertNotNull(statistics);
    assertEquals(1, statistics.getInstances().size());
    assertEquals(1, statistics.getRecords().size());
    assertEquals(1, statistics.getSubfields().size());
  }

  @Test
  public void testFullProcess() throws Exception {
    clearOutput(outputDir, outputFiles);

    ClassificationAnalysis processor = new ClassificationAnalysis(new String[]{
      "--defaultRecordType", "BOOKS",
      "--marcVersion", "GENT",
      "--alephseq",
      "--outputDir", outputDir,
      inputFile
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();

    for (String outputFile : outputFiles) {
      File output = new File(outputDir, outputFile);
      if (outputFile.equals("classifications-collocations.csv"))
        assertFalse(outputFile, output.exists());
      else
        assertTrue(outputFile, output.exists());
      output.delete();
    }
  }

  @Test
  public void alephseq() throws IOException {
    clearOutput(outputDir, outputFiles);

    var args = new String[]{
      "--defaultRecordType", "BOOKS",
      "--marcVersion", "GENT",
      "--alephseq",
      "--outputDir", outputDir,
      inputFile
    };
    ClassificationAnalysis.main(args);

    File output = new File(outputDir, "classifications-by-records.csv");
    assertTrue(output.exists());
    String actual = Files.readString(output.toPath());
    assertEquals(
      "records-with-classification,count\n" +
        "true,1\n",
      actual);

    output = new File(outputDir, "classifications-by-schema.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    assertEquals(
      "id,field,location,scheme,abbreviation,abbreviation4solr,recordcount,instancecount,type\n" +
      "3,082,$a,\"Dewey Decimal Classification\",\"ddc\",ddc,1,1,CLASSIFICATION_SCHEME\n" +
      "1,650,$2,\"Library of Congress subject headings (Washington, DC: LC, Cataloging Distribution Service)\",\"lcsh\",lcsh,1,4,SUBJECT_HEADING\n" +
      "2,650,$2,\"Faceted application of subject terminology (Dublin, Ohio: OCLC)\",\"fast\",fast,1,1,SUBJECT_HEADING\n",
      actual);

    output = new File(outputDir, "classifications-by-schema-subfields.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    assertEquals(
      "id,subfields,count\n" +
      "3,a,1\n" +
      "1,a;2,4\n" +
      "2,a;0;2,1\n", actual);

    /*
    output = new File(outputDir, "classifications-collocations.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    assertEquals("abbreviations,recordcount,percent\n" +
      "ddc;fast;lcsh,1,100.00%\n", actual);
     */

    output = new File(outputDir, "classifications-histogram.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    assertEquals(
      "count,frequency\n" +
      "6,1\n", actual);

    output = new File(outputDir, "classifications-frequency-examples.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    assertEquals(
      "count,id\n" +
        "6,000000002\n", actual);

    clearOutput(outputDir, outputFiles);
  }

  @Test
  public void marcxml() throws IOException {
    clearOutput(outputDir, outputFiles);
    inputFile = TestUtils.getPath("marcxml/marcxml.xml");

    var args = new String[]{
      "--defaultRecordType", "BOOKS",
      "--marcVersion", "GENT",
      "--collectCollocations",
      "--marcxml",
      "--outputDir", outputDir,
      inputFile
    };
    ClassificationAnalysis.main(args);

    File output = new File(outputDir, "classifications-by-records.csv");
    assertTrue(output.exists());
    String actual = Files.readString(output.toPath());
    assertEquals(
      "records-with-classification,count\n" +
        "false,1\n",
      actual);

    output = new File(outputDir, "classifications-by-schema.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    assertEquals(
      "id,field,location,scheme,abbreviation,abbreviation4solr,recordcount,instancecount,type\n",
      actual);

    output = new File(outputDir, "classifications-by-schema-subfields.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    assertEquals("id,subfields,count\n", actual);

    output = new File(outputDir, "classifications-collocations.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    assertEquals("abbreviations,recordcount,percent\n", actual);

    output = new File(outputDir, "classifications-histogram.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    assertEquals(
      "count,frequency\n" +
        "0,1\n", actual);

    output = new File(outputDir, "classifications-frequency-examples.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    assertEquals(
      "count,id\n" +
        "0,987874829\n", actual);

    clearOutput(outputDir, outputFiles);
  }

  @Test
  public void pica() throws IOException {
    clearOutput(outputDir, outputFiles);

    var args = new String[]{
      "--schemaType", "PICA",
      "--marcForma", "PICA_PLAIN",
      "--collectCollocations",
      "--outputDir", outputDir,
      TestUtils.getPath("pica/k10plus-sample.pica")
    };
    ClassificationAnalysis.main(args);

    File output = new File(outputDir, "classifications-by-records.csv");
    assertTrue(output.exists());
    String actual = Files.readString(output.toPath());
    assertEquals(
      "records-with-classification,count\n" +
      "true,6\n",
      actual);

    output = new File(outputDir, "classifications-by-schema.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    /*
    assertEquals(
      "id,field,location,scheme,abbreviation,abbreviation4solr,recordcount,instancecount,type\n" +
        "4,045A,$a,\"Library of Congress Classification\",\"lcc\",lcc,1,1,CLASSIFICATION_SCHEME\n" +
        "1,045E,$a,\"This mixes multiple systems used in DNB before 2004\",\"dnbsgr\",dnbsgr,2,2,UNKNOWN\n" +
        "3,045F,$a,\"Dewey-Dezimalklassifikation\",\"ddc\",ddc,1,1,CLASSIFICATION_SCHEME\n" +
        "2,045R,$a,\"Regensburger Verbundklassifikation\",\"rvk\",rvk,1,1,CLASSIFICATION_SCHEME\n",
      actual);
     */
    assertEquals(
      "id,field,location,scheme,abbreviation,abbreviation4solr,recordcount,instancecount,type\n" +
        "5,044A,$a,\"LoC Subject Headings\",\"lcsh0\",lcsh0,1,2,SUBJECT_HEADING\n" +
        "6,045A,$a,\"LCC-Notation\",\"lcc\",lcc,1,1,CLASSIFICATION_SCHEME\n" +
        "2,045E,$a,\"Sachgruppen der Deutschen Nationalbibliografie bis 2003\",\"sdnb\",sdnb,2,2,UNKNOWN\n" +
        "7,045F,$a,\"DDC-Notation\",\"ddc\",ddc,1,1,CLASSIFICATION_SCHEME\n" +
        "1,045Q/01,$a,\"Basisklassifikation\",\"basis\",basis,5,5,UNKNOWN\n" +
        "3,045R,$a,\"Regensburger Verbundklassifikation (RVK)\",\"rvk\",rvk,1,1,CLASSIFICATION_SCHEME\n" +
        "4,045V,$a,\"SSG-Nummer/FID-Kennzeichen\",\"ssg-nr\",ssg_nr,1,1,UNKNOWN\n",
      actual);

    output = new File(outputDir, "classifications-by-schema-subfields.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    /*
    assertEquals(
      "id,subfields,count\n" +
        "4,a,1\n" +
        "1,a+,2\n" +
        "3,a+,1\n" +
        "2,V;a;j;k+;3;7;9,1\n", actual);
     */

    assertEquals(
      "id,subfields,count\n" +
        "5,a,1\n" +
        "5,a+,1\n" +
        "6,a,1\n" +
        "2,a+,2\n" +
        "7,a+,1\n" +
        "1,V;a;j;9,5\n" +
        "3,V;a;j;k+;3;7;9,1\n" +
        "4,a,1\n", actual);

    output = new File(outputDir, "classifications-collocations.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    /*
    assertEquals("abbreviations,recordcount,percent\n" +
      "dnbsgr;rvk,1,50.00%\n" +
      "ddc;dnbsgr;lcc,1,50.00%\n", actual);
     */
    assertEquals("abbreviations,recordcount,percent\n" +
      "basis,4,66.67%\n" +
      "rvk;sdnb;ssg-nr,1,16.67%\n" +
      "basis;ddc;lcc;lcsh0;sdnb,1,16.67%\n", actual);

    output = new File(outputDir, "classifications-histogram.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    /*
    assertEquals(
      "count,frequency\n" +
        "0,4\n" +
        "2,1\n" +
        "3,1\n", actual);
     */
    assertEquals(
     "count,frequency\n" +
       "1,4\n" +
       "3,1\n" +
       "6,1\n", actual);

    output = new File(outputDir, "classifications-frequency-examples.csv");
    assertTrue(output.exists());
    actual = Files.readString(output.toPath());
    /*
    assertEquals(
      "count,id\n" +
        "0,010000011\n" +
        "2,010000054\n" +
        "3,010000070\n", actual);
    */
    assertEquals(
      "count,id\n" +
        "1,010000011\n" +
        "3,010000054\n" +
        "6,010000070\n", actual);

    clearOutput(outputDir, outputFiles);
  }

  @Test
  public void classificationAnalysis_whenUnimarc_runsSuccessfully() throws IOException {
    clearOutput(outputDir, outputFiles);

    String[] args = {
      "--schemaType", "UNIMARC",
      "--marcFormat", "MARC_LINE",
      "--outputDir", outputDir,
      TestUtils.getPath("unimarc/unimarc.mrctxt")
    };
    ClassificationAnalysis.main(args);

    File expectedFile = new File(getTestResource("unimarc/expected-results/classification/classifications-by-records.csv"));
    File actualFile = new File(outputDir, "classifications-by-records.csv");
    String expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    String actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    assertEquals(expected, actual);

    expectedFile = new File(getTestResource("unimarc/expected-results/classification/classifications-by-schema.csv"));
    actualFile = new File(outputDir, "classifications-by-schema.csv");
    expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    assertEquals(expected, actual);

    expectedFile = new File(getTestResource("unimarc/expected-results/classification/classifications-by-schema-subfields.csv"));
    actualFile = new File(outputDir, "classifications-by-schema-subfields.csv");
    expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    assertEquals(expected, actual);

    expectedFile = new File(getTestResource("unimarc/expected-results/classification/classifications-frequency-examples.csv"));
    actualFile = new File(outputDir, "classifications-frequency-examples.csv");
    expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    assertEquals(expected, actual);

    expectedFile = new File(getTestResource("unimarc/expected-results/classification/classifications-histogram.csv"));
    actualFile = new File(outputDir, "classifications-histogram.csv");
    expected = FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    actual = FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    assertEquals(expected, actual);

    clearOutput(outputDir, outputFiles);
  }
}
