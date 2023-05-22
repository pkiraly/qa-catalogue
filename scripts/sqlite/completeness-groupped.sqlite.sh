#!/usr/bin/env bash
#
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# Strores completeness-grouped-marc-elements.csv into SQLite
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
#

log() {
  timestamp=`date +"%F %T"`
  echo -en "\033[0D\033[1;37m$timestamp>\033[0m "
  echo "$1"
}

OUTPUT_DIR=$1

log "create table grouped_marc_elements"
sqlite3 ${OUTPUT_DIR}/qa_catalogue.sqlite << EOF
CREATE TABLE IF NOT EXISTS "grouped_marc_elements" (
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
CREATE INDEX IF NOT EXISTS "gme_groupId" ON "grouped_marc_elements" ("groupId");
CREATE INDEX IF NOT EXISTS "gme_documenttype" ON "grouped_marc_elements" ("documenttype");
EOF

log "clean grouped_marc_elements"
sqlite3 ${OUTPUT_DIR}/qa_catalogue.sqlite << EOF
DELETE FROM grouped_marc_elements;
EOF

log "create headless CSV"
tail -n +2 ${OUTPUT_DIR}/completeness-grouped-marc-elements.csv > ${OUTPUT_DIR}/completeness-grouped-marc-elements-noheader.csv

log "import marc elements"
sqlite3 ${OUTPUT_DIR}/qa_catalogue.sqlite << EOF
.mode csv
.import ${OUTPUT_DIR}/completeness-grouped-marc-elements-noheader.csv grouped_marc_elements
EOF

log "drop headless CSV"
rm ${OUTPUT_DIR}/completeness-grouped-marc-elements-noheader.csv