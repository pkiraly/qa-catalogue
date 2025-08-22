#!/usr/bin/env bash

. ./setdir.sh

NAME=bzbw
MARC_DIR=${BASE_INPUT_DIR}/bzbw/v2
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=*.mrc

. ./common-script
