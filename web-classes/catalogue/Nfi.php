<?php


class Nfi extends Catalogue {

  protected $name = 'nfi';
  protected $label = 'Kansallis Kirjasto/National Biblioteket (The National Library of Finnland)';
  protected $url = 'https://www.kansalliskirjasto.fi/en';
  protected $marcVersion = 'FENNICA';

  function getOpacLink($id, $record) {
    // return 'https://melinda.kansalliskirjasto.fi/byid/' . trim($id);
    return 'https://kansalliskirjasto.finna.fi/Search/Results?bool0[]=OR&lookfor0[]=ctrlnum%3A%22FCC'
        . trim($id)
        . '%22&lookfor0[]=ctrlnum%3A%22(FI-MELINDA)'
        . trim($id)
        . '%22';
  }
}