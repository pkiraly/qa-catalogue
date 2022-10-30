package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BLClassifierTest {

  @Test
  public void testClassify() throws URISyntaxException, IOException {
    List<String> lines = FileUtils.readLinesFromResource("bl/006013122.mrctxt");
    BibliographicRecord marcRecord = MarcFactory.createFromFormattedText(lines, MarcVersion.BL);

    BLClassifier classifier = new BLClassifier();
    assertEquals("DEFICIENT", classifier.classify(marcRecord));
  }
}