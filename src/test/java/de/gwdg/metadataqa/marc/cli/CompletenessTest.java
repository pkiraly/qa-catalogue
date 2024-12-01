package de.gwdg.metadataqa.marc.cli;

import com.opencsv.CSVReader;
import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.TestUtils;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import junit.framework.TestCase;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class CompletenessTest extends CliTestUtils {

  private String inputFile;
  private String outputDir;
  private List<String> outputFiles;
  private List<String> groupedOutputFiles;

  @Before
  public void setUp() throws Exception {
    inputFile = TestUtils.getPath("alephseq/alephseq-example3.txt");
    outputDir = TestUtils.getPath("output");
    outputFiles = Arrays.asList(
      "libraries.csv",
      "libraries003.csv",
      "marc-elements.csv",
      "packages.csv",
      "completeness.params.json"
    );
    groupedOutputFiles = Arrays.asList(
      "libraries.csv",
      "libraries003.csv",
      "completeness-groups.csv",
      "completeness-grouped-marc-elements.csv",
      "completeness-grouped-packages.csv",
      "completeness.params.json",
      "id-groupid.csv"
    );
  }

  @Test
  public void completeness_alephseq() throws Exception {
    clearOutput(outputDir, outputFiles);

    Completeness processor = new Completeness(new String[]{
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
      assertTrue(output.exists());
    }

    // Now compare the output files with the expected ones
    File expectedFile = new File(getTestResource("alephseq/expected-results/completeness/marc-elements.csv"));
    File actualFile = new File(outputDir, "marc-elements.csv");
    String expected = org.apache.commons.io.FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    String actual = org.apache.commons.io.FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);

    expectedFile = new File(getTestResource("alephseq/expected-results/completeness/packages.csv"));
    actualFile = new File(outputDir, "packages.csv");
    expected = org.apache.commons.io.FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    actual = org.apache.commons.io.FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void completeness_pica() throws Exception {
    clearOutput(outputDir, outputFiles);

    Completeness processor = new Completeness(new String[]{
      "--schemaType", "PICA",
      "--marcForma", "PICA_PLAIN",
      "--outputDir", outputDir,
      TestUtils.getPath("pica/k10plus-sample.pica")
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();

    for (String outputFile : outputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(output.exists());
    }

    // Now compare the output files with the expected ones
    File expectedFile = new File(getTestResource("pica/expected-results/completeness/marc-elements.csv"));
    File actualFile = new File(outputDir, "marc-elements.csv");
    String expected = org.apache.commons.io.FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    String actual = org.apache.commons.io.FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);

    expectedFile = new File(getTestResource("pica/expected-results/completeness/packages.csv"));
    actualFile = new File(outputDir, "packages.csv");
    expected = org.apache.commons.io.FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    actual = org.apache.commons.io.FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testRegex() {
    String a = "041$_ind1";
    assertEquals("041$ind1", a.replace("_ind", "ind"));

    String b = "041$|0";
    assertEquals("041$0", b.replaceAll("\\|(\\d)$", "$1"));
  }

  @Test
  public void completeness_pica_groupBy() throws Exception {
    clearOutput(outputDir, groupedOutputFiles);

    String inputFile = TestUtils.getPath("pica/pica-with-holdings-info.dat");
    assertTrue(new File(inputFile).exists());

    Completeness processor = new Completeness(new String[]{
      "--schemaType", "PICA",
      "--groupBy", "001@$0",
      "--marcFormat", "PICA_NORMALIZED",
      "--outputDir", outputDir,
      inputFile
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();

    for (String outputFile : groupedOutputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(outputFile + " should exist", output.exists());
      if (outputFile.equals("completeness-grouped-marc-elements.csv")) {
        CSVReader reader = new CSVReader(new FileReader(output));
        String[] record;
        int lineNr = 0;
        while ((record = reader.readNext()) != null) {
          if (lineNr == 0)
            assertEquals(
              "groupId,documenttype,path,sortkey,packageid,package,tag,subfield,number-of-record,number-of-instances,min,max,mean,stddev,histogram",
              StringUtils.join(record, ",")
            );
          else {
            int records = Integer.parseInt(record[8]);
            int occurrences = Integer.parseInt(record[9]);
            assertTrue(records <= occurrences);
            int total = 0;
            String histogram = record[14].replaceAll("^\"(.*)\"$", "$1");
            for (String expr : histogram.split("; ")) {
              String[] parts = expr.split("=");
              total += Integer.parseInt(parts[0]) * Integer.parseInt(parts[1]);
            }
            assertEquals(occurrences, total);
          }
          lineNr++;
        }
        reader.close();
      }
      output.delete();
      assertFalse(outputFile + " should not exist anymore", output.exists());
    }
  }

  @Test
  public void completeness_pica_groupBy_file() throws Exception {
    clearOutput(outputDir, groupedOutputFiles);

    Completeness processor = new Completeness(new String[]{
      "--schemaType", "PICA",
      "--groupBy", "001@$0",
      "--groupListFile", FileUtils.getPath("k10plus-libraries-by-unique-iln.txt").toAbsolutePath().toString(),
      "--marcFormat", "PICA_NORMALIZED",
      "--outputDir", outputDir,
      TestUtils.getPath("pica/pica-with-holdings-info.dat")
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();

    for (String outputFile : groupedOutputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(outputFile + " should exist", output.exists());
      if (outputFile.equals("completeness-grouped-marc-elements.csv")) {
        CSVReader reader = new CSVReader(new FileReader(output));
        String[] record;
        int lineNr = 0;
        while ((record = reader.readNext()) != null) {
          if (lineNr == 0)
            assertEquals(
              "groupId,documenttype,path,sortkey,packageid,package,tag,subfield,number-of-record,number-of-instances,min,max,mean,stddev,histogram",
              StringUtils.join(record, ",")
            );
          else {
            int records = Integer.parseInt(record[8]);
            int occurrences = Integer.parseInt(record[9]);
            assertTrue(records <= occurrences);
            int total = 0;
            String histogram = record[14].replaceAll("^\"(.*)\"$", "$1");
            for (String expr : histogram.split("; ")) {
              String[] parts = expr.split("=");
              total += Integer.parseInt(parts[0]) * Integer.parseInt(parts[1]);
            }
            assertEquals(occurrences, total);
          }
          lineNr++;
        }
        reader.close();

      } else if (outputFile.equals("completeness-groups.csv")) {
        output = new File(outputDir, outputFile);
        assertTrue(output.exists());
        String actual = Files.readString(output.toPath());
        TestCase.assertEquals(
          "id,group,count\n" +
            "0,all,10\n" +
            "100,\"Otto-von-Guericke-Universität, Universitätsbibliothek Magdeburg [DE-Ma9]\",1\n" +
            "1016,Regionalbibliothek Neubrandenburg [DE-198],1\n" +
            "104,Schleswig-Holsteinische Landesbibliothek (LB) Kiel [DE-68],1\n" +
            "11,SBB-PK Berlin [DE-1a],1\n" +
            "121,Landesbibliothek (LB) Oldenburg [DE-45],1\n" +
            "122,\"Jade Hochschule Wilhelmshaven/Oldenburg/Elsfleth, Studienort Oldenburg, Bibliothek [DE-897]\",1\n" +
            "15,15,1\n" +
            "154,\"Alfred-Wegener-Institut Helmholtz-Zentrum für Polar- und Meeresforschung, (AWI), Bibliothek Bremerhaven [DE-Bv2]\",1\n" +
            "161,HS Hannover [DE-960],1\n" +
            "165,HBK Braunschweig [DE-834],1\n" +
            "183,\"Behörde für Stadtentwicklung und Wohnen / Behörde für Umwelt und Energie - Bibliothek Stadtentwicklung, Umwelt und Geologie: Sondersammlung des Geologischen Landesamtes (GLA)  Hamburg [DE-H144...]\",1\n" +
            "20,Universitätsbibliothek Braunschweig [DE-84],2\n" +
            "2001,Universitätsbibliothek Tübingen [DE-21],1\n" +
            "2004,\"Universität Freiburg, Pathologisches Institut [DE-25-1...]\",1\n" +
            "2008,Württembergische Landesbibliothek [DE-24],1\n" +
            "2012,\"Universität Heidelberg, Archäologisches Institut [DE-16-31...]\",1\n" +
            "2035,Deutsche Universität für Verwaltungswissenschaften Speyer [DE-Sp3],1\n" +
            "2040,Institut für Auslandsbeziehungen Stuttgart [DE-212],1\n" +
            "21,Staats- und  Universitätsbibliothek Bremen [DE-46],3\n" +
            "22,\"Staats- und Universitätsbibliothek Carl von Ossietzky , Hamburg [DE-18]\",3\n" +
            "227,StB Braunschweig [DE-56],2\n" +
            "23,TUB Hamburg-Harburg [DE-830],1\n" +
            "24,UB Kiel [DE-8...],3\n" +
            "2488,\"Historisches Museum, Frankfurt/Main [DE-F207]\",1\n" +
            "2510,\"Hochschule für Gestaltung, Bibliothek [DE-2279]\",1\n" +
            "26,\"ZBW - Leibniz-Informationszentrum Wirtschaft, Standort Kiel [DE-206...]\",1\n" +
            "264,\"Jade Hochschule Wilhelmshaven/Oldenburg/Elsfleth, Studienort Elsfleth [DE-897-1]\",1\n" +
            "285,UB Potsdam [DE-517...],1\n" +
            "31,Thüringer Universitäts- und Landesbibliothek (ThULB) Jena [DE-27],2\n" +
            "39,\"Universitätsbibliothek Erfurt / Forschungsbibliothek Gotha, Universitätsbibliothek Erfurt [DE-547...]\",2\n" +
            "40,Niedersächsische Staats- und Universitätsbibliothek Göttingen und Universität Göttingen [DE-7],2\n" +
            "50,\"Herzog August Bibliothek (HAB) , Wolfenbüttel [DE-23]\",1\n" +
            "60,\"Helmut-Schmidt-Universität, Universität der Bundeswehr Hamburg, Universitätsbibliothek  (HSU), Hamburg [DE-705]\",1\n" +
            "608,\"Kuratorium für Forschung im Küsteningenieurwesen (KFKI), Hamburg [DE-H369]\",1\n" +
            "61,\"Herzogin Anna Amalia Bibliothek / Klassik Stiftung Weimar (HAAB), Weimer [DE-32]\",1\n" +
            "62,UB Rostock [DE-28...],3\n" +
            "640,\"Commerzbibliothek der Handelskammer, Hamburg [DE-205]\",1\n" +
            "65,\"Universitäts- und Landesbibliothek Sachsen-Anhalt / Zentralex, ULB Halle [DE-3]\",2\n" +
            "674,674,1\n" +
            "69,UB Greifswald [DE-9],1\n" +
            "70,Technische Informationsbibliothek (TIB) / Leibniz-Informationszentrum Technik und Naturwissenschaften und Universitätsbibliothek [DE-89],3\n" +
            "72,Gottfried Wilhelm Leibniz Bibliothek - Niedersächsische Landesbibliothek (GWLB) Hannover [DE-35],2\n" +
            "77,\"Nds. Landtag, Hannover [DE-Hv14]\",3\n" +
            "813,Diözesanbibliothek Münster [DE-Mue73],1\n" +
            "91,\"HAWK HS für angewandte Wissenschaft und Kunst Hildesheim/Holzminden/Göttingen, Bibliothek, Elektronische Ressourcen [DE-Hil3]\",1\n" +
            "92,\"Landesbibliothek Mecklenburg-Vorpommern (LBMV), Schwerin [DE-33]\",1\n",
          actual);

      } else if (outputFile.equals("completeness-grouped-packages.csv")) {
        output = new File(outputDir, outputFile);
        assertTrue(output.exists());
        List<String> lines = FileUtils.readLinesFromFile(output.toPath().toString());
        assertEquals("group,documenttype,packageid,name,label,iscoretag,count", lines.get(0));
        assertEquals("0,Druckschriften (einschließlich Bildbänden),50,0...,PICA+ bibliographic description,false,10", lines.get(1));
        assertEquals("0,Druckschriften (einschließlich Bildbänden),99,unknown,unknown origin,false,10", lines.get(2));
        assertEquals("0,all,50,0...,PICA+ bibliographic description,false,10", lines.get(3));
        assertEquals("0,all,99,unknown,unknown origin,false,10", lines.get(4));
        assertEquals("100,Druckschriften (einschließlich Bildbänden),50,0...,PICA+ bibliographic description,false,1", lines.get(5));
        assertEquals("100,Druckschriften (einschließlich Bildbänden),99,unknown,unknown origin,false,1", lines.get(6));

      } else if (outputFile.equals("id-groupid.csv")) {
        output = new File(outputDir, outputFile);
        assertTrue(output.exists());
        List<String> lines = FileUtils.readLinesFromFile(output.toPath().toString());
        assertEquals(76, lines.size());
        assertEquals("id,groupId", lines.get(0));
        assertEquals("010000011,0", lines.get(1));
        assertEquals("010000011,77", lines.get(2));
        assertEquals("010000011,2035", lines.get(3));
        assertEquals("010000011,70", lines.get(4));
        assertEquals("010000011,20", lines.get(5));

      } else if (outputFile.equals("completeness.params.json")) {
        output = new File(outputDir, outputFile);
        assertTrue(output.exists());
        String line = Files.readString(output.toPath());
        assertTrue(line.contains("{\"args\":[\""));
        assertTrue(line.contains("qa-catalogue/src/test/resources/pica/pica-with-holdings-info.dat\"],"));
        assertTrue(line.contains("\"marcVersion\":\"MARC21\","));
        assertTrue(line.contains("\"marcFormat\":\"PICA_NORMALIZED\","));
        assertTrue(line.contains("\"dataSource\":\"FILE\","));
        assertTrue(line.contains("\"limit\":-1,"));
        assertTrue(line.contains("\"offset\":-1,"));
        assertTrue(line.contains("\"id\":null,"));
        assertTrue(line.contains("\"defaultRecordType\":\"BOOKS\","));
        assertTrue(line.contains("\"alephseq\":false,"));
        assertTrue(line.contains("\"marcxml\":false,"));
        assertTrue(line.contains("\"lineSeparated\":false,"));
        assertTrue(line.contains("\"trimId\":false,"));
        assertTrue(line.contains("\"outputDir\":\"/"));
        assertTrue(line.contains("/qa-catalogue/src/test/resources/output\","));
        assertTrue(line.contains("\"recordFilter\":{\"criteria\":[],\"booleanCriteria\":null,\"empty\":true},"));
        assertTrue(line.contains("\"ignorableFields\":{\"fields\":null,\"empty\":true},"));
        assertTrue(line.contains("\"stream\":null,"));
        assertTrue(line.contains("\"defaultEncoding\":null,"));
        assertTrue(line.contains("\"alephseqLineType\":null,"));
        assertTrue(line.contains("\"picaIdField\":\"003@$0\","));
        assertTrue(line.contains("\"picaSubfieldSeparator\":\"$\","));
        assertTrue(line.contains("\"picaSchemaFile\":null,"));
        assertTrue(line.contains("\"picaRecordTypeField\":\"002@$0\","));
        assertTrue(line.contains("\"schemaType\":\"PICA\","));
        assertTrue(line.contains("\"groupBy\":\"001@$0\","));
        assertTrue(line.contains("\"groupListFile\":\"/"));
        assertTrue(line.contains("qa-catalogue/target/test-classes/k10plus-libraries-by-unique-iln.txt\","));
        assertTrue(line.contains("\"format\":\"COMMA_SEPARATED\","));
        assertTrue(line.contains("\"advanced\":false,"));
        assertTrue(line.contains("\"onlyPackages\":false,"));
        assertTrue(line.contains("\"pica\":true,"));
        assertTrue(line.contains("\"replacementInControlFields\":null,"));
        assertTrue(line.contains("\"marc21\":false,"));
        assertTrue(line.contains("\"mqaf.version\":\"0.9.5\","));
        assertTrue(line.contains("\"qa-catalogue.version\":\"0.8.0-SNAPSHOT\""));
        assertTrue(line.contains("\"duration\":\"00:00:00\""));
        assertTrue(line.contains("\"numberOfprocessedRecords\":10"));

      } else if (outputFile.equals("libraries.csv")) {
        output = new File(outputDir, outputFile);
        assertTrue(output.exists());
        String actual = Files.readString(output.toPath());
        assertEquals("library,count\n", actual);

      } else if (outputFile.equals("libraries003.csv")) {
        output = new File(outputDir, outputFile);
        assertTrue(output.exists());
        String actual = Files.readString(output.toPath());
        assertEquals("library,count\n", actual);

      } else {
        fail("Untested file: " + outputFile);
      }
      output.delete();
      assertFalse(outputFile + " should not exist anymore", output.exists());
    }
  }

  @Test
  public void completeness_whenUnimarc() throws Exception {
    clearOutput(outputDir, outputFiles);

    Completeness processor = new Completeness(new String[]{
        "--schemaType", "UNIMARC",
        "--marcFormat", "MARC_LINE",
        "--outputDir", outputDir,
        TestUtils.getPath("unimarc/unimarc.mrctxt")
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();

    for (String outputFile : outputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(output.exists());
    }

    // Now compare the output files with the expected ones
    File expectedFile = new File(getTestResource("unimarc/expected-results/completeness/marc-elements.csv"));
    File actualFile = new File(outputDir, "marc-elements.csv");
    String expected = org.apache.commons.io.FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    String actual = org.apache.commons.io.FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);

    expectedFile = new File(getTestResource("unimarc/expected-results/completeness/packages.csv"));
    actualFile = new File(outputDir, "packages.csv");
    expected = org.apache.commons.io.FileUtils.readFileToString(expectedFile, "UTF-8").replaceAll("\r\n", "\n");
    actual = org.apache.commons.io.FileUtils.readFileToString(actualFile, "UTF-8").replaceAll("\r\n", "\n");

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void completeness_picaplain() throws Exception {
    clearOutput(outputDir, outputFiles);

    Completeness processor = new Completeness(new String[]{
      "--schemaType", "PICA",
      "--marcFormat", "PICA_PLAIN",
      "--outputDir", outputDir,
      TestUtils.getPath("pica/pica-plain.pp")
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();

    for (String outputFile : outputFiles) {
      // System.err.println(outputFile);
      File output = new File(outputDir, outputFile);
      assertTrue(output.exists());
      String expected = org.apache.commons.io.FileUtils.readFileToString(output, "UTF-8");
      assertTrue(StringUtils.isNotBlank(expected));
    }
  }
}