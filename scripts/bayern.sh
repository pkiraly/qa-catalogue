#!/usr/bin/env bash

. ./setdir.sh
NAME=bayern
MARC_DIR=${BASE_INPUT_DIR}/bayern/marc
TYPE_PARAMS="--marcxml --marcVersion B3KAT --emptyLargeCollectors"
MASK=b3kat*.xml

. ./common-script

echo "DONE"
exit 0
