#!/usr/bin/env bash
# Országos Széchényi Könyvtár (Hungarian National Library)
# https://www.oszk.hu/

. ./setdir.sh
NAME=oszk
TYPE_PARAMS="--emptyLargeCollectors --defaultEncoding UTF8 --marcVersion HUNMARC"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"

# translations
QAC_ROOT=$(realpath $(dirname ${0})/..)
TYPE_PARAMS="${TYPE_PARAMS} --translationConfigurationFile ${QAC_ROOT}/scripts/translations/translations-shacl.yml"
TYPE_PARAMS="${TYPE_PARAMS} --shaclOutputType STATUS"
TYPE_PARAMS="${TYPE_PARAMS} --translationDebugFailedRules 245c,7004"
TYPE_PARAMS="${TYPE_PARAMS} --translationPlaceNameDictionaryDir ${QAC_ROOT}/../pkiraly/place-names/data"
TYPE_PARAMS="${TYPE_PARAMS} --translationExport translations-export.jsonld"

MASK=OSZK_*.mrc.gz

. ./common-script
