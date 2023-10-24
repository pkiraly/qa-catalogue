package de.gwdg.metadataqa.marc.cli;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.gwdg.metadataqa.marc.CsvUtils;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.ValidatorParameters;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.utils.BibiographicPath;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPath;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPathParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class QACli<T extends CommonParameters> {
  private static final Logger logger = Logger.getLogger(QACli.class.getCanonicalName());
  protected T parameters;

  public static final String ALL = "0";
  protected BibiographicPath groupBy = null;
  protected File idCollectorFile;
  private FileTime jarModifiedTime;
  private boolean isJarModifiedTimeDetected = false;
  protected boolean doSaveGroupIds = true;

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
      logger.log(Level.INFO, "Saving configuration to {0}.", configFile.getAbsolutePath());
      mapper.writeValue(configFile, configuration);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected Set<String> getGroupIds(CommonParameters parameters, BibliographicRecord bibliographicRecord) {
    if (this.groupBy != null) {
      List<String> idLists = parameters.isPica() ? bibliographicRecord.select((PicaPath) groupBy) : null; // TODO: MARC21
      return QACli.extractGroupIds(idLists);
    }
    return new HashSet<>();
  }

  protected void saveGroupIds(String recordId, Set<String> groupIds) {
    if (doGroups() && !groupIds.isEmpty())
      for (String groupId : groupIds)
        printToFile(idCollectorFile, CsvUtils.createCsv(recordId, groupId));
  }


  public static Set<String> extractGroupIds(List<String> idLists) {
    Set<String> groupIds = new HashSet<>();
    groupIds.add(ALL);
    if (idLists != null) {
      for (String idList : idLists) {
        String[] ids = idList.split(",");
        groupIds.addAll(Arrays.asList(ids));
      }
    }
    return groupIds;
  }

  public boolean doGroups() {
    return groupBy != null;
  }

  protected boolean isJarNewerThan(String outputDir, String fileName) {
    try {
      initializeJarModifiedTime();
      File reportFile = new File(outputDir, fileName);
      if (!reportFile.exists())
        return true;
      else {
        FileTime groupModifiedTime = Files.readAttributes(reportFile.toPath(), BasicFileAttributes.class).lastModifiedTime();
        return (jarModifiedTime == null || jarModifiedTime.compareTo(groupModifiedTime) > 0);
      }
    } catch (IOException e) {
      logger.severe("Error during prepareReportFile: " + e);
    }
    return false;
  }

  protected void initializeMeta(CommonParameters parameters) {
    File idCollectorMeta = new File(parameters.getOutputDir(), "id-groupid.meta.txt");
    String currentFileList = getFilesWithDate(parameters.getArgs());
    if (isJarNewerThan(parameters.getOutputDir(), "id-groupid.csv")) {
      if (!idCollectorMeta.delete())
        logger.severe("id-groupid.meta.txt has not been deleted.");
    } else {
      if (idCollectorMeta.exists()) {
        try {
          String storedFileList = FileUtils.readFileToString(idCollectorMeta, StandardCharsets.UTF_8).trim();
          doSaveGroupIds = ! currentFileList.equals(storedFileList);
          if (!idCollectorMeta.delete())
            logger.severe("id-groupid.meta.txt has not been deleted.");
        } catch (IOException e) {
          logger.severe(e.getLocalizedMessage());
        }
      }
    }
    printToFile(idCollectorMeta, currentFileList);
  }

  private String getFilesWithDate(String[] fileNames) {
    List<String> filesWithDate = new ArrayList<>();
    for (String fileName : fileNames) {
      try {
        FileTime modifiedTime = Files.readAttributes(new File(fileName).toPath(), BasicFileAttributes.class).lastModifiedTime();
        filesWithDate.add(fileName + ":" + modifiedTime.toString());
      } catch (IOException e) {
        logger.warning(e.getLocalizedMessage());
      }
    }
    return StringUtils.join(filesWithDate, ",");
  }

  protected File prepareReportFile(String outputDir, String fileName) {
    File reportFile = new File(outputDir, fileName);
    if (reportFile.exists() && !reportFile.delete())
      logger.log(Level.SEVERE, "File {0} hasn't been deleted", new Object[]{reportFile.getAbsolutePath()});
    return reportFile;
  }

  /**
   * Print to file
   * @param file The output file
   * @param content The content
   */
  protected void printToFile(File file, String content) {
    try {
      FileUtils.writeStringToFile(file, content, Charset.defaultCharset(), true);
    } catch (IOException e) {
      if (parameters.doLog())
        logger.log(Level.SEVERE, "printToFile", e);
    }
  }

  private void initializeJarModifiedTime() {
    if (!isJarModifiedTimeDetected) {
      try {
        File currentJar = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
        if (currentJar.isFile()) {
          jarModifiedTime = Files.readAttributes(currentJar.toPath(), BasicFileAttributes.class).lastModifiedTime();
        }
      } catch (URISyntaxException | IOException e) {
        throw new RuntimeException(e);
      }
      isJarModifiedTimeDetected = true;
    }
  }
}
