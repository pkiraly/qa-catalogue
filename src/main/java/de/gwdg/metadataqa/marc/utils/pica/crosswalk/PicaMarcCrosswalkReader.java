package de.gwdg.metadataqa.marc.utils.pica.crosswalk;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PicaMarcCrosswalkReader {
  private static List<Crosswalk> mapping;
  private static Map<String, List<Crosswalk>> marcIndex;
  private static Map<String, List<Crosswalk>> picaIndex;

  public static List<Crosswalk> read() {
    if (mapping == null)
      initialize();

    return mapping;
  }

  public static List<Crosswalk> lookupMarc21(String key) {
    return marcIndex.getOrDefault(key, new ArrayList<>());
  }

  public static List<Crosswalk> lookupPica(String key) {
    return picaIndex.getOrDefault(key, new ArrayList<>());
  }

  private static void initialize() {
    InputStreamReader streamReader = new InputStreamReader(PicaMarcCrosswalkReader.class.getResourceAsStream("/pica/pica-marc.tsv"));
    CSVReader reader = new CSVReaderBuilder(streamReader)
      .withCSVParser(new CSVParserBuilder().withSeparator('\t').build())
      .withSkipLines(1)
      .build();
    CsvToBean<Crosswalk> csvToBean = new CsvToBeanBuilder<Crosswalk>(reader).withType(Crosswalk.class).build();

    mapping = csvToBean.parse();

    index();
  }

  private static void index() {
    marcIndex = new HashMap<>();
    picaIndex = new HashMap<>();
    for (Crosswalk crosswalk : mapping) {
      if (crosswalk.getMarc21() != null) {
        if (!marcIndex.containsKey(crosswalk.getMarc21()))
          marcIndex.put(crosswalk.getMarc21(), new ArrayList<>());
        marcIndex.get(crosswalk.getMarc21()).add(crosswalk);
      }
      if (crosswalk.getPica() != null) {
        if (!picaIndex.containsKey(crosswalk.getPica()))
          picaIndex.put(crosswalk.getPica(), new ArrayList<>());
        picaIndex.get(crosswalk.getPica()).add(crosswalk);
      }
    }
  }
}
