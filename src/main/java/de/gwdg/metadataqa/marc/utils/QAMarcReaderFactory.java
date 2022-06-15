package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.utils.marcreader.AlephseqMarcReader;
import de.gwdg.metadataqa.marc.utils.marcreader.LineSeparatedMarcReader;
import de.gwdg.metadataqa.marc.utils.marcreader.MarcMakerReader;
import de.gwdg.metadataqa.marc.utils.marcreader.MarclineReader;
import de.gwdg.metadataqa.marc.utils.pica.PicaReader;
import org.apache.commons.lang3.StringUtils;
import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.MarcXmlReader;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public final class QAMarcReaderFactory {

  private CommonParameters parameters;

  private QAMarcReaderFactory(CommonParameters parameters) {
    this.parameters = parameters;
  }

  private MarcReader getIsoFileReader(String fileName) throws Exception {
    return getIsoStreamReader(new FileInputStream(fileName));
  }

  private MarcReader getIsoFileReader(String fileName, String encoding) throws Exception {
    return getIsoStreamReader(new FileInputStream(fileName), encoding);
  }

  private MarcReader getIsoStreamReader(InputStream stream) throws Exception {
    return getIsoStreamReader(stream, null);
  }

  private MarcReader getIsoStreamReader(InputStream stream, String encoding) throws Exception {
    return new MarcStreamReader(stream, encoding);
  }

  private MarcReader getXmlFileReader(String fileName) throws Exception {
    return getXmlStreamReader(new FileInputStream(fileName));
  }

  private MarcReader getXmlStreamReader(InputStream stream) throws Exception {
    return new MarcXmlReader(stream);
  }

  private MarcReader getLineSeparatedFileReader(String fileName) throws Exception {
    return new LineSeparatedMarcReader(fileName);
  }

  private MarcReader getLineSeparatedStreamReader(InputStream stream) throws Exception {
    return new LineSeparatedMarcReader(stream);
  }


  private MarcReader getAlephseqFileReader(String fileName) throws Exception {
    AlephseqMarcReader reader = new AlephseqMarcReader(fileName);
    confiigureAlephseqMarcReader(reader);
    return reader;
  }

  private MarcReader getAlephseqStreamReader(InputStream stream) throws Exception {
    AlephseqMarcReader reader = new AlephseqMarcReader(stream);
    confiigureAlephseqMarcReader(reader);
    return reader;
  }

  private void confiigureAlephseqMarcReader(AlephseqMarcReader reader) {
    if (parameters != null && parameters.getAlephseqLineType() != null)
      reader.setLineType(parameters.getAlephseqLineType());
  }

  private MarcReader getPicaPlainFileReader(String fileName, CommonParameters parameters) {
    // String encoding = (parameters != null && StringUtils.isNotBlank(parameters.getDefaultEncoding())) ? parameters.getDefaultEncoding() : "UTF-8";
    PicaReader reader = new PicaReader(fileName);
    confiigurePicaReader(reader, parameters);
    return reader;
  }

  private MarcReader getPicaPlainStreamReader(InputStream stream, CommonParameters parameters) {
    String encoding = (parameters != null && StringUtils.isNotBlank(parameters.getDefaultEncoding())) ? parameters.getDefaultEncoding() : "UTF-8";
    PicaReader reader = new PicaReader(stream, encoding);
    confiigurePicaReader(reader, parameters);
    return reader;
  }

  private void confiigurePicaReader(PicaReader reader, CommonParameters parameters) {
    if (parameters != null) {
      if (StringUtils.isNotEmpty(parameters.getPicaIdField()))
        reader.setIdField(parameters.getPicaIdField());
      if (StringUtils.isNotEmpty(parameters.getPicaIdCode()))
        reader.setIdField(parameters.getPicaIdField());
      if (StringUtils.isNotEmpty(parameters.getPicaIdField()))
        reader.setIdField(parameters.getPicaIdField());
    }
  }

  private MarcReader getMarclineFileReader(String fileName) throws Exception {
    return new MarclineReader(fileName);
  }

  private MarcReader getMarclineStreamReader(InputStream stream) throws Exception {
    return new MarclineReader(stream);
  }

  private MarcReader getMarcMakerFileReader(String fileName) throws Exception {
    return new MarcMakerReader(fileName);
  }

  private MarcReader getMarcMakerStreamReader(InputStream stream) throws Exception {
    return new MarcMakerReader(stream);
  }

  public static MarcReader getFileReader(MarcFormat marcFormat, String fileName) throws Exception {
    return getFileReader(marcFormat, fileName, null);
  }

  public static MarcReader getFileReader(MarcFormat marcFormat, String fileName, CommonParameters parameters) throws Exception {
    QAMarcReaderFactory factory = new QAMarcReaderFactory(parameters);
    MarcReader reader = null;
    switch (marcFormat) {
      case ALEPHSEQ:
        reader = factory.getAlephseqFileReader(fileName); break;
      case LINE_SEPARATED:
        reader = factory.getLineSeparatedFileReader(fileName); break;
      case XML:
        reader = factory.getXmlFileReader(fileName); break;
      case MARC_LINE:
        reader = factory.getMarclineFileReader(fileName); break;
      case MARC_MAKER:
        reader = factory.getMarcMakerFileReader(fileName); break;
      case PICA_PLAIN:
        reader = factory.getPicaPlainFileReader(fileName, parameters); break;
      case ISO:
      default:
        String encoding = parameters != null ? parameters.getDefaultEncoding() : null;
        reader = factory.getIsoFileReader(fileName, encoding); break;
    }
    return reader;
  }

  public static MarcReader getStreamReader(MarcFormat marcFormat, InputStream stream) throws Exception {
    return getStreamReader(marcFormat, stream, null);
  }

  public static MarcReader getStreamReader(MarcFormat marcFormat, InputStream stream, CommonParameters parameters) throws Exception {
    QAMarcReaderFactory factory = new QAMarcReaderFactory(parameters);
    MarcReader reader = null;
    switch (marcFormat) {
      case ALEPHSEQ:
        reader = factory.getAlephseqStreamReader(stream); break;
      case LINE_SEPARATED:
        reader = factory.getLineSeparatedStreamReader(stream); break;
      case XML:
        reader = factory.getXmlStreamReader(stream); break;
      case MARC_LINE:
        reader = factory.getMarclineStreamReader(stream); break;
      case MARC_MAKER:
        reader = factory.getMarcMakerStreamReader(stream); break;
      case PICA_PLAIN:
        reader = factory.getPicaPlainStreamReader(stream, parameters); break;
      case ISO:
      default:
        String encoding = parameters != null ? parameters.getDefaultEncoding() : null;
        reader = factory.getIsoStreamReader(stream, encoding); break;
    }
    return reader;
  }

  public static MarcReader getStringReader(MarcFormat marcFormat, String content) throws Exception {
    return getStringReader(marcFormat, content, null);
  }

  public static MarcReader getStringReader(MarcFormat marcFormat, String content, CommonParameters parameters) throws Exception {
    InputStream stream = new ByteArrayInputStream(content.getBytes());
    return getStreamReader(marcFormat, stream, parameters);
  }

}
