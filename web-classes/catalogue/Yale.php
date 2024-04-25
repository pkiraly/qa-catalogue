<?php

class Yale extends Catalogue {
  protected $name = 'yale';
  protected $label = 'Yale Library';
  protected $url = 'https://library.yale.edu/';
  protected $linkTemplate = 'https://search.library.yale.edu/catalog/{id}';
}
