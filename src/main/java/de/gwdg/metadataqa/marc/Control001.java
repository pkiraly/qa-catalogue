package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.tags.control.Control001Definition;

import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Control001 extends MarcControlField implements Extractable {

  private static final Logger logger = Logger.getLogger(Control001.class.getCanonicalName());

  public Control001(String content) {
    super(Control001Definition.getInstance(), content);
    processContent();
  }

  protected void processContent() {
    // do nothing, this string should not be parsed
  }

  @Override
  public String getContent() {
    return content;
  }

  @Override
  public String toString() {
    return "Control001{" +
      "content='" + content + '\'' +
    '}';
  }

}
