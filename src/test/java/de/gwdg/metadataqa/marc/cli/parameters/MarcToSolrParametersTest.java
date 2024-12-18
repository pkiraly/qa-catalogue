package de.gwdg.metadataqa.marc.cli.parameters;

import de.gwdg.metadataqa.marc.model.SolrFieldType;
import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MarcToSolrParametersTest {

  @Test
  public void testDefaults() {
    String[] arguments = new String[]{"a-marc-file.mrc"};
    try {
      MarcToSolrParameters parameters = new MarcToSolrParameters(arguments);

      assertNotNull(parameters.getArgs());
      assertEquals(1, parameters.getArgs().length);
      assertEquals("a-marc-file.mrc", parameters.getArgs()[0]);

      assertFalse(parameters.doHelp());

      assertNull(parameters.getSolrUrl());
      assertFalse(parameters.isDoCommit());
      assertNotNull(parameters.getSolrFieldType());
      assertEquals(SolrFieldType.MIXED, parameters.getSolrFieldType());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testHelp() {
    String[] arguments = new String[]{"--help", "a-marc-file.mrc"};
    try {
      MarcToSolrParameters parameters = new MarcToSolrParameters(arguments);
      assertTrue(parameters.doHelp());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testSolrUrl() {
    String[] arguments = new String[]{"--solrUrl", "http://localhost:8983/solr", "a-marc-file.mrc"};
    try {
      MarcToSolrParameters parameters = new MarcToSolrParameters(arguments);
      assertNotNull(parameters.getSolrUrl());
      assertEquals("http://localhost:8983/solr", parameters.getSolrUrl());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testDoCommit() {
    String[] arguments = new String[]{"--doCommit", "a-marc-file.mrc"};
    try {
      MarcToSolrParameters parameters = new MarcToSolrParameters(arguments);
      assertTrue(parameters.isDoCommit());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testSolrFieldTypeMarc() {
    String[] arguments = new String[]{"--solrFieldType", "marc-tags", "a-marc-file.mrc"};
    try {
      MarcToSolrParameters parameters = new MarcToSolrParameters(arguments);
      assertEquals(SolrFieldType.MARC, parameters.getSolrFieldType());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testSolrFieldTypeHuman() {
    String[] arguments = new String[]{"--solrFieldType", "human-readable", "a-marc-file.mrc"};
    try {
      MarcToSolrParameters parameters = new MarcToSolrParameters(arguments);
      assertEquals(SolrFieldType.HUMAN, parameters.getSolrFieldType());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testSolrFieldTypeMixed() {
    String[] arguments = new String[]{"--solrFieldType", "mixed", "a-marc-file.mrc"};
    try {
      MarcToSolrParameters parameters = new MarcToSolrParameters(arguments);
      assertEquals(SolrFieldType.MIXED, parameters.getSolrFieldType());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testFieldPrefix() {
    String[] arguments = new String[]{"--fieldPrefix", "q", "a-marc-file.mrc"};
    try {
      MarcToSolrParameters parameters = new MarcToSolrParameters(arguments);
      assertEquals("q", parameters.getFieldPrefix());
      assertEquals(SolrFieldType.MIXED, parameters.getSolrFieldType());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }


}
