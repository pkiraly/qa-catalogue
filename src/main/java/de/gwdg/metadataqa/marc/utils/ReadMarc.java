package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.utils.marcreader.AlephseqMarcReader;
import de.gwdg.metadataqa.marc.utils.marcreader.LineSeparatedMarcReader;
import de.gwdg.metadataqa.marc.utils.marcreader.MarcMakerReader;
import de.gwdg.metadataqa.marc.utils.marcreader.MarclineReader;
import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.MarcXmlReader;
import org.marc4j.marc.Record;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ReadMarc {

  private static final Logger logger = Logger.getLogger(ReadMarc.class.getCanonicalName());

  public static List<Record> read(String fileName) throws Exception {
    return read(fileName, null);
  }

  public static List<Record> read(String fileName, String encoding) throws Exception {
    InputStream in = new FileInputStream(fileName);
    MarcReader reader = new MarcStreamReader(in, encoding);

    List<Record> records = new ArrayList<>();
    while (reader.hasNext()) {
      Record marc4jRecord = reader.next();
      records.add(marc4jRecord);
    }
    return records;
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

  public static MarcReader getReader(String fileName, boolean isMarcxml) throws Exception {
    return getReader(fileName, isMarcxml, false);
  }

  public static MarcReader getFileReader(MarcFormat marcFormat, String fileName) throws Exception {
    return getFileReader(marcFormat, fileName, null);
  }

  public static MarcReader getFileReader(MarcFormat marcFormat, String fileName, String encoding) throws Exception {
    MarcReader reader = null;
    switch (marcFormat) {
      case ALEPHSEQ:
        reader = ReadMarc.getAlephseqFileReader(fileName); break;
      case LINE_SEPARATED:
        reader = ReadMarc.getLineSeparatedFileReader(fileName); break;
      case XML:
        reader = ReadMarc.getXmlFileReader(fileName); break;
      case MARC_LINE:
        reader = ReadMarc.getMarclineFileReader(fileName); break;
      case MARC_MAKER:
        reader = ReadMarc.getMarcMakerFileReader(fileName); break;
      case ISO:
      default:
        reader = ReadMarc.getIsoFileReader(fileName, encoding); break;
    }
    return reader;
  }

  public static MarcReader getStreamReader(MarcFormat marcFormat, InputStream stream) throws Exception {
    return getStreamReader(marcFormat, stream, null);
  }

  public static MarcReader getStreamReader(MarcFormat marcFormat, InputStream stream, String encoding) throws Exception {
    logger.info(String.format("MarcReader.getMarcStreamReader - format %s", marcFormat));
    MarcReader reader = null;
    switch (marcFormat) {
      case ALEPHSEQ:
        reader = ReadMarc.getAlephseqStreamReader(stream); break;
      case LINE_SEPARATED:
        reader = ReadMarc.getLineSeparatedStreamReader(stream); break;
      case XML:
        reader = ReadMarc.getXmlStreamReader(stream); break;
      case MARC_LINE:
        reader = ReadMarc.getMarclineStreamReader(stream); break;
      case MARC_MAKER:
        reader = ReadMarc.getMarcMakerStreamReader(stream); break;
      case ISO:
      default:
        reader = ReadMarc.getIsoStreamReader(stream, encoding); break;
    }
    return reader;
  }

  public static MarcReader getReader(String fileName, boolean isMarcxml, boolean isLineSeaparated) throws Exception {
    MarcReader reader = null;
    if (isLineSeaparated)
      reader = ReadMarc.getLineSeparatedFileReader(fileName);
    else
      reader = (isMarcxml) ? ReadMarc.getXmlFileReader(fileName) : ReadMarc.getIsoFileReader(fileName);

    return reader;
  }
}
