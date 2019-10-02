package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.utils.ReadMarc;
import org.junit.Test;
import org.marc4j.marc.Record;

import java.nio.file.Path;

public class ClassificationAnalysisTest {

  @Test
  public void test() throws Exception {
    Path path = FileUtils.getPath("general/0001-01.mrc");
    Record marc4jRecord = ReadMarc.read(path.toString()).get(0);
    ClassificationAnalysis analysis = new ClassificationAnalysis(new String[]{});
    MarcRecord record = MarcFactory.createFromMarc4j(marc4jRecord);
    analysis.processRecord(record, 1);
  }

}
