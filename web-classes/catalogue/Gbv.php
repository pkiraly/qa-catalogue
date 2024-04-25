<?php

class Gbv extends Catalogue {
  protected $name = 'gbv';
  protected $label = 'Verbundzentrale des Gemeinsamen Bibliotheksverbundes';
  protected $url = 'http://www.gbv.de/';
  protected $linkTemplate = 'https://kxp.k10plus.de/DB=2.1/PPNSET?PPN={id}';
}
