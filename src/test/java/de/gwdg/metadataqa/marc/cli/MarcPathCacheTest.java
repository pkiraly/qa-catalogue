package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.dao.record.Marc21Record;
import de.gwdg.metadataqa.marc.definition.tags.tags20x.Tag245;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class MarcPathCacheTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullArgument() {
        new MarcPathCache(null);
    }

    @Test
    public void testGet() {
        BibliographicRecord marcRecord = new Marc21Record("u2407796");

        marcRecord.addDataField(new DataField(Tag245.getInstance(), "0", "0",
                "6", "880-01",
                "a", "iPhone the Bible wan jia sheng jing."));

        MarcPathCache pathCache = new MarcPathCache(marcRecord);
        List<XmlFieldInstance> results = pathCache.get("245$a");
        assertEquals(1, results.size());
        assertEquals("iPhone the Bible wan jia sheng jing.", results.get(0).getValue());
    }

    @Test
    public void testGetNonexistentField() {

        BibliographicRecord marcRecord = new Marc21Record("u2407796");
        marcRecord.addDataField(new DataField(Tag245.getInstance(), "0", "0",
                "6", "880-01",
                "a", "iPhone the Bible wan jia sheng jing."));
        MarcPathCache pathCache = new MarcPathCache(marcRecord);
        List<XmlFieldInstance> results = pathCache.get("100");
        assertTrue(results.isEmpty());
    }
}
