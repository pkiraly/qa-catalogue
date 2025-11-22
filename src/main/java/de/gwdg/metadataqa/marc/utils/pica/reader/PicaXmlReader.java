package de.gwdg.metadataqa.marc.utils.pica.reader;

import de.gwdg.metadataqa.marc.utils.pica.reader.xml.PicaXmlParserThread;
import org.marc4j.RecordStack;
import org.marc4j.marc.Record;
import org.xml.sax.InputSource;

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

  @Override
  public boolean hasNext() {
    return queue.hasNext();
  }

  @Override
  public Record next() {
    return queue.pop();
  }
}
