package de.gwdg.metadataqa.marc.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BasicStatistics {
  private final Map<Integer, Integer> histogram;
  private Integer min = null;
  private Integer max = null;
  private Double mean = null;

  public BasicStatistics(Map<Integer, Integer> histogram) {
    this.histogram = histogram;
    calculate();
  }

  private void calculate() {
    double count = 0;
    double sum = 0;
    for (Map.Entry<Integer, Integer> entry : histogram.entrySet()) {
      if (min == null) {
        min = entry.getKey();
      } else {
        min = Math.min(min, entry.getKey());
      }

      if (max == null) {
        max = entry.getKey();
      } else {
        max = Math.max(max, entry.getKey());
      }

      count += entry.getValue();
      sum += entry.getKey() * entry.getValue();
    }
    mean = sum / count;
  }

  public Integer getMin() {
    return min;
  }

  public Integer getMax() {
    return max;
  }

  public Double getMean() {
    return mean;
  }

  public String formatHistogram() {
    List<String> histogramList = new ArrayList<>();
    for (Map.Entry histogram : histogram.entrySet()) {
      histogramList.add(histogram.getKey() + "=" + histogram.getValue());
    }
    return StringUtils.join(histogramList, "; ");
  }
}
