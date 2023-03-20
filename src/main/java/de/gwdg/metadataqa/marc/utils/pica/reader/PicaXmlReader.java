package de.gwdg.metadataqa.marc.utils.pica.reader;

import de.gwdg.metadataqa.marc.utils.pica.reader.xml.PicaXmlParserThread;
// import org.marc4j.MarcException;
import org.marc4j.RecordStack;
import org.marc4j.marc.Record;
import org.xml.sax.InputSource;

// import javax.xml.transform.Source;
// import javax.xml.transform.TransformerConfigurationException;
// import javax.xml.transform.TransformerFactory;
// import javax.xml.transform.sax.SAXTransformerFactory;
// import javax.xml.transform.sax.TransformerHandler;
// import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

/** This class is a variation of MarcXmlReader */
public class PicaXmlReader extends PicaReader {
  private final RecordStack queue;

  public PicaXmlReader(final InputStream input) {
    this.queue = new RecordStack();
    createProducerFromInputSurce(new InputSource(input));
  }

  /**
   * Constructs an instance with the specified input source.
   *
   * @param input the input source
   */
  public PicaXmlReader(final InputSource input) {
    this.queue = new RecordStack();
    createProducerFromInputSurce(input);
  }

  private void createProducerFromInputSurce(final InputSource input) {
    final PicaXmlParserThread producer = new PicaXmlParserThread(queue, input, idTag, idCode.charAt(0));
    producer.start();
  }

  /**
   * Constructs an instance with the specified input stream and stylesheet
   * location.
   *
   * The stylesheet is used to transform the source file and should produce
   * valid MARC XML records. The result is then used to create
   * <code>Record</code> objects.
   *
   * @param input the input stream
   * @param stylesheetUrl the stylesheet location
   */
  /*
  public PicaXmlReader(final InputStream input, final String stylesheetUrl) {
    this(new InputSource(input), new StreamSource(stylesheetUrl));
  }
   */

  /**
   * Constructs an instance with the specified input stream and stylesheet
   * source.
   *
   * The stylesheet is used to transform the source file and should produce
   * valid MARCXML records. The result is then used to create
   * <code>Record</code> objects.
   *
   * @param input the input stream
   * @param stylesheet the stylesheet source
   */
  /*
  public PicaXmlReader(final InputStream input, final Source stylesheet) {
    this(new InputSource(input), stylesheet);
  }
   */

  /*
  public PicaXmlReader(final InputStream input, final TransformerHandler th) {
    this(new InputSource(input), th);
  }
   */

  /*
  public PicaXmlReader(final InputSource input, final Source stylesheet) {
    this.queue = new RecordStack();
    final PicaXmlParserThread producer = new PicaXmlParserThread(queue, input, idTag, idCode.charAt(0));
    final TransformerFactory factory = TransformerFactory.newInstance();
    final SAXTransformerFactory stf = (SAXTransformerFactory) factory;
    TransformerHandler th = null;
    try {
      th = stf.newTransformerHandler(stylesheet);
    } catch (final TransformerConfigurationException e) {
      throw new MarcException("Error creating TransformerHandler", e);
    }
    producer.setTransformerHandler(th);
    producer.start();
  }
   */

  /*
  public PicaXmlReader(final InputSource input, final TransformerHandler th) {
    this.queue = new RecordStack();
    final PicaXmlParserThread producer = new PicaXmlParserThread(queue, input, idTag, idCode.charAt(0));
    producer.setTransformerHandler(th);
    producer.start();
  }
   */

  @Override
  public boolean hasNext() {
    return queue.hasNext();
  }

  @Override
  public Record next() {
    return queue.pop();
  }
}
