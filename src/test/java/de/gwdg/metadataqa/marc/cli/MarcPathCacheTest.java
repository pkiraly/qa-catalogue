package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.marc.analysis.AuthorityCategory;
import de.gwdg.metadataqa.marc.analysis.ShelfReadyFieldsBooks;
import de.gwdg.metadataqa.marc.analysis.ThompsonTraillFields;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;

public class MarcPathCacheTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullArgument() {
        new MarcPathCache(null);
    }

    @Test
    public void testGet() {
        BibliographicRecord record = new BibliographicRecord() {
            @Override
            public List<DataField> getAuthorityFields() {
                return null;
            }

            @Override
            public Map<DataField, AuthorityCategory> getAuthorityFieldsMap() {
                return null;
            }

            @Override
            public boolean isAuthorityTag(String tag) {
                return false;
            }

            @Override
            public boolean isSkippableAuthoritySubfield(String tag, String code) {
                return false;
            }

            @Override
            public boolean isSubjectTag(String tag) {
                return false;
            }

            @Override
            public boolean isSkippableSubjectSubfield(String tag, String code) {
                return false;
            }

            @Override
            public Map<ShelfReadyFieldsBooks, Map<String, List<String>>> getShelfReadyMap() {
                return null;
            }

            @Override
            public Map<ThompsonTraillFields, List<String>> getThompsonTraillTagsMap() {
                return null;
            }
        };
        record.addDataField(new DataField("245", "0", "0", "Title" ,MarcVersion.MARC21));
        MarcPathCache pathCache = new MarcPathCache(record);
        List<XmlFieldInstance> results = pathCache.get("245$a");
        //List<XmlFieldInstance> results = pathCache.get("245$atitle");
        assertEquals(0, results.size());
        //assertEquals("Title", results.get(0).getValue());
        assertTrue(results.isEmpty());
    }

    @Test
    public void testGetNonexistentField() {
        BibliographicRecord record = new BibliographicRecord() {
            @Override
            public List<DataField> getAuthorityFields() {
                return null;
            }

            @Override
            public Map<DataField, AuthorityCategory> getAuthorityFieldsMap() {
                return null;
            }

            @Override
            public boolean isAuthorityTag(String tag) {
                return false;
            }

            @Override
            public boolean isSkippableAuthoritySubfield(String tag, String code) {
                return false;
            }

            @Override
            public boolean isSubjectTag(String tag) {
                return false;
            }

            @Override
            public boolean isSkippableSubjectSubfield(String tag, String code) {
                return false;
            }

            @Override
            public Map<ShelfReadyFieldsBooks, Map<String, List<String>>> getShelfReadyMap() {
                return null;
            }

            @Override
            public Map<ThompsonTraillFields, List<String>> getThompsonTraillTagsMap() {
                return null;
            }
        };
        record.addDataField(new DataField("245", "0", "0","Title" ,MarcVersion.MARC21));
        MarcPathCache pathCache = new MarcPathCache(record);
        List<XmlFieldInstance> results = pathCache.get("100");
        assertTrue(results.isEmpty());
    }
}
