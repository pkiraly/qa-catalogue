#!/usr/bin/env bash
# Yale Library
# https://library.yale.edu/

. ./setdir.sh

NAME=yale
MARC_DIR=${BASE_INPUT_DIR}/yale/2024-02-11
TYPE_PARAMS="--emptyLargeCollectors"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=bib_*.mrc.gz

. ./common-script
