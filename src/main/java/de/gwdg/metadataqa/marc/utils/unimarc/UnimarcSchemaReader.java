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
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads a UNIMARC schema from a JSON file, so that it can be used to process UNIMARC records.
 */
public class UnimarcSchemaReader {
  private static final String LABEL = "label";
  private static final String REPEATABLE = "repeatable";
  private static final String REQUIRED = "required";
  private static final String START = "start";
  private static final String END = "end";
  private static final String UNIMARC_EMPTY_CODE = "#";

  private static final Logger logger = Logger.getLogger(UnimarcSchemaReader.class.getCanonicalName());
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

      // In this situation, it isn't necessary to access the JSON value of 'tag' directly,
      // as it is already available as the key of the UNIMARC field.
      UnimarcFieldDefinition fieldDefinition = new UnimarcFieldDefinition(
          tag,
          (String) jsonField.get(LABEL),
          (boolean) jsonField.get(REPEATABLE),
          (boolean) jsonField.get(REQUIRED)
      );

      Indicator indicator1 = getIndicator(1, jsonField);
      fieldDefinition.setInd1(indicator1);
      Indicator indicator2 = getIndicator(2, jsonField);
      fieldDefinition.setInd2(indicator2);
      List<SubfieldDefinition> subfieldDefinitions = getSubfields(jsonField);
      fieldDefinition.setSubfieldDefinitions(subfieldDefinitions);

      schema.add(fieldDefinition);
    }
  }

  /**
   * @param indicatorNumber Either 1 or 2
   * @param jsonField The JSON object of the field
   */
  private Indicator getIndicator(int indicatorNumber, JSONObject jsonField) {
    JSONObject jsonIndicator = (JSONObject) jsonField.get("indicator" + indicatorNumber);
    if (jsonIndicator == null) {
      // Return an empty indicator which represent the empty values in order to conform with MARC21 fields
      return new Indicator();
    }

    Indicator indicator = new Indicator((String) jsonIndicator.get(LABEL));
    List<EncodedValue> codes = getCodes(jsonIndicator, "codes");
    indicator.setCodes(codes);

    return indicator;
  }

  private List<SubfieldDefinition> getSubfields(JSONObject jsonField) {
    // Subfields are a JSON object in our schema
    JSONObject subfields = (JSONObject) jsonField.get("subfields");
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

      List<EncodedValue> codes = getCodes(jsonSubfield, "codelist");
      UnimarcCodeList codeList = new UnimarcCodeList();
      codeList.setCodes(codes);
      subfieldDefinition.setCodeList(codeList);

      List<ControlfieldPositionDefinition> positions = getPositions(jsonSubfield);
      subfieldDefinition.setPositions(positions);

      subfieldDefinitions.add(subfieldDefinition);
    }
    return subfieldDefinitions;
  }

  private List<ControlfieldPositionDefinition> getPositions(JSONObject jsonSubfield) {
    JSONObject positions = (JSONObject) jsonSubfield.get("positions");
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
      List<EncodedValue> codes = getCodes(position, "codes");
      positionDefinition.setCodes(codes);

      // Get maximal length of codes
      OptionalInt maxCodeLength = codes.stream().mapToInt(code -> code.getCode().length()).max();
      if (maxCodeLength.isPresent()) {
        // If the maximal length is smaller than the position length, then the position is repeatable
        boolean isRepeatable = maxCodeLength.getAsInt() < positionEnd - positionStart;
        positionDefinition.setRepeatableContent(isRepeatable);

        // The unit length is the maximal length of the codes
        positionDefinition.setUnitLength(maxCodeLength.getAsInt());
      }
      positionDefinitions.add(positionDefinition);
    }
    return positionDefinitions;
  }

  /**
   * Retrieves the codes from the JSON object
   * @param codesHolder Meant to be either an indicator or a position
   * @param objectKey The key of the codes object, "codes" or "codelist"
   * @return A list of codes
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
