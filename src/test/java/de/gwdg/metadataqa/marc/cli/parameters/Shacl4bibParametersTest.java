package de.gwdg.metadataqa.marc.cli.parameters;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static org.junit.Assert.*;

public class Shacl4bibParametersTest {


  @Test
  public void getShaclConfigurationFile() {
    String[] arguments = new String[]{"--shaclConfigurationFile", "shacl.cnf"};
    try {
      Shacl4bibParameters parameters = new Shacl4bibParameters(arguments);
      assertEquals("shacl.cnf", parameters.getShaclConfigurationFile());
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void getShaclOutputFile() {
    String[] arguments = new String[]{"--shaclOutputFile", "shacl.csv"};
    try {
      Shacl4bibParameters parameters = new Shacl4bibParameters(arguments);
      assertEquals("shacl.csv", parameters.getShaclOutputFile());
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void formatParameters() {
    String[] arguments = new String[]{"--shaclConfigurationFile", "shacl.cnf", "--shaclOutputFile", "shacl.csv"};
    try {
      Shacl4bibParameters parameters = new Shacl4bibParameters(arguments);
      assertEquals("schemaType: MARC21\n" +
        "marcVersion: MARC21, MARC21\n" +
        "marcFormat: ISO, Binary (ISO 2709)\n" +
        "dataSource: FILE, from file\n" +
        "limit: -1\n" +
        "offset: -1\n" +
        "MARC files: \n" +
        "id: null\n" +
        "defaultRecordType: null\n" +
        "fixAlephseq: false\n" +
        "fixAlma: false\n" +
        "alephseq: false\n" +
        "marcxml: false\n" +
        "lineSeparated: false\n" +
        "outputDir: .\n" +
        "trimId: false\n" +
        "ignorableFields: \n" +
        "allowableRecords: \n" +
        "ignorableRecords: \n" +
        "defaultEncoding: null\n" +
        "alephseqLineType: null\n" +
        "groupBy: null\n" +
        "groupListFile: null\n" +
        "shaclConfigurationFile: shacl.cnf\n" +
        "shaclOutputFile: shacl.csv\n",
        parameters.formatParameters());
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}