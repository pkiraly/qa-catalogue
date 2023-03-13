package de.gwdg.metadataqa.marc.cli;

import com.opencsv.CSVReader;
import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static de.gwdg.metadataqa.api.util.FileUtils.getPath;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class CompletenessTest extends CliTestUtils {

  private String inputFile;
  private String outputDir;
  private List<String> outputFiles;
  private List<String> grouppedOutputFiles;

  @Before
  public void setUp() throws Exception {
    inputFile = getPath("src/test/resources/alephseq/alephseq-example3.txt");
    outputDir = getPath("src/test/resources/output");
    outputFiles = Arrays.asList(
      "libraries.csv",
      "libraries003.csv",
      "marc-elements.csv",
      "packages.csv",
      "completeness.params.json"
    );
    grouppedOutputFiles = Arrays.asList(
      "libraries.csv",
      "libraries003.csv",
      "completeness-groups.csv",
      "completeness-groupped-marc-elements.csv",
      "completeness-groupped-packages.csv",
      "completeness.params.json"
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
      output.delete();
      assertFalse(output.exists());
    }
  }

  @Test
  public void completeness_pica() throws Exception {
    clearOutput(outputDir, outputFiles);

    Completeness processor = new Completeness(new String[]{
      "--schemaType", "PICA",
      "--marcForma", "PICA_PLAIN",
      "--outputDir", outputDir,
      getPath("src/test/resources/pica/k10plus-sample.pica")
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();

    for (String outputFile : outputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(output.exists());
      output.delete();
      assertFalse(output.exists());
    }
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
    clearOutput(outputDir, grouppedOutputFiles);

    Completeness processor = new Completeness(new String[]{
      "--schemaType", "PICA",
      "--groupBy", "001@$0",
      "--marcFormat", "PICA_NORMALIZED",
      "--outputDir", outputDir,
      getPath("src/test/resources/pica/pica-with-holdings-info.dat")
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();

    for (String outputFile : grouppedOutputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(outputFile + " should exist", output.exists());
      if (outputFile.equals("completeness-groupped-marc-elements.csv")) {
        CSVReader reader = new CSVReader(new FileReader(output));
        String[] record = null;
        int lineNr = 0;
        while ((record = reader.readNext()) != null) {
          if (lineNr > 0) {
            int records = Integer.parseInt(record[7]);
            int occurrences = Integer.parseInt(record[8]);
            assertTrue(records <= occurrences);
            int total = 0;
            String histogram = record[13].replaceAll("^\"(.*)\"$", "$1");
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
    clearOutput(outputDir, grouppedOutputFiles);

    Completeness processor = new Completeness(new String[]{
      "--schemaType", "PICA",
      "--groupBy", "001@$0",
      "--groupListFile", FileUtils.getPath("kxp-uniq-library-names.tsv").toAbsolutePath().toString(),
      "--marcFormat", "PICA_NORMALIZED",
      "--outputDir", outputDir,
      getPath("src/test/resources/pica/pica-with-holdings-info.dat")
    });
    RecordIterator iterator = new RecordIterator(processor);
    iterator.start();

    for (String outputFile : grouppedOutputFiles) {
      File output = new File(outputDir, outputFile);
      assertTrue(outputFile + " should exist", output.exists());
      if (outputFile.equals("completeness-groupped-marc-elements.csv")) {
        CSVReader reader = new CSVReader(new FileReader(output));
        String[] record = null;
        int lineNr = 0;
        while ((record = reader.readNext()) != null) {
          if (lineNr > 0) {
            int records = Integer.parseInt(record[7]);
            int occurrences = Integer.parseInt(record[8]);
            assertTrue(records <= occurrences);
            int total = 0;
            String histogram = record[13].replaceAll("^\"(.*)\"$", "$1");
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
            "100,\"Otto-von-Guericke-Universität, Universitätsbibliothek Magdeburg\",1\n" +
            "1016,Regionalbibliothek Neubrandenburg,1\n" +
            "104,Schleswig-Holsteinische Landesbibliothek (LB) Kiel,1\n" +
            "11,SBB-PK Berlin,1\n" +
            "121,Landesbibliothek (LB) Oldenburg,1\n" +
            "122,\"Jade Hochschule Wilhelmshaven/Oldenburg/Elsfleth, Studienort Oldenburg, Bibliothek \",1\n" +
            "15,15,1\n" +
            "154,\"Alfred-Wegener-Institut Helmholtz-Zentrum für Polar- und Meeresforschung, (AWI), Bibliothek Bremerhaven\",1\n" +
            "161,HS Hannover,1\n" +
            "165,HBK Braunschweig,1\n" +
            "183,\"Behörde für Stadtentwicklung und Wohnen / Behörde für Umwelt und Energie - Bibliothek Stadtentwicklung, Umwelt und Geologie: Sondersammlung des Geologischen Landesamtes (GLA)  Hamburg\",1\n" +
            "20,Universitätsbibliothek Braunschweig,2\n" +
            "2001,Universitätsbibliothek Tübingen,1\n" +
            "2004,\"Universität Freiburg, Pathologisches Institut\",1\n" +
            "2008,Württembergische Landesbibliothek,1\n" +
            "2012,\"Universität Heidelberg, Archäologisches Institut\",1\n" +
            "2035,Deutsche Universität für Verwaltungswissenschaften Speyer,1\n" +
            "2040,Institut für Auslandsbeziehungen Stuttgart,1\n" +
            "21,Staats- und  Universitätsbibliothek Bremen,3\n" +
            "22,\"Staats- und Universitätsbibliothek Carl von Ossietzky , Hamburg\",3\n" +
            "227,StB Braunschweig,2\n" +
            "23,TUB Hamburg-Harburg,1\n" +
            "24,UB Kiel,3\n" +
            "2488,\"Historisches Museum, Frankfurt/Main\",1\n" +
            "2510,\"Hochschule für Gestaltung, Bibliothek\",1\n" +
            "26,\"ZBW - Leibniz-Informationszentrum Wirtschaft, Standort Kiel \",1\n" +
            "264,\"Jade Hochschule Wilhelmshaven/Oldenburg/Elsfleth, Studienort Elsfleth\",1\n" +
            "285,UB Potsdam,1\n" +
            "31,Thüringer Universitäts- und Landesbibliothek (ThULB) Jena,2\n" +
            "39,\"Universitätsbibliothek Erfurt / Forschungsbibliothek Gotha, Universitätsbibliothek Erfurt \",2\n" +
            "40,Niedersächsische Staats- und Universitätsbibliothek Göttingen und Universität Göttingen,2\n" +
            "50,\"Herzog August Bibliothek (HAB) , Wolfenbüttel\",1\n" +
            "60,\"Helmut-Schmidt-Universität, Universität der Bundeswehr Hamburg, Universitätsbibliothek  (HSU), Hamburg\",1\n" +
            "608,\"Kuratorium für Forschung im Küsteningenieurwesen (KFKI), Hamburg\",1\n" +
            "61,\"Herzogin Anna Amalia Bibliothek / Klassik Stiftung Weimar (HAAB), Weimer\",1\n" +
            "62,UB Rostock,3\n" +
            "640,\"Commerzbibliothek der Handelskammer, Hamburg\",1\n" +
            "65,\"Universitäts- und Landesbibliothek Sachsen-Anhalt / Zentralex, ULB Halle\",2\n" +
            "674,674,1\n" +
            "69,UB Greifswald,1\n" +
            "70,Technische Informationsbibliothek (TIB) / Leibniz-Informationszentrum Technik und Naturwissenschaften und Universitätsbibliothek ,3\n" +
            "72,Gottfried Wilhelm Leibniz Bibliothek - Niedersächsische Landesbibliothek (GWLB) Hannover,2\n" +
            "77,\"Nds. Landtag, Hannover\",3\n" +
            "813,Diözesanbibliothek Münster,1\n" +
            "91,\"HAWK HS für angewandte Wissenschaft und Kunst Hildesheim/Holzminden/Göttingen, Bibliothek, Elektronische Ressourcen \",1\n" +
            "92,\"Landesbibliothek Mecklenburg-Vorpommern (LBMV), Schwerin\",1\n" +
            "all,all,10\n",
          actual);

      }
      output.delete();
      assertFalse(outputFile + " should not exist anymore", output.exists());
    }
  }
}