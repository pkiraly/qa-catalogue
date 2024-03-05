package de.gwdg.metadataqa.marc.dao.record;

import de.gwdg.metadataqa.marc.dao.Control001;
import de.gwdg.metadataqa.marc.dao.Marc21Leader;
import de.gwdg.metadataqa.marc.dao.MarcControlField;
import de.gwdg.metadataqa.marc.dao.MarcLeader;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
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


}
