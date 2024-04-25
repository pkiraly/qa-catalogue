<?php

class Knihoveda extends Catalogue {
  protected $name = 'knihoveda';
  protected $label = 'Knihoveda';
  protected $url = 'https://www.zb.uzh.ch/';
  // protected $marcVersion = 'GENT';
  protected $linkTemplate = 'https://uzb.swisscovery.slsp.ch/permalink/41SLSP_UZB/1d8t6qj/alma{id}';
}
