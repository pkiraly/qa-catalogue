#!/usr/bin/env bash
# KB (Koninklijke Bibliotheek van Nederland)
# https://www.kb.nl

. ./setdir.sh
NAME=kb
MARC_DIR=${BASE_INPUT_DIR}/kb/2023-02-24
TYPE_PARAMS="--marcxml --emptyLargeCollectors"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=kb-marc*.xml.gz

. ./common-script
