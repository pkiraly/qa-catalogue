package de.gwdg.metadataqa.marc;

import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CsvUtilsTest {

  @Test
  public void createCsv_array() {
    assertEquals("column1,column2,column3\n", CsvUtils.createCsv(new String[]{"column1", "column2", "column3"}));
    assertEquals("column1,column2,\"column3,3\"\n", CsvUtils.createCsv(new String[]{"column1", "column2", "column3,3"}));
    assertEquals("column1,column2,c\\olumn3\n", CsvUtils.createCsv(new String[]{"column1", "column2", "c\\olumn3"}));
    assertEquals("column1,column2,colum\\n3\n", CsvUtils.createCsv(new String[]{"column1", "column2", "colum\n3"}));
  }

  @Test
  public void createCsv_list() {
    assertEquals("column1,column2,colum\\n3\n", CsvUtils.createCsv(Arrays.asList("column1", "column2", "colum\n3")));
    assertEquals("column1,1,column2,colum\\n3\n", CsvUtils.createCsv(Arrays.asList("column1", 1, "column2", "colum\n3")));
  }
}