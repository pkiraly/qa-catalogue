package de.gwdg.metadataqa.marc.analysis.functional;

import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.utils.pica.crosswalk.Crosswalk;
import de.gwdg.metadataqa.marc.utils.pica.crosswalk.PicaMarcCrosswalkReader;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PicaFrbrFunctionLister extends FrbrFunctionLister {
  private Map<FRBRFunction, Map<String, List<String>>> pathByFunctionExtended;

  public PicaFrbrFunctionLister() {
    super();
  }

  public void prepareBaseline() {
    // First get the MARC21 paths for each function
    Marc21FrbrFunctionLister marc21FrbrFunctionLister = new Marc21FrbrFunctionLister(MarcVersion.MARC21);
    marc21FrbrFunctionLister.prepareBaseline();


    Map<FRBRFunction, List<String>> marcPathByFunction = marc21FrbrFunctionLister.getPathByFunction();

    pathByFunctionExtended = new EnumMap<>(FRBRFunction.class);
    pathByFunction = new EnumMap<>(FRBRFunction.class);
    functionByPath = new HashMap<>();

    // Iterate over all functions as entries with their respective MARC21 paths
    // And then for each MARC21 path, get its PICA equivalent (or equivalents, as the crosswalk may return more than one)
    for (Map.Entry<FRBRFunction, List<String>> entry : marcPathByFunction.entrySet()) {
      for (String address : entry.getValue()) {
        if (!address.contains("$")) {
          continue;
        }

        // Only if the address (MARC21 path) contains a subfield indicator ($) we try to get the PICA equivalent

        FRBRFunction function = entry.getKey();
        String key = address.replace("$", " $");

        // The PICA equivalents are obtained from the crosswalk. From the MARC21 and PICA nature, it is possible
        // that the crosswalk returns more than one PICA equivalent for a single MARC21 path (or none at all).
        for (Crosswalk crosswalk : PicaMarcCrosswalkReader.lookupMarc21(key)) {
          String pica = crosswalk.getPica();
          pathByFunctionExtended.putIfAbsent(function, new HashMap<>());
          pathByFunctionExtended.get(function).putIfAbsent(pica, new ArrayList<>());
          pathByFunctionExtended.get(function).get(pica).add(crosswalk.getPicaUf());

          pathByFunction.putIfAbsent(function, new ArrayList<>());
          pathByFunction.get(function).add(pica + crosswalk.getPicaUf());

          String picaPath = pica + crosswalk.getPicaUf();
          functionByPath.putIfAbsent(picaPath, new ArrayList<>());
          functionByPath.get(pica + crosswalk.getPicaUf()).add(function);
        }
      }
    }
  }

  public Map<FRBRFunction, Map<String, List<String>>> getPathByFunctionExtended() {
    return pathByFunctionExtended;
  }
}
