package de.gwdg.metadataqa.marc.definition.controltype;

public enum Control007Category implements ControlType {
	COMMON         ("common", "Common"),
	MAP                 ("a", "Map"),
	ELECTRONIC_RESOURCE ("c", "Electronic resource"),
	GLOBE               ("d", "Globe"),
	TACTILE_MATERIAL    ("f", "Tactile material"),
	PROJECTED_GRAPHIC   ("g", "Projected graphic"),
	MICROFORM           ("h", "Microform"),
	NONPROJECTED_GRAPHIC("k", "Nonprojected graphic"),
	MOTION_PICTURE      ("m", "Motion picture"),
	KIT                 ("o", "Kit"),
	NOTATED_MUSIC       ("q", "Notated music"),
	REMOTE_SENSING_IMAGE("r", "Remote-sensing image"),
	SOUND_RECORDING     ("s", "Sound recording"),
	TEXT                ("t", "Text"),
	VIDEO_RECORDING     ("v", "Videorecording"),
	UNSPECIFIED         ("z", "Unspecified");

	String code;
	String label;

	Control007Category(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	public String getValue() {
		return label;
	}

	public static Control007Category byCode(String code) {
		for(Control007Category category : values())
			if (category.code.equals(code))
				return category;
		return null;
	}

}
