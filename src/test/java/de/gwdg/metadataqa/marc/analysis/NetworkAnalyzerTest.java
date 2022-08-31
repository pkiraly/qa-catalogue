package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class NetworkAnalyzerTest {

  BibliographicRecord marcRecord;

  @Before
  public void setup() throws IOException, URISyntaxException {
    List<String> lines = FileUtils.readLinesFromResource("marctxt/010000011.mrctxt");
    marcRecord = MarcFactory.createFromFormattedText(lines);
  }

  @Test
  public void process() {
    NetworkAnalyzer analyzer = new NetworkAnalyzer(marcRecord);
    Set<DataField> collector = analyzer.process(1);
    assertEquals(5, collector.size());
    assertEquals(
      Arrays.asList("084", "655", "710", "810", "810"),
      collector.stream()
        .map(DataField::getTag)
        .sorted()
        .collect(Collectors.toList())
    );
  }
}