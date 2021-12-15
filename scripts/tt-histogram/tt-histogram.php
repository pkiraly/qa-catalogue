<?php

$dir = $argv[1];

$prefix = 'tt-completeness';
$inputFile = $dir . '/' . $prefix . '.csv';
$in = fopen($inputFile, "r");

$header = '';
$collector = [];
$lineNumber = 0;
while (($line = fgets($in)) != false) {
  $lineNumber++;
  $values = str_getcsv($line);
  if ($header == '') {
    $header = $values;
  } else {
    // 
    if (count($header) != count($values)) {
      error_log(sprintf('error in %s line #%d: %d vs %d', $inputFile, $lineNumber, count($header), count($values)));
    } else {
      $record = (object)array_combine($header, $values);
      foreach ($header as $field) {
        if ($field != 'id') {
          $value = $record->{$field};
          if (!isset($collector[$field][$value]))
            $collector[$field][$value] = 1;
          else
            $collector[$field][$value]++;
        }
      }
    }
  }
}
fclose($in);

foreach ($collector as $field => $values) {
  $histogram_file = sprintf("%s/%s-histogram-%s.csv", $dir, $prefix, $field);
  echo $histogram_file, "\n";
  ksort($values);
  $lines = ['count,frequency'];
  foreach ($values as $a => $b)
  	$lines[] = sprintf("%d,%d", $a, $b);
  file_put_contents($histogram_file, implode("\n", $lines));
}

echo "DONE";