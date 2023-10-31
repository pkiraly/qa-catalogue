package de.gwdg.metadataqa.marc.utils.unimarc;


import java.util.HashMap;
import java.util.Map;

public class UnimarcSchemaManager {
    Map<String, UnimarcFieldDefinition> fieldDirectory = new HashMap<>();

    public void add(UnimarcFieldDefinition fieldDefinition) {
        fieldDirectory.put(fieldDefinition.getTag(), fieldDefinition);
    }

    public int size() {
        return fieldDirectory.size();
    }

    /**
     * Retrieve a field definition by the given dataField's tag.
     * This method is used as a means of keeping consistency with the PicaSchemaManager in hopes of being
     * able to provide a unified interface for both in the future.
     * @param dataField The data field to look up
     * @return The definition for the respective dataField
     */
    public UnimarcFieldDefinition lookup(UnimarcDataField dataField) {
        String tag = dataField.getTag();
        return lookup(tag);
    }

    public UnimarcFieldDefinition lookup(String searchTerm) {
        return fieldDirectory.get(searchTerm);
    }
}
