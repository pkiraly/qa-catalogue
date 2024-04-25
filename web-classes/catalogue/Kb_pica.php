<?php

class Kb_pica extends Catalogue {

  protected $name = 'kb_pica';
  protected $label = 'KB (PICA)';
  protected $url = 'https://opac.k10plus.de';
  protected $schemaType = 'PICA';
  protected $position = 4;
  public static $supportedTypes = [
    'Druckschriften (einschließlich Bildbänden)', 'Tonträger, Videodatenträger, Bildliche Darstellungen',
    'Blindenschriftträger und andere taktile Materialien', 'Mikroform', 'Handschriftliches Material',
    'Lokales Katalogisat (nur GBV)', 'Elektronische Ressource im Fernzugriff', 'Elektronische Ressource auf Datenträger',
    'Objekt', 'Medienkombination', 'Mailboxsatz'
  ];
  protected $defaultLang = 'de';
  protected $linkTemplate = 'https://opac.k10plus.de/DB=2.299/PPNSET?PPN={id}&PRS=HOL&HILN=888&INDEXSET=21';
}
