package de.gwdg.metadataqa.marc.cli.utils.translation;

import de.gwdg.metadataqa.api.configuration.ConfigurationReader;
import de.gwdg.metadataqa.api.configuration.SchemaConfiguration;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.schema.Schema;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.gwdg.metadataqa.marc.cli.utils.translation.ContributorNormaliser.TYPE.AUTHOR;
import static de.gwdg.metadataqa.marc.cli.utils.translation.ContributorNormaliser.TYPE.EDITOR;
import static de.gwdg.metadataqa.marc.cli.utils.translation.ContributorNormaliser.TYPE.TRANSLATOR;
import static org.junit.Assert.*;

public class ContributorNormaliserTest {

  SchemaConfiguration schemaConfiguration;
  ContributorNormaliser contributorNormaliser;

  @Before
  public void setUp() throws Exception {
    String shaclFilePath = Paths.get("scripts/translations/translations-shacl.yml").toAbsolutePath().toString();
    schemaConfiguration = ConfigurationReader.readSchemaYaml(shaclFilePath);
    Schema schema = schemaConfiguration.asSchema();
    List<String> patterns = schema.getPathByLabel("245$c").getRules().get(0).getMqafPattern().getPattern();
    // List<String> patterns = List.of("ubersetzer", "Fordította|fordt́otta|Forditotta|Fordították", "[Ff]ord\\.");
    Pattern contribPattern = Pattern.compile(String.format("(%s)", StringUtils.join(patterns, "|")));
    System.err.println();
    contributorNormaliser = new ContributorNormaliser("", contribPattern);
  }

  @Test
  public void hasLowercase() {
    assertTrue(contributorNormaliser.hasLowercase("[ford. Tótisz András]"));
    assertFalse(contributorNormaliser.hasLowercase("[Ford. Tótisz András]"));
    assertTrue(contributorNormaliser.hasLowercase("[átd. Tótisz András]"));
    assertFalse(contributorNormaliser.hasLowercase("[Átd. Tótisz András]"));

    assertFalse(contributorNormaliser.hasLowercase("Honoré de Balzac"));
    assertFalse(contributorNormaliser.hasLowercase("José Rodrigues dos Santos"));
    assertFalse(contributorNormaliser.hasLowercase("Arkagyij és Borisz Sztrugackij"));
    assertFalse(contributorNormaliser.hasLowercase("Inga von Bernau"));
    assertFalse(contributorNormaliser.hasLowercase("írta Verne Gyula"));
    assertFalse(contributorNormaliser.hasLowercase("írta és ill. Beatrix Potter"));
    assertFalse(contributorNormaliser.hasLowercase("szerk. Borzsák István"));
    assertFalse(contributorNormaliser.hasLowercase("[zenéjét szerezte Verdi]"));
    assertFalse(contributorNormaliser.hasLowercase("by Ferenc Molnár"));
  }

  @Test
  public void hasContributor() {
    assertTrue(contributorNormaliser.hasContributor("[ubersetzer Tótisz András]"));
    assertTrue(contributorNormaliser.hasContributor("[ford. Tótisz András]"));
    assertTrue(contributorNormaliser.hasContributor("[Ford. Tótisz András]"));
    assertTrue(contributorNormaliser.hasContributor("[átd. Tótisz András]"));
    assertTrue(contributorNormaliser.hasContributor("[Átd. Tótisz András]"));
  }

  @Test
  public void getTranslators() {
    contributorNormaliser.process(List.of(new XmlFieldInstance("[ford. Tótisz András]")));
    assertEquals(List.of("Tótisz András"), contributorNormaliser.getContributors().get(TRANSLATOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("írta és ford. Csanád Béla")));
    assertEquals(List.of("Csanád Béla"), contributorNormaliser.getContributors().get(TRANSLATOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("ford. Illyés Gyula et al.")));
    assertEquals(List.of("Illyés Gyula"), contributorNormaliser.getContributors().get(TRANSLATOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("ford. Füssi-Nagy Géza")));
    assertEquals(List.of("Füssi-Nagy Géza"), contributorNormaliser.getContributors().get(TRANSLATOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("ford. Sz. Elek Judit")));
    assertEquals(List.of("Sz. Elek Judit"), contributorNormaliser.getContributors().get(TRANSLATOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("ford. Hanusovszky Judit és Szántó Péter")));
    assertEquals(List.of("Hanusovszky Judit", "Szántó Péter"), contributorNormaliser.getContributors().get(TRANSLATOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("ford. Babits Mihály, Szabó Lőrinc, Tóth Árpád")));
    assertEquals(List.of("Babits Mihály", "Szabó Lőrinc", "Tóth Árpád"), contributorNormaliser.getContributors().get(TRANSLATOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("vál. és ford. Szopori Nagy Lajos")));
    assertEquals(List.of("Szopori Nagy Lajos"), contributorNormaliser.getContributors().get(TRANSLATOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("ford. és magy. Babits Mihály")));
    assertEquals(List.of("Babits Mihály"), contributorNormaliser.getContributors().get(TRANSLATOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("ford. Szájer Zoltán.")));
    assertEquals(List.of("Szájer Zoltán"), contributorNormaliser.getContributors().get(TRANSLATOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("... ford. Tormay Cecilia")));
    assertEquals(List.of("Tormay Cecilia"), contributorNormaliser.getContributors().get(TRANSLATOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("ford. F. Solti Erzsébet")));
    assertEquals(List.of("F. Solti Erzsébet"), contributorNormaliser.getContributors().get(TRANSLATOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("vál., szerk. és ford. Salamon Gábor és Zalotay Melinda")));
    assertEquals(List.of("Salamon Gábor", "Zalotay Melinda"), contributorNormaliser.getContributors().get(TRANSLATOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("fordította Babits Mihály ... et al.")));
    assertEquals(List.of("Babits Mihály"), contributorNormaliser.getContributors().get(TRANSLATOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("magyar szöveg Szőcs Zsóka")));
    assertEquals(List.of("Szőcs Zsóka"), contributorNormaliser.getContributors().get(TRANSLATOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("ford. Simon P. Ibolya")));
    assertEquals(List.of("Simon P. Ibolya"), contributorNormaliser.getContributors().get(TRANSLATOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("Sárosi Gyula fordításában")));
    assertEquals(List.of("Sárosi Gyula"), contributorNormaliser.getContributors().get(TRANSLATOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("Sárosi Gyula fordításában")));
    assertEquals(List.of("Sárosi Gyula"), contributorNormaliser.getContributors().get(TRANSLATOR));

    // átköltésében
    contributorNormaliser.process(List.of(new XmlFieldInstance("Faludy György átköltésében")));
    assertEquals(List.of("Faludy György"), contributorNormaliser.getContributors().get(TRANSLATOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("a meséket ford. Vikár Béla valamint Kozma Andor és Zempléni Árpád")));
    assertEquals(List.of("Vikár Béla", "Kozma Andor", "Zempléni Árpád"), contributorNormaliser.getContributors().get(TRANSLATOR));
  }

  @Test
  public void getEditors() {
    contributorNormaliser.process(List.of(new XmlFieldInstance("főszerk. Kovács János")));
    assertEquals(List.of("Kovács János"), contributorNormaliser.getContributors().get(EDITOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("vál. és az előszót írta N. Kovács Tímea")));
    assertEquals(List.of("N. Kovács Tímea"), contributorNormaliser.getContributors().get(EDITOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("ed. by Katalin Kürtösi and József Pál")));
    assertEquals(List.of("Katalin Kürtösi", "József Pál"), contributorNormaliser.getContributors().get(EDITOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("ausgew. von Károly Weber")));
    assertEquals(List.of("Károly Weber"), contributorNormaliser.getContributors().get(EDITOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("Lator László válogatása")));
    assertEquals(List.of("Lator László"), contributorNormaliser.getContributors().get(EDITOR));
  }

  @Test
  public void getAuthors() {
    contributorNormaliser.process(List.of(new XmlFieldInstance("Ruben Saillens alapján")));
    assertEquals(List.of("Ruben Saillens"), contributorNormaliser.getContributors().get(AUTHOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("Elizabeth Lenhard adaptációja")));
    assertEquals(List.of("Elizabeth Lenhard"), contributorNormaliser.getContributors().get(AUTHOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("Raymond Radiguet et al. művei")));
    assertEquals(List.of("Raymond Radiguet"), contributorNormaliser.getContributors().get(AUTHOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("La Fontaine és Ezópusz meséit átd. Pádár Éva")));
    assertEquals(List.of("La Fontaine", "Ezópusz", "Pádár Éva"), contributorNormaliser.getContributors().get(AUTHOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("Mark Twain eredeti regényét átd. Oliver Ho")));
    assertEquals(List.of("Mark Twain", "Oliver Ho"), contributorNormaliser.getContributors().get(AUTHOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("Mark Twain eredeti regényét átd. Oliver Ho")));
    assertEquals(List.of("Mark Twain", "Oliver Ho"), contributorNormaliser.getContributors().get(AUTHOR));

    contributorNormaliser.process(List.of(new XmlFieldInstance("Marion Auer regénye")));
    assertEquals(List.of("Marion Auer"), contributorNormaliser.getContributors().get(AUTHOR));
  }

  @Test
  public void regex() {
    Pattern regex = null;
    Matcher matcher = null;
    regex = Pattern.compile("^[\\p{javaUpperCase}][\\p{javaLowerCase}]+$");
    assertTrue(regex.matcher("Tótisz").matches());

    regex = Pattern.compile("^([\\p{javaUpperCase}][\\p{javaLowerCase}]+( [\\p{javaUpperCase}][\\p{javaLowerCase}]+)+)$");
    matcher = regex.matcher("Tótisz András");
    assertTrue(matcher.matches());
    assertEquals("Tótisz András", matcher.group(1));

    regex = Pattern.compile("^ford\\. ([\\p{javaUpperCase}][\\p{javaLowerCase}]+( [\\p{javaUpperCase}][\\p{javaLowerCase}]+)+)$");
    matcher = regex.matcher("ford. Tótisz András");
    assertTrue(matcher.matches());
    assertEquals("Tótisz András", matcher.group(1));
  }

  @Test
  public void regex2() {
    Pattern regex = null;
    Matcher matcher = null;
    regex = Pattern.compile("^([\\p{javaUpperCase}][\\p{javaLowerCase}\\.]*(( |-|\\. | és |, | and | u\\. | valamint )[\\p{javaUpperCase}]([\\p{javaLowerCase}]+|\\.))+)(?: et al\\.|\\.| \\.\\.\\. et al\\.)? (?:fordításában|átköltésében)$");
    matcher = regex.matcher("Faludy György átköltésében");
    assertTrue(matcher.matches());
    assertEquals("Faludy György", matcher.group(1));
  }
}