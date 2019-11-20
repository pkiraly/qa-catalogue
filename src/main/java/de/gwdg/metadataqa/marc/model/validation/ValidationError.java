package de.gwdg.metadataqa.marc.model.validation;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class ValidationError implements Serializable {

  private Integer id = null;
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

  public Integer getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this.hashCode() == o.hashCode())
      return true;

    if (!(o instanceof ValidationError)) return false;

    ValidationError that = (ValidationError) o;

    return new EqualsBuilder()
      .append(getMarcPath(), that.getMarcPath())
      .append(getType(), that.getType())
      .append(getMessage(), that.getMessage())
      .append(getUrl(), that.getUrl())
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .append(getMarcPath())
      .append(getType())
      .append(getMessage())
      .append(getUrl())
      .toHashCode();
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
