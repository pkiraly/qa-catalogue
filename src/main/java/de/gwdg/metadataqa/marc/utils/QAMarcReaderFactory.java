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

  private QAMarcReaderFactory() {}

  private MarcReader getMarcStreamReader(CommonParameters parameters) throws Exception {
    return QAMarcReaderFactory.getStreamReader(parameters.getMarcFormat(), parameters.getStream(), parameters);
  }

  public static MarcReader getIsoFileReader(String fileName) throws Exception {
    return getIsoStreamReader(new FileInputStream(fileName));
  }

  public static MarcReader getIsoFileReader(String fileName, String encoding) throws Exception {
    return getIsoStreamReader(new FileInputStream(fileName), encoding);
  }

  public static MarcReader getIsoStreamReader(InputStream stream) throws Exception {
    return getIsoStreamReader(stream, null);
  }

  public static MarcReader getIsoStreamReader(InputStream stream, String encoding) throws Exception {
    return new MarcStreamReader(stream, encoding);
  }

  public static MarcReader getXmlFileReader(String fileName) throws Exception {
    return getXmlStreamReader(new FileInputStream(fileName));
  }

  public static MarcReader getXmlStreamReader(InputStream stream) throws Exception {
    return new MarcXmlReader(stream);
  }

  public static MarcReader getLineSeparatedFileReader(String fileName) throws Exception {
    return new LineSeparatedMarcReader(fileName);
  }

  public static MarcReader getLineSeparatedStreamReader(InputStream stream) throws Exception {
    return new LineSeparatedMarcReader(stream);
  }

  public static MarcReader getMarcStringReader(String content) throws Exception {
    InputStream is = new ByteArrayInputStream(content.getBytes());
    MarcReader reader = new MarcStreamReader(is);
    return reader;
  }

  public static MarcReader getAlephseqFileReader(String fileName) throws Exception {
    return new AlephseqMarcReader(fileName);
  }

  public static MarcReader getAlephseqStreamReader(InputStream stream) throws Exception {
    return new AlephseqMarcReader(stream);
  }

  public static MarcReader getPicaPlainFileReader(String fileName, CommonParameters parameters) {
    // String encoding = (parameters != null && StringUtils.isNotBlank(parameters.getDefaultEncoding())) ? parameters.getDefaultEncoding() : "UTF-8";
    PicaReader reader = new PicaReader(fileName);
    confiigurePicaReader(reader, parameters);
    return reader;
  }

  public static MarcReader getPicaPlainStreamReader(InputStream stream, CommonParameters parameters) {
    String encoding = (parameters != null && StringUtils.isNotBlank(parameters.getDefaultEncoding())) ? parameters.getDefaultEncoding() : "UTF-8";
    PicaReader reader = new PicaReader(stream, encoding);
    confiigurePicaReader(reader, parameters);
    return reader;
  }

  private static void confiigurePicaReader(PicaReader reader, CommonParameters parameters) {
    if (parameters != null) {
      if (StringUtils.isNotEmpty(parameters.getPicaIdField()))
        reader.setIdField(parameters.getPicaIdField());
      if (StringUtils.isNotEmpty(parameters.getPicaIdCode()))
        reader.setIdField(parameters.getPicaIdField());
      if (StringUtils.isNotEmpty(parameters.getPicaIdField()))
        reader.setIdField(parameters.getPicaIdField());
    }
  }

  public static MarcReader getMarclineFileReader(String fileName) throws Exception {
    return new MarclineReader(fileName);
  }

  public static MarcReader getMarclineStreamReader(InputStream stream) throws Exception {
    return new MarclineReader(stream);
  }

  public static MarcReader getMarcMakerFileReader(String fileName) throws Exception {
    return new MarcMakerReader(fileName);
  }

  public static MarcReader getMarcMakerStreamReader(InputStream stream) throws Exception {
    return new MarcMakerReader(stream);
  }

  public static MarcReader getFileReader(MarcFormat marcFormat, String fileName) throws Exception {
    return getFileReader(marcFormat, fileName, null);
  }

  public static MarcReader getFileReader(MarcFormat marcFormat, String fileName, CommonParameters parameters) throws Exception {
    MarcReader reader = null;
    switch (marcFormat) {
      case ALEPHSEQ:
        reader = getAlephseqFileReader(fileName);
        if (parameters != null && parameters.getAlephseqLineType() != null)
          ((AlephseqMarcReader) reader).setLineType(parameters.getAlephseqLineType());
        break;
      case LINE_SEPARATED:
        reader = getLineSeparatedFileReader(fileName); break;
      case XML:
        reader = getXmlFileReader(fileName); break;
      case MARC_LINE:
        reader = getMarclineFileReader(fileName); break;
      case MARC_MAKER:
        reader = getMarcMakerFileReader(fileName); break;
      case PICA_PLAIN:
        reader = getPicaPlainFileReader(fileName, parameters); break;
      case ISO:
      default:
        String encoding = parameters != null ? parameters.getDefaultEncoding() : null;
        reader = getIsoFileReader(fileName, encoding); break;
    }
    return reader;
  }

  public static MarcReader getStreamReader(MarcFormat marcFormat, InputStream stream) throws Exception {
    return getStreamReader(marcFormat, stream, null);
  }

  public static MarcReader getStreamReader(MarcFormat marcFormat, InputStream stream, CommonParameters parameters) throws Exception {
    MarcReader reader = null;
    switch (marcFormat) {
      case ALEPHSEQ:
        reader = getAlephseqStreamReader(stream);
        if (parameters != null && parameters.getAlephseqLineType() != null)
          ((AlephseqMarcReader) reader).setLineType(parameters.getAlephseqLineType());
        break;
      case LINE_SEPARATED:
        reader = getLineSeparatedStreamReader(stream); break;
      case XML:
        reader = getXmlStreamReader(stream); break;
      case MARC_LINE:
        reader = getMarclineStreamReader(stream); break;
      case MARC_MAKER:
        reader = getMarcMakerStreamReader(stream); break;
      case PICA_PLAIN:
        reader = getPicaPlainStreamReader(stream, parameters); break;
      case ISO:
      default:
        String encoding = parameters != null ? parameters.getDefaultEncoding() : null;
        reader = getIsoStreamReader(stream, encoding); break;
    }
    return reader;
  }

}
