package de.gwdg.metadataqa.marc.dao.record;

import de.gwdg.metadataqa.marc.dao.Control001;
import de.gwdg.metadataqa.marc.dao.Marc21Leader;
import de.gwdg.metadataqa.marc.dao.MarcControlField;
import de.gwdg.metadataqa.marc.dao.MarcLeader;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.utils.SchemaSpec;
import de.gwdg.metadataqa.marc.utils.marcspec.MarcSpec;
import de.gwdg.metadataqa.marc.utils.marcspec.MarcSpecExtractor;
import de.gwdg.metadataqa.marc.utils.unimarc.UnimarcConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a MARC record (primarily used for MARC21 and UNIMARC).
 */
public abstract class MarcRecord extends BibliographicRecord {

  protected MarcLeader leader;
  protected MarcControlField control001;
  protected MarcControlField control003;
  protected MarcControlField control005;
  private static final List<String> simpleControlTags = Arrays.asList("001", "003", "005");


  protected MarcRecord() {
    super();
    control001 = new Control001(id);
  }

  protected MarcRecord(String id) {
    super(id);
  }

  public List<MarcControlField> getControlfields() {
    List<MarcControlField> list = new ArrayList<>();
    list.add(control001);
    if (control003 != null)
      list.add(control003);
    if (control005 != null)
      list.add(control005);
    return list;
  }

  public List<MarcControlField> getSimpleControlfields() {
    return Arrays.asList(
        control001, control003, control005
    );
  }

  public void setLeader(MarcLeader leader) {
    this.leader = leader;
    leader.setMarcRecord(this);
  }

  public void setLeader(String leader) {
    this.leader = new Marc21Leader(leader);
    this.leader.setMarcRecord(this);
  }

  public void setLeader(String leader, MarcVersion marcVersion) {
    if (marcVersion.equals(MarcVersion.UNIMARC)) {
      leader = UnimarcConverter.leaderFromUnimarc(leader);
    }

    this.leader = new Marc21Leader(leader);
    this.leader.setMarcRecord(this);
  }

  public MarcLeader.Type getType() {
    return leader != null ? leader.getType() : MarcLeader.Type.BOOKS;
  }

  public MarcLeader getLeader() {
    return leader;
  }

  public MarcControlField getControl001() {
    return control001;
  }

  public BibliographicRecord setControl001(MarcControlField control001) {
    this.control001 = control001;
    control001.setMarcRecord(this);
    controlfieldIndex.put(control001.getDefinition().getTag(), List.of(control001));
    return this;
  }

  public MarcControlField getControl003() {
    return control003;
  }

  public void setControl003(MarcControlField control003) {
    this.control003 = control003;
    control003.setMarcRecord(this);
    controlfieldIndex.put(control003.getDefinition().getTag(), List.of(control003));
  }

  public MarcControlField getControl005() {
    return control005;
  }

  public void setControl005(MarcControlField control005) {
    this.control005 = control005;
    control005.setMarcRecord(this);
    controlfieldIndex.put(control005.getDefinition().getTag(), List.of(control005));
  }

  @Override
  public List<String> select(SchemaSpec schemaSpec) {
    List<String> results = new ArrayList<>();
    /*
    if (schemaSpec instanceof MarcSpec) {
      MarcSpec marcSpec = (MarcSpec) schemaSpec;

      if (marcSpec.getFieldTag().equals("LDR") && leader != null && StringUtils.isNotEmpty(leader.getContent())) {
        return selectLeader(marcSpec);
      }

      if (controlfieldIndex.containsKey(marcSpec.getFieldTag())) {
        return selectControlFields(marcSpec);
      }

      if (datafieldIndex.containsKey(marcSpec.getFieldTag())) {
        return selectDatafields(marcSpec);
      }
    } else if (schemaSpec instanceof MarcSpec2) {

     */
      MarcSpec marcSpec = (MarcSpec) schemaSpec;
      Object result = MarcSpecExtractor.extract((MarcRecord) this, marcSpec);
      if (result != null) {
        if (result instanceof List)
          results.addAll((List) result);
        else if (result instanceof String)
          results.add((String) result);
      }
    // }

    return results;
  }

  /*
  protected List<String> selectLeader(MarcSpec selector) {
    List<String> selectedResults = new ArrayList<>();
    if (selector.hasRangeSelector()) {
      selectedResults.add(selector.selectRange(leader.getContent()));
    } else {
      selectedResults.add(leader.getContent());
    }
    return selectedResults;
  }

  protected List<String> selectControlFields(MarcSpec selector) {
    List<String> selectedResults = new ArrayList<>();
    for (MarcControlField controlField : controlfieldIndex.get(selector.getFieldTag())) {
      if (controlField == null) {
        continue;
      }
      if (!simpleControlTags.contains(controlField.getDefinition().getTag())) {
        // TODO: check control subfields
      }
      if (selector.hasRangeSelector()) {
        selectedResults.add(selector.selectRange(controlField.getContent()));
      } else {
        selectedResults.add(controlField.getContent());
      }
    }
    return selectedResults;
  }
  */

}
