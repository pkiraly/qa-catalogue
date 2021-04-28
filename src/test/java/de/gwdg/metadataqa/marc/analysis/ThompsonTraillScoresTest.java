package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
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

    ttScores.set(ThompsonTraillFields.AUTHORS, 0);
    assertEquals(0, (int) ttScores.get(ThompsonTraillFields.AUTHORS));

    ttScores.set(ThompsonTraillFields.ALTERNATIVE_TITLES, 0);
    assertEquals(0, (int) ttScores.get(ThompsonTraillFields.ALTERNATIVE_TITLES));

    ttScores.set(ThompsonTraillFields.EDITION, 0);
    assertEquals(0, (int) ttScores.get(ThompsonTraillFields.EDITION));

    ttScores.set(ThompsonTraillFields.CONTRIBUTORS, 1);
    assertEquals(1, (int) ttScores.get(ThompsonTraillFields.CONTRIBUTORS));

    ttScores.set(ThompsonTraillFields.SERIES, 4);
    assertEquals(4, (int) ttScores.get(ThompsonTraillFields.SERIES));

    ttScores.calculateTotal();
    assertEquals(
      Arrays.asList(0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5),
      ttScores.asList()
    );
    assertEquals(5, (int) ttScores.get(ThompsonTraillFields.TOTAL));
    assertEquals(0, (int) ttScores.get(ThompsonTraillFields.OTHER));
  }
}