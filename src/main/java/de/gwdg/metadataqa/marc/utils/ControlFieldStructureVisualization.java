package de.gwdg.metadataqa.marc.utils;

import de.gwdg.metadataqa.marc.definition.*;

import java.util.List;
import java.util.Map;

public class ControlFieldStructureVisualization {
	public static void main(String[] args) {
		String undefPattern = "\\";

		Map<Control008Type, List<ControlSubfield>> allSubfields = Control008Subfields.getSubfields();
		for (Control008Type type : allSubfields.keySet()) {
			System.err.println(type.getValue());
			boolean isFirst = true;
			int lastEnd = 18;
			char chr = type.equals(Control008Type.ALL_MATERIALS) ? 'a' : 'i';
			if (!type.equals(Control008Type.ALL_MATERIALS))
				for (int i = 0; i<18; i++)
					System.err.print(" ");
			for (ControlSubfield subfield : allSubfields.get(type)) {

				if (lastEnd != -1 && lastEnd != subfield.getPositionStart())
					for (int i = lastEnd; i<subfield.getPositionStart(); i++)
						System.err.print(undefPattern);


				for (int i = subfield.getPositionStart(); i<subfield.getPositionEnd(); i++)
					System.err.print(subfield.isRepeatableContent() ? String.valueOf(chr).toUpperCase() : chr);
				chr++;
				lastEnd = subfield.getPositionEnd();
			}

			if (lastEnd < 35)
				for (int i = lastEnd; i<35; i++)
					System.err.print(undefPattern);

			System.err.println();
		}

		Map<Control007Category, List<ControlSubfield>> all007Subfields = Control007Subfields.getSubfields();
		for (Control007Category type : all007Subfields.keySet()) {
			System.err.println(type.getLabel());
			boolean isFirst = true;
			int lastEnd = -1;
			char chr = type.equals(Control007Category.COMMON) ? 'a' : 'a';
			for (ControlSubfield subfield : all007Subfields.get(type)) {

				if (lastEnd != -1 && lastEnd != subfield.getPositionStart())
					for (int i = lastEnd; i<subfield.getPositionStart(); i++)
						System.err.print(undefPattern);


				for (int i = subfield.getPositionStart(); i<subfield.getPositionEnd(); i++)
					System.err.print(subfield.isRepeatableContent() ? String.valueOf(chr).toUpperCase() : chr);
				chr++;
				lastEnd = subfield.getPositionEnd();
			}

			if (lastEnd < 22)
				for (int i = lastEnd; i<22; i++)
					System.err.print(undefPattern);

			System.err.println();
		}

		System.err.println("-------------------------------------");
		Map<Control008Type, List<ControlSubfield>> all006Subfields = Control006Subfields.getSubfields();
		for (Control008Type type : all006Subfields.keySet()) {
			System.err.println(type.getValue());
			boolean isFirst = true;
			int lastEnd = 0;
			char chr = type.equals(Control008Type.ALL_MATERIALS) ? 'a' : 'i';
			for (ControlSubfield subfield : all006Subfields.get(type)) {

				if (lastEnd != subfield.getPositionStart())
					for (int i = lastEnd; i<subfield.getPositionStart(); i++)
						System.err.print(undefPattern);


				for (int i = subfield.getPositionStart(); i<subfield.getPositionEnd(); i++)
					System.err.print(subfield.isRepeatableContent() ? String.valueOf(chr).toUpperCase() : chr);

				chr++;
				lastEnd = subfield.getPositionEnd();
			}

			if (lastEnd <= 17)
				for (int i = lastEnd; i<=17; i++)
					System.err.print(undefPattern);

			System.err.println();
		}

	}
}
