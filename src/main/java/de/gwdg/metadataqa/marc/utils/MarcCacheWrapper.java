package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarcCacheWrapper {

	private JsonPathCache<? extends XmlFieldInstance> cache;
	private static final List<String> controlfields = Arrays.asList("001", "003", "005", "006", "007", "008");

	public MarcCacheWrapper(JsonPathCache<? extends XmlFieldInstance> cache) {
		this.cache = cache;
	}

	public String extractCompact(String tag) {
		String[] parts = tag.split("\\$");

		if (parts.length == 1)
			return extract(tag);

		return extract(parts[0], parts[1]);
	}

	public String extract(String tag) {
		return extract(tag, 0, null);
	}

	public String extract(String tag, String code) {
		return extract(tag, 0, code);
	}

	public String extract(String tag, int counter, String code) {
		String value = null;
		String path = createPath(tag, counter, code);
		if (path != null) {
			List<? extends XmlFieldInstance> list = cache.getCache().get(path);
			if (list != null && !list.isEmpty() && list.get(0) != null) {
				value = extractValues(path);
			}
		}
		return value;
	}

	private String createPath(String tag, int counter, String code) {
		String path = null;

		if (tag.equals("leader")) {
			path = "$.leader";
		} else if (isControlField(tag)) {
			path = String.format("$.controlfield[?(@.tag == '%s')].content", tag);
		} else {
			path = String.format("$.datafield[?(@.tag == '%s')]/%d/$.%s", tag, counter, getSuffix(code));
		}

		return path;
	}

	private String getSuffix(String code) {
		String suffix = "";
		if (code.equals("ind1") || code.equals("ind2"))
			suffix = code;
		else
			suffix = String.format("subfield[?(@.code == '%s')].content", code);
		return suffix;
	}

	private String extractValues(String path) {
		String value;List<String> values = new ArrayList<>();
		for (XmlFieldInstance field : cache.getCache().get(path)) {
			if (field.getValue() != null)
				values.add(field.getValue());
		}
		value = StringUtils.join(values, "|");
		return value;
	}

	private boolean isControlField(String tag) {
		return controlfields.contains(tag);
	}

}
