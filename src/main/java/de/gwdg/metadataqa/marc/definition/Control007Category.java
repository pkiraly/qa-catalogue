package de.gwdg.metadataqa.marc.definition;

public enum Control007Category {
	Common("common", "Common"),
	Map("a", "Map"),
	ElectronicResource("c", "Electronic resource"),
	Globe("d", "Globe"),
	TactileMaterial("f", "Tactile material"),
	ProjectedGraphic("g", "Projected graphic"),
	Microform("h", "Microform"),
	NonprojectedGraphic("k", "Nonprojected graphic"),
	MotionPicture("m", "Motion picture"),
	Kit("o", "Kit"),
	NotatedMusic("q", "Notated music"),
	RemoteSensingImage("r", "Remote-sensing image"),
	SoundRecording("s", "Sound recording"),
	Text("t", "Text"),
	Videorecording("v", "Videorecording"),
	Unspecified("z", "Unspecified");

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

	public static Control007Category byCode(String code) {
		for(Control007Category category : values())
			if (category.code.equals(code))
				return category;
		return null;
	}

}
