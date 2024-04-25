<?php

class Catalogue {

  protected $name;
  protected $label;
  protected $url;
  protected $schemaType = 'MARC21';
  protected $marcVersion = 'MARC21';
  protected $position = 3;
  public static $supportedTypes = [
    'Books', 'Computer Files', 'Continuing Resources', 'Maps', 'Mixed Materials', 'Music', 'Visual Materials', 'all'
  ];
  protected $defaultLang = 'en';
  protected $linkTemplate;

  public function __construct(Utils\Configuration $configuration) {
    $this->name = $this->name ?? $configuration->getCatalogue() ?? '';
    $this->label = $configuration->getLabel() ?? $this->label;
    $this->url = $configuration->getUrl() ?? $this->url;
    $this->schemaType = $configuration->getSchema() ?? $this->schemaType;
    $this->defaultLang = $configuration->getLanguage() ?? $this->defaultLang;
    $this->linkTemplate = $configuration->getLinkTemplate() ?? $this->linkTemplate;
  }

  public function getOpacLink($id, $record) {
    if ($this->linkTemplate && $id) {
      return str_replace('{id}', trim($id), $this->linkTemplate);
    }
  }

  public function getName() {
    return $this->name;
  }

  public function getLabel() {
    return $this->label;
  }

  public function getUrl() {
    return $this->url;
  }

  public function getSchemaType(): string {
    return $this->schemaType;
  }

  public function getMarcVersion() {
    return $this->marcVersion;
  }

  public function getTag(string $input): string {
    return $this->schemaType == 'PICA' ?
      substr($input, 0, strpos($input, '$')) :
      substr($input, 0, 3);
  }

  public function getSubfield($input): string {
    if (is_null($input))
      return "";
    return $this->schemaType == 'PICA' ?
      substr($input, strpos($input, '$')) :
      substr($input, 3);
  }

  public function getDefaultLang(): string {
    return $this->defaultLang;
  }
}
