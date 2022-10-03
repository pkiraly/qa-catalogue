package de.gwdg.metadataqa.marc.utils.pica.crosswalk;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.utils.FrbrFunctionLister;
import de.gwdg.metadataqa.marc.utils.MarcTagLister;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.gwdg.metadataqa.marc.definition.FRBRFunction.DiscoverySelect;
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
    Map<FRBRFunction, List<String>> marcFunctions = lister.getMarcPathByfunction();
    Map<FRBRFunction, List<String>> picaFunctions = new HashMap<>();
      List<Crosswalk> mapping = PicaMarcCrosswalkReader.read();
    for (Map.Entry<FRBRFunction, List<String>> entry : marcFunctions.entrySet()) {
      for (String address : entry.getValue()) {
        if (address.contains("$")) {
          String key = address.replace("$", " $");
          for (Crosswalk crosswalk : PicaMarcCrosswalkReader.lookupMarc21(key)) {
            if (!picaFunctions.containsKey(entry.getKey()))
              picaFunctions.put(entry.getKey(), new ArrayList<>());
            picaFunctions.get(entry.getKey()).add(crosswalk.getPica());
          }
        }
      }
    }
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
