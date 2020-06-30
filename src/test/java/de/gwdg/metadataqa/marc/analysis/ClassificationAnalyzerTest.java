package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ClassificationAnalyzerTest {

  @Test
  public void test() throws IOException, URISyntaxException {
    List<String> lines = FileUtils.readLines("marctxt/010000011.mrctxt");
    MarcRecord record = MarcFactory.createFromFormattedText(lines);
    ClassificationStatistics statistics = new ClassificationStatistics();

    ClassificationAnalyzer analyzer = new ClassificationAnalyzer(record, statistics);
    int count = analyzer.process();
    assertEquals(1, count);
    Map<Schema, Integer> recordStats = statistics.getRecords();
    assertEquals(2, recordStats.size());
    Schema first = (Schema) recordStats.keySet().toArray()[0];

    assertEquals(
      "Gemeinsame Normdatei: Beschreibung des Inhalts (Leipzig, Frankfurt: Deutsche Nationalbibliothek)",
      first.getSchema());
    assertEquals(1, (int) recordStats.get(first));
    assertEquals(1, (int) statistics.getInstances().get(first));
    Map<List<String>, Integer> subfieldMap = statistics.getSubfields().get(first);
    assertEquals(1, subfieldMap.size());
    List<String> list = (List<String>) subfieldMap.keySet().toArray()[0];
    assertEquals("[0+, a, 2]", list.toString());

    Schema second = (Schema) recordStats.keySet().toArray()[1];
    assertEquals("Basisklassifikation", second.getSchema());
    assertEquals(1, (int) statistics.getInstances().get(second));
    subfieldMap = statistics.getSubfields().get(second);
    assertEquals(1, subfieldMap.size());
    list = (List<String>) subfieldMap.keySet().toArray()[0];
    assertEquals("[a, 2]", list.toString());
  }
}
