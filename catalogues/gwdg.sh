#!/usr/bin/env bash
# BOSLIT (Bibliography of Scottish Literature in Translation)
# https://data.nls.uk/data/metadata-collections/boslit/

. ./setdir.sh
NAME=gwdg
# TYPE_PARAMS="--marcVersion GENT"
TYPE_PARAMS="--marcxml --emptyLargeCollectors --defaultEncoding ISO8859_1"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
TYPE_PARAMS="${TYPE_PARAMS} --solrForScoresUrl http://localhost:8983/solr/gwdg_validation"

QAC_ROOT=$(realpath $(dirname ${0})/..)

# SHACL
TYPE_PARAMS="${TYPE_PARAMS} --translationConfigurationFile ${QAC_ROOT}/src/test/resources/translation/translations-shacl.yml"
TYPE_PARAMS="${TYPE_PARAMS} --shaclOutputType STATUS"
TYPE_PARAMS="${TYPE_PARAMS} --shaclOutputFile translations.csv"

# translations
TYPE_PARAMS="${TYPE_PARAMS} --translationDebugFailedRules 245c,7004"
TYPE_PARAMS="${TYPE_PARAMS} --translationPlaceNameDictionaryDir ${QAC_ROOT}/../pkiraly/place-names/data"
TYPE_PARAMS="${TYPE_PARAMS} --translationExport translations-export.jsonld"

MASK=koha.xml

. ./common-script
