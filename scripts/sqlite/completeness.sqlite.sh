#!/usr/bin/env bash
#
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# Strores marc-elements.csv into SQLite
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
#

log() {
  timestamp=`date +"%F %T"`
  echo -en "\033[0D\033[1;37m$timestamp>\033[0m "
  echo "$1"
}

OUTPUT_DIR=$1
HAS_GROUP_PARAM=$2

log "OUTPUT_DIR: ${OUTPUT_DIR}"
log "HAS_GROUP_PARAM: ${HAS_GROUP_PARAM}"

log "create table marc_elements"
sqlite3 ${OUTPUT_DIR}/qa_catalogue.sqlite << EOF
DROP TABLE IF EXISTS "marc_elements";
CREATE TABLE IF NOT EXISTS "marc_elements" (
  "groupId"             INTEGER,
  "documenttype"        TEXT,
  "path"                TEXT,
  "sortkey"             TEXT,
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
CREATE INDEX IF NOT EXISTS "gme_groupId" ON "marc_elements" ("groupId");
CREATE INDEX IF NOT EXISTS "gme_documenttype" ON "marc_elements" ("documenttype");
CREATE INDEX IF NOT EXISTS "gme_sortkey" ON "marc_elements" ("sortkey");
EOF

log "clean marc_elements"
sqlite3 ${OUTPUT_DIR}/qa_catalogue.sqlite << EOF
DELETE FROM marc_elements;
EOF

log "create headless CSV"
if [[ "${HAS_GROUP_PARAM}" == "1" ]]; then
  tail -n +2 ${OUTPUT_DIR}/completeness-grouped-marc-elements.csv > ${OUTPUT_DIR}/marc-elements-noheader.csv
else
  tail -n +2 ${OUTPUT_DIR}/marc-elements.csv > ${OUTPUT_DIR}/marc-elements-noheader.csv
fi

log "import marc elements"
sqlite3 ${OUTPUT_DIR}/qa_catalogue.sqlite << EOF
.mode csv
.import ${OUTPUT_DIR}/marc-elements-noheader.csv marc_elements
EOF

log "drop headless CSV"
rm ${OUTPUT_DIR}/marc-elements-noheader.csv