package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.utils.BibiographicPath;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPathParser;

public abstract class QACli {

  protected BibiographicPath groupBy = null;

  protected void initializeGroups(String groupBy, boolean isPica) {
    if (groupBy != null) {
      this.groupBy = isPica
        ? PicaPathParser.parse(groupBy)
        : null; // TODO: create it for MARC21
    }
  }
}
