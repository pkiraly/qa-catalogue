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
  public void fields() {
    assertEquals(
      "{id=id, ISBN=isbn, Authors=authors, Alternative Titles=alternative-titles,"
      + " Edition=edition, Contributors=contributors, Series=series,"
      + " Table of Contents and Abstract=toc-and-abstract, Date 008=date-008,"
      + " Date 26X=date-26x, LC/NLM Classification=classification-lc-nlm,"
      + " Subject Headings: Library of Congress=classification-loc,"
      + " Subject Headings: Mesh=classification-mesh,"
      + " Subject Headings: Fast=classification-fast,"
      + " Subject Headings: GND=classification-gnd,"
      + " Subject Headings: Other=classification-other, Online=online,"
      + " Language of Resource=language-of-resource,"
      + " Country of Publication=country-of-publication,"
      + " Language of Cataloging=no-language-or-english,"
      + " Descriptive cataloging standard is RDA=rda,"
      + " total=total}",
      ThompsonTraillAnalysis.getFields().toString()
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
