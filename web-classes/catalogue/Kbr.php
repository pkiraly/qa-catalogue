<?php

class Kbr extends Catalogue {
  protected $name = 'kbr';
  protected $label = 'KBR (Koninklijke Bibliotheek van België/Bibliothèque royale de Belgique)';
  protected $url = 'https://www.kbr.be/';
  protected $marcVersion = 'KBR';
  protected $linkTemplate = 'https://opac.kbr.be/LIBRARY/doc/SYRACUSE/{id}';
}
