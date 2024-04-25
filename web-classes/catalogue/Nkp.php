<?php


class Nkp extends Catalogue {

  protected $name = 'nkp';
  protected $label = 'Národní knihovna České republiky';
  protected $url = 'https://nkp.cz/';
  protected $marcVersion = 'NKCR';

  function getOpacLink($id, $record) {
    $rid = FALSE;
    foreach ($record->getFields('015') as $tag) {
      if (isset($tag->subfields->a)) {
        $rid = str_replace('cnb', '', $tag->subfields->a);
        break;
      }
    }
    if ($rid === FALSE)
      $rid = preg_replace('/^[a-z]{1,3}\d{4}/', '00', trim($id));
    return 'https://aleph.nkp.cz/F/?func=direct'
         . '&doc_number=' . $rid
         . '&local_base=CNB';
  }
}