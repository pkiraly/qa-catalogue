<?php

define('LN', "\n");
define('UPDATE_URL', 'http://localhost:8983/solr/%s/update');
define('GET_SOME', 'http://localhost:8983/solr/%s/select?q=*%%3A*&start=%d&rows=%d');

$source_core = $argv[1];
$target_core = $argv[2];

// $source_core = 'validation';
// $target_core = 'k10plus_pica_grouped';

$start = 0;
$rows = 50;
$count  = sprintf('http://localhost:8983/solr/%s/select?q=*%%3A*&rows=0', $source_core);

$resp = json_decode(file_get_contents($count));
$numFound = (int) $resp->response->numFound;
printf("Copy groupId and errorId from %s to %s (in %d records)\n", $source_core, $target_core, $numFound);

while ($start < $numFound) {
  if ($start == 0 || $start % 1000 == 0)
    printf("%s> %d\n", date_format(date_create(), "Y-m-d H:i:s"), $start);
  $records = [];
  $resp = json_decode(file_get_contents(sprintf(GET_SOME, $source_core, $start, $rows)));
  foreach ($resp->response->docs as $doc) {
    $id = $doc->id;
    $record = (object)[
      'id' => $doc->id,
      'groupId_is' => (object)['set' => $doc->groupId_is],
    ];
    if (isset($doc->errorId_is))
      $record->errorId_is = (object)['set' => $doc->errorId_is];
    $records[] = $record;
  }
  update($target_core, $records);
  $start += $rows;
}
update($target_core, (object)["commit" => (object)[]]);

printf("%s> DONE\n", date_format(date_create(), "Y-m-d H:i:s"));

function update($target_core, $data) {
  $ch = curl_init(sprintf(UPDATE_URL, $target_core)); 
  curl_setopt($ch, CURLOPT_POST, 1);
  curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
  curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1); 
  curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json'));
  $result = json_decode(curl_exec($ch));
  curl_close($ch);
  if ($result->responseHeader->status != 0)
    print_r($result);
}

