package de.gwdg.metadataqa.marc.utils.pica.crosswalk;

import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.utils.FrbrFunctionLister;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PicaMarcCrosswalkReaderTest {

  @Test
  public void test() {
    List<Crosswalk> mapping = PicaMarcCrosswalkReader.read();
    assertEquals(1319, mapping.size());
  }

  @Test
  public void testLoad() {
    FrbrFunctionLister lister = new FrbrFunctionLister(MarcVersion.MARC21);
    Map<FRBRFunction, List<String>> picaFunctions = lister.getPicaPathByFunction();
    assertEquals(11, picaFunctions.size());
    assertEquals(167, picaFunctions.get(FRBRFunction.DiscoveryObtain).size());
    assertEquals(178, picaFunctions.get(FRBRFunction.DiscoverySearch).size());
    assertEquals(103, picaFunctions.get(FRBRFunction.DiscoverySelect).size());
    assertEquals(353, picaFunctions.get(FRBRFunction.DiscoveryIdentify).size());
    assertEquals(  4, picaFunctions.get(FRBRFunction.ManagementDisplay).size());
    // assertEquals( 26, picaFunctions.get(FRBRFunction.ManagementSort).size());
    assertEquals( 16, picaFunctions.get(FRBRFunction.ManagementProcess).size());
    assertEquals(  8, picaFunctions.get(FRBRFunction.ManagementIdentify).size());
    assertEquals(  3, picaFunctions.get(FRBRFunction.UseRestrict).size());
    assertEquals(  5, picaFunctions.get(FRBRFunction.UseInterpret).size());
    assertEquals( 17, picaFunctions.get(FRBRFunction.UseManage).size());
    assertEquals(  4, picaFunctions.get(FRBRFunction.UseOperate).size());
  }
}
