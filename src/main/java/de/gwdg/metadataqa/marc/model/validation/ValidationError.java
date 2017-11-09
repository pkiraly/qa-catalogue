package de.gwdg.metadataqa.marc.model.validation;

public class ValidationError {

	private String recordId;
	private String marcPath;
	private ValidationErrorType type;
	private String message;
	private String url;

	public ValidationError(String recordId, String marcPath, ValidationErrorType type, String message, String url) {
		this.recordId = recordId;
		this.marcPath = marcPath;
		this.type = type;
		this.message = message;
		this.url = url;
	}

	public String getRecordId() {
		return recordId;
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
}
