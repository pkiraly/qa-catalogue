package de.gwdg.metadataqa.marc.cli.utils;

import de.gwdg.metadataqa.api.configuration.schema.Rule;
import de.gwdg.metadataqa.api.json.DataElement;
import de.gwdg.metadataqa.api.rule.RuleCatalog;
import de.gwdg.metadataqa.api.rule.RuleCheckingOutputType;
import de.gwdg.metadataqa.api.schema.BaseSchema;
import de.gwdg.metadataqa.api.schema.Schema;
import de.gwdg.metadataqa.marc.CsvUtils;
import de.gwdg.metadataqa.marc.RuleCatalogUtils;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.Marc21BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.tags.tags20x.Tag245;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Shacl4bibUseCases {

  private Marc21BibliographicRecord marcRecord;

  @Before
  public void setUp() throws Exception {
    marcRecord = new Marc21BibliographicRecord("u2407796");
    marcRecord.addDataField(new DataField(Tag245.getInstance(),"0", "0",
      "6", "880-01", "a", "iPhone the Bible wan jia sheng jing."));
  }

  private Schema prepareSchema(String independent, String dependent, String pattern, Map<String, Object> config) {
    Schema schema = new BaseSchema()
      .addField(new DataElement(independent, independent)
        .addRule(
          new Rule()
            .withId(independent)
            .withMinCount(1)
            .withHidden((boolean) config.getOrDefault("hidden1", true))
            .withDebug((boolean) config.getOrDefault("debug1", false))
        ))
      .addField(new DataElement(dependent, dependent)
        .addRule(
          new Rule()
            .withId(dependent)
            .withAnd(
              List.of(
                new Rule().withDependencies(List.of(independent))
                  .withDebug((boolean) config.getOrDefault("debug2", false)),
                new Rule().withPattern(pattern)
                  .withDebug((boolean) config.getOrDefault("debug2", false))
              )
            )
            .withDebug((boolean) config.getOrDefault("debug2", false))
            .withSuccessScore(3)
            .withNaScore(-3)
            .withFailureScore(-3)
        ))
      ;
    return schema;
  }

  private String execute(String independent, String dependent, String pattern) {
    return execute(independent, dependent, pattern, new HashMap<>());

  }

  private String execute(String independent,
                         String dependent,
                         String pattern,
                         Map<String, Object> config) {

    RuleCatalog ruleCatalog = RuleCatalogUtils.create(prepareSchema(independent, dependent, pattern, config));
    ruleCatalog.setOutputType(RuleCheckingOutputType.BOTH);
    // System.err.println(ruleCatalog.getHeader());

    MarcSpecSelector selector = new MarcSpecSelector(marcRecord);
    List<Object> values = RuleCatalogUtils.extract(ruleCatalog, ruleCatalog.measure(selector));
    return CsvUtils.createCsvFromObjects(values).trim();
  }

  @Test
  public void existent_valid() {
    assertEquals("1,3,3", execute("245", "245$a", "iPhone"));
  }

  @Test
  public void existent_invalid() {
    assertEquals("0,-3,-3", execute("245", "245$a", "Linux"));
  }

  @Test
  public void existent_nonexistent() {
    assertEquals("NA,-3,-3", execute("245", "245$b", "iPhone"));
  }

  @Test
  public void nonexistent_valid() {
    assertEquals("0,-3,-3", execute("246", "245$a", "iPhone"));
  }

  @Test
  public void nonexistent_invalid() {
    assertEquals("0,0,0,-3,-3", execute("246", "245$a", "Linux",
      Map.of("hidden1", false, "debug2", true)));
  }

  @Test
  public void nonexistent_nonexistent() {
    assertEquals("NA,-3,-3", execute("246", "246$a", "iPhone"));
  }
}
