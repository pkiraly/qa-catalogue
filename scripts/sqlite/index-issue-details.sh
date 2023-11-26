#!/usr/bin/env bash
#
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# calculate and store the aggregated number of instances and records
# for issue types, categories and paths within each groups
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
#

log() {
  echo "$(date +'%F %T')> $1"
}

OUTPUT_DIR=$1
NAME=$2
HAS_GROUP_PARAM=$3
ONLY_INDEX=$4
SOLR_FOR_SCORES_URL=$5

log "OUTPUT_DIR: ${OUTPUT_DIR}"

if [[ -f $(pwd)/solr-functions ]]; then
  . ./solr-functions
else
  . ./../../solr-functions
fi

if [[ "${SOLR_FOR_SCORES_URL}" != "" ]]; then
  SOLR_HOST=$(extract_host $SOLR_FOR_SCORES_URL)
  SOLR_CORE=$(extract_core $SOLR_FOR_SCORES_URL)
else
  SOLR_CORE=${NAME}_validation
fi
log "using Solr at ${SOLR_HOST}/${SOLR_CORE}"
log "create Solr core"

CORE_EXISTS=$(check_core $SOLR_CORE)
log "$SOLR_CORE exists: $CORE_EXISTS"
if [[ $CORE_EXISTS != 1 && "${ONLY_INDEX}" == "0" ]]; then
  echo "Create Solr core '$SOLR_CORE'"
  create_core $SOLR_CORE
  prepare_schema $SOLR_CORE
else
  purge_core $SOLR_CORE
fi

log "populate Solr core"

if [[ "${HAS_GROUP_PARAM}" == "0" ]]; then
  # index id-groupid.csv and issue-details.csv
  php scripts/sqlite/validation-result-indexer-simple.php ${OUTPUT_DIR} ${SOLR_HOST} ${SOLR_CORE}
else
  # index id-groupid.csv and issue-details.csv
  php scripts/sqlite/validation-result-indexer-grouped.php ${OUTPUT_DIR} ${SOLR_HOST} ${SOLR_CORE}
fi

optimize_core $SOLR_CORE
