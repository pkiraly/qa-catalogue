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
import de.gwdg.metadataqa.marc.definition.ControlValue;
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
  private static final Pattern numericalPattern = Pattern.compile("^(\\d)$");
  public static final String IND_1 = "$!ind1";
  public static final String IND_2 = "$!ind2";
  public static final String NUMERICAL_SUBFIELD = "$|";
  public static final String SUBFIELD = "$";
  private static Map<String, Map<String, String>> keyMap = new HashMap<>();

  private final BibiographicPath groupBy;
  private final CompletenessParameters parameters;
  private final CompletenessDAO completenessDAO;
  private final CompletenessPlugin plugin;
  BibliographicRecord bibliographicRecord;
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
      List<String> idLists = parameters.isPica() ? bibliographicRecord.select((PicaPath) groupBy) : null; // TODO: MARC21
      groupIds = QACli.extractGroupIds(idLists);
    }
  }

  public void process() {
    documentType = plugin.getDocumentType(bibliographicRecord);
    completenessDAO.getElementCardinality().computeIfAbsent(documentType, s -> new HashMap<>());
    completenessDAO.getElementCardinality().computeIfAbsent(Completeness.ALL_TYPE, s -> new HashMap<>());
    completenessDAO.getElementFrequency().computeIfAbsent(documentType, s -> new HashMap<>());
    completenessDAO.getElementFrequency().computeIfAbsent(Completeness.ALL_TYPE, s -> new HashMap<>());

    if (bibliographicRecord.getControl003() != null)
      Utils.count(bibliographicRecord.getControl003().getContent(), completenessDAO.getLibrary003Counter());

    for (String library : extract(bibliographicRecord, "852", "a"))
      Utils.count(library, completenessDAO.getLibraryCounter());

    if (!parameters.isPica()) {
      processLeader();
      processSimpleControlfields();
      processPositionalControlFields();
    }
    processDataFields();
  }

  private void processLeader() {
    if (bibliographicRecord.getLeader() != null) {
      for (ControlValue position : bibliographicRecord.getLeader().getValuesList()) {
        String marcPath = position.getDefinition().getId();
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
    }
  }

  private void processSimpleControlfields() {
    for (MarcControlField field : bibliographicRecord.getSimpleControlfields()) {
      if (field != null) {
        String marcPath = field.getDefinition().getTag();
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
    }
  }

  private void processPositionalControlFields() {
    for (MarcPositionalControlField field : bibliographicRecord.getPositionalControlfields()) {
      if (field != null) {
        for (ControlValue position : field.getValuesList()) {
          String marcPath = position.getDefinition().getId();
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
    List<DataField> fields = marcRecord.getDatafield(tag);
    if (fields != null && !fields.isEmpty()) {
      for (DataField field : fields) {
        List<MarcSubfield> subfieldInstances = field.getSubfield(subfield);
        if (subfieldInstances != null) {
          for (MarcSubfield subfieldInstance : subfieldInstances) {
            values.add(subfieldInstance.getValue());
          }
        }
      }
    }
    return values;
  }

  private String getPackageName(DataField field) {
    String packageName;
    if (field.getDefinition() != null) {
      if (completenessDAO.getPackageNameCache().containsKey(field.getDefinition()))
        packageName = completenessDAO.getPackageNameCache().get(field.getDefinition());
      else {
        packageName = plugin.getPackageName(field);
        if (StringUtils.isBlank(packageName)) {
          logger.log(Level.WARNING, "{0} has no package. /{1}", new Object[]{field, field.getDefinition().getClass()});
          packageName = TagCategory.OTHER.getPackageName();
        }
        completenessDAO.getPackageNameCache().put(field.getDefinition(), packageName);
      }
    } else {
      packageName = TagCategory.OTHER.getPackageName();
    }
    return packageName;
  }

  private List<String> getMarcPaths(DataField field) {
    List<String> marcPaths = new ArrayList<>();

    if (parameters.isMarc21()) {
      if (field.getInd1() != null
          && field.getDefinition() != null
          && (field.getDefinition().getInd1().exists()
             || !field.getInd1().equals(" ")))
        marcPaths.add(field.getTagWithOccurrence() + IND_1);

      if (field.getInd2() != null
          && field.getDefinition() != null
          && (field.getDefinition().getInd2().exists() || !field.getInd2().equals(" ")))
         marcPaths.add(field.getTagWithOccurrence() + IND_2);
    }

    for (MarcSubfield subfield : field.getSubfields())
      marcPaths.add(getKey(field, subfield));

    return marcPaths;
  }

  private static String getKey(DataField field, MarcSubfield subfield) {
    String f = field.getTagWithOccurrence();
    String c = subfield.getCode();
    keyMap.computeIfAbsent(f, s -> new HashMap<>());
    if (!keyMap.get(f).containsKey(c)) {
      String key = numericalPattern.matcher(c).matches() ? f + NUMERICAL_SUBFIELD + c : f + SUBFIELD + c;
      keyMap.get(f).put(c, key);
    }
    return keyMap.get(f).get(c);
  }

  private void addGroupedElementCardinality(String marcPath, String groupId) {
    completenessDAO.getGroupedElementCardinality().computeIfAbsent(groupId, s -> new HashMap<>());
    completenessDAO.getGroupedElementCardinality().get(groupId).computeIfAbsent(documentType, s -> new HashMap<>());
    completenessDAO.getGroupedElementCardinality().get(groupId).computeIfAbsent(Completeness.ALL_TYPE, s -> new HashMap<>());
    Utils.count(marcPath, completenessDAO.getGroupedElementCardinality().get(groupId).get(documentType));
    Utils.count(marcPath, completenessDAO.getGroupedElementCardinality().get(groupId).get(Completeness.ALL_TYPE));
  }
}
