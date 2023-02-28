package de.gwdg.metadataqa.marc.analysis.completeness;

import de.gwdg.metadataqa.marc.MarcSubfield;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class RecordCompleteness {

  private static final Logger logger = Logger.getLogger(RecordCompleteness.class.getCanonicalName());
  private static final Pattern numericalPattern = Pattern.compile("^(\\d)$");

  private final BibiographicPath groupBy;
  private final CompletenessParameters parameters;
  private final CompletenessDAO completenessDAO;
  private final CompletenessPlugin plugin;
  BibliographicRecord bibliographicRecord;
  String documentType;
  boolean hasGroupBy;
  Map<String, Integer> recordFrequency = new TreeMap<>();
  Map<String, Integer> recordPackageCounter = new TreeMap<>();
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
    completenessDAO.getElementCardinality().computeIfAbsent(documentType, s -> new TreeMap<>());
    completenessDAO.getElementFrequency().computeIfAbsent(documentType, s -> new TreeMap<>());

    if (bibliographicRecord.getControl003() != null)
      count(bibliographicRecord.getControl003().getContent(), completenessDAO.getLibrary003Counter());

    for (String library : extract(bibliographicRecord, "852", "a"))
      count(library, completenessDAO.getLibraryCounter());

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
            addGrouppedElementCardinality(marcPath, groupId);
        } else {
          count(marcPath, completenessDAO.getElementCardinality().get(documentType));
          count(marcPath, completenessDAO.getElementCardinality().get("all"));
        }
        count(marcPath, recordFrequency);
        count(TagCategory.TAGS_00X.getPackageName(), recordPackageCounter);
      }
    }
  }

  private void processSimpleControlfields() {
    for (MarcControlField field : bibliographicRecord.getSimpleControlfields()) {
      if (field != null) {
        String marcPath = field.getDefinition().getTag();
        if (hasGroupBy()) {
          for (String groupId : groupIds)
            addGrouppedElementCardinality(marcPath, groupId);
        } else {
          count(marcPath, completenessDAO.getElementCardinality().get(documentType));
          count(marcPath, completenessDAO.getElementCardinality().get("all"));
        }
        count(marcPath, recordFrequency);
        count(TagCategory.TAGS_00X.getPackageName(), recordPackageCounter);
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
              addGrouppedElementCardinality(marcPath, groupId);
          } else {
            count(marcPath, completenessDAO.getElementCardinality().get(documentType));
            count(marcPath, completenessDAO.getElementCardinality().get("all"));
          }
          count(marcPath, recordFrequency);
          count(TagCategory.TAGS_00X.getPackageName(), recordPackageCounter);
        }
      }
    }
  }

  private void processDataFields() {
    for (DataField field : bibliographicRecord.getDatafields()) {
      if (parameters.getIgnorableFields().contains(field.getTagWithOccurrence()))
        continue;

      count(getPackageName(field), recordPackageCounter);
      count(field.getTagWithOccurrence(), recordFrequency);
      for (String marcPath : getMarcPaths(field))
        count(marcPath, recordFrequency);

      if (groupBy != null) {
        for (String groupId : groupIds)
          processGrouppedDataField(field, groupId);
      } else {
        processDataField(field);
      }
    }
  }

  private void processDataField(DataField field) {
    count(field.getTagWithOccurrence(), completenessDAO.getElementCardinality().get(documentType));
    count(field.getTagWithOccurrence(), completenessDAO.getElementCardinality().get("all"));

    List<String> marcPaths = getMarcPaths(field);
    for (String marcPath : marcPaths) {
      count(marcPath, completenessDAO.getElementCardinality().get(documentType));
      count(marcPath, completenessDAO.getElementCardinality().get("all"));
    }
  }

  private void processGrouppedDataField(DataField field, String groupId) {
    addGrouppedElementCardinality(field.getTagWithOccurrence(), groupId);

    List<String> marcPaths = getMarcPaths(field);
    for (String marcPath : marcPaths) {
      count(marcPath, completenessDAO.getGrouppedElementCardinality().get(groupId).get(documentType));
      count(marcPath, completenessDAO.getGrouppedElementCardinality().get(groupId).get("all"));
    }
  }

  private <T extends Object> void count(T key, Map<T, Integer> counter) {
    counter.computeIfAbsent(key, s -> 0);
    counter.put(key, counter.get(key) + 1);
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
          logger.warning(String.format("%s has no package. /%s", field, field.getDefinition().getClass()));
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
      if (field.getInd1() != null)
        if (field.getDefinition() != null && field.getDefinition().getInd1().exists() || !field.getInd1().equals(" "))
          marcPaths.add(String.format("%s$!ind1", field.getTagWithOccurrence()));

      if (field.getInd2() != null)
        if (field.getDefinition() != null && field.getDefinition().getInd2().exists() || !field.getInd2().equals(" "))
          marcPaths.add(String.format("%s$!ind2", field.getTagWithOccurrence()));
    }

    for (MarcSubfield subfield : field.getSubfields())
      if (numericalPattern.matcher(subfield.getCode()).matches())
        marcPaths.add(String.format("%s$|%s", field.getTagWithOccurrence(), subfield.getCode()));
      else
        marcPaths.add(String.format("%s$%s", field.getTagWithOccurrence(), subfield.getCode()));

    return marcPaths;
  }

  private void addGrouppedElementCardinality(String marcPath, String groupId) {
    completenessDAO.getGrouppedElementCardinality().computeIfAbsent(groupId, s -> new TreeMap<>());
    completenessDAO.getGrouppedElementCardinality().get(groupId).computeIfAbsent(documentType, s -> new TreeMap<>());
    completenessDAO.getGrouppedElementCardinality().get(groupId).computeIfAbsent("all", s -> new TreeMap<>());
    count(marcPath, completenessDAO.getGrouppedElementCardinality().get(groupId).get(documentType));
    count(marcPath, completenessDAO.getGrouppedElementCardinality().get(groupId).get("all"));
  }
}
