package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.definition.MarcFormat;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.marc.Record;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QAMarcReaderFactoryTest {

  // ISO

  @Test
  public void getIsoFileReader_simple() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getIsoFileReader(getFile("general/000-line-seperated.mrc"));
    assertTrue(reader.hasNext());
    assertEquals("010000178", reader.next().getControlNumber());
  }

  @Test
  public void getFileReader_iso() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.ISO, getFile("general/000-line-seperated.mrc"));
    assertTrue(reader.hasNext());
    assertEquals("010000178", reader.next().getControlNumber());
  }

  @Test
  public void getIsoFileReader_simple_with_encoding() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getIsoFileReader(getFile("general/000-line-seperated.mrc"), "UTF-8");
    assertTrue(reader.hasNext());
    assertEquals("010000178", reader.next().getControlNumber());
  }

  @Test
  public void getStreamReader_iso() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getStreamReader(MarcFormat.ISO, new FileInputStream(getFile("general/000-line-seperated.mrc")));
    assertTrue(reader.hasNext());
    assertEquals("010000178", reader.next().getControlNumber());
  }

  // MARC XML

  @Test
  public void getXmlFileReader() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getXmlFileReader(getFile("marcxml/marcxml.xml"));
    assertTrue(reader.hasNext());
    assertEquals("987874829", reader.next().getControlNumber());
  }

  @Test
  public void getFileReader_xml() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.XML, getFile("marcxml/marcxml.xml"));
    assertTrue(reader.hasNext());
    assertEquals("987874829", reader.next().getControlNumber());
  }

  @Test
  public void getXmlStreamReader() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getXmlStreamReader(new FileInputStream(getFile("marcxml/marcxml.xml")));
    assertTrue(reader.hasNext());
    assertEquals("987874829", reader.next().getControlNumber());
  }

  @Test
  public void getStreamReader_xml() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getStreamReader(MarcFormat.XML, new FileInputStream(getFile("marcxml/marcxml.xml")));
    assertTrue(reader.hasNext());
    assertEquals("987874829", reader.next().getControlNumber());
  }

  // line separated

  @Test
  public void getLineSeparatedFileReader() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getLineSeparatedFileReader(getFile("general/000-line-seperated.mrc"));
    assertTrue(reader.hasNext());
    assertEquals("010000178", reader.next().getControlNumber());
  }

  @Test
  public void getFileReader_line_separated() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.LINE_SEPARATED, getFile("general/000-line-seperated.mrc"));
    assertTrue(reader.hasNext());
    assertEquals("010000178", reader.next().getControlNumber());
  }

  @Test
  public void getLineSeparatedStreamReader() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getLineSeparatedStreamReader(new FileInputStream(getFile("general/000-line-seperated.mrc")));
    assertTrue(reader.hasNext());
    assertEquals("010000178", reader.next().getControlNumber());
  }

  @Test
  public void getStreamReader_line_separated() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getStreamReader(MarcFormat.LINE_SEPARATED, new FileInputStream(getFile("general/000-line-seperated.mrc")));
    assertTrue(reader.hasNext());
    assertEquals("010000178", reader.next().getControlNumber());
  }

  // Marc as String

  @Test
  public void getMarcStringReader() throws Exception {
    String content = "01107nam a22004211  4500001001000000003000600010005001700016007000300033008004100036024002900077856007300106500005700179040002200236540002300258540002300281500000900304024001900313020001500332024002700347900002500374990001900399542000900418300002100427245002000448100001900468351001200487260002700499300003200526084001200558084001200570084001200582084001400594650001200608650001000620084002000630650001700650650001800667\u001E010000178\u001EDE-89\u001E20180105183145.0\u001Et|\u001E180105s1983    gw            000   ger d\u001E7 \u001FaTIBKAT:010000178\u001F2TIB_ID\u001E4 \u001Fuhttps://www.tib.eu/de/suchen/id/TIBKAT%3A010000178\u001F3link to metadata\u001E  \u001Fa(MetadataCopyright)TIBKAT, Copyright TIB/UB Hannover\u001E  \u001FaDE-89\u001Fbger\u001FcDE-89\u001E  \u001Facommercial licence\u001E  \u001Facommercial licence\u001E  \u001FaBuch\u001E7 \u001Fa010000178\u001F2ppn\u001E  \u001Fa3873844060\u001E7 \u001FaGBV:010000178\u001F2firstid\u001E  \u001FbTIBKAT\u001FdE 94 B 10281\u001E  \u001Fa(epn)24482861X\u001E1 \u001Fi1983\u001E  \u001Fa371 S. (unknown)\u001E  \u001FaDas Töpferbuch\u001E1 \u001FaLeach, Bernard\u001E  \u001Fa6. Aufl\u001E  \u001FbHörnemann\u001Fc1983\u001FaBonn\u001E  \u001Fb22 cm ; graph. Darst., Ill.\u001E  \u001F2dbn\u001Fa46\u001E  \u001F2dbn\u001Fa42\u001E  \u001F2dbn\u001Fa43\u001E  \u001F2bk\u001Fa21.88\u001E04\u001FaKeramik\u001E04\u001FaKunst\u001E  \u001F2linsearch\u001Farest\u001E17\u001FaKeramik\u001F2gnd\u001E17\u001FaTöpfern\u001F2gnd\u001E\u001D";
    MarcReader reader = QAMarcReaderFactory.getMarcStringReader(content);
    assertTrue(reader.hasNext());
    assertEquals("010000178", reader.next().getControlNumber());
  }

  // Alepseq

  @Test
  public void getAlephseqFileReader() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getAlephseqFileReader(getFile("alephseq/alephseq-example1.txt"));
    assertTrue(reader.hasNext());
    assertEquals("000000002", reader.next().getControlNumber());
  }

  @Test
  public void getFileReader_alephseq() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.ALEPHSEQ, getFile("alephseq/alephseq-example1.txt"));
    assertTrue(reader.hasNext());
    assertEquals("000000002", reader.next().getControlNumber());
  }

  @Test
  public void getAlephseqStreamReader() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getAlephseqStreamReader(new FileInputStream(getFile("alephseq/alephseq-example1.txt")));
    assertTrue(reader.hasNext());
    assertEquals("000000002", reader.next().getControlNumber());
  }

  @Test
  public void getStreamReader_alephseq() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getStreamReader(MarcFormat.ALEPHSEQ, new FileInputStream(getFile("alephseq/alephseq-example1.txt")));
    assertTrue(reader.hasNext());
    assertEquals("000000002", reader.next().getControlNumber());
  }

  // MARCLine

  @Test
  public void getMarclineFileReader() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getMarclineFileReader(getFile("marctxt/010000011.mrctxt"));
    assertTrue(reader.hasNext());
    assertEquals("010000011", reader.next().getControlNumber());
  }

  @Test
  public void getFileReader_marcline() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.MARC_LINE, getFile("marctxt/010000011.mrctxt"));
    assertTrue(reader.hasNext());
    assertEquals("010000011", reader.next().getControlNumber());
  }

  @Test
  public void getMarclineStreamReader() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getMarclineStreamReader(new FileInputStream(getFile("marctxt/010000011.mrctxt")));
    assertTrue(reader.hasNext());
    assertEquals("010000011", reader.next().getControlNumber());
  }

  @Test
  public void getStreamReader_marcline() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getStreamReader(MarcFormat.MARC_LINE, new FileInputStream(getFile("marctxt/010000011.mrctxt")));
    assertTrue(reader.hasNext());
    assertEquals("010000011", reader.next().getControlNumber());
  }

  // MARC Maker

  @Test
  public void getMarcMakerFileReader() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getMarcMakerFileReader(getFile("marcmaker/01.marcmaker"));
    assertTrue(reader.hasNext());
    assertEquals("987874829", reader.next().getControlNumber());
  }

  @Test
  public void getFileReader_marcmaker() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.MARC_MAKER, getFile("marcmaker/01.marcmaker"));
    assertTrue(reader.hasNext());
    assertEquals("987874829", reader.next().getControlNumber());
  }

  @Test
  public void getMarcMakerStreamReader() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getMarcMakerStreamReader(new FileInputStream(getFile("marcmaker/01.marcmaker")));
    assertTrue(reader.hasNext());
    assertEquals("987874829", reader.next().getControlNumber());
  }

  @Test
  public void getStreamReader_marcmaker() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getStreamReader(MarcFormat.MARC_MAKER, new FileInputStream(getFile("marcmaker/01.marcmaker")));
    assertTrue(reader.hasNext());
    assertEquals("987874829", reader.next().getControlNumber());
  }

  // PICA

  @Test
  public void getPicaPlainFileReader() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getPicaPlainFileReader(getFile("pica/k10plus-sample.pica"), null);
    assertTrue(reader.hasNext());
    assertEquals("010000011", reader.next().getControlNumber());
  }

  @Test
  public void getFileReader_pica_plain() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getFileReader(MarcFormat.PICA_PLAIN, getFile("pica/k10plus-sample.pica"), null);
    assertTrue(reader.hasNext());
    assertEquals("010000011", reader.next().getControlNumber());
  }

  @Test
  public void getPicaPlainStreamReader() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getPicaPlainStreamReader(new FileInputStream(getFile("pica/k10plus-sample.pica")), null);
    assertTrue(reader.hasNext());
    assertEquals("010000011", reader.next().getControlNumber());
  }

  @Test
  public void getStreamReader_pica_plain() throws Exception {
    MarcReader reader = QAMarcReaderFactory.getStreamReader(MarcFormat.PICA_PLAIN, new FileInputStream(getFile("pica/k10plus-sample.pica")), null);
    assertTrue(reader.hasNext());
    assertEquals("010000011", reader.next().getControlNumber());
  }

  private String getFile(String relativePath) {
    try {
      return FileUtils.getPath(relativePath).toFile().getAbsolutePath();
    } catch (IOException e) {
      // throw new RuntimeException(e);
    } catch (URISyntaxException e) {
      // throw new RuntimeException(e);
    }
    return "";
  }
}
