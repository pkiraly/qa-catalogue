#!/usr/bin/env bash

. ./setdir.sh
NAME=nlm
TYPE_PARAMS="--emptyLargeCollectors"
MARC_DIR=${BASE_INPUT_DIR}/nlm
MASK=catplusbase.202?

. ./common-script

echo "DONE"
exit 0
