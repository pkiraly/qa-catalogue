<?php

define('LN', "\n");
define('CMD', "curl -X POST -H 'Content-Type: application/json' 'http://localhost:8983/solr/%s/update' --data-binary '%s'");

function index($records) {
  global $solrUrl;

  $ch = curl_init($solrUrl); 
  curl_setopt($ch, CURLOPT_POST, 1);
  curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($records));
  curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1); 
  curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json'));
  $result = curl_exec($ch);
  curl_close($ch);
  // print_r ($result);
}

function processIssueDetails($dir) {
  $fileDetails  = $dir . 'issue-details.csv';
  echo 'fileDetails: ', $fileDetails, "\n";
  $handle = fopen($fileDetails, "r");
  if ($handle) {
    $i = 0;
    $records = [];
    while (($line = fgets($handle)) != false) {
      $values = str_getcsv($line);
      $id = $values[0];
      if ($id != 'recordId') {
        if (++$i % 100000 == 0)
          echo $i, LN;
        $record = (object)['id' => $id, 'errorId_is' => []];
        $pairs = explode(';', $values[1]);
        foreach ($pairs as $pair) {
          list($eid, $iid) = explode(':', $pair);
          $record->errorId_is[] = (int) $eid;
        }
        $records[] = $record;
        if (count($records) == 10000) {
          index($records);
          $records = [];
        }
      }
    }

    index($records);
    index((object)["commit" => (object)[]]);
    fclose($handle);
  }
}

$dir = $argv[1];
$host = $argv[2];
$core = $argv[3];

if (preg_match('/[^\/]$/', $dir))
  $dir .= '/';

$solrUrl = sprintf('%s/solr/%s/update', $host, $core);

processIssueDetails($dir);

echo "indexing is DONE\n";
exit();
