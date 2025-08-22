package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.calculator.output.MetricCollector;
import de.gwdg.metadataqa.api.calculator.output.OutputCollector;
import de.gwdg.metadataqa.api.interfaces.MetricResult;
import de.gwdg.metadataqa.api.rule.RuleCatalog;
import de.gwdg.metadataqa.api.rule.RuleCheckingOutputType;
import de.gwdg.metadataqa.api.schema.Schema;
import de.gwdg.metadataqa.api.util.CompressionLevel;

import java.util.List;

public class RuleCatalogUtils {

  /**
   * Transform result to a list
   * @param ruleCatalog
   * @param results
   * @return
   */
  public static List<Object> extract(RuleCatalog ruleCatalog,
                                     List<MetricResult> results) {
    MetricCollector collector = new MetricCollector();
    collector.addResult(ruleCatalog, results, CompressionLevel.NORMAL);
    return (List<Object>) collector.createOutput(OutputCollector.TYPE.STRING_LIST, CompressionLevel.NORMAL);
  }

  public static RuleCatalog create(Schema schema) {
    return new RuleCatalog(schema)
                 .setOnlyIdInHeader(true)
                 .setOutputType(RuleCheckingOutputType.STATUS);
  }
}
