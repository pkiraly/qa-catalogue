package de.gwdg.metadataqa.marc.definition.tags.tags84x;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.definition.tags.tags3xx.Tag300;
import org.junit.Test;

public class Tag880Test {

	@Test
	public void test1() {
		DataField field = new DataField(Tag880.getInstance(), "1", " ",
			"6", "100-01/(2/r", "a", "פריימן, חיים בן ישראל מאיר.");


	}
	/*
	"10 $6245-02/(2/r$aספר קיצור דיני תרומות ומעשרות /$cמאת חיים בן ישראל מאיר פריימן.",
	"14 $6246-03/(2/r$aקיצור דיני תרומות ומעשרות"
	"   $6260-04/(2/r$aבני־ברק :$bמישור,$c759 [1998 or 1999].",
	"1  $6100-01/$1$a吳正德.",
	"10 $6245-02/$1$a頭戴之硬盔 /$c[撰文・編輯吳正德].",
	"   $6250-03/$1$a初版.",
	"   $6260-04/$1$a台北縣三芝鄉 :$b財團法人李天禄布袋戲文敎基金會,$c民國87 [1998]"
	*/
}
