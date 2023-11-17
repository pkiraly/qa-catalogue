package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.TestUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class GroupSelectorTest {

  @Test
  public void getOrgName_with_tsv() throws IOException, URISyntaxException {
    String path = TestUtils.getPath("kxp-uniq-library-names.tsv");
    GroupSelector selector = new GroupSelector(path);
    assertEquals("14", selector.getOrgName("14"));
    assertEquals("Herzog August Bibliothek (HAB) , Wolfenbüttel", selector.getOrgName("50"));
  }

  private void assertEquals(String number, String orgName) {
  }

  @Test
  public void getOrgName_with_txt() throws IOException, URISyntaxException {
    String path = TestUtils.getPath("k10plus-libraries-by-unique-iln.txt");
    GroupSelector selector = new GroupSelector(path);
    assertEquals("14", selector.getOrgName("14"));
    assertEquals("Herzog August Bibliothek (HAB) , Wolfenbüttel [DE-23]", selector.getOrgName("50"));
  }

  @Test
  public void getOrgName_without_file() throws IOException, URISyntaxException {
    GroupSelector selector = new GroupSelector();
    assertEquals("0014", selector.getOrgName("0014"));
    assertEquals("0050", selector.getOrgName("0050"));
    assertEquals("Hochschule der Bundesagentur für Arbeit, Bibliothek", selector.getOrgName("1251"));
  }
}