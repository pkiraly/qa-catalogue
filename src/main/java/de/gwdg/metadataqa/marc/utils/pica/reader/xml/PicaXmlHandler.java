package de.gwdg.metadataqa.marc.utils.pica.reader.xml;

import de.gwdg.metadataqa.marc.utils.pica.PicaDataField;
import org.marc4j.MarcError;
import org.marc4j.MarcException;
import org.marc4j.RecordStack;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Leader;
import org.marc4j.marc.MarcFactory;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.impl.ControlFieldImpl;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/** This class is a variation of MarcXmlHandler */
public class PicaXmlHandler implements ContentHandler {

  private static final Logger logger = Logger.getLogger(PicaXmlHandler.class.getCanonicalName());

  private final String idField;
  private final char idCode;
  private Record record;
  private RecordStack queue;
  private StringBuffer sb;
  private Subfield subfield;

  private ControlField controlField;

  private PicaDataField dataField;

  private String tag;

  private String prev_tag = "n/a";

  private static final int COLLECTION_ID = 1;

  private static final int LEADER_ID = 2;

  private static final int RECORD_ID = 3;

  private static final int CONTROLFIELD_ID = 4;

  private static final int DATAFIELD_ID = 5;

  private static final int SUBFIELD_ID = 6;

  /** The tag attribute name string */
  private static final String TAG_ATTR = "tag";

  /** The code attribute name string */
  private static final String CODE_ATTR = "code";

  /** The first indicator attribute name string */
  private static final String IND_1_ATTR = "ind1";

  /** The second indicator attribute name string */
  private static final String IND_2_ATTR = "ind2";

  /** The type attribute name string */
  private static final String TYPE_ATTR = "type";

  private static final Map<String, Integer> ELEMENTS;
  private static final Set<String> RECORD_TYPES;

  private MarcFactory factory = null;

  static {
    ELEMENTS = new HashMap<>();
    ELEMENTS.put("collection", Integer.valueOf(COLLECTION_ID));
    ELEMENTS.put("leader", Integer.valueOf(LEADER_ID));
    ELEMENTS.put("record", Integer.valueOf(RECORD_ID));
    ELEMENTS.put("controlfield", Integer.valueOf(CONTROLFIELD_ID));
    ELEMENTS.put("datafield", Integer.valueOf(DATAFIELD_ID));
    ELEMENTS.put("subfield", Integer.valueOf(SUBFIELD_ID));

    RECORD_TYPES = new HashSet<>();
    RECORD_TYPES.add("Bibliographic");
    RECORD_TYPES.add("Authority");
    RECORD_TYPES.add("Holdings");
    RECORD_TYPES.add("Classification");
    RECORD_TYPES.add("Community");
  }

  public PicaXmlHandler(final RecordStack queue, final String idField, final char idCode) {
    this.queue = queue;
    this.idField = idField;
    this.idCode = idCode;
    factory = MarcFactory.newInstance();
  }

  @Override
  public void setDocumentLocator(Locator locator) {
    // not implemented
  }

  @Override
  public void startDocument() throws SAXException {
  }

  @Override
  public void endDocument() throws SAXException {
    queue.end();
  }

  @Override
  public void startPrefixMapping(String s, String s1) throws SAXException {
    // not implemented
  }

  @Override
  public void endPrefixMapping(String s) throws SAXException {
    // not implemented
  }

  @Override
  public void startElement(final String uri, final String name, final String qName, final Attributes atts) throws SAXException {
    final String realname = name.length() == 0 ? qName : name;
    final Integer elementType = ELEMENTS.get(stripNsPrefix(realname));

    if (elementType == null) {
      if (record != null) {
        record.addError("n/a", "n/a", MarcError.MINOR_ERROR, "Unexpected XML element: " + realname);
        return;
      } else {
        throw new MarcException("Unexpected XML element: " + realname);
      }
    }

    switch (elementType.intValue()) {
      case COLLECTION_ID:
        break;
      case RECORD_ID:
        final String typeAttr = atts.getValue(TYPE_ATTR);

        record = factory.newRecord();

        if (typeAttr != null && RECORD_TYPES.contains(typeAttr)) {
          record.setType(typeAttr);
        }
        prev_tag = "n/a";

        break;
      case LEADER_ID:
        sb = new StringBuffer();
        break;
      case CONTROLFIELD_ID:
        tag = atts.getValue(TAG_ATTR);

        if (tag == null) {
          if (record != null) {
            record.addError("n/a", "n/a", MarcError.MINOR_ERROR, "Missing tag element in ControlField after tag: "+ prev_tag);
          } else {
            throw new MarcException("ControlField missing tag value, found outside a record element");
          }
          break;
        }

        controlField = factory.newControlField(tag);
        sb = new StringBuffer();
        break;
      case DATAFIELD_ID:
        tag = atts.getValue(TAG_ATTR);

        if (tag == null) {
          if (record != null) {
            record.addError("n/a", "n/a", MarcError.MINOR_ERROR, "Missing tag element in datafield after tag: "+prev_tag);
          } else {
            throw new MarcException("DataField missing tag value, found outside a record element");
          }
          break;
        }
        dataField = new PicaDataField(tag);
        String occurrence = atts.getValue("occurrence");
        if (occurrence != null) {
          dataField.setOccurrence(occurrence);
        }

        for (int i = 0; i < atts.getLength(); i++) {
          if (!atts.getLocalName(i).equals(TAG_ATTR) && !atts.getLocalName(i).equals("occurrence"))
            logger.warning("unhandled attr: " + atts.getLocalName(i));
        }
        break;

      case SUBFIELD_ID:
        String code = atts.getValue(CODE_ATTR);

        if (code == null) {
          if (record != null) {
            record.addError(tag, "n/a", MarcError.MINOR_ERROR, "Subfield (" + tag + ") missing code attribute");
          } else {
            throw new MarcException("Subfield in DataField (" + tag + ") missing code attribute");
          }
          break;
        }

        if (code.length() == 0) {
          code = " ";
        }

        subfield = factory.newSubfield(code.charAt(0));
        sb = new StringBuffer();
        break;
    }
    prev_tag = tag;
  }

  @Override
  public void endElement(final String uri, final String name, final String qName) throws SAXException {
    final String realname = name.length() == 0 ? qName : name;
    final String element = stripNsPrefix(realname);
    final Integer elementType = ELEMENTS.get(element);

    if (elementType == null) {
      if (record != null) {
        //record.addError("n/a", "n/a", MarcError.MINOR_ERROR, "Unexpected XML element: " + realname);
        return;
      } else {
        throw new MarcException("Unexpected XML element: " + realname);
      }
    }

    switch (elementType.intValue()) {
      case COLLECTION_ID:
        break;
      case RECORD_ID:
        setId();
        queue.push(record);
        break;
      case LEADER_ID:
        final Leader leader = factory.newLeader(sb.toString());
        record.setLeader(leader);
        break;
      case CONTROLFIELD_ID:
        if (controlField != null) {
          controlField.setData(sb.toString());
          record.addVariableField(controlField);
          controlField = null;
        }
        break;
      case DATAFIELD_ID:
        if (dataField != null) {
          record.addVariableField(dataField);
          dataField = null;
        }
        break;
      case SUBFIELD_ID:
        if (dataField != null && subfield != null) {
          subfield.setData(sb.toString());
          dataField.addSubfield(subfield);
          subfield = null;
        }
        break;
    }
  }

  private void setId() {
    for (DataField field : record.getDataFields()) {
      if (field.getTag().equals(idField)) {
        Subfield subfield = field.getSubfield(idCode);
        if (subfield != null)
          record.addVariableField(new ControlFieldImpl("001", subfield.getData()));
      }
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    if (sb != null) {
      sb.append(ch, start, length);
    }
  }

  @Override
  public void ignorableWhitespace(char[] chars, int i, int i1) throws SAXException {
    // not implemented
  }

  @Override
  public void processingInstruction(String s, String s1) throws SAXException {
    // not implemented
  }

  @Override
  public void skippedEntity(String s) throws SAXException {
    // not implemented
  }

  private String stripNsPrefix(final String aName) {
    final int index = aName.indexOf(":");

    if (index == -1 || index + 1 == aName.length()) {
      return aName;
    } else {
      return aName.substring(index + 1);
    }
  }
}
