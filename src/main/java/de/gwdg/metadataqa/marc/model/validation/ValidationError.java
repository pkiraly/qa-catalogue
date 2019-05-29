package de.gwdg.metadataqa.marc.model.validation;

import java.io.Serializable;

public class ValidationError implements Serializable {

  private String recordId;
  private String marcPath;
  private ValidationErrorType type;
  private String message;
  private String url;
  private boolean trimId = false;

  public ValidationError(String recordId, String marcPath, ValidationErrorType type, String message, String url) {
    this.recordId = recordId;
    this.marcPath = marcPath;
    this.type = type;
    this.message = message;
    this.url = url;
  }

  public String getRecordId() {
    return trimId ? recordId.trim() : recordId;
  }

  public String getMarcPath() {
    return marcPath;
  }

  public ValidationErrorType getType() {
    return type;
  }

  public String getMessage() {
    return message;
  }

  public String getUrl() {
    return url;
  }

  public boolean getTrimId() {
    return trimId;
  }

  public void setTrimId(boolean trimId) {
    this.trimId = trimId;
  }

  @Override
  public String toString() {
    return "ValidationError{" +
      "recordId='" + recordId + '\'' +
      ", marcPath='" + marcPath + '\'' +
      ", type=" + type +
      ", message='" + message + '\'' +
      ", url='" + url + '\'' +
      '}';
  }

  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }
}
