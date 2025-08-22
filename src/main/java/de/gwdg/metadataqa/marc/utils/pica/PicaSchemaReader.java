package de.gwdg.metadataqa.marc.utils.pica;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PicaSchemaReader {

  private static final Logger logger = Logger.getLogger(PicaSchemaReader.class.getCanonicalName());

  private final JSONParser parser = new JSONParser(JSONParser.MODE_RFC4627);
  private final Map<String, PicaFieldDefinition> map = new HashMap<>();
  private final PicaSchemaManager schema = new PicaSchemaManager();

  private PicaSchemaReader(String fileName) {
    try {
      readFile(fileName);
    } catch (IOException | ParseException e) {
      logger.severe(e.getLocalizedMessage());
    }
  }

  private PicaSchemaReader(InputStream inputStream) {
    try {
      readStream(inputStream);
    } catch (ParseException e) {
      logger.severe(e.getLocalizedMessage());
    }
  }

  public static Map<String, PicaFieldDefinition> create(String filename) {
    PicaSchemaReader reader = new PicaSchemaReader(filename);
    return reader.map;
  }

  public static PicaSchemaManager createSchema(String filename) {
    PicaSchemaReader reader = new PicaSchemaReader(filename);
    return reader.getSchema();
  }

  public static PicaSchemaManager createSchema(InputStream inputStream) {
    PicaSchemaReader reader = new PicaSchemaReader(inputStream);
    return reader.getSchema();
  }

  public static PicaSchemaManager createSchemaManager(String picaSchemaFile) {
    logger.info("read schema");
    PicaSchemaManager picaSchemaManager = null;
    String schemaFile = null;
    if (StringUtils.isNotEmpty(picaSchemaFile)) {
      logger.info("getPicaSchemaFile");
      schemaFile = picaSchemaFile;
    } else if (new File("src/main/resources/pica/avram-k10plus-title.json").exists()) {
      logger.info("default file");
      schemaFile = Paths.get("src/main/resources/pica/avram-k10plus-title.json").toAbsolutePath().toString();
    } else if (new File("avram-schemas/k10plus-title.json").exists()) {
      logger.info("from avram-schemas directory");
      schemaFile = Paths.get("avram-schemas/k10plus-title.json").toAbsolutePath().toString();
    }

    if (schemaFile != null && new File(schemaFile).exists()) {
      logger.log(Level.INFO, "read from file: {0}", schemaFile);
      picaSchemaManager = PicaSchemaReader.createSchema(schemaFile);
    } else {
      logger.info("read from resource");
      ClassLoader classLoader = PicaSchemaReader.class.getClassLoader();
      URL resource = classLoader.getResource("pica/avram-k10plus-title.json");
      if (resource != null) {
        logger.info("Resource's URL is " + resource.toString());
        InputStream schemaStream = classLoader.getResourceAsStream("pica/avram-k10plus-title.json");
        picaSchemaManager = PicaSchemaReader.createSchema(schemaStream);
      } else {
        logger.info("resource is null, Avram schema is not available this way");
        new IllegalStateException("Avram schema is not available");
      }
    }
    return picaSchemaManager;
  }

  private void readFile(String fileName) throws IOException, ParseException {
    JSONObject obj = (JSONObject) parser.parse(new FileReader(fileName));
    process(obj);
  }

  private void readStream(InputStream inputStream) throws ParseException {
    JSONObject obj = (JSONObject) parser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    process(obj);
  }

  private void process(JSONObject jsonObject) {
    JSONObject fields = (JSONObject) jsonObject.get("fields");
    for (Map.Entry<String, Object> entry : fields.entrySet()) {
      String id = entry.getKey();
      JSONObject field = (JSONObject) entry.getValue();
      PicaTagDefinition tag = new PicaTagDefinition(
        (String) field.get("pica3"),         // pica3
        (String) field.get("tag"),           // picaplus
        (boolean) field.get("repeatable"),   // repeatable
        false,                               // sheet
        (String) field.get("label")          // label
      );
      tag.setId(id);
      tag.setDescriptionUrl((String) field.get("url"));
      tag.setModified((String) field.get("modified"));
      tag.setOccurrence((String) field.get("occurrence"));
      tag.setCounter((String) field.get("counter"));
      if (tag.getCounter() != null && tag.getOccurrence() != null) {
        logger.log(Level.INFO, "{0} has both counter and occurrence", id);
      }
      processSubfields(field, tag);
      PicaFieldDefinition definition = new PicaFieldDefinition(tag);
      addTag(definition);
      if (id.endsWith("/00")) {
        PicaFieldDefinition definition2 = definition.copyWithChangesId();
        addTag(definition2);
      }
    }
  }

  private void processSubfields(JSONObject field, PicaTagDefinition tag) {
    Object subfieldsRaw = field.get("subfields");
    List<SubfieldDefinition> subfieldDefinitions = new LinkedList<>();
    if (subfieldsRaw == null) {
      tag.setSubfields(subfieldDefinitions);
      return;
    }

    if (subfieldsRaw instanceof JSONObject) {
      JSONObject subfields = (JSONObject) subfieldsRaw;
      for (Map.Entry<String, Object> entry : subfields.entrySet()) {
        processSubfield(entry.getValue(), subfieldDefinitions);
      }
    } else if (subfieldsRaw instanceof JSONArray) {
      JSONArray subfields = (JSONArray) subfieldsRaw;
      for (Object subfield : subfields) {
        processSubfield(subfield, subfieldDefinitions);
      }
    }
    tag.setSubfields(subfieldDefinitions);
  }

  private void processSubfield(Object o, List<SubfieldDefinition> subfieldDefinitions) {
    SubfieldDefinition definition = extractSubfield(o);
    if (definition != null) {
      subfieldDefinitions.add(definition);
    }
  }

  private SubfieldDefinition extractSubfield(Object o) {
    if (!(o instanceof JSONObject)) {
      logger.log(Level.WARNING, "the JSON node's type is not JSONObject, but {0}", o.getClass().getCanonicalName());
      return null;
    }
    JSONObject subfield = (JSONObject) o;
    String code = (String) subfield.get("code");
    String label = (String) subfield.get("label");
    String cardinalityCode = ((boolean) subfield.get("repeatable")) ? Cardinality.Repeatable.getCode() : Cardinality.Nonrepeatable.getCode();
    SubfieldDefinition definition = new SubfieldDefinition(code, label, cardinalityCode);
    for (String key : subfield.keySet()) {
      // Object value = entry.getValue();
      if (key.equals("code")) {
      } else if (key.equals("label")) {
      } else if (key.equals("repeatable")) {
      } else if (key.equals("modified")) {
        // skip
      } else if (key.equals("order")) {
        // skip
      } else if (key.equals("pica3")) {
        // skip
      } else {
        logger.log(Level.WARNING, "unhandled key in subfield: {0}", key);
      }
    }
    return definition;
  }

  private void addTag(PicaFieldDefinition definition) {
    schema.add(definition);
  }

  public Map<String, PicaFieldDefinition> getMap() {
    return map;
  }

  public PicaSchemaManager getSchema() {
    return schema;
  }
}
