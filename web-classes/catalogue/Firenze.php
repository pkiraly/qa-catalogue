<?php


class Firenze extends Catalogue {

  protected $name = 'firenze';
  protected $label = 'Biblioteca Nazionale Centrale di Firenze';
  protected $url = 'https://www.bncf.firenze.sbn.it/';
  protected $marcVersion = 'MARC21';

  function getOpacLink($id, $record) {
    $rid = trim($id);
    if (preg_match('/^IT\\\+ICCU\\\+CUB\\\+/', $rid)) {
      $rid = preg_replace('/^IT\\\+ICCU\\\+CUB\\\+/', 'CUB', $rid);
    }

    return 'https://opac.bncf.firenze.sbn.it/bncf-prod/resource?uri=' . $rid . '&found=1';
  }
}
