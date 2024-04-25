<?php

class Test extends Catalogue {
  protected $name = 'test';
  protected $label = 'Ein megatoller Test mit einem eigenen (QA) Katalog';
  protected $url = 'https://example.com/';
  protected $marcVersion = 'DNB';
  protected $linkTemplate = 'http://example.com/{id}';
}
