package de.gwdg.metadataqa.marc.analysis;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.cli.CliTestUtils;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.utils.QAMarcReaderFactory;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaManager;
import de.gwdg.metadataqa.marc.utils.pica.PicaSchemaReader;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShelfReadyAnalysisTest {

  @Test
  public void getScores_forMarc() throws IOException, URISyntaxException {
    List<String> lines = FileUtils.readLinesFromResource("marctxt/010000011.mrctxt");
    BibliographicRecord marcRecord = MarcFactory.createFromFormattedText(lines);

    List<Double> scores = ShelfReadyAnalysis.getScores(marcRecord);
    assertEquals(
      "38.0,40.0,29.0,0.0,0.0,0.0,0.0,9.5,23.200000000000003,10.333333333333332,0.0,0.0,44.0,0.0,17.6,0.0,0.0,30.0,14.333333333333332,35.0,35.0,34.0,40.0,0.0,0.0,14.0,0.0,0.0,0.0,44.0,44.0,0.0,0.0,32.0,15.704901960784314",
      StringUtils.join(scores, ","));
  }

  @Test
  public void getScores_forPica() throws Exception {
    PicaSchemaManager schema = PicaSchemaReader.createSchema(CliTestUtils.getTestResource("pica/schema/k10plus.json"));
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.PICA_PLAIN, CliTestUtils.getTestResource("pica/k10plus-sample.pica"), null);
    reader.hasNext();
    Record record = reader.next();
    BibliographicRecord marcRecord = MarcFactory.createPicaFromMarc4j(record, schema);

    List<Double> scores = ShelfReadyAnalysis.getScores(marcRecord);
    assertEquals(
      "0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,44.0,0.0,0.0,0.0,0.0,2.5,10.75,0.0,0.0,0.0,0.0,0.0,0.0,14.0,0.0,0.0,0.0,44.0,44.0,0.0,0.0,32.0,5.625",
      StringUtils.join(scores, ","));

  }
}
