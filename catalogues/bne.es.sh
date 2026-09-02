#!/usr/bin/env bash
# National Library of Spain
# https://bnelab.bne.es/en/dataset/bibliographic-catalogue/

. ./setdir.sh
QAC_ROOT=$(realpath $(dirname ${0})/..)

NAME=bne
MARC_DIR=${BASE_INPUT_DIR}/bne.es
TYPE_PARAMS="--emptyLargeCollectors"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"

TYPE_PARAMS="${TYPE_PARAMS} --translationConfigurationFile ${QAC_ROOT}/scripts/translations/translations-shacl.yml"
TYPE_PARAMS="${TYPE_PARAMS} --translationDebugFailedRules 245c,7004"
TYPE_PARAMS="${TYPE_PARAMS} --translationPlaceNameDictionaryDir ${PLACE_NAME_DIR}"
TYPE_PARAMS="${TYPE_PARAMS} --translationExport translations-export.jsonld"

PLACE_NAME_DIR=$(realpath ${QAC_ROOT}/../place-names/data)
echo "PLACE_NAME_DIR: ${PLACE_NAME_DIR}"

MASK=*.mrc.gz

. ./common-script
