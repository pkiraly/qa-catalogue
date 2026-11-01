#!/usr/bin/env bash
# KB (Koninklijke Bibliotheek van Nederland)
# https://www.kb.nl

. ./setdir.sh
QAC_ROOT=$(realpath $(dirname ${0})/..)

NAME=kb
MARC_DIR=${BASE_INPUT_DIR}/kb/2023-02-24
TYPE_PARAMS="--marcxml --emptyLargeCollectors"

# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --solrForScoresUrl http://localhost:8983/solr/kbr_scores"
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"

PLACE_NAME_DIR=$(realpath ${QAC_ROOT}/../pkiraly/place-names/data)
echo "PLACE_NAME_DIR: ${PLACE_NAME_DIR}"

# translations
TYPE_PARAMS="${TYPE_PARAMS} --translationConfigurationFile ${QAC_ROOT}/scripts/translations/translations-shacl.yml"
TYPE_PARAMS="${TYPE_PARAMS} --translationDebugFailedRules 245c,7004"
TYPE_PARAMS="${TYPE_PARAMS} --translationPlaceNameDictionaryDir ${PLACE_NAME_DIR}"
TYPE_PARAMS="${TYPE_PARAMS} --translationExport translations-export.jsonld"

MASK=kb-marc*.xml.gz

. ./common-script
