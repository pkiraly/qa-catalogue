package de.gwdg.metadataqa.marc.cli.plugin;

import de.gwdg.metadataqa.marc.cli.parameters.CompletenessParameters;
import de.gwdg.metadataqa.marc.dao.MarcRecord;

import java.util.regex.Pattern;

public class PicaCompletenessPlugin implements CompletenessPlugin {
  private final CompletenessParameters parameters;
  private final String field;
  private final String subfield;

  public PicaCompletenessPlugin(CompletenessParameters parameters) {
    this.parameters = parameters;
    String[] parts = parameters.getPicaRecordTypeField().split(Pattern.quote(parameters.getPicaSubfieldSeparator()));
    field = parts[0];
    subfield = parts[1];
  }

  @Override
  public String getDocumentType(MarcRecord marcRecord) {
    return marcRecord.getDatafield(field).get(0).getSubfield(subfield).get(0).getValue();
  }
}
