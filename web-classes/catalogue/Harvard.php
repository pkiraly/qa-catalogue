<?php

class Harvard extends Catalogue {
  protected $name = 'harvard';
  protected $label = 'Harvard Library';
  protected $url = 'https://library.harvard.edu/';
  protected $linkTemplate = 'http://id.lib.harvard.edu/alma/{id}/catalog';
}
