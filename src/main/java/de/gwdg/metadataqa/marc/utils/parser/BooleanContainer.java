package de.gwdg.metadataqa.marc.utils.parser;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BooleanContainer<T> {

  public enum Op{AND, OR}

  private Op op;
  private List<BooleanContainer<T>> children = new ArrayList<>();
  private T value;

  public BooleanContainer() {}

  public BooleanContainer(T value) {
    this.value = value;
  }

  public BooleanContainer(Op op, List<BooleanContainer<T>> children) {
    this.op = op;
    this.children = children;
  }

  public Op getOp() {
    return op;
  }

  public void setOp(Op op) {
    this.op = op;
  }

  public List<BooleanContainer<T>> getChildren() {
    return children;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public int size() {
    int size = 0;
    if (value != null)
      size++;
    if (children != null) {
      for (BooleanContainer child : children) {
        size += child.size();
      }
    }
    return size;
  }

  @Override
  public String toString() {
    List<String> props = new ArrayList<>();
    if (op != null)
      props.add("op=" + op);
    if (!children.isEmpty())
      props.add("children=" + children);
    if (value != null)
      props.add("value='" + value + '\'');
     return this.getClass().getSimpleName() + "{" + StringUtils.join(props, ", ") + '}';
  }
}
