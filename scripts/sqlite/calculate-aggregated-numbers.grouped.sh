#!/usr/bin/env bash
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# calculate and store the aggregated number of instances and records 
# for issue types, categories and paths within each groups
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
set -ueo pipefail

log() {
  echo "$(date +'%F %T')> $1"
}

OUTPUT_DIR=$1
NAME=$2
SOLR_FOR_SCORES_URL=${3:-}

log "OUTPUT_DIR: $OUTPUT_DIR"

if [[ -f $(pwd)/solr-functions ]]; then
  . ./solr-functions
else
  . ./../../solr-functions
fi

if [ -z "$SOLR_FOR_SCORES_URL" ]; then
  SOLR_CORE="${NAME}_validation"
else
  SOLR_HOST=$(extract_host "$SOLR_FOR_SCORES_URL")
  SOLR_CORE=$(extract_core "$SOLR_FOR_SCORES_URL")
fi
log "using Solr at $SOLR_HOST/$SOLR_CORE"

log "calculate numbers"

# creating
# $OUTPUT_DIR/issue-grouped-types.csv
# $OUTPUT_DIR/issue-grouped-categories.csv
# $OUTPUT_DIR/issue-grouped-paths.csv
Rscript scripts/sqlite/qa_catalogue.grouping.R "$OUTPUT_DIR" "$SOLR_HOST" "$SOLR_CORE"

log "import issue_grouped_types"
tail -n +2 "$OUTPUT_DIR/issue-grouped-types.csv" > "$OUTPUT_DIR/issue-grouped-types-noheader.csv"
sqlite3 "$OUTPUT_DIR/qa_catalogue.sqlite" << EOF
.mode csv
.import $OUTPUT_DIR/issue-grouped-types-noheader.csv issue_grouped_types

EOF
rm "$OUTPUT_DIR/issue-grouped-types-noheader.csv"

log "import issue_grouped_categories"
tail -n +2 "$OUTPUT_DIR/issue-grouped-categories.csv" > "$OUTPUT_DIR/issue-grouped-categories-noheader.csv"
sqlite3 "$OUTPUT_DIR/qa_catalogue.sqlite" << EOF
.mode csv
.import $OUTPUT_DIR/issue-grouped-categories-noheader.csv issue_grouped_categories
EOF

rm "$OUTPUT_DIR/issue-grouped-categories-noheader.csv"

log "import issue_grouped_paths"
tail -n +2 "$OUTPUT_DIR/issue-grouped-paths.csv" > "$OUTPUT_DIR/issue-grouped-paths-noheader.csv"
sqlite3 "$OUTPUT_DIR/qa_catalogue.sqlite" << EOF
.mode csv
.import $OUTPUT_DIR/issue-grouped-paths-noheader.csv issue_grouped_paths
EOF

rm "$OUTPUT_DIR/issue-grouped-paths-noheader.csv"

log "index sqlite tables"
sqlite3 "$OUTPUT_DIR/qa_catalogue.sqlite" << EOF
CREATE INDEX IF NOT EXISTS "types_groupId" ON issue_grouped_types ("groupId");
CREATE INDEX IF NOT EXISTS "types_typeId" ON issue_grouped_types ("typeId");
CREATE INDEX IF NOT EXISTS "categories_groupId" ON issue_grouped_categories ("groupId");
CREATE INDEX IF NOT EXISTS "categories_categoryId" ON issue_grouped_categories ("categoryId");
CREATE INDEX IF NOT EXISTS "paths_groupId" ON issue_grouped_paths ("groupId");
CREATE INDEX IF NOT EXISTS "paths_typeId" ON issue_grouped_paths ("typeId");
CREATE INDEX IF NOT EXISTS "paths_path" ON issue_grouped_paths ("path");
EOF
