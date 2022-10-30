package de.gwdg.metadataqa.marc.utils.pica;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VocabularyPattern {
  private List<String> codes = new ArrayList<>();
  private Pattern pattern;

  public VocabularyPattern(String codes, String pattern) {
    this.codes = transform(codes);
    this.pattern = Pattern.compile(pattern);
  }

  private List<String> transform(String _codes) {
    List<String> codes = new ArrayList<>();
    if (_codes.length() == 1) {
      codes.add(_codes);
    } else {
      if (_codes.startsWith("[") && _codes.endsWith("]"))
        for (int i = 1; i < _codes.length() - 1; i++)
          codes.add(_codes.substring(i, i+1));
    }
    return codes;
  }

  public boolean fitsSubfield(String code) {
    return codes.contains(code);
  }

  public String extract(String s) {
    Matcher matcher = pattern.matcher(s);
    if (matcher.matches())
      return matcher.group(1);
    return null;
  }

  @Override
  public String toString() {
    return "VocabularyPattern{" +
      "codes=" + codes +
      ", pattern=" + pattern +
      '}';
  }

  public List<String> getCodes() {
     return codes;
  }
}
