package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.tags.control.Control003Definition;

import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Control003 extends MarcControlField implements Extractable {

  private static final Logger logger = Logger.getLogger(Control003.class.getCanonicalName());

  public Control003(String content) {
    super(Control003Definition.getInstance(), content);
    processContent();
  }

  protected void processContent() {
    // do nothing, this string should not be parsed
  }

  @Override
  public String toString() {
    return "Control003{" +
      "content='" + content + '\'' +
    '}';
  }
}