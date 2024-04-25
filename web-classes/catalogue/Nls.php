<?php

class Nls extends Catalogue {
  protected $name = 'nls';
  protected $label = 'The National Bibliography of Scotland (from the National Library of Scotland)';
  protected $url = 'https://www.nls.uk/';
  protected $linkTemplate = 'https://search.nls.uk/primo-explore/search?query=any,contains,%22{id}'
                          . '%22&tab=tab1_local&search_scope=SCOPE1&vid=44NLS_VU1&lang=en_US&offset=0';
}
