<?php

class Bl extends Catalogue {
  protected $name = 'bl';
  protected $label = 'British Library';
  protected $url = 'http://bl.uk/';
  protected $marcVersion = 'BL';
  protected $linkTemplate = 'http://explore.bl.uk/BLVU1:LSCOP-ALL:BLL01{id}';
}
