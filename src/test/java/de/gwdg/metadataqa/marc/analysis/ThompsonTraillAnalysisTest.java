package de.gwdg.metadataqa.marc.analysis;

import org.junit.Test;

import java.util.Arrays;

import static de.gwdg.metadataqa.marc.Utils.createRow;
import static org.junit.Assert.assertEquals;

public class ThompsonTraillAnalysisTest {
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
  public void min() {
    assertEquals(10, Math.min(20, 10));
  }
}
