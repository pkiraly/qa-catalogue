package de.gwdg.metadataqa.marc.definition.structure;

import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.CompilanceLevel;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.bibliographic.BibliographicFieldDefinition;
import de.gwdg.metadataqa.marc.definition.general.codelist.CodeList;
import de.gwdg.metadataqa.marc.definition.general.parser.SubfieldContentParser;
import de.gwdg.metadataqa.marc.definition.general.parser.SubfieldContentSplitter;
import de.gwdg.metadataqa.marc.definition.general.validator.SubfieldValidator;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class SubfieldDefinition implements Serializable {

  private static final Logger logger = Logger.getLogger(SubfieldDefinition.class.getCanonicalName());

  private String code;
  private String bibframeTag;
  private String mqTag;
  private String cardinalityCode;
  private String label;
  private BibliographicFieldDefinition parent;
  private SubfieldValidator validator;
  private SubfieldContentParser contentParser;
  private SubfieldContentSplitter contentSplitter;
  private boolean hasCodeList = true;
  protected CodeList codeList;
  private List<EncodedValue> codes;
  private Map<MarcVersion, List<EncodedValue>> localCodes;
  private List<String> allowedCodes;
  private String codeForIndex = null;
  private List<ControlfieldPositionDefinition> positions;
  private List<FRBRFunction> functions;
  private CompilanceLevel nationalCompilanceLevel;
  private CompilanceLevel minimalCompilanceLevel;
  private List<MarcVersion> disallowedIn;
  private MarcVersion marcVersion = null;

  /**
   * Returns the subfield code needed for Solr indexing. If MQ or BIBFRAME tags are defined, they are used in that order.
   * Otherwise, the code is prefixed with an underscore (and special characters are replaced with their names).
   * @implNote The code that's referred to here is the identifier of the subfield and not code in the sense of possible
   * values of the subfield.
   * @return The subfield code for Solr indexing.
   */
  public String getCodeForIndex() {
    if (codeForIndex != null) {
      return codeForIndex;
    }

    if (mqTag != null) {
      if (mqTag.equals("rdf:value"))
        codeForIndex = "";
      else
        codeForIndex = mqTag;

      return codeForIndex;
    }

    if (bibframeTag != null) {
      switch (bibframeTag) {
        case "rdf:value": codeForIndex = ""; break;
        case "rdfs:label": codeForIndex = "label"; break;
        default: codeForIndex = bibframeTag; break;
      }
      return codeForIndex;
    }

    switch (code) {
      case "#":
        codeForIndex = "hash";
        break;
      case "*":
        codeForIndex = "star";
        break;
      case "@":
        codeForIndex = "at";
        break;
      default:
        codeForIndex = code;
        break;
    }

    return codeForIndex;
  }

  /**
   * Create a MarcSubfield object
   *
   * @param code The subfield code
   * @param label The description of the code
   */
  public SubfieldDefinition(String code, String label) {
    this.code = code;
    this.label = label;
  }

  public SubfieldDefinition(String code, String label, String cardinalityCode) {
    this.code = code;
    this.label = label;
    this.cardinalityCode = cardinalityCode;
    if (code.startsWith("ind")) {
      processIndicatorType(cardinalityCode);
    }
  }

  public SubfieldDefinition(String code, String label, boolean repeatable) {
    this.code = code;
    this.label = label;

    // this could probably be changed to a Cardinality object
    this.cardinalityCode = repeatable ? Cardinality.Repeatable.getCode() : Cardinality.Nonrepeatable.getCode();
  }

  public String getCode() {
    return code;
  }

  public String getPath() {
    return String.format("%s$%s", getParent().getExtendedTag(), getCode());
  }

  public SubfieldDefinition setCodes(List<EncodedValue> codes) {
    this.codes = codes;
    return this;
  }

  public SubfieldDefinition setCodes(String... input) {
    codes = new ArrayList<>();
    for (int i = 0; i<input.length; i+=2) {
      codes.add(new EncodedValue(input[i], input[i+1]));
    }
    return this;
  }

  /**
   * Local codes are defined by a particular library, not in the LoC MARC21 definition
   * @param version
   * @param input
   * @return
   */
  public SubfieldDefinition setLocalCodes(MarcVersion version, String... input) {
    if (localCodes == null)
      localCodes = new EnumMap<>(MarcVersion.class);
    localCodes.put(version, new ArrayList<>());
    for (int i = 0; i < input.length; i += 2) {
      localCodes.get(version).add(new EncodedValue(input[i], input[i+1]));
    }
    return this;
  }

  /**
   * Get the EncodedValue object (label+code) from the list of codes of this subfield for the given code.
   * @param code The code to look up.
   * @return The EncodedValue object for the given code or null if not found.
   */
  public EncodedValue getCode(String code) {
    return getCode(codes, code);
  }

  /**
   * Get the EncodedValue object (label+code) for a given subfield code.
   * @param codes The list of EncodedValue objects (codes of the subfield).
   * @param code The code to look up.
   * @return The EncodedValue object for the given code or null if not found.
   */
  public EncodedValue getCode(List<EncodedValue> codes, String code) {
    for (EncodedValue encodedValue : codes) {
      if (encodedValue.getCode().equals(code)
        || encodedValue.isRange() && encodedValue.getRange().isValid(code)) {
        return encodedValue;
      }
    }

    return null;
  }

  public List<EncodedValue> getCodes() {
    return codes;
  }

  public Map<MarcVersion, List<EncodedValue>> getLocalCodes() {
    return localCodes;
  }

  public List<EncodedValue> getLocalCodes(MarcVersion version) {
    return localCodes.getOrDefault(version, null);
  }

  public EncodedValue getLocalCode(MarcVersion version, String code) {
    List<EncodedValue> localEncodedValues = getLocalCodes(version);
    if (localEncodedValues == null)
      return null;
    return getCode(localEncodedValues, code);
  }

  public String getCardinalityCode() {
    return cardinalityCode;
  }

  public Cardinality getCardinality() {
    return Cardinality.byCode(cardinalityCode);
  }

  public String getLabel() {
    return label;
  }

  public List<String> getAllowedCodes() {
    return allowedCodes;
  }

  public SubfieldContentParser getContentParser() {
    return contentParser;
  }

  public boolean hasContentParser() {
    return contentParser != null;
  }

  public SubfieldDefinition setContentParser(SubfieldContentParser contentParser) {
    this.contentParser = contentParser;
    return this;
  }

  public boolean hasContentSplitter() {
    return contentSplitter != null;
  }

  public SubfieldContentSplitter getContentSplitter() {
    return contentSplitter;
  }

  public SubfieldDefinition setContentSplitter(SubfieldContentSplitter contentSplitter) {
    this.contentSplitter = contentSplitter;
    return this;
  }


  private void processIndicatorType(String types) {
    allowedCodes = new ArrayList<>();
    if (types.equals("blank")) {
      allowedCodes.add(" ");
    } else {
      for (int i = 0, len = types.length(); i < len; i++) {
        String type = String.valueOf(types.charAt(i));
        if (type.equals("b"))
          type = " ";

        allowedCodes.add(type);
      }
    }
  }

  public SubfieldDefinition setValidator(SubfieldValidator validator) {
    this.validator = validator;
    return this;
  }

  public boolean hasValidator() {
    return validator != null;
  }

  public SubfieldValidator getValidator() {
    return validator;
  }

  public SubfieldDefinition setCodeList(CodeList codeList) {
    this.codeList = codeList;
    return this;
  }

  public CodeList getCodeList() {
    return codeList;
  }

  /**
   * Resolves the value of a subfield to its label if it can be found in the codeList or the list of codes. Otherwise,
   * it returns the value as is.
   * @param value The value of the subfield to resolve.
   * @return The resolved value. Either the label of the code or the value itself.
   */
  public String resolve(String value) {
    // If the code value is present in the code list, return the label
    if (codeList != null && codeList.isValid(value)) {
      return codeList.getCode(value).getLabel();
    }

    // If no codes are defined for this subfield, return the value as is
    if (codes == null) {
      return value;
    }

    // If the code value is present in the list of codes, return the label
    EncodedValue resolvedCode = getCode(value);
    if (resolvedCode != null) {
      return resolvedCode.getLabel();
    }

    return value;
  }

  public Map<String, String> resolvePositional(String value) {
    Map<String, String> pairs = new LinkedHashMap<>();
    var i = 0;
    for (ControlfieldPositionDefinition def : getPositions()) {
      try {
        String part = Utils.substring(value, def.getPositionStart(), def.getPositionEnd());
        String resolved = def.resolve(part);
        String suffix = StringUtils.isNotBlank(def.getMqTag()) ? def.getMqTag() : String.valueOf(i);
        pairs.put(suffix, resolved);
      } catch (StringIndexOutOfBoundsException e) {
        logger.warning(getPath() + ": " + e.getLocalizedMessage());
      }
      i++;
    }
    return pairs;
  }

  public String getBibframeTag() {
    return bibframeTag;
  }

  public SubfieldDefinition setBibframeTag(String bibframeTag) {
    this.bibframeTag = bibframeTag;
    return this;
  }

  public String getMqTag() {
    return mqTag;
  }

  public BibliographicFieldDefinition getParent() {
    return parent;
  }

  public List<FRBRFunction> getFrbrFunctions() {
    return functions;
  }

  public SubfieldDefinition setParent(BibliographicFieldDefinition parent) {
    this.parent = parent;
    return this;
  }

  public SubfieldDefinition setMqTag(String mqTag) {
    this.mqTag = mqTag;
    return this;
  }

  public void setPositions(List<ControlfieldPositionDefinition> positions) {
    this.positions = positions;
  }

  public List<ControlfieldPositionDefinition> getPositions() {
    return positions;
  }

  public boolean hasPositions() {
    return positions != null;
  }

  public ControlfieldPositionDefinition getPosition(int start, int end) {
    for (ControlfieldPositionDefinition def : positions) {
      if (def.getPositionStart() == start && def.getPositionEnd() == end) {
        return def;
      }
    }
    return null;
  }

  public SubfieldDefinition setCompilanceLevels(String national) {
    setNationalCompilanceLevel(national);
    return this;
  }

  public SubfieldDefinition setCompilanceLevels(String national, String minimal) {
    setNationalCompilanceLevel(national);
    setMinimalCompilanceLevel(minimal);
    return this;
  }

  public CompilanceLevel getNationalCompilanceLevel() {
    return nationalCompilanceLevel;
  }

  public SubfieldDefinition setNationalCompilanceLevel(CompilanceLevel nationalLevel) {
    this.nationalCompilanceLevel = nationalLevel;
    return this;
  }

  public SubfieldDefinition setNationalCompilanceLevel(String level) {
    if (StringUtils.isNotBlank(level))
      this.nationalCompilanceLevel = CompilanceLevel.byAbbreviation(level);
    return this;
  }

  public CompilanceLevel getMinimalCompilanceLevel() {
    return minimalCompilanceLevel;
  }

  public SubfieldDefinition setMinimalCompilanceLevel(String level) {
    if (StringUtils.isNotBlank(level))
      this.minimalCompilanceLevel = CompilanceLevel.byAbbreviation(level);
    return this;
  }

  public SubfieldDefinition setMinimalCompilanceLevel(CompilanceLevel minimalLevel) {
    this.minimalCompilanceLevel = minimalLevel;
    return this;
  }

  public SubfieldDefinition setFrbrFunctions(FRBRFunction... functions) {
    this.functions = Arrays.asList(functions);
    return this;
  }

  public SubfieldDefinition disallowIn(MarcVersion... versions) {
    this.disallowedIn = Arrays.asList(versions);
    return this;
  }

  public List<MarcVersion> getDisallowedIn() {
    return disallowedIn;
  }

  public boolean isDisallowedIn(MarcVersion marcVersion) {
    return disallowedIn != null &&
           disallowedIn.contains(marcVersion);
  }

  public MarcVersion getMarcVersion() {
    return marcVersion;
  }

  public void setMarcVersion(MarcVersion marcVersion) {
    this.marcVersion = marcVersion;
  }

  public boolean isRepeatable() {
    return getCardinality().equals(Cardinality.Repeatable);
  }

  public boolean hasCodeList() {
    return hasCodeList;
  }

  public void hasCodeList(boolean hasCodeList) {
    this.hasCodeList = hasCodeList;
  }

  @Override
  public String toString() {
    return "SubfieldDefinition{" +
      "code='" + code + '\'' +
      ", cardinality='" + cardinalityCode + '\'' +
      ", label='" + label + '\'' +
      '}';
  }

}