package de.gwdg.metadataqa.marc.utils.pica.xml;

import org.marc4j.MarcException;
import org.marc4j.RecordStack;
import org.xml.sax.InputSource;

import javax.xml.transform.sax.TransformerHandler;

/** This class is a variation of MarcXmlParserThread */
public class PicaXmlParserThread extends Thread {
  private final RecordStack queue;

  private volatile InputSource input;

  private volatile TransformerHandler th;

  private volatile String idField;
  private volatile char idCode;

  /**
   * Creates a new instance and registers the <code>RecordQueue</code>.
   *
   * @param queue the record queue
   */
  public PicaXmlParserThread(final RecordStack queue) {
    this.queue = queue;
  }

  /**
   * Creates a new instance and registers the <code>RecordQueue</code> and the
   * <code>InputStream</code>.
   *
   * @param queue the record queue
   * @param input the input stream
   */
  public PicaXmlParserThread(final RecordStack queue, final InputSource input, String idField, char idCode) {
    this.queue = queue;
    this.input = input;
    this.idField = idField;
    this.idCode = idCode;
  }

  /**
   * Returns the content handler to transform the source to MARCXML.
   *
   * @return TransformerHandler - the transformation content handler
   */
  public TransformerHandler getTransformerHandler() {
    return th;
  }

  /**
   * Sets the content handler to transform the source to MARCXML.
   *
   * @param th - the transformation content handler
   */
  public void setTransformerHandler(final TransformerHandler th) {
    this.th = th;
  }

  /**
   * Returns the input stream.
   *
   * @return InputSource - the input source
   */
  public InputSource getInputSource() {
    return input;
  }

  /**
   * Sets the input stream.
   *
   * @param input the input stream
   */
  public void setInputSource(final InputSource input) {
    this.input = input;
  }

  /**
   * Creates a new <code>MarcXmlHandler</code> instance, registers the
   * <code>RecordQueue</code> and sends the <code>InputStream</code> to the
   * <code>MarcXmlParser</code> parser.
   */
  @Override
  public void run() {
    try {
      final PicaXmlHandler handler = new PicaXmlHandler(queue, idField, idCode);
      final PicaXmlParser parser = new PicaXmlParser(handler);

      if (th == null) {
        parser.parse(input);
      } else {
        parser.parse(input, th);
      }
    } catch (final MarcException me) {
      queue.passException(me);
    } finally {
      queue.end();
    }
  }
}
