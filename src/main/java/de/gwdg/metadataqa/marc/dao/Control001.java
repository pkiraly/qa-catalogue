package de.gwdg.metadataqa.marc.dao;

import de.gwdg.metadataqa.marc.Extractable;
import de.gwdg.metadataqa.marc.definition.tags.control.Control001Definition;

/**
 *
 */
public class Control001 extends SimpleControlField implements Extractable {

  public Control001(String content) {
    super(Control001Definition.getInstance(), content);
  }

  @Override
  public String toString() {
    return "Control001{" +
      "content='" + content + '\'' +
    '}';
  }
}
