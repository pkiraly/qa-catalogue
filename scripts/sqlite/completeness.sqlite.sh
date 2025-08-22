#!/usr/bin/env bash
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# Strores marc-elements.csv into SQLite
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
set -ueo pipefail

dir=$(dirname $0)

log() {
  timestamp=$(date +"%F %T")
  echo -en "\033[0D\033[1;37m$timestamp>\033[0m "
  echo "$1"
}

OUTPUT_DIR=$1
HAS_GROUP_PARAM=$2

log "OUTPUT_DIR: $OUTPUT_DIR"
log "HAS_GROUP_PARAM: $HAS_GROUP_PARAM"

log "create table marc_elements"
sqlite3 "$OUTPUT_DIR/qa_catalogue.sqlite" < $dir/marc-elements.sql

log "clean marc_elements"
sqlite3 "$OUTPUT_DIR/qa_catalogue.sqlite" << EOF
DELETE FROM marc_elements;
EOF

log "create headless CSV"
if [[ "$HAS_GROUP_PARAM" == "1" ]]; then
  tail -n +2 "$OUTPUT_DIR/completeness-grouped-marc-elements.csv" > "$OUTPUT_DIR/marc-elements-noheader.csv"
else
  tail -n +2 "$OUTPUT_DIR/marc-elements.csv" > "$OUTPUT_DIR/marc-elements-noheader.csv"
fi

log "import marc elements"
sqlite3 "$OUTPUT_DIR/qa_catalogue.sqlite" << EOF
.mode csv
.import $OUTPUT_DIR/marc-elements-noheader.csv marc_elements
EOF

log "drop headless CSV"
rm "$OUTPUT_DIR/marc-elements-noheader.csv"
