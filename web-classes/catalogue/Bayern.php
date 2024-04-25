<?php

class Bayern extends Catalogue {
  protected $name = 'bayern';
  protected $label = 'Verbundkatalog B3Kat des Bibliotheksverbundes Bayern (BVB) und des Kooperativen Bibliotheksverbundes Berlin-Brandenburg (KOBV)';
  protected $url = 'https://www.bib-bvb.de/';
  protected $marcVersion = 'B3KAT';
  protected $linkTemplate = 'http://gateway-bayern.de/{id}';
}
