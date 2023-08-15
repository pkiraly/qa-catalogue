package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.utils.Counter;
import de.gwdg.metadataqa.marc.utils.FrbrFunctionLister;
import de.gwdg.metadataqa.marc.utils.FunctionValue;
import de.gwdg.metadataqa.marc.utils.ReadMarc;
import org.junit.Test;
import org.marc4j.marc.Record;

import java.nio.file.Path;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FunctionalAnalysisTest {

  @Test
  public void test() throws Exception {
    Path path = FileUtils.getPath("general/0001-01.mrc");
    Record marc4jRecord = ReadMarc.read(path.toString()).get(0);
    FunctionalAnalysis analysis = new FunctionalAnalysis(new String[]{});
    BibliographicRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord);
    analysis.processRecord(marcRecord, 1);
    FrbrFunctionLister lister = analysis.getFrbrFunctionLister();
    assertNotNull(lister);
    assertNotNull(lister.getHistogram().get(FRBRFunction.DiscoverySearch));
    Counter<FunctionValue> counter = lister.getHistogram().get(FRBRFunction.DiscoverySearch);
    assertEquals(1, counter.keys().size());
    FunctionValue value = (FunctionValue) counter.keys().toArray()[0];
    assertEquals(7, value.getCount());
    assertEquals(0.015086206896551725, value.getPercent(), 0.00001);
  }
}
