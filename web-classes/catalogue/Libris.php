<?php

class Libris extends Catalogue {
  protected $name = 'libris';
  protected $label = 'Libris, the Swedish national union catalogue';
  protected $url = 'https://libris.kb.se/';
  protected $linkTemplate = 'http://libris.kb.se/bib/{id}';
}
