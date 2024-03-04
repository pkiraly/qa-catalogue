package de.gwdg.metadataqa.marc.analysis.thompsontraill;

import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.utils.pica.crosswalk.Crosswalk;
import de.gwdg.metadataqa.marc.utils.pica.crosswalk.PicaMarcCrosswalkReader;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class PicaThompsonTraillAnalysis extends ThompsonTraillAnalysis {

  private Map<ThompsonTraillFields, List<String>> thompsonTraillTagMap;

  @Override
  public List<Integer> getScores(BibliographicRecord marcRecord) {
    var ttScores = new ThompsonTraillScores();

    Map<ThompsonTraillFields, List<String>> thompsonTraillTagsMap = getThompsonTraillTagsMap();
    for (Map.Entry<ThompsonTraillFields, List<String>> entry : thompsonTraillTagsMap.entrySet()) {
      ttScores.set(entry.getKey(), countFields(marcRecord, entry.getValue()));
    }

    ttScores.calculateTotal();
    return ttScores.asList();
  }

  private int countFields(BibliographicRecord marcRecord, List<String> tags) {
    var counter = 0;
    for (String tag : tags) {
      if (tagExists(marcRecord, tag)) {
        counter += marcRecord.getDatafield(tag).size();
      }
    }
    return counter;
  }

  public Map<ThompsonTraillFields, List<String>> getThompsonTraillTagsMap() {
    if (thompsonTraillTagMap == null)
      initializeThompsonTrailTags();

    return thompsonTraillTagMap;
  }

  private void initializeThompsonTrailTags() {
    thompsonTraillTagMap = new LinkedHashMap<>();
    Map<ThompsonTraillFields, List<String>> marc21ThomasTraillMap =
        (new Marc21ThompsonTraillAnalysis()).getThompsonTraillTagsMap();

    for (Map.Entry<ThompsonTraillFields, List<String>> entry : marc21ThomasTraillMap.entrySet()) {
      ThompsonTraillFields category = entry.getKey();
      thompsonTraillTagMap.put(category, new ArrayList<>());

      for (String marcEntry : entry.getValue()) {
        mapPicaEntryFromMarc21(marcEntry, category);
      }
    }
  }

  private void mapPicaEntryFromMarc21(String marc21MapEntry, ThompsonTraillFields category) {
    for (Crosswalk crosswalk : PicaMarcCrosswalkReader.lookupMarc21Field(marc21MapEntry)) {
      if (!thompsonTraillTagMap.get(category).contains(crosswalk.getPica())) {
        thompsonTraillTagMap.get(category).add(crosswalk.getPica());
      }
    }
  }
}
