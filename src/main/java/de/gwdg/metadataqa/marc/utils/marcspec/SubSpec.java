package de.gwdg.metadataqa.marc.utils.marcspec;

import org.apache.commons.lang3.StringUtils;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

public class SubSpec {

  private static final List<String> OPERATORS = Arrays.asList("?", "!", "=", "!=", "~", "!~");

  private SubTerm leftSubTerm;
  private String operator;
  private SubTerm rightSubTerm;

  public SubTerm getLeftSubTerm() {
    return leftSubTerm;
  }

  public void setLeftSubTerm(SubTerm leftSubTerm) {
    this.leftSubTerm = leftSubTerm;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    if (StringUtils.isNotBlank(operator)) {
      if (OPERATORS.contains(operator))
        this.operator = operator;
      else
        throw new InvalidParameterException("Operator not allowed: " + operator);
    } else {
      this.operator = "?";
    }
  }

  public SubTerm getRightSubTerm() {
    return rightSubTerm;
  }

  public void setRightSubTerm(SubTerm rightSubTerm) {
    this.rightSubTerm = rightSubTerm;
  }
}
