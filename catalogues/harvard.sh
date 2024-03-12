#!/usr/bin/env bash
# Harvard Library
# https://library.harvard.edu/

. ./setdir.sh

NAME=harvard
MARC_DIR=${BASE_INPUT_DIR}/harvard/20220215
TYPE_PARAMS="--marcxml --emptyLargeCollectors --indexWithTokenizedField"
MASK=20220215_*.xml.gz

. ./common-script
