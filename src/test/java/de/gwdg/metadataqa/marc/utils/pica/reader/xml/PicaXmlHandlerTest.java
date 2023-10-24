package de.gwdg.metadataqa.marc.utils.pica.reader.xml;

import de.gwdg.metadataqa.marc.cli.CliTestUtils;
import org.junit.Test;
import org.marc4j.RecordStack;
import org.marc4j.marc.Record;
import org.xml.sax.InputSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PicaXmlHandlerTest {

  @Test
  public void main() throws FileNotFoundException {
    String fileName = CliTestUtils.getTestResource("picaxml/pica.xml");
    InputStream inputStream = new FileInputStream(fileName);
    InputSource inputSource = new InputSource(inputStream);

    RecordStack queue = new RecordStack();
    final PicaXmlHandler handler = new PicaXmlHandler(queue, "003@", '0');
    final PicaXmlParser parser = new PicaXmlParser(handler);
    parser.parse(inputSource);
    assertTrue(queue.hasNext());
    Record record = queue.pop();
    assertNotNull(record);
    assertFalse(record.hasErrors());
  }
}