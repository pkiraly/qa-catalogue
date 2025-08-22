package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.analysis.functional.FrbrFunctionLister;
import de.gwdg.metadataqa.marc.analysis.functional.Marc21FrbrFunctionLister;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FrbrFunctionListerTest {

  @Test
  public void testGetMarcPathByfunction() {
    FrbrFunctionLister lister = new Marc21FrbrFunctionLister(MarcVersion.MARC21);
    Map<FRBRFunction, List<String>> functions = lister.getPathByFunction();
    assertEquals(12,  functions.size());
    assertEquals(455, functions.get(FRBRFunction.DiscoveryObtain).size());
    assertEquals(464, functions.get(FRBRFunction.DiscoverySearch).size());
    assertEquals(349, functions.get(FRBRFunction.DiscoverySelect).size());
    assertEquals(969, functions.get(FRBRFunction.DiscoveryIdentify).size());
    assertEquals( 80, functions.get(FRBRFunction.ManagementDisplay).size());
    assertEquals( 26, functions.get(FRBRFunction.ManagementSort).size());
    assertEquals(547, functions.get(FRBRFunction.ManagementProcess).size());
    assertEquals(493, functions.get(FRBRFunction.ManagementIdentify).size());
    assertEquals( 24, functions.get(FRBRFunction.UseRestrict).size());
    assertEquals(118, functions.get(FRBRFunction.UseInterpret).size());
    assertEquals(103, functions.get(FRBRFunction.UseManage).size());
    assertEquals( 63, functions.get(FRBRFunction.UseOperate).size());
  }
}
