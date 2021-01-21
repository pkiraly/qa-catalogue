package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static de.gwdg.metadataqa.marc.Utils.createRow;
import static org.junit.Assert.assertEquals;

public class FrbrFunctionListerTest {

  @Test
  public void testGetMarcPathByfunction() {
    FrbrFunctionLister lister = new FrbrFunctionLister(MarcVersion.MARC21);
    Map<FRBRFunction, List<String>> functions = lister.getMarcPathByfunction();
    assertEquals(12,  functions.size());
    assertEquals(466, functions.get(FRBRFunction.DiscoveryObtain).size());
    assertEquals(464, functions.get(FRBRFunction.DiscoverySearch).size());
    assertEquals(360, functions.get(FRBRFunction.DiscoverySelect).size());
    assertEquals(976, functions.get(FRBRFunction.DiscoveryIdentify).size());
    assertEquals( 80, functions.get(FRBRFunction.ManagementDisplay).size());
    assertEquals( 26, functions.get(FRBRFunction.ManagementSort).size());
    assertEquals(544, functions.get(FRBRFunction.ManagementProcess).size());
    assertEquals(491, functions.get(FRBRFunction.ManagementIdentify).size());
    assertEquals( 24, functions.get(FRBRFunction.UseRestrict).size());
    assertEquals(118, functions.get(FRBRFunction.UseInterpret).size());
    assertEquals(107, functions.get(FRBRFunction.UseManage).size());
    assertEquals( 67, functions.get(FRBRFunction.UseOperate).size());
  }
}
