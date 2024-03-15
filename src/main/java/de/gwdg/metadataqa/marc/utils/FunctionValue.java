package de.gwdg.metadataqa.marc.utils;

import java.util.Objects;

public class FunctionValue {
  private int count = 0;
  private double percentage = 0.0;

  public FunctionValue(int count, double percent) {
    this.count = count;
    this.percentage = percent;
  }

  public FunctionValue() {}

  public void count() {
    this.count += 1;
  }

  public void calculatePercentage(int total) {
    percentage = count * 1.0 / total;
  }

  public void add(FunctionValue other) {
    this.count += other.count;
    this.percentage += other.percentage;
  }

  public int getCount() {
    return count;
  }

  public double getPercentage() {
    return percentage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FunctionValue that = (FunctionValue) o;
    return count == that.count && Double.compare(that.percentage, percentage) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(count, percentage);
  }
}
