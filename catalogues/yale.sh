#!/usr/bin/env bash
# Yale Library
# https://library.yale.edu/

. ./setdir.sh

NAME=yale
MARC_DIR=${BASE_INPUT_DIR}/yale/2023-11-05
TYPE_PARAMS="--emptyLargeCollectors --indexWithTokenizedField --indexFieldCounts"
MASK=bib_*.mrc.gz

. ./common-script
