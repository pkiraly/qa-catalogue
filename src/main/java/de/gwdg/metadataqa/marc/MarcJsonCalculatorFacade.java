package de.gwdg.metadataqa.marc;

import com.jayway.jsonpath.InvalidJsonException;
import de.gwdg.metadataqa.api.calculator.CalculatorFacade;
import de.gwdg.metadataqa.api.calculator.CompletenessCalculator;
import de.gwdg.metadataqa.api.calculator.FieldExtractor;
import de.gwdg.metadataqa.api.calculator.LanguageCalculator;
import de.gwdg.metadataqa.api.calculator.MultilingualitySaturationCalculator;
import de.gwdg.metadataqa.api.calculator.TfIdfCalculator;
import de.gwdg.metadataqa.api.model.EdmFieldInstance;
import de.gwdg.metadataqa.api.problemcatalog.EmptyStrings;
import de.gwdg.metadataqa.api.problemcatalog.LongSubject;
import de.gwdg.metadataqa.api.problemcatalog.ProblemCatalog;
import de.gwdg.metadataqa.api.problemcatalog.TitleAndDescriptionAreSame;
import de.gwdg.metadataqa.api.schema.MarcJsonSchema;
import de.gwdg.metadataqa.api.schema.Schema;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class MarcJsonCalculatorFacade extends CalculatorFacade {

	private static final Logger logger = Logger.getLogger(MarcJsonCalculatorFacade.class.getCanonicalName());

	public enum Formats {

		OAI_PMH_XML("xml"),
		FULLBEAN("fullbean");

		private final String name;

		private Formats(String name) {
			this.name = name;
		}
	};

	private MarcJsonSchema schema = new MarcJsonSchema();
	protected FieldExtractor fieldExtractor;
	protected Formats format = Formats.OAI_PMH_XML;

	public MarcJsonCalculatorFacade() {}

	public MarcJsonCalculatorFacade(boolean enableFieldExistenceMeasurement, 
			boolean enableFieldCardinalityMeasurement,
			boolean enableCompletenessMeasurement, boolean enableTfIdfMeasurement, 
			boolean enableProblemCatalogMeasurement) {
		super(enableFieldExistenceMeasurement, enableFieldCardinalityMeasurement, enableCompletenessMeasurement, enableTfIdfMeasurement,
			enableProblemCatalogMeasurement);
	}

	public MarcJsonCalculatorFacade(boolean enableFieldExistenceMeasurement, 
			boolean enableFieldCardinalityMeasurement,
			boolean enableCompletenessMeasurement, boolean enableTfIdfMeasurement, 
			boolean enableProblemCatalogMeasurement, 
			boolean abbreviate) {
		super(enableFieldExistenceMeasurement, enableFieldCardinalityMeasurement, enableCompletenessMeasurement, enableTfIdfMeasurement, enableProblemCatalogMeasurement);
		changed();
	}

	@Override
	public void configure() {

		calculators = new ArrayList<>();
		fieldExtractor = new FieldExtractor(schema);
		calculators.add(fieldExtractor);

		if (completenessMeasurementEnabled 
				|| fieldExistenceMeasurementEnabled 
				|| fieldCardinalityMeasurementEnabled) {
			completenessCalculator = new CompletenessCalculator(schema);
			completenessCalculator.setCompleteness(completenessMeasurementEnabled);
			completenessCalculator.setExistence(fieldExistenceMeasurementEnabled);
			completenessCalculator.setCardinality(fieldCardinalityMeasurementEnabled);
			completenessCalculator.collectFields(completenessCollectFields);
			calculators.add(completenessCalculator);
		}

		if (tfIdfMeasurementEnabled) {
			tfidfCalculator = new TfIdfCalculator(schema);
			tfidfCalculator.setDoCollectTerms(collectTfIdfTerms);
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


		if (languageMeasurementEnabled) {
			languageCalculator = new LanguageCalculator(schema);
			calculators.add(languageCalculator);
		}

		if (multilingualSaturationMeasurementEnabled) {
			multilingualSaturationCalculator = new MultilingualitySaturationCalculator(schema);
			if (saturationExtendedResult)
				multilingualSaturationCalculator.setResultType(MultilingualitySaturationCalculator.ResultTypes.EXTENDED);
			calculators.add(multilingualSaturationCalculator);
		}
	}

	@Override
	public String measure(String jsonRecord) throws InvalidJsonException {
		return this.<EdmFieldInstance>measureWithGenerics(jsonRecord);
	}

	public Formats getFormat() {
		return format;
	}

	public void setFormat(Formats format) {
		this.format = format;
	}

	public Schema getSchema() {
		return schema;
	}
}
