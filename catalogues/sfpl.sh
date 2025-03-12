#!/usr/bin/env bash

. ./setdir.sh

NAME=sfpl
MARC_DIR=${BASE_INPUT_DIR}/san-francisco-public-library/marc
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"

MASK=*.mrc

. ./common-script
