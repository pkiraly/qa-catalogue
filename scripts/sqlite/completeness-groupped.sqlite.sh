#!/usr/bin/env bash
#
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# Strores completeness-groupped-marc-elements.csv into SQLite
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
#

log() {
  timestamp=`date +"%F %T"`
  echo -en "\033[0D\033[1;37m$timestamp>\033[0m "
  echo "$1"
}

OUTPUT_DIR=$1

log "create table groupped_marc_elements"
sqlite3 ${OUTPUT_DIR}/qa_catalogue.sqlite << EOF
CREATE TABLE IF NOT EXISTS "groupped_marc_elements" (
  "groupId"             INTEGER,
  "documenttype"        TEXT,
  "path"                TEXT,
  "packageid"           INTEGER,
  "package"             TEXT,
  "tag"                 TEXT,
  "subfield"            TEXT,
  "number-of-record"    INTEGER,
  "number-of-instances" INTEGER,
  "min"                 INTEGER,
  "max"                 INTEGER,
  "mean"                REAL,
  "stddev"              REAL,
  "histogram"           TEXT
);
CREATE INDEX IF NOT EXISTS "groupId" ON "groupped_marc_elements" ("groupId");
EOF

log "clean groupped_marc_elements"
sqlite3 ${OUTPUT_DIR}/qa_catalogue.sqlite << EOF
DELETE FROM groupped_marc_elements;
EOF

log "create headless CSV"
tail -n +2 ${OUTPUT_DIR}/completeness-groupped-marc-elements.csv > ${OUTPUT_DIR}/completeness-groupped-marc-elements-noheader.csv

log "import marc elements"
sqlite3 ${OUTPUT_DIR}/qa_catalogue.sqlite << EOF
.mode csv
.import ${OUTPUT_DIR}/completeness-groupped-marc-elements-noheader.csv groupped_marc_elements
EOF

log "drop headless CSV"
rm ${OUTPUT_DIR}/completeness-groupped-marc-elements-noheader.csv