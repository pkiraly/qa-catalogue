package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static de.gwdg.metadataqa.marc.Utils.createRow;
import static org.junit.Assert.assertEquals;

public class ThompsonTraillAnalysisTest {

  BibliographicRecord marcRecord;

  @Before
  public void setup() throws IOException, URISyntaxException {
    List<String> lines = FileUtils.readLinesFromResource("marctxt/010000011.mrctxt");
    marcRecord = MarcFactory.createFromFormattedText(lines);
  }

  @Test
  public void header() {
    assertEquals(
      Arrays.asList(
        "id", "isbn", "authors", "alternative-titles", "edition", "contributors",
        "series", "toc-and-abstract", "date-008", "date-26x", "classification-lc-nlm",
        "classification-loc", "classification-mesh", "classification-fast",
        "classification-gnd", "classification-other", "online", "language-of-resource",
        "country-of-publication", "no-language-or-english", "rda", "total"
      ),
      ThompsonTraillAnalysis.getHeader()
    );
  }

  @Test
  public void headerRow() {
    assertEquals(
      "id,isbn,authors,alternative-titles,edition,contributors,series,toc-and-abstract,"
      + "date-008,date-26x,classification-lc-nlm,classification-loc,classification-mesh,"
      + "classification-fast,classification-gnd,classification-other,online,"
      + "language-of-resource,country-of-publication,no-language-or-english,rda,total\n",
      createRow(ThompsonTraillAnalysis.getHeader()))
    ;
    createRow(ThompsonTraillAnalysis.getHeader());
  }

  @Test
  public void getScores() {
    assertEquals(
      Arrays.asList(0, 0, 0, 0, 1, 4, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 9),
      ThompsonTraillAnalysis.getScores(marcRecord)
    );
  }

  @Test
  public void min() {
    assertEquals(10, Math.min(20, 10));
  }


}
