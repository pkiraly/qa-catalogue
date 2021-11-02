package de.gwdg.metadataqa.marc.analysis.bl;

import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.TagDefinitionLoader;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static de.gwdg.metadataqa.marc.analysis.bl.Band.BASIC;
import static de.gwdg.metadataqa.marc.analysis.bl.Band.EFFECTIVE;
import static de.gwdg.metadataqa.marc.analysis.bl.Band.SATISFACTORY;

public enum UseCase {
  /*******
   BASIC
   */
  // 245 — Title Statement / Title
  B01(BASIC, "FIND by the title or name of the entity described", "",
    "245$a", "Mandatory"),

  // 852: Location,
  // 856: Electronic Location and Access
  B02(BASIC, "OBTAIN the item or access to the content using shelfmark or link", "See also README B02",
    "852;856", "Mandatory"),

  // 506 — Restrictions on Access Note,
  // 540 — Terms Governing Use and Reproduction Note
  B03(BASIC, "SELECT in accordance with restrictions on access or reuse", "See  README B03",
    "506;540", "Mandatory if applicable"),

  // 336 — Content Type,
  // 337 — Media Type,
  // 338 — Carrier Type
  B04(BASIC, "SELECT by content, media  or carrier type", "",
    "336;337;338", "Mandatory"),
  B05(BASIC, "IDENTIFY or SELECT by attributes appropriate to the type of resource", "See specific tabs",
    "As specified", "Mandatory if applicable"),

  // 541 — Immediate Source of Acquisition Note,
  // 561 — Ownership and Custodial History
  // 700 — Added Entry - Personal Name
  B06(BASIC, "IDENTIFY the provenance of the item(s) described", "Copy specific policy applies",
    "541;561;700", "Mandatory if applicable"),

  // 021 ??? -- isn#t it 020?
  // 022 — International Standard Serial Number
  B07(BASIC, "FIND using a standard identifier ", "Monograph;Serial",
    "021;022", "Mandatory if applicable"),

  // 040 — Cataloging Source
  B08(BASIC, "IDENTIFY of SELECT by source of metadata", "",
    "040", "Mandatory"),

  // FMT -- cataloguing template (?)
  B09(BASIC, "MANAGE by using the cataloguing template appropriate to the resource/workflow", "",
    "FMT", "Mandatory"),

  // FIN - Finished cataloguing
  // 859 — Digital Resource Flag
  B10(BASIC, "MANAGE routing to services", "Record is finished (FIN=Y); Digital: 859$a is valid",
    "FIN;859", "Mandatory if applicable"),

  B11(BASIC, "MANAGE by date of publication (or coverage)", "",
    "008/6-14", "Mandatory"),
  B12(BASIC, "MANAGE by country of publication", "",
    "008/15-17", "Mandatory"),
  B13(BASIC, "MANAGE by language of content", "",
    "008/35-37", "Mandatory"),

  // 040 — Cataloging Source
  B14(BASIC, "MANAGE by source of cataloguing", "",
    "040", "Mandatory"),

  // 015 — National Bibliography Number
  B15(BASIC, "FIND by National Bibliography number", "Valid for BNB",
    "015", "Mandatory if applicable"),

  /********
   SATISFACTORY
   */
  // 130 — Main Entry - Uniform Title
  // 1XX$t title of work
  // 240 — Uniform Title
  // 730 — Added Entry - Uniform Title
  // What does 1XX/240 mean?
  S01(SATISFACTORY, "IDENTIFY the intellectual content of the resource",
    "The Work or Expression manifested needs to be  disambiguated or explicitly identified",
    "130;1XX$t;240;730;7XX$t", "Mandatory if applicable"),
  S02(SATISFACTORY, "IDENTIFY principle responsibility for the intellectual content of the resource",
    "Creator of the work is known",
    "1XX;7XX", "Mandatory if applicable"),
  // my additions: 052, 055, 072, 080, 084, 085, 086,
  S03(SATISFACTORY, "IDENTIFY the subject of the Work",
    "The Work satisfies the criteria for assignment of subject terms according to the subject system(s) being applied",
    "052;055;072;080;084;085;086;6XX", "Mandatory if applicable"),
  S04(SATISFACTORY, "IDENTIFY or SELECT by attributes appropriate to the type of resource",
    "See specific tabs",
    "As specified", "Mandatory if applicable"),

  // 250 — Edition Statement
  S05(SATISFACTORY, "IDENTIFY or SELECT by version or edition", "Edition statement or version",
    "250", "Mandatory if applicable"),

  // 264 — Production, Publication, Distribution, Manufacture, and Copyright Notice
  // 260 — Publication, Distribution, etc. (Imprint)
  S06(SATISFACTORY, "IDENTIFY or SELECT by production, publication,  distribution or manufacture details",
    "Publication statement ",
    "264;260", "Mandatory if applicable"),

  // 246 — Varying Form of Title
  S07(SATISFACTORY, "FIND by variant titles", "Manifestation has or is known by variant titles",
    "246", "Mandatory if applicable"),

  // 505 — Formatted Contents Note
  S08(SATISFACTORY, "SELECT by aggregated works", "Aggregate is of a type for which a contents note is required",
    "505", "Mandatory if applicable"),

  // 300 — Physical Description, $a Extent
  S09(SATISFACTORY, "SELECT by extent", "Extent is complete",
    "300$a", "Mandatory if applicable"),
  // 588 — Source of Description Note
  S10(SATISFACTORY, "MANAGE source of information", "Source is not title page or equivalent",
    "588", "Mandatory if applicable"),

  /*
     EFFECTIVE
   */
  //
  E01(EFFECTIVE, "IDENTIFY Agents with responsibility for augmentations of a Work, for example translators, illustrators, performers, etc. ",
    "Responsibility for augmentation, etc. can be determined",
    "7XX", "Mandatory if applicable"),
  // $e Relator term
  E02(EFFECTIVE, "NAVIGATE to/from an Agent associated with the resource", "Valid relationship identifier/URI",
    "1XX$e;7XX$e", "Mandatory if applicable"),

  // 082 — Dewey Decimal Classification Number
  E03(EFFECTIVE, "MANAGE collection by subject", "In scope for DDC",
    "082", "Mandatory if applicable"),
  E04(EFFECTIVE, "IDENTIFY former owners/custodians of the item described", "Provenance is considered significant",
    "7XX", "Mandatory if applicable"),
  E05(EFFECTIVE, "NAVIGATE to related Works, Expressions or Manifestations", "Derived works or expressions",
    "6XX;730;7XX$t;LKR", "Mandatory if applicable"),

  // 700 — Added Entry - Personal Name / Title of a work
  // 710 — Added Entry - Corporate Name / Title of a work
  // 730 — Added Entry - Uniform Title
  E06(EFFECTIVE, "FIND aggregated Works", "Manifestation embodies more than one significant Expression",
    "700$t;710$t;730", "Mandatory if applicable")
  ;

  private Matcher alphaMatcher = Pattern.compile("^[A-Z]{3}$").matcher("");
  private Band band;
  private String useCase;
  private String condition;
  private String encoding;
  private List<String> dataElelemnts;
  private List<Element> elements = new ArrayList<>();
  private String status;

  UseCase(Band band, String useCase, String condition, String encoding, String status) {
    this.band = band;
    this.useCase = useCase;
    this.condition = condition;
    this.encoding = encoding;
    this.status = status;
    createDataELements();
  }

  private void createDataELements() {
    dataElelemnts = Arrays.asList(encoding.split(";"));
    for (String element : dataElelemnts) {
      String[] parts = element.split("\\$", 2);
      String tag = parts[0];
      String subfield = parts.length > 1 ? parts[1] : null;
      List<DataFieldDefinition> candidates = TagDefinitionLoader.findPatterns(tag, MarcVersion.BL);
      if (!candidates.isEmpty()) {
        for (DataFieldDefinition definition : candidates)
          if (subfield == null || definition.getSubfield(subfield) != null)
            elements.add(new Element(definition.getTag(), definition, subfield));
      } else if (alphaMatcher.reset(tag).matches()) {
        elements.add(new Element(tag, null, subfield));
      }
    }
  }

  public Band getBand() {
    return band;
  }

  public String getUseCase() {
    return useCase;
  }

  public String getCondition() {
    return condition;
  }

  public String getEncoding() {
    return encoding;
  }

  public String getStatus() {
    return status;
  }

  public List<String> getDataElelemnts() {
    return dataElelemnts;
  }

  public List<Element> getElements() {
    return elements;
  }

  public List<String> getDataElelemntsNormalized() {
    return elements.stream().map(e -> e.toString()).collect(Collectors.toList());
  }

}
