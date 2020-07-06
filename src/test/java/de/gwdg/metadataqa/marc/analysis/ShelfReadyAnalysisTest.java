package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcRecord;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShelfReadyAnalysisTest {

  @Test
  public void test_getScores() throws IOException, URISyntaxException {
    List<String> lines = FileUtils.readLines("marctxt/010000011.mrctxt");
    MarcRecord record = MarcFactory.createFromFormattedText(lines);

    List<Double> scores = ShelfReadyAnalysis.getScores(record);
    assertEquals(
      "38.0,40.0,29.0,0.0,0.0,0.0,0.0,9.5,23.200000000000003,10.333333333333332,0.0,0.0,44.0,0.0,17.6,0.0,0.0,30.0,14.333333333333332,35.0,35.0,34.0,40.0,0.0,0.0,14.0,0.0,0.0,0.0,44.0,44.0,0.0,0.0,32.0,15.704901960784314",
      StringUtils.join(scores, ","));
  }

}
