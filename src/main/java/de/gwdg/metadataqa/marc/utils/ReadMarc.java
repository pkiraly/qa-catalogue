package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.definition.MarcFormat;
import de.gwdg.metadataqa.marc.utils.marcreader.AlephseqMarcReader;
import de.gwdg.metadataqa.marc.utils.marcreader.LineSeparatedMarcReader;
import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.MarcXmlReader;
import org.marc4j.marc.Record;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadMarc {

  public static List<Record> read(String fileName) throws Exception {
    InputStream in = new FileInputStream(fileName);
    MarcReader reader = new MarcStreamReader(in);

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

  public static MarcReader getIsoStreamReader(InputStream stream) throws Exception {
    return new MarcStreamReader(stream);
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

  public static MarcReader getReader(String fileName, boolean isMarcxml) throws Exception {
    return getReader(fileName, isMarcxml, false);
  }

  public static MarcReader getFileReader(MarcFormat marcFormat, String fileName) throws Exception {
    MarcReader reader = null;
    switch (marcFormat) {
      case ALEPHSEQ:
        reader = ReadMarc.getAlephseqFileReader(fileName); break;
      case LINE_SEPARATED:
        reader = ReadMarc.getLineSeparatedFileReader(fileName); break;
      case XML:
        reader = ReadMarc.getXmlFileReader(fileName); break;
      case ISO:
      default:
        reader = ReadMarc.getIsoFileReader(fileName); break;
    }
    return reader;
  }

  public static MarcReader getStreamReader(MarcFormat marcFormat, InputStream stream) throws Exception {
    MarcReader reader = null;
    switch (marcFormat) {
      case ALEPHSEQ:
        reader = ReadMarc.getAlephseqStreamReader(stream); break;
      case LINE_SEPARATED:
        reader = ReadMarc.getLineSeparatedStreamReader(stream); break;
      case XML:
        reader = ReadMarc.getXmlStreamReader(stream); break;
      case ISO:
      default:
        reader = ReadMarc.getIsoStreamReader(stream); break;
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
