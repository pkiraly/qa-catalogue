package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
import de.gwdg.metadataqa.marc.utils.pica.organisation.K10Organisation;
import de.gwdg.metadataqa.marc.utils.pica.organisation.K10OrganisationReader;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class GroupSelector {
  private OrganizationCodes organizationCodes;
  private Map<String, K10Organisation> orgMap;

  public GroupSelector() {
    this(null);
  }

  public GroupSelector(String groupListFile) {
    if (groupListFile == null) {
      System.err.println("initialize OrganizationCodes");
      organizationCodes = OrganizationCodes.getInstance();
    } else {
      System.err.println("initialize from file");
      orgMap = K10OrganisationReader.fileToCodeList(groupListFile);
    }
  }

  public String getOrgName(String key) {
    String orgName = key.equals("0") ? "all" : key;
    if (organizationCodes != null) {
      EncodedValue x = organizationCodes.getCode("DE-" + key);
      orgName = x == null ? key : x.getLabel();
    } else if (orgMap != null) {
      K10Organisation x = orgMap.get(key);
      if (x != null) {
        if (StringUtils.isNotBlank(x.getName()))
          orgName = x.getName();
        else if (StringUtils.isNotBlank(x.getCode()))
          orgName = x.getCode();
      }
    }
    return orgName;
  }
}
