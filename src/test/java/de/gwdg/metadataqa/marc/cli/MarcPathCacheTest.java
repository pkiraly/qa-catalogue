package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.MarcFactory;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;
public class MarcPathCacheTest {

    @Test
    public void get(){
        List<String> lines = null;
        try {
            lines = FileUtils.readLinesFromResource("bl/006013122.mrctxt");
        } catch (URISyntaxException | IOException e) {
            e.getMessage();
        }
        BibliographicRecord marcRecord = MarcFactory.createFromFormattedText(lines, MarcVersion.BL);
        MarcPathCache pathCache = new MarcPathCache(marcRecord);


    }

}
