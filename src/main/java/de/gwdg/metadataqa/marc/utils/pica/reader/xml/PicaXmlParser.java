package de.gwdg.metadataqa.marc.utils.pica.reader.xml;

import org.marc4j.MarcException;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.TransformerHandler;

/** This class is a variation of MarcXmlParser */
public class PicaXmlParser {
  private ContentHandler handler = null;

  /**
   * Default constructor.
   *
   * @param handler the <code>MarcXmlHandler</code> object
   */
  public PicaXmlParser(final PicaXmlHandler handler) {
    this.handler = handler;
  }

  /**
   * Calls the parser.
   *
   * @param input the input source
   */
  public void parse(final InputSource input) {
    parse(handler, input);
  }

  /**
   * Calls the parser and tries to transform the source into MARCXML using the
   * given stylesheet source before creating <code>Record</code> objects.
   *
   * @param input the input source
   * @param th the transformation content handler
   */
  public void parse(final InputSource input, final TransformerHandler th) {
    final SAXResult result = new SAXResult();
    result.setHandler(handler);
    th.setResult(result);
    parse(th, input);
  }

  private void parse(final ContentHandler handler, final InputSource input) {
    final SAXParserFactory spf = SAXParserFactory.newInstance();
    XMLReader reader = null;
    try {
      spf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      reader = spf.newSAXParser().getXMLReader();
      reader.setFeature("http://xml.org/sax/features/namespaces", true);
      reader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
      reader.setContentHandler(handler);
      reader.parse(input);
    } catch (final Exception e) {
      throw new MarcException("Unable to parse input", e);
    }
  }
}
