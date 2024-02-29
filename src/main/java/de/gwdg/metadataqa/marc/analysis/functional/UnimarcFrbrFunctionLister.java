package de.gwdg.metadataqa.marc.analysis.functional;

import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.logging.Logger;

public class UnimarcFrbrFunctionLister extends FrbrFunctionLister {

  private static final Logger logger = Logger.getLogger(UnimarcFrbrFunctionLister.class.getCanonicalName());


  public UnimarcFrbrFunctionLister() {
    super();
  }

  public void prepareBaseline() {
    // There's a file which for each FRBR function (task) lists the fields and subfields that support it. Since there are
    // no flat fields in UNIMARC, it's only in the format of "field$subfield" (e.g. "100$a"). In addition, the source
    // paper doesn't list control fields, leader or indicators, so they are not included here.
    // Also, the paper defines only four user tasks which are part of the Discovery group, namely: Search (Find),
    // Identify, Select and Obtain. The other two groups are not defined in the paper.
    // A similar definition of tasks is evident in the following paper:
    // https://repository.ifla.org/bitstream/123456789/811/2/ifla-functional-requirements-for-bibliographic-records-frbr.pdf

    JSONParser parser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
    pathByFunction = new EnumMap<>(FRBRFunction.class);
    functionByPath = new HashMap<>();

    String frbrPath = "/unimarc/frbr-user-tasks.json";
    URL frbrUrl = UnimarcFrbrFunctionLister.class.getResource(frbrPath);
    JSONObject frbrTasks;
    try {
      FileReader reader = new FileReader(frbrUrl.getFile());
      frbrTasks = (JSONObject) parser.parse(reader);
    } catch (FileNotFoundException e) {
      logger.severe("The file " + frbrPath + " was not found. The baseline will not be prepared.");
      return;
    } catch (ParseException e) {
      logger.severe("An error occurred while parsing the file " + frbrPath + ". The baseline will not be prepared.");
      return;
    } catch (NullPointerException e) {
      logger.severe("The file " + frbrPath + " is null. The baseline will not be prepared.");
      return;
    }

    for (FRBRFunction functionUserTask : FRBRFunction.values()) {
      if (!frbrTasks.containsKey(functionUserTask.name()) || functionUserTask.getParent() == null) {
        continue;
      }
      JSONArray userTaskPaths = (JSONArray) frbrTasks.get(functionUserTask.name());
      for (Object pathObject : userTaskPaths) {
        String path = (String) pathObject;
        pathByFunction.putIfAbsent(functionUserTask, new ArrayList<>());
        pathByFunction.get(functionUserTask).add(path);
        functionByPath.putIfAbsent(path, new ArrayList<>());
        functionByPath.get(path).add(functionUserTask);
      }
    }
  }
}
