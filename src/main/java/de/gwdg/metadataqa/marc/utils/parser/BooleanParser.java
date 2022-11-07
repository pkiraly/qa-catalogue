package de.gwdg.metadataqa.marc.utils.parser;

import java.util.Deque;
import java.util.LinkedList;

/**
 * A top level parser which parser string such as
 *    .... AND ... AND (... OR ...)
 * It takes care of operators and parenthesis, but does not parse the content of individual units.
 */
public class BooleanParser {

  String token = "";
  String last = "";
  int start = 0;
  boolean skippedOp = false;
  String input;
  Deque<Integer> parens = new LinkedList<>();

  private BooleanParser(String input) {
    this.input = input;
  }

  public static BooleanContainer<String> parse(String input) {
    BooleanParser parser = new BooleanParser(input);
    return parser.parse();
  }

  private BooleanContainer<String> parse() {
    BooleanContainer<String> root = new BooleanContainer<>();
    for (int i = 0; i < input.length(); i++) {
      String n = input.substring(i, i+1);
      if (n.equals("&") && last.equals("&")) {
        processOp(i, root, BooleanContainer.Op.AND);
      } else if (n.equals("|") && last.equals("|")) {
        processOp(i, root, BooleanContainer.Op.OR);
      } else if (n.equals("(")) {
        parens.add(i);
      } else if (n.equals(")")) {
        if (parens.isEmpty())
          throw new IllegalArgumentException("Error: closing parens without opening one: " + input);
        parens.pollLast();
      }
      last = n;
    }
    token = input.substring(start).trim();
    addChild(root, token);
    if (!parens.isEmpty())
      throw new IllegalArgumentException("Error: opening parens without closing one: " + input);
    return root;
  }

  private void processOp(int i, BooleanContainer<String> root, BooleanContainer.Op and) {
    if (parens.isEmpty()) {
      if (root.getOp() == null)
        root.setOp(and);
      addChild(root, input.substring(start, i -1).trim());
      start = i +1;
      skippedOp = false;
    } else {
      skippedOp = true;
    }
  }

  private void addChild(BooleanContainer<String> root, String token) {
    if (skippedOp && !(token.startsWith("(") && token.endsWith(")")))
      throw new IllegalArgumentException("internal operator with imperfect parenthes: " + input);

    BooleanContainer<String> child = (token.startsWith("(") && token.endsWith(")"))
      ? parse(token.substring(1, token.length()-1))
      : new BooleanContainer<>(token);
    if (child.getValue() != null && child.getOp() == null && root.getOp() == null)
      root.setValue(child.getValue());
    else
      root.getChildren().add(child);
  }
}
