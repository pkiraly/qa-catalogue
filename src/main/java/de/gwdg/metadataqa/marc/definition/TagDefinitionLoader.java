package de.gwdg.metadataqa.marc.definition;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class TagDefinitionLoader {
	private static final Pattern PATTERN_20x = Pattern.compile("^2[0-4]\\d$");
	private static final Pattern PATTERN_25x = Pattern.compile("^2[5-9]\\d$");
	private static final Pattern PATTERN_70x = Pattern.compile("^7[0-5]\\d$");
	private static final Pattern PATTERN_76x = Pattern.compile("^7[6-9]\\d$");
	private static final Pattern PATTERN_80x = Pattern.compile("^8[0-3]\\d$");
	private static final Pattern PATTERN_84x = Pattern.compile("^8[4-9]\\d$");

	public static DataFieldDefinition load(String tag) {
		DataFieldDefinition dataFieldDefinition = null;
		try {
			String className = getClassName(tag);
			if (className != null) {
				Class definitionClazz = Class.forName(className);
				Method getInstance = definitionClazz.getMethod("getInstance");
				dataFieldDefinition = (DataFieldDefinition) getInstance.invoke(definitionClazz);
			}
		} catch (ClassNotFoundException e) {
			// e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return dataFieldDefinition;
	}

	public static String getClassName(String tag) {
		String packageName = null;
		if (tag.startsWith("0")) {
			packageName = "tags01x";
		} else if (tag.startsWith("1")) {
			packageName = "tags1xx";
		} else if (PATTERN_20x.matcher(tag).matches()) {
			packageName = "tags20x";
		} else if (PATTERN_25x.matcher(tag).matches()) {
			packageName = "tags25x";
		} else if (tag.startsWith("3")) {
			packageName = "tags3xx";
		} else if (tag.startsWith("4")) {
			packageName = "tags4xx";
		} else if (tag.startsWith("5")) {
			packageName = "tags5xx";
		} else if (tag.startsWith("6")) {
			packageName = "tags6xx";
		} else if (PATTERN_70x.matcher(tag).matches()) {
			packageName = "tags70x";
		} else if (PATTERN_76x.matcher(tag).matches()) {
			packageName = "tags76x";
		} else if (PATTERN_80x.matcher(tag).matches()) {
			packageName = "tags80x";
		} else if (PATTERN_84x.matcher(tag).matches()) {
			packageName = "tags84x";
		}

		if (packageName == null)
			return null;

		return String.format("de.gwdg.metadataqa.marc.definition.%s.Tag%s", packageName, tag);
	}
}
