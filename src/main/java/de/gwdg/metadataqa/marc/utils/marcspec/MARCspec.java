package de.gwdg.metadataqa.marc.utils.marcspec;

import de.gwdg.metadataqa.marc.utils.marcspec.exception.InvalidMARCspecException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MARCspec {
  public static final Pattern hasSpacePattern = Pattern.compile("\\s");

  private Field field;

  private List<Subfield> subfields;

  public MARCspec(Field field) {
    this();
    this.field = field;
  }

  public MARCspec() {
    subfields = new ArrayList<>();
  }

  public MARCspec(String spec) {
    MARCspecParser parser = new MARCspecParser();
    MARCspec o = parser.parse(spec);
    field = o.getField();
    subfields = o.getSubfields();
  }

  public Field getField() {
    return field;
  }

  public void setField(Field field) {
    this.field = field;
  }

  public List<Subfield> getSubfields() {
    return subfields;
  }

  public void setSubfields(List<Subfield> subfields) {
    this.subfields = subfields;
  }

  public static Position[] validatePos(String positionInput) {
    int posLength = positionInput.length();

    if (1 > posLength) {
      throw new InvalidMARCspecException(InvalidMARCspecException.PR + InvalidMARCspecException.PR1, positionInput);
    }

    Pattern positionPattern = Pattern.compile("[^0-9\\-#]");
    if (positionPattern.matcher(positionInput).find()) { // alphabetic characters etc. are not valid
      throw new InvalidMARCspecException(InvalidMARCspecException.PR + InvalidMARCspecException.PR2, positionInput);
    }

    // something like 123- is not valid
    if (positionInput.endsWith("-")) {
      throw new InvalidMARCspecException(InvalidMARCspecException.PR + InvalidMARCspecException.PR3, positionInput);
    }

    // something like -123 ist not valid
    if (positionInput.startsWith("-")) {
      throw new InvalidMARCspecException(InvalidMARCspecException.PR + InvalidMARCspecException.PR4, positionInput);
    }

    String[] positions = positionInput.split("-");
    // only one - is allowed
    if (positions.length > 2) {
      throw new InvalidMARCspecException(InvalidMARCspecException.PR + InvalidMARCspecException.PR5, positionInput);
    }

    // Here, removed null assignment to an out-of-bounds index positions[1]

    Position[] indexPositions = new Position[positions.length];
    for (int i = 0; i<positions.length; i++) {
      indexPositions[i] = new Position(positions[i]);
    }

    return indexPositions;
  }

  public void addSubfield(Subfield subfield) {
    subfields.add(subfield);
  }

  @Override
  public String toString() {
    return "MARCspec{" +
      "field=" + field +
      ", subfields=" + subfields +
      '}';
  }
}
