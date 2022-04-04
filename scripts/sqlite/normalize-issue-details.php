<?php

$dir = $argv[1];
if (preg_match('/[^\/]$/', $dir))
  $dir .= '/';

$inputFile  = $dir . 'issue-details.csv';
echo 'input: ', $inputFile, "\n";
$in = fopen($inputFile, "r");

$outputFile = $dir . 'issue-details-normalized.csv';
echo 'output: ', $outputFile, "\n";
$out = fopen($outputFile, 'w');

while (($line = fgets($in)) != false) {
  $values = str_getcsv($line);
  $id = $values[0];
  if ($id == 'recordId') {
    fputcsv($out, ['id', 'errorId', 'instances']);
  } else {
    $values = explode(';', $values[1]);
    foreach ($values as $value) {
      list($errorId, $instances) = explode(':', $value, 2);
      // printf("%s,%d,%d\n", $id, (int) $errorId, (int) $value);
      fputcsv($out, [$id, (int) $errorId, (int) $instances]);
    }
  }
}

fclose($in);
fclose($out);
echo "normalization is DONE\n";
