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

function getDetails() {
  global $inDe, $inDeFirst;

  $line = fgets($inDe);
  if ($inDeFirst) {
    $line = fgets($inDe);
    $inDeFirst = false;
  }

  $errorIds = false;
  if ($line != false) {
    $values = str_getcsv($line);
    $id = $values[0];
    if ($id != 'recordId') {
      $errorIds = (object)['id' => $id];
      $pairs = explode(';', $values[1]);
      foreach ($pairs as $pair) {
        list($eid, $iid) = explode(':', $pair);
        $errorIds->eids[] = (int) $eid;
      }
    }
  }
  return $errorIds;
}

function processIdGroupid() {
  global $dir;

  $fileId  = $dir . 'id-groupid.csv';
  echo 'fileId: ', $fileId, "\n";
  $inId = fopen($fileId, "r");

  $records = [];
  $record = false;
  $prevId = false;
  $details = false;
  $i = 0;
  while (($line = fgets($inId)) != false) {
    if (++$i % 100000 == 0)
      echo $i, LN;
    $values = str_getcsv($line);
    $id = $values[0];
    $gid = $values[1] == 'all' ? 0 : (int)$values[1];
    if ($id != 'id') {
      if ($id != $prevId) {
        if ($record !== false) {
          if ($details !== false) {
            if ($record->id == $details->id) {
              $record->errorId_is = $details->eids;
              $details = false;
            }
          } else {
            $details = getDetails();
            if ($details !== false && $record->id == $details->id) {
              $record->errorId_is = $details->eids;
              $details = false;
            }
          }
          $records[] = $record;
        }

        if (count($records) == 10000) {
          index($records);
          $records = [];        
        }
        $record = (object)['id' => $id, 'groupId_is' => []];
      }
      $record->groupId_is[] = $gid;
    }
    $prevId = $id;
  }
  $records[] = $record;
  index($records);
  index((object)["commit" => (object)[]]);

  fclose($inId);
}

$dir = $argv[1];
if (preg_match('/[^\/]$/', $dir))
  $dir .= '/';

$core = $argv[2];
$solrUrl = sprintf('http://localhost:8983/solr/%s/update', $core);
$inDeFirst = true;

$fileDetails  = $dir . 'issue-details.csv';
echo 'fileDetails: ', $fileDetails, "\n";
$inDe = fopen($fileDetails, "r");

processIdGroupid();

echo "indexing is DONE\n";
exit();
