package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.PicaRecord;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static de.gwdg.metadataqa.marc.Utils.createRow;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

  @Test
  public void pica() {
    Map<ThompsonTraillFields, List<String>> map = (new PicaRecord()).getThompsonTraillTagsMap();
    assertNotNull(map);
    assertEquals(17, map.size());
    assertEquals(List.of("004A", "004D"), map.get(ThompsonTraillFields.ISBN));
    assertEquals(List.of("028A", "029A"), map.get(ThompsonTraillFields.AUTHORS));
    assertEquals(List.of("027A", "047C", "046C", "046A", "025@"), map.get(ThompsonTraillFields.ALTERNATIVE_TITLES));
    assertEquals(List.of("032@"), map.get(ThompsonTraillFields.EDITION));
    assertEquals(List.of("028B/01", "028C", "028E", "028G", "033J", "037Q", "071A",
      "072A", "073A", "074A", "075A", "071B", "072B", "073B", "074B", "075B", "071C", "072C",
      "073C", "074C", "075C", "029G", "029F", "029E"), map.get(ThompsonTraillFields.CONTRIBUTORS));
    assertEquals(List.of("036D"), map.get(ThompsonTraillFields.SERIES));
    assertEquals(List.of("031C", "047I"), map.get(ThompsonTraillFields.TOC));
    assertEquals(List.of(), map.get(ThompsonTraillFields.DATE_008));
    assertEquals(List.of(), map.get(ThompsonTraillFields.DATE_26X));
    assertEquals(List.of("044N", "044A", "044C", "044L/00", "045D/00"), map.get(ThompsonTraillFields.LC_NLM));
    assertEquals(List.of("044N", "044A", "044C", "044L/00", "045D/00"), map.get(ThompsonTraillFields.MESH));
    assertEquals(List.of("044N", "044A", "044C", "044L/00", "045D/00"), map.get(ThompsonTraillFields.FAST));
    assertEquals(List.of("044N", "044A", "044C", "044L/00", "045D/00"), map.get(ThompsonTraillFields.GND));
    assertEquals(List.of("044N", "044A", "044C", "044L/00", "045D/00"), map.get(ThompsonTraillFields.OTHER));
    assertEquals(List.of(), map.get(ThompsonTraillFields.ONLINE));
    assertEquals(List.of(), map.get(ThompsonTraillFields.LANGUAGE_OF_RESOURCE));
    assertEquals(List.of(), map.get(ThompsonTraillFields.COUNTRY_OF_PUBLICATION));
  }
}
