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
    assertEquals(26, definitions.size());
    assertEquals(
      Arrays.asList("700", "710", "711", "720", "730", "740", "751", "752", "753", "754", "758", "760", "762", "765", "767", "770", "772", "773", "774", "775", "776", "777", "780", "785", "786", "787"),
      definitions);
  }

  @Test
  public void testPatternMatchingWithSubfield() {
    assertEquals(Arrays.asList("1XX", "7XX$e"), UseCase.E02.getDataElelemnts());
    List<String> definitions = UseCase.E02.getDataElelemntsNormalized();
    assertEquals(11, definitions.size());
    assertEquals(
      Arrays.asList("110", "111", "130", "100", "700$e", "710$e", "711$e", "720$e", "751$e", "752$e", "775$e"),
      definitions);
  }

  @Test
  public void testAll() {
    for (UseCase useCase : UseCase.values()) {
      System.err.println(useCase.getEncoding() + " -> " + useCase.getDataElelemntsNormalized());
    }

  }

}