package de.gwdg.metadataqa.marc.model.kos;

public enum KosType {
  LIST(11, KosCategory.TERM_LIST, "list",
    "http://bartoc.org/en/taxonomy/term/13689",
    "limited sets of terms in some sequential order"),
  DICTIONARY(12, KosCategory.TERM_LIST, "dictionary",
    "http://bartoc.org/en/taxonomy/term/13689",
    "alphabetical lists of terms and their definitions that provide variant senses for each term, where applicable"),
  GLOSSARY(13, KosCategory.TERM_LIST, "glossary",
    "http://bartoc.org/en/taxonomy/term/12506",
    "alphabetical lists of terms, usually with definitions"),
  SYNONYM_RING(14, KosCategory.TERM_LIST, "synonym ring",
    "http://bartoc.org/en/taxonomy/term/13695",
    "sets of terms that are considered equivalent for the purpose of retrieval"),

  NAME_AUTHORITY_LIST(21, KosCategory.METADATA_LIKE_MODEL, "name authority list",
    "http://bartoc.org/en/taxonomy/term/13692",
    "lists of terms that are used to control the variant names for an entity or the domain value for a particular field"),
  GAZETTEER(22, KosCategory.METADATA_LIKE_MODEL, "gazetteer",
    "http://bartoc.org/en/taxonomy/term/13690",
    "geospatial dictionaries of named and typed places"),

  SUBJECT_HEADING(31, KosCategory.CLASSIFICATION, "subject heading scheme",
    "http://bartoc.org/en/taxonomy/term/13694",
    "schemes that provide a set of controlled terms to represent the subjects of items in a collection and sets of rules for combining terms into compound headings"),
  CATEGORIZATION(32, KosCategory.CLASSIFICATION, "categorization scheme",
    "http://bartoc.org/en/taxonomy/term/13688",
    "loosely formed grouping schemes"),
  TAXONOMY(33, KosCategory.CLASSIFICATION, "taxonomy",
    "http://bartoc.org/en/taxonomy/term/5",
    "divisions of items into ordered groups or categories based on particular characteristics"),
  CLASSIFICATION_SCHEME(34, KosCategory.CLASSIFICATION, "classification scheme",
    "http://bartoc.org/en/taxonomy/term/3",
    "hierarchical and faceted arrangements of numerical or alphabetical notations to represent broad topics"),

  THESAURUS(41, KosCategory.RELATIONSHIP_MODEL,  "thesaurus",
    "http://bartoc.org/en/taxonomy/term/1",
    "sets of terms representing concepts and the hierarchical, equivalence, and associative relationships among them"),
  SEMANTIC_NETWORK(42, KosCategory.RELATIONSHIP_MODEL, "semantic network",
    "http://bartoc.org/en/taxonomy/term/13693",
    "sets of terms representing concepts, modeled as the nodes in a network of variable relationship types"),
  ONTOLOGY(43, KosCategory.RELATIONSHIP_MODEL, "ontology",
    "http://bartoc.org/en/taxonomy/term/2",
    "specific concept models representing complex relationships between objects, including the rules and axioms that are missing in semantic networks");

  private int score = 0;
  private KosCategory parent = null;
  private String label = null;
  private String url = null;
  private String description = null;

  KosType(int score, KosCategory parent, String label, String url) {
    this.score = score;
    this.parent = parent;
    this.label = label;
    this.url = url;
  }

  KosType(int score, KosCategory parent, String label, String url, String description) {
    this(score, parent, label, url);
    this.description = description;
  }

  public int getScore() {
    return score;
  }

  public String getLabel() {
    return label;
  }

  public String getUrl() {
    return url;
  }
}
