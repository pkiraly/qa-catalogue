package de.gwdg.metadataqa.marc.dao;

import de.gwdg.metadataqa.marc.Extractable;
import de.gwdg.metadataqa.marc.definition.tags.control.Control003Definition;

import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Control003 extends SimpleControlField implements Extractable {

  public Control003(String content) {
    super(Control003Definition.getInstance(), content);
  }

  @Override
  public String toString() {
    return "Control003{" +
      "content='" + content + '\'' +
    '}';
  }
}