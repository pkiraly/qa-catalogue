package de.gwdg.metadataqa.marc.utils.unimarc;

import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Reads a UNIMARC schema from a JSON file, so that it can be used to process UNIMARC records.
 */
public class UnimarcSchemaReader {
  private static final Logger logger = Logger.getLogger(UnimarcSchemaReader.class.getCanonicalName());
  private static final String LABEL = "label";
  private static final String TAG = "tag";
  private static final String REQUIRED = "required";
  private static final String REPEATABLE = "repeatable";
  private static final String INDICATOR_1 = "indicator1";
  private static final String INDICATOR_2 = "indicator2";
  private static final String SUBFIELDS = "subfields";
  private static final String POSITIONS = "positions";
  private static final String CODES = "codes";
  private static final String FLAGS = "flags";
  private static final String CODELIST = "codelist";
  private static final String PATTERN = "pattern";
  private static final String START = "start";
  private static final String END = "end";

  private static final Map<String, Integer> knownFieldProperties = Map.of(
      LABEL, 1,
      TAG, 1,
      REQUIRED, 1,
      REPEATABLE, 1,
      INDICATOR_1, 1,
      INDICATOR_2, 1,
      SUBFIELDS, 1,
      POSITIONS, 1);
  private static final Map<String, Integer> knownSubfieldProperties = Map.of(
      LABEL, 1,
      REPEATABLE, 1,
      CODELIST, 1,
      POSITIONS, 1,
      CODES, 1);
  private static final Map<String, Integer> knownIndicatorProperties = Map.of(
      LABEL, 1,
      CODES,1);

  private final JSONParser parser = new JSONParser(JSONParser.MODE_RFC4627);
  private final UnimarcSchemaManager schema = new UnimarcSchemaManager();
  private final Map<String, List<EncodedValue>> codeLists = new HashMap<>();

  public UnimarcSchemaManager createSchema(InputStream inputStream) {
    try {
      JSONObject jsonObject = readStream(inputStream);
      processCodeLists(jsonObject);
      processFields(jsonObject);
    } catch (ParseException e) {
      logger.severe(e.getLocalizedMessage());
    }

    return schema;
  }

  public UnimarcSchemaManager createSchema(String filename) {
    try {
      JSONObject jsonObject = readFile(filename);
      processCodeLists(jsonObject);
      processFields(jsonObject);
    } catch (FileNotFoundException | ParseException e) {
      logger.severe(e.getLocalizedMessage());
    }

    return schema;
  }

  /**
   * Used to load all code lists from the JSON object and store them in the codeLists map. The code lists are going to
   * be used to resolve the codes in subfields or positions.
   * @param jsonObject The JSON object of the schema which is assumed to contain the codelists object.
   */
  private void processCodeLists(JSONObject jsonObject) {
    JSONObject fields = (JSONObject) jsonObject.get("codelists");
    for (Map.Entry<String, Object> entry : fields.entrySet()) {
      String codeListName = entry.getKey();
      JSONObject properties = (JSONObject) entry.getValue();
      List<EncodedValue> codeList = processCodes((JSONObject) properties.get(CODES));
      codeLists.put(codeListName, codeList);
    }
  }

  private JSONObject readFile(String filename) throws FileNotFoundException, ParseException {
    FileReader reader = new FileReader(filename);
    return (JSONObject) parser.parse(reader);
  }

  private JSONObject readStream(InputStream stream) throws ParseException {
    InputStreamReader streamReader = new InputStreamReader(stream, StandardCharsets.UTF_8);
    return (JSONObject) parser.parse(streamReader);
  }

  private void processFields(JSONObject obj) {
    JSONObject fields = (JSONObject) obj.get("fields");
    for (Map.Entry<String, Object> entry : fields.entrySet()) {
      String tag = entry.getKey();
      JSONObject jsonField = (JSONObject) fields.get(tag);

      // If the tag is 'LEADER', then create a special field definition for the leader which is essentially a
      // ControlfieldDefinition. If the tag is not 'LEADER', then create a normal field definition.

      if (tag.equals("LEADER")) {
        UnimarcLeaderDefinition leaderDefinition = createLeaderDefinition(jsonField);
        schema.setLeaderDefinition(leaderDefinition);
        continue;
      }

      UnimarcFieldDefinition fieldDefinition = createFieldDefinition(tag, jsonField);

      if (schema.lookup(tag) != null) {
        logger.warning(() -> "duplicate field definition for tag: " + tag);
      }
      // Take the last definition of a field, as it is the most recent one
      schema.add(fieldDefinition);
    }
  }

  private UnimarcLeaderDefinition createLeaderDefinition(JSONObject jsonField) {
    UnimarcLeaderDefinition leaderDefinition = new UnimarcLeaderDefinition();
    // Set position definitions
    List<ControlfieldPositionDefinition> positions = getPositions(jsonField, "leader");
    leaderDefinition.setControlfieldPositions(positions);
    return leaderDefinition;
  }

  private UnimarcFieldDefinition createFieldDefinition(String tag, JSONObject jsonField) {
    // In this situation, it isn't necessary to access the JSON value of 'tag' directly,
    // as it is already available as the key of the UNIMARC field.
    UnimarcFieldDefinition fieldDefinition = new UnimarcFieldDefinition(
        tag,
        (String) jsonField.get(LABEL),
        jsonField.get(REPEATABLE) != null && (boolean) jsonField.get(REPEATABLE),
        jsonField.get(REQUIRED) != null && (boolean) jsonField.get(REQUIRED)
    );

    Indicator indicator1 = getIndicator(1, jsonField);
    fieldDefinition.setInd1(indicator1);
    Indicator indicator2 = getIndicator(2, jsonField);
    fieldDefinition.setInd2(indicator2);
    List<SubfieldDefinition> subfieldDefinitions = getSubfields(jsonField, tag);
    fieldDefinition.setSubfieldDefinitions(subfieldDefinitions);

    // Log all unhandled field properties
    for (String property : jsonField.keySet()) {
      if (!knownFieldProperties.containsKey(property)) {
        logger.warning(() -> "unhandled property for field " + tag + ": " + property);
      }
    }

    return fieldDefinition;
  }

  /**
   * Retrieves the indicator from the JSON object. An in
   * @param indicatorNumber Either 1 or 2
   * @param jsonField The JSON object of the field
   */
  private Indicator getIndicator(int indicatorNumber, JSONObject jsonField) {
    String indicatorKey = "indicator" + indicatorNumber;
    JSONObject jsonIndicator = (JSONObject) jsonField.get(indicatorKey);
    if (jsonIndicator == null) {
      // Return an empty indicator which represent the empty values in order to conform with MARC21 fields
      return new Indicator();
    }

    Indicator indicator = new Indicator((String) jsonIndicator.get(LABEL));
    List<EncodedValue> codes = getValueExpressions(jsonIndicator);
    indicator.setCodes(codes);

    // Log all unhandled indicator properties
    for (String property : jsonIndicator.keySet()) {
      if (!knownIndicatorProperties.containsKey(property)) {
        logger.warning(() -> "unhandled indicator property: " + property);
      }
    }

    return indicator;
  }

  private List<SubfieldDefinition> getSubfields(JSONObject jsonField, String parentTag) {
    // Subfields are a JSON object in our schema
    JSONObject subfields = (JSONObject) jsonField.get(SUBFIELDS);
    if (subfields == null) {
      return List.of();
    }

    List<SubfieldDefinition> subfieldDefinitions = new ArrayList<>();

    for (Map.Entry<String, Object> entry : subfields.entrySet()) {
      String code = entry.getKey();
      // Avoid personally defined JSON comments
      if (code.startsWith("//")) {
        continue;
      }

      JSONObject jsonSubfield = (JSONObject) subfields.get(code);
      // In this situation, it isn't necessary to access the JSON value of 'code' directly,
      // as it is already available as the key of the UNIMARC subfield.
      Object repeatable = jsonSubfield.get(REPEATABLE);

      SubfieldDefinition subfieldDefinition = new SubfieldDefinition(
          code,
          (String) jsonSubfield.get(LABEL),
          repeatable != null && (boolean) repeatable
      );

      List<EncodedValue> valueExpressions = getValueExpressions(jsonSubfield);
      subfieldDefinition.setCodes(valueExpressions);

      String subfieldTag = String.format("%s$%s", parentTag, code);

      List<ControlfieldPositionDefinition> positions = getPositions(jsonSubfield, subfieldTag);
      subfieldDefinition.setPositions(positions);

      // Check if the subfield is a duplicate
      if (subfieldDefinitions.stream().anyMatch(subfield -> subfield.getCode().equals(code))) {
        logger.warning(() -> "duplicate subfield definition for tag: " + subfieldTag);
      }
      subfieldDefinitions.add(subfieldDefinition);

      // Log all unhandled subfield properties
      for (String property : jsonSubfield.keySet()) {
        if (!knownSubfieldProperties.containsKey(property)) {
          logger.warning(() -> String.format("%s$%s unhandled subfield property: %s", parentTag, code, property));
        }
      }
    }
    return subfieldDefinitions;
  }

  private List<ControlfieldPositionDefinition> getPositions(JSONObject positionParent, String parentTag) {
    JSONObject positions = (JSONObject) positionParent.get(POSITIONS);
    if (positions == null) {
      return List.of();
    }
    List<ControlfieldPositionDefinition> positionDefinitions = new ArrayList<>();
    for (Map.Entry<String, Object> positionEntry : positions.entrySet()) {

      if (positionEntry.getKey().startsWith("//")) {
        continue;
      }

      JSONObject position = (JSONObject) positionEntry.getValue();

      int positionStart = (int) position.get(START);
      Object positionEndObject = position.get(END);

      // As the implementation of ControlfieldPositionDefinition requires a positionEnd, and it seems
      // to be slightly different to what is specified in the UNIMARC manuals, we add 1 to the positionEnd
      int positionEnd = (positionEndObject == null ? positionStart : (int) positionEndObject) + 1;

      ControlfieldPositionDefinition positionDefinition = new ControlfieldPositionDefinition(
          (String) position.get(LABEL),
          positionStart,
          positionEnd
      );
      String positionKey = positionEntry.getKey();
      // Check position places. This doesn't produce any side effects except for log warnings.
      checkPositionPlaces(positionKey, positionStart, positionEndObject);
      String positionId = String.format("%s/%s", parentTag, positionEntry.getKey());
      positionDefinition.setId(positionId);

      assignPositionValueExpressions(position, positionDefinition);

      positionDefinitions.add(positionDefinition);
    }
    return positionDefinitions;
  }

  private void assignPositionValueExpressions(JSONObject position, ControlfieldPositionDefinition positionDefinition) {
    List<EncodedValue> codes = getCodes(position, CODES);
    if (!codes.isEmpty()) {
      positionDefinition.setCodes(codes);

      positionDefinition.setRepeatableContent(false);
      // In case there are codes, don't check for any other value-defining properties such as flags or patterns
      return;
    }

    // In case there are no codes, check for flags and a pattern
    // Flags make the position repeatable
    // Patterns are used to validate the content of the position
    // Both flags and the pattern are represented as EncodedValues
    codes = getCodes(position, FLAGS);
    if (!codes.isEmpty()) {
      positionDefinition.setCodes(codes);
      positionDefinition.setRepeatableContent(true);

      int unitLength = codes.get(0).getCode().length();
      positionDefinition.setUnitLength(unitLength);
    }

    EncodedValue pattern = getPattern(position);

    // The pattern can also contain groups, which are used to extract the value from the position
    // For example: ^(0[1-9]|[1-9][0-9])$|^(xx)$
    // Semantics of the first and the second group are defined in the schema in a "groups" object
    if (pattern != null) {
      assignGroupsToPattern(position, pattern);
      codes.add(pattern);
    }

    positionDefinition.setCodes(codes);
  }

  private List<EncodedValue> getValueExpressions(JSONObject subfield) {
    List<EncodedValue> codes = getCodes(subfield, CODES);
    if (!codes.isEmpty()) {
      // In case there are codes, don't check for any other value-defining properties such as flags or patterns
      return codes;
    }

    // In case there are no codes, check for a pattern
    // Patterns are used to validate the content of the position
    EncodedValue pattern = getPattern(subfield);

    // The pattern can also contain groups, which are used to extract the value from the position
    // For example: ^(0[1-9]|[1-9][0-9])$|^(xx)$
    // Semantics of the first and the second group are defined in the schema in a "groups" object
    if (pattern != null) {
      assignGroupsToPattern(subfield, pattern);
      codes.add(pattern);
    }

    return codes;
  }

  private void checkPositionPlaces(String key, int positionStart, Object positionEndObject) {
    // Compare the key with the postitionStart and positionEnd. The key should be in the format positionStart-positionEnd,
    // or only positionStart if positionEnd is null
    String[] keyParts = key.split("-");

    if (keyParts.length == 1) {
      // If the key is only one part, then it should be equal to the positionStart int
      if (Integer.parseInt(keyParts[0]) != positionStart) {
        logger.warning(() -> String.format("positionStart (%s) and key (%s) don't match", positionStart, key));
      }
      return;
    }

    if (keyParts.length != 2) {
      logger.warning(() -> String.format("key (%s) is not in the format of positionStart-positionEnd", key));
      return;
    }

    // If the key is two parts, then the first part should be equal to the positionStart and the second part
    // should be equal to the positionEnd
    if (Integer.parseInt(keyParts[0]) != positionStart) {
      logger.warning(() -> String.format("positionStart (%s) and key (%s) don't match", positionStart, key));
    }

    if (positionEndObject == null) {
      logger.warning(() -> String.format("positionEnd (%s) and key (%s) don't match", positionEndObject, key));
      return;
    }

    int positionEnd = (int) positionEndObject;

    if (Integer.parseInt(keyParts[1]) != positionEnd) {
      logger.warning(() -> String.format("positionEnd (%s) and key (%s) don't match", positionEndObject, key));
    }
  }

  /**
   * Retrieves the codes from the JSON object.
   * Codes are in format of am object "key": "value", where the key is the code and the value is the label.
   * Codes can also be in form of a codelist, which is a reference to a list of codes loaded from the codelists object
   * of the same schema.
   * @param codesHolder Meant to be either an indicator, a subfield or a position
   * @param objectKey The key of the codes object, "codes" or "codelist"
   * @return The list of codes for the respective codesHolder
   */
  private List<EncodedValue> getCodes(JSONObject codesHolder, String objectKey) {
    Object listValue = codesHolder.get(objectKey);
    if (listValue instanceof String) {
      return codeLists.computeIfAbsent((String) listValue, s -> new ArrayList<>());
    }

    JSONObject codes = (JSONObject) listValue;
    if (codes == null) {
      return new ArrayList<>();
    }
    return processCodes(codes);
  }

  private EncodedValue getPattern(JSONObject position) {
    String pattern = (String) position.get(PATTERN);
    if (pattern == null) {
      return null;
    }

    // Pattern is a regular expression, so we need to check if it is valid
    try {
      Pattern.compile(pattern);
    } catch (Exception e) {
      logger.warning(() -> "invalid pattern: " + pattern);
      return null;
    }

    EncodedValue codePattern = new EncodedValue(pattern, PATTERN);
    codePattern.setRegex(true);

    return codePattern;
  }

  private void assignGroupsToPattern(JSONObject position, EncodedValue codePattern) {
    JSONObject groups = (JSONObject) position.get("groups");
    if (groups == null) {
      return;
    }

    Map<Integer, String> regexGroups = new HashMap<>();
    for (Map.Entry<String, Object> groupEntry : groups.entrySet()) {
      try {
        int groupNumber = Integer.parseInt(groupEntry.getKey());
        // groupEntry.getValue() is an object containing the label of the group
        JSONObject groupBody = (JSONObject) groupEntry.getValue();
        String groupLabel = (String) groupBody.get(LABEL);
        regexGroups.put(groupNumber, groupLabel);
      } catch (NumberFormatException e) {
        // If the group number is not a number, then it is invalid and no group is added
        logger.warning(() -> "invalid group number: " + groupEntry.getKey());
        return;
      }
    }
    codePattern.setRegexGroups(regexGroups);
  }

  private List<EncodedValue> processCodes(JSONObject codes) {
    List<EncodedValue> encodedValues = new ArrayList<>();
    for (Map.Entry<String, Object> codeEntry : codes.entrySet()) {
      String code = codeEntry.getKey();
      String codeLabel = (String) codeEntry.getValue();

      if (code.startsWith("//")) {
        continue;
      }

      addCode(encodedValues, code, codeLabel);

      // Code ranges were abolished in favor of patterns
    }

    return encodedValues;
  }

  private void addCode(List<EncodedValue> encodedValues, String code, String codeLabel) {
    EncodedValue encodedValue = new EncodedValue(code, codeLabel);
    encodedValues.add(encodedValue);
  }
}
