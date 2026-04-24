#!/usr/bin/env bash
# National and University Library in Zagreb / Nacionalna i sveučilišna knjižnica u Zagrebu
# https://www.nsk.hr/

. ./setdir.sh
QAC_ROOT=$(realpath $(dirname ${0})/..)

NAME=nsk
MARC_DIR=${BASE_INPUT_DIR}/nsk
# MARC_DIR=${BASE_INPUT_DIR}/current
TYPE_PARAMS="--emptyLargeCollectors --marcxml"
# 911
# TYPE_PARAMS="${TYPE_PARAMS} --ignorableFields 590,591,592,593,594,595,596,659,900,912,916,940,941,942,944,945,946,948,949,950,951,952,953,954,970,971,972,973,975,977,988,989"
TYPE_PARAMS="${TYPE_PARAMS} --compoundFields subject=600\$*,610\$*,647\$*,648\$*,650\$*,651\$*,654\$*"
TYPE_PARAMS="${TYPE_PARAMS} --solrForScoresUrl http://localhost:8983/solr/kbr_scores"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"

# TYPE_PARAMS="${TYPE_PARAMS} --shaclConfigurationFile /opt/shacl4bib/kbr-full.yaml"
# TYPE_PARAMS="${TYPE_PARAMS} --shaclConfigurationFile /home/qa_admin/git/data-validation-rules/kbr-full.yaml"
# TYPE_PARAMS="${TYPE_PARAMS} --shaclOutputType STATUS"

PLACE_NAME_DIR=$(realpath ${QAC_ROOT}/../place-names/data)

# echo "PLACE_NAME_DIR: ${PLACE_NAME_DIR}"

# translations
TYPE_PARAMS="${TYPE_PARAMS} --translationConfigurationFile ${QAC_ROOT}/scripts/translations/translations-shacl.yml"
TYPE_PARAMS="${TYPE_PARAMS} --translationDebugFailedRules 245c,7004"
TYPE_PARAMS="${TYPE_PARAMS} --translationPlaceNameDictionaryDir ${PLACE_NAME_DIR}"
TYPE_PARAMS="${TYPE_PARAMS} --translationExport translations-export.jsonld"

MASK=${MASK:-000*.xml.gz}

. ./common-script
