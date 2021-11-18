package de.gwdg.metadataqa.marc.cli;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CompletenessTest {

  @Test
  public void test() {
    Map<String, Map<String, Integer>> packages = new HashMap<>();
    Map<String, Integer> general = new HashMap<>();
    general.put("010", 1);
    general.put("020", 2);
    general.put("030", 3);
    packages.put("general", general);
    Map<String, Integer> specific = new HashMap<>();
    specific.put("110", 1);
    specific.put("120", 2);
    specific.put("130", 3);
    packages.put("specific", specific);


    packages.forEach((a, b) -> {
      System.err.println(a);
      b.forEach((c, d) ->{
        System.err.println(c + ": " + d);
      });
    });

  }

  @Test
  public void testSimple() {
    Map<Integer, String> namesMap = new HashMap<>();
    namesMap.put(1, "Larry");
    namesMap.put(2, "Steve");
    namesMap.put(3, "James");

    namesMap.forEach((key, value) -> System.out.println(key + " " + value));
  }

  @Test
  public void testRegex() {
    String a = "041$_ind1";
    assertEquals("041$ind1", a.replace("_ind", "ind"));

    String b = "041$|0";
    assertEquals("041$0", b.replaceAll("\\|(\\d)$", "$1"));
  }
}