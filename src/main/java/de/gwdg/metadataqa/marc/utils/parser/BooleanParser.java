package de.gwdg.metadataqa.marc.utils.parser;

import java.util.Deque;
import java.util.LinkedList;

/**
 * A top level parser which parser string such as
 *    .... AND ... AND (... OR ...)
 * It takes care of oerators and parenthesis, but does not parse the content of individual units.
 */
public class BooleanParser {

  private BooleanParser() {}

  public static BooleanContainer parse(String input) {
    String token = "";
    String last = "";
    int start = 0;
    boolean skippedOp = false;
    BooleanContainer root = new BooleanContainer();
    Deque<Integer> parens = new LinkedList<>();
    for (int i = 0; i < input.length(); i++) {
      String n = input.substring(i, i+1);
      if (n.equals("&") && last.equals("&")) {
        if (parens.isEmpty()) {
          token = input.substring(start, i-1).trim();
          if (root.getOp() == null) {
            root.setOp(BooleanContainer.Op.AND);
          }
          addChild(root, token);
          start = i+1;
          skippedOp = false;
        } else {
          skippedOp = true;
        }
      } else if (n.equals("|") && last.equals("|")) {
        if (parens.isEmpty()) {
          token = input.substring(start, i-1).trim();
          if (root.getOp() == null) {
            root.setOp(BooleanContainer.Op.OR);
          }
          addChild(root, token);
          start = i+1;
          skippedOp = false;
        } else {
          skippedOp = true;
        }
      } else if (n.equals("(")) {
        parens.add(i);
      } else if (n.equals(")")) {
        if (parens.isEmpty()) {
          System.err.println("Error: closing parens without opening one: " + input);
        }
        parens.pollLast();
      }
      last = n;
    }
    token = input.substring(start).trim();
    addChild(root, token);
    if (!parens.isEmpty()) {
      System.err.println("Error: opening parens without closing one: " + input);
    }
    return root;
  }

  private static void addChild(BooleanContainer root, String token) {
    BooleanContainer child = (token.startsWith("(") && token.endsWith(")"))
      ? parse(token.substring(1, token.length()-1))
      : new BooleanContainer(token);
    root.getChildren().add(child);
  }
}
