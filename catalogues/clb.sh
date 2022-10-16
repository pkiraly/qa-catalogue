#!/usr/bin/env bash

. ./setdir.sh
NAME=ucl
MARC_DIR=${BASE_INPUT_DIR}/ucl
TYPE_PARAMS="--marcxml --emptyLargeCollectors --marcVersion NKCR"
MASK=ucloall.xml.gz

. ./common-script

echo "DONE"
exit 0
