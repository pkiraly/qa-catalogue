<?php

class Gent extends Catalogue {
  protected $name = 'gent';
  protected $label = 'Universiteitsbibliotheek Gent';
  protected $url = 'https://lib.ugent.be/';
  protected $marcVersion = 'GENT';
  protected $linkTemplate = 'https://lib.ugent.be/catalog/rug01:{id}';
}
