<?php

class Mokka extends Catalogue {
  protected $name = 'mokka';
  protected $label = 'Magyar Országos Közös Katalógus';
  protected $url = 'http://mokka.hu/';
  protected $linkTemplate = 'http://mokka.hu/web/guest/record/-/record/{id}';
}
