package de.gwdg.metadataqa.marc.cli;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.utils.BibiographicPath;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPathParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class QACli {

  protected BibiographicPath groupBy = null;

  protected void initializeGroups(String groupBy, boolean isPica) {
    if (groupBy != null) {
      this.groupBy = isPica
        ? PicaPathParser.parse(groupBy)
        : null; // TODO: create it for MARC21
    }
  }

  protected <T extends CommonParameters> void saveParameters(String fileName, T parameters) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      String json = mapper.writeValueAsString(parameters);
      Map<String, Object> configuration = mapper.readValue(json, new TypeReference<>(){});
      configuration.put("mqaf.version", de.gwdg.metadataqa.api.cli.Version.getVersion());
      configuration.put("qa-catalogue.version", de.gwdg.metadataqa.marc.cli.Version.getVersion());
      File configFile = Paths.get(parameters.getOutputDir(), fileName).toFile();
      mapper.writeValue(configFile, configuration);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static Set<String> extractGroupIds(List<String> idLists) {
    Set<String> groupIds = new HashSet<>();
    groupIds.add("all");
    if (idLists != null) {
      for (String idList : idLists) {
        String[] ids = idList.split(",");
        for (String id : ids) {
          groupIds.add(id);
        }
      }
    }
    return groupIds;
  }

}
