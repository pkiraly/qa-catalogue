package de.gwdg.metadataqa.marc.definition.structure;

import de.gwdg.metadataqa.marc.Code;
import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.CompilanceLevel;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.general.codelist.CodeList;
import de.gwdg.metadataqa.marc.definition.general.parser.SubfieldContentParser;
import de.gwdg.metadataqa.marc.definition.general.validator.SubfieldValidator;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;
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
  private DataFieldDefinition parent;
  private SubfieldValidator validator;
  private SubfieldContentParser contentParser;
  protected CodeList codeList;
  private List<Code> codes;
  private Map<MarcVersion, List<Code>> localCodes;
  private List<String> allowedCodes;
  private String codeForIndex = null;
  private List<ControlfieldPositionDefinition> positions;
  private List<FRBRFunction> functions;
  private CompilanceLevel nationalCompilanceLevel;
  private CompilanceLevel minimalCompilanceLevel;
  private List<MarcVersion> disallowedIn;

  public String getCodeForIndex() {
    if (codeForIndex == null) {
      if (mqTag != null) {
        if (mqTag.equals("rdf:value"))
          codeForIndex = "";
        else
          codeForIndex = "_" + mqTag;
      } else if (bibframeTag != null) {
        switch (bibframeTag) {
          case "rdf:value": codeForIndex = ""; break;
          case "rdfs:label": codeForIndex = "label"; break;
          default: codeForIndex = "_" + bibframeTag; break;
        }
      } else {
        codeForIndex = "_" + code;
      }
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

  public String getCode() {
    return code;
  }

  public String getPath() {
    return String.format("%s$%s", getParent().getTag(), getCode());
  }

  public SubfieldDefinition setCodes(List<Code> codes) {
    this.codes = codes;
    return this;
  }

  public SubfieldDefinition setCodes(String... input) {
    codes = new ArrayList<>();
    for (int i = 0; i<input.length; i+=2) {
      codes.add(new Code(input[i], input[i+1]));
    }
    return this;
  }

  public SubfieldDefinition setLocalCodes(MarcVersion version, String... input) {
    if (localCodes == null)
      localCodes = new HashMap<>();
    localCodes.put(version, new ArrayList<>());
    for (int i = 0; i < input.length; i += 2) {
      localCodes.get(version).add(new Code(input[i], input[i+1]));
    }
    return this;
  }

  public Code getCode(String _code) {
    return getCode(codes, _code);
  }

  public Code getCode(List<Code> _codes, String _code) {
    for (Code code : _codes) {
      if (code.getCode().equals(_code)) {
        return code;
      } else if (code.isRange() && code.getRange().isValid(_code)) {
        return code;
      }
    }
    return null;
  }

  public List<Code> getCodes() {
    return codes;
  }

  public Map<MarcVersion, List<Code>> getLocalCodes() {
    return localCodes;
  }

  public List<Code> getLocalCodes(MarcVersion version) {
    return localCodes.getOrDefault(version, null);
  }

  public Code getLocalCode(MarcVersion version, String _code) {
    List<Code> _codes = getLocalCodes(version);
    if (_codes == null)
      return null;
    return getCode(_codes, _code);
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

  private void processIndicatorType(String types) {
    allowedCodes = new ArrayList<>();
    if (types.equals("blank")) {
      allowedCodes.add(" ");
    } else {
      for (int i = 0, len = types.length(); i < len; i++) {
        String type = String.valueOf(types.charAt(i));
        if (type.equals("b")) {
          type = " ";
        }
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

  public String resolve(String value) {
    if (codeList != null && codeList.isValid(value))
      return codeList.getCode(value).getLabel();

    if (codes != null) {
      Code code = getCode(value);
      if (code != null)
        return code.getLabel();
    }

    return value;
  }

  public Map<String, String> resolvePositional(String value) {
    Map<String, String> pairs = new LinkedHashMap<>();
    int i = 0;
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

  public DataFieldDefinition getParent() {
    return parent;
  }

  public List<FRBRFunction> getFrbrFunctions() {
    return functions;
  }

  public SubfieldDefinition setParent(DataFieldDefinition parent) {
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

  @Override
  public String toString() {
    return "MarcSubfield{" +
      "code='" + code + '\'' +
      ", typeCode='" + cardinalityCode + '\'' +
      ", label='" + label + '\'' +
      '}';
  }

}