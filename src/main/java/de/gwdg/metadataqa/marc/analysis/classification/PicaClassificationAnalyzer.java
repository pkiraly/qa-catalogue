package de.gwdg.metadataqa.marc.analysis.classification;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.analysis.ClassificationStatistics;
import de.gwdg.metadataqa.marc.analysis.FieldWithScheme;
import de.gwdg.metadataqa.marc.cli.parameters.ClassificationParameters;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.utils.pica.PicaSubjectManager;
import de.gwdg.metadataqa.marc.utils.pica.PicaVocabularyManager;
import de.gwdg.metadataqa.marc.utils.pica.VocabularyEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PicaClassificationAnalyzer extends ClassificationAnalyzer {

  private static final Logger logger = Logger.getLogger(
    PicaClassificationAnalyzer.class.getCanonicalName()
  );
  private PicaVocabularyManager picaVocabularyManager = null;

  private static List<FieldWithScheme> picaFieldsWithScheme = PicaSubjectManager.readFieldsWithScheme();

  // Not used since the PICA fields are read from the file: k10plus-subjects.tsv
  private static final List<FieldWithScheme> PICA_FIELDS_WITH_SCHEME = Arrays.asList(
    new FieldWithScheme("041A", "Schlagwortfolgen (DNB und Verbünde)"),
    new FieldWithScheme("044K", "Schlagwortfolgen (GBV, SWB, K10plus)"),
    new FieldWithScheme("044L", "Einzelschlagwörter (Projekte)"),
    new FieldWithScheme("044N", "Schlagwörter aus einem Thesaurus und freie Schlagwörter"),
    new FieldWithScheme("044S", "Gattungsbegriffe bei Alten Drucken"),
    new FieldWithScheme("044Z", "Lokale Schlagwörter auf bibliografischer Ebene"),
    new FieldWithScheme("045A", "LCC-Notation"),
    new FieldWithScheme("045B/00", "Allgemeine Systematik für Bibliotheken (ASB)"),
    new FieldWithScheme("045B/01", "Systematik der Stadtbibliothek Duisburg (SSD)"),
    new FieldWithScheme("045B/02", "Systematik für Bibliotheken (SfB)"),
    new FieldWithScheme("045B/03", "Klassifikation für Allgemeinbibliotheken (KAB)"),
    new FieldWithScheme("045B/04", "Systematiken der ekz"),
    new FieldWithScheme("045B/05", "Gattungsbegriffe (DNB)"),
    new FieldWithScheme("045C", "Klassifikation der National Library of Medicine (NLM)"),
    // TODO: 045D/00-29 - "STW-Schlagwörter"
    // TODO: 045D/30-39 - "STW-Schlagwörter - automatisierte verbale Sacherschließung"
    // TODO: 045D/40-48 - "STW-Schlagwörter - Platzhalter"
    new FieldWithScheme("045D/49", "ZBW-Schlagwörter - Veröffentlichungsart"),
    new FieldWithScheme("045D/50", "Vorläufige Schhlagwörter (STW)"),
    new FieldWithScheme("045D/60", "FIV-Schlagwörter (Themen)"),
    new FieldWithScheme("045D/70", "FIV-Schlagwörter (Aspekte)"),
    new FieldWithScheme("045E", "Sachgruppen der Deutschen Nationalbibliografie bis 2003"),
    new FieldWithScheme("045F", "DDC-Notation"),
    new FieldWithScheme("045G", "Sachgruppen der Deutschen Nationalbibliografie ab 2004"),
    new FieldWithScheme("045H", "DDC-Notation: Vollständige Notation"),
    new FieldWithScheme("045M", "Lokale Notationen auf bibliografischer Ebene"),
    new FieldWithScheme("045N", "FIV-Regionalklassifikation"),
    new FieldWithScheme("045Q/01", "Basisklassifikation"),
    new FieldWithScheme("045R", "Regensburger Verbundklassifikation (RVK)"),
    new FieldWithScheme("045S", "Deutsche Bibliotheksstatistik (DBS)"),
    new FieldWithScheme("045T", "Nicht mehr gültige Notationen der Regensburger Verbundklassifikation (RVK)"),
    new FieldWithScheme("045V", "SSG-Nummer/FID-Kennzeichen"),
    new FieldWithScheme("045W", "SSG-Angabe für thematische OLC-Ausschnitte"),
    new FieldWithScheme("045X", "Notation eines Klassifikationssystems"),
    new FieldWithScheme("045Y", "SSG-Angabe für Fachkataloge")
  );

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
    // TODO processFromTSV set to true directly before the if statement. This should be reconsidered.
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
    if (!marcRecord.hasDatafield(tag)) {
      return 0;
    }

    List<DataField> fields = marcRecord.getDatafield(tag);
    List<Schema> schemas = new ArrayList<>();
    for (DataField field : fields) {
      String firstSubfield = getFirstSubfield(field);
      if (firstSubfield != null) {
        var currentSchema = new Schema(field.getTagWithOccurrence(), firstSubfield, voc, schema);
        schemas.add(currentSchema);
        updateSchemaSubfieldStatistics(field, currentSchema);
      } else {
        logger.log(Level.SEVERE, "undetected subfield in record {0} {1}", new Object[]{marcRecord.getId(), field});
      }
    }
    registerSchemas(schemas);
    return schemas.size();
  }

  private String getFirstSubfield(DataField field) {
    if (field.getSubfield("a") != null) {
      return "$a";
    } else {
      for (MarcSubfield subfield : field.getSubfields()) {
        String code = subfield.getCode();
        if (!code.equals("A")) {
          return "$" + code;
        }
      }
    }
    return null;
  }
}
