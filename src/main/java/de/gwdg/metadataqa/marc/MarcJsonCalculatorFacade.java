package de.gwdg.metadataqa.marc;

import com.jayway.jsonpath.InvalidJsonException;
import de.gwdg.metadataqa.api.calculator.CalculatorFacade;
import de.gwdg.metadataqa.api.calculator.CompletenessCalculator;
import de.gwdg.metadataqa.api.calculator.FieldExtractor;
import de.gwdg.metadataqa.api.calculator.LanguageCalculator;
import de.gwdg.metadataqa.api.calculator.MultilingualitySaturationCalculator;
import de.gwdg.metadataqa.api.calculator.TfIdfCalculator;
import de.gwdg.metadataqa.api.configuration.MeasurementConfiguration;
import de.gwdg.metadataqa.api.model.EdmFieldInstance;
import de.gwdg.metadataqa.api.schema.MarcJsonSchema;
import de.gwdg.metadataqa.api.schema.Schema;
import java.util.ArrayList;

/**
 *
 */
public class MarcJsonCalculatorFacade extends CalculatorFacade {

  public enum Formats {

    OAI_PMH_XML("xml"),
    FULLBEAN("fullbean");

    private final String name;

    Formats(String name) {
      this.name = name;
    }
  }

  private MarcJsonSchema marcJsonSchema = new MarcJsonSchema();
  protected FieldExtractor marcFieldExtractor;

  public MarcJsonCalculatorFacade() {}

  public MarcJsonCalculatorFacade(MeasurementConfiguration config) {
    super(config);
  }

  public MarcJsonCalculatorFacade(boolean enableFieldExistenceMeasurement, 
      boolean enableFieldCardinalityMeasurement,
      boolean enableCompletenessMeasurement, boolean enableTfIdfMeasurement, 
      boolean enableProblemCatalogMeasurement) {
    super(new MeasurementConfiguration(enableFieldExistenceMeasurement, enableFieldCardinalityMeasurement, enableCompletenessMeasurement, enableTfIdfMeasurement,
      enableProblemCatalogMeasurement));
  }

  @Override
  public void configure() {

    calculators = new ArrayList<>();
    marcFieldExtractor = new FieldExtractor(marcJsonSchema);
    calculators.add(marcFieldExtractor);

    if (configuration.isCompletenessMeasurementEnabled()
        || configuration.isFieldExistenceMeasurementEnabled()
        || configuration.isFieldCardinalityMeasurementEnabled()) {
      completenessCalculator = new CompletenessCalculator(marcJsonSchema);
      completenessCalculator.setCompleteness(configuration.isCompletenessMeasurementEnabled());
      completenessCalculator.setExistence(configuration.isFieldExistenceMeasurementEnabled());
      completenessCalculator.setCardinality(configuration.isFieldCardinalityMeasurementEnabled());
      completenessCalculator.collectFields(configuration.isCompletenessFieldCollectingEnabled());
      calculators.add(completenessCalculator);
    }

    if (configuration.isTfIdfMeasurementEnabled()) {
      tfidfCalculator = new TfIdfCalculator(marcJsonSchema);
      tfidfCalculator.enableTermCollection(configuration.collectTfIdfTerms());
      calculators.add(tfidfCalculator);
    }

    /*
    if (problemCatalogMeasurementEnabled) {
      ProblemCatalog problemCatalog = new ProblemCatalog(schema);
      LongSubject longSubject = new LongSubject(problemCatalog);
      TitleAndDescriptionAreSame titleAndDescriptionAreSame = new TitleAndDescriptionAreSame(problemCatalog);
      EmptyStrings emptyStrings = new EmptyStrings(problemCatalog);
      calculators.add(problemCatalog);
    }
    */

    if (configuration.isLanguageMeasurementEnabled()) {
      calculators.add(new LanguageCalculator(marcJsonSchema));
    }

    if (configuration.isMultilingualSaturationMeasurementEnabled()) {
      MultilingualitySaturationCalculator multilingualSaturationCalculator = new MultilingualitySaturationCalculator(marcJsonSchema);
      if (configuration.isSaturationExtendedResult())
        multilingualSaturationCalculator.setResultType(MultilingualitySaturationCalculator.ResultTypes.EXTENDED);
      calculators.add(multilingualSaturationCalculator);
    }
  }

  @Override
  public String measure(String jsonRecord) throws InvalidJsonException {
    return (String) this.<EdmFieldInstance>measureWithGenerics(jsonRecord);
  }

  @Override
  public Schema getSchema() {
    return marcJsonSchema;
  }
}
