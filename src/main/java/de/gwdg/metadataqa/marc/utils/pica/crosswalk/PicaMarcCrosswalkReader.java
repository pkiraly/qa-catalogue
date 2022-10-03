package de.gwdg.metadataqa.marc.utils.pica.crosswalk;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStreamReader;
import java.util.List;

public class PicaMarcCrosswalkReader {
  private static List<Crosswalk> mapping;

  public static List<Crosswalk> read() {
    if (mapping == null)
      initialize();

    return mapping;
  }

  private static void initialize() {
    InputStreamReader streamReader = new InputStreamReader(PicaMarcCrosswalkReader.class.getResourceAsStream("/pica/pica-marc.tsv"));
    CSVReader reader = new CSVReaderBuilder(streamReader)
      .withCSVParser(new CSVParserBuilder().withSeparator('\t').build())
      .withSkipLines(1)
      .build();
    CsvToBean<Crosswalk> csvToBean = new CsvToBeanBuilder<Crosswalk>(reader).withType(Crosswalk.class).build();

    mapping = csvToBean.parse();
  }
}
