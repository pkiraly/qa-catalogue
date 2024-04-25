<?php


class Bnpl extends Catalogue {

  protected $name = 'bnpl';
  protected $label = 'Biblioteka Narodowa (Polish National Library)';
  protected $url = 'https://bn.org.pl/';

  function getOpacLink($id, $record) {
    foreach ($record->getDoc()->{'035a_SystemControlNumber_ss'} as $tag35a) {
      if (preg_match('/^\d/', $tag35a)) {
        $identifier = $tag35a;
        break;
      }
    }
    return sprintf(
      'https://katalogi.bn.org.pl/discovery/fulldisplay?docid=alma%s&context=L&vid=48OMNIS_NLOP:48OMNIS_NLOP&search_scope=NLOP_IZ_NZ&tab=LibraryCatalog&lang=pl',
      trim($identifier));
  }
}
