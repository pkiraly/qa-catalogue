package de.gwdg.metadataqa.marc.analysis;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import static de.gwdg.metadataqa.api.util.FileUtils.getPath;
import static org.junit.Assert.*;

public class GroupSelectorTest {

  @Test
  public void getOrgName_with_file() throws IOException, URISyntaxException {
    Path path = getPath("kxp-uniq-library-names.tsv");
    GroupSelector selector = new GroupSelector(path.toAbsolutePath().toString());
    assertEquals("14", selector.getOrgName("14"));
    assertEquals("Herzog August Bibliothek (HAB) , Wolfenbüttel", selector.getOrgName("50"));
  }

  @Test
  public void getOrgName_without_file() throws IOException, URISyntaxException {
    GroupSelector selector = new GroupSelector();
    assertEquals("0014", selector.getOrgName("0014"));
    assertEquals("0050", selector.getOrgName("0050"));
    assertEquals("Hochschule der Bundesagentur für Arbeit, Bibliothek", selector.getOrgName("1251"));
  }
}