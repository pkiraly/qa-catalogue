package de.gwdg.metadataqa.marc.utils;

import java.util.Objects;

public class FunctionValue {
  private int count = 0;
  private double percent = 0.0;

  public FunctionValue(int count, double percent) {
    this.count = count;
    this.percent = percent;
  }

  public FunctionValue() {}

  public void count() {
    this.count += 1;
  }

  public void calculatePercent(int total) {
    percent = count * 1.0 / total;
  }

  public void add(FunctionValue other) {
    this.count += other.count;
    this.percent += other.percent;
  }

  public int getCount() {
    return count;
  }

  public double getPercent() {
    return percent;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FunctionValue that = (FunctionValue) o;
    return count == that.count && Double.compare(that.percent, percent) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(count, percent);
  }
}
