package de.gwdg.metadataqa.marc.analysis.serial;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.UnimarcRecord;

import java.util.List;

/**
 * This class based on the class published in
 * Jamie Carlstone (2017) Scoring the Quality of E-Serials MARC Records Using Java,
 * Serials Review, 43:3-4, pp. 271-277, DOI: 10.1080/00987913.2017.1350525
 * <a href="https://www.tandfonline.com/doi/full/10.1080/00987913.2017.1350525">Available here</a>
 */
public class UnimarcSerial extends MarcSerial {

  public UnimarcSerial(UnimarcRecord marcRecord) {
    super(marcRecord);
  }

  public List<Integer> determineRecordQualityScore() {

    detectUnknownDate1();
    detectUnknownCountry();
    detectUnknownLanguage();
    // No authentication code
    // No encoding level checks
    // No 006 checks
    detectPublisher();
    detectPublisherRDA();
    detectPublicationFrequency();
    detectContentTypeRDA();
    detectDateOfPublication();
    // No source of description note
    detectLocSubjectHeadings();
    // Discard any that are not "o" for electronic.
    // This MUST be wrong as the field 008/23 can't be "o" for Continuing Resources.
    // So this part wasn't even considered for UNIMARC.
    detectInactiveTitles();
    // Not sure what's meant by deletion. Field 9xx are local in UNIMARC, which seems to be the case for MARC21 as well.
    // Not checking if the Date1 starts with 0, as its meaning is unclear.
    // No PPC checks because there's no 042 in UNIMARC.
    // Not checking encoding level 3 because there isn't a UNIMARC equivalent for it.

    scores.calculateTotal();
    return scores.asList();
  }

  private void detectUnknownDate1() {
    // Date 1 is unknown
    // The date in a UNIMARC record is located in the field 100$a~9-12
    List<String> subfieldValues = marcRecord.extract("100", "a");

    // Since there is supposed to be only one 100$a, we can use the first element
    if (subfieldValues.isEmpty() || subfieldValues.get(0) == null || subfieldValues.get(0).length() < 12) {
      return;
    }

    String date1 = subfieldValues.get(0).substring(9, 13);
    // It should be considered that some catalogues use different codes for blanks
    // MARC21 "uuuu" corresponds to "####" in UNIMARC when it's about continuing resources
    if (date1.equals("####")) {
      scores.set(SerialFields.DATE_1_UNKNOWN, -3);
    }
  }

  private void detectUnknownCountry() {
    // Country of publication is totally unknown
    // The country in a UNIMARC record is located in the field 102$a
    List<String> subfieldValues = marcRecord.extract("102", "a");
    if (subfieldValues.isEmpty()  || subfieldValues.get(0) == null) {
      return;
    }

    String country = subfieldValues.get(0);

    if (country.equalsIgnoreCase("xx")) {
      scores.set(SerialFields.COUNTRY_UNKNOWN, -1);
    }
  }

  private void detectUnknownLanguage() {
    // Publication language is totally unknown
    // The language in a UNIMARC record is located in the field 101$a
    List<String> subfieldValues = marcRecord.extract("101", "a");
    if (subfieldValues.isEmpty() || subfieldValues.get(0) == null) {
      return;
    }

    String language = subfieldValues.get(0);

    // It would make sense to even potentially consider this score if there's no $a subfield
    if (language.equalsIgnoreCase("xxx")) {
      scores.set(SerialFields.COUNTRY_UNKNOWN, -1);
    }
  }

  private void detectPublisher() {
    List<DataField> publisherFields = marcRecord.getDatafield("210");
    // Not repeatable, so we can use the first one
    DataField publisherField = publisherFields == null || publisherFields.isEmpty() ? null : publisherFields.get(0);

    if (isFieldEmpty(publisherField)) {
      scores.set(SerialFields.HAS_PUBLISHER_260, 1);
    }
  }

  private void detectPublisherRDA() {
    List<DataField> publisherFields = marcRecord.getDatafield("214");
    // Not repeatable, so we can use the first one
    DataField publisherField = publisherFields == null || publisherFields.isEmpty() ? null : publisherFields.get(0);

    if (isFieldEmpty(publisherField)) {
      scores.set(SerialFields.HAS_PUBLISHER_264, 1);
    }
  }

  private void detectPublicationFrequency() {
    List<DataField> frequencyFields = marcRecord.getDatafield("326");
    // Repeatable
    boolean hasFrequencyFields = fieldsHaveAnySubfields(frequencyFields);

    if (hasFrequencyFields) {
      scores.set(SerialFields.HAS_PUBLICATION_FREQUENCY_310, 1);
    }
  }

  private void detectContentTypeRDA() {
    // I assume this is the fields 181
    List<DataField> contentTypeFields = marcRecord.getDatafield("181");
    // Repeatable
    boolean hasContentTypeFields = fieldsHaveAnySubfields(contentTypeFields);

    if (hasContentTypeFields) {
      scores.set(SerialFields.HAS_CONTENT_TYPE_336, 1);
    }
  }

  private void detectDateOfPublication() {
    // This appears to be 207 in UNIMARC

    List<DataField> numberingFields = marcRecord.getDatafield("207");
    // Not repeatable, so we can use the first one
    DataField numberingField = numberingFields == null || numberingFields.isEmpty() ? null : numberingFields.get(0);

    if (isFieldEmpty(numberingField)) {
      scores.set(SerialFields.HAS_PUBLISHER_264, 1);
    }
  }

  private void detectLocSubjectHeadings() {
    // Has a Library of Congress subject heading (6XX_0)
    List<DataField> subjects = marcRecord.getSubjects();
    if (subjects.isEmpty()) {
      scores.set(SerialFields.HAS_NO_SUBJECT, -5);
    } else {
      scores.set(SerialFields.HAS_SUBJECT, subjects.size());
    }
  }

  private void detectInactiveTitles() {
    // The date2 in a UNIMARC record is located in the field 100$a~13-16
    List<String> subfieldValues = marcRecord.extract("100", "a");

    // Since there is supposed to be only one 100$a, we can use the first element
    if (subfieldValues.isEmpty() || subfieldValues.get(0) == null || subfieldValues.get(0).length() < 12) {
      return;
    }

    String date2 = subfieldValues.get(0).substring(13, 17);
    // Discard any that are not active titles
    if (!date2.equals("9999")) {
      scores.resetAll();
      scores.set(SerialFields.INACTIVE_TITLE, -100);
    }
  }

  private boolean isFieldEmpty(DataField field) {
    if (field == null) {
      return true;
    }
    // Since there are no flat fields, we make sure that there are subfields
    return field.getSubfields().isEmpty();
  }

  private boolean fieldsHaveAnySubfields(List<DataField> fields) {
    if (fields == null || fields.isEmpty()) {
      return false;
    }
    return fields.stream().anyMatch(field -> !field.getSubfields().isEmpty());
  }
}
