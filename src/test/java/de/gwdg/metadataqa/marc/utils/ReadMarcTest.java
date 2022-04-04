package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import org.junit.Test;
import org.marc4j.marc.Record;

import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReadMarcTest {
  private static ClassLoader classLoader = ReadMarcTest.class.getClassLoader();

  @Test
  public void test() throws Exception {
    Path path = FileUtils.getPath("general/0001-01.mrc");
    List<Record> records = ReadMarc.read(path.toString());
    for (Record marc4jRecord : records) {
      MarcRecord marcRecord = MarcFactory.createFromMarc4j(marc4jRecord);
      assertEquals(marc4jRecord.getLeader().marshal(), marcRecord.getLeader().getLeaderString());
      switch (marc4jRecord.getControlNumber()) {
        case "   00000002 " : test02(marcRecord); break;
        case "   00000004 " : test04(marcRecord); break;
        case "   00000006 " : test06(marcRecord); break;
        case "   00000007 " : test07(marcRecord); break;
        case "   00000009 " : test09(marcRecord); break;
      }

      // System.err.println(record.getLeader().getLeaderString());
      // System.err.printf("%s: %s\n", Control001.getMqTag(), record.getControl001().getContent());
      // System.err.println(record.formatForIndex());
    }
  }

  private void test02(MarcRecord marcRecord) {
    assertEquals("00720cam a22002051  4500", marcRecord.getLeader().getLeaderString());
    String expected = "IdentifiedByLccn:    00000002 \n" +
      "SystemControlNumber: (OCoLC)5853149\n" +
      "SystemControlNumber_recordNumber: 5853149\n" +
      "SystemControlNumber_organizationCode: OCoLC\n" +
      "AdminMetadata_catalogingAgency: United States, Library of Congress\n" +
      "AdminMetadata_transcribingAgency: DSI\n" +
      "AdminMetadata_modifyingAgency: United States, Library of Congress\n" +
      "ClassificationLcc_ind1: Item is in LC\n" +
      "ClassificationLcc_ind2: Assigned by LC\n" +
      "ClassificationLcc: RX671\n" +
      "ClassificationLcc_itemPortion: .A92\n" +
      "MainPersonalName_ind1: Surname\n" +
      "MainPersonalName_personalName: Aurand, Samuel Herbert,\n" +
      "MainPersonalName_dates: 1854-\n" +
      "Title_ind1: Added entry\n" +
      "Title_ind2: No nonfiling characters\n" +
      "Title_mainTitle: Botanical materia medica and pharmacology;\n" +
      "Title_subtitle: drugs considered from a botanical, pharmaceutical, physiological, therapeutical and toxicological standpoint.\n" +
      "Title_responsibilityStatement: By S. H. Aurand.\n" +
      "Publication_ind1: Not applicable/No information provided/Earliest available publisher\n" +
      "Publication_place: Chicago,\n" +
      "Publication_agent: P. H. Mallen Company,\n" +
      "Publication_date: 1899.\n" +
      "PhysicalDescription_extent: 406 p.\n" +
      "PhysicalDescription_dimensions: 24 cm.\n" +
      "GeneralNote: Homeopathic formulae.\n" +
      "Topic_ind1: No information provided\n" +
      "Topic_ind2: Library of Congress Subject Headings\n" +
      "Topic_topicalTerm: Botany, Medical.\n" +
      "Topic_ind1: No information provided\n" +
      "Topic_ind2: Library of Congress Subject Headings\n" +
      "Topic_topicalTerm: Homeopathy\n" +
      "Topic_generalSubdivision: Materia medica and therapeutics.\n";
    assertEquals(expected, marcRecord.formatForIndex());
  }

  private void test04(MarcRecord marcRecord) {
    assertEquals("00720cam a2200229 a 4500", marcRecord.getLeader().getLeaderString());
    String expected = "IdentifiedByLccn:    00000004 \n" +
      "SystemControlNumber: (OCoLC)ocm34987929\n" +
      "SystemControlNumber_recordNumber: ocm34987929\n" +
      "SystemControlNumber_organizationCode: OCoLC\n" +
      "AdminMetadata_catalogingAgency: United States, Library of Congress\n" +
      "AdminMetadata_transcribingAgency: VRT\n" +
      "AdminMetadata_modifyingAgency: United States, Library of Congress\n" +
      "AuthenticationCode: LC Copy Cataloging\n" +
      "GeographicCoverage_code: n-us---\n" +
      "ClassificationLcc_ind1: Item is in LC\n" +
      "ClassificationLcc_ind2: Assigned by LC\n" +
      "ClassificationLcc: KF505.Z9\n" +
      "ClassificationLcc_itemPortion: C43\n" +
      "MainPersonalName_ind1: Surname\n" +
      "MainPersonalName_personalName: Chadman, Charles E.\n" +
      "MainPersonalName_fullerForm: (Charles Erehart),\n" +
      "MainPersonalName_dates: 1873-\n" +
      "Title_ind1: Added entry\n" +
      "Title_ind2: No nonfiling characters\n" +
      "Title_mainTitle: Personal rights and the domestic relations /\n" +
      "Title_responsibilityStatement: by Charles E. Chadman.\n" +
      "Publication_ind1: Not applicable/No information provided/Earliest available publisher\n" +
      "Publication_place: Conneaut, OH :\n" +
      "Publication_agent: Home Study Pub. Co.,\n" +
      "Publication_date: 1899.\n" +
      "PhysicalDescription_extent: xi, 186 p. ;\n" +
      "PhysicalDescription_dimensions: 18 cm.\n" +
      "SeriesStatementAddedEntryTitle_ind2: No nonfiling characters\n" +
      "SeriesStatementAddedEntryTitle: Home law school series ;\n" +
      "SeriesStatementAddedEntryTitle_volume: [v. 1] no. 3\n" +
      "Topic_ind1: No information provided\n" +
      "Topic_ind2: Library of Congress Subject Headings\n" +
      "Topic_topicalTerm: Persons (Law)\n" +
      "Topic_geographicSubdivision: United States.\n" +
      "Topic_ind1: No information provided\n" +
      "Topic_ind2: Library of Congress Subject Headings\n" +
      "Topic_topicalTerm: Domestic relations\n" +
      "Topic_geographicSubdivision: United States.\n";
    assertEquals(expected, marcRecord.formatForIndex());
  }

  private void test06(MarcRecord marcRecord) {
    assertEquals("00472cam a22001571  4500", marcRecord.getLeader().getLeaderString());
    String expected = "IdentifiedByLccn:    00000006 \n" +
      "AdminMetadata_catalogingAgency: United States, Library of Congress\n" +
      "AdminMetadata_transcribingAgency: United States, Library of Congress\n" +
      "ClassificationLcc_ind1: Item is in LC\n" +
      "ClassificationLcc_ind2: Assigned by LC\n" +
      "ClassificationLcc: PZ3.G654\n" +
      "ClassificationLcc_itemPortion: S\n" +
      "ClassificationLcc: PR9199.2.G6\n" +
      "MainPersonalName_ind1: Surname\n" +
      "MainPersonalName_personalName: Connor, Ralph,\n" +
      "MainPersonalName_dates: 1860-1937.\n" +
      "Title_ind1: Added entry\n" +
      "Title_ind2: Number of nonfiling characters: 4\n" +
      "Title_mainTitle: The sky pilot;\n" +
      "Title_subtitle: a tale of the foothills,\n" +
      "Title_responsibilityStatement: by Ralph Connor [pseud.]\n" +
      "Publication_ind1: Not applicable/No information provided/Earliest available publisher\n" +
      "Publication_place: Chicago,\n" +
      "Publication_place: New York [etc]\n" +
      "Publication_agent: F. H. Revell company,\n" +
      "Publication_date: 1899.\n" +
      "PhysicalDescription_extent: 300 p.\n" +
      "PhysicalDescription_dimensions: 19 cm.\n";
    assertEquals(expected, marcRecord.formatForIndex());
  }

  private void test07(MarcRecord marcRecord) {
    assertEquals("00548cam a22001811  4500", marcRecord.getLeader().getLeaderString());
    String expected = "IdentifiedByLccn:    00000007 \n" +
      "SystemControlNumber: (OCoLC)3421715\n" +
      "SystemControlNumber_recordNumber: 3421715\n" +
      "SystemControlNumber_organizationCode: OCoLC\n" +
      "AdminMetadata_catalogingAgency: United States, Library of Congress\n" +
      "AdminMetadata_transcribingAgency: TxDW\n" +
      "AdminMetadata_modifyingAgency: NcGU\n" +
      "AdminMetadata_modifyingAgency: United States, Library of Congress\n" +
      "AuthenticationCode: LC PreMARC Retrospective Conversion Project\n" +
      "ClassificationLcc_ind1: Item is in LC\n" +
      "ClassificationLcc_ind2: Assigned by LC\n" +
      "ClassificationLcc: PS1767\n" +
      "ClassificationLcc_itemPortion: .M3 1899\n" +
      "MainPersonalName_ind1: Surname\n" +
      "MainPersonalName_personalName: Guiney, Louise Imogen,\n" +
      "MainPersonalName_dates: 1861-1920.\n" +
      "Title_ind1: Added entry\n" +
      "Title_ind2: Number of nonfiling characters: 4\n" +
      "Title_mainTitle: The martyrs' idyl,\n" +
      "Title_subtitle: and shorter poems,\n" +
      "Title_responsibilityStatement: by Louise Imogen Guiney.\n" +
      "Publication_ind1: Not applicable/No information provided/Earliest available publisher\n" +
      "Publication_place: Boston,\n" +
      "Publication_place: New York,\n" +
      "Publication_agent: Houghton, Mifflin and Company,\n" +
      "Publication_date: 1899.\n" +
      "PhysicalDescription_extent: vi, 81, [1] p.\n" +
      "PhysicalDescription_dimensions: 19 cm.\n";
    assertEquals(expected, marcRecord.formatForIndex());
  }

  private void test09(MarcRecord marcRecord) {
    assertEquals("00483nam a2200169 a 4500", marcRecord.getLeader().getLeaderString());
    String expected = "IdentifiedByLccn:    00000009 \n" +
      "AdminMetadata_catalogingAgency: United States, Library of Congress\n" +
      "AdminMetadata_transcribingAgency: United States, Library of Congress\n" +
      "ClassificationLcc_ind1: Item is in LC\n" +
      "ClassificationLcc_ind2: Assigned by LC\n" +
      "ClassificationLcc: PS2025\n" +
      "ClassificationLcc_itemPortion: .T5 1899\n" +
      "ShelfMarkLcc: PS2025\n" +
      "ShelfMarkLcc_itemNumber: .T5 1899 Copy 2\n" +
      "ShelfMarkLcc_copy: Copy 2.\n" +
      "MainPersonalName_ind1: Surname\n" +
      "MainPersonalName_personalName: Howells, William Dean,\n" +
      "MainPersonalName_dates: 1837-1920.\n" +
      "Title_ind1: Added entry\n" +
      "Title_ind2: No nonfiling characters\n" +
      "Title_mainTitle: Their silver wedding journey /\n" +
      "Title_responsibilityStatement: by W.D. Howells.\n" +
      "Publication_ind1: Not applicable/No information provided/Earliest available publisher\n" +
      "Publication_place: New York :\n" +
      "Publication_agent: Harper,\n" +
      "Publication_date: 1899.\n" +
      "PhysicalDescription_extent: 2 v. :\n" +
      "PhysicalDescription_otherPhysicalDetails: ill. ;\n" +
      "PhysicalDescription_dimensions: 21 cm.\n";
    assertEquals(expected, marcRecord.formatForIndex());
  }

}
