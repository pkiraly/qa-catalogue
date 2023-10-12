package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import org.marc4j.marc.Record;

import java.util.ArrayList;
import java.util.List;

public class IteratorResponse {
    private Record marc4jRecord = null;
    private List<ValidationError> errors = new ArrayList<>();
    private boolean hasBlockingError = false;

    public IteratorResponse() {
    }

    public Record getMarc4jRecord() {
        return marc4jRecord;
    }

    public void setMarc4jRecord(Record marc4jRecord) {
        this.marc4jRecord = marc4jRecord;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public void addError(String recordId, String error) {
        this.errors.add(new ValidationError(recordId, "record", ValidationErrorType.RECORD_PARSING, error,null));
    }

    public void addError(String recordId, String tag, String error) {
        this.errors.add(new ValidationError(recordId, tag, ValidationErrorType.RECORD_PARSING, error,null));
    }

    public void setErrors(List<ValidationError> errors) {
        this.errors = errors;
    }

    public boolean hasBlockingError() {
        return hasBlockingError;
    }

    public void hasBlockingError(boolean hasBlockingError) {
        this.hasBlockingError = hasBlockingError;
    }
}
