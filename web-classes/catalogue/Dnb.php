<?php

class Dnb extends Catalogue {
  protected $name = 'dnb';
  protected $label = 'Deutsche Nationalbibliothek';
  protected $url = 'https://www.dnb.de/';
  protected $marcVersion = 'DNB';
  protected $linkTemplate = 'http://d-nb.info/{id}';
}
