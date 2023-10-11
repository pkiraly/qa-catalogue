package de.gwdg.metadataqa.marc.utils.marcreader;

import de.gwdg.metadataqa.marc.model.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public abstract class ErrorAwareReader {
    protected List<ValidationError> errors = new ArrayList<>();
    public List<ValidationError> getErrors() {
        return errors;
    }
    protected void addError(ValidationError error) {
        errors.add(error);
    }
}
