package de.gwdg.metadataqa.marc.utils.unimarc;

import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.utils.marcreader.schema.AvramMarc21SchemaReader;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads a UNIMARC schema from a JSON file, so that it can be used to process UNIMARC records.
 */
public class UnimarcSchemaReader {
  private static final Logger logger = Logger.getLogger(AvramMarc21SchemaReader.class.getCanonicalName());
  private static final String LABEL = "label";
  private static final String TAG = "tag";
  private static final String REQUIRED = "required";
  private static final String REPEATABLE = "repeatable";
  private static final String INDICATOR_1 = "indicator1";
  private static final String INDICATOR_2 = "indicator2";
  private static final String SUBFIELDS = "subfields";
  private static final String POSITIONS = "positions";
  private static final String CODES = "codes";
  private static final String CODELIST = "codelist";
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
      POSITIONS, 1);
  private static final Map<String, Integer> knownIndicatorProperties = Map.of(
      LABEL, 1,
      CODES,1);

  private static final String UNIMARC_EMPTY_CODE = "#";

  private final JSONParser parser = new JSONParser(JSONParser.MODE_RFC4627);
  private final UnimarcSchemaManager schema = new UnimarcSchemaManager();

  public UnimarcSchemaManager createSchema(InputStream inputStream) {
    try {
      JSONObject obj = readFile(inputStream);
      processFields(obj);
    } catch (ParseException e) {
      logger.severe(e.getLocalizedMessage());
    }

    return schema;
  }

  public UnimarcSchemaManager createSchema(String filename) {
    try {
      JSONObject obj = readFile(filename);
      processFields(obj);
    } catch (FileNotFoundException | ParseException e) {
      logger.severe(e.getLocalizedMessage());
    }

    return schema;
  }

  private JSONObject readFile(String filename) throws FileNotFoundException, ParseException {
    FileReader reader = new FileReader(filename);
    return (JSONObject) parser.parse(reader);
  }

  private JSONObject readFile(InputStream stream) throws ParseException {
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
    List<EncodedValue> codes = getCodes(jsonIndicator, CODES);
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

      List<EncodedValue> codes = getCodes(jsonSubfield, CODELIST);
      UnimarcCodeList codeList = new UnimarcCodeList();
      codeList.setCodes(codes);
      subfieldDefinition.setCodeList(codeList);

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
          logger.warning(() -> "unhandled subfield property: " + property);
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

      String positionId = String.format("%s/%s", parentTag, positionEntry.getKey());
      positionDefinition.setId(positionId);

      List<EncodedValue> codes = getCodes(position, CODES);
      positionDefinition.setCodes(codes);

      // Get first length of the codes
      int codeLength = codes.stream().findFirst().map(EncodedValue::getCode).map(String::length).orElse(0);
      if (codeLength != 0) {
        // If the length is smaller than the position length, then the position is repeatable
        boolean isRepeatable = codeLength < positionEnd - positionStart;
        positionDefinition.setRepeatableContent(isRepeatable);

        // The unit length is the maximal length of the codes
        positionDefinition.setUnitLength(codeLength);
      }
      positionDefinitions.add(positionDefinition);

      // Check position places in the end. This doesn't produce any side effects except for log warnings.
      String positionKey = positionEntry.getKey();
      checkPositionPlaces(positionKey, positionStart, positionEndObject);
    }
    return positionDefinitions;
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
   * @param codesHolder Meant to be either an indicator, a subfield or a position
   * @param objectKey The key of the codes object, "codes" or "codelist"
   * @return The list of codes for the respective codesHolder
   */
  private List<EncodedValue> getCodes(JSONObject codesHolder, String objectKey) {
    JSONObject codes = (JSONObject) codesHolder.get(objectKey);
    if (codes == null) {
      return List.of();
    }
    List<EncodedValue> encodedValues = new ArrayList<>();
    for (Map.Entry<String, Object> codeEntry : codes.entrySet()) {
      String code = codeEntry.getKey();
      String codeLabel = (String) codes.get(code);

      if (code.startsWith("//")) {
        continue;
      }

      // If the code is in form of a range, then try to split it into the start and end
      // and generate codes for everything within the range

      List<Long> codeRange = getCodeRange(code);

      if (codeRange.isEmpty()) {
        addCode(encodedValues, code, codeLabel);
      }

      // Essentially, this for loop is only executed when codeRange.isEmpty() is false, so no need for else
      for (long codeFromRange : codeRange) {
        EncodedValue encodedValue = new EncodedValue(String.valueOf(codeFromRange), codeLabel);
        encodedValues.add(encodedValue);
      }
    }

    return encodedValues;
  }

  private void addCode(List<EncodedValue> encodedValues, String code, String codeLabel) {
    EncodedValue encodedValue = new EncodedValue(code, codeLabel);
    encodedValues.add(encodedValue);

    // In case of an empty code, and the empty code isn't only a whitespace, then add whitespace as well
    // This could potentially be done within one single EncodedValue
    // check if code marches UNIMARC_EMPTY_CODE multiple times using regex
    boolean isUnimarcEmptyCode = code.matches("^" + UNIMARC_EMPTY_CODE + "+$");
    if (isUnimarcEmptyCode && !code.isBlank()) {
      // Create a whitespace of that many spaces as the empty code has characters
      String whitespace = " ".repeat(code.length());
      EncodedValue emptyCode = new EncodedValue(whitespace, codeLabel);
      encodedValues.add(emptyCode);
    }
  }

  /**
   * Checks whether the code is in form of a range, and if so, generates a list of codes from the start to the end.
   * If the code isn't in form of a range, then an empty list is returned.
   * @param code The code to check
   * @return A list of codes, or an empty list
   */
  private List<Long> getCodeRange(String code) {

    // What's checked in here is whether the code is in form of a range, but only for digits
    Pattern rangePattern = Pattern.compile("^(\\d)+-(\\d+)$");

    // Get the start and end of the range and check that the start is smaller than the end.
    // If the start is smaller than the end, then generate a list of codes from the start to the end
    Matcher rangeMatcher = rangePattern.matcher(code);

    if (!rangeMatcher.matches()) {
      return List.of();
    }

    long start = Long.parseLong(rangeMatcher.group(1));
    long end = Long.parseLong(rangeMatcher.group(2));

    if (start >= end) {
      return List.of();
    }

    List<Long> range = new ArrayList<>();
    for (long i = start; i <= end; i++) {
      range.add(i);
    }
    return range;
  }
}
