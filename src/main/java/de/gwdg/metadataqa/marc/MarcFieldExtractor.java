package de.gwdg.metadataqa.marc;

import com.jayway.jsonpath.InvalidJsonException;
import de.gwdg.metadataqa.api.counter.FieldCounter;
import de.gwdg.metadataqa.api.interfaces.Calculator;
import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.schema.Schema;
import de.gwdg.metadataqa.api.util.CompressionLevel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import de.gwdg.metadataqa.marc.definition.general.codelist.CodeList;
import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class MarcFieldExtractor implements Calculator, Serializable {

  public static final String CALCULATOR_NAME = "fieldExtractor";

  private static final Logger logger = Logger.getLogger(MarcFieldExtractor.class.getCanonicalName());
  private static final List<String> authorFields = Arrays.asList("100$a", "110$a", "700$a", "710$a");

  public static final String FIELD_NAME = "recordId";
  private String idPath;
  protected FieldCounter<List<String>> resultMap;
  protected Schema schema;
  private String recordId;
  private Leader leader;
  private Control007 x007;
  private Control008 x008;
  private Map<String, Object> duplumKeyMap;
  private List<String> titleWords;
  private List<String> authorWords;
  private String duplumKeyType;
  private List<String> dateOfPublication;
  private List<String> isbn;
  private String publisherOrDistributorNumber;
  private String abbreviatedNameOfPublisher;
  private String numberOfPart;
  private String nameOfPart;
  private String extent;
  private String musicalPresentationStatement;
  private String volumeDesignation;
  private String relatedParts;
  private List<X035aSystemControlNumber> systemControlNumbers;
  private Map<String, Object> oclcMap;
  private boolean valid;


  public MarcFieldExtractor() {
  }

  public MarcFieldExtractor(Schema schema) {
    this.schema = schema;
    setIdPath(schema.getExtractableFields().get("001"));
  }

  public MarcFieldExtractor(String idPath) {
    this.idPath = idPath;
  }

  @Override
  public String getCalculatorName() {
    return CALCULATOR_NAME;
  }

  @Override
  public void measure(JsonPathCache cache)
        throws InvalidJsonException {
    valid = true;
    resultMap = new FieldCounter<>();
    duplumKeyMap = null;
    recordId = null;
    leader = null;
    x007 = null;
    x008 = null;
    titleWords = null;
    authorWords = null;
    duplumKeyType = null;
    dateOfPublication = null;
    isbn = null;
    publisherOrDistributorNumber = null;
    abbreviatedNameOfPublisher = null;
    numberOfPart = null;
    nameOfPart = null;
    extent = null;
    musicalPresentationStatement = null;
    volumeDesignation = null;
    relatedParts = null;
    systemControlNumbers = null;
    oclcMap = null;

    recordId = ((List<XmlFieldInstance>) cache.get(getIdPath())).get(0).getValue();
    cache.setRecordId(recordId);
    resultMap.put(FIELD_NAME, Arrays.asList(recordId));
    if (schema != null) {
      String path;
      for (String fieldName : schema.getExtractableFields().keySet()) {
        if (!fieldName.equals(FIELD_NAME)) {
          path = schema.getExtractableFields().get(fieldName);
          List<XmlFieldInstance> instances = (List<XmlFieldInstance>) cache.get(path);
          List<String> values = null;
          if (!isNull(instances)) {
            values = new ArrayList<>();
            for (XmlFieldInstance instance : instances) {
              values.add(instance.getValue());
            }
            if (fieldName.equals("leader")) {
              leader = new Leader(values.get(0));
            }
          }
          resultMap.put(fieldName, values);
        }
      }
    }
    processLeader();
    process007();
    process008();
    processType();
    processTitleWords();
    processAuthorWords();
    processDateOfPublication();
    processIsbn();
    processPublisherOrDistributorNumber();
    processAbbreviatedNameOfPublisher();
    processNumberOfPart();
    processNameOfPart();
    processExtent();
    processMusicalPresentationStatement();
    processVolumeDesignation();
    processRelatedParts();
    processSystemControlNumbers();
    processOclcFields();
    createDuplumKeyMap();
  }

  private static boolean isNull(List<XmlFieldInstance> values) {
    return values == null
          || values.isEmpty()
          || values.get(0) == null
          || values.get(0).getValue() == null;
  }

  public String getIdPath() {
    return idPath;
  }

  public void setIdPath(String idPath) {
    this.idPath = idPath;
  }

  @Override
  public Map<String, ? extends Object> getResultMap() {
    return resultMap.getMap();
  }

  @Override
  public Map<String, Map<String, ? extends Object>> getLabelledResultMap() {
    Map<String, Map<String, ? extends Object>> labelledResultMap = new LinkedHashMap<>();
    labelledResultMap.put(getCalculatorName(), resultMap.getMap());
    return labelledResultMap;
  }

  @Override
  public String getCsv(boolean withLabel, CompressionLevel compressionLevel) {
    return resultMap.getList(withLabel, CompressionLevel.ZERO); // the extracted fields should never be compressed!
  }

  @Override
  public List<String> getHeader() {
    List<String> headers = new ArrayList<>();
    headers.add(FIELD_NAME);
    return headers;
  }

  public void processLeader() {
    if (resultMap.has("leader"))
      leader = new Leader(resultMap.get("leader").get(0));
    else
      logger.severe(String.format("No leader in result map. Nr of existing vars: %s",
          StringUtils.join(resultMap.getMap().keySet(), ", ")));
  }

  public void process007() {
    if (resultMap.get("007") == null) {
      valid = false;
    } else {
      x007 = new Control007(resultMap.get("007").get(0));
    }
  }

  public void process008() {
    if (resultMap.get("008") != null 
        && StringUtils.isNotBlank(resultMap.get("008").get(0)))
      x008 = new Control008(resultMap.get("008").get(0), leader.getType());
  }

  private void processTitleWords() {
    titleWords = extractWords(StringUtils.join(resultMap.get("245$a"), " "), 3);
  }

  private void processType() {
    String typeOfRecord = leader.getByLabel("Type of record");
    String bibliographicLevel = leader.getByLabel("Bibliographic level");
    if (typeOfRecord.equals("a") && bibliographicLevel.equals("s")) {
      duplumKeyType = "p";
    } else if (bibliographicLevel.equals("d")) {
      duplumKeyType = "s";
    } else if (bibliographicLevel.equals("a") || bibliographicLevel.equals("b")) {
      duplumKeyType = "a";
    } else {
      duplumKeyType = "m";
    }
  }

  public List<String> extractWords(String text, int length) {
    List<String> tokens = new ArrayList<>();
    if (StringUtils.isBlank(text))
      return tokens;

    StringTokenizer st = new StringTokenizer(text);
    while (st.hasMoreTokens())
      tokens.add(st.nextToken());

    int max = Math.min(length, tokens.size());
    return tokens.subList(0, max);
  }

  public String getRecordId() {
    return recordId;
  }

  public Leader getLeader() {
    return leader;
  }

  public Control007 getX007() {
    return x007;
  }

  public Control008 getX008() {
    return x008;
  }

  public List<String> getTitleWords() {
    return titleWords;
  }

  public List<String> getAuthorWords() {
    return authorWords;
  }

  public String getDuplumKeyType() {
    return duplumKeyType;
  }

  public List<String> getDateOfPublication() {
    return dateOfPublication;
  }

  public List<String> getIsbn() {
    return isbn;
  }

  public String getPublisherOrDistributorNumber() {
    return publisherOrDistributorNumber;
  }

  public String getAbbreviatedNameOfPublisher() {
    return abbreviatedNameOfPublisher;
  }

  public String getNumberOfPart() {
    return numberOfPart;
  }

  public String getNameOfPart() {
    return nameOfPart;
  }

  public String getExtent() {
    return extent;
  }

  public String getMusicalPresentationStatement() {
    return musicalPresentationStatement;
  }

  public String getVolumeDesignation() {
    return volumeDesignation;
  }

  public String getRelatedParts() {
    return relatedParts;
  }

  private void processAuthorWords() {
    String author = extractAuthor();
    authorWords = extractWords(author, 3);
  }

  private String extractAuthor() {
    String author = null;
    for (String field : authorFields) {
      Object value = resultMap.get(field);
      // String candidate = resultMap.get(field).toString();
      String stringValue;
      if (value instanceof List) {
        stringValue = StringUtils.join((List)value, " ");
      } else {
        stringValue = (String)value;
      }
      if (StringUtils.isNotBlank(stringValue)) {
        author = stringValue;
        break;
      }
    }
    return author;
  }

  private void processDateOfPublication() {
    dateOfPublication = resultMap.get("260$c");
  }

  private void processIsbn() {
    isbn = resultMap.get("020$a");
  }

  private void processPublisherOrDistributorNumber() {
    publisherOrDistributorNumber = duplumKeyType.equals("m")
        ? null : StringUtils.join(resultMap.get("028$a"), "; ");
  }

  private void processAbbreviatedNameOfPublisher() {
    abbreviatedNameOfPublisher = StringUtils.join(resultMap.get("060$b"), "; ");
  }

  private void processNumberOfPart() {
    numberOfPart = StringUtils.join(resultMap.get("245$n"), "; ");
  }

  private void processNameOfPart() {
    nameOfPart = StringUtils.join(resultMap.get("245$p"), "; ");
  }

  private void processExtent() {
    extent = StringUtils.join(resultMap.get("300$a"), "; ");
  }

  private void processMusicalPresentationStatement() {
    musicalPresentationStatement = StringUtils.join(resultMap.get("254$a"), "; ");
  }

  private void processVolumeDesignation() {
    volumeDesignation = StringUtils.join(resultMap.get("490$v"), "; ");
  }

  private void processRelatedParts() {
    relatedParts = StringUtils.join(resultMap.get("773$g"), "; ");
  }

  private void processOclcFields() {
    oclcMap = new LinkedHashMap<>();
    oclcMap.put("oclcLibraryIdentifier", resolve(resultMap.get("029$a"), OrganizationCodes.getInstance()));
    oclcMap.put("otherSystemControlNumber", resultMap.get("029$b"));
    oclcMap.put("catalogingAgency", resolve(resultMap.get("040$a"), OrganizationCodes.getInstance()));
    oclcMap.put("languageOfCataloging", resolve(resultMap.get("040$b"), LanguageCodes.getInstance()));
    oclcMap.put("transcribingAgency", resolve(resultMap.get("040$c"), OrganizationCodes.getInstance()));
    oclcMap.put("modifyingAgency", resolve(resultMap.get("040$d"), OrganizationCodes.getInstance()));
    oclcMap.put("topicalTerm", resultMap.get("650$a"));
    oclcMap.put("manifestId", resultMap.get("911$9"));
    oclcMap.put("workId", resultMap.get("912$9"));
    oclcMap.put("placeOfPublication", resultMap.get("260$a"));
    oclcMap.put("nameOfPublisher", resultMap.get("260$b"));
    // oclcMap.put("dateOfPublication", resultMap.get("260$c"));
    oclcMap.put("sourceOfHeading", resultMap.get("650$2"));
    oclcMap.put("title", resultMap.get("245$a"));
    // oclcMap.put("extent", resultMap.get("300$a"));

  }

  private Object resolve(List<String> list, CodeList codeService) {
    if (list == null || list.isEmpty())
      return list;

    List<String> resolvedList = new ArrayList<>();
    for (String code : list)
      if (codeService.isValid(code))
        resolvedList.add(codeService.getCode(code).getLabel());
      else
        resolvedList.add(code);
    return resolvedList;
  }

  public Map<String, Object> getDuplumKeyMap() {
    if (duplumKeyMap == null) {
      createDuplumKeyMap();
    }
    return duplumKeyMap;
  }

  public void createDuplumKeyMap() {
    duplumKeyMap = new HashMap<String, Object>();
    duplumKeyMap.put("recordId", recordId);
    duplumKeyMap.put("titleWords", titleWords);
    duplumKeyMap.put("authorWords", authorWords);
    duplumKeyMap.put("duplumKeyType", duplumKeyType);
    duplumKeyMap.put("dateOfPublication", dateOfPublication);
    duplumKeyMap.put("isbn", isbn);
    duplumKeyMap.put("publisherOrDistributorNumber", publisherOrDistributorNumber);
    duplumKeyMap.put("abbreviatedNameOfPublisher", abbreviatedNameOfPublisher);
    duplumKeyMap.put("numberOfPart", numberOfPart);
    duplumKeyMap.put("nameOfPart", nameOfPart);
    duplumKeyMap.put("extent", extent);
    duplumKeyMap.put("musicalPresentationStatement", musicalPresentationStatement);
    duplumKeyMap.put("volumeDesignation", volumeDesignation);
    duplumKeyMap.put("relatedParts", relatedParts);
    duplumKeyMap.put("systemControlNumbers", systemControlNumbers);
    for (Map.Entry<String, Object> entry : oclcMap.entrySet()) {
      duplumKeyMap.put(entry.getKey(), entry.getValue());
    }
  }

  public boolean isValid() {
    return valid;
  }

  private void processSystemControlNumbers() {
    systemControlNumbers = new ArrayList<>();
    if (resultMap.get("035$a") != null) {
      for (String original : resultMap.get("035$a")) {
        systemControlNumbers.add(new X035aSystemControlNumber(original));
      }
    }
  }
}
