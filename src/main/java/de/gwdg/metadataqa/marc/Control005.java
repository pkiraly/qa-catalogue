package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.ControlField;
import de.gwdg.metadataqa.marc.definition.ControlSubfieldDefinition;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.tags.control.Control005Definition;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Control005  extends MarcControlField implements Extractable {

  private static final Logger logger = Logger.getLogger(Control005.class.getCanonicalName());
  private static final Pattern DATE_TIME = Pattern.compile("^(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})\\.(\\d)$");

  public Control005(String content) {
    super(Control005Definition.getInstance(), content);
    process();
  }

  private void process() {
  }

  @Override
  public String toString() {
    return "Control005{" +
        "content='" + content + '\'' +
        '}';
  }
}