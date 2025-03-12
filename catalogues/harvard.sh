#!/usr/bin/env bash
# Harvard Library
# https://library.harvard.edu/

. ./setdir.sh

NAME=harvard
MARC_DIR=${BASE_INPUT_DIR}/harvard/20220215
TYPE_PARAMS="--marcxml --emptyLargeCollectors"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=20220215_*.xml.gz

. ./common-script
