<?php
/**
 * This script filters command line parameters for the QA Catalogue scripts.
 * It filters out those values that are not defined for a particular script and returns the rest,
 * Parameters:
 *  - the first parameter is the name of the script (such as validate, completeness etc.)
 *  - all the other parameters are those that we intend to pass the script
 * Returns
 *  - the acceptable parameters for that script
 */
define('LN', "\n");

$type = $argv[1];
$parameters = $argv;
$full_definition = json_decode(file_get_contents('cli-parameter-definitions.json'));
if (!isset($full_definition->{$type})) {
  if (in_array($type, ['marc-history', 'bl-classification'])) {
    $type = 'common';
  } else {
    die('ERROR: undefined type: ' . $type . LN);
  }
}

$param_definitions = array_merge($full_definition->common, $full_definition->{$type});

$filtered = [];
$is_key = true;
for ($i = 2; $i < count($parameters); $i++) {
  if ($is_key) {
    if (preg_match('/^-(\w)$/', $parameters[$i], $matches)) {
      $param_definition = find_definition($matches[1], 'short');
    }
    elseif (preg_match('/^--(\w+)$/', $parameters[$i], $matches)) {
      $param_definition = find_definition($matches[1], 'long');
    }
    else {
      $param_definition = null;
    }
    if (!is_null($param_definition)) {
      $filtered[] = $parameters[$i];
      if ($param_definition->hasArg)
      	$is_key = false;
    }
  } else {
  	$filtered[] = $parameters[$i];
  	$is_key = true;
  }
}

echo join(' ', $filtered), LN;

function find_definition($key, $arg_type) {
  global $param_definitions;

  foreach ($param_definitions as $param) {
  	if ($arg_type == 'short' && $param->short == $key)
  	  return $param;
  	if ($arg_type == 'long' && $param->long == $key)
  	  return $param;
  }
  return null;
}
