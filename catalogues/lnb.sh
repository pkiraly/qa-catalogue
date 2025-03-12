#!/usr/bin/env bash
# Latvijas Nacionālā bibliotēka
# https://lnb.lv/

. ./setdir.sh

NAME=lnb
MARC_DIR=${BASE_INPUT_DIR}/lnb.lv
TYPE_PARAMS="--marcxml --emptyLargeCollectors"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=nat_bibliography-2017_2020-marc.xml.gz

. ./common-script
