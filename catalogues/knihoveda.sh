#!/usr/bin/env bash
# Knihoveda
# https://www.zb.uzh.ch/

. ./setdir.sh

NAME=knihoveda
MARC_DIR=${BASE_INPUT_DIR}/knihoveda/
TYPE_PARAMS="--marcxml --emptyLargeCollectors"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=K?.xml

. ./common-script
