package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.utils.marcreader.AlephseqMarcReader;
import de.gwdg.metadataqa.marc.utils.marcreader.LineSeparatedMarcReader;
import de.gwdg.metadataqa.marc.utils.marcreader.MarcMakerReader;
import de.gwdg.metadataqa.marc.utils.marcreader.MarclineReader;
import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.MarcXmlReader;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public final class QAMarcReaderFactory {

  private QAMarcReaderFactory() {}

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

  public static MarcReader getFileReader(MarcFormat marcFormat, String fileName, String encoding) throws Exception {
    MarcReader reader = null;
    switch (marcFormat) {
      case ALEPHSEQ:
        reader = QAMarcReaderFactory.getAlephseqFileReader(fileName); break;
      case LINE_SEPARATED:
        reader = QAMarcReaderFactory.getLineSeparatedFileReader(fileName); break;
      case XML:
        reader = QAMarcReaderFactory.getXmlFileReader(fileName); break;
      case MARC_LINE:
        reader = QAMarcReaderFactory.getMarclineFileReader(fileName); break;
      case MARC_MAKER:
        reader = QAMarcReaderFactory.getMarcMakerFileReader(fileName); break;
      case ISO:
      default:
        reader = QAMarcReaderFactory.getIsoFileReader(fileName, encoding); break;
    }
    return reader;
  }

  public static MarcReader getStreamReader(MarcFormat marcFormat, InputStream stream) throws Exception {
    return getStreamReader(marcFormat, stream, null);
  }

  public static MarcReader getStreamReader(MarcFormat marcFormat, InputStream stream, String encoding) throws Exception {
    MarcReader reader = null;
    switch (marcFormat) {
      case ALEPHSEQ:
        reader = QAMarcReaderFactory.getAlephseqStreamReader(stream); break;
      case LINE_SEPARATED:
        reader = QAMarcReaderFactory.getLineSeparatedStreamReader(stream); break;
      case XML:
        reader = QAMarcReaderFactory.getXmlStreamReader(stream); break;
      case MARC_LINE:
        reader = QAMarcReaderFactory.getMarclineStreamReader(stream); break;
      case MARC_MAKER:
        reader = QAMarcReaderFactory.getMarcMakerStreamReader(stream); break;
      case ISO:
      default:
        reader = QAMarcReaderFactory.getIsoStreamReader(stream, encoding); break;
    }
    return reader;
  }

  public static MarcReader getReader(String fileName, boolean isMarcxml) throws Exception {
    return getReader(fileName, isMarcxml, false);
  }

  public static MarcReader getReader(String fileName, boolean isMarcxml, boolean isLineSeaparated) throws Exception {
    MarcReader reader = null;
    if (isLineSeaparated)
      reader = QAMarcReaderFactory.getLineSeparatedFileReader(fileName);
    else
      reader = (isMarcxml) ? QAMarcReaderFactory.getXmlFileReader(fileName) : QAMarcReaderFactory.getIsoFileReader(fileName);

    return reader;
  }
}
