package de.gwdg.metadataqa.marc.codes;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class StandardIdentifier {
  private String code;
  private String name;
  private String url;
  private String organization;
  private String language;
  private String note;

  public StandardIdentifier(String code, String name, String url, String organization, String language, String note) {
    this.code = code;
    this.name = name;
    this.url = url;
    this.organization = organization;
    this.language = language;
    this.note = note;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }

  public String getOrganization() {
    return organization;
  }

  public String getLanguage() {
    return language;
  }

  public String getNote() {
    return note;
  }
}
