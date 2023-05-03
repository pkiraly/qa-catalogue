package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.analysis.bl.UseCase;
import de.gwdg.metadataqa.marc.analysis.bl.Element;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.gwdg.metadataqa.marc.analysis.bl.Band.BASIC;
import static de.gwdg.metadataqa.marc.analysis.bl.Band.DEFICIENT;
import static de.gwdg.metadataqa.marc.analysis.bl.Band.SATISFACTORY;
import static de.gwdg.metadataqa.marc.analysis.bl.Band.EFFECTIVE;

public class BLClassifier implements Classifier {

  private static final Logger logger = Logger.getLogger(BLClassifier.class.getCanonicalName());

  private List<UseCase> basicUseCases = new ArrayList<>();
  private List<UseCase> satisfactoryUseCases = new ArrayList<>();
  private List<UseCase> effectiveUseCases = new ArrayList<>();

  public BLClassifier() {
    for (UseCase useCase : UseCase.values()) {
      if (useCase.getBand().equals(BASIC))
        basicUseCases.add(useCase);
      else if (useCase.getBand().equals(SATISFACTORY))
        satisfactoryUseCases.add(useCase);
      else if (useCase.getBand().equals(EFFECTIVE))
        effectiveUseCases.add(useCase);
    }
  }

  @Override
  public String classify(BibliographicRecord marcRecord) {
    String level = DEFICIENT.name();

    for (UseCase useCase : basicUseCases)
      if (useCase.getStatus().equals("Mandatory") && !useCase.getElements().isEmpty() && !satisfy(marcRecord, useCase))
        return level;
    level = BASIC.name();

    for (UseCase useCase : satisfactoryUseCases)
      if (!useCase.getElements().isEmpty() && !satisfy(marcRecord, useCase))
        return level;
    level = SATISFACTORY.name();

    for (UseCase useCase : effectiveUseCases)
      if (!useCase.getElements().isEmpty() && !satisfy(marcRecord, useCase))
        return level;
    level = EFFECTIVE.name();
    return level;
  }

  private boolean satisfy(BibliographicRecord marcRecord, UseCase useCase) {
    for (Element element : useCase.getElements()) {
      if (marcRecord.hasDatafield(element.getTag())) {
        if (element.getSubfield() == null) {
          return true;
        } else {
          for (DataField field : marcRecord.getDatafield(element.getTag())) {
            List<MarcSubfield> subfields = field.getSubfield(element.getSubfield());
            if (subfields != null && !subfields.isEmpty())
              return true;
          }
        }
      }
    }
    logger.log(Level.INFO, "failed for {} ({} -- {} -- {})",
      new Object[]{useCase.name(), useCase.getUseCase(), useCase.getEncoding(), useCase.getDataElelemntsNormalized()});
    return false;
  }
}
