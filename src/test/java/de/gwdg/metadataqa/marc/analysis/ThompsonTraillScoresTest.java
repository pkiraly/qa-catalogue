package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcRecord;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ThompsonTraillScoresTest {

  MarcRecord marcRecord;

  @Before
  public void setup() throws IOException, URISyntaxException {
    List<String> lines = FileUtils.readLines("marctxt/010000011.mrctxt");
    marcRecord = MarcFactory.createFromFormattedText(lines);
  }

  @Test
  public void count() {
    ThompsonTraillScores ttScores = new ThompsonTraillScores();

    ttScores.set(ThompsonTraillFields.ISBN, 0);
    assertEquals(0, (int) ttScores.get(ThompsonTraillFields.ISBN));

    ttScores.set(ThompsonTraillFields.Authors, 0);
    assertEquals(0, (int) ttScores.get(ThompsonTraillFields.Authors));

    ttScores.set(ThompsonTraillFields.AlternativeTitles, 0);
    assertEquals(0, (int) ttScores.get(ThompsonTraillFields.AlternativeTitles));

    ttScores.set(ThompsonTraillFields.Edition, 0);
    assertEquals(0, (int) ttScores.get(ThompsonTraillFields.Edition));

    ttScores.set(ThompsonTraillFields.Contributors, 1);
    assertEquals(1, (int) ttScores.get(ThompsonTraillFields.Contributors));

    ttScores.set(ThompsonTraillFields.Series, 4);
    assertEquals(4, (int) ttScores.get(ThompsonTraillFields.Series));

    ttScores.calculateTotal();
    assertEquals(
      Arrays.asList(0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5),
      ttScores.asList()
    );
    assertEquals(5, (int) ttScores.get(ThompsonTraillFields.TOTAL));
    assertEquals(0, (int) ttScores.get(ThompsonTraillFields.Other));
  }
}