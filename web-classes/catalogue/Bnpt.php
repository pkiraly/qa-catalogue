<?php


class Bnpt extends Catalogue {

  protected $name = 'bnpt';
  protected $label = 'Biblioteca Nacional de Portugal (Portugal National Library)';
  protected $url = 'https://www.bnportugal.gov.pt/';
  protected $schemaType = "UNIMARC";

  function getOpacLink($id, $record) {
    return sprintf('http://id.bnportugal.gov.pt/bib/catbnp/%s', trim($id));
  }
}

