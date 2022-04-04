package de.gwdg.metadataqa.marc.model.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ErrorsCollector implements Serializable {

  private static final long serialVersionUID = 1905122041950251207L;

  private List<ValidationError> errors;

  public ErrorsCollector() {
    errors = new ArrayList<>();
  }

  public void add(String recordId,
                  String marcPath,
                  ValidationErrorType type,
                  String message,
                  String url) {
    errors.add(new ValidationError(recordId, marcPath, type, message, url));
  }

  public List<ValidationError> getErrors() {
    return errors;
  }

  public void addAll(List<ValidationError> otherErrors) {
    errors.addAll(otherErrors);
  }
}
