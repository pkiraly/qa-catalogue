package de.gwdg.metadataqa.marc.cli;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CliParameterDefinitionsExporterTest {

  @Test
  public void test() {
    CliParameterDefinitionsExporter extractor = new CliParameterDefinitionsExporter();
    String json = extractor.exportAll();
    assertNotNull(json);
    assertTrue(json.contains("\"common\""));
    ObjectMapper mapper = new ObjectMapper();
    Map firstItem = null;
    LinkedHashMap parameters = null;
    try {
      parameters = (LinkedHashMap) mapper.readValue(json, Object.class);

      assertEquals(17, parameters.size());
      assertEquals(
        Set.of(
          "common", "completeness", "validate", "index", "classifications",
          "authorities", "tt-completeness", "shelf-ready-completeness",
          "bl-classification", "serial-score", "formatter", "functional-analysis",
          "network-analysis", "marc-history", "record-patterns", "shacl4bib",
          "translations"
        ),
        parameters.keySet());

      assertTrue(parameters.containsKey("common"));
      assertEquals(ArrayList.class, parameters.get("common").getClass());
      assertEquals(31, ((List) parameters.get("common")).size());

      firstItem = (Map) ((List) parameters.get("common")).get(0);
      assertEquals(4, firstItem.size());
      assertEquals("m", firstItem.get("short"));
      assertEquals("marcVersion", firstItem.get("long"));
      assertEquals(true, firstItem.get("hasArg"));
      assertEquals("MARC version ('OCLC' or 'DNB')", firstItem.get("description"));

      assertTrue(parameters.containsKey("completeness"));
      assertEquals(3, ((List) parameters.get("completeness")).size());
      firstItem = (Map) ((List) parameters.get("completeness")).get(0);
      assertEquals(4, firstItem.size());
      assertEquals("R", firstItem.get("short"));
      assertEquals("format", firstItem.get("long"));
      assertEquals(true, firstItem.get("hasArg"));
      assertEquals("specify a format", firstItem.get("description")); // TODO: not a definition

      assertTrue(parameters.containsKey("validate"));
      assertEquals(8, ((List) parameters.get("validate")).size());
      firstItem = (Map) ((List) parameters.get("validate")).get(0);
      assertEquals(4, firstItem.size());
      assertEquals("G", firstItem.get("short"));
      assertEquals("summaryFileName", firstItem.get("long"));
      assertEquals(true, firstItem.get("hasArg"));
      assertEquals("the summary file name (provides a summary of issues, such as the number of instance and number of records having the particular issue)", firstItem.get("description"));

      assertTrue(parameters.containsKey("index"));
      assertEquals(9, ((List) parameters.get("index")).size());
      firstItem = (Map) ((List) parameters.get("index")).get(0);
      assertEquals(4, firstItem.size());
      assertEquals("S", firstItem.get("short"));
      assertEquals("solrUrl", firstItem.get("long"));
      assertEquals(true, firstItem.get("hasArg"));
      assertEquals("the URL of Solr server including the core (e.g. http://localhost:8983/solr/loc)", firstItem.get("description"));

      assertTrue(parameters.containsKey("classifications"));
      assertEquals(1, ((List) parameters.get("classifications")).size());
      firstItem = (Map) ((List) parameters.get("classifications")).get(0);
      assertEquals(4, firstItem.size());
      assertEquals("A", firstItem.get("short"));
      assertEquals("collectCollocations", firstItem.get("long"));
      assertEquals(false, firstItem.get("hasArg"));
      assertEquals("collect collocation of schemas", firstItem.get("description"));

      assertTrue(parameters.containsKey("authorities"));
      assertEquals(8, ((List) parameters.get("authorities")).size());
      firstItem = (Map) ((List) parameters.get("authorities")).get(0);
      assertEquals(4, firstItem.size());
      assertEquals("G", firstItem.get("short"));
      assertEquals("summaryFileName", firstItem.get("long"));
      assertEquals(true, firstItem.get("hasArg"));
      assertEquals("the summary file name (provides a summary of issues, such as the number of instance and number of records having the particular issue)", firstItem.get("description"));

      assertTrue(parameters.containsKey("tt-completeness"));
      assertEquals(1, ((List) parameters.get("tt-completeness")).size());
      firstItem = (Map) ((List) parameters.get("tt-completeness")).get(0);
      assertEquals(4, firstItem.size());
      assertEquals("F", firstItem.get("short"));
      assertEquals("fileName", firstItem.get("long"));
      assertEquals(true, firstItem.get("hasArg"));
      assertEquals("the report file name (default is tt-completeness.csv)", firstItem.get("description"));

      assertTrue(parameters.containsKey("shelf-ready-completeness"));
      assertEquals(1, ((List) parameters.get("shelf-ready-completeness")).size());
      firstItem = (Map) ((List) parameters.get("shelf-ready-completeness")).get(0);
      assertEquals(4, firstItem.size());
      assertEquals("F", firstItem.get("short"));
      assertEquals("fileName", firstItem.get("long"));
      assertEquals(true, firstItem.get("hasArg"));
      assertEquals("the report file name (default is shelf-ready-completeness.csv)", firstItem.get("description"));

      assertTrue(parameters.containsKey("bl-classification"));
      assertEquals(0, ((List) parameters.get("bl-classification")).size());

      assertTrue(parameters.containsKey("serial-score"));
      assertEquals(1, ((List) parameters.get("serial-score")).size());
      firstItem = (Map) ((List) parameters.get("serial-score")).get(0);
      assertEquals(4, firstItem.size());
      assertEquals("F", firstItem.get("short"));
      assertEquals("fileName", firstItem.get("long"));
      assertEquals(true, firstItem.get("hasArg"));
      assertEquals("the report file name (default is serial-score.csv)", firstItem.get("description"));

      assertTrue(parameters.containsKey("formatter"));
      assertEquals(8, ((List) parameters.get("formatter")).size());
      firstItem = (Map) ((List) parameters.get("formatter")).get(0);
      assertEquals(4, firstItem.size());
      assertEquals("l", firstItem.get("short"));
      assertEquals("selector", firstItem.get("long"));
      assertEquals(true, firstItem.get("hasArg"));
      assertEquals("selectors", firstItem.get("description"));

      assertTrue(parameters.containsKey("functional-analysis"));
      assertEquals(3, ((List) parameters.get("functional-analysis")).size());
      firstItem = (Map) ((List) parameters.get("functional-analysis")).get(0);
      assertEquals(4, firstItem.size());
      assertEquals("R", firstItem.get("short"));
      assertEquals("format", firstItem.get("long"));
      assertEquals(true, firstItem.get("hasArg"));
      assertEquals("specify a format", firstItem.get("description"));

      assertTrue(parameters.containsKey("network-analysis"));
      assertEquals(2, ((List) parameters.get("network-analysis")).size());
      firstItem = (Map) ((List) parameters.get("network-analysis")).get(0);
      assertEquals(4, firstItem.size());
      assertEquals("l", firstItem.get("short"));
      assertEquals("group-limit", firstItem.get("long"));
      assertEquals(true, firstItem.get("hasArg"));
      assertEquals("pair creation limit", firstItem.get("description"));

      assertTrue(parameters.containsKey("marc-history"));
      assertEquals(0, ((List) parameters.get("marc-history")).size());

      assertTrue(parameters.containsKey("record-patterns"));
      assertEquals(3, ((List) parameters.get("record-patterns")).size());
      firstItem = (Map) ((List) parameters.get("record-patterns")).get(0);
      assertEquals(4, firstItem.size());
      assertEquals("R", firstItem.get("short"));
      assertEquals("format", firstItem.get("long"));
      assertEquals(true, firstItem.get("hasArg"));
      assertEquals("specify a format", firstItem.get("description"));

      assertTrue(parameters.containsKey("shacl4bib"));
      assertEquals(3, ((List) parameters.get("shacl4bib")).size());
      firstItem = (Map) ((List) parameters.get("shacl4bib")).get(0);
      assertEquals(4, firstItem.size());
      assertEquals("C", firstItem.get("short"));
      assertEquals("shaclConfigurationFile", firstItem.get("long"));
      assertEquals(true, firstItem.get("hasArg"));
      assertEquals("specify the configuration file", firstItem.get("description"));

      assertTrue(parameters.containsKey("translations"));
      assertEquals(6, ((List) parameters.get("translations")).size());
      assertEquals(
        "shaclConfigurationFile, shaclOutputFile, shaclOutputType, translationDebugFailedRules, translationPlaceNameDictionaryDir, translationExport",
        ((List<?>) parameters.get("translations")).stream()
        .map(s -> ((Map<String, Object>) s).get("long").toString())
        .collect(Collectors.joining(", ")));
      firstItem = (Map) ((List) parameters.get("translations")).get(0);
      assertEquals(4, firstItem.size());
      assertEquals("C", firstItem.get("short"));
      assertEquals("shaclConfigurationFile", firstItem.get("long"));
      assertEquals(true, firstItem.get("hasArg"));
      assertEquals("specify the configuration file", firstItem.get("description"));

    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    assertNotNull(parameters);
  }
}
