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

public class AuthorithyAnalizerTest {

  @Test
  public void test() throws IOException, URISyntaxException {
    List<String> lines = FileUtils.readLines("general/010000011.mrctxt");
    MarcRecord record = MarcFactory.createFromFormattedText(lines);
    AuthorityStatistics statistics = new AuthorityStatistics();

    AuthorithyAnalizer analyzer = new AuthorithyAnalizer(record, statistics);
    int count = analyzer.process();
    assertEquals(3, count);
    Map<Schema, Integer> recordStats = statistics.getRecords();
    assertEquals(2, recordStats.size());
    Schema first = (Schema) recordStats.keySet().toArray()[0];

    assertEquals("DE-627", first.getSchema());
    assertEquals(1, (int) recordStats.get(first));
    assertEquals(1, (int) statistics.getInstances().get(first));
    Map<List<String>, Integer> subfieldMap = statistics.getSubfields().get(first);
    assertEquals(1, subfieldMap.size());
    List<String> list = (List<String>) subfieldMap.keySet().toArray()[0];
    assertEquals("[0+, a]", list.toString());

    Schema second = (Schema) recordStats.keySet().toArray()[1];
    assertEquals("undetectable", second.getSchema());
    assertEquals(2, (int) statistics.getInstances().get(second));
    subfieldMap = statistics.getSubfields().get(second);
    assertEquals(2, subfieldMap.size());
    list = (List<String>) subfieldMap.keySet().toArray()[0];
    assertEquals("[a, t, v, w+, 9]", list.toString());

    list = (List<String>) subfieldMap.keySet().toArray()[1];
    assertEquals("[a, t, v, w+, x, 9]", list.toString());
  }
}
