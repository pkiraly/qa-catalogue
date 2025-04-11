#!/usr/bin/env bash
# Danish Royal Library
# https://data.nls.uk/data/metadata-collections/boslit/

. ./setdir.sh
NAME=dkb
TYPE_PARAMS="--marcxml --emptyLargeCollectors --processRecordsWithoutId"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"

QAC_ROOT=$(realpath $(dirname ${0})/..)

# SHACL
TYPE_PARAMS="${TYPE_PARAMS} --shaclConfigurationFile ${QAC_ROOT}/src/test/resources/translation/translations-shacl.yml"
TYPE_PARAMS="${TYPE_PARAMS} --shaclOutputType STATUS"
TYPE_PARAMS="${TYPE_PARAMS} --shaclOutputFile translations.csv"

# translations
TYPE_PARAMS="${TYPE_PARAMS} --translationDebugFailedRules 245c,7004"
TYPE_PARAMS="${TYPE_PARAMS} --translationPlaceNameDictionaryDir ${QAC_ROOT}/../pkiraly/place-names/data"
TYPE_PARAMS="${TYPE_PARAMS} --translationExport translations-export.jsonld"

MASK=kb_*.xml.gz

. ./common-script
