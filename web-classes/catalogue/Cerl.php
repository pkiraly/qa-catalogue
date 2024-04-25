<?php


class Cerl extends Catalogue {

  protected $name = 'cerl';
  protected $label = 'The Heritage of the Printed Book Database';
  protected $url = 'https://www.cerl.org/resources/hpb/main/';

  function getOpacLink($id, $record) {
    $identifier = '';
    foreach ($record->getDoc()->{'035a_SystemControlNumber_ss'} as $tag35a) {
      if (!preg_match('/OCoLC/', $tag35a)) {
        $identifier = $tag35a;
        break;
      }
    }
    return 'http://hpb.cerl.org/record/' . $identifier;
  }
}