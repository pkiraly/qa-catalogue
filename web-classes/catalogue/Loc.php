<?php

class Loc extends Catalogue {
  protected $name = 'loc';
  protected $label = 'Library of Congress';
  protected $url = 'https://catalog.loc.gov/';
  protected $linkTemplate = 'https://lccn.loc.gov/{id}';
}
