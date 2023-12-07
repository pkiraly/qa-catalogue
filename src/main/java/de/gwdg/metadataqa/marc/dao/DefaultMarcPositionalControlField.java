package de.gwdg.metadataqa.marc.dao;

import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.structure.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.structure.DefaultControlFieldDefinition;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultMarcPositionalControlField extends MarcPositionalControlField {
  private static final Logger logger = Logger.getLogger(DefaultMarcPositionalControlField.class.getCanonicalName());

  public DefaultMarcPositionalControlField(DefaultControlFieldDefinition definition, String content) {
    super(definition, content);
    processContent();
  }

  public DefaultMarcPositionalControlField(ControlFieldDefinition definition, String content, Leader.Type recordType) {
    super(definition, content, recordType);
  }

  @Override
  protected void processContent() {
    for (ControlfieldPositionDefinition position : ((DefaultControlFieldDefinition) definition).getPositions()) {
      int end = Math.min(content.length(), position.getPositionEnd());
      try {
        String value = content.substring(position.getPositionStart(), end);
        ControlValue controlValue = new ControlValue(position, value);
        valuesList.add(controlValue);
      } catch (StringIndexOutOfBoundsException e) {
        logger.log(
          Level.SEVERE,
          "Problem with processing {5} (\"{0}\"). The content length is only {1} while reading position @{2}-{3} (for {4})",
          new Object[]{
            content, content.length(), position.getPositionStart(), position.getPositionEnd(), position.getLabel(), definition.getTag()
          });
      }
    }
  }

  @Override
  public ControlfieldPositionDefinition getSubfieldByPosition(Integer charStart) {
    return null;
  }
}
