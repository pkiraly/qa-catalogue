#!/usr/bin/env bash

. ./setdir.sh
NAME=onb
MARC_DIR=${BASE_INPUT_DIR}/onb
TYPE_PARAMS=" --emptyLargeCollectors --fixAlma"
MASK=onb*.mrc

. ./common-script

echo "DONE"
exit 0
