package de.gwdg.metadataqa.marc.analysis.completeness;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.cli.Completeness;
import de.gwdg.metadataqa.marc.cli.QACli;
import de.gwdg.metadataqa.marc.cli.parameters.CompletenessParameters;
import de.gwdg.metadataqa.marc.cli.plugin.CompletenessPlugin;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcControlField;
import de.gwdg.metadataqa.marc.dao.MarcPositionalControlField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.dao.record.MarcRecord;
import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.tags.TagCategory;
import de.gwdg.metadataqa.marc.utils.BibiographicPath;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPath;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class RecordCompleteness {

  private static final Logger logger = Logger.getLogger(RecordCompleteness.class.getCanonicalName());

  /**
   * Pattern to match one single digit.
   */
  private static final Pattern numericalPattern = Pattern.compile("^(\\d)$");
  public static final String IND_1 = "$!ind1";
  public static final String IND_2 = "$!ind2";
  public static final String NUMERICAL_SUBFIELD = "$|";
  public static final String SUBFIELD = "$";
  private static Map<String, Map<String, String>> sortKeyMap = new HashMap<>();

  private final BibiographicPath groupBy;
  private final CompletenessParameters parameters;
  private final CompletenessDAO completenessDAO;
  private final CompletenessPlugin plugin;
  BibliographicRecord bibliographicRecord;

  // TODO Ask why the documentType is relevant. Why is it a String and not a MarcLeader.Type?
  String documentType;
  boolean hasGroupBy;
  Map<String, Integer> recordFrequency = new HashMap<>();
  Map<String, Integer> recordPackageCounter = new HashMap<>();
  Set<String> groupIds = new HashSet<>();

  public RecordCompleteness(BibliographicRecord bibliographicRecord,
                            CompletenessParameters parameters,
                            CompletenessDAO completenessDAO,
                            CompletenessPlugin plugin,
                            BibiographicPath groupBy) {
    this.bibliographicRecord = bibliographicRecord;
    this.parameters = parameters;
    this.completenessDAO = completenessDAO;
    this.plugin = plugin;
    this.groupBy = groupBy;
    this.hasGroupBy = (groupBy != null);

    if (hasGroupBy) {
      // TODO: MARC21 and UNIMARC
      List<String> idLists = parameters.isPica() ? bibliographicRecord.select((PicaPath) groupBy) : null;
      groupIds = QACli.extractGroupIds(idLists);
    }
  }

  public void process() {
    documentType = plugin.getDocumentType(bibliographicRecord);
    completenessDAO.getElementCardinality().computeIfAbsent(documentType, s -> new HashMap<>());
    completenessDAO.getElementCardinality().computeIfAbsent(Completeness.ALL_TYPE, s -> new HashMap<>());
    completenessDAO.getElementFrequency().computeIfAbsent(documentType, s -> new HashMap<>());
    completenessDAO.getElementFrequency().computeIfAbsent(Completeness.ALL_TYPE, s -> new HashMap<>());

    if (bibliographicRecord instanceof Marc21Record && ((Marc21Record) bibliographicRecord).getControl003() != null)
      Utils.count(((Marc21Record) bibliographicRecord).getControl003().getContent(), completenessDAO.getLibrary003Counter());

    for (String library : extract(bibliographicRecord, "852", "a"))
      Utils.count(library, completenessDAO.getLibraryCounter());

    // If it's UNIMARC or MARC21
    if (!parameters.isPica()) {
      MarcRecord marcRecord = (MarcRecord) bibliographicRecord;
      processLeader(marcRecord);
      processSimpleControlfields(marcRecord);
    }
    // If it's only MARC21
    if (parameters.isMarc21()) {
      Marc21Record marcRecord = (Marc21Record) bibliographicRecord;
      processPositionalControlFields(marcRecord);
    }
    processDataFields();
  }

  private void processLeader(MarcRecord marcRecord) {
    if (marcRecord.getLeader() == null) {
      return;
    }

    for (ControlValue position : marcRecord.getLeader().getValuesList()) {
      // For each value of the leader, we count the cardinality of the element
      String marcPath = position.getDefinition().getId();
      countByMarcPath(marcPath);
    }
  }

  private void processSimpleControlfields(MarcRecord marcRecord) {
    for (MarcControlField field : marcRecord.getSimpleControlfields()) {
      if (field == null) {
        continue;
      }

      String marcPath = field.getDefinition().getTag();
      countByMarcPath(marcPath);
    }
  }

  private void processPositionalControlFields(Marc21Record marcRecord) {
    for (MarcPositionalControlField field : marcRecord.getPositionalControlfields()) {
      if (field == null) {
        continue;
      }
      for (ControlValue position : field.getValuesList()) {
        String marcPath = position.getDefinition().getId();
        countByMarcPath(marcPath);
      }
    }
  }

  private void processDataFields() {
    for (DataField field : bibliographicRecord.getDatafields()) {
      if (parameters.getIgnorableFields().contains(field.getTagWithOccurrence()))
        continue;

      Utils.count(getPackageName(field), recordPackageCounter);
      Utils.count(field.getTagWithOccurrence(), recordFrequency);

      for (String marcPath : getMarcPaths(field))
        Utils.count(marcPath, recordFrequency);

      if (groupBy != null) {
        for (String groupId : groupIds)
          processGroupedDataField(field, groupId);
      } else {
        processDataField(field);
      }
    }
  }

  private void processDataField(DataField field) {
    Utils.count(field.getTagWithOccurrence(), completenessDAO.getElementCardinality().get(documentType));
    Utils.count(field.getTagWithOccurrence(), completenessDAO.getElementCardinality().get(Completeness.ALL_TYPE));

    List<String> marcPaths = getMarcPaths(field);
    for (String marcPath : marcPaths) {
      Utils.count(marcPath, completenessDAO.getElementCardinality().get(documentType));
      Utils.count(marcPath, completenessDAO.getElementCardinality().get(Completeness.ALL_TYPE));
    }
  }

  private void processGroupedDataField(DataField field, String groupId) {
    addGroupedElementCardinality(field.getTagWithOccurrence(), groupId);

    List<String> marcPaths = getMarcPaths(field);
    for (String marcPath : marcPaths) {
      Utils.count(marcPath, completenessDAO.getGroupedElementCardinality().get(groupId).get(documentType));
      Utils.count(marcPath, completenessDAO.getGroupedElementCardinality().get(groupId).get(Completeness.ALL_TYPE));
    }
  }

  private void countByMarcPath(String marcPath) {
    if (hasGroupBy()) {
      for (String groupId : groupIds)
        addGroupedElementCardinality(marcPath, groupId);
    } else {
      Utils.count(marcPath, completenessDAO.getElementCardinality().get(documentType));
      Utils.count(marcPath, completenessDAO.getElementCardinality().get(Completeness.ALL_TYPE));
    }
    Utils.count(marcPath, recordFrequency);
    Utils.count(TagCategory.TAGS_00X.getPackageName(), recordPackageCounter);
  }

  public Set<String> getGroupIds() {
    return groupIds;
  }

  public Map<String, Integer> getRecordFrequency() {
    return recordFrequency;
  }

  public Map<String, Integer> getRecordPackageCounter() {
    return recordPackageCounter;
  }

  public String getDocumentType() {
    return documentType;
  }

  public boolean hasGroupBy() {
    return hasGroupBy;
  }

  private List<String> extract(BibliographicRecord marcRecord, String tag, String subfield) {
    List<String> values = new ArrayList<>();
    List<DataField> fields = marcRecord.getDatafieldsByTag(tag);
    if (fields == null || fields.isEmpty()) {
      return values;
    }

    for (DataField field : fields) {
      List<MarcSubfield> subfieldInstances = field.getSubfield(subfield);
      if (subfieldInstances == null) {
        continue;
      }
      for (MarcSubfield subfieldInstance : subfieldInstances) {
        values.add(subfieldInstance.getValue());
      }
    }
    return values;
  }

  /**
   * Returns the package name of a given field. Tries to get the package name from the cache first, and if it is not
   * present, it will be retrieved from the plugin which was supplied to the constructor.
   * @param field The field to get the package name for
   * @return The package name of the given field
   */
  private String getPackageName(DataField field) {
    String packageName;
    DataFieldDefinition fieldDefinition = field.getDefinition();

    if (fieldDefinition == null) {
      packageName = TagCategory.OTHER.getPackageName();
      return packageName;
    }

    Map<DataFieldDefinition, String> packageNameCache = completenessDAO.getPackageNameCache();
    if (packageNameCache.containsKey(fieldDefinition)) {
      packageName = packageNameCache.get(fieldDefinition);
      return packageName;
    }

    packageName = plugin.getPackageName(field);
    if (StringUtils.isBlank(packageName)) {
      logger.log(Level.WARNING, "{0} has no package. /{1}", new Object[]{field, fieldDefinition.getClass()});
      packageName = TagCategory.OTHER.getPackageName();
    }
    packageNameCache.put(fieldDefinition, packageName);
    return packageName;
  }

  private List<String> getMarcPaths(DataField field) {
    List<String> marcPaths = new ArrayList<>();
    DataFieldDefinition fieldDefinition = field.getDefinition();

    if (!parameters.isPica() && fieldDefinition != null) {
      if (field.getInd1() != null && (fieldDefinition.getInd1().exists() || !field.getInd1().equals(" "))) {
        marcPaths.add(field.getTagWithOccurrence() + IND_1);
      }

      if (field.getInd2() != null && (fieldDefinition.getInd2().exists() || !field.getInd2().equals(" "))) {
        marcPaths.add(field.getTagWithOccurrence() + IND_2);
      }
    }

    for (MarcSubfield subfield : field.getSubfields()) {
      marcPaths.add(getSubfieldMarcPath(field, subfield));
    }
    return marcPaths;
  }

  /**
   * Returns the MARC path for a given subfield in such a format that it can be used as a sorting key, so that
   * the subfields are sorted by having the numerical subfields after the letter subfields.
   * @param field The data field that contains the subfield
   * @param subfield The subfield to get the MARC path for
   * @return The MARC path for the given subfield
   */
  private static String getSubfieldMarcPath(DataField field, MarcSubfield subfield) {
    String fieldTag = field.getTagWithOccurrence();
    String subfieldCode = subfield.getCode();

    sortKeyMap.computeIfAbsent(fieldTag, s -> new HashMap<>());

    Map<String, String> fieldMap = sortKeyMap.get(fieldTag);

    boolean isSubfieldCodePresent = fieldMap.containsKey(subfieldCode);
    if (!isSubfieldCodePresent) {
      boolean isDigit = numericalPattern.matcher(subfieldCode).matches();

      String key = isDigit ? fieldTag + NUMERICAL_SUBFIELD + subfieldCode : fieldTag + SUBFIELD + subfieldCode;
      fieldMap.put(subfieldCode, key);
    }
    return fieldMap.get(subfieldCode);
  }

  private void addGroupedElementCardinality(String marcPath, String groupId) {
    completenessDAO.getGroupedElementCardinality().computeIfAbsent(groupId, s -> new HashMap<>());
    completenessDAO.getGroupedElementCardinality().get(groupId).computeIfAbsent(documentType, s -> new HashMap<>());
    completenessDAO.getGroupedElementCardinality().get(groupId).computeIfAbsent(Completeness.ALL_TYPE, s -> new HashMap<>());
    Utils.count(marcPath, completenessDAO.getGroupedElementCardinality().get(groupId).get(documentType));
    Utils.count(marcPath, completenessDAO.getGroupedElementCardinality().get(groupId).get(Completeness.ALL_TYPE));
  }
}
