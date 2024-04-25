<?php

class Uva extends Catalogue {
  protected $name = 'uva';
  protected $label = 'Bibliotheek Universiteit van Amsterdam/Hogeschool van Amsterdam';
  protected $url = 'https://uba.uva.nl/home';
  protected $marcVersion = 'UVA';
  protected $linkTemplate = 'https://pid.uba.uva.nl/ark:/88238/b1{id}';
}
