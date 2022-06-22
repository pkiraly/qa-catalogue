package de.gwdg.metadataqa.marc.utils.parser;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BooleanContainer {

  public enum Op{AND, OR};

  private Op op;
  private List<BooleanContainer> children = new ArrayList<>();
  private Object value;

  public BooleanContainer() {}

  public BooleanContainer(String value) {
    this.value = value;
  }

  public BooleanContainer(Op op, List<BooleanContainer> children) {
    this.op = op;
    this.children = children;
  }

  public Op getOp() {
    return op;
  }

  public void setOp(Op op) {
    this.op = op;
  }

  public List<BooleanContainer> getChildren() {
    return children;
  }

  public Object getValue() {
    return value;
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
     return this.getClass().getSimpleName() + "{" + StringUtils.join(props) + '}';
  }
}
