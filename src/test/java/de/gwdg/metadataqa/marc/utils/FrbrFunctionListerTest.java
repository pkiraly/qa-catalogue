package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FrbrFunctionListerTest {

  @Test
  public void testGetMarcPathByfunction() {
    FrbrFunctionLister lister = new FrbrFunctionLister(MarcVersion.MARC21);
    Map<FRBRFunction, List<String>> functions = lister.getMarcPathByFunction();
    assertEquals(12,  functions.size());
    assertEquals(454, functions.get(FRBRFunction.DiscoveryObtain).size());
    assertEquals(464, functions.get(FRBRFunction.DiscoverySearch).size());
    assertEquals(348, functions.get(FRBRFunction.DiscoverySelect).size());
    assertEquals(968, functions.get(FRBRFunction.DiscoveryIdentify).size());
    assertEquals( 80, functions.get(FRBRFunction.ManagementDisplay).size());
    assertEquals( 26, functions.get(FRBRFunction.ManagementSort).size());
    assertEquals(545, functions.get(FRBRFunction.ManagementProcess).size());
    assertEquals(491, functions.get(FRBRFunction.ManagementIdentify).size());
    assertEquals( 24, functions.get(FRBRFunction.UseRestrict).size());
    assertEquals(118, functions.get(FRBRFunction.UseInterpret).size());
    assertEquals(103, functions.get(FRBRFunction.UseManage).size());
    assertEquals( 63, functions.get(FRBRFunction.UseOperate).size());
  }
}
