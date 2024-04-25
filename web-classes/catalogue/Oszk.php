<?php


class Oszk extends Catalogue {

  protected $name = 'oszk';
  protected $label = 'Országos Széchényi Könyvtár';
  protected $url = 'https://www.oszk.hu/';
  // protected $marcVersion = 'B3KAT';

  function getOpacLink($id, $record) {
    return 'https://nektar.oszk.hu/hu/manifestation/' . preg_replace('/^0+/', '', trim($id));
  }
}
