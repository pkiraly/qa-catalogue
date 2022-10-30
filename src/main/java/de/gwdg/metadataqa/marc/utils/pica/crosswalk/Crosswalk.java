package de.gwdg.metadataqa.marc.utils.pica.crosswalk;

import com.opencsv.bean.CsvBindByPosition;

public class Crosswalk {
  @CsvBindByPosition(position = 0)
  private String pica;
  @CsvBindByPosition(position = 1)
  private String picaUf;
  @CsvBindByPosition(position = 2)
  private String marc21;

  public String getPica() {
    return pica;
  }

  public void setPica(String pica) {
    this.pica = pica;
  }

  public String getPicaUf() {
    return picaUf;
  }

  public void setPicaUf(String picaUf) {
    this.picaUf = picaUf;
  }

  public String getMarc21() {
    return marc21;
  }

  public void setMarc21(String marc21) {
    this.marc21 = marc21;
  }

  @Override
  public String toString() {
    return "Crosswalk{" +
      "pica='" + pica + '\'' +
      ", picaUf='" + picaUf + '\'' +
      ", marc21='" + marc21 + '\'' +
      '}';
  }
}
