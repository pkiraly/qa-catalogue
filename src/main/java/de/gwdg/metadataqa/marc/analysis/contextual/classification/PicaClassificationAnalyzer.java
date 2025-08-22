package de.gwdg.metadataqa.marc.analysis.contextual.classification;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.analysis.FieldWithScheme;
import de.gwdg.metadataqa.marc.cli.parameters.ClassificationParameters;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.utils.pica.PicaSubjectManager;
import de.gwdg.metadataqa.marc.utils.pica.PicaVocabularyManager;
import de.gwdg.metadataqa.marc.utils.pica.VocabularyEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PicaClassificationAnalyzer extends ClassificationAnalyzer {

  private static final Logger logger = Logger.getLogger(
    PicaClassificationAnalyzer.class.getCanonicalName()
  );
  private PicaVocabularyManager picaVocabularyManager = null;

  private static List<FieldWithScheme> picaFieldsWithScheme = PicaSubjectManager.readFieldsWithScheme();

  public PicaClassificationAnalyzer(BibliographicRecord marcRecord, ClassificationStatistics statistics) {
    super(marcRecord, statistics);
    picaVocabularyManager = PicaVocabularyManager.getInstance();
  }

  public PicaClassificationAnalyzer(BibliographicRecord marcRecord, ClassificationStatistics statistics, ClassificationParameters parameters) {
    this(marcRecord, statistics);
    this.parameters = parameters;
  }

  public int process() {
    var total = 0;
    schemasInRecord = new ArrayList<>();

    total = processFieldsWithSchemePica(total, picaFieldsWithScheme);

    increaseCounters(total);

    return total;
  }

  private int processFieldsWithSchemePica(int total, List<FieldWithScheme> fieldsWithScheme) {
    int count = total;
    // FIXME processFromTSV set to true directly before the if statement. This should be reconsidered.
    boolean processFromTSV = true;
    if (!processFromTSV) {
      for (VocabularyEntry entry : picaVocabularyManager.getAll()) {
        count += processPicaSubject(entry.getPica(), entry.getVoc(), entry.getLabel());
      }
      return count;
    }

    for (FieldWithScheme entry : fieldsWithScheme) {
      String tag = entry.getTag();
      String schemeName = entry.getSchemaName();
      String voc = tag;
      try {
        // Get the abbreviation for the classification scheme
        voc = classificationSchemes.resolve(schemeName);
      } catch (IllegalArgumentException ignored) {
      }
      count += processPicaSubject(tag, voc, schemeName);
    }
    return count;
  }

  private int processPicaSubject(String tag, String voc, String schema) {
    if (!bibliographicRecord.hasDatafield(tag)) {
      return 0;
    }

    List<DataField> fields = bibliographicRecord.getDatafieldsByTag(tag);
    List<Schema> schemas = new ArrayList<>();
    for (DataField field : fields) {
      String firstSubfield = getFirstSubfield(field);
      if (firstSubfield == null) {
        String logMessage = String.format("undetected subfield in record '%s' %s", bibliographicRecord.getId(), field);
        logger.log(Level.SEVERE, logMessage);
        continue;
      }
      var currentSchema = new Schema(field.getTagWithOccurrence(), firstSubfield, voc, schema);
      schemas.add(currentSchema);
      updateSchemaSubfieldStatistics(field, currentSchema);
    }

    registerSchemas(schemas);
    return schemas.size();
  }

  private String getFirstSubfield(DataField field) {
    if (field.getSubfield("a") != null) {
      return "$a";
    }

    for (MarcSubfield subfield : field.getSubfields()) {
      String code = subfield.getCode();
      if (!code.equals("A")) {
        return "$" + code;
      }
    }
    return null;
  }
}
