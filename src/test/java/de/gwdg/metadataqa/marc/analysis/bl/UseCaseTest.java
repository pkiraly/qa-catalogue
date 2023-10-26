package de.gwdg.metadataqa.marc.analysis.bl;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class UseCaseTest {

  @Test
  public void test() {
    assertEquals(Arrays.asList("852", "856"), UseCase.B02.getDataElelemnts());
    assertEquals(Arrays.asList("852", "856"), UseCase.B02.getElements().stream().map(e -> e.toString()).collect(Collectors.toList()));
  }

  @Test
  public void testPatternMatching() {
    List<String> definitions = UseCase.E04.getElements().stream().map(e -> e.toString()).collect(Collectors.toList());
    assertEquals(27, definitions.size());
    assertEquals(
      List.of("700", "710", "711", "720", "730", "740", "751", "752", "753", "754", "758", "760", "762", "765", "767", "770", "772", "773", "774", "775", "776", "777", "780", "785", "786", "787", "788"),
      definitions);
  }

  @Test
  public void testPatternMatchingWithSubfield() {
    assertEquals(Arrays.asList("1XX$e", "7XX$e"), UseCase.E02.getDataElelemnts());
    List<String> definitions = UseCase.E02.getDataElelemntsNormalized();
    assertEquals(11, definitions.size());
    assertEquals(
      List.of("110$e", "111$e", "100$e", "700$e", "710$e", "711$e", "720$e", "751$e", "752$e", "775$e", "788$e"),
      definitions);
  }

  @Test
  public void testAll() {
    UseCase[] useCases = UseCase.values();
    int i = 0;
    assertEquals("245$a", useCases[i].getEncoding());
    assertEquals(List.of("245$a"), useCases[i].getDataElelemnts());
    assertEquals(List.of("245$a"), useCases[i].getDataElelemntsNormalized());

    i++;
    assertEquals("852;856", useCases[i].getEncoding());
    assertEquals(List.of("852", "856"), useCases[i].getDataElelemnts());
    assertEquals(List.of("852", "856"), useCases[i].getDataElelemntsNormalized());

    //  -> [506, 540], [506, 540]
    i++;
    assertEquals("506;540", useCases[i].getEncoding());
    assertEquals(List.of("506", "540"), useCases[i].getDataElelemnts());
    assertEquals(List.of("506", "540"), useCases[i].getDataElelemntsNormalized());

    // 336;337;338 -> [336, 337, 338], [336, 337, 338]
    i++;
    assertEquals("336;337;338", useCases[i].getEncoding());
    assertEquals(List.of("336", "337", "338"), useCases[i].getDataElelemnts());
    assertEquals(List.of("336", "337", "338"), useCases[i].getDataElelemntsNormalized());

    // As specified -> [As specified], []
    // 541;561;700 -> [541, 561, 700], [541, 561, 700]
    // 021;022 -> [021, 022], [022]
    // 040 -> [040], [040]
    // FMT -> [FMT], [FMT]
    // FIN;859 -> [FIN, 859], [FIN, 859]
    // 008/6-14 -> [008/6-14], []
    // 008/15-17 -> [008/15-17], []
    // 008/35-37 -> [008/35-37], []
    // 040 -> [040], [040]
    // 015 -> [015], [015]
    // 130;1XX$t;240;730;7XX$t -> [130, 1XX$t, 240, 730, 7XX$t], [130, 110$t, 111$t, 130$t, 100$t, 240, 730, 700$t, 710$t, 711$t, 730$t, 760$t, 762$t, 765$t, 767$t, 770$t, 772$t, 773$t, 774$t, 775$t, 776$t, 777$t, 780$t, 785$t, 786$t, 787$t]
    // 1XX;7XX -> [1XX, 7XX], [110, 111, 130, 100, 700, 710, 711, 720, 730, 740, 751, 752, 753, 754, 758, 760, 762, 765, 767, 770, 772, 773, 774, 775, 776, 777, 780, 785, 786, 787]
    // 052;055;072;080;084;085;086;6XX -> [052, 055, 072, 080, 084, 085, 086, 6XX], [052, 055, 072, 080, 084, 085, 086, 600, 610, 611, 630, 647, 648, 650, 651, 653, 654, 655, 656, 657, 658, 662, 688, 690, 692]
    // As specified -> [As specified], []
    // 250 -> [250], [250]
    // 264;260 -> [264, 260], [264, 260]
    // 246 -> [246], [246]
    // 505 -> [505], [505]
    // 300$a -> [300$a], [300$a]
    // 588 -> [588], [588]
    // 7XX -> [7XX], [700, 710, 711, 720, 730, 740, 751, 752, 753, 754, 758, 760, 762, 765, 767, 770, 772, 773, 774, 775, 776, 777, 780, 785, 786, 787]
    // 1XX$e;7XX$e -> [1XX$e, 7XX$e], [110$e, 111$e, 100$e, 700$e, 710$e, 711$e, 720$e, 751$e, 752$e, 775$e]
    // 082 -> [082], [082]
    // 7XX -> [7XX], [700, 710, 711, 720, 730, 740, 751, 752, 753, 754, 758, 760, 762, 765, 767, 770, 772, 773, 774, 775, 776, 777, 780, 785, 786, 787]
    // 6XX;730;7XX$t;LKR -> [6XX, 730, 7XX$t, LKR], [600, 610, 611, 630, 647, 648, 650, 651, 653, 654, 655, 656, 657, 658, 662, 688, 690, 692, 730, 700$t, 710$t, 711$t, 730$t, 760$t, 762$t, 765$t, 767$t, 770$t, 772$t, 773$t, 774$t, 775$t, 776$t, 777$t, 780$t, 785$t, 786$t, 787$t, LKR]
    // 700$t;710$t;730 -> [700$t, 710$t, 730], [700$t, 710$t, 730]

    /*
    for (UseCase useCase : UseCase.values()) {
      System.err.println(useCase.getEncoding() + " -> " + useCase.getDataElelemnts() + ", " + useCase.getDataElelemntsNormalized());
    }
     */
  }
}