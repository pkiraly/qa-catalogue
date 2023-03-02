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
import de.gwdg.metadataqa.marc.definition.general.validator.SubfieldValidator;
import de.gwdg.metadataqa.marc.utils.pica.PicaFieldDefinition;
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
  private BibliographicFieldDefinition parent;
  private SubfieldValidator validator;
  private SubfieldContentParser contentParser;
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
        if (code.equals("#"))
          codeForIndex = "_hash";
        else if (code.equals("*"))
          codeForIndex = "_star";
        else if (code.equals("@"))
          codeForIndex = "_at";
        else
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

  public SubfieldDefinition setLocalCodes(MarcVersion version, String... input) {
    if (localCodes == null)
      localCodes = new EnumMap<>(MarcVersion.class);
    localCodes.put(version, new ArrayList<>());
    for (int i = 0; i < input.length; i += 2) {
      localCodes.get(version).add(new EncodedValue(input[i], input[i+1]));
    }
    return this;
  }

  public EncodedValue getCode(String code) {
    return getCode(codes, code);
  }

  public EncodedValue getCode(List<EncodedValue> codes, String otherCode) {
    for (EncodedValue code : codes)
      if (code.getCode().equals(otherCode))
        return code;
      else if (code.isRange() && code.getRange().isValid(otherCode))
        return code;

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
    List<EncodedValue> codes = getLocalCodes(version);
    if (codes == null)
      return null;
    return getCode(codes, code);
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

  public String resolve(String value) {
    if (codeList != null && codeList.isValid(value))
      return codeList.getCode(value).getLabel();

    if (codes != null) {
      EncodedValue code = getCode(value);
      if (code != null)
        return code.getLabel();
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

  @Override
  public String toString() {
    return "SubfieldDefinition{" +
      "code='" + code + '\'' +
      ", cardinality='" + cardinalityCode + '\'' +
      ", label='" + label + '\'' +
      '}';
  }
}