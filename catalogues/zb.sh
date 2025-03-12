#!/usr/bin/env bash
# Zentralbibliothek Zürich
# https://www.zb.uzh.ch/

. ./setdir.sh

NAME=zb
MARC_DIR=${BASE_INPUT_DIR}/zb/
TYPE_PARAMS="--marcVersion ZB --marcxml --fixAlma --emptyLargeCollectors"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=${MASK:=datadump-zb3.xml.gz}

. ./common-script
